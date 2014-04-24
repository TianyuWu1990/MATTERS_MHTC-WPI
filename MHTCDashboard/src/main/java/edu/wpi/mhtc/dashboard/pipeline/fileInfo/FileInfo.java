package edu.wpi.mhtc.dashboard.pipeline.fileInfo;

import edu.wpi.mhtc.dashboard.pipeline.parser.ParserType;

public class FileInfo {
	private String fileName;
	private FileType fileType;
	private ParserType parserType;
	private LoadInfo loadInfo;
	
	public FileInfo(String fileName, FileType fileType, ParserType parserType, LoadInfo loadInfo){
		this.fileName = fileName;
		this.fileType = fileType;
		this.loadInfo = loadInfo;
		this.parserType = parserType;
	}
	
	public FileInfo(String fileName){
		this.fileName = fileName;
	}
	
	@Override
	public int hashCode(){
		return fileName.hashCode();
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		if(!(obj instanceof FileInfo)){
			return false; 
		}
		FileInfo info = (FileInfo)obj;
		return this.fileName.equals(info);
	}
	
	
	/*
	 * setters and getters
	 */
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
}