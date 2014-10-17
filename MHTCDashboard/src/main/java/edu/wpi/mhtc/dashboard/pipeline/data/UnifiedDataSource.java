package edu.wpi.mhtc.dashboard.pipeline.data;

import java.io.File;

import edu.wpi.mhtc.dashboard.pipeline.parsers.ParserType;
import edu.wpi.mhtc.dashboard.pipeline.parsers.UnifiedFormatException;

public class UnifiedDataSource extends DataSource{
	
	FileType fileType = FileType.xlsx;
	ParserType parserType = ParserType.excel;
	
	public UnifiedDataSource(File file, Category category) throws UnifiedFormatException{
		super(file, category);
		if(!file.getName().endsWith("xls") && !file.getName().endsWith("xlsx")){
			throw new UnifiedFormatException("File must be an Excel spreadsheet. File was "+file.getName());
		}
	}
	
	
//	public static void main(String[] args) throws Exception{
//		
//		File file = new File("TestUnifiedFile.xlsx");
//		Category cat = new Category(2);
//		
//		UnifiedDataSource uni = new UnifiedDataSource(file, cat);
//		
//		File badFile = new File("pom.xml");
//		
//		UnifiedDataSource uni2 = new UnifiedDataSource(badFile, cat);
//	}
	
}

