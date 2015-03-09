/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dashboard.pipeline.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import edu.wpi.mhtc.dashboard.pipeline.cleaner.NumericCleaner;
import edu.wpi.mhtc.dashboard.pipeline.cleaner.StateCleaner;
import edu.wpi.mhtc.dashboard.pipeline.cleaner.YearCleaner;
import edu.wpi.mhtc.dashboard.pipeline.dao.Metric;
import edu.wpi.mhtc.dashboard.pipeline.dao.Statistic;
import edu.wpi.mhtc.dashboard.pipeline.data.CategoryException;
import edu.wpi.mhtc.dashboard.pipeline.data.DataSource;
import edu.wpi.mhtc.dashboard.pipeline.data.FileType;
import edu.wpi.mhtc.model.state.State;
import edu.wpi.mhtc.persistence.StateMapper;

public class UnifiedParser implements IParser {
		
	List<edu.wpi.mhtc.dashboard.pipeline.dao.Metric> metrics;
	List<State> states;

	HashMap<String, Integer> columnNames;	//holds column names from header in order
	ArrayList<Statistic> lines;
	Workbook workbook;
	Sheet sheet;			//files in unified format only have one sheet
	DataSource source;
	int headerRow;			//files in unified format have a header with columns "year" and "state" and one or more metric names

	/**
	 * 
	 * @param source
	 * @param metrics 
	 * @throws UnifiedFormatException if file does not conform to Unified Format.
	 * @throws CategoryException if metrics defined in file are not associated with category in the database.
	 * @throws IOException 
	 * @throws InvalidFormatException 
	 */
	public UnifiedParser(DataSource source, List<Metric> metrics, List<State> states) throws UnifiedFormatException, CategoryException, InvalidFormatException, IOException{

		if (source.getFileType() == FileType.xlsx) {
			this.workbook = (XSSFWorkbook) WorkbookFactory.create(
					source.getFile());
		}
		else if (source.getFileType() == FileType.xls) {
			this.workbook = WorkbookFactory.create(source.getFile());
		}
		else{
			throw new UnifiedFormatException("File must be in excel format, but file type was: "+ source.getFileType());
		}
		
		if(workbook == null){
			throw new UnifiedFormatException("bad workbook");
		}
		
		this.source = source;
		
		this.sheet = workbook.getSheetAt(0);	//files in unified format only have one sheet
		
		this.metrics = metrics;
		
		this.headerRow = findHeader();						//find file header, make sure has year and state
				
		this.columnNames = new HashMap<String, Integer>();
		getHeaderColumnNames();					// Get header column names, and validate if it is a metric
		
		this.states = states;
		
	}

	
	//	this populates the lines list with Line objects. Then can call getLineIterator() to access them.
	@Override
	public boolean parseAll() throws Exception{

		YearCleaner yearCleaner = new YearCleaner();
		StateCleaner stateCleaner = new StateCleaner();
		NumericCleaner numCleaner = new NumericCleaner();

		lines = new ArrayList<Statistic>();	
		Statistic line = null;
		int stateCol = columnNames.remove("state");
		int yearCol = columnNames.remove("year");
		for (Row row : sheet) {

			if(row.getRowNum() > headerRow){	//skip extra info above header
				int i = row.getRowNum();
				if (!isRowEmpty(row)){		

					line = new Statistic();

					String state = null;
					String year = null;

//					get state field
					Cell stateCell = row.getCell(stateCol);
					if(stateCell != null){
						try {
							state = stateCleaner.clean(stateCell.getStringCellValue());
						} 
						catch (Exception e) {
							//	TODO: use this to report error to admin
							e.printStackTrace();
						}
					}

//					get year field
					Cell yearCell = row.getCell(yearCol);
					if(yearCell != null){
						try{
							if(yearCell.getCellType() == Cell.CELL_TYPE_STRING){
								year = yearCleaner.clean(yearCell.getStringCellValue());		//clean year
							}
							else{
								year = yearCleaner.clean(yearCell.getNumericCellValue());
							}
						}
						catch(Exception e){
							//TODO: use this to report error to admin
							e.printStackTrace();
						}
					}

					if(year == null || state == null){
						System.out.println("Bad Row "+row.getRowNum() );
						break;
					}
					
					State s = getState(state);
					
					line.setStateName(s.getName());
					line.setStateID(s.getId());
					line.setYear(Integer.parseInt(year));

//					get metric fields
					for (String name: columnNames.keySet()) {
						
						Cell cell = row.getCell(columnNames.get(name));
						if(cell != null){

							edu.wpi.mhtc.dashboard.pipeline.dao.Metric m = getMetric(name);
							String value = null;

							switch (cell.getCellType()) {

							case Cell.CELL_TYPE_NUMERIC:
								value = String.valueOf(cell.getNumericCellValue());
								break;

							case Cell.CELL_TYPE_STRING:
								value = cell.getStringCellValue();
								break;

							case Cell.CELL_TYPE_BOOLEAN:
								//	TODO: not sure what values boolean metrics have in db, if they are ever stored
								break;

							default:
								break;
							}

							if(value != null){
								try{
									String cleanedValue = numCleaner.clean(value);
//									TODO: don't want to use -1 to check this, could be actual value
									if(cleanedValue.equals(Integer.toString(-1))){
//										invalid data
										System.out.println("bad data "+value+", line skipped");
										break;
									}
									
									line.setMetricID(m.getId());
									line.setValue(Double.parseDouble(cleanedValue));
																		
									if (line.isValid())
										lines.add(line);
								}
								catch(Exception e){
									//	TODO: use this to report error to admin
								}
								
							}
						}
					}
				} 

			}
		}
		return lines.isEmpty();
	}

	
	
//	Once source has been parsed, can iterate over the lines to be added to db
	@Override
	public Iterator<Statistic> iterator() {
		return lines.iterator();
	}

