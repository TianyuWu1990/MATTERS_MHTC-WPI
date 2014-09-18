package edu.wpi.mhtc.dashboard.pipeline.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import edu.wpi.mhtc.dashboard.pipeline.config.CatInfoConfig;
import edu.wpi.mhtc.dashboard.pipeline.config.StateInfoConfig;
import edu.wpi.mhtc.dashboard.pipeline.db.DBLoader;
import edu.wpi.mhtc.dashboard.pipeline.fileInfo.CatInfo;

/*
 * this is the class that maps to the real data in the database 
 */
public class DBData {
	private String year;
	private State state;
	private Map<String, String> map; /* key is the metric ID, value is the actual value */
	private LineData lineData;

	public DBData(LineData lineData) throws Exception{
		this.lineData = lineData;
		this.map = new HashMap<String, String>();
		this.doMapping();
	}
	
	public CatInfo getCatInfo() throws Exception{
		return CatInfoConfig.getInstance().getCatInfo(lineData.getFileInfo());
	}
	
	// replace the data name to the metric ID, map the value to the metric ID
	private void doMapping() throws Exception{
		doMetricMapping();
		doStateMapping();
		doYearMapping();
	}
	
	/*
	 *	get the metric ID -> metric value pairs 
	 */
	private void doMetricMapping() throws Exception{
	    CatInfo catInfo;
	    if (lineData.getFileInfo().isUnified()) {
	    	
	        Map<String, String> metricMap = new HashMap<String, String>();
	        for (String metric : lineData.getMap().keySet()) {
	            
	        	
	        	
//	       CK- What is this for? mapping metrics to themselves? 
//	       It is passed to catInfo, but nothing in call hierarchy accesses this element
	        	metricMap.put(metric, metric);
	        }
	        
	        catInfo = new CatInfo(lineData.getFileInfo().getCatId(), "", lineData.getFileInfo().getFileName(), metricMap);
	    } 
	    else {
	        catInfo = CatInfoConfig.getInstance().getCatInfo(lineData.getFileInfo());
	    }
	    
		Map<String, String> categoryMetrics = DBLoader.getMetricInfo(catInfo.getCatID()); //metrics associated with this category
		
		if(categoryMetrics.isEmpty()){  
			System.out.println("there are no metrics associated with this category");
			return;
		}
		for(Entry<String, String> metric : categoryMetrics.entrySet()){
			String metricName = metric.getKey();
			String metricID = metric.getValue();
			
			String colName = catInfo.getColumnNameByMetricName(metricName);

			if (colName == null) {

				System.out.println("The column name " + metricName
						+ " was not found in in this line.");
				break;
			}
			
			String metricVal = lineData.getValueFromColumnName(colName);
			
			
			
			this.map.put(metricID, metricVal);
		}
	}
	
	/*
	 * validate if the data is valid or not
	 * 
	 */
	public boolean isValid(){
		return this.state != null;
	}
	
	
	/*
	 *	get state name and state ID 
	 */
	private void doStateMapping() throws Exception{
		String fullName = this.lineData.getValueFromColumnName("state").toLowerCase();
		this.state = StateInfoConfig.getInstance().getStateByFullName(fullName);
	}
	
	/*
	 * get Year
	 */
	private void doYearMapping(){
		int year = (int)Float.parseFloat(this.lineData.getValueFromColumnName("year"));
		this.year = Integer.toString(year);
	}
	
	/*
	 * getters and setters
	 */
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}

	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}
	public LineData getLineData() {
		return lineData;
	}
	public void setLineData(LineData lineData) {
		this.lineData = lineData;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
}
