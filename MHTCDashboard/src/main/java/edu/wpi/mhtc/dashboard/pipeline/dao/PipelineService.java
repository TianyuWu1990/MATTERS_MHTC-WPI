package edu.wpi.mhtc.dashboard.pipeline.dao;

import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Service for getting pipeline information
 * @author Alex Fortier
 *
 */
public class PipelineService {
	
	@Autowired private PipelineDAO dao;
	
	/**
	 * Save new pipeline metadata to the database
	 * @param pipelineName
	 * @param pipelineDesc
	 * @param path
	 * @param filename
	 * @param uploadedBy
	 */
	public void save(String pipelineName, String pipelineDesc, String path, String filename, String uploadedBy) {
		dao.save(new Pipeline(pipelineName, pipelineDesc, path, filename, uploadedBy));
	}
	
	/**
	 * Delete pipeline metadata from database, based on name
	 * TODO Need to look into a way to delete files from web server
	 * @param pipelineName
	 */
	public void delete(String pipelineName) {
		dao.delete(pipelineName);
	}
	
	/**
	 * Get all pipeline metadata from the database
	 * @return
	 */
	public List<Pipeline> getAll() {
		return dao.getAll();
	}
}
