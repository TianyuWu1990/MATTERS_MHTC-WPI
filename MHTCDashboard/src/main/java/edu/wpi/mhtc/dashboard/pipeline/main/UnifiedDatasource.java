package edu.wpi.mhtc.dashboard.pipeline.main;

import java.io.File;
import java.util.List;

import edu.wpi.mhtc.dashboard.pipeline.fileInfo.FileType;
import edu.wpi.mhtc.dashboard.pipeline.fileInfo.LoadInfo;
import edu.wpi.mhtc.dashboard.pipeline.parser.ParserType;

public class UnifiedDatasource {
	
	private File file;
	private FileType fileType = FileType.xlsx;
	private ParserType parserType = ParserType.excel;
	private int categoryID;	//set by user in admin panel, passes as arg to constructor

	
	
//	This will throw an exception if one is thrown when creating a category object
//	Category object retrieves catId and associated metrics from the db
	
//	TODO: check metrics defined in file against those associated with category in db
//	TODO: handle creating new category in the db
	public UnifiedDatasource(File file, String categoryID) throws Exception{
		this.file = file;
		this.categoryID = Integer.parseInt(categoryID);
	}
	
	public int getCategoryID(){
		return categoryID;
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

	public boolean isUnified() {
		return true;
	}
}
