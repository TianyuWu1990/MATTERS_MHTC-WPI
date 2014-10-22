package edu.wpi.mhtc.dashboard.pipeline.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.wpi.mhtc.dashboard.pipeline.db.DBLoader;

public class Category {
	
	private int id;
	private List<Metric> metrics;
/**
 * 
 * @param id
 * @throws SQLException 
 * @throws Exception if metrics cannot be found for Category in database.
 */
	public Category(int id) throws CategoryException, SQLException{
		
		this.id = id;
		metrics = getMetrics(id);
		
	}
	
	
	public int getId(){
		return id;
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
		throw new CategoryException("No metric in this category matches "+name);
	}
	
	
	/**
	 * A valid metric will be associated with this category in the database.
	 * @param Metric name
	 * @throws CategoryException if this is not one of the category's metrics.
	 */
	public void validateMetric(String name) throws CategoryException{
		for(Metric metric: metrics){
			if(name.equals(metric.getName())) return;
		}
		throw new CategoryException("No metric in this category matches name "+name);	
	}
	
	/**
	 * 
	 * @param catID
	 * @return List of metrics associated with this Category ID in the database.
	 * @throws CategoryException if metrics cannot be retrieved from database
	 * @throws SQLException
	 */
	public List<Metric> getMetrics(int catID) throws SQLException, CategoryException{
		
		List<Metric> metrics;
		
		try{
//			key is the metric name, value is the metric ID
			Map<String, String> metricMap = DBLoader.getMetricInfo(catID);
			metrics = new ArrayList<Metric>(metricMap.size());
			
			for(String name : metricMap.keySet()){
				metrics.add(new Metric(name, Integer.parseInt(metricMap.get(name))));
			}
			
			if(metrics.isEmpty()){
				throw new CategoryException("No metrics found for Category " + catID);
			}
			return metrics;
		}
		catch (Exception e){
			throw new CategoryException("Could not retrieve metrics for catID "+ catID + " from db", e);
		}
	}
	
}


