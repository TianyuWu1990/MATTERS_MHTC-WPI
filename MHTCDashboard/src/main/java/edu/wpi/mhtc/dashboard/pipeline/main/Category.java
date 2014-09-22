package edu.wpi.mhtc.dashboard.pipeline.main;

import java.util.Map;

import edu.wpi.mhtc.dashboard.pipeline.db.DBLoader;

public class Category {
	
	private String name;
	private int id;
	private Map<String, String> metrics;
	
	
//	if category is not found in db, throws an exception
//	(I think. should chase this down and see exactly what cause of the exceptions thrown by db methods are) 
	public Category(String name) throws Exception{
		
		this.name = name;
		id = DBLoader.getCategoryId(name);
		metrics = DBLoader.getMetricInfo(id); 
		
	}
	
	public int getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}

	public Map<String, String> getMetrics(){
		return metrics;
	}
	
	
//	redundant??
	public boolean insertNewCategory(String name, String parentID, String source) throws Exception{
//		check for category before calling!!	
//		If this method passed a category name that is already present in the db
//		it will insert a duplicate with a different id

		return DBLoader.insertNewCategory(name, parentID, source);
	}
	
}
