package edu.wpi.mhtc.dashboard.pipeline.dao;

import java.util.List;

public interface PipelineDAO {

	public void save(Pipeline pipeline);
	
	public void delete(String pipelineName);
	
	public List<Pipeline> getAll();

	public Pipeline get(String pipelineName);
}
