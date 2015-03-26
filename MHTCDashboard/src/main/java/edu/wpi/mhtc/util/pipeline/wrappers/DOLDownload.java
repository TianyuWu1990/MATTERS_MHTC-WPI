/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.util.pipeline.wrappers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class DOLDownload {
	
	
	/**
	 * Download Average Employer Contribution Rates by State from http://www.ows.doleta.gov/unemploy/avg_employ.asp.
	 * Excel files are download, pdf files are converted to text
	 * available years: 2005 - 2014
	 * @param beginyear
	 * @param endyear
	 * @param directory to save resulting files
	 * @throws IOException
	 */
	public void getEmployerRateYears(int beginyear, int endyear, String directory) throws IOException {
		
		URLDownload down = new URLDownload();
		PDFReader reader = new PDFReader();
		
		StringBuilder link = new StringBuilder("http://www.ows.doleta.gov/unemploy/docs/aetr-");
		StringBuilder filename = new StringBuilder(directory);
		filename.append("/Unemployment_Employer_Rates_");
		URL url = null;
		URLConnection con = null;
		
		for(int i = beginyear ; i <= endyear;  i++){
			
			link.delete(45, link.length()); 
			link.append(i);
			link.append("est.pdf");
			
			filename.delete(32, filename.length());
			filename.append(i);
			filename.append(".pdf");
			
			try{
				url = new URL(link.toString());
				con = url.openConnection();
				con.getInputStream();
				
				down.HTTPDownload(link.toString(), filename.toString());
				reader.readPDF(new File(filename.toString()));
			}
			catch(FileNotFoundException e){	
				link.delete(link.length() - 3, link.length());
				link.append("xls");
				filename.delete(filename.length()-3, filename.length());
				filename.append("xls");
				down.HTTPDownload(link.toString(), filename.toString());
			}
		}
	}

}
