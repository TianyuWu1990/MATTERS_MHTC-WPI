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
	Double value;	

	public Line(){
	}
		
	public void setYear(String year){
		this.year = Integer.parseInt(year);
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
	
	public Double getMetricValue(){
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

