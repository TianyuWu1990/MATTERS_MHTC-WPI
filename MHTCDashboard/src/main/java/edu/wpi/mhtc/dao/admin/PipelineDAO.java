/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dao.admin;

import java.util.List;

import edu.wpi.mhtc.model.admin.Pipeline;

/**
 * Interface for Pipeline DAO layer
 * @author Alex Fortier
 *
 */
public interface PipelineDAO {

	/**
	 * Save a pipeline to the database
	 * @param pipeline
	 */
	public void save(Pipeline pipeline);
	
	/**
	 * Delete pipeline from the database
	 * @param pipelineName
	 */
	public void delete(String pipelineName);
	
	/**
	 * Get all pipelines from the database
	 * @return
	 */
	public List<Pipeline> getAll();

	/**
	 * Get specific pipeline from database
	 * @param pipelineName
	 * @return
	 */
	public Pipeline get(String pipelineName);
}
