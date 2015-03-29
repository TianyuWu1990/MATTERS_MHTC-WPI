/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.model.admin;

/**
 * Statistic model for data
 * @author Alex Fortier
 *
 */
public class Statistic {

	int stateID;
	String stateName;
	int metricID;
	String metricName;
	int year;
	Double value;
	
	/**
	 * Check whether a Statistic is valid to be added to DB
	 * @return
	 */
	public boolean isValid(){
		return (year != 0 && stateID != 0 && value != null);
	}
	
	/**
	 * @return the stateID
	 */
	public int getStateID() {
		return stateID;
	}
	
	/**
	 * @param stateID the stateID to set
	 */
	public void setStateID(int stateID) {
		this.stateID = stateID;
	}
	
	/**
	 * @return the metricID
	 */
	public int getMetricID() {
		return metricID;
	}
	
	/**
	 * @param metricID the metricID to set
	 */
	public void setMetricID(int metricID) {
		this.metricID = metricID;
	}
	
	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}
	
	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}
	
	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}
	
	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}
	
	/**
	 * @return the stateName
	 */
	public String getStateName() {
		return stateName;
	}

	/**
	 * @param stateName the stateName to set
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	/**
	 * @return the metricName
	 */
	public String getMetricName() {
		return metricName;
	}

	/**
	 * @param metricName the metricName to set
	 */
	public void setMetricName(String metricName) {
		this.metricName = metricName;
	}

}
