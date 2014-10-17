package edu.wpi.mhtc.dashboard.pipeline.parsers;

import edu.wpi.mhtc.dashboard.pipeline.data.Category;
import edu.wpi.mhtc.dashboard.pipeline.data.CategoryException;
import edu.wpi.mhtc.dashboard.pipeline.data.Line;

public interface IParser extends Iterable<Line> {
	
	public boolean parseAll() throws Exception;

	public void validateMetrics(Category category) throws CategoryException;
}

