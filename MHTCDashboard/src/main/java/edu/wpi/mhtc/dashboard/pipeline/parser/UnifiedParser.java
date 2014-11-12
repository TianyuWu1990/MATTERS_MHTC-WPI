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
		
		if(workbook == null){
			throw new UnifiedFormatException("bad workbook");
		}
		
		this.category = source.getCategory();		
		this.sheet = workbook.getSheetAt(0);	//files in unified format only have one sheet

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
	public Iterator<Line> iterator(){
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
	public void validateMetrics(Category category) throws CategoryException {
		
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
	
	/**
	 * Simple getter for lines, which contains all data tuples
	 * @return
	 */
	public ArrayList<Line> getLines() {
		return lines;
	}

}
