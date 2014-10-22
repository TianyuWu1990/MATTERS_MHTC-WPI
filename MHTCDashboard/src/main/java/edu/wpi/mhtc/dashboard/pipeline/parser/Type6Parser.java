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

import edu.wpi.mhtc.dashboard.pipeline.data.DataSource;
import edu.wpi.mhtc.dashboard.pipeline.data.Line;



//notes:
//This is just for this file for historical data, for rest of the years it will not be in this file format.
//“Gov_Tax_Collections_Historical_DB” in State and Local Tax Burden
//Column 1: Year
//Column 3: State (Can ignore 2nd column)
//Ignore 4th Column i.e. FY Ending Date
//Rest columns include tax amounts. Ignore anything in  () in column names

public class Type6Parser implements IParser {

	private DataSource source;
	private List<String> columnNames;
	private List<Line> lines;
	private Workbook workbook;
	private Sheet sheet;
	private int startRow;
	private int endRow;

	public Type6Parser(DataSource source) throws InvalidFormatException,
			IOException {
		this.source = source;
		this.columnNames = new LinkedList<String>();
		this.lines = new LinkedList<Line>();
		this.init();
	}

	private void init() throws InvalidFormatException, IOException {
		this.workbook = WorkbookFactory.create(new File(this.source
				.getFileName()));
		this.sheet = this.workbook.getSheetAt(0);
		if (this.source.getLoadInfo().isRowSpecified()) {
			this.startRow = this.source.getLoadInfo().getStartRow();
			this.endRow = this.source.getLoadInfo().getEndRow();
		} else {
			this.startRow = 1;
			this.endRow = this.sheet.getLastRowNum();
		}
	}

	@Override
	public Iterator<Line> iterator() {
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
			this.lines.add(new Line(map, this.source));
		}
		this.fileData.setLineList(this.lines);
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

	public class Type6Iterator implements Iterator<Line> {

		private Workbook workbook;
		private Sheet sheet;
		private DataSource source;
		private List<String> columnNames;
		private int currentRow;
		private int endRow;

		public Type6Iterator() {
			this.source = Type6Parser.this.source;
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
			this.workbook = WorkbookFactory.create(new File(this.source
					.getFileName()));
			this.sheet = this.workbook.getSheetAt(0);
		}

		@Override
		public boolean hasNext() {
			return (this.currentRow < this.endRow);
		}

		@Override
		public Line next() {
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
			return new Line(map, this.source);
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub

		}

	}

}
