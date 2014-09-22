package edu.wpi.mhtc.dashboard.pipeline.main;

import java.io.File;
import java.util.Iterator;

import edu.wpi.mhtc.dashboard.pipeline.data.DBData;
import edu.wpi.mhtc.dashboard.pipeline.data.LineData;
import edu.wpi.mhtc.dashboard.pipeline.db.DBSaver;
import edu.wpi.mhtc.dashboard.pipeline.parser.ParserFactory;
import edu.wpi.mhtc.dashboard.pipeline.parser.UnifiedParser;

public class DataPipeline {

	public static void run(File file, String catID) throws Exception{
		
		UnifiedDatasource source = new UnifiedDatasource(file, catID);
	
//		this is a hack right now, ideally parser would be defined as IParser
//		and the parseAll method wouldn't have to be called with an int
		UnifiedParser parser = (UnifiedParser) ParserFactory.getInstance(source);
		parser.parseAll(1);
		
		Iterator<Line> iter = parser.getLineIterator();
		while (iter.hasNext()) {
			Line line = iter.next();
			try{
				
				
//			TODO: enter data in db using line objects
//				DBSaver.saveLine(line);
				
			}catch(Exception e){
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
}
	
	
