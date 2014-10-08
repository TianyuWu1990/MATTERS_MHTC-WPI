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
