package edu.wpi.mhtc.dashboard.pipeline.parser;

import java.io.File;
import java.io.FileInputStream;
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


//Another Unemployment parser. Not suer which file this is for

//public class Type4Parser implements IParser {
//
//	private DataSource source;
//	private List<String> columnNames;
//	private List<Line> lines;
//	private Workbook workbook;
//	private Sheet sheet;
//	private int startRow;
//	private int endRow;
//	private String state;
//
//	public Type4Parser(DataSource source) throws InvalidFormatException,
//			IOException {
//		this.source = source;
//		this.columnNames = new LinkedList<String>();
//		this.lines = new LinkedList<Line>();
//		this.init();
//	}
//
//	private void init() throws InvalidFormatException, IOException {
//		this.workbook = WorkbookFactory.create( new FileInputStream(this.source.getFileName()));
//		this.sheet = this.workbook.getSheetAt(0);
//		String fileName = this.source.getFileName();
//		this.state = fileName.substring(
//				fileName.lastIndexOf(File.separator) + 1,
//				fileName.lastIndexOf("_"));
//		if (this.source.getLoadInfo().isRowSpecified()) {
//			this.startRow = this.source.getLoadInfo().getStartRow();
//			this.endRow = this.source.getLoadInfo().getEndRow();
//		} else {
//			this.startRow = 12;
//			this.endRow = 23;
//		}
//		this.columnNames.add("state");
//		this.columnNames.add("year");
//		this.columnNames.add("unemployment rate");
//	}
//
//	@Override
//	public Iterator<Line> iterator() {
//		return new Type4Iterator();
//	}
//
//	@Override
//	public FileData parseAll() throws Exception {
//		for (int i = this.startRow; i < this.endRow; i++) {
//			HashMap<String, String> map = new HashMap<String, String>();
//			map.put(this.columnNames.get(0), this.state);
//			Row row = this.sheet.getRow(i);
//			Cell cell = row.getCell(0);
//			map.put(this.columnNames.get(1),
//					Integer.toString((int) cell.getNumericCellValue()));
//			double average = 0;
//			for (int j = 1; j < 13; j++) {
//				cell = row.getCell(j);
//				if (cell.getCellType() == 0) {
//					average += cell.getNumericCellValue();
//				}
//				if (cell.getCellType() == 1) {
//					average += Double.parseDouble(cell.getStringCellValue());
//				}
//			}
//			average /= 12;
//			map.put(this.columnNames.get(2), Double.toString(average));
//			this.lines.add(new Line(map, this.source));
//		}
//		this.fileData.setLineList(this.lines);
//		return this.fileData;
//	}
//
//	@Override
//	public List<String> getColumnNames() {
//		if (this.columnNames.isEmpty()) {
//			this.columnNames.add("state");
//			this.columnNames.add("year");
//			this.columnNames.add("unemployment rate");
//		}
//		return this.columnNames;
//	}
//
//	public class Type4Iterator implements Iterator<Line> {
//
//		private Workbook workbook;
//		private Sheet sheet;
//		private DataSource source;
//		private List<String> columnNames;
//		private int currentRow;
//		private int endRow;
//		private String state;
//
//		public Type4Iterator() {
//			this.source = Type4Parser.this.source;
//			this.endRow = Type4Parser.this.endRow;
//			this.currentRow = Type4Parser.this.startRow;
//			this.state = Type4Parser.this.state;
//			this.columnNames = Type4Parser.this.columnNames;
//			try {
//				this.init();
//			} catch (InvalidFormatException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		private void init() throws InvalidFormatException, IOException {
//			this.workbook = WorkbookFactory.create(new FileInputStream(this.source
//					.getFileName()));
//			this.sheet = this.workbook.getSheetAt(0);
//		}
//
//		@Override
//		public boolean hasNext() {
//			return (this.currentRow < this.endRow);
//		}
//
//		@Override
//		public Line next() {
//			HashMap<String, String> map = new HashMap<String, String>();
//			map.put(this.columnNames.get(0), this.state);
//			Row row = this.sheet.getRow(this.currentRow++);
//			Cell cell = row.getCell(0);
//			map.put(this.columnNames.get(1),
//					Integer.toString((int) cell.getNumericCellValue()));
//			double average = 0;
//			for (int j = 1; j < 13; j++) {
//				cell = row.getCell(j);
//				if (cell.getCellType() == 0) {
//					average += cell.getNumericCellValue();
//				}
//				if (cell.getCellType() == 1) {
//					average += Double.parseDouble(cell.getStringCellValue());
//				}
//			}
//			average /= 12;
//			map.put(this.columnNames.get(2), Double.toString(average));
//			return new Line(map, this.source);
//		}
//
//		@Override
//		public void remove() {
//			// TODO Auto-generated method stub
//
//		}
//
//	}
//
//}
