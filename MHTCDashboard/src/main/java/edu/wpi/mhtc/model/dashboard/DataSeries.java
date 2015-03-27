/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.model.dashboard;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class DataSeries {

    private List<Data> dataPoints;
    private Data recent;
    
    private Metric metric;
    private State state;

    private String trend;

    public DataSeries(Metric metric, State state) {
        this.metric = metric;
        this.state = state;
        dataPoints = new LinkedList<Data>();
        
        trend = "no_change";
    }
    
    public void addData(List<Data> data) {
        this.dataPoints.addAll(data);
        
        updateData();
    }
    
    public List<Data> getDataPoints() {
        return dataPoints;
    }
    
    // TODO Json ignore this and getState()
    public Metric getMetric() {
        return metric;
    }
    
    public int getMetricId() {
        return metric.getId();
    }
    
    public State getState() {
        return state;
    }
    
    public int getStateId() {
        return state.getId();
    }
    
    public String getTrend() {
        return trend;
    }
    
    public Data getRecent() {
        return recent;
    }
    
    private void updateData() {

        if (dataPoints.size() > 0) {
        
            Collections.sort(dataPoints, new Comparator<Data>() {
    
                @Override
                public int compare(Data o1, Data o2) {
                    return o1.getYear() - o2.getYear();
                }
                
            });
            
            recent = dataPoints.get(0);
            Data old = dataPoints.get(0);
            
            for (Data data : dataPoints) {
                if (recent.getYear() < data.getYear()) {
                    recent = data;
                }
                if (old.getYear() > data.getYear()) {
                    old = data;
                }
            }
            
            double diff = recent.getValue() - old.getValue();
            
            String reversed;
            if (metric.getTrendType().equals("reversed"))
                reversed = "_reversed";
            else
                reversed = "";
            
            if (diff > 0) {
                trend = "up"+reversed;
            } else if (diff < 0) {
                trend = "down"+reversed;
            } else {
                trend = "no_change";
            }
            
        }
    }

}
