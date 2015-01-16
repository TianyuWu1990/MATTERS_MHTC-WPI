/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dashboard.pipeline.data;

/**
 * Holds all data needed for one tuple to be inserted into the statistics table of the database.
 * @author cakuhlman
 *
 */
public class Line {

	int stateID;
	int metricID;
	int year;
	Float value;	

	public Line(){
	}
	
	/**
	 * 
	 * @param stateName 
	 * @throws Exception if the stateName cannot be found in the database
	 */
	public void setState(String stateName) throws Exception{
		int id = State.getStateID(stateName);
		if(id == -1){	//this is not a valid state
			throw new Exception("Bad state name "+ stateName);
		}
		stateID = id;
	}
	
	public void setYear(String year){
		this.year = Integer.parseInt(year);
	}
	
	public void addMetric(Metric metric){
		if(metric.isValid()){
			metricID = metric.getID();
			value = new Float(metric.getValue());	
		}
	}

	public int getYear() {
		return year;
	}
	
	
	public int getStateID() {
		return stateID;
	}
	

	public int getMetricID(){
		return metricID;
	}
	
	public Float getMetricValue(){
		return value;
	}
	/**
	 * 
	 * @return Whether this line contains all needed data to be inserted into the database.
	 */
	
	public boolean isValid(){
		return (year!=0 && stateID != 0 && value!=null);
	}

//	for testing
	@Override
	public String toString(){
		return stateID + ", " + metricID + "," + year + ", "+value ;
		
	}
}

