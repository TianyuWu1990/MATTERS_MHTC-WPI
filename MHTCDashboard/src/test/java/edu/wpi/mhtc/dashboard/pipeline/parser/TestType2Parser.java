package edu.wpi.mhtc.dashboard.pipeline.parser;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import parser.ParserType;
import parser.Type2Parser;
import data.FileData;
import data.LineData;
import fileInfo.FileInfo;
import fileInfo.FileType;
import fileInfo.LoadInfo;

public class TestType2Parser {
	public String fileName = "testfiles" + File.separator + "CA_2009.csv";

	@Test
	public void testParseAll() {
		LoadInfo loadInfo = new LoadInfo(-1, -1, false);
		FileInfo fileInfo = new FileInfo(this.fileName, FileType.csv,
				ParserType.type2, loadInfo);
		Type2Parser type2Parser = new Type2Parser(fileInfo);
		try {
			FileData fileData = type2Parser.parseAll();
			Iterator<LineData> iter = fileData.getLineDataList().iterator();
			Map<String, String> map = new HashMap<String, String>();
			while (iter.hasNext()) {
				map = iter.next().getMap();
				assertNotNull(map);
				for (Entry<String, String> entry : map.entrySet()) {
					System.out
							.println(entry.getKey() + "\t" + entry.getValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testIterator() {
		LoadInfo loadInfo = new LoadInfo(-1, -1, false);
		FileInfo fileInfo = new FileInfo(this.fileName, FileType.csv,
				ParserType.type2, loadInfo);
		Type2Parser type2Parser = new Type2Parser(fileInfo);
		LineData lineData;
		Map<String, String> map = new HashMap<String, String>();

		Iterator<LineData> iter = type2Parser.iterator();
		while (iter.hasNext()) {
			lineData = iter.next();
			map = lineData.getMap();
			assertNotNull(map);
			for (Entry<String, String> entry : map.entrySet()) {
				System.out.println(entry.getKey() + "\t" + entry.getValue());
			}
		}
	}
}
