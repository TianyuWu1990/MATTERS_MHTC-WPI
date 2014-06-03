package edu.wpi.mhtc.model.persistence;

import java.util.List;


public interface Query<T> {


    String getName();
    List<QueryParam> getParams();
    Class<?> getType();
    
}
