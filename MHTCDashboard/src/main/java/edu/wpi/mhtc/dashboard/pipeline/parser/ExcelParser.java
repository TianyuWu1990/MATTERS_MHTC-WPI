package edu.wpi.mhtc.dashboard.pipeline.parser;

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

import edu.wpi.mhtc.dashboard.pipeline.data.FileData;
import edu.wpi.mhtc.dashboard.pipeline.data.LineData;
import edu.wpi.mhtc.dashboard.pipeline.fileInfo.FileInfo;
import edu.wpi.mhtc.dashboard.pipeline.fileInfo.FileType;

public class ExcelParser implements IParser {

	public FileInfo fileInfo;
	private FileData fileData;
	private ArrayList<String> columnNames;
	private ArrayList<LineData> lineDatalist;
	private Workbook workbook;
	private int startRow;
	private int endRow;

	public ExcelParser(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
		this.fileData = new FileData(this.fileInfo);
		this.columnNames = new ArrayList<String>();
		this.lineDatalist = new ArrayList<LineData>();
		this.init();
	}

	private void init() {
		try {
			if (this.fileInfo.getFileType() == FileType.xlsx) {
				this.workbook = (XSSFWorkbook) WorkbookFactory.create(
						this.fileInfo.getFile());
			}
			if (this.fileInfo.getFileType() == FileType.xls) {
				this.workbook = WorkbookFactory.create(this.fileInfo.getFile());
			}
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.getStartAndEndRow();
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

	@Override
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
				lineDatalist.add(new LineData(table, fileInfo));
			}
		}
		this.fileData.setLineDataList(this.lineDatalist);
		return this.fileData;
	}

	private void getStartAndEndRow() {
		if (this.fileInfo.getLoadInfo().isRowSpecified()) {
			this.startRow = this.fileInfo.getLoadInfo().getStartRow();
			this.endRow = this.fileInfo.getLoadInfo().getEndRow();
		} else {
			Sheet sheet = this.workbook.getSheetAt(0);
			this.startRow = 1;
			this.endRow = sheet.getLastRowNum() + 1;
		}
	}

	@Override
	public Iterator<LineData> iterator() {
		return new ExcelIterator();
	}

	private class ExcelIterator implements Iterator<LineData> {

		private List<String> columnNames;
		private Workbook workbook;
		private FileInfo fileInfo;
		private int startRow;
		private int endRow;
		private int currentRow;

		public ExcelIterator() {
			this.fileInfo = ExcelParser.this.fileInfo;
			this.columnNames = ExcelParser.this.getColumnNames();
			this.init();
		}

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

		private void init() {
			try {
				if (ExcelParser.this.fileInfo.getFileType() == FileType.xlsx) {
					this.workbook = (XSSFWorkbook) WorkbookFactory
							.create(new File(ExcelParser.this.fileInfo
									.getFileName()));
				}
				if (ExcelParser.this.fileInfo.getFileType() == FileType.xls) {
					this.workbook = WorkbookFactory.create(new File(
							ExcelParser.this.fileInfo.getFileName()));
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
			if (this.fileInfo.getLoadInfo().isRowSpecified()) {
				this.startRow = this.fileInfo.getLoadInfo().getStartRow();
				this.endRow = this.fileInfo.getLoadInfo().getEndRow();
			} else {
				Sheet sheet = this.workbook.getSheetAt(0);
				this.startRow = 1;
				this.endRow = sheet.getLastRowNum() + 1;
			}
		}

	}

}
