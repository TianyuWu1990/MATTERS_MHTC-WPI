package edu.wpi.mhtc.pipeline.parser;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import edu.wpi.mhtc.pipeline.data.DataSource;
import edu.wpi.mhtc.pipeline.data.Line;

public class Type5Parser implements IParser {

	private DataSource source;
	private List<String> columnNames;
	private List<Line> lines;
	private Workbook workbook;
	private Sheet sheet;
	private int startRow;
	private int endRow;
	private List<String> years;

	public Type5Parser(DataSource source) throws InvalidFormatException,
			IOException {
		this.source = source;
		this.columnNames = new LinkedList<String>();
		this.lines = new LinkedList<Line>();
		this.years = new LinkedList<String>();
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
			this.startRow = 5;
			this.endRow = 19;
		}
	}

	@Override
	public Iterator<Line> iterator() {
		return new Type5Iterator();
	}

	@Override
	public FileData parseAll() throws Exception {
		this.getColumnNames();
		Row row = this.sheet.getRow(this.startRow);
		for (int i = 2; i < row.getLastCellNum(); i++) {
			Cell cell = row.getCell(i);
			if (cell.getCellType() == 1) {
				this.years.add(cell.getStringCellValue().trim());
			}
		}
		for (int i = this.startRow + 1; i <= this.endRow; i++) {
			row = this.sheet.getRow(i);
			Cell firstCell = row.getCell(1);
			if (firstCell.getCellType() == 1) {
				for (int j = 2; j < row.getLastCellNum(); j++) {
					Cell cell = row.getCell(j);
					Map<String, String> map = new HashMap<String, String>();
					if (cell.getCellType() == 0) {
						map.put(this.columnNames.get(1), firstCell
								.getStringCellValue().trim());
						map.put(this.columnNames.get(0), this.years.get(j - 2));
						map.put(this.columnNames.get(2), Integer
								.toString((int) cell.getNumericCellValue()));
						this.lines.add(new Line(map, this.source));
					}
				}
			}
		}
		this.fileData.setLineList(this.lines);
		return this.fileData;
	}

	@Override
	public List<String> getColumnNames() {
		if (this.columnNames.isEmpty()) {
			this.columnNames.add("year");
			this.columnNames.add("state");
			Sheet sheet = this.workbook.getSheetAt(0);
			Row row = sheet.getRow(1);
			String string = row.getCell(0).getStringCellValue();
			this.columnNames.add(string
					.substring(0, string.lastIndexOf(")") + 1).trim()
					.toLowerCase());
		}
		return this.columnNames;
	}

	public class Type5Iterator implements Iterator<Line> {

		private DataSource source;
		private List<String> columnNames;
		private Workbook workbook;
		private Sheet sheet;
		private int endRow;
		private int currentRow;
		private List<String> years;
		private int currentCell;

		public Type5Iterator() {
			this.source = Type5Parser.this.source;
			this.endRow = Type5Parser.this.endRow;
			this.currentRow = Type5Parser.this.startRow + 1;
			this.columnNames = Type5Parser.this.getColumnNames();
			this.years = new LinkedList<String>();
			this.currentCell = 2;
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
			Row row = this.sheet.getRow(this.currentRow - 1);
			for (int i = 2; i < row.getLastCellNum(); i++) {
				Cell cell = row.getCell(i);
				if (cell.getCellType() == 1) {
					this.years.add(cell.getStringCellValue().trim());
				}
			}
		}

		@Override
		public boolean hasNext() {
			if (this.currentRow < this.endRow) {
				return true;
			} else {
				if (this.currentRow == this.endRow) {
					return (this.currentCell <= this.sheet.getLastRowNum());
				} else {
					return false;
				}
			}
		}

		@Override
		public Line next() {
			Row row = this.sheet.getRow(this.currentRow);
			Cell firstCell = row.getCell(1);
			Line lineData = null;
			if (firstCell.getCellType() == 1) {
				Cell cell = row.getCell(this.currentCell);
				Map<String, String> map = new HashMap<String, String>();
				if (cell.getCellType() == 0) {
					map.put(this.columnNames.get(1), firstCell
							.getStringCellValue().trim());
					map.put(this.columnNames.get(0),
							this.years.get(this.currentCell - 2));
					map.put(this.columnNames.get(2),
							Integer.toString((int) cell.getNumericCellValue()));
					lineData = new Line(map, this.source);
				}
			}
			if (this.currentCell < row.getLastCellNum() - 1) {
				this.currentCell++;
			} else {
				this.currentRow++;
				this.currentCell = 2;
			}
			return lineData;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub

		}

	}

}
