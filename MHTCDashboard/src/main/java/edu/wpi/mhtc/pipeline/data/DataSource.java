package edu.wpi.mhtc.pipeline.data;

import java.io.File;

import edu.wpi.mhtc.pipeline.parser.ParserType;

public abstract class DataSource {
	
	FileType fileType;
	ParserType parserType;
	File file;
	Category category;	
	
	public DataSource(File file, Category category){
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
