/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dashboard.pipeline.data;

import java.io.File;

import edu.wpi.mhtc.dashboard.pipeline.parser.ParserType;

/**
 * CSV file with data organized in the unified format.
 *
 */
public class UnifiedCSVDataSource extends DataSource{
	
	public UnifiedCSVDataSource(File file, Category category){
		super(file, category);
		this.fileType = FileType.csv;
		this.parserType = ParserType.text;
	}
	
}

