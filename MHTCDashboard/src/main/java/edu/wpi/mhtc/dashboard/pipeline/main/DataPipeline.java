package edu.wpi.mhtc.dashboard.pipeline.main;

import java.io.File;
import java.sql.SQLException;
import java.util.Iterator;

import edu.wpi.mhtc.dashboard.pipeline.data.Category;
import edu.wpi.mhtc.dashboard.pipeline.data.Line;
import edu.wpi.mhtc.dashboard.pipeline.db.DBSaver;
import edu.wpi.mhtc.dashboard.pipeline.parser.ParserFactory;
import edu.wpi.mhtc.dashboard.pipeline.parser.UnifiedParser;
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
		
		Category category = new Category(Integer.parseInt(catID));

		UnifiedDatasource source = new UnifiedDatasource(file, category);
	
//		this is a hack right now, ideally parser would be defined as IParser
//		and the parseAll method wouldn't have to be called with an int
		UnifiedParser parser = (UnifiedParser) ParserFactory.getInstance(source);
		parser.parseAll(1);
		
		Iterator<Line> iter = parser.getLineIterator();
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
	
	
