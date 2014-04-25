package edu.wpi.mhtc.dashboard.pipeline.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import edu.wpi.mhtc.dashboard.pipeline.cleaner.ICleaner;
import edu.wpi.mhtc.dashboard.pipeline.cleaner.NumericCleaner;
import edu.wpi.mhtc.dashboard.pipeline.cleaner.StateCleaner;
import edu.wpi.mhtc.dashboard.pipeline.cleaner.YearCleaner;
import edu.wpi.mhtc.dashboard.pipeline.config.CleanerInfoConfig;
import edu.wpi.mhtc.dashboard.pipeline.fileInfo.FileInfo;

/*
 * this class contains a row in a file 
 */
public class LineData {

	private Map<String, String> map; /* key is the column Name, value is the actual value */
	private FileInfo fileInfo;
	
	public LineData(Map<String, String> map, FileInfo fileInfo){
		this.map = map;
		this.fileInfo = fileInfo;
	}

	public DBData getDBData() throws Exception{
		this.clean();
		DBData dbData = new DBData(this);
		return dbData;
	}
	
	public void clean() throws Exception{
		Map<String, String> newMap = new HashMap<String, String>();
		for(Entry<String, String> entry : map.entrySet()){
			String metricName = entry.getKey();
			String metricVal = entry.getValue();
			ICleaner cleaner = getCleaner(metricName);
			String updatedVal = cleaner.clean(metricVal);
			newMap.put(metricName, updatedVal);
		}
		this.map = newMap;
	}
	
	/*
	 * get the correct cleaner according to the metricName and the fileInfo
	 */
	private ICleaner getCleaner(String metricName) throws Exception{
		if(this.fileInfo.isUnified()){
			if("state".equalsIgnoreCase(metricName)){
				return new StateCleaner();
			}else if("year".equalsIgnoreCase(metricName)){
				return new YearCleaner();
			}else{
				return new NumericCleaner();
			}
		}else{
			return CleanerInfoConfig.getInstance().getCleanInfo(this.fileInfo, metricName);
		}
	}
	
	
	/*
	 * getters and setters
	 */
	public String getValueFromColumnName(String colName){
		return this.map.get(colName);
	}
	
	public Map<String, String> getMap() {
		return map;
	}

	public void setTable(HashMap<String, String> map) {
		this.map = map;
	}

	public FileInfo getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}
}
