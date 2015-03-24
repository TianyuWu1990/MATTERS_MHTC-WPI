/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.util.pipeline.wrappers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

/**
 * Wrapper for HTML tables.
 *
 */
public final class WebTableWrapper {
	private WebTableWrapper() {
		// Nothing here
	}

	/**
	 * HTML Table data scraper. Note that it can only work with ordinary HTML table tags <tr><th><td>
	 * Outputs a file with data values from the table separated by a string defined by "separator"
	 * @param url: The website URL
	 * @param tblSelector: CSS Selector for the table. This needs to be unique.
	 * @param filename: Excel file to save
	 * @param separator: String Separator for each data value. Preferred "," or " ".
	 * @param ignoreColumnIndexes: Array of column to ignore. Starts from 0.
	 */
	public static void download(String url, String tblSelector, String filename, List<Integer> list) {
		try {
			Document doc = Jsoup.connect(url).timeout(0).get();
			Elements tbl = doc.select(tblSelector);

			if (tbl.isEmpty()) {
				System.out.println("==== HTML content retrieved =====");
				System.out.println(doc);
				System.out.println("================================");
				throw new IOException("Can't find any table with CSS selector");
			} else {
				Workbook wb;
				wb = (filename.indexOf("xlsx") > 0) ? new XSSFWorkbook() : new HSSFWorkbook();
				Sheet sheet = wb.createSheet();
				
				/* Scrap table header data */
				Elements rows = tbl.select("tr");
				
				// Find out if the header is composed by th or td ?
				Elements headerCells = rows.get(0).select("th");
				headerCells = headerCells.isEmpty() ? rows.get(0).select("td") : headerCells;
				
				// Print the header out first
				int rowNum = 0;
				int cellNum = 0;
				Row row = sheet.createRow(rowNum);
				
				for (int i = 0; i < headerCells.size(); i++) 
					if (!list.contains(i)) { // Ignore the column or not?
						Cell cell = row.createCell(cellNum++);
						String val = headerCells.get(i).text();
						setCellValue(cell, val);
					}
				
				// Print out data
				rowNum = 1;
	
				for (int i = 1; i < rows.size(); i++) {
					cellNum = 0;
					row = sheet.createRow(rowNum++);
					Elements currentRow = rows.get(i).select("td");
					
					for (int j = 0; j < currentRow.size(); j++) 
						if (!list.contains(j)) { // Ignore the column or not?
							
							Cell cell = row.createCell(cellNum++);
							//We want to write strings and doubles formatted correctly in excel, so check if numeric value
							String val = currentRow.get(j).text();
							setCellValue(cell, val);
						}
				}
				
	            //Write the workbook in file system
	            FileOutputStream out = new FileOutputStream(new File(filename));
	            wb.write(out);
	            out.close();

				System.out.println("Saved table " + tblSelector + " data from " + url);		
			}
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
	
	/**
	 * Used for JavaScript-generated HTML tables
	 * @param url
	 * @param tblSelector must be the ID of the table, or else it won't work
	 * @param filename
	 * @param seconds how long to wait for the JavaScript to populate the page
	 * @throws FailingHttpStatusCodeException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static void downloadHtmlUnit(String url, String tblSelector, String filename, long seconds) 
			throws FailingHttpStatusCodeException, MalformedURLException, IOException 
	{
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		HtmlPage webPage = webClient.getPage(url);
		
		// This is the important part! For tables loaded via JS, you need to wait
		// until they have time to complete. For BLS, 3 * 1000 (3 sec) seems to work
		webClient.waitForBackgroundJavaScript(seconds * 1000);
		
		// Now, continue to get the table
		HtmlTable dataTable = webPage.getFirstByXPath("//*[@id=\""+tblSelector+"\"]");
		
		// Initialize variables for info
		Workbook wb;
		wb = (filename.indexOf("xlsx") > 0) ? new XSSFWorkbook() : new HSSFWorkbook();
		Sheet sheet = wb.createSheet();
		
		int rowNum = 0;

		for (final HtmlTableRow row: dataTable.getRows()) {
			System.out.println("Found row: "+row);
			
			// Create a row on the sheet
			Row sheetRow = sheet.createRow(rowNum++);
			
			// Init cell num
			int cellNum = 0;
			
			for (int i = 0; i < row.getCells().size(); i++) {
				// Get cell contents, convert to string
				HtmlTableCell cell = row.getCell(i);
				String val = cell.asText();
				
				// Create new cell for row
				Cell sheetCell = sheetRow.createCell(cellNum++);
				
				// Copy data from HTML table to sheet cell
				setCellValue(sheetCell, val);
			}
			
		}
		
        //Write the workbook in file system
        FileOutputStream out = new FileOutputStream(new File(filename));
        wb.write(out);
        out.close();

		System.out.println("Saved table " + tblSelector + " data from " + url);
		
	}

	
// parse type from string and write to excel workbook cell
	private static void setCellValue(Cell cell, String val) {
		try{
			double d = Double.parseDouble(val);
//value is numeric
			cell.setCellValue(d);
		}
//value is a string
		catch(NumberFormatException e){
			cell.setCellValue(val);
		}
	}
}
