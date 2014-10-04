package edu.wpi.mhtc.dashboard.pipeline.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.wpi.mhtc.dashboard.pipeline.data.Metric;
import edu.wpi.mhtc.dashboard.pipeline.db.DBLoader;

/**
 * 
 * @author cakuhlman
 *
 */

public class MetricInfoConfig implements IConfig{
	
//	private List<State> stateList;
	private static MetricInfoConfig metricInfoConfig; 
	
	
	/* Don't let anyone else instantiate this class */
	private MetricInfoConfig() {}
	
	
	/**
     * 
     * @return  the Metric InfoConfig object.
     *    
     */
	
	public static  MetricInfoConfig getInstance() throws Exception{
		if(metricInfoConfig == null){
			metricInfoConfig = new MetricInfoConfig();
		}
		return metricInfoConfig;
	}
	
	@Override
	public void loadConfig() throws Exception {
		
	}
	
	/**
	 * @return List of metrics associated with this Category ID in the database.
	 */
	public List<Metric> getMetrics(int catID) throws Exception{
		
		List<Metric> metrics;
		
		try{
//			key is the metric name, value is the metric ID
			Map<String, String> metricMap = DBLoader.getMetricInfo(catID);
			metrics = new ArrayList<Metric>(metricMap.size());
			
			for(String name : metricMap.keySet()){
				metrics.add(new Metric(name, Integer.parseInt(metricMap.get(name))));
			}
			return metrics;
		}
		catch (Exception e){
			throw new Exception("Could not retrieve metrics for catID "+ catID + " from db", e);
		}
	}
	
}
