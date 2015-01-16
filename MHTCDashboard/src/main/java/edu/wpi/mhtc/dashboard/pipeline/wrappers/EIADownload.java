/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dashboard.pipeline.wrappers;

import java.io.PrintWriter;

import edu.wpi.mhtc.dashboard.pipeline.data.State;
import org.json.*;

/**
 * Wrapper for EIA.gov.
 *
 */
public class EIADownload implements IWrapper {
	final private String apiKey = "606B66BBE8BB7EDF94953ADF6430E182";
	final private String apiUrl = "http://api.eia.gov/series/?api_key=" + apiKey;
	private PrintWriter writerRate;
	
	public EIADownload() throws Exception {
		
		this.download();
	}
	
	public void download() throws Exception {
		System.out.println("==== eiv.gov API downloader started ====");
		this.DownloadAverageElectricityCost();	
	}
	
	/**
	 * Download the average electricity rate into JSON file for each state.
	 * Data-structure: 
	 
		Units: json.series[0].units = cents per kilowatthour
		Data: json.series.data[x][y]
		
		x: array indexes. Use for loops and get them all
		y = 0: year, available from 2001-2013 now
		y = 1: Average electricity cost

	 * @throws Exception
	 */
	public void DownloadAverageElectricityCost() throws Exception {
		System.out.println("* Downloading Average Electricity Rate from eia.gov...");
		URLDownload downloader = new URLDownload();
		writerRate = new PrintWriter("tmp/eia-rate.txt", "UTF-8");
		
		for (State state: State.getList()) {
			
			String stateInit = state.getInitial();
			String url = apiUrl + String.format("&series_id=ELEC.PRICE.%s-COM.A", stateInit.toUpperCase());
			
			String jsonStr = downloader.getText(url);

			processJsonToFile(stateInit.toUpperCase(), jsonStr, writerRate);
		}
		System.out.println("Completed.");
		writerRate.close();
	}
	
	private void processJsonToFile(String state, String jsonStr, PrintWriter writer) {
		JSONObject json = new JSONObject(jsonStr);
		JSONArray seriesData =  json.getJSONArray("series").getJSONObject(0).getJSONArray("data");
		
		for (int i = 0; i < seriesData.length(); i++) {
			String year = seriesData.getJSONArray(i).getString(0);
			String data = seriesData.getJSONArray(i).get(1).toString();
			
			writer.println(state + " " + year + " " + data);
		}
	}
	
}
