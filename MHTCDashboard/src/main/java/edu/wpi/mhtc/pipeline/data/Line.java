package edu.wpi.mhtc.pipeline.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.wpi.mhtc.pipeline.config.StateInfoConfig;


/**
 * Holds all data needed for one tuple to be inserted into the statistics table of the database.
 * @author cakuhlman
 *
 */
public class Line {

	private int year;
	private State state;
	private Metric metric;
	

	public Line(){
	}
	
	/**
	 * 
	 * @param stateName 
	 * @throws Exception if the stateName cannot be found in the database
	 */
	public void setState(String stateName) throws Exception{
		state = StateInfoConfig.getInstance().getStateByFullName(stateName);
	}
	
//	TODO: do we ever check for a valid year?
	public void setYear(String year){
		this.year = Integer.parseInt(year);
	}
	
	public void addMetric(Metric metric) throws Exception{
		if(metric.isValid()){
			this.metric = metric;
		}
		else throw new Exception("Invalid Metric for category ");
	}

	public int getYear() {
		return year;
	}
	
	
	public int getStateID() {
		return Integer.parseInt(state.getStateID());
	}
	

	public int getMetricID(){
		return metric.getID();
	}
	
	public Float getMetricValue(){
		return metric.getValue();
	}
	/**
	 * 
	 * @return Whether this line contains all needed data to be inserted into the database.
	 */
	
	public boolean isValid(){
		return (year!=0 && state!=null && metric!=null);
	}


}

