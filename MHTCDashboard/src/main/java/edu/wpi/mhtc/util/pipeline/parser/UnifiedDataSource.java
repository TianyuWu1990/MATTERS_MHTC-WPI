/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.util.pipeline.parser;

import java.io.File;

import edu.wpi.mhtc.model.admin.Category;

/**
 * Excel file containing data organized in unified format loaded using manual upload tool in admin center.
 * @author cakuhlman
 * @version December 2014
 *
 */
public class UnifiedDataSource extends DataSource {
		
	public UnifiedDataSource(File file, Category category) throws UnifiedFormatException {
		super(file, category);
		if(file.getName().endsWith("xls")){
			fileType = FileType.xls;
		}
		else if(file.getName().endsWith("xlsx")){
			fileType = FileType.xlsx;
		}
		else {
			UnifiedFormatException u = new UnifiedFormatException("File must be an Excel spreadsheet. File was "+file.getName());
			u.setSolution("Please convert the uploaded file to .xlsx or .xls format");
			
			throw u;
		}
		
	}
	
}

