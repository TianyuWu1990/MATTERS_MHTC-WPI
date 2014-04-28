package edu.wpi.mhtc.dashboard.pipeline.fileInfo;

import java.util.HashMap;
import java.util.Map;

public class CatInfo {
	String catID;
	String catName;
	String fileName;
	Map<String, String> metricMap; /* key is the metric name in db, value is the column name */
	
	public CatInfo(String catID, String catName, String fileName,
			Map<String, String> metricTable) {
		super();
		this.catID = catID;
		this.catName = catName;
		this.fileName = fileName;
		this.metricMap = metricTable;
	}

	
	/*
	 * getters and setters
	 */
	public String getColumnNameByMetricName(String metricName){
		return metricMap.get(metricName);
	}
	
	public String getCatID() {
		return catID;
	}

	public void setCatID(String catID) {
		this.catID = catID;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Map<String, String> getMetricTable() {
		return metricMap;
	}

	public void setMetricMap(HashMap<String, String> metricTable) {
		this.metricMap = metricTable;
	}


}
