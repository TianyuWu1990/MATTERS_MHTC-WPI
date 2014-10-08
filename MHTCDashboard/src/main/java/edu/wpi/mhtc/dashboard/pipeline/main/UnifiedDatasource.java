package edu.wpi.mhtc.dashboard.pipeline.main;

import java.io.File;

import edu.wpi.mhtc.dashboard.pipeline.data.Category;
import edu.wpi.mhtc.dashboard.pipeline.fileInfo.FileType;
import edu.wpi.mhtc.dashboard.pipeline.parser.ParserType;

public class UnifiedDatasource {
	
	private final FileType fileType = FileType.xlsx;
	private final ParserType parserType = ParserType.excel;
	private File file;
	private Category category;	//set by user in admin panel, passes as arg to constructor

	
//	TODO: handle creating new category in the db
	
	/**
	 * 
	 * @param file
	 * @param category
	 */
	public UnifiedDatasource(File file, Category category){
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

