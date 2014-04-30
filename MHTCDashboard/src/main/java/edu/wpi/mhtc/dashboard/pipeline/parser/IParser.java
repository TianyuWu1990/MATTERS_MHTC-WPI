package edu.wpi.mhtc.dashboard.pipeline.parser;

import java.util.List;

import edu.wpi.mhtc.dashboard.pipeline.data.FileData;
import edu.wpi.mhtc.dashboard.pipeline.data.LineData;

public interface IParser extends Iterable<LineData> {
	FileData parseAll() throws Exception;

	List<String> getColumnNames();
}
