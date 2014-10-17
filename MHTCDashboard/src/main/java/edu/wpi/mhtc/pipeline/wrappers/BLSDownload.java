package edu.wpi.mhtc.pipeline.wrappers;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
//test push into git
//Downloads Unemployment (Not Seasonally adjusted) Data from Bureau of Labor Statistics
public class BLSDownload {
	
	static final HashMap<String, String[]> map;
	static{
		map = new HashMap<String,String[]>();
		
		// All States/Regions are assigned a stateID as well as a excel file name
		map.put("AL", new String[]{"01","AL-BLS.xls"});
		map.put("AK", new String[]{"02","AK-BLS.xls"});
		map.put("AZ", new String[]{"03","AZ-BLS.xls"});
		map.put("AR", new String[]{"04","AR-BLS.xls"});
		map.put("CA", new String[]{"05","CA-BLS.xls"});
		map.put("CO", new String[]{"06","CO-BLS.xls"});
		map.put("CT", new String[]{"07","CT-BLS.xls"});
		map.put("DC", new String[]{"08","DC-BLS.xls"});
		map.put("DE", new String[]{"09","DE-BLS.xls"});
		map.put("FL", new String[]{"10","FL-BLS.xls"});
		map.put("GA", new String[]{"11","GA-BLS.xls"});
		map.put("HI", new String[]{"12","HI-BLS.xls"});
		map.put("ID", new String[]{"13","ID-BLS.xls"});
		map.put("IL", new String[]{"14","IL-BLS.xls"});
		map.put("IN", new String[]{"15","IN-BLS.xls"});
		map.put("IA", new String[]{"16","IA-BLS.xls"});
		map.put("KS", new String[]{"17","KS-BLS.xls"});
		map.put("KY", new String[]{"18","KY-BLS.xls"});
		map.put("LA", new String[]{"19","LA-BLS.xls"});
		map.put("ME", new String[]{"20","ME-BLS.xls"});
		map.put("MD", new String[]{"21","MD-BLS.xls"});
		map.put("MA", new String[]{"22","MA-BLS.xls"});
		map.put("MI", new String[]{"23","MI-BLS.xls"});
		map.put("MN", new String[]{"24","MN-BLS.xls"});
		map.put("MS", new String[]{"25","MS-BLS.xls"});
		map.put("MO", new String[]{"26","MO-BLS.xls"});
		map.put("MT", new String[]{"27","MT-BLS.xls"});
		map.put("NE", new String[]{"28","NE-BLS.xls"});
		map.put("NV", new String[]{"29","NV-BLS.xls"});
		map.put("NH", new String[]{"30","NH-BLS.xls"});
		map.put("NJ", new String[]{"31","NJ-BLS.xls"});
		map.put("NM", new String[]{"32","NM-BLS.xls"});
		map.put("NY", new String[]{"33","NY-BLS.xls"});
		map.put("NC", new String[]{"34","NC-BLS.xls"});
		map.put("ND", new String[]{"35","ND-BLS.xls"});
		map.put("OH", new String[]{"36","OH-BLS.xls"});
		map.put("OK", new String[]{"37","OK-BLS.xls"});
		map.put("OR", new String[]{"38","OR-BLS.xls"});
		map.put("PA", new String[]{"39","PA-BLS.xls"});
		map.put("RI", new String[]{"40","RI-BLS.xls"});
		map.put("SC", new String[]{"41","SC-BLS.xls"});
		map.put("SD", new String[]{"42","SD-BLS.xls"});
		map.put("TN", new String[]{"43","TN-BLS.xls"});
		map.put("TX", new String[]{"44","TX-BLS.xls"});
		map.put("UT", new String[]{"45","UT-BLS.xls"});
		map.put("VT", new String[]{"46","VT-BLS.xls"});
		map.put("VA", new String[]{"47","VA-BLS.xls"});
		map.put("WA", new String[]{"48","WA-BLS.xls"});
		map.put("WV", new String[]{"49","WV-BLS.xls"});
		map.put("WI", new String[]{"50","WI-BLS.xls"});
		map.put("WY", new String[]{"51","WY-BLS.xls"});
	}


	
	
	
	public void getBLSAllStates(String beginyear, String endyear, String directory) throws IOException {
		for(String state : map.keySet()){
			getBLS(state, beginyear, endyear, directory);
		}
	}
	
	
	/**
	 * 
	 * @param state
	 * @param beginyear
	 * @param endyear
	 * @param directory
	 * @throws IOException
	 */
	public void getBLS(String state, String beginyear, String endyear, String directory) throws IOException {
		
		URL url = new URL("http://data.bls.gov/pdq/SurveyOutputServlet"); // http://data.bls.gov/pdq/SurveyOutputServlet,
																			// http://data.bls.gov/cgi-bin/dsrv
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		httpCon.setDoOutput(true);
		httpCon.setRequestMethod("POST");

		//post parameters
		String urlParameters = "x=" + URLEncoder.encode("35", "UTF-8") + "&y="
				+ URLEncoder.encode("7", "UTF-8") + "&request_action="
				+ URLEncoder.encode("get_data", "UTF-8") + "&reformat="
				+ URLEncoder.encode("true", "UTF-8") + "&from_results_page="
				+ URLEncoder.encode("true", "UTF-8") + "&years_option="
				+ URLEncoder.encode("specific_years", "UTF-8") + "&delimiter="
				+ URLEncoder.encode("comma", "UTF-8") + "&output_type="
				+ URLEncoder.encode("multi", "UTF-8") + "&periods_option="
				+ URLEncoder.encode("all_periods", "UTF-8") + "&output_view="
				+ URLEncoder.encode("data", "UTF-8") + "&to_year="
				+ URLEncoder.encode(endyear, "UTF-8") + "&from_year="
				+ URLEncoder.encode(beginyear, "UTF-8") + "&output_format="
				+ URLEncoder.encode("excelTable", "UTF-8")
				+ "&original_output_type="
				+ URLEncoder.encode("simple", "UTF-8") + "&series_id="
				+ URLEncoder.encode("LAUST"+map.get(state.toUpperCase())[0]+"0000000000003", "UTF-8");

		httpCon.setDoInput(true);
		httpCon.setDoOutput(true);
		httpCon.setAllowUserInteraction(false);
		
		
		
		//Send request
		DataOutputStream wr = new DataOutputStream(httpCon.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		
		httpCon.connect();
		
		

		//get input stream
		InputStream is = httpCon.getInputStream();
		FileOutputStream fos = new FileOutputStream(new File(directory+map.get(state.toUpperCase())[1]));

		byte[] buffer = new byte[4096];
		int length;
		while ((length = is.read(buffer)) > 0) {
			fos.write(buffer, 0, length);
		}

		System.out.println(httpCon.getResponseCode());
		System.out.println(httpCon.getResponseMessage());
		System.out.println(httpCon.getHeaderFields().get("Content-Type"));
		fos.close();
		is.close();

	}
}
