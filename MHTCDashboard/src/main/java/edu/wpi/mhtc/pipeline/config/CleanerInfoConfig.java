package edu.wpi.mhtc.pipeline.config;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import edu.wpi.mhtc.pipeline.cleaner.CleanType;
import edu.wpi.mhtc.pipeline.cleaner.CleanerFactory;
import edu.wpi.mhtc.pipeline.cleaner.ICleaner;
import edu.wpi.mhtc.pipeline.data.FileInfo;

public class CleanerInfoConfig implements IConfig{

	private static CleanerInfoConfig cleanerInfoConfig;
	/* key is the file name, value is a Map (key is the column name, value is the Cleaner) */
	private Map<String, Map<String, ICleaner>> map; 
	
	private CleanerInfoConfig() throws Exception{
		this.loadConfig();
	}
	
	public static CleanerInfoConfig getInstance() throws Exception{
		if(cleanerInfoConfig == null){
			cleanerInfoConfig = new CleanerInfoConfig();
		}
		return cleanerInfoConfig;
	}
	
	
	public ICleaner getCleanInfo(FileInfo fileInfo, String columnName){
		return map.get(fileInfo.getFileName()).get(columnName);
	}
	
	@Override
	public void loadConfig() throws Exception {
		this.map = new HashMap<String, Map<String, ICleaner>>();
		loadCleanInfoFromXML();
	}

	/*
	 * get config from xml
	 */
	private void loadCleanInfoFromXML() throws Exception{
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(IConfigFilePath.CleanerInfoConfigPath));
		List<?> nodes = document.selectNodes("/config/catInfo");
		for(Object o : nodes){
			Element elem = (Element)o;
			String fileName = elem.elementText("fileName");
			Map<String, ICleaner> metricMap = new HashMap<String, ICleaner>();
			List<?> metricNodes = elem.selectNodes("./metric");
			for(Object oo : metricNodes){
				Element metricElem = (Element)oo;
				String columnName = metricElem.elementText("columnName");
				String type = metricElem.elementText("type");
				ICleaner cleaner = CleanerFactory.getInstance(CleanType.valueOf(type));
				metricMap.put(columnName, cleaner);
			}
			this.map.put(fileName, metricMap);
		}
	}
	
}