	/**
	 * 
	 * @return the row number of the header
	 * @throws UnifiedFormatException if a header row containing column headers for "state" and "year" is not found
	 */
	int findHeader() throws UnifiedFormatException{
		
		boolean foundYear = false;
		boolean foundState = false;
		
		for (Row row : sheet) {
			for (Cell cell : row) {
				if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					String cellValue = cell.getRichStringCellValue().getString().trim();
					
					if(cellValue.equalsIgnoreCase("year")){
						foundYear = true;
					}
					else if(cellValue.equalsIgnoreCase("state")){
						foundState = true;
					}
				}
			}
			if(foundState && foundYear){
				return row.getRowNum();  
			}
		}
		throw new UnifiedFormatException("No header found!!");
	}


	boolean isRowEmpty(Row row) {
//		int i = row.getPhysicalNumberOfCells();
//		return  i == 0;
		for (int c = row.getFirstCellNum(); c <= row.getLastCellNum(); c++) {
			Cell cell = row.getCell(c);
			if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
			
				return false;
		}
		
		return true;
	}

	/**
	 * @throws CategoryException if the metric header names do not match the metrics for this category
	 */
	@Override
	public void getHeaderColumnNames() throws CategoryException {
				
		for (Cell cell : sheet.getRow(headerRow)) {
			if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				String cellValue = cell.getRichStringCellValue().getString().trim();
				
				if(cellValue.equalsIgnoreCase("year") || cellValue.equalsIgnoreCase("state")){
					columnNames.put(cellValue.toLowerCase(), cell.getColumnIndex());
				} else {
					validateHeaderName(cellValue);
					columnNames.put(cellValue.toLowerCase(), cell.getColumnIndex());
				}
					
			}
		}
	}
	
	private void validateHeaderName(String cellValue) throws CategoryException {
		for (edu.wpi.mhtc.dashboard.pipeline.dao.Metric m : metrics) {
			if (m.getName().equalsIgnoreCase(cellValue)) {
				return;
			}
		}
		
		// Metric wasn't found in the DB, let user know
		CategoryException c = new CategoryException("No metric in category \"" + cellValue + "\" matches metric \""+cellValue+"\".");
		c.setSolution("The possible metrics for category \"" + cellValue + "\" are:<ul>");
		
		for (edu.wpi.mhtc.dashboard.pipeline.dao.Metric metric: metrics) {
			c.setSolution("<li>" + metric.getName() + "</li>");
		}
		
		c.setSolution("</ul>Please confirm that you are uploading the right metric to the right category.");
				
		throw c;
		
	}
	
	public edu.wpi.mhtc.dashboard.pipeline.dao.Metric getMetric(String name) {
		for (edu.wpi.mhtc.dashboard.pipeline.dao.Metric m : metrics) {
			if (m.getName().equalsIgnoreCase(name)) {
				return m;
			}
		}
		return null;
	}
	
	public State getState(String stateName) {
		for (State s : states) {
			if (s.getName().equalsIgnoreCase(stateName)) {
				return s;
			}
		}
		
		return null;
	}


	/**
	 * Simple getter for lines, which contains all data tuples
	 * @return
	 */
	public ArrayList<Statistic> getLines() {
		return lines;
	}

}
