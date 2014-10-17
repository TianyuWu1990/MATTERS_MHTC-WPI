package edu.wpi.mhtc.dashboard.pipeline.parsers;

import java.io.File;
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

import edu.wpi.mhtc.dashboard.pipeline.data.DataSource;
import edu.wpi.mhtc.dashboard.pipeline.data.FileType;
import edu.wpi.mhtc.dashboard.pipeline.data.Line;
import edu.wpi.mhtc.pipeline.data.FileData;

public class ExcelParser implements IParser {

	private DataSource source;
	private ArrayList<String> columnNames;
	private ArrayList<Line> lines;
	private Workbook workbook;
	private int startRow;
	private int endRow;

	public ExcelParser(DataSource source) throws InvalidFormatException, IOException {
		this.source = source;
		this.columnNames = new ArrayList<String>();
		this.lines = new ArrayList<Line>();
		this.init();
	}

	private void init() throws InvalidFormatException, IOException {

		if (this.source.getFileType() == FileType.xlsx) {
			this.workbook = (XSSFWorkbook) WorkbookFactory.create(
					this.source.getFile());
		}
		if (this.source.getFileType() == FileType.xls) {
			this.workbook = WorkbookFactory.create(this.source.getFile());
		}

		this.getStartAndEndRow();
	}

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

	public FileData parseAll() {
		this.getColumnNames();
		for (int i = 0; i < this.workbook.getNumberOfSheets(); i++) {
			Sheet sheet = this.workbook.getSheetAt(i);
			for (int j = this.startRow; j < this.endRow; j++) {
				Row row = sheet.getRow(j);
				if (row == null) {
					continue;
				}
				Cell cell = row.getCell(0);
				if (cell == null
						|| (cell.getCellType() == 1 && cell
								.getStringCellValue().trim().length() == 0)
						|| cell.getCellType() == 3) {
					continue;
				}
				HashMap<String, String> table = new HashMap<String, String>();
				for (int k = row.getFirstCellNum(); k < row.getLastCellNum(); k++) {
					cell = row.getCell(k);
					String rowName = this.columnNames.get(k);
					if (cell == null) {
						table.put(rowName, "NULL");
					} else {
						switch (cell.getCellType()) {
						case 0:
							double value = cell.getNumericCellValue();
							if (value % 1 == 0) {
								table.put(rowName,
										Integer.toString((int) value));
							} else {
								table.put(rowName, Double.toString(value));
							}

							break;
						case 1:
							table.put(rowName, cell.getStringCellValue());
							break;
						case 3:
							table.put(rowName, "BLANK");
							break;
						case 5:
							table.put(rowName,
									Byte.toString(cell.getErrorCellValue()));
							break;
						default:
							break;
						}
					}
				}
				lines.add(new Line());
			}
		}
		this.fileData.setLineList(this.lines);
		return this.fileData;
	}

	private void getStartAndEndRow() {
		if (this.source.getLoadInfo().isRowSpecified()) {
			this.startRow = this.source.getLoadInfo().getStartRow();
			this.endRow = this.source.getLoadInfo().getEndRow();
		} else {
			Sheet sheet = this.workbook.getSheetAt(0);
			this.startRow = 2;
			this.endRow = sheet.getLastRowNum() + 1;
		}
	}

	@Override
	public Iterator<Line> iterator() {
		return lines.iterator();
	}

	private class ExcelIterator implements Iterator<Line> {

		private List<String> columnNames;
		private Workbook workbook;
		private DataSource source;
		private int startRow;
		private int endRow;
		private int currentRow;

		public ExcelIterator() {
			this.source = ExcelParser.this.source;
			this.columnNames = ExcelParser.this.getColumnNames();
			this.init();
		}

		@Override
		public boolean hasNext() {
			return (this.currentRow <= this.endRow);
		}

		@Override
		public Line next() {
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
			return new Line(map, this.source);
		}

		@Override
		public void remove() {
		}

		private void init() {
			try {
				if (ExcelParser.this.source.getFileType() == FileType.xlsx) {
					this.workbook = (XSSFWorkbook) WorkbookFactory
							.create(new File(ExcelParser.this.source
									.getFileName()));
				}
				if (ExcelParser.this.source.getFileType() == FileType.xls) {
					this.workbook = WorkbookFactory.create(new File(
							ExcelParser.this.source.getFileName()));
				}
			} catch (InvalidFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.getStartAndEndRow();
			this.currentRow = this.startRow;
			if (this.columnNames.isEmpty()) {
				Sheet sheet = this.workbook.getSheetAt(0);
				Row row = sheet.getRow(this.startRow - 1);
				Cell cell = null;
				for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
					cell = row.getCell(i);
					if (cell.getCellType() == 1) {
						this.columnNames.add(cell.getStringCellValue()
								.toLowerCase());
					}
				}
			}
			this.currentRow = this.startRow + 1;
		}

		private void getStartAndEndRow() {
			if (this.source.getLoadInfo().isRowSpecified()) {
				this.startRow = this.source.getLoadInfo().getStartRow();
				this.endRow = this.source.getLoadInfo().getEndRow();
			} else {
				Sheet sheet = this.workbook.getSheetAt(0);
				this.startRow = 1;
				this.endRow = sheet.getLastRowNum() + 1;
			}
		}

	}

}
