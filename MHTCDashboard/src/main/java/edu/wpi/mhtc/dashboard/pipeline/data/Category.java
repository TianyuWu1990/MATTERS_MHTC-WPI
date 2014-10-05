package edu.wpi.mhtc.dashboard.pipeline.data;

import java.util.List;

import edu.wpi.mhtc.dashboard.pipeline.config.MetricInfoConfig;
import edu.wpi.mhtc.dashboard.pipeline.db.DBLoader;

public class Category {
	
	private int id;
	private List<Metric> metrics;
/**
 * 
 * @param id
 * @throws Exception if metrics cannot be found for Category in database.
 */
	public Category(int id) throws CategoryException{
		
		this.id = id;
		try{
			metrics = MetricInfoConfig.getInstance().getMetrics(id);
		}
		catch(Exception e){
			throw new CategoryException("Cannot retrieve metrics for Category "+id+ " from database", e);
		}
	}
	
	public int getId(){
		return id;
	}
	
	
//	TODO: maybe use CatInfoConfig??
	public boolean insertNewCategory(String name, String parentID, String source) throws Exception{
//		check for category before calling!!	
//		If this method passed a category name that is already present in the db
//		it will insert a duplicate with a different id

		return DBLoader.insertNewCategory(name, parentID, source);
	}

	/**
	 * 
	 * @param name
	 * @return Metric
	 * @throws Exception if the name does not match any metrics associated with this Category in the database
	 */
	public Metric getMetric(String name) throws CategoryException {
		
		for(Metric metric: metrics){
			if(name.equals(metric.getName())) return metric;
		}
		throw new CategoryException("No metric in this category matches name "+name);
	}
	
	public boolean containsMetric(String name){
		for(Metric metric: metrics){
			if(name.equals(metric.getName())) return true;
		}
		return false;
	}
	
	public void validateMetric(String name) throws CategoryException{
		for(Metric metric: metrics){
			if(name.equals(metric.getName())) return;
		}
		throw new CategoryException("No metric in this category matches name "+name);	
	}
	
}


