package pipeline.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

import data.FileData;
import data.LineData;
import parser.ParserType;
import parser.Type6Parser;
import fileInfo.FileInfo;
import fileInfo.FileType;
import fileInfo.LoadInfo;

public class TestType6Parser {

	private String fileName = "testfiles" + File.separator
			+ "Gov_Tax_Collections_Historical_DB.xls";

	@Test
	public void testGetColumnNames() {
		LoadInfo loadInfo = new LoadInfo(-1, -1, false);
		FileInfo fileInfo = new FileInfo(this.fileName, FileType.xls,
				ParserType.type6, loadInfo);
		try {
			Type6Parser type6Parser = new Type6Parser(fileInfo);
			List<String> list = type6Parser.getColumnNames();
			assertEquals(list.get(0), "year");
			assertEquals(list.get(1), "state");
			assertEquals(list.get(3), "property tax");
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testParseAll() throws Exception {
		LoadInfo loadInfo = new LoadInfo(-1, -1, false);
		FileInfo fileInfo = new FileInfo(this.fileName, FileType.xls,
				ParserType.type6, loadInfo);
		try {
			Type6Parser type6Parser = new Type6Parser(fileInfo);
			FileData fileData = type6Parser.parseAll();
			Iterator<LineData> iter = fileData.getLineDataList().iterator();
			Map<String, String> map = null;
			while (iter.hasNext()) {
				map = iter.next().getMap();
				assertNotNull(map);
			}
			assertEquals(map.get("year"), "1902");
			assertEquals(map.get("state"), "US STATE GOVTS");
			assertEquals(map.get("total taxes"), "156000");
			assertEquals(map.get("property tax"), "82000");
			assertEquals(map.get("Tot Sales & Gr Rec Tax".toLowerCase()),
					"28000");
			assertEquals(map.get("Total Gen Sales Tax".toLowerCase()), "0");
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
				ParserType.type6, loadInfo);
		try {
			Type6Parser type6Parser = new Type6Parser(fileInfo);
			Iterator<LineData> iter = type6Parser.iterator();
			Map<String, String> map = new HashMap<String, String>();
			while (iter.hasNext()) {
				map = iter.next().getMap();
				assertNotNull(map);
			}
			assertEquals(map.get("year"), "1902");
			assertEquals(map.get("state"), "US STATE GOVTS");
			assertEquals(map.get("total taxes"), "156000");
			assertEquals(map.get("property tax"), "82000");
			assertEquals(map.get("Tot Sales & Gr Rec Tax".toLowerCase()),
					"28000");
			assertEquals(map.get("Total Gen Sales Tax".toLowerCase()), "0");
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
