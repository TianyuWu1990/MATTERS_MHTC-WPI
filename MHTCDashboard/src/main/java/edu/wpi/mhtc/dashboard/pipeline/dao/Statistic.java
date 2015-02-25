package edu.wpi.mhtc.dashboard.pipeline.dao;

public class Statistic {

	int stateID;
	String stateName;
	int metricID;
	String metricName;
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
