/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dashboard.pipeline.main;

import java.io.File;

import edu.wpi.mhtc.dashboard.pipeline.data.Category;
import edu.wpi.mhtc.dashboard.pipeline.data.Line;
import edu.wpi.mhtc.dashboard.pipeline.data.UnifiedDataSource;
import edu.wpi.mhtc.dashboard.pipeline.db.TransactionManager;
import edu.wpi.mhtc.dashboard.pipeline.parser.UnifiedParser;
import edu.wpi.mhtc.dashboard.pipeline.wrappers.URLDownload;

public class DataPipeline {
	
	public static void run(File file, String catID, boolean overwrite) throws Exception {
		
		Category category = new Category(Integer.parseInt(catID));

		UnifiedDataSource source = new UnifiedDataSource(file, category);
	
		UnifiedParser parser = new UnifiedParser(source);
		parser.parseAll();
		
		for(Line l : parser.getLines()){
			System.out.println(l.getStateID()+" "+ l.getMetricValue()+ " " + l.getYear());
		}
		
		TransactionManager.insertData(parser.getLines(), overwrite);
	}
	
}
	
	
