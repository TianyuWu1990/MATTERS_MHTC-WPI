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
	private LoadInfo loadInfo;	  //not sure what this is for?
	private String categoryName;	//set by user in admin panel, passes as arg to constructor

	
	
//	This will throw an exception if one is thrown when creating a category object
//	Category object retrieves catId and associated metrics from the db
	
//	TODO: check metrics defined in file against those associated with category in db
//	TODO: handle creating new category in the db
	public UnifiedDatasource(File file, String categoryName) throws Exception{
		this.file = file;
		this.categoryName = categoryName;
	}
	
	public String getCategoryName(){
		return categoryName;
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

	public LoadInfo getLoadInfo() {
		return loadInfo;
	}
	public void setLoadInfo(LoadInfo loadInfo) {
		this.loadInfo = loadInfo;
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
	
	
//	not sure if this is needed?
//	public void setCategory(Category category){
//		this.category = category;
//	}
	
	
	

}
