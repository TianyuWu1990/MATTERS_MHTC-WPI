package edu.wpi.mhtc.dashboard.pipeline.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import edu.wpi.mhtc.dashboard.pipeline.config.StateInfoConfig;
import edu.wpi.mhtc.dashboard.pipeline.wrappers.BEADownload;
import edu.wpi.mhtc.dashboard.pipeline.wrappers.BLSDownload;
import edu.wpi.mhtc.dashboard.pipeline.wrappers.DataGovDownload;
import edu.wpi.mhtc.dashboard.pipeline.wrappers.EIADownload;
import edu.wpi.mhtc.dashboard.pipeline.wrappers.URLDownload;
import edu.wpi.mhtc.dashboard.pipeline.wrappers.UnZip;
import edu.wpi.mhtc.dashboard.pipeline.wrappers.WebTableWrapper;

public class DataWrapperMain {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		/*URLDownload down = new URLDownload();
		BLSDownload bls = new BLSDownload();
		BEADownload bea = new BEADownload();
		UnZip unZip = new UnZip();
		

		//US Census-State and Local Tax Burden
		down.HTTPDownload("http://www2.census.gov/govs/statetax/state_tax_collections.zip", "tmp/state_tax_collections_1.zip");
		unZip.unZipIt("tmp/state_tax_collections_1.zip","tmp");
		
		//NSF-TechEmployment
		down.HTTPDownload("http://www.nsf.gov/statistics/seind12/c8/tt08-54.xls", "tmp/techemployment.xls");
		
		//Unemployment Insurance Payroll Tax
		down.HTTPDownload("http://www.taxpolicycenter.org/taxfacts/Content/Excel/state_unemp_rate.xls", "tmp/state_unemp_rate.xls");
		
		//Bureau of Labor Statistics-Unemployment rate
		bls.getBLS("ma", "2004", "2014", "tmp/");
		bls.getBLS("ca", "2004", "2014", "tmp/");
		bls.getBLS("co", "2004", "2014", "tmp/");
		bls.getBLS("tx", "2004", "2014", "tmp/");
		bls.getBLS("mn", "2004", "2014", "tmp/");
		bls.getBLS("ga", "2004", "2014", "tmp/");
		bls.getBLS("nc", "2004", "2014", "tmp/");
		bls.getBLS("va", "2004", "2014", "tmp/");
		bls.getBLS("pa", "2004", "2014", "tmp/");
		bls.getBLS("ny", "2004", "2014", "tmp/");
		bls.getBLS("nh", "2004", "2014", "tmp/");
		bls.getBLS("ct", "2004", "2014", "tmp/");
		bls.getBLS("ut", "2004", "2014", "tmp/");
		bls.getBLS("wa", "2004", "2014", "tmp/");
		bls.getBLS("md", "2004", "2014", "tmp/");
		
		//Bureau of Economic Analysis-all regions and years
		bea.download("http://www.bea.gov/itable/download.cfm?ext=csv&fid=B543E5DBA3B4E53FD891B19007B42B878D129B6B2748DC214E8A4E7CABFEDCCB522F771881E09A5857B449F0B6C397CB7F09B19F65C08EDE33EDFE47038C81A4", "tmp/allbea.csv");
		
		DataGovDownload data_gov_downloader = new DataGovDownload();
		data_gov_downloader.smartDownload("https://inventory.data.gov/dataset/usaid-development-credit-authority-guarantee-data-utilization-and-claims", "tmp/dev_credit.csv");
		data_gov_downloader.smartDownload("https://inventory.data.gov/dataset/032e19b4-5a90-41dc-83ff-6e4cd234f565/resource/38625c3d-5388-4c16-a30f-d105432553a4", "tmp/ipeds.csv");
		data_gov_downloader.queryDownload("SELECT \"STABBR\", COUNT(*) from \"38625c3d-5388-4c16-a30f-d105432553a4\" GROUP BY \"STABBR\"", "tmp/ipeds_count.json");
		
		EIADownload eiaDownloader = new EIADownload();
		*/
		WebTableWrapper.download("http://www.nsf.gov/statistics/seind14/index.cfm/state-data/table.htm?table=8", "#my_table", "tmp/8th_grade_performance.txt", ",", Arrays.asList(-1));
		WebTableWrapper.download("http://www.nsf.gov/statistics/seind14/index.cfm/state-data/table.htm?table=33", "#my_table", "tmp/bs_workforce.txt", ",", Arrays.asList(13));
		WebTableWrapper.download("http://taxfoundation.org/article/2014-state-business-tax-climate-index", ".tablesorter.printImitationTable.plainTable", "tmp/tf-14-tci.txt", ",", Arrays.asList(-1));
	}
	
	public static void twenty_source_download() throws Exception {
		DataPipeline.download("url", "temp.xlsx", "Income");
	}

}

