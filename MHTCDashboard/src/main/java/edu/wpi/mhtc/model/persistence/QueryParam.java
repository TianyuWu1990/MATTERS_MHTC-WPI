package edu.wpi.mhtc.model.persistence;

import org.springframework.jdbc.core.SqlParameter;

public class QueryParam {

    private final SqlParameter param;
    private final Object value;
    
    public QueryParam(SqlParameter param, Object value) {
        this.param = param;
        this.value = value;
    }

    public SqlParameter getParam() {
        return param;
    }

    public Object getValue() {
        return value;
    }
    
}
