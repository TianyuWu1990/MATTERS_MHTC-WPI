package edu.wpi.mhtc.model.persistence.queries;

import java.util.LinkedList;
import java.util.List;

import edu.wpi.mhtc.model.persistence.Query;
import edu.wpi.mhtc.model.persistence.QueryParam;

public class NoParamsQuery<T> implements Query<T> {

    private String queryName;
    private Class<?> type;
    
    public NoParamsQuery<T> withName(String queryName) {
        this.queryName = queryName;
        return this;
    }
    
    public NoParamsQuery<T> withReturnType(Class<?> type) {
        this.type = type;
        return this;
    }

    @Override
    public String getName() {
        return queryName;
    }

    @Override
    public List<QueryParam> getParams() {
        return new LinkedList<QueryParam>();
    }

    @Override
    public Class<?> getType() {
        return type;
    }

    
    
}
