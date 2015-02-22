package edu.wpi.mhtc.dashboard.pipeline.dao;

public class Statistic {

	int stateID;
	int metricID;
	int year;
	double value;
	
	public Statistic() {}
	
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
	
}
