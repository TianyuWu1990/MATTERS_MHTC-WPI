package edu.wpi.mhtc.dashboard.pipeline.main;

import java.util.Iterator;
import java.util.List;

import edu.wpi.mhtc.dashboard.pipeline.config.CatInfoConfig;
import edu.wpi.mhtc.dashboard.pipeline.config.FileInfoConfig;
import edu.wpi.mhtc.dashboard.pipeline.config.StateInfoConfig;
import edu.wpi.mhtc.dashboard.pipeline.db.DBSaver;
import edu.wpi.mhtc.dashboard.pipeline.parsers.IParser;
import edu.wpi.mhtc.dashboard.pipeline.parsers.ParserFactory;

public class Main {

	/**
	 * main Entry of the data pipeline
	 */
	
	public static void main(String[] args) throws Exception {

		init();

		List<FileInfo> fileInfoList = FileInfoConfig.getInstance().getFileInfos();
		int count = 0;
		for(FileInfo fileInfo : fileInfoList){	// for each fileInfo, go through the data pipeline
			IParser parser = ParserFactory.getInstance(fileInfo); // get the corresponding parser
			FileData fileData = parser.parseAll();	// parse all data
			System.out.println("File: " + fileInfo.getFileName());
			Iterator<LineData> iter = fileData.getLineDataList().iterator();
			while (iter.hasNext()) {
				LineData lineData = iter.next();
				DBData dbData = lineData.getDBData();
				if (dbData.isValid()) {
					System.out.println(dbData.getYear() + "\t"
							+ dbData.getState().getFullName() + "\t"
							+ dbData.getCatInfo().getCatName());
					count++;
					try{
						DBSaver.saveLineData(dbData);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}
		System.out.println("total: " + count);
	}
	
	/*
	 * initialize the data pipeline
	 */
	public static void init() throws Exception{
		FileInfoConfig.getInstance();
		CatInfoConfig.getInstance();
		StateInfoConfig.getInstance();
	}
	
}
