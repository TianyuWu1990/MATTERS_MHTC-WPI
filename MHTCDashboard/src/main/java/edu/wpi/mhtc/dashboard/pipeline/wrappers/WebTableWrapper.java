package edu.wpi.mhtc.dashboard.pipeline.wrappers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public final class WebTableWrapper {
	private WebTableWrapper() {
		// Nothing here
	}

	/**
	 * HTML Table data scrapper. Note that it can only work with ordinary HTML table tages <tr><th><td>
	 * Outputs a file with data values from the table separated by a string defined by "separator"
	 * @param url: The website URL
	 * @param tblSelector: CSS Selector for the table. This needs to be unique.
	 * @param filename: Excel file to save
	 * @param separator: String Separator for each data value. Preferred "," or " ".
	 * @param ignoreColumnIndexes: Array of column to ignore. Starts from 0.
	 */
	public static void download(String url, String tblSelector, String filename, List<Integer> list) {
		try {
			org.jsoup.nodes.Document doc = Jsoup.connect(url).timeout(0).get();
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
						cell.setCellValue(headerCells.get(i).text());
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
							cell.setCellValue(currentRow.get(j).text());
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
}
