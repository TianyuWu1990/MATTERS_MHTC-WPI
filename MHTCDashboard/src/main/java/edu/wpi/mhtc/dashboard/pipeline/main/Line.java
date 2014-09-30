package edu.wpi.mhtc.dashboard.pipeline.main;

import java.util.HashMap;

public class Line {
	
	static HashMap<String, Integer> stateMap;
	
	
//	TODO: a state class exists in the data package - should be using that??
	static{
		stateMap = new HashMap<String, Integer>();
		
		String[] states = {"Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut",
				"Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", 
				"Kentucky", "Louisiana", "Maine", "Maryland","Massachusetts", "Michigan", "Minnesota", "Mississippi", 
				"Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire","New Jersey", "New Mexico", 
				"New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", 
				"Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", 
				"Washington", "West Virginia", "Wisconsin", "Wyoming", "United States", "District of Columbia"};
		
		int i = 1;
		for (String state : states){
			stateMap.put(state, i);
			i++;
		}
	}
	
	private int year;
	private int stateID;
	private int metricID;
	private String metricName;
	private Float metricValue;

	
	public void setState(String stateName){
		stateID = stateMap.get(stateName);
	}
	
	public void setYear(String year){
		this.year = Integer.parseInt(year);
	}
	
	public void addMetric(String name, int id, Float value){
		metricID = id;
		metricName = name;
		metricValue = value;
	}

	public int getYear() {
		return year;
	}
	public int getStateID() {
		return stateID;
	}
	public String getMetricName() {
		return metricName;
	}
	public Float getMetricValue() {
		return metricValue;
	}
	public int getMetricID() {
		return metricID;
	}
	
	public boolean isValid(){
		return (year!=0 && stateID!=0 && metricID!=0);
	}
//	private Set<Metric> metrics;
//	
//	public Line(){
//		metrics = new HashSet<Metric>();
//	}
//	public void put(String name, String value){
//		metrics.add(new Metric(name, value));
//	}
//	
//	public Set<Metric> getMetrics(){
//		return metrics;
//	}

}

