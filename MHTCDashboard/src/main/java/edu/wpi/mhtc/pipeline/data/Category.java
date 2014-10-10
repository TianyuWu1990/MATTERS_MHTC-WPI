package edu.wpi.mhtc.pipeline.data;

import java.sql.SQLException;
import java.util.List;

import edu.wpi.mhtc.pipeline.config.MetricInfoConfig;
import edu.wpi.mhtc.pipeline.db.DBLoader;

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
		metrics = MetricInfoConfig.getInstance().getMetrics(id);
		
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
	
}


