package edu.wpi.mhtc.dashboard.pipeline.fileInfo;

import java.io.File;

import edu.wpi.mhtc.dashboard.pipeline.parser.ParserType;


public class FileInfo {
	private String fileName;
	private FileType fileType;
	private ParserType parserType;
	private LoadInfo loadInfo;
	private File file;
	private boolean isUnified = false;
	
	public FileInfo(String fileName, FileType fileType, ParserType parserType, LoadInfo loadInfo){
		this.fileName = fileName;
		this.fileType = fileType;
		this.loadInfo = loadInfo;
		this.parserType = parserType;
		this.file = new File(fileName);
	}
	
	public FileInfo(String fileName){
		this.fileName = fileName;
	}
	
	/*
	 * only for the admin tool to call
	 */
	public FileInfo(File file){
		this.file = file;
		this.isUnified = true;
		this.fileName = file.getName();
		this.fileType = this.getFileTypeByFileName(this.fileName);
		this.parserType = ParserType.excel;
		this.loadInfo = new LoadInfo(0, 0, false);
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
	
	private FileType getFileTypeByFileName(String fileName){
		String[] types = fileName.split("\\.");
		return FileType.valueOf(types[types.length - 1]);
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

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public boolean isUnified() {
		return isUnified;
	}

	public void setUnified(boolean isUnified) {
		this.isUnified = isUnified;
	}
}