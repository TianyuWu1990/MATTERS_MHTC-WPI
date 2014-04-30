package edu.wpi.mhtc.dashboard.pipeline.parser;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import edu.wpi.mhtc.dashboard.pipeline.data.FileData;
import edu.wpi.mhtc.dashboard.pipeline.data.LineData;
import edu.wpi.mhtc.dashboard.pipeline.fileInfo.FileInfo;

public class Type6Parser implements IParser {

	private FileInfo fileInfo;
	private FileData fileData;
	private List<String> columnNames;
	private List<LineData> lineDatalist;
	private Workbook workbook;
	private Sheet sheet;
	private int startRow;
	private int endRow;

	public Type6Parser(FileInfo fileInfo) throws InvalidFormatException,
			IOException {
		this.fileInfo = fileInfo;
		this.fileData = new FileData(this.fileInfo);
		this.columnNames = new LinkedList<String>();
		this.lineDatalist = new LinkedList<LineData>();
		this.init();
	}

	private void init() throws InvalidFormatException, IOException {
		this.workbook = WorkbookFactory.create(new File(this.fileInfo
				.getFileName()));
		this.sheet = this.workbook.getSheetAt(0);
		if (this.fileInfo.getLoadInfo().isRowSpecified()) {
			this.startRow = this.fileInfo.getLoadInfo().getStartRow();
			this.endRow = this.fileInfo.getLoadInfo().getEndRow();
		} else {
			this.startRow = 1;
			this.endRow = this.sheet.getLastRowNum();
		}
	}

	@Override
	public Iterator<LineData> iterator() {
		return new Type6Iterator();
	}

	@Override
	public FileData parseAll() throws Exception {
		if (this.columnNames.isEmpty()) {
			this.getColumnNames();
		}
		for (int i = this.startRow; i <= this.endRow; i++) {
			Iterator<String> iter = this.columnNames.iterator();
			Row row = this.sheet.getRow(i);
			HashMap<String, String> map = new HashMap<String, String>();
			for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
				if (j != 1 && j != 3) {
					Cell cell = row.getCell(j);
					if (cell.getCellType() == 0) {
						int value = (int) cell.getNumericCellValue();
						if (value >= 0) {
							map.put(iter.next(), Integer.toString(value));
						} else {
							map.put(iter.next(), new String());
						}
					}
					if (cell.getCellType() == 1) {
						map.put(iter.next(), cell.getStringCellValue().trim());
					}
				}
			}
			this.lineDatalist.add(new LineData(map, this.fileInfo));
		}
		this.fileData.setLineDataList(this.lineDatalist);
		return this.fileData;
	}

	@Override
	public List<String> getColumnNames() {
		Row row = this.sheet.getRow(0);
		for (int i = 0; i < row.getLastCellNum(); i++) {
			if (i != 1 && i != 3) {
				Cell cell = row.getCell(i);
				if (cell.getCellType() == 1) {
					String str = cell.getStringCellValue();
					if (str.lastIndexOf("(") != -1) {
						str = str.substring(0, str.lastIndexOf("(") - 1);
					}
					if (i == 2) {
						this.columnNames.add("state");
					} else {
						this.columnNames.add(str.trim().toLowerCase());
					}
				}
			}
		}
		return this.columnNames;
	}

	public class Type6Iterator implements Iterator<LineData> {

		private Workbook workbook;
		private Sheet sheet;
		private FileInfo fileInfo;
		private List<String> columnNames;
		private int currentRow;
		private int endRow;

		public Type6Iterator() {
			this.fileInfo = Type6Parser.this.fileInfo;
			this.endRow = Type6Parser.this.endRow;
			this.currentRow = Type6Parser.this.startRow - 1;
			this.columnNames = Type6Parser.this.getColumnNames();
			try {
				this.init();
			} catch (InvalidFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private void init() throws InvalidFormatException, IOException {
			this.workbook = WorkbookFactory.create(new File(this.fileInfo
					.getFileName()));
			this.sheet = this.workbook.getSheetAt(0);
		}

		@Override
		public boolean hasNext() {
			return (this.currentRow < this.endRow);
		}

		@Override
		public LineData next() {
			Iterator<String> iter = this.columnNames.iterator();
			Row row = this.sheet.getRow(++this.currentRow);
			HashMap<String, String> map = new HashMap<String, String>();
			for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
				if (i != 1 && i != 3) {
					Cell cell = row.getCell(i);
					if (cell.getCellType() == 0) {
						int value = (int) cell.getNumericCellValue();
						if (value < 0) {
							value = 0;
						}
						map.put(iter.next(), Integer.toString(value));
					}
					if (cell.getCellType() == 1) {
						map.put(iter.next(), cell.getStringCellValue().trim());
					}
				}
			}
			return new LineData(map, this.fileInfo);
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub

		}

	}

}
