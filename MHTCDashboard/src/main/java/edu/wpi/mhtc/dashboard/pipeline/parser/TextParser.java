/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dashboard.pipeline.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import edu.wpi.mhtc.dashboard.pipeline.cleaner.NumericCleaner;
import edu.wpi.mhtc.dashboard.pipeline.cleaner.StateCleaner;
import edu.wpi.mhtc.dashboard.pipeline.cleaner.YearCleaner;
import edu.wpi.mhtc.dashboard.pipeline.dao.Statistic;
import edu.wpi.mhtc.dashboard.pipeline.data.CategoryException;
import edu.wpi.mhtc.dashboard.pipeline.data.DataSource;
import edu.wpi.mhtc.dashboard.pipeline.data.FileType;
import edu.wpi.mhtc.dashboard.pipeline.data.Line;
import edu.wpi.mhtc.dashboard.pipeline.data.Metric;

public class TextParser implements IParser {

	public DataSource source;
	private List<Statistic> lines;
	private CSVParser parser;
	private ArrayList<Metric> metrics;
	private Integer stateColumnNum;
	private Integer yearColumnNum;
	private HashMap<String, Integer> columnNames;
	private long headerRecordNumber;
	/**
	 * 
	 * @param source
	 * @throws Exception if the source is not a csv file
	 */
	public TextParser(DataSource source) throws Exception {
		
		if (source.getFileType() != FileType.csv){
			throw new Exception("Wrong file type for csv parser: "+source.getFileType());
		}
		
		this.source = source;
		parser = new CSVParser(new BufferedReader(new FileReader(source.getFile())), CSVFormat.DEFAULT);
		this.lines = new ArrayList<Statistic>();
		
	}
	
	/**
	 * 
	 * @return the row number of the header
	 * @throws UnifiedFormatException if a header row containing column headers for "state" and "year" is not found
	 */
	CSVRecord findHeader() throws UnifiedFormatException{
		
		boolean foundYear = false;
		boolean foundState = false;
		
		for(CSVRecord record : parser){
			record.toMap();
			for (String cellValue : record) {
				if(cellValue.equalsIgnoreCase("year")){
					foundYear = true;
				}
				else if(cellValue.equalsIgnoreCase("state")){
					foundState = true;
				}
			}
			if(foundState && foundYear){
				return record;
			}
		}
		throw new UnifiedFormatException("No header found!!");
	}



//TODO: test this
	boolean isRecordEmpty(CSVRecord record) {
		return record.size()==0;
	}


	/**
	 * @throws CategoryException if the metric header names do not match the metrics for this category
	 * @throws UnifiedFormatException 
	 */
	@Override
	public void getHeaderColumnNames() throws CategoryException, UnifiedFormatException{

		columnNames = new HashMap<String, Integer>();
		CSVRecord record = findHeader();
		if(record.getRecordNumber()==headerRecordNumber){
			for(int i = 0 ; i<record.size() ; i++ ){
				String cellValue = record.get(i);
				if(cellValue.equalsIgnoreCase("year") || cellValue.equalsIgnoreCase("state")){
					columnNames.put(cellValue, i);
				}
				else{
					source.getCategory().getMetric(cellValue);	//make sure valid metric
					columnNames.put(cellValue, i);
				}
			}
		}

	}
	

	
	@Override
	public boolean parseAll() throws CategoryException, UnifiedFormatException {
		
		
		getHeaderColumnNames();
		
		yearColumnNum = columnNames.remove("year");
		stateColumnNum = columnNames.remove("state");
		
		NumericCleaner numCleaner = new NumericCleaner();
		StateCleaner stateCleaner = new StateCleaner();
		YearCleaner yearCleaner = new YearCleaner();
		
		for(CSVRecord record : parser){
			
			for(Metric m : metrics){
				Statistic line = new Statistic();
				
				String year = record.get(yearColumnNum);
				line.setYear(Integer.parseInt(yearCleaner.clean(year)));
				
				String state = record.get(stateColumnNum);
				try {
					line.setStateName(stateCleaner.clean(state));
				} catch (Exception e) {
					System.out.println("Could not parse line: " + record.getRecordNumber());
					System.out.println(record.toString());
				}
				
				String value = record.get(m.getName());
				try {
					value = numCleaner.clean(value);
				} catch (Exception e) {
					System.out.println("Could not parse line: " + record.getRecordNumber());
					System.out.println(record.toString());
				}
				m.setValue(Float.parseFloat(value)); 
				
				line.addMetric(m);
				lines.add(line);
			}
		}
		return lines.isEmpty();
	}

	
	@Override
	public Iterator<Statistic> iterator() {
		return lines.iterator();
	} 

}
