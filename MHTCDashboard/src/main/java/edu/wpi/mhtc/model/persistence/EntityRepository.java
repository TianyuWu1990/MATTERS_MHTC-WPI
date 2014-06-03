package edu.wpi.mhtc.model.persistence;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import edu.wpi.mhtc.persistence.PSqlRowMapper;
import edu.wpi.mhtc.persistence.PSqlStringMappedJdbcCall;

public class EntityRepository {

    private final String basePackage;
    private List<QueryableInfo> queryables;
    private JdbcTemplate template;
    
    public EntityRepository(String basePackage, JdbcTemplate template) {
        this.basePackage = basePackage;
        this.template = template;
        queryables = new LinkedList<QueryableInfo>();
        
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Queryable.class));
        
        Set<BeanDefinition> definitions = scanner.findCandidateComponents(basePackage);
        
        for (BeanDefinition definition : definitions)
            parseBeanDefinition(definition);
    }
    
    private void parseBeanDefinition(BeanDefinition definition) {
        String className = definition.getBeanClassName();
        
        try {
            Class<?> cls = Class.forName(className);
            registerQueryable(cls);
            
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class idenfitied by scanner but cannot be found in classpath: " + className);
        }
        
    }
    
    public void registerQueryable(Class<?> cls) {
        
        Field[] fields = cls.getDeclaredFields();
        QueryableInfo info = new QueryableInfo(cls);
        
        for (Field  field : fields) {
            QueryProperty props = field.getAnnotation(QueryProperty.class);
            if (props == null)
                continue;
            
            String name = props.name();
            int type = props.type();
            
            String fieldName = field.getName();
            String propName = fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
            String getterName = "get" + propName;
            String setterName = "set" + propName;
            if (field.getType() == boolean.class)
                getterName = "is" + propName;
            
            Method getter, setter;
            
            try {
                getter =  cls.getMethod(getterName);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Found property with name " + fieldName + " on queryable " + cls.getName() + " but could not find associated getter method " + getterName, e);
            } catch (SecurityException e) {
                throw new RuntimeException("Unable to access method " + getterName + " on class " + cls.getName(), e);
            }
            
            try {
                setter =  cls.getMethod(setterName, field.getType());
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Found property with name " + fieldName + " on queryable " + cls.getName() + " but could not find associated setter method " + setterName, e);
            } catch (SecurityException e) {
                throw new RuntimeException("Unable to access method " + setterName + " on class " + cls.getName(), e);
            }
            
            info.addField(new QueryField(name, fieldName, type, getter, setter));
        }
        
        queryables.add(info);
    }
    
    @SuppressWarnings("unchecked")
    public <T> List<T> executeQuery(Query<T> query) {
        
        PSqlStringMappedJdbcCall<T> call = new PSqlStringMappedJdbcCall<T>(template);
        call.withSchemaName("mhtc_sch").withProcedureName(query.getName());
        
        QueryableInfo queryable = null;
        
        for (QueryableInfo info : queryables) {
            if (info.type == query.getType())
                queryable = info;
        }
        
        if (queryable == null) {
            throw new RuntimeException("Could not find valid queryable object for type " + query.getType());
        }
        
        call.addDeclaredRowMapper((PSqlRowMapper<T>) queryable.createMapper(query.getType()));
        
        
        List<QueryParam> params = query.getParams();
        Map<String, Object> inputParams = new HashMap<String, Object>();
        
        for (QueryParam param : params) {
            call.addDeclaredParameter(param.getParam());
            inputParams.put(param.getParam().getName(), param.getValue());
        }
        
        return call.execute(inputParams);
        
    }
    
    private class QueryableInfo {
        
        private List<QueryField> fields;
        private final Class<?> type;
        
        public QueryableInfo(Class<?> type) {
            this.type = type;
            this.fields = new LinkedList<QueryField>();
        }
        
        public void addField(QueryField field) {
            fields.add(field);
        }
        
        public <T> PSqlRowMapper<T> createMapper(final Class<T> type) {
            
            return new PSqlRowMapper<T>() {

                @Override
                public T mapRow(SqlRowSet rs, int rowNum) throws SQLException {
                    try {
                        T instance = type.newInstance();
                        
                        for (QueryField field : fields) {
                            if (field.getSqlType() == Types.INTEGER) {
                                field.getSetter().invoke(instance, rs.getInt(field.getName()));
                            } else if (field.getSqlType() == Types.VARCHAR) {
                                field.getSetter().invoke(instance, rs.getString(field.getName()));
                            } else if (field.getSqlType() == Types.BOOLEAN) {
                                field.getSetter().invoke(instance, rs.getBoolean(field.getName()));
                            }
                        }
                        
                        return instance;
                        
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidResultSetAccessException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalArgumentException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
                
            };
            
        }
        
    }
    
    private class QueryField {
        
        private final String name;
        private final String fieldName;
        private final int sqlType;
        private final Method setter;
        private final Method getter;
        
        public QueryField(String name, String fieldName, int sqlType, Method getter, Method setter) {
            this.name = name;
            this.fieldName = fieldName;
            this.sqlType = sqlType;
            this.setter = setter;
            this.getter = getter;
        }

        public String getName() {
            return name;
        }
        
        public String getFieldName() {
            return fieldName;
        }

        public int getSqlType() {
            return sqlType;
        }

        public Method getSetter() {
            return setter;
        }

        public Method getGetter() {
            return getter;
        }
        
    }
}
