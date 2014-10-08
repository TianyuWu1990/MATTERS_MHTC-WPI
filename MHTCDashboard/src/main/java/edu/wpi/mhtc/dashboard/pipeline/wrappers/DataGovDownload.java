package edu.wpi.mhtc.dashboard.pipeline.wrappers;

// Download JSON data from DataGov
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;


public class DataGovDownload {
	private String base_url = "https://inventory.data.gov/api/action/";
	private URLDownload downloader;
	private Map<String, String> cookies;
	
	public DataGovDownload() throws IOException {
		this.loginInventoryPage();
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
	
	// This will login into inventory page. Magic. Don't change.
	private void loginInventoryPage() throws IOException {
		Response document    = Jsoup.connect("https://inventory.data.gov/login_generic?came_from=/user/logged_in")
									.data("login", "wpimhtc", "password", "wpimhtc", "remember", "63072000")
								    .method(Method.POST)
								    .execute();
		cookies = document.cookies();
	}
	
	// Smart file downloader, it goes into the government data API site,
	// looks for the CSV URL of the download button, grabs and download that CSV file
	// Tested for : https://inventory.data.gov/dataset/032e19b4-5a90-41dc-83ff-6e4cd234f565/resource/38625c3d-5388-4c16-a30f-d105432553a4
	
	public void smartDownload(String url, String filename) throws IOException {
		try {
			org.jsoup.nodes.Document doc = Jsoup.connect(url).cookies(cookies).get();
			String downloadLink = doc.select("a.resource-url-analytics").first().attr("href"); 
			System.out.println(downloadLink);
			downloader.HTTPDownload(downloadLink, filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
