package edu.wpi.mhtc.dashboard.pipeline.parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.wpi.mhtc.dashboard.pipeline.data.FileData;
import edu.wpi.mhtc.dashboard.pipeline.data.LineData;
import edu.wpi.mhtc.dashboard.pipeline.fileInfo.FileInfo;
import edu.wpi.mhtc.dashboard.pipeline.fileInfo.FileType;

public class CSVParser implements IParser {

	public FileInfo fileInfo;
	private FileData fileData;
	private LinkedList<String> columnNames;
	private List<LineData> lineDataList;
	private String csvFile;

	public CSVParser(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
		this.fileData = new FileData(this.fileInfo);
		this.columnNames = new LinkedList<String>();
		this.lineDataList = new LinkedList<LineData>();
	}

	@Override
	public List<String> getColumnNames() {
		if (this.columnNames.isEmpty()) {
			if (this.fileInfo.getFileType() == FileType.csv) {
				this.csvFile = this.fileInfo.getFileName();
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
		return this.columnNames;
	}

	@Override
	public FileData parseAll() throws Exception {
		this.getColumnNames();
		if (this.fileInfo.getFileType() == FileType.csv) {
			this.csvFile = this.fileInfo.getFileName();
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
			this.lineDataList.add(new LineData(table, fileInfo));
		}
		br.close();
		this.fileData.setLineDataList(this.lineDataList);
		return this.fileData;
	}

	@Override
	public Iterator<LineData> iterator() {
		try {
			return new CSVIterator(fileInfo);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	private class CSVIterator implements Iterator<LineData> {
		public FileInfo fileInfo;

		private LinkedList<String> columnNames;
		private int currentRow;
		private int totalRows;
		private String csvFile;
		private BufferedReader br;

		public CSVIterator(FileInfo fileInfo) throws IOException {
			this.fileInfo = fileInfo;

			if (this.fileInfo.getFileType() == FileType.csv) {
				this.csvFile = this.fileInfo.getFileName();
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
		public LineData next() {

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

			return new LineData(map, this.fileInfo);
		}

		@Override
		public void remove() {

		}

	}

}
