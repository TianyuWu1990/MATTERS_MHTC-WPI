package edu.wpi.mhtc.pipeline.parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.wpi.mhtc.pipeline.data.Category;
import edu.wpi.mhtc.pipeline.data.CategoryException;
import edu.wpi.mhtc.pipeline.data.DataSource;
import edu.wpi.mhtc.pipeline.data.FileType;
import edu.wpi.mhtc.pipeline.data.Line;

public class CSVParser implements IParser {

	public DataSource source;
	private LinkedList<String> columnNames;
	private List<Line> lines;
	private String csvFile;

	public CSVParser(DataSource source) {
		this.source = source;
		this.columnNames = new LinkedList<String>();
		this.lines = new LinkedList<Line>();
	}

	@Override
	public void validateMetrics(Category category) throws CategoryException{
		if (this.columnNames.isEmpty()) {
			if (this.source.getFileType() == FileType.csv) {
				this.csvFile = this.source.getFileName();
			}
			BufferedReader br = null;
			String line;
			String cvsSplitBy = ",";
			try {
				br = new BufferedReader(new FileReader(csvFile));
				line = br.readLine();
				String[] value = line.split(cvsSplitBy);
				for (int i = 0; i < value.length; i++) {
					this.columnNames.add(value[i].substring(1,
							value[i].length() - 1).toLowerCase());
					// this.columnNames.add(value[i].toLowerCase());
				}
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean parseAll() throws Exception {
		this.validateMetrics(source.getCategory());
		if (this.source.getFileType() == FileType.csv) {
			this.csvFile = this.source.getFileName();
		}
		BufferedReader br = null;
		String line;
		String cvsSplitBy = ",";
		br = new BufferedReader(new FileReader(csvFile));
		line = br.readLine();
		while ((line = br.readLine()) != null) {
			HashMap<String, String> table = new HashMap<String, String>();
			String[] s = line.split(cvsSplitBy, -1);
			for (int k = 0; k < s.length; k++) {
				if (s[k].isEmpty()) {
					table.put(this.columnNames.get(k), "NULL");
				} else {
					table.put(this.columnNames.get(k),
							s[k].substring(1, s[k].length() - 1));
				}
			}
			this.lines.add(new Line(table, source));
		}
		br.close();
		this.fileData.setLineList(this.lines);
		return this.fileData;
	}

	@Override
	public Iterator<Line> iterator() {
		try {
			return new CSVIterator(source);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	private class CSVIterator implements Iterator<Line> {
		public DataSource source;

		private LinkedList<String> columnNames;
		private int currentRow;
		private int totalRows;
		private String csvFile;
		private BufferedReader br;

		public CSVIterator(DataSource source) throws IOException {
			this.source = source;

			if (this.source.getFileType() == FileType.csv) {
				this.csvFile = this.source.getFileName();
			}
			this.br = new BufferedReader(new FileReader(csvFile));
			this.totalRows = 0;
			while (br.readLine() != null) {
				this.totalRows++;
			}
			this.br.close();

			this.br = new BufferedReader(new FileReader(csvFile));

			if (CSVParser.this.columnNames.isEmpty()) {
				this.columnNames = new LinkedList<String>();

				String line;
				String cvsSplitBy = ",";
				line = br.readLine();
				String[] value = line.split(cvsSplitBy);
				for (int i = 0; i < value.length; i++) {
					// this.rowNames.add(value[i].substring(1,
					// value[i].length() - 1).toLowerCase());
					this.columnNames.add(value[i].toLowerCase());
				}
			} else {
				this.columnNames = CSVParser.this.columnNames;
			}
			this.currentRow = 2;
		}

		public boolean hasNext() {
			return (this.currentRow <= this.totalRows);
		}

		@Override
		public Line next() {

			String line = "";
			String cvsSplitBy = ",";
			try {
				line = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			HashMap<String, String> map = new HashMap<String, String>();
			String[] s = line.split(cvsSplitBy, -1);

			for (int k = 0; k < s.length; k++) {
				if (s[k].isEmpty()) {
					map.put(this.columnNames.get(k), "NULL");
				} else {
					// table.put(this.rowNames.get(k),
					// s[k].substring(1, s[k].length() - 1));
					map.put(this.columnNames.get(k),
							s[k].substring(1, s[k].length()).trim());
				}
			}
			this.currentRow++;

			return new Line(map, this.source);
		}

		@Override
		public void remove() {

		}

	}


	 

}
