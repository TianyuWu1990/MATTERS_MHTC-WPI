package edu.wpi.mhtc.dashboard.pipeline.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

import parser.ParserType;
import parser.Type7Parser;
import data.FileData;
import data.LineData;
import fileInfo.FileInfo;
import fileInfo.FileType;
import fileInfo.LoadInfo;

public class TestType7Parser {

	private String fileName = "testfiles" + File.separator
			+ "Tech_Employment_2003_2006_2008.xls";

	@Test
	public void testGetColumnNames() throws InvalidFormatException, IOException {
		LoadInfo loadInfo = new LoadInfo(-1, -1, false);
		FileInfo fileInfo = new FileInfo(this.fileName, FileType.xls,
				ParserType.type7, loadInfo);
		Type7Parser type7Parser = new Type7Parser(fileInfo);
		List<String> list = type7Parser.getColumnNames();
		for (String s : list) {
			System.out.println(s);
		}
		assertEquals(list.size(), 5);
		assertTrue(list.contains("Employment in high-technology establishments"
				.toLowerCase()));
		assertTrue(list.contains("Total employment".toLowerCase()));
		assertTrue(list.contains("High-Technology total employment (%)"
				.toLowerCase()));
		assertTrue(list.contains("state"));
		assertTrue(list.contains("year"));
	}

	@Test
	public void testParserAll() throws Exception {
		LoadInfo loadInfo = new LoadInfo(-1, -1, false);
		FileInfo fileInfo = new FileInfo(this.fileName, FileType.xls,
				ParserType.type7, loadInfo);
		Type7Parser type7Parser = new Type7Parser(fileInfo);
		FileData fileData = type7Parser.parseAll();
		Iterator<LineData> iter = fileData.getLineDataList().iterator();
		Map<String, String> map = null;
		for (int i = 0; i < 156; i++) {
			map = iter.next().getMap();
			assertNotNull(map);
		}
		assertEquals(map.get("state"), "Wyoming");
		assertEquals(map.get("year"), "2008");
		assertEquals(map.get("Employment in high-technology establishments"
				.toLowerCase()), "18423");
		assertEquals(map.get("Total employment".toLowerCase()), "221835");
		assertEquals(
				map.get("High-Technology total employment (%)".toLowerCase()),
				"8.3");
		while (iter.hasNext()) {
			map = iter.next().getMap();
			assertNotNull(map);
		}
		assertEquals(map.get("state"), "Puerto Rico");
		assertEquals(map.get("year"), "2008");
		assertEquals(map.get("Employment in high-technology establishments"
				.toLowerCase()), "NA");
		assertEquals(map.get("Total employment".toLowerCase()), "NA");
		assertEquals(
				map.get("High-Technology total employment (%)".toLowerCase()),
				"NA");
	}

	@Test
	public void testIterator() throws InvalidFormatException, IOException {
		LoadInfo loadInfo = new LoadInfo(-1, -1, false);
		FileInfo fileInfo = new FileInfo(this.fileName, FileType.xls,
				ParserType.type7, loadInfo);
		Type7Parser type7Parser = new Type7Parser(fileInfo);
		Iterator<LineData> iter = type7Parser.iterator();
		Map<String, String> map = null;
		for (int i = 0; i < 156; i++) {
			map = iter.next().getMap();
			assertNotNull(map);
		}
		assertEquals(map.get("state"), "Wyoming");
		assertEquals(map.get("year"), "2008");
		assertEquals(map.get("Employment in high-technology establishments"
				.toLowerCase()), "18423");
		assertEquals(map.get("Total employment".toLowerCase()), "221835");
		assertEquals(
				map.get("High-Technology total employment (%)".toLowerCase()),
				"8.3");
		while (iter.hasNext()) {
			map = iter.next().getMap();
			assertNotNull(map);
		}
		assertEquals(map.get("state"), "Puerto Rico");
		assertEquals(map.get("year"), "2008");
		assertEquals(map.get("Employment in high-technology establishments"
				.toLowerCase()), "NA");
		assertEquals(map.get("Total employment".toLowerCase()), "NA");
		assertEquals(
				map.get("High-Technology total employment (%)".toLowerCase()),
				"NA");
	}

}
