package edu.wpi.mhtc.dashboard.pipeline.parser;

import java.io.File;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.sun.media.sound.InvalidFormatException;

import edu.wpi.mhtc.dashboard.pipeline.data.FileData;
import edu.wpi.mhtc.dashboard.pipeline.data.LineData;
import edu.wpi.mhtc.dashboard.pipeline.fileInfo.FileInfo;

public class Type1Parser implements IParser {

	private FileInfo fileInfo;
	private FileData fileData;
	private List<String> columnNames;
	private List<LineData> lineDatalist;
	private Workbook workbook;
	private Sheet currentSheet;
	private int startRow;
	private int endRow;
	private String currentYear;

	public Type1Parser(FileInfo fileInfo) throws InvalidFormatException,
			IOException {
		this.fileInfo = fileInfo;
		this.fileData = new FileData(this.fileInfo);
		this.columnNames = new LinkedList<String>();
		this.lineDatalist = new LinkedList<LineData>();
		this.currentYear = "";
		this.init();
	}

	private void init() throws InvalidFormatException, IOException {
		try {
			this.workbook = WorkbookFactory.create(new File(this.fileInfo
					.getFileName()));
		} catch (org.apache.poi.openxml4j.exceptions.InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.currentSheet = this.workbook.getSheetAt(0);
		if (this.fileInfo.getLoadInfo().isRowSpecified()) {
			this.startRow = this.fileInfo.getLoadInfo().getStartRow();
			this.endRow = this.fileInfo.getLoadInfo().getEndRow();
		} else {
			this.startRow = 1;
			this.endRow = this.currentSheet.getLastRowNum();
		}
	}

	@Override
	public Iterator<LineData> iterator() {
		try {
			return new Type1Iterator();
		} catch (org.apache.poi.openxml4j.exceptions.InvalidFormatException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	@Override
	public FileData parseAll() throws Exception {
		this.getColumnNames();
		for (int i = 0; i < this.workbook.getNumberOfSheets(); i++) {
			this.currentSheet = this.workbook.getSheetAt(i);
			this.parseYear();
			for (int j = this.startRow + 1; j <= this.endRow; j++) {
				Row row = this.currentSheet.getRow(j);
				Cell cell = row.getCell(0);
				if (cell.getCellType() == 1
						&& cell.getStringCellValue().length() > 0) {
					Map<String, String> map = new HashMap<String, String>();
					Iterator<String> iter = this.columnNames.iterator();
					for (int k = row.getFirstCellNum(); k < row
							.getLastCellNum(); k++) {
						cell = row.getCell(k);
						String value = "";
						if (cell.getCellType() == 0) {
							double v = cell.getNumericCellValue();
							if (v % 1 == 0) {
								value = Integer.toString((int) v);
							} else {
								value = Double.toString(v);
							}
							map.put(iter.next(), value);
						}
						if (cell.getCellType() == 1) {
							value = cell.getStringCellValue().trim();
							map.put(iter.next(), value);
						}
					}
					map.put("year", this.currentYear);
					this.lineDatalist.add(new LineData(map, this.fileInfo));
				}
			}
		}
		this.fileData.setLineDataList(this.lineDatalist);
		return this.fileData;
	}

	private void parseYear() {
		for (int j = this.startRow - 1; j >= 0; j--) {
			Row row = this.currentSheet.getRow(j);
			boolean flag = false;
			for (int k = row.getFirstCellNum(); k < row.getLastCellNum(); k++) {
				Cell cell = row.getCell(k);
				if (cell != null && cell.getCellType() == 1
						&& cell.getStringCellValue().trim().length() > 0) {
					flag = true;
					String string = row.getCell(k).getStringCellValue().trim();
					if (string.indexOf("\n") != -1) {
						string = string.substring(0, string.indexOf("\n"));
					}
					this.currentYear = string.substring(string.length() - 4,
							string.length());
					break;
				}
			}
			if (flag) {
				break;
			}
		}
	}

	@Override
	public List<String> getColumnNames() {
		if (this.columnNames.isEmpty()) {
			this.columnNames.add("state");
			Sheet sheet = this.workbook.getSheetAt(0);
			Row row = sheet.getRow(this.startRow);
			for (int i = 1; i < row.getLastCellNum(); i++) {
				Cell cell = row.getCell(i);
				String string = cell.getStringCellValue();
				if (string.length() > 0) {
					if (string.lastIndexOf("[") != -1) {
						this.columnNames.add(string
								.substring(0, string.lastIndexOf("[")).trim()
								.toLowerCase());
					} else {
						this.columnNames.add(string.trim().toLowerCase());
					}
				}
			}
			this.columnNames.add("year");
		}
		return this.columnNames;
	}

	public class Type1Iterator implements Iterator<LineData> {

		private Workbook workbook;
		private FileInfo fileInfo;
		private List<String> columnNames;
		private int startRow;
		private int currentRow;
		private int endRow;
		private int currentSheet;
		private String year;

		public Type1Iterator() throws org.apache.poi.openxml4j.exceptions.InvalidFormatException {
			this.fileInfo = Type1Parser.this.fileInfo;
			this.startRow = Type1Parser.this.startRow + 1;
			this.currentRow = this.startRow;
			this.endRow = Type1Parser.this.endRow;
			this.columnNames = Type1Parser.this.getColumnNames();
			this.currentSheet = 0;
			this.year = "";
			try {
				this.init();
			} catch (InvalidFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private void init() throws InvalidFormatException, IOException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {
			this.workbook = WorkbookFactory.create(new File(this.fileInfo
					.getFileName()));
		}

		private void parseYear() {
			for (int i = this.startRow - 2; i >= 0; i--) {
				Row row = this.workbook.getSheetAt(this.currentSheet).getRow(i);
				boolean flag = false;
				for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
					Cell cell = row.getCell(j);
					if (cell != null && cell.getCellType() == 1
							&& cell.getStringCellValue().trim().length() > 0) {
						flag = true;
						String string = row.getCell(j).getStringCellValue()
								.trim();
						if (string.indexOf("\n") != -1) {
							string = string.substring(0, string.indexOf("\n"));
						}
						this.year = string.substring(string.length() - 4,
								string.length());
						break;
					}
				}
				if (flag) {
					break;
				}
			}
		}

		@Override
		public boolean hasNext() {
			return (this.findNextLegalRow() != null);
		}

		@Override
		public LineData next() {
			LineData lineData = null;
			SimpleEntry<Integer, Integer> simpleEntry = null;
			if ((simpleEntry = this.findNextLegalRow()) != null) {
				int previousSheet = this.currentSheet;
				this.currentRow = simpleEntry.getValue();
				this.currentSheet = simpleEntry.getKey();
				if (this.currentSheet != previousSheet || this.year.isEmpty()) {
					this.parseYear();
				}
				Row row = this.workbook.getSheetAt(this.currentSheet).getRow(
						this.currentRow);
				Cell cell = row.getCell(0);
				if (cell.getCellType() == 1
						&& cell.getStringCellValue().length() > 0) {
					Map<String, String> map = new HashMap<String, String>();
					Iterator<String> iter = this.columnNames.iterator();
					for (int k = row.getFirstCellNum(); k < row
							.getLastCellNum(); k++) {
						cell = row.getCell(k);
						String value = "";
						if (cell.getCellType() == 0) {
							double v = cell.getNumericCellValue();
							if (v % 1 == 0) {
								value = Integer.toString((int) v);
							} else {
								value = Double.toString(v);
							}
							map.put(iter.next(), value);
						}
						if (cell.getCellType() == 1) {
							value = cell.getStringCellValue().trim();
							map.put(iter.next(), value);
						}
					}
					map.put("year", this.year);
					lineData = new LineData(map, this.fileInfo);
				}
			}
			return lineData;
		}

		private SimpleEntry<Integer, Integer> findNextLegalRow() {
			int currentRow = this.currentRow;
			int currentSheet = this.currentSheet;
			while (currentSheet < this.workbook.getNumberOfSheets()) {
				Sheet sheet = this.workbook.getSheetAt(currentSheet);
				while (currentRow < this.endRow) {
					Row row = sheet.getRow(++currentRow);
					Cell cell = row.getCell(0);
					if (cell.getCellType() == 1
							&& cell.getStringCellValue().length() > 0) {
						return new SimpleEntry<Integer, Integer>(currentSheet,
								currentRow);
					}
				}
				currentSheet++;
				currentRow = this.startRow;
			}
			return null;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub

		}

	}

}
