package edu.wpi.mhtc.dashboard.pipeline.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class ParserInfoConfig implements IConfig{

	private static ParserInfoConfig parserInfoConfig;
	private Map<String, String> map; /* key is the file name, value is the parser name */
	
	
	private ParserInfoConfig() throws Exception{
		this.loadConfig();
	}
	
	public static ParserInfoConfig getInstance() throws Exception{
		if(parserInfoConfig == null){
			parserInfoConfig = new ParserInfoConfig();
		}
		return parserInfoConfig;
	}
	
	public String getParserNameFromFileName(String fileName){
		return map.get(fileName);
	}
	
	@Override
	public void loadConfig() throws Exception {
		Properties pro = new Properties();
		pro.load(new FileInputStream(new File(IConfigFilePath.ParserInfoConfigPath)));
		for(Entry<Object, Object> entry : pro.entrySet()){
			String parserName = (String)entry.getKey();
			String fileNames = (String)entry.getValue();
			String[] fns = fileNames.split(",");
			for(String fn : fns){
				this.map.put(fn, parserName);
			}
		}
	}

}
