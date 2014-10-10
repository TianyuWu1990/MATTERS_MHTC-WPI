package edu.wpi.mhtc.pipeline.main;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import edu.wpi.mhtc.pipeline.config.CatInfoConfig;
import edu.wpi.mhtc.pipeline.config.FileInfoConfig;
import edu.wpi.mhtc.pipeline.config.StateInfoConfig;
import edu.wpi.mhtc.pipeline.data.DBData;
import edu.wpi.mhtc.pipeline.data.FileData;
import edu.wpi.mhtc.pipeline.data.FileInfo;
import edu.wpi.mhtc.pipeline.data.LineData;
import edu.wpi.mhtc.pipeline.db.DBSaver;
import edu.wpi.mhtc.pipeline.parser.IParser;
import edu.wpi.mhtc.pipeline.parser.ParserFactory;

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
