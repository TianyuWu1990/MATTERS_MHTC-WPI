package edu.wpi.mhtc.pipeline.config;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import edu.wpi.mhtc.pipeline.data.CatInfo;
import edu.wpi.mhtc.pipeline.data.FileInfo;

public class CatInfoConfig implements IConfig{
	private static CatInfoConfig catInfoConfig;
	private Map<String, CatInfo> table; /* key is the file Name, value is the CatInfo */
	private List<CatInfo> catInfoList;
	
	private CatInfoConfig() throws Exception{
		this.loadConfig();
	}

	public static CatInfoConfig getInstance() throws Exception{
		if(catInfoConfig == null){
			catInfoConfig = new CatInfoConfig();
		}
		return catInfoConfig;
	}

	@Override
	public void loadConfig() throws Exception{
		this.table = new HashMap<String, CatInfo>();
		List<CatInfo> catInfolist = getCatInfoFromXML();
		for(CatInfo catInfo : catInfolist){
			table.put(catInfo.getFileName(), catInfo);
		}
	}
	
	/*
	 * get CatInfo according file name
	 */
	public CatInfo getCatInfo(FileInfo fileInfo){
		return this.table.get(fileInfo.getFileName());
	}
	
	/*
	 * get config from xml
	 */
	private List<CatInfo> getCatInfoFromXML() throws Exception{
		LinkedList<CatInfo> list = new LinkedList<CatInfo>();
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(IConfigFilePath.CatInfoConfigPath));
		List<?> nodes = document.selectNodes("/config/catInfo");
		for(Object o : nodes){
			Element elem = (Element)o;
			String catID = elem.elementText("catID");
			String catName = elem.elementText("catName");
			String fileName = elem.elementText("fileName");
			HashMap<String, String> metricTable = new HashMap<String, String>();
			List<?> metricNodes = elem.selectNodes("./metric");
			for(Object oo : metricNodes){
				Element metricElem = (Element)oo;
				String metricName = metricElem.elementText("metricName");
				String columnName = metricElem.elementText("columnName");
				metricTable.put(metricName, columnName);
			}
			CatInfo catInfo = new CatInfo(catID, catName, fileName, metricTable);
			list.add(catInfo);
		}
		this.catInfoList = list;
		return list;
	}

	
	/*
	 * getters and setters
	 */
	public List<CatInfo> getCatInfoList() {
		return catInfoList;
	}


}
