package edu.wpi.mhtc.dashboard.pipeline.data;

import java.io.File;

import edu.wpi.mhtc.dashboard.pipeline.parser.ParserType;

public class UnifiedCSVDataSource extends DataSource{
	
	public UnifiedCSVDataSource(File file, Category category){
		super(file, category);
		this.fileType = FileType.csv;
		this.parserType = ParserType.text;
	}
	
}

