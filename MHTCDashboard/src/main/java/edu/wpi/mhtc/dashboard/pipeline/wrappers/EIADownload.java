package edu.wpi.mhtc.dashboard.pipeline.wrappers;

import edu.wpi.mhtc.dashboard.pipeline.config.StateInfoConfig;

public class EIADownload implements IWrapper {
	final private String apiKey = "606B66BBE8BB7EDF94953ADF6430E182";
	final private String apiUrl = "http://api.eia.gov/series/?api_key=" + apiKey;
	
	public EIADownload() throws Exception {
		this.download();
	}
	
	public void download() throws Exception {
		System.out.println("==== eiv.gov API downloader started ====");
		this.DownloadAverageElectricityCost();	
	}
	
	/**
	 * Download the average electricity cost into JSON files for each state.
	 * Data-structure: 
	 
		Units: json.series[0].units
		Data: json.series.data[x][y]
		
		x: array indexes. Use for loops and get them all
		y = 0: year, available from 2001-2013 now
		y = 1: Average electricity cost

	 * @throws Exception
	 */
	public void DownloadAverageElectricityCost() throws Exception {
		System.out.println("* Downloading Average Electricity Cost from eia.gov...");
		URLDownload downloader = new URLDownload();
		
		for (String state: StateInfoConfig.getInstance().getStateInitialsList()) {
			String url = apiUrl + String.format("&series_id=ELEC.PRICE.%s-COM.A", state.toUpperCase());
			
			downloader.HTTPDownload(url, "tmp/eia-cost-" + state + ".json");
			System.out.println("Downloaded Average Electricity Cost for " + state.toUpperCase() + ".");
		}
	}
	
	/**
	 * NOT COMPLETED, NEED URL.
	 * Download the average electricity rate into JSON file
	 * Data-structure: 
	 
		Units: json.series[0].units
		Data: json.series.data[x][y]

	 * @throws Exception
	 */
	public void DownloadAverageElectricityRate() throws Exception {
		System.out.println("* Downloading Average Electricity Rate from eia.gov...");
		URLDownload downloader = new URLDownload();
		
		for (String state: StateInfoConfig.getInstance().getStateInitialsList()) {
			String url = apiUrl + String.format("&series_id=ELEC.PRICE.%s-COM.A", state.toUpperCase());
			
			downloader.HTTPDownload(url, "tmp/eia-rate-" + state + ".json");
			System.out.println("Downloaded Average Electricity Rate for " + state.toUpperCase() + ".");
		}
	}
}
