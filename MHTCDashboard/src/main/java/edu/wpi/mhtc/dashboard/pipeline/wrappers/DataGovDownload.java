package edu.wpi.mhtc.dashboard.pipeline.wrappers;

// Download JSON data from DataGov
import java.io.IOException;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;


public class DataGovDownload {
	private String base_url = "https://inventory.data.gov/api/action/";
	private URLDownload downloader;
	
	public DataGovDownload() {
		downloader = new URLDownload();
	}
	
	
	// Performs query on select database
	// query = SELECT * from "resource_id";
	// PostgreSQL query!
	public void queryDownload(String sql, String filename) {
		String download_url = base_url + "datastore_search_sql?sql=" + URLEncoder.encode(sql);
		URLDownload downloader = new URLDownload();
		downloader.HTTPDownload(download_url, filename);
	}
	
	// Smart file downloader, it goes into the government data API site,
	// looks for the CSV URL of the download button, grabs and download that CSV file
	// Tested for : https://inventory.data.gov/dataset/032e19b4-5a90-41dc-83ff-6e4cd234f565/resource/38625c3d-5388-4c16-a30f-d105432553a4
	public void smartDownload(String url, String filename) throws IOException {
		try {
			org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
			String downloadLink = doc.select("a.btn.btn-primary.resource-url-analytics.resource-type-None").first().attr("href");
			downloader.HTTPDownload(downloadLink, filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
