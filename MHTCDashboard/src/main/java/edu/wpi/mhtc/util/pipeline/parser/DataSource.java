/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.util.pipeline.parser;

import java.io.File;

import edu.wpi.mhtc.model.admin.Category;

/**
 * Wraps an input file and all associated metadata.
 *
 */
public abstract class DataSource {
	
	FileType fileType;
	ParserType parserType;
	File file;
	Category category;	
	
	protected DataSource(File file, Category category){
		this.file = file;
		this.category = category;
	}
	
	/*
	 * setters and getters
	 */
	public String getFileName() {
		return file.getName();
	}
	
	public FileType getFileType() {
		return fileType;
	}

	public ParserType getParserType() {
		return parserType;
	}

	public File getFile() {
		return file;
	}

	public Category getCategory(){
		return category;
	}
}
