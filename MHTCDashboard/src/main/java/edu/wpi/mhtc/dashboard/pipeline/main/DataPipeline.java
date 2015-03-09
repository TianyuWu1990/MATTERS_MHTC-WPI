/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dashboard.pipeline.main;

import java.io.File;
import java.util.List;

import edu.wpi.mhtc.dashboard.pipeline.dao.Metric;
import edu.wpi.mhtc.dashboard.pipeline.dao.Statistic;
import edu.wpi.mhtc.dashboard.pipeline.data.Category;
import edu.wpi.mhtc.dashboard.pipeline.data.UnifiedDataSource;
import edu.wpi.mhtc.dashboard.pipeline.db.TransactionManager;
import edu.wpi.mhtc.dashboard.pipeline.parser.UnifiedParser;
import edu.wpi.mhtc.model.state.State;

public class DataPipeline {
	
	public static void run(File file, Category category, List<Metric> metrics, List<State> states, boolean overwrite) throws Exception {

		UnifiedDataSource source = new UnifiedDataSource(file, category);
	
		UnifiedParser parser = new UnifiedParser(source, metrics, states);
		parser.parseAll();
		
		for(Statistic l : parser.getLines()){
			System.out.println(l.getStateID()+" "+ l.getValue()+ " " + l.getYear());
		}
		
		TransactionManager.insertData(parser.getLines(), overwrite);
	}
	
}
	
	
