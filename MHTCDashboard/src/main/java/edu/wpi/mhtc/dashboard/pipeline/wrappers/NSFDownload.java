package edu.wpi.mhtc.dashboard.pipeline.wrappers;

import java.io.IOException;
import java.io.PrintWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NSFDownload{
	public NSFDownload() throws Exception {

	}
	
	public void downloadSingle(String url, String filename) throws IOException {
		if (url.indexOf("nsf.gov") < 0) {
			throw new IOException("Invalid NSF web page URL.");
		}
		
		PrintWriter writer = new PrintWriter(filename, "UTF-8");
		
		try {
			org.jsoup.nodes.Document doc = Jsoup.connect(url).timeout(0).get();

			/* Scrap years data */
			Elements years = doc.select("#my_table th");
			
			for (Element year : years) {
				if (this.isNumeric(year.text())) {
					writer.print(year.text() + " ");
				}
			}
			writer.println();
			/* Now, scrap data */
			doc.select("td span").empty(); // Clean up the trend graphs in span tags
			Elements rowData = doc.select("#my_table tbody tr");
			
			for (Element row : rowData) {
				Elements rowInfo = row.select("td");
				String rowLine = "";
				for (Element cell: rowInfo) {
					rowLine += cell.text() + " ";					
				}
				writer.println(rowLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		writer.close();
		System.out.println("Saved NSF data from " + url + " to file " + filename);
	}
	
	/**
	 * Check if the String only contains digits
	 * @param s: Input String
	 * @return: True if String s is numeric, otherwise False
	 */
	private boolean isNumeric(String s) {  
	    return s.matches("[-+]?\\d*\\.?\\d+");  
	}  

}
