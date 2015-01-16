package edu.wpi.mhtc.dashboard.pipeline.wrappers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class DOLDownload {
	
	
	/**
	 * download Average Employer Contribution Rates by State from http://www.ows.doleta.gov/unemploy/avg_employ.asp.
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
		
		for(int i = beginyear ; i <= endyear;  i++){
			
			String link = "http://www.ows.doleta.gov/unemploy/docs/aetr-"+i+"est.pdf";
			String filename = directory+"/Unemployment_Employer_Rates_"+i;
			
			try{
				URL url = new URL(link);
				URLConnection con = url.openConnection();
				con.getInputStream();
				
				down.HTTPDownload(link, filename+".pdf");
				reader.readPDF(new File(filename+".pdf"));
			}
			catch(FileNotFoundException e){	
				link = "http://www.ows.doleta.gov/unemploy/docs/aetr-"+i+"est.xls";
				down.HTTPDownload(link, filename+".xls");
			}
		}
	}

}
