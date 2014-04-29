package edu.wpi.mhtc.dashboard.pipeline.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import edu.wpi.mhtc.dashboard.pipeline.data.FileData;
import edu.wpi.mhtc.dashboard.pipeline.data.LineData;
import edu.wpi.mhtc.dashboard.pipeline.fileInfo.FileInfo;

public class Type2Parser implements IParser {

	private FileInfo fileInfo;
	private FileData fileData;
	private List<String> columnNames;
	private HashMap<String, String> map;
	private List<LineData> lineDataList;
	private String csvFile;
	private int year;
	private String state;

	public Type2Parser(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
		this.fileData = new FileData(this.fileInfo);
		this.columnNames = new LinkedList<String>();
		this.map = new HashMap<String, String>();
		this.lineDataList = new LinkedList<LineData>();
		this.csvFile = this.fileInfo.getFileName();
		this.year = Integer.parseInt(this.csvFile.substring(
				this.csvFile.lastIndexOf(".") - 4,
				this.csvFile.lastIndexOf(".")));
		this.state = this.csvFile.substring(
				this.csvFile.lastIndexOf(File.separator) + 1,
				this.csvFile.lastIndexOf(File.separator) + 3);
	}

	@Override
	public Iterator<LineData> iterator() {
		if (this.lineDataList.isEmpty()) {
			try {
				this.parseAll();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new Type2Iterator();
	}

	@Override
	public FileData parseAll() throws Exception {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		BufferedReader br = null;
		String line;
		String cvsSplitBy = ",";
		br = new BufferedReader(new FileReader(this.csvFile));
		line = br.readLine();
		while ((line = br.readLine()) != null) {
			String[] values = line.split(cvsSplitBy);
			String awardLevelCode = values[6]
					.substring(1, values[6].length() - 1).trim().toLowerCase();
			int grandTotal = Integer.parseInt(values[7].substring(1,
					values[7].length() - 1).trim());
			if (map.containsKey(awardLevelCode)) {
				map.put(awardLevelCode, map.get(awardLevelCode) + grandTotal);
			} else {
				map.put(awardLevelCode, grandTotal);
			}
		}
		br.close();
		this.map.put("year", Integer.toString(this.year));
		this.map.put("state", this.state);
		for (Entry<String, Integer> entry : map.entrySet()) {
			this.map.put(entry.getKey(), Integer.toString(entry.getValue()));
		}
		this.lineDataList.add(new LineData(this.map, this.fileInfo));
		this.fileData.setLineDataList(this.lineDataList);
		return this.fileData;
	}

	@Override
	public List<String> getColumnNames() {
		if (this.columnNames.isEmpty()) {
			BufferedReader br = null;
			String line;
			String cvsSplitBy = ",";
			try {
				br = new BufferedReader(new FileReader(this.csvFile));
				line = br.readLine();
				while ((line = br.readLine()) != null) {
					String[] value = line.split(cvsSplitBy);
					String columnName = value[6]
							.substring(1, value[6].length() - 1).trim()
							.toLowerCase();
					if (!this.columnNames.contains(columnName)) {
						this.columnNames.add(columnName);
					}
				}
				this.columnNames.add("year");
				this.columnNames.add("state");
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return this.columnNames;
	}

	public class Type2Iterator implements Iterator<LineData> {

		private List<LineData> lineDataList;
		private int currentLine;

		public Type2Iterator() {
			this.lineDataList = Type2Parser.this.lineDataList;
			this.currentLine = 0;
		}

		@Override
		public boolean hasNext() {
			return (this.currentLine < this.lineDataList.size());
		}

		@Override
		public LineData next() {
			this.currentLine++;
			return this.lineDataList.get(0);
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub

		}

	}

}
