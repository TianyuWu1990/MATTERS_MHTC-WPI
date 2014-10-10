package edu.wpi.mhtc.pipeline.main;

import java.io.File;
import java.sql.SQLException;
import java.util.Iterator;

import edu.wpi.mhtc.pipeline.data.Category;
import edu.wpi.mhtc.pipeline.data.Line;
import edu.wpi.mhtc.pipeline.data.UnifiedDataSource;
import edu.wpi.mhtc.pipeline.db.DBSaver;
import edu.wpi.mhtc.pipeline.parser.ParserFactory;
import edu.wpi.mhtc.pipeline.parser.UnifiedParser;
import edu.wpi.mhtc.pipeline.wrappers.URLDownload;

public class DataPipeline {
	
	public static void download(String targeturl, String filename, String catId) throws Exception{
		URLDownload down = new URLDownload();
		
		// Download the file
		down.HTTPDownload(targeturl, filename);
		
		// Import the file into the database.
		run(new File(filename), catId);
	}

	public static void run(File file, String catID) throws Exception{
		
		Category category = new Category(Integer.parseInt(catID));

		UnifiedDataSource source = new UnifiedDataSource(file, category);
	
		UnifiedParser parser = (UnifiedParser) ParserFactory.getInstance(source);
		parser.parseAll();
		
		Iterator<Line> iter = parser.iterator();
		while (iter.hasNext()) {
			Line line = iter.next();
			try{
				DBSaver.saveLine(line);
			}catch(SQLException e){
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
}
	
	
