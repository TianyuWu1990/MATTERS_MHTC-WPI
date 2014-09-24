package edu.wpi.mhtc.dashboard.pipeline.main;

import java.util.ArrayList;
import java.util.List;

public class Line {
	
	private List<Metric> metrics;
	
	public Line(){
		metrics = new ArrayList<Metric>();
	}
	public void put(String name, String value){
		metrics.add(new Metric(name, value));
	}
	
	public List<Metric> getMetrics(){
		return metrics;
	}

}


class Metric {
	
	private String name;
	private String value;
	
	public Metric(String name, String value){
		this.name= name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}
	
	

}

