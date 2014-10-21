<<<<<<< HEAD
package edu.wpi.mhtc.dashboard.pipeline.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import edu.wpi.mhtc.dashboard.pipeline.cleaner.NumericCleaner;
import edu.wpi.mhtc.dashboard.pipeline.cleaner.StateCleaner;
import edu.wpi.mhtc.dashboard.pipeline.cleaner.YearCleaner;
import edu.wpi.mhtc.dashboard.pipeline.data.Category;
import edu.wpi.mhtc.dashboard.pipeline.data.CategoryException;
import edu.wpi.mhtc.dashboard.pipeline.data.FileData;
import edu.wpi.mhtc.dashboard.pipeline.data.Line;
import edu.wpi.mhtc.dashboard.pipeline.data.LineData;
import edu.wpi.mhtc.dashboard.pipeline.data.Metric;
import edu.wpi.mhtc.dashboard.pipeline.main.UnifiedDatasource;

public class UnifiedParser implements IParser {

	private ArrayList<String> columnNames;	//holds column names from header in order
	private ArrayList<Line> lines;
	private XSSFWorkbook workbook;
	private Sheet sheet;			//files in unified format only have one sheet
	private Category category;
	private int headerRow;			//files in unified format have a header with columns "year" and "state" and one or more metric names
	private int startRow;
	private int endRow;

	/**
	 * 
	 * @param source
	 * @throws UnifiedFormatException if file does not conform to Unified Format.
	 * @throws CategoryException if metrics defined in file are not associated with it's category in the database.
	 * @throws IOException 
	 * @throws InvalidFormatException 
	 */
	public UnifiedParser(UnifiedDatasource source) throws UnifiedFormatException, CategoryException, InvalidFormatException, IOException{

		this.workbook = (XSSFWorkbook) WorkbookFactory.create(source.getFile());

		this.sheet = workbook.getSheetAt(0);	//files in unified format only have one sheet

		this.headerRow = findHeader();						//find file header

		this.category = source.getCategory();

		validateMetricNames(category);			//verify that the metric column names match the metrics for this source's category
		//also sets the columnNames

		this.lines = new ArrayList<Line>();
		this.getStartAndEndRow(headerRow);

	}





	//	this populates the lines list with Line objects. Then can call getLineIterator() to access them.
	//	TODO: data should be cleaned before it is parsed?? Where/when does that happen?
	public void parseAll(int placeholder) throws Exception{

		YearCleaner yearCleaner = new YearCleaner();
		StateCleaner stateCleaner = new StateCleaner();
		NumericCleaner numCleaner = new NumericCleaner();

		lines.clear();		
		Line line = null;
		for (Row row : sheet) {

			if (!isRowEmpty(row)){
				line = new Line();
				for (Cell cell : row) {
					if (cell != null){

						String columnName = columnNames.get(cell.getColumnIndex());

						if(columnName.equalsIgnoreCase("year")){	
							//								
							if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_STRING){
								try{
									String cleanedYear = yearCleaner.clean(cell.getNumericCellValue());		//clean year
									line.setYear(cleanedYear);												//add to line
								}
								catch(Exception e){
									//TODO: use this to report error to admin
									//should define yearException? or general CleaningException? or DataFormatException?
									e.printStackTrace();
								}
							}
						}

						else if(columnName.equalsIgnoreCase("state") && (cell.getCellType() == Cell.CELL_TYPE_STRING)){
							try {
								String cleanedState = stateCleaner.clean(cell.getStringCellValue());
								line.setState(cleanedState);
							} 
							catch (Exception e) {
								//	TODO: use this to report error to admin
								e.printStackTrace();
							}
						}

						else{

							Metric metric = category.getMetric(columnName);

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
									metric.setValue(Float.parseFloat(cleanedValue));
									line.addMetric(metric);
									metric.clearValue();
								}
								catch(Exception e){
									//	TODO: use this to report error to admin
								}
							}
						}
					}
				} 
				if(line.isValid())
					lines.add(line);
			}
		}
	}




	public Iterator<Line> getLineIterator(){
		return lines.iterator();
	}


	private int findHeader() throws UnifiedFormatException{
		for (Row row : sheet) {
			for (Cell cell : row) {
				if (cell.getCellType() == Cell.CELL_TYPE_STRING) {

					if (cell.getRichStringCellValue().getString().trim().equalsIgnoreCase("year") 
							|| cell.getRichStringCellValue().getString().trim().equalsIgnoreCase("state")) {

						return row.getRowNum();  
					}
				}
			}
		}
		throw new UnifiedFormatException("No header found!!");
	}



	private void getStartAndEndRow(int headerRow) {

		while (isRowEmpty(sheet.getRow(headerRow))){
			headerRow++;
		}
		this.startRow = headerRow + 1;
		this.endRow = sheet.getLastRowNum() + 1;

	}



	private boolean isRowEmpty(Row row) {
		if (row.getLastCellNum() == -1) {
			return true;
		}
		
		/*for (int c = row.getFirstCellNum(); c <= row.getLastCellNum(); c++) {
			Cell cell = row.getCell(c);
			if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
				return false;
		}*/
		
		return false;
	}

	private void validateMetricNames(Category category) throws CategoryException{
		Row row = sheet.getRow(headerRow);
		for (Cell cell : row) {
			if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				String metricName = cell.getStringCellValue().trim().toLowerCase();
				category.getMetric(metricName);
				this.columnNames.add(metricName);
			}
		}		
	}	

	//	placeholders
	@Override
	public Iterator<LineData> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public FileData parseAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<String> getColumnNames() {
		return this.columnNames;
	}

}
=======
package edu.wpi.mhtc.dashboard.pipeline.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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
import edu.wpi.mhtc.dashboard.pipeline.data.Category;
import edu.wpi.mhtc.dashboard.pipeline.data.CategoryException;
import edu.wpi.mhtc.dashboard.pipeline.data.FileType;
import edu.wpi.mhtc.dashboard.pipeline.data.Line;
import edu.wpi.mhtc.dashboard.pipeline.data.Metric;
import edu.wpi.mhtc.dashboard.pipeline.data.UnifiedDataSource;

