package edu.wpi.mhtc.pipeline.data;

import java.io.File;

import edu.wpi.mhtc.pipeline.parser.ParserType;

public class UnifiedDataSource extends DataSource{
	
	FileType fileType = FileType.xlsx;
	ParserType parserType = ParserType.excel;
	
	public UnifiedDataSource(File file, Category category){
		super(file, category);
	}
	
}

