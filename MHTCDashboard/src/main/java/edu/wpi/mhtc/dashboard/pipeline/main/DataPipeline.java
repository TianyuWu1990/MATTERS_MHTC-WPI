package edu.wpi.mhtc.dashboard.pipeline.main;

import java.io.File;

import edu.wpi.mhtc.dashboard.pipeline.data.Category;
import edu.wpi.mhtc.dashboard.pipeline.data.UnifiedDataSource;
import edu.wpi.mhtc.dashboard.pipeline.db.TransactionManager;
import edu.wpi.mhtc.dashboard.pipeline.parser.UnifiedParser;
import edu.wpi.mhtc.dashboard.pipeline.wrappers.URLDownload;

public class DataPipeline {
	
	public static void download(String targeturl, String filename, String catId) throws Exception{
		URLDownload down = new URLDownload();
		
		// Download the file
		down.HTTPDownload(targeturl, filename);
		
		// Import the file into the database.
		run(new File(filename), catId);
	}

	public static void run(File file, String catID) throws Exception {
		
		Category category = new Category(Integer.parseInt(catID));

		UnifiedDataSource source = new UnifiedDataSource(file, category);
	
		UnifiedParser parser = new UnifiedParser(source);
		parser.parseAll();
		
		TransactionManager.insertData(parser.getLines());
	}
	
}
	
	
