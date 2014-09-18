package edu.wpi.mhtc.dashboard.pipeline.main;

import java.util.List;

public class Category {
	
	private String name;
	private int id;
	private List<Metric> metrics;
	
	public Category(String categoryName){
		
		name = categoryName;
//		TODO where should the method be to retrieve the category id from the database?
//		id = getCategoryID(name); or a category is not created this way, it is done by calling a method getCategory(name) from some other method
		
	}
	
	private int lookupID(String categoryName){
		int id;
		return id;
	}
	
	private int insertNewCategory(){
		
		
	}
	public int getId(){
		return id;
	}

}
