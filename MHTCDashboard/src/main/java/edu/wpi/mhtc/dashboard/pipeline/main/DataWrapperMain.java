/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dashboard.pipeline.main;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import edu.wpi.mhtc.dashboard.pipeline.wrappers.BEADownload;
import edu.wpi.mhtc.dashboard.pipeline.wrappers.BLSDownload;
import edu.wpi.mhtc.dashboard.pipeline.wrappers.DOLDownload;
import edu.wpi.mhtc.dashboard.pipeline.wrappers.DataGovDownload;
import edu.wpi.mhtc.dashboard.pipeline.wrappers.EIADownload;
import edu.wpi.mhtc.dashboard.pipeline.wrappers.IPEDSDownload;
import edu.wpi.mhtc.dashboard.pipeline.wrappers.URLDownload;
import edu.wpi.mhtc.dashboard.pipeline.wrappers.UnZip;
import edu.wpi.mhtc.dashboard.pipeline.wrappers.WebTableWrapper;

public class DataWrapperMain {

	final static String DATA_DIRECTORY = "tmp";
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws IOException {

		File dir = new File(DATA_DIRECTORY);
		//		create this directory if it doesn't already exist
		dir.mkdir();

		URLDownload down = new URLDownload();
		BLSDownload bls = new BLSDownload();
		BEADownload bea = new BEADownload();
		DOLDownload dol = new DOLDownload();
		UnZip unZip = new UnZip();

//		//US Census-State and Local Tax Burden
//		down.HTTPDownload("http://www2.census.gov/govs/statetax/state_tax_collections.zip", DATA_DIRECTORY+"/state_tax_collections_1.zip");
//		unZip.unZipIt(DATA_DIRECTORY+"/state_tax_collections_1.zip", DATA_DIRECTORY);
//
//		//NSF-TechEmployment
//		down.HTTPDownload("http://www.nsf.gov/statistics/seind14/content/chapter-8/8-55_all.xls ", DATA_DIRECTORY+"/techemployment.xls");
//
//		//Unemployment Insurance Payroll Tax
//		down.HTTPDownload("http://www.taxpolicycenter.org/taxfacts/Content/Excel/state_unemp_rate.xls", DATA_DIRECTORY+"/state_unemp_rate.xls");
//
//		//Bureau of Labor Statistics-Unemployment rate
//		bls.getBLSAllStates("2004", "2014", dir);
//
//
//		//Bureau of Economic Analysis-all regions and years
//		bea.download("http://www.bea.gov/itable/download.cfm?ext=csv&fid=B543E5DBA3B4E53FD891B19007B42B878D129B6B2748DC214E8A4E7CABFEDCCB"
//				+ "522F771881E09A5857B449F0B6C397CB7F09B19F65C08EDE33EDFE47038C81A4", DATA_DIRECTORY+"/allbea.csv");
//
//		//IPEDS number of colleges and universities
//		DataGovDownload data_gov_downloader = new DataGovDownload();
//		
//		data_gov_downloader.smartDownload("https://inventory.data.gov/dataset/usaid-development-credit-authority-guarantee-data-utilization-and-claims", "tmp/dev_credit.csv");
//		
//		//IPEDS number of colleges and universities 2013
//		data_gov_downloader.smartDownload("https://inventory.data.gov/dataset/032e19b4-5a90-41dc-83ff-6e4cd234f565/resource/38625c3d-5388-4c16-a30f-d105432553a4", "tmp/ipeds.csv");
//		data_gov_downloader.queryDownload("SELECT \"STABBR\", COUNT(*) from \"38625c3d-5388-4c16-a30f-d105432553a4\" GROUP BY \"STABBR\"", "tmp/ipeds_count.json");
//		
////		//Energy
////		try {
////			EIADownload eiaDownloader = new EIADownload();
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
//		
//		//NSF - 8th grade performance
//		WebTableWrapper.download("http://www.nsf.gov/statistics/seind14/index.cfm/state-data/table.htm?table=8", "#my_table", "tmp/8th_grade_performance.xls", Arrays.asList(-1));
//		
//		//NSF - BS degrees in workforce
//		WebTableWrapper.download("http://www.nsf.gov/statistics/seind14/index.cfm/state-data/table.htm?table=33", "#my_table", "tmp/bs_workforce.xls", Arrays.asList(13));
//
//		//WebTableWrapper.download("http://taxfoundation.org/article/2014-state-business-tax-climate-index", ".tablesorter.printImitationTable.plainTable", "tmp/tf-14-tci.xls", Arrays.asList(-1));
//		
//		//CNBC rankings
//		WebTableWrapper.download("http://www.cnbc.com/id/100824779", ".csvData.data", "tmp/cnbc-13-overall-ranks.xls", Arrays.asList(-1));
//		
//		//IPEDS STEM Degrees
//		IPEDSDownload ipeds = new IPEDSDownload();
//		
//		
//		//Mass High Tech Clusters - files are named Indicator 1 ...
//		down.HTTPDownload("http://index.masstech.org/sites/index/files/ff/10/Indicator%201%20raw%20data.zip", DATA_DIRECTORY+"/mass_tech_clusters.zip");
//		unZip.unZipIt(DATA_DIRECTORY+"/mass_tech_clusters.zip", DATA_DIRECTORY);
//		
//		//Milken rankings
////		Can't find table???
////		WebTableWrapper.download("http://statetechandscience.org/stateTech.taf?page=state-ranking", "table_id", "tmp/milken-ranks.xls", Arrays.asList(-1));
//			
//		//Tax foundation - personal income tax
//		down.HTTPDownload("http://taxfoundation.org/sites/taxfoundation.org/files/docs/State%20Individual%20Income%20Tax%20Rates%2C%202000-2014.xlsx", DATA_DIRECTORY+"/Personal_Income_Tax_Rate.xls");
//
//		//Tax foundation - capital gains tax
//		down.HTTPDownload("http://taxfoundation.org/sites/taxfoundation.org/files/docs/FF414dataset_1.xls", DATA_DIRECTORY+"/Capital_Gains_Tax_Rate.xls");
//
//		//Tax foundation - Corporate Income Tax 
//		down.HTTPDownload("http://taxfoundation.org/sites/taxfoundation.org/files/docs/State%20Corporate%20Income%20Tax%20Rates%2C%202000-2014.xlsx", DATA_DIRECTORY+"/Corporate_Income_Tax_Rate.xls");
//		
//		//Tax foundation - Sales tax 
//		down.HTTPDownload("http://taxfoundation.org/sites/taxfoundation.org/files/docs/State%20Sales%2C%20Gasoline%2C%20Cigarette%20and%20Alcohol%20Taxes%2C%202000-2014.xlsx", DATA_DIRECTORY+"/Sales_Tax_Rate.xls");
//				
//		//Unemployment
//		dol.getEmployerRateYears(2009, 2014, DATA_DIRECTORY);
		
		// BLS Wrapper
		WebTableWrapper.downloadHtmlUnit("http://www.bls.gov/cew/cewind.htm#year=2009&qtr=A&own=5&ind=10&size=0", "table1", "tmp/bls_average_annual_wage_2009.xls", 10);
		WebTableWrapper.downloadHtmlUnit("http://www.bls.gov/cew/cewind.htm#year=2010&qtr=A&own=5&ind=10&size=0", "table1", "tmp/bls_average_annual_wage_2010.xls", 10);
		WebTableWrapper.downloadHtmlUnit("http://www.bls.gov/cew/cewind.htm#year=2011&qtr=A&own=5&ind=10&size=0", "table1", "tmp/bls_average_annual_wage_2011.xls", 10);
		WebTableWrapper.downloadHtmlUnit("http://www.bls.gov/cew/cewind.htm#year=2012&qtr=A&own=5&ind=10&size=0", "table1", "tmp/bls_average_annual_wage_2012.xls", 10);
		WebTableWrapper.downloadHtmlUnit("http://www.bls.gov/cew/cewind.htm#year=2013&qtr=A&own=5&ind=10&size=0", "table1", "tmp/bls_average_annual_wage_2013.xls", 10);
	}
	
}
