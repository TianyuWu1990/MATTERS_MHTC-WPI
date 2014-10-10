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

public class Type7Parser implements IParser {

	private DataSource source;
	private List<String> columnNames;
	private List<Line> lines;
	private Workbook workbook;
	private Sheet sheet;
	private int startRow;
	private int endRow;
	private List<String> years;

	public Type7Parser(DataSource source) throws InvalidFormatException,
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
			this.startRow = 2;
			this.endRow = 57;
		}
	}

	@Override
	public Iterator<Line> iterator() {
		return new Type7Iterator();
	}

	@Override
	public FileData parseAll() throws Exception {
		this.getColumnNames();
		this.parseYear();
		for (int i = this.startRow + 2; i <= this.endRow; i++) {
			Row row = this.sheet.getRow(i);
			Cell cell = row.getCell(0);
			String state = "";
			if (cell != null && cell.getCellType() == 1
					&& cell.getStringCellValue().trim().length() > 0) {
				state = cell.getStringCellValue().trim();
				for (int j = 0; j < 3; j++) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("state", state);
					map.put("year", this.years.get(j));
					for (int k = 0; k < 3; k++) {
						cell = row.getCell(4 * k + 1 + j);
						if (cell != null) {
							if (cell.getCellType() == 1
									&& cell.getStringCellValue().trim()
											.length() > 0) {
								map.put(this.columnNames.get(k), cell
										.getStringCellValue().trim());
							}
							if (cell.getCellType() == 0) {
								if (k < 2) {
									map.put(this.columnNames.get(k), Integer
											.toString((int) cell
													.getNumericCellValue()));
								} else {
									map.put(this.columnNames.get(k), Double
											.toString(cell
													.getNumericCellValue()));
								}
							}
						}
					}
					this.lines.add(new Line(map, this.source));
				}
			}
		}
		this.fileData.setLineList(this.lines);
		return this.fileData;
	}

	private List<String> parseYear() {
		Row row = this.sheet.getRow(this.startRow + 1);
		for (int i = 1; i < 4; i++) {
			Cell cell = row.getCell(i);
			if (cell != null && cell.getCellType() == 0) {
				this.years.add(Integer.toString((int) cell
						.getNumericCellValue()));
			}
		}
		return this.years;
	}

	@Override
	public List<String> getColumnNames() {
		if (this.columnNames.isEmpty()) {
			Row row = this.sheet.getRow(this.startRow);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				Cell cell = row.getCell(i);
				if (cell != null && cell.getCellType() == 1
						&& cell.getStringCellValue().trim().length() > 0) {
					String string = cell.getStringCellValue().trim();
					if (string.indexOf("/\n") != -1) {
						String[] s = string.split("/\n");
						string = "";
						for (String str : s) {
							string += str + " ";
						}
					}
					this.columnNames.add(string.trim().toLowerCase());
				}
			}
			this.columnNames.add("state");
			this.columnNames.add("year");
		}
		return this.columnNames;
	}

	public class Type7Iterator implements Iterator<Line> {

		private DataSource source;
		private List<String> columnNames;
		private Workbook workbook;
		private Sheet sheet;
		private int endRow;
		private int currentRow;
		private List<String> years;
		private int currentYearIndex;

		public Type7Iterator() {
			this.source = Type7Parser.this.source;
			this.endRow = Type7Parser.this.endRow;
			this.currentRow = Type7Parser.this.startRow + 1;
			this.columnNames = Type7Parser.this.getColumnNames();
			this.years = Type7Parser.this.parseYear();
			this.currentYearIndex = 2;
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
			return (this.findNextLegalLine() != -1);
		}

		@Override
		public Line next() {
			Line lineData = null;
			int nextLine;
			if ((nextLine = this.findNextLegalLine()) != -1) {
				Map<String, String> map = new HashMap<String, String>();
				if (nextLine != this.currentRow) {
					this.currentYearIndex = 0;
				} else {
					this.currentYearIndex++;
				}
				this.currentRow = nextLine;
				Row row = this.sheet.getRow(this.currentRow);
				String state = row.getCell(0).getStringCellValue().trim();
				map.put("state", state);
				map.put("year", this.years.get(this.currentYearIndex));
				for (int i = 0; i < 3; i++) {
					Cell cell = row.getCell(4 * i + 1 + this.currentYearIndex);
					if (cell != null) {
						if (cell.getCellType() == 1
								&& cell.getStringCellValue().trim().length() > 0) {
							map.put(this.columnNames.get(i), cell
									.getStringCellValue().trim());
						}
						if (cell.getCellType() == 0) {
							if (i < 2) {
								map.put(this.columnNames.get(i), Integer
										.toString((int) cell
												.getNumericCellValue()));
							} else {
								map.put(this.columnNames.get(i), Double
										.toString(cell.getNumericCellValue()));
							}
						}
					}
				}
				lineData = new Line(map, this.source);
			}
			return lineData;
		}

		private int findNextLegalLine() {
			if (this.currentYearIndex < this.years.size() - 1) {
				return this.currentRow;
			}
			int currentRow = this.currentRow;
			while (currentRow < this.endRow) {
				Row row = this.sheet.getRow(++currentRow);
				Cell cell = row.getCell(0);
				if (cell != null && cell.getCellType() == 1
						&& cell.getStringCellValue().trim().length() > 0) {
					return currentRow;
				}
			}
			return -1;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub

		}

	}

}
