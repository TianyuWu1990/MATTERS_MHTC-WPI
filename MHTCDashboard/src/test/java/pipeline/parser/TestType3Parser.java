package pipeline.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

import parser.ParserType;
import parser.Type3Parser;
import data.FileData;
import data.LineData;
import fileInfo.FileInfo;
import fileInfo.FileType;
import fileInfo.LoadInfo;

public class TestType3Parser {

	public String fileName = "testfiles" + File.separator
			+ "Employment_state_2011_dl.xls";

	@Test
	public void testParseAll() {
		LoadInfo loadInfo = new LoadInfo(-1, -1, false);
		FileInfo fileInfo = new FileInfo(this.fileName, FileType.xls,
				ParserType.type3, loadInfo);
		try {
			Type3Parser type3Parser = new Type3Parser(fileInfo);
			FileData fileData = type3Parser.parseAll();
			Iterator<LineData> iter = fileData.getLineDataList().iterator();
			Map<String, String> map = null;
			while (iter.hasNext()) {
				map = iter.next().getMap();
				assertNotNull(map);
			}
			assertEquals(map.get("state"), "VI");
			assertEquals(map.get("tot_emp"), "43310");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testIterator() {
		LoadInfo loadInfo = new LoadInfo(-1, -1, false);
		FileInfo fileInfo = new FileInfo(this.fileName, FileType.xls,
				ParserType.type3, loadInfo);
		try {
			Type3Parser type3Parser = new Type3Parser(fileInfo);
			Iterator<LineData> iter = type3Parser.iterator();
			Map<String, String> map = new HashMap<String, String>();
			while (iter.hasNext()) {
				map = iter.next().getMap();
				assertNotNull(map);
			}
			assertEquals(map.get("state"), "VI");
			assertEquals(map.get("tot_emp"), "43310");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
