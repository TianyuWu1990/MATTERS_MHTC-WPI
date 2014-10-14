package pipeline.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

import parser.ParserType;
import parser.Type5Parser;
import data.FileData;
import data.LineData;
import fileInfo.FileInfo;
import fileInfo.FileType;
import fileInfo.LoadInfo;

public class TestType5Parser {

	public String fileName = "testfiles" + File.separator
			+ "personal_income_percapita.xls";

	@Test
	public void testParseAll() throws Exception {
		LoadInfo loadInfo = new LoadInfo(-1, -1, false);
		FileInfo fileInfo = new FileInfo(this.fileName, FileType.xls,
				ParserType.type5, loadInfo);
		try {
			Type5Parser type5Parser = new Type5Parser(fileInfo);
			FileData fileData = type5Parser.parseAll();
			Iterator<LineData> iter = fileData.getLineDataList().iterator();
			Map<String, String> map = null;
			while (iter.hasNext()) {
				map = iter.next().getMap();
				assertNotNull(map);
			}
			assertEquals(map.get("year"), "2013");
			assertEquals(map.get("state"), "Washington");
			assertEquals(map.get("Per capita personal income (dollars)"
					.toLowerCase()), "47031");

		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testIterator() throws Exception {
		LoadInfo loadInfo = new LoadInfo(-1, -1, false);
		FileInfo fileInfo = new FileInfo(this.fileName, FileType.xls,
				ParserType.type5, loadInfo);
		try {
			Type5Parser type5Parser = new Type5Parser(fileInfo);
			Iterator<LineData> iter = type5Parser.iterator();
			Map<String, String> map = new HashMap<String, String>();
			while (iter.hasNext()) {
				map = iter.next().getMap();
				assertNotNull(map);
			}
			assertEquals(map.get("year"), "2013");
			assertEquals(map.get("state"), "Washington");
			assertEquals(map.get("Per capita personal income (dollars)"
					.toLowerCase()), "47031");
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}