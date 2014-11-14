package edu.wpi.mhtc.dashboard.pipeline.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

import parser.ParserType;
import parser.Type4Parser;
import data.FileData;
import data.LineData;
import fileInfo.FileInfo;
import fileInfo.FileType;
import fileInfo.LoadInfo;

public class TestType4Parser {
	public String fileName = "testfiles" + File.separator
			+ "California_UnemploymentRate.xls";

	@Test
	public void testParseAll() {
		LoadInfo loadInfo = new LoadInfo(-1, -1, false);
		FileInfo fileInfo = new FileInfo(this.fileName, FileType.xls,
				ParserType.type4, loadInfo);
		try {
			Type4Parser type4Parser = new Type4Parser(fileInfo);
			FileData fileData = type4Parser.parseAll();
			Iterator<LineData> iter = fileData.getLineDataList().iterator();
			Map<String, String> map = null;
			while(iter.hasNext()){
				map = iter.next().getMap();
				assertNotNull(map);
			}
			assertEquals(map.get("state"), "California");
			assertEquals(map.get("year"), "2013");
			assertEquals(map.get("unemployment rate"), "8.891666666666667");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testIterator() {
		LoadInfo loadInfo = new LoadInfo(-1, -1, false);
		FileInfo fileInfo = new FileInfo(this.fileName, FileType.xls,
				ParserType.type4, loadInfo);
		try {
			Type4Parser type4Parser = new Type4Parser(fileInfo);
			Iterator<LineData> iter = type4Parser.iterator();
			Map<String, String> map = new HashMap<String, String>();
			while (iter.hasNext()) {
				map = iter.next().getMap();
				assertNotNull(map);
			}
			assertEquals(map.get("state"), "California");
			assertEquals(map.get("year"), "2013");
			assertEquals(map.get("unemployment rate"), "8.891666666666667");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
