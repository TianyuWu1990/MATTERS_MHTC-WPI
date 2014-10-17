package edu.wpi.mhtc.pipeline.main;

import java.io.File;
import java.io.IOException;

import edu.wpi.mhtc.pipeline.wrappers.BEADownload;
import edu.wpi.mhtc.pipeline.wrappers.BLSDownload;
import edu.wpi.mhtc.pipeline.wrappers.URLDownload;
import edu.wpi.mhtc.pipeline.wrappers.UnZip;

public class DataWrapperMain {

	final static String DATA_DIRECTORY = "tmp";
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		File dir = new File(DATA_DIRECTORY);
//		create this directory if it doesn't already exist
		dir.mkdir();
	
		
		URLDownload down = new URLDownload();
		BLSDownload bls = new BLSDownload();
		BEADownload bea = new BEADownload();
		UnZip unZip = new UnZip();
		

		//US Census-State and Local Tax Burden
		down.HTTPDownload("http://www2.census.gov/govs/statetax/state_tax_collections.zip", DATA_DIRECTORY+"/state_tax_collections_1.zip");
		unZip.unZipIt(DATA_DIRECTORY+"/state_tax_collections_1.zip", DATA_DIRECTORY);
		
		//NSF-TechEmployment
//	CK-10/2014 this is original source:	
//		down.HTTPDownload("http://www.nsf.gov/statistics/seind12/c8/tt08-54.xls", "DATA_DIRECTORY/techemployment.xls");
		
//	The nsf site has changed, and new years are available here:
		down.HTTPDownload("http://www.nsf.gov/statistics/seind14/content/chapter-8/8-55_all.xls ", DATA_DIRECTORY+"/techemployment.xls");
		
		//Unemployment Insurance Payroll Tax
		down.HTTPDownload("http://www.taxpolicycenter.org/taxfacts/Content/Excel/state_unemp_rate.xls", DATA_DIRECTORY+"/state_unemp_rate.xls");
		
		//Bureau of Labor Statistics-Unemployment rate
		bls.getBLSAllStates("2004", "2014", dir);
		
		
		//Bureau of Economic Analysis-all regions and years
		bea.download("http://www.bea.gov/itable/download.cfm?ext=csv&fid=B543E5DBA3B4E53FD891B19007B42B878D129B6B2748DC214E8A4E7CABFEDCCB522F771881E09A5857B449F0B6C397CB7F09B19F65C08EDE33EDFE47038C81A4", DATA_DIRECTORY+"/allbea.csv");
	}
	
	public static void twenty_source_download() throws Exception {
		DataPipeline.download("url", "temp.xlsx", "Income");
	}

}
