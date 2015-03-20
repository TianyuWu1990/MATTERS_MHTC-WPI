/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.util.pipeline.parser;

import java.io.File;

import edu.wpi.mhtc.model.admin.Category;

/**
 * CSV file with data organized in the unified format.
 *
 */
public class UnifiedCSVDataSource extends DataSource{
	
	FileType fileType;
	ParserType parserType;
	
	public UnifiedCSVDataSource(File file, Category category){
		super(file, category);
		this.fileType = FileType.csv;
		this.parserType = ParserType.text;
	}
	
}

