package edu.wpi.mhtc.dashboard.pipeline.data;

import java.io.File;

import edu.wpi.mhtc.dashboard.pipeline.parser.ParserType;

public class UnifiedCSVDataSource extends DataSource{
	
	FileType fileType = FileType.csv;
	ParserType parserType = ParserType.text;
	
	public UnifiedCSVDataSource(File file, Category category){
		super(file, category);
	}
	
}

