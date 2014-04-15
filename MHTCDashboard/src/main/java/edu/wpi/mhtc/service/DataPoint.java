package edu.wpi.mhtc.service;

import edu.wpi.mhtc.persistence.DBMetric;
import edu.wpi.mhtc.persistence.DBState;

public class DataPoint {

	private DBState state;
	private DBMetric metric;
	private int year;
	private double value;

	public DataPoint(DBState state, DBMetric metric, int year, double value) {
		this.state = state;
		this.metric = metric;
		this.year = year;
		this.value = value;
	}

	public DBState getStateid() {
		return state;
	}
	
	public DBMetric getMetricid() {
		return metric;
	}

	public int getYear() {
		return year;
	}

	public double getValue() {
		return value;
	}

}
