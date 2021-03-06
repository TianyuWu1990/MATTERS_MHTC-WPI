/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dashboard.pipeline.data;

/**
 * Corresponds to one value to be inserted into db.
 * @author cakuhlman
 *
 */
public class Metric {
	
	int id;
	String name;
	Double value;
	
	/**
	 * One value stored in the database is represented as a Metric object. It is associated with only one category.
	 * @param name
	 * @param id metricID in database
	 */
	
	public Metric(String name, int id){
		this.name = name;		
		this.id = id;
	}
		
	/**
	 * 
	 * @param value of the metric (to be inserted into database). 
	 */
	public void setValue(Double value){
		this.value = value;
	}

	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Double getValue() {
		return value;
	}
	
	
	/**
	 * 
	 * @return Whether the value of this metric has been set.
	 */
	
	public boolean isValid(){
		return value != null;
	}

	public void clearValue() {
		value = null;
		
	}

}
