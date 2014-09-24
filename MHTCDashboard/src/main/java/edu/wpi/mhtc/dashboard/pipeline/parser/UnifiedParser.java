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

import edu.wpi.mhtc.dashboard.pipeline.data.FileData;
import edu.wpi.mhtc.dashboard.pipeline.data.LineData;
import edu.wpi.mhtc.dashboard.pipeline.fileInfo.FileInfo;
import edu.wpi.mhtc.dashboard.pipeline.main.Line;
import edu.wpi.mhtc.dashboard.pipeline.main.UnifiedDatasource;

public class UnifiedParser implements IParser {
	
	private ArrayList<String> columnNames;
	private ArrayList<Line> lines;
	private XSSFWorkbook workbook;
	private int startRow;
	private int endRow;

	public UnifiedParser(UnifiedDatasource unifiedSource) throws InvalidFormatException, IOException {
		
		this.columnNames = new ArrayList<String>();
		this.lines = new ArrayList<Line>();
		this.workbook = (XSSFWorkbook) WorkbookFactory.create(unifiedSource.getFile());
		this.getStartAndEndRow();
		
	}
	
//	for now, start on row 2. Assume row 0 is a title, row 1 is header
	private void getStartAndEndRow() {
		
			Sheet sheet = this.workbook.getSheetAt(0);
			this.startRow = 2;
			this.endRow = sheet.getLastRowNum() + 1;

	}

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
			for (int j = this.startRow; j < this.endRow; j++) {
				
				Row row = sheet.getRow(j);
				Line line;
				Cell cell;
				if (containsData(row)){

					line = new Line();
					for (int k = row.getFirstCellNum(); k < row.getLastCellNum(); k++) {
						cell = row.getCell(k);
						String columnName = columnNames.get(k);
						if (cell == null) {
							line.put(columnName, "NULL");
						} 
						else {
							switch (cell.getCellType()) {
							
							case Cell.CELL_TYPE_NUMERIC:
								double value = cell.getNumericCellValue();
								if (value % 1 == 0) {
									line.put(columnName,
											Integer.toString((int) value));
								} else {
									line.put(columnName, Double.toString(value));
								}

								break;
							case Cell.CELL_TYPE_STRING:
								line.put(columnName, cell.getStringCellValue());
								break;
								
//								ck-shouldn't this be 'null' not blank? what does blank mean?
							case Cell.CELL_TYPE_BLANK:
								line.put(columnName, "BLANK");
								break;
								
//								ck -added check for boolean
							case Cell.CELL_TYPE_BOOLEAN:
								line.put(columnName, String.valueOf(cell.getBooleanCellValue()));
								
							case Cell.CELL_TYPE_ERROR:
								line.put(columnName,
										Byte.toString(cell.getErrorCellValue()));
								break;
							default:
								break;
							}
						}
					}
					lines.add(line);
				}
			}
		}
	}



	@Override
	public Iterator<LineData> iterator() {
		return new ExcelIterator();
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

	
	
	
	
	
	
	

	
	
	private class ExcelIterator implements Iterator<LineData> {

		private List<String> columnNames;
		private Workbook workbook;
		private FileInfo fileInfo;
		private int startRow;
		private int endRow;
		private int currentRow;

//		public ExcelIterator() {
//			this.fileInfo = ExcelParser.this.fileInfo;
//			this.columnNames = ExcelParser.this.getColumnNames();
//			this.init();
//		}

		@Override
		public boolean hasNext() {
			return (this.currentRow <= this.endRow);
		}

		@Override
		public LineData next() {
			Sheet sheet = this.workbook.getSheetAt(0);
			Row row = sheet.getRow(this.currentRow - 1);
			Cell cell = null;
			HashMap<String, String> map = new HashMap<String, String>();
			for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
				cell = row.getCell(i);
				String rowName = this.columnNames.get(i);
				if (cell == null) {
					map.put(rowName, "NULL");
				} else {
					switch (cell.getCellType()) {
					case 0:
						map.put(rowName,
								Double.toString(cell.getNumericCellValue()));
						break;
					case 1:
						map.put(rowName, cell.getStringCellValue());
						break;
					case 3:
						map.put(rowName, "BLANK");
						break;
					case 5:
						map.put(rowName,
								Byte.toString(cell.getErrorCellValue()));
						break;
					default:
						break;
					}
				}
			}
			this.currentRow++;
			return new LineData(map, this.fileInfo);
		}

		@Override
		public void remove() {
		}

		
		
		
//		private void init() {
//			try {
//				if (ExcelParser.this.fileInfo.getFileType() == FileType.xlsx) {
//					this.workbook = (XSSFWorkbook) WorkbookFactory
//							.create(new File(ExcelParser.this.fileInfo
//									.getFileName()));
//				}
//				if (ExcelParser.this.fileInfo.getFileType() == FileType.xls) {
//					this.workbook = WorkbookFactory.create(new File(
//							ExcelParser.this.fileInfo.getFileName()));
//				}
//			} catch (InvalidFormatException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			this.getStartAndEndRow();
//			this.currentRow = this.startRow;
//			if (this.columnNames.isEmpty()) {
//				Sheet sheet = this.workbook.getSheetAt(0);
//				Row row = sheet.getRow(this.startRow - 1);
//				Cell cell = null;
//				for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
//					cell = row.getCell(i);
//					if (cell.getCellType() == 1) {
//						this.columnNames.add(cell.getStringCellValue()
//								.toLowerCase());
//					}
//				}
//			}
//			this.currentRow = this.startRow + 1;
//		}



	}

}
