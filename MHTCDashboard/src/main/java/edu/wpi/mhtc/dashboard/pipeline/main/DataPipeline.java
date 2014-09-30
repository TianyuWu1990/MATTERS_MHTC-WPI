package edu.wpi.mhtc.dashboard.pipeline.main;

import java.io.File;
import java.util.Iterator;

import edu.wpi.mhtc.dashboard.pipeline.data.DBData;
import edu.wpi.mhtc.dashboard.pipeline.data.FileData;
import edu.wpi.mhtc.dashboard.pipeline.data.LineData;
import edu.wpi.mhtc.dashboard.pipeline.db.DBSaver;
import edu.wpi.mhtc.dashboard.pipeline.fileInfo.FileInfo;
import edu.wpi.mhtc.dashboard.pipeline.parser.IParser;
import edu.wpi.mhtc.dashboard.pipeline.parser.ParserFactory;
import edu.wpi.mhtc.dashboard.pipeline.wrappers.URLDownload;

public class DataPipeline {
	
	public static void download(String targeturl, String filename, String catId) throws Exception{
		URLDownload down = new URLDownload();
		
		// Download the file
		down.HTTPDownload(targeturl, filename);
		
		// Import the file into the database.
		DataPipeline.run(new File(filename), catId);
	}

	public static void run(File file, String catID) throws Exception{
		FileInfo fileInfo = new FileInfo(file);
		fileInfo.setCatId(catID);
		IParser parser = ParserFactory.getInstance(fileInfo);
		FileData fileData = parser.parseAll();
		
		Iterator<LineData> iter = fileData.getLineDataList().iterator();
		while (iter.hasNext()) {
			LineData lineData = iter.next();
			DBData dbData = lineData.getDBData();
			if (dbData.isValid()) {
				/*
				 * logs
				 */
				/*System.out.println(dbData.getYear() + "\t"
						+ dbData.getState().getFullName() + "\t"
						+ dbData.getCatInfo().getCatName());*/
				try{
					DBSaver.saveLineData(dbData);
				}catch(Exception e){
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}
	}
}
	
	
