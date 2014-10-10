package edu.wpi.mhtc.pipeline.parser;

import edu.wpi.mhtc.pipeline.data.Category;
import edu.wpi.mhtc.pipeline.data.CategoryException;
import edu.wpi.mhtc.pipeline.data.Line;

public interface IParser extends Iterable<Line> {
	
	public boolean parseAll() throws Exception;

	public void validateMetricNames(Category category) throws CategoryException;
}

