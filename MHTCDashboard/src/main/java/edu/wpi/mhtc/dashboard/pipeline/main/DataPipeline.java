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
import edu.wpi.mhtc.dashboard.pipeline.parser.UnifiedDataSource;
import edu.wpi.mhtc.dashboard.pipeline.parser.UnifiedParser;
import edu.wpi.mhtc.model.state.State;

public class DataPipeline {
	
	public static List<Statistic> run(File file, Category category, List<Metric> metrics, List<State> states) throws Exception {

		UnifiedDataSource source = new UnifiedDataSource(file, category);
	
		UnifiedParser parser = new UnifiedParser(source, metrics, states);
		parser.parseAll();
		
		List<Statistic> lines = parser.getLines();
		
		for(Statistic l : lines){
			System.out.println(l.getStateID()+" "+ l.getValue()+ " " + l.getYear());
		}
		
		return lines;
	}
	
}
	
	
