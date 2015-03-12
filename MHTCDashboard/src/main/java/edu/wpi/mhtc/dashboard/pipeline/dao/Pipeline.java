/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dashboard.pipeline.dao;

import java.nio.file.Path;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Pipeline class to store all information regarding a Talend pipeline
 * @author Alex Fortier
 *
 */
public class Pipeline {

	private String pipelineName;
	private String pipelineDesc;
	private String path;
	private String filename;
	private String dateAdded;
	private String uploadedBy;
	
	public Pipeline(String pipelineName, String pipelineDesc, String path, String filename, String uploadedBy) {
		this.pipelineName = pipelineName;
		this.pipelineDesc = pipelineDesc;
		this.path = path;
		this.filename = filename;
		this.uploadedBy = uploadedBy;
	}
	
	public Pipeline(String pipelineName, String pipelineDesc, String path, String filename, String dateAdded, String uploadedBy) {
		this.pipelineName = pipelineName;
		this.pipelineDesc = pipelineDesc;
		this.path = path;
		this.filename = filename;
		this.dateAdded = dateAdded;
		this.uploadedBy = uploadedBy;
	}

	/**
	 * @return the pipelineName
	 */
	public String getPipelineName() {
		return pipelineName;
	}
	/**
	 * @param pipelineName the pipelineName to set
	 */
	public void setPipelineName(String pipelineName) {
		this.pipelineName = pipelineName;
	}
	/**
	 * @return the pipelineDesc
	 */
	public String getPipelineDesc() {
		return pipelineDesc;
	}
	/**
	 * @param pipelineDesc the pipelineDesc to set
	 */
	public void setPipelineDesc(String pipelineDesc) {
		this.pipelineDesc = pipelineDesc;
	}
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}
	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
	/**
	 * @return the dateAdded
	 */
	public String getDateAdded() {
		return dateAdded;
	}
	/**
	 * @param timestamp the dateAdded to set
	 */
	public void setDateAdded(String timestamp) {
		this.dateAdded = timestamp;
	}
	/**
	 * @return the uploadedBy
	 */
	public String getUploadedBy() {
		return uploadedBy;
	}
	/**
	 * @param uploadedBy the uploadedBy to set
	 */
	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}
	
}
