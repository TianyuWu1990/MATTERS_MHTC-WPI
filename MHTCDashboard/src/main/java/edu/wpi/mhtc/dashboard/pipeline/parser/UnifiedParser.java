package edu.wpi.mhtc.dashboard.pipeline.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import edu.wpi.mhtc.dashboard.pipeline.data.FileData;
import edu.wpi.mhtc.dashboard.pipeline.data.LineData;
import edu.wpi.mhtc.dashboard.pipeline.main.Category;
import edu.wpi.mhtc.dashboard.pipeline.main.Line;
import edu.wpi.mhtc.dashboard.pipeline.main.UnifiedDatasource;

public class UnifiedParser implements IParser {
	
	private Category category;
	private ArrayList<String> columnNames;
	private ArrayList<Line> lines;
	private XSSFWorkbook workbook;
	private int startRow;
	private int endRow;

//	exception comes from category creation
	public UnifiedParser(UnifiedDatasource source) throws Exception {
		
		this.columnNames = new ArrayList<String>();
		this.lines = new ArrayList<Line>();
		this.workbook = (XSSFWorkbook) WorkbookFactory.create(source.getFile());
		this.category = new Category(source.getCategoryID());
		this.getStartAndEndRow();
		
	}
	
//	for now, start on row 2. Assume row 0 is a title, row 1 is header
	private void getStartAndEndRow() {
		
			Sheet sheet = this.workbook.getSheetAt(0);
			this.startRow = 2;
			this.endRow = sheet.getLastRowNum() + 1;

	}
	
//	could have a check that column names match metrics for this category here
	@Override
	public List<String> getColumnNames() {
		if (this.columnNames.isEmpty()) {
			Sheet sheet = this.workbook.getSheetAt(0);
			Row row = sheet.getRow(this.startRow - 1);
			Cell cell = null;
			for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
				cell = row.getCell(j);
				if (cell.getCellType() == 1) {
					this.columnNames.add(cell.getStringCellValue()
							.toLowerCase());
				}
			}
		}
		return this.columnNames;
	}

	
//	placeholder for now
	@Override
	public FileData parseAll() {
		return new FileData(null);
	}
	
	
//	this populates the lines list with Line objects. Then can call getLineIterator() to access them.
//	TODO: data should be cleaned before it is parsed?? Where/when does that happen?
	public void parseAll(int placeholder){

		getColumnNames();
		lines.clear();

		for (int i = 0; i < this.workbook.getNumberOfSheets(); i++) {
			Sheet sheet = this.workbook.getSheetAt(i);
			
			Line line = null;
			for (int j = this.startRow; j < this.endRow; j++) {
				
				Row row = sheet.getRow(j);
				Cell cell;
				
				if (containsData(row)){
					
					line = new Line();
					for (int k = row.getFirstCellNum(); k < row.getLastCellNum(); k++) {
						cell = row.getCell(k);
						if (cell != null){
							
							String columnName = columnNames.get(k);
							
							if(columnName.equalsIgnoreCase("year")){
								if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
									line.setYear(String.valueOf((int) cell.getNumericCellValue()));
								}
								else if (cell.getCellType() == Cell.CELL_TYPE_STRING){
									line.setYear(cell.getStringCellValue());
								}
							}
							
							else if(columnName.equalsIgnoreCase("state") && (cell.getCellType() == Cell.CELL_TYPE_STRING)){
								line.setState(cell.getStringCellValue());
							}
							
							else{
								int id = category.getMetricID(columnName);
								Float value = null;
								switch (cell.getCellType()) {
								
								case Cell.CELL_TYPE_NUMERIC:
									value = (float) cell.getNumericCellValue();
									break;
									
								case Cell.CELL_TYPE_STRING:
									value = Float.parseFloat(cell.getStringCellValue());
									break;
								case Cell.CELL_TYPE_BOOLEAN:
//									TODO: not sure what values boolean metrics have in db, if they are ever stored
//									value =
									break;
								default:
									break;
								}
								if(value != null)
									line.addMetric(columnName, id, value);
							}
						}
					} 
					if(line.isValid())
						lines.add(line);
				}
			}
		}
	}
				
		
	




	
	public Iterator<Line> getLineIterator(){
		return lines.iterator();
	}
	
	
	private boolean containsData(Row row){
		
		if (row != null ) {
			Cell cell = row.getCell(0);
			
			boolean isString = cell.getCellType() == Cell.CELL_TYPE_STRING 
					&& cell.getStringCellValue().trim().length() != 0;
			boolean isNumeric = cell.getCellType() == Cell.CELL_TYPE_NUMERIC;
			
			return cell != null && (isString || isNumeric);
		}
		else 
			return false;
	}

	@Override
	public Iterator<LineData> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	
	
	
	

	
	
	

}
