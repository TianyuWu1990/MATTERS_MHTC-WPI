package edu.wpi.mhtc.pipeline.data;

public class Metric {
	
	private int id;
	private String name;
	private Float value;
	
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
	public void setValue(Float value){
		this.value = value;
	}

	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Float getValue() {
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
