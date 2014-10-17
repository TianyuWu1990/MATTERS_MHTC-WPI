package edu.wpi.mhtc.dashboard.pipeline.config;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import edu.wpi.mhtc.dashboard.pipeline.data.FileType;
import edu.wpi.mhtc.dashboard.pipeline.data.LoadInfo;
import edu.wpi.mhtc.dashboard.pipeline.parsers.ParserType;
import edu.wpi.mhtc.pipeline.data.FileInfo;

public class FileInfoConfig implements IConfig{
	
	private List<FileInfo> fileInfoList;
	private static FileInfoConfig fileInfoConfig;
	
	private FileInfoConfig() throws Exception{
		this.loadConfig();
	}
	
	public static FileInfoConfig getInstance() throws Exception{
		if(fileInfoConfig == null){
			fileInfoConfig = new FileInfoConfig();
		}
		return fileInfoConfig;
	}
	
	/*
	 * get the fileInfo list from xml config file
	 */
	public List<FileInfo> getFileInfos() throws Exception{
		return this.fileInfoList;
	}

	@Override
	public void loadConfig() throws Exception {
		fileInfoList = new LinkedList<FileInfo>();
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(IConfigFilePath.FileInfoConfigPath));
		List<?> nodes = document.selectNodes("/config/fileInfo");
		for(Object o : nodes){
			Element elem = (Element)o;
			String fileName = elem.elementText("absoluteName");
			String fileType = elem.elementText("fileType");
			String parserType = elem.elementText("parserType");
			boolean isRowSpecified = Boolean.parseBoolean(elem.elementText("isRowSpecified")); 
			int startRow = -1;
			int endRow = -1;
			if(isRowSpecified){
				startRow = Integer.parseInt(elem.elementText("startRow"));
				endRow = Integer.parseInt(elem.elementText("endRow"));
			}
			ParserType p;
			FileInfo info = new FileInfo(fileName, FileType.valueOf(fileType), ParserType.valueOf(parserType), new LoadInfo(startRow, endRow, isRowSpecified));
			fileInfoList.add(info);
		}
	}
}
