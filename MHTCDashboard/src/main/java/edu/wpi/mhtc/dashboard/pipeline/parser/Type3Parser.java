package edu.wpi.mhtc.dashboard.pipeline.parser;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.sun.media.sound.InvalidFormatException;

import edu.wpi.mhtc.dashboard.pipeline.data.FileData;
import edu.wpi.mhtc.dashboard.pipeline.data.LineData;
import edu.wpi.mhtc.dashboard.pipeline.fileInfo.FileInfo;

public class Type3Parser implements IParser {

	private FileInfo fileInfo;
	private FileData fileData;
	private List<String> columnNames;
	private List<LineData> lineDatalist;
	private Workbook workbook;
	private Sheet sheet;
	private int startRow;
	private int endRow;
	private String year;

	public Type3Parser(FileInfo fileInfo) throws InvalidFormatException,
			IOException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {
		this.fileInfo = fileInfo;
		this.fileData = new FileData(this.fileInfo);
		this.columnNames = new LinkedList<String>();
		this.lineDatalist = new LinkedList<LineData>();
		this.init();
	}

	private void init() throws InvalidFormatException, IOException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {
		this.workbook = WorkbookFactory.create(new File(this.fileInfo
				.getFileName()));
		this.sheet = this.workbook.getSheetAt(0);
		String fileName = this.fileInfo.getFileName();
		this.year = fileName.substring(fileName.lastIndexOf("_") - 4,
				fileName.lastIndexOf("_"));
		if (this.fileInfo.getLoadInfo().isRowSpecified()) {
			this.startRow = this.fileInfo.getLoadInfo().getStartRow();
			this.endRow = this.fileInfo.getLoadInfo().getEndRow();
		} else {
			this.startRow = 1;
			this.endRow = this.workbook.getSheetAt(0).getLastRowNum();
		}
	}

	@Override
	public Iterator<LineData> iterator() {
		try {
			return new Type3Iterator();
		} catch (org.apache.poi.openxml4j.exceptions.InvalidFormatException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public FileData parseAll() throws Exception {
		for (int i = this.startRow; i < this.endRow; i++) {
			Row row = this.sheet.getRow(i);
			Cell cell = row.getCell(4);
			if (cell.getCellType() == 1
					&& cell.getStringCellValue().equalsIgnoreCase(
							"All Occupations")) {
				HashMap<String, String> map = new HashMap<String, String>();
				cell = row.getCell(1);
				String state = cell.getStringCellValue();
				cell = row.getCell(6);
				String value = "";
				if (cell.getCellType() == 0) {
					value = Integer.toString((int) cell.getNumericCellValue());
				}
				if (cell.getCellType() == 1) {
					value = cell.getStringCellValue();
				}
				map.put("state", state);
				map.put("year", this.year);
				map.put("tot_emp", value);
				LineData lineData = new LineData(map, this.fileInfo);
				lineDatalist.add(lineData);
			}
		}
		this.fileData.setLineDataList(this.lineDatalist);
		return this.fileData;
	}

	@Override
	public List<String> getColumnNames() {
		if (this.columnNames.isEmpty()) {
			this.columnNames.add("state");
			this.columnNames.add("tot_emp");
			this.columnNames.add("year");
		}
		return this.columnNames;
	}

	public class Type3Iterator implements Iterator<LineData> {

		private Workbook workbook;
		private Sheet sheet;
		private FileInfo fileInfo;
		private int currentRow;
		private int endRow;
		private String year;

		public Type3Iterator() throws org.apache.poi.openxml4j.exceptions.InvalidFormatException {
			this.fileInfo = Type3Parser.this.fileInfo;
			this.endRow = Type3Parser.this.endRow;
			this.currentRow = Type3Parser.this.startRow - 1;
			this.year = Type3Parser.this.year;
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
			this.sheet = this.workbook.getSheetAt(0);
		}

		@Override
		public boolean hasNext() {
			int current = this.currentRow;
			while (current < this.endRow) {
				Row row = this.sheet.getRow(++current);
				Cell cell = row.getCell(4);
				if (cell.getCellType() == 1
						&& cell.getStringCellValue().equalsIgnoreCase(
								"All Occupations")) {
					return true;
				}
			}
			return false;
		}

		@Override
		public LineData next() {
			while (this.currentRow <= this.endRow) {
				Row row = this.sheet.getRow(++this.currentRow);
				Cell cell = row.getCell(4);
				if (cell.getCellType() == 1
						&& cell.getStringCellValue().equalsIgnoreCase(
								"All Occupations")) {
					HashMap<String, String> map = new HashMap<String, String>();
					cell = row.getCell(1);
					String state = cell.getStringCellValue();
					cell = row.getCell(6);
					String value = new String();
					if (cell.getCellType() == 0) {
						value = Integer.toString((int) cell
								.getNumericCellValue());
					}
					if (cell.getCellType() == 1) {
						value = cell.getStringCellValue();
					}
					map.put("state", state);
					map.put("year", this.year);
					map.put("tot_emp", value);
					return new LineData(map, this.fileInfo);
				}
			}
			return null;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub

		}

	}

}
