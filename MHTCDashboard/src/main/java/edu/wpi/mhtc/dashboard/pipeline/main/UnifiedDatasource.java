package edu.wpi.mhtc.dashboard.pipeline.main;

import java.io.File;
import java.util.List;

import edu.wpi.mhtc.dashboard.pipeline.fileInfo.FileType;
import edu.wpi.mhtc.dashboard.pipeline.fileInfo.LoadInfo;
import edu.wpi.mhtc.dashboard.pipeline.parser.ParserType;

public class UnifiedDatasource {
	
	private File file;
	private FileType fileType;
	private ParserType parserType;
	private LoadInfo loadInfo;
	private Category category;
	private List<Metric> metrics;
	
	
	
	public UnifiedDatasource(File file, String categoryName){
		this.file = file;
		this.category = new Category(categoryName);
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
	public void setFileType(FileType fileType) {
		this.fileType = fileType;
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

	public void setParserType(ParserType parserType) {
		this.parserType = parserType;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public boolean isUnified() {
		return true;
	}
	
	public int getCatId() {
	    return category.getId();
	}
	
	public void setCategory(Category category){
		this.category = category;
	}
	
	public List<Metric> getMetrics(){
		return metrics;
	}
	

}
