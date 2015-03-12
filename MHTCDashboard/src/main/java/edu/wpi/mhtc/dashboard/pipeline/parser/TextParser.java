/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dashboard.pipeline.parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
import edu.wpi.mhtc.dashboard.pipeline.dao.Metric;
import edu.wpi.mhtc.dashboard.pipeline.dao.Statistic;
import edu.wpi.mhtc.dashboard.pipeline.data.CategoryException;
import edu.wpi.mhtc.dashboard.pipeline.parser.DataSource;
import edu.wpi.mhtc.dashboard.pipeline.parser.FileType;
import edu.wpi.mhtc.dashboard.pipeline.parser.IParser;
import edu.wpi.mhtc.dashboard.pipeline.parser.UnifiedFormatException;
import edu.wpi.mhtc.model.state.State;


/**
 * TextParser takes in a properly formatted CSV file to
 * gather data to put in the database
 * @author cakulhman
 * @version December 2014
 * 
 */
public class TextParser implements IParser {

	public DataSource source;
	private List<Statistic> lines;
	private CSVParser parser;
	private Integer stateColumnNum;
	private Integer yearColumnNum;
	private HashMap<String, Integer> columnNames;
	private long headerRecordNumber;
	List<Metric> metrics;
	List<State> states;
	/**
	 * 
	 * @param source
	 * @throws UnifiedFormatException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws Exception if the source is not a csv file
	 */
	
	public TextParser(DataSource source, List<Metric> metrics, List<State> states) throws Exception {
		
		if (source.getFileType() != FileType.csv){
			throw new UnifiedFormatException("Wrong file type for csv parser: "+source.getFileType());
		}
		
		this.source = source;
		parser = new CSVParser(new BufferedReader(new FileReader(source.getFile())), CSVFormat.DEFAULT);
		this.lines = new ArrayList<Statistic>();
		
		this.metrics = metrics;
		this.states = states;
		
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
		String cellValue = null;
		
		if(record.getRecordNumber()==headerRecordNumber){
			for(int i = 0 ; i<record.size() ; i++ ){
				cellValue = record.get(i);
				if(cellValue.equalsIgnoreCase("year") || cellValue.equalsIgnoreCase("state")){
					columnNames.put(cellValue, i);
				}
				else{
					validateHeaderName(cellValue);	//make sure valid metric
					columnNames.put(cellValue, i);
				}
			}
		}

	}
	
	/**
	 * Confirms if the header for a metric column in the spreadsheet
	 * matches one of the possible metric names for the given category
	 * @param cellValue
	 * @throws CategoryException if the metric header name(s) does not match the metrics for this category
	 */
	private void validateHeaderName(String cellValue) throws CategoryException {
		for (Metric m : metrics) {
			if (m.getName().equalsIgnoreCase(cellValue)) {
				return;
			}
		}
		
		// Metric wasn't found in the DB, let user know
		CategoryException c = new CategoryException("No metric in category \"" + cellValue + "\" matches metric \""+cellValue+"\".");
		c.setSolution("The possible metrics for category \"" + cellValue + "\" are:<ul>");
		
		for (Metric metric: metrics) {
			c.setSolution("<li>" + metric.getName() + "</li>");
		}
		
		c.setSolution("</ul>Please confirm that you are uploading the right metric to the right category.");
				
		throw c;
		
	}

	
	@Override
	public boolean parseAll() throws CategoryException, UnifiedFormatException {
		
		getHeaderColumnNames();
		
		yearColumnNum = columnNames.remove("year");
		stateColumnNum = columnNames.remove("state");
		
		NumericCleaner numCleaner = new NumericCleaner();
		StateCleaner stateCleaner = new StateCleaner(states);
		YearCleaner yearCleaner = new YearCleaner();
		Statistic line = null;
		String year = null;
		String state = null;
		String value = null;
		
		for(CSVRecord record : parser){
			
			for(String name: columnNames.keySet()){
				line = new Statistic();

				year = record.get(yearColumnNum);
				year = yearCleaner.clean(year);
				
				state = record.get(stateColumnNum);
				try {
					state = stateCleaner.clean(state);
				} catch (Exception e) {
					System.out.println("Could not parse line: " + record.getRecordNumber());
					System.out.println(record.toString());
				}
				
				value = record.get(name);

				try {
					value = numCleaner.clean(value);
				} catch (Exception e) {
					System.out.println("Could not parse line: " + record.getRecordNumber());
					System.out.println(record.toString());
				}
				
				Metric m = getMetric(name);
				line.setMetricID(m.getId());
				
				State s = getState(state);
				line.setStateName(s.getName());
				line.setStateID(s.getId());

				line.setYear(Integer.parseInt(year));
				line.setValue(Double.parseDouble(value));
				
				lines.add(line);
			}
		}
		return lines.isEmpty();
	}

	
	@Override
	public Iterator<Statistic> iterator() {
		return lines.iterator();
	}
	
	/**
	 * Retrieve a metric from the list of metrics by name
	 * @param name
	 * @return 
	 */
	public Metric getMetric(String name) {
		for (Metric m : metrics) {
			if (m.getName().equalsIgnoreCase(name)) {
				return m;
			}
		}
		return null;
	}
	
	/**
	 * Retrieve a state from the list of states by name
	 * @param stateName
	 * @return
	 */
	public State getState(String stateName) {
		for (State s : states) {
			if (s.getName().equalsIgnoreCase(stateName)) {
				return s;
			}
		}
		
		return null;
	}


}
