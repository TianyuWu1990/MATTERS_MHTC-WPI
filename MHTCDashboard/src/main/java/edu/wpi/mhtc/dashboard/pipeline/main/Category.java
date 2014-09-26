package edu.wpi.mhtc.dashboard.pipeline.main;

import java.util.Map;
import java.util.Map.Entry;

import edu.wpi.mhtc.dashboard.pipeline.db.DBLoader;

public class Category {
	
	private int id;
	
//	key is the metric name, value is the metric ID
	private Map<String, String> metrics;
	
	
//	if category is not found in db, throws an exception
//	(I think. should chase this down and see exactly what cause of the exceptions thrown by db methods are) 
	public Category(int id) throws Exception{
		
		this.id = id;
		metrics = DBLoader.getMetricInfo(id); 
		
		System.out.println("\n\nMETRICS");
		for(Entry<String, String> e: metrics.entrySet()){
			System.out.println(e.getKey());
			System.out.println(e.getValue());
		}
	}
	
	public int getId(){
		return id;
	}
	
	
//	redundant??
	public boolean insertNewCategory(String name, String parentID, String source) throws Exception{
//		check for category before calling!!	
//		If this method passed a category name that is already present in the db
//		it will insert a duplicate with a different id

		return DBLoader.insertNewCategory(name, parentID, source);
	}

	
	public int getMetricID(String name) {
		String metricID = metrics.get(name);
		System.out.println("\n\nMETRIC ID IS: " + metricID);
		return Integer.parseInt(metricID);
	}
	
}
