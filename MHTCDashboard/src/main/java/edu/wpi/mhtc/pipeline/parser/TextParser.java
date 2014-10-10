package edu.wpi.mhtc.pipeline.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import edu.wpi.mhtc.pipeline.cleaner.NumericCleaner;
import edu.wpi.mhtc.pipeline.cleaner.StateCleaner;
import edu.wpi.mhtc.pipeline.cleaner.YearCleaner;
import edu.wpi.mhtc.pipeline.data.Category;
import edu.wpi.mhtc.pipeline.data.CategoryException;
import edu.wpi.mhtc.pipeline.data.DataSource;
import edu.wpi.mhtc.pipeline.data.FileType;
import edu.wpi.mhtc.pipeline.data.Line;
import edu.wpi.mhtc.pipeline.data.Metric;

public class TextParser implements IParser {

	public DataSource source;
	private List<Line> lines;
	private CSVParser parser;
	private ArrayList<Metric> metrics;
	private Integer stateColumnNum;
	private Integer yearColumnNum;
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
		parser = new CSVParser(new BufferedReader(new FileReader(source.getFileName())), CSVFormat.DEFAULT);
		this.lines = new ArrayList<Line>();
		
	}
	
	/**
	 *  
	 * @throws Category exception if the column names do not match the metrics in the the db.
	 */
	@Override
	public void validateMetrics(Category category) throws CategoryException{
		
		Map<String,Integer> map = parser.getHeaderMap();
		
		stateColumnNum = map.remove("state");
		if( stateColumnNum == null){
			
			stateColumnNum = map.remove("State");
			
			if( stateColumnNum == null){
				throw new CategoryException("no state for this category");
			}
		}
		
		yearColumnNum = map.remove("year");
		
		if(yearColumnNum == null){
			
			yearColumnNum = map.remove("Year");
			
			if(yearColumnNum == null){
				throw new CategoryException("no year for this category");
			}
		}
		
		metrics = new ArrayList<Metric>();
		for(String s : map.keySet()){
			metrics.add(source.getCategory().getMetric(s));
		}
	}

	
	@Override
	public boolean parseAll() throws CategoryException {
		
		this.validateMetrics(source.getCategory());
		NumericCleaner numCleaner = new NumericCleaner();
		StateCleaner stateCleaner = new StateCleaner();
		YearCleaner yearCleaner = new YearCleaner();
		
		for(CSVRecord record : parser){
			
			for(Metric m : metrics){
				Line line = new Line();
				
				String year = record.get(yearColumnNum);
				line.setYear(yearCleaner.clean(year));
				
				String state = record.get(stateColumnNum);
				try {
					line.setState(stateCleaner.clean(state));
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
	public Iterator<Line> iterator() {
		return lines.iterator();
	} 

}