public class UnifiedParser implements IParser {

	HashMap<String, Integer> columnNames;	//holds column names from header in order
	ArrayList<Line> lines;
	Workbook workbook;
	Sheet sheet;			//files in unified format only have one sheet
	Category category;
	int headerRow;			//files in unified format have a header with columns "year" and "state" and one or more metric names

	/**
	 * 
	 * @param source
	 * @throws UnifiedFormatException if file does not conform to Unified Format.
	 * @throws CategoryException if metrics defined in file are not associated with category in the database.
	 * @throws IOException 
	 * @throws InvalidFormatException 
	 */
	public UnifiedParser(UnifiedDataSource source) throws UnifiedFormatException, CategoryException, InvalidFormatException, IOException{

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
		
		
		this.category = source.getCategory();
		this.sheet = workbook.getSheetAt(workbook.getActiveSheetIndex());	//files in unified format only have one sheet

		this.headerRow = findHeader();						//find file header, make sure has year and state
		validateMetrics(category);		//verify that the metric column names match the metrics for this source's category

	}

	
	//	this populates the lines list with Line objects. Then can call getLineIterator() to access them.
	@Override
	public boolean parseAll() throws Exception{

		YearCleaner yearCleaner = new YearCleaner();
		StateCleaner stateCleaner = new StateCleaner();
		NumericCleaner numCleaner = new NumericCleaner();

		lines = new ArrayList<Line>();	
		Line line = null;
		int stateCol = columnNames.remove("state");
		int yearCol = columnNames.remove("year");
		for (Row row : sheet) {

			if(row.getRowNum() > headerRow){	//skip extra info above header

				if (!isRowEmpty(row)){		

					String state = null;
					String year = null;

//					get state field
					Cell stateCell = row.getCell(stateCol);
					try {
						state = stateCleaner.clean(stateCell.getStringCellValue());
					} 
					catch (Exception e) {
						//	TODO: use this to report error to admin
						e.printStackTrace();
					}

//					get year field
					Cell yearCell = row.getCell(yearCol);
					
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

					if(year == null || state == null){
						System.out.println("Bad Row "+row.getRowNum() );
						break;
					}

					
//					get metric fields
					for (String name: columnNames.keySet()) {
						line = new Line();
						line.setState(state);
						line.setYear(year);

						Cell cell = row.getCell(columnNames.get(name));
						if(cell != null){

							Metric metric = category.getMetric(name);
							metric.clearValue();

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
									metric.setValue(Float.parseFloat(cleanedValue));
									line.addMetric(metric);
									if(line.isValid())
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
	public Iterator<Line>  iterator(){
		return lines.iterator();
	}


	
	/**
	 * 
	 * @return the row number of the header
	 * @throws UnifiedFormatException if a header row containing column headers for "state" and "year" is not found
	 */
	private int findHeader() throws UnifiedFormatException{
		
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



//TODO: test this
	private boolean isRowEmpty(Row row) {
		if (row.getLastCellNum() == -1) {
			return true;
		}
		
		/*for (int c = row.getFirstCellNum(); c <= row.getLastCellNum(); c++) {
			Cell cell = row.getCell(c);
			if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
				return false;
		}*/
		
		return false;
	}


	/**
	 * @throws CategoryException if the metric header names do not match the metrics for this category
	 */
	@Override
	public void validateMetrics(Category category) throws CategoryException{
		
		columnNames = new HashMap<String, Integer>();
		for (Cell cell : sheet.getRow(headerRow)) {
			if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				String cellValue = cell.getRichStringCellValue().getString().trim();
				if(cellValue.equalsIgnoreCase("year") || cellValue.equalsIgnoreCase("state")){
					columnNames.put(cellValue, cell.getColumnIndex());
				}
				else{
					category.getMetric(cellValue);	//make sure valid metric
					columnNames.put(cellValue, cell.getColumnIndex());
				}
			}
		}
	}

}
>>>>>>> origin/dev-backend-pipeline-refactoring
