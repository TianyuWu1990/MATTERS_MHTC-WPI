package edu.wpi.mhtc.dashboard.pipeline.wrappers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public final class WebTableWrapper {
	private WebTableWrapper() {

	}

	/**
	 * HTML Table data scrapper.
	 * @param url: The website URL
	 * @param tblSelector: CSS Selector for the table. This needs to be unique.
	 * @param filename: File to save
	 * @param separator: String Separator for each data value. Preferred "," or " ".
	 * @param ignoreColumnIndexes: Array of column to ignore. Starts from 0.
	 */
	public static void download(String url, String tblSelector, String filename, String separator, ArrayList<Integer> ignoreColumnIndexes) {
		try {
			org.jsoup.nodes.Document doc = Jsoup.connect(url).timeout(0).get();
			PrintWriter writer = new PrintWriter(filename, "UTF-8");

			Elements tbl = doc.select(tblSelector);

			if (tbl.isEmpty()) {
				System.out.println("Can't find any table with CSS selector: " + tblSelector);
				throw new IOException("Can't find any table with CSS selector");
			} else {
				/* Scrap table header data */
				Elements rows = tbl.select("tr");
				
				// Find out if the header is composed by th or td ?
				Elements headerCells = rows.get(0).select("th");
				headerCells = headerCells.isEmpty() ? rows.get(0).select("td") : headerCells;
				
				// Print the header out first
				for (int i = 0; i < headerCells.size(); i++) 
					if (!ignoreColumnIndexes.contains(i)) { // Ignore the column or not?
						writer.print(headerCells.get(i).text());
						if (i < headerCells.size() - 1) {
							writer.print(separator);
						}
					}
				writer.println();
				// Print out data
				for (int i = 1; i < rows.size(); i++) {
					Elements currentRow = rows.get(i).select("td");
					for (int j = 0; j < currentRow.size(); j++) 
						if (!ignoreColumnIndexes.contains(j)) { // Ignore the column or not?
							writer.print(currentRow.get(j).text());
							if (j < currentRow.size() - 1) {
								writer.print(separator);
							}
						}
					writer.println();
				}
			}
			writer.close();
			System.out.println("Saved table " + tblSelector + " data from " + url);		
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}
