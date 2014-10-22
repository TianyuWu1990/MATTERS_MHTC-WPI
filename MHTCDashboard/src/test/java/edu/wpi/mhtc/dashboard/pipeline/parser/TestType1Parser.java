//package edu.wpi.mhtc.dashboard.pipeline.parser;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
//import org.junit.Test;
//
//import parser.ParserType;
//import parser.Type1Parser;
//import data.FileData;
//import data.LineData;
//import fileInfo.FileInfo;
//import fileInfo.FileType;
//import fileInfo.LoadInfo;
//
//public class TestType1Parser {
//
//	public String fileName1 = "testfiles" + File.separator
//			+ "state_unemp_rate_2008-2013.xls";
//	public String fileName2 = "testfiles" + File.separator
//			+ "Gov_Tax_Collections2012ByCategory.xls";
//
//	@Test
//	public void testFile1ParseAll() throws Exception {
//		LoadInfo loadInfo = new LoadInfo(4, 57, true);
//		FileInfo fileInfo = new FileInfo(this.fileName1, FileType.xls,
//				ParserType.type1, loadInfo);
//		Type1Parser type1Parser = new Type1Parser(fileInfo);
//		FileData fileData = type1Parser.parseAll();
//		Iterator<LineData> iter = fileData.getLineDataList().iterator();
//		Map<String, String> map = null;
//		while(iter.hasNext()){
//			map = iter.next().getMap();
//			assertNotNull(map);
//		}
//		assertEquals(map.get("state"), "Wyoming");
//		assertEquals(map.get("wages subject to tax"), "21500");
//		assertEquals(map.get("minimum rate"), "0.0027");
//		assertEquals(map.get("maximum rate"), "0.0903");
//		assertEquals(map.get("new employer rate"), "0.0153");
//		assertEquals(map.get("year"), "2008");
//	}
//	
//	@Test
//	public void testFile1Iterator() throws InvalidFormatException, IOException{
//		LoadInfo loadInfo = new LoadInfo(4, 57, true);
//		FileInfo fileInfo = new FileInfo(this.fileName1, FileType.xls,
//				ParserType.type1, loadInfo);
//		Type1Parser type1Parser = new Type1Parser(fileInfo);
//		Iterator<LineData> iter = type1Parser.iterator();
//		List<String> list = type1Parser.getColumnNames();
//		for(String name : list){
//			System.out.println(name);
//		}
//		Map<String, String> map = null;
//		while(iter.hasNext()){
//			map = iter.next().getMap();
//			assertNotNull(map);
//		}
//		assertEquals(map.get("state"), "Wyoming");
//		assertEquals(map.get("wages subject to tax"), "21500");
//		assertEquals(map.get("minimum rate"), "0.0027");
//		assertEquals(map.get("maximum rate"), "0.0903");
//		assertEquals(map.get("new employer rate"), "0.0153");
//		assertEquals(map.get("year"), "2008");
//	}
//
//	@Test
//	public void testFile2ParseAll() throws Exception {
//		LoadInfo loadInfo = new LoadInfo(4, 55, true);
//		FileInfo fileInfo = new FileInfo(this.fileName2, FileType.xls,
//				ParserType.type1, loadInfo);
//		Type1Parser type1Parser = new Type1Parser(fileInfo);
//		FileData fileData = type1Parser.parseAll();
//		Iterator<LineData> iter = fileData.getLineDataList().iterator();
//		Map<String, String> map = null;
//		while(iter.hasNext()){
//			map = iter.next().getMap();
//			assertNotNull(map);
//		}
//		assertEquals(map.get("state"), "Wyoming");
//		assertEquals(map.get("total taxes"), "2,550,991");
//		assertEquals(map.get("property taxes"), "316,734");
//		assertEquals(map.get("sales and gross receipts taxes"), "1,120,203");
//		assertEquals(map.get("license taxes"), "140,045");
//		assertEquals(map.get("year"), "2012");
//	}
//	
//	@Test
//	public void testFile2Iterator() throws InvalidFormatException, IOException{
//		LoadInfo loadInfo = new LoadInfo(4, 55, true);
//		FileInfo fileInfo = new FileInfo(this.fileName2, FileType.xls,
//				ParserType.type1, loadInfo);
//		Type1Parser type1Parser = new Type1Parser(fileInfo);
//		Iterator<LineData> iter = type1Parser.iterator();
//		Map<String, String> map = null;
//		while(iter.hasNext()){
//			map = iter.next().getMap();
//			assertNotNull(map);
//		}
//		assertEquals(map.get("state"), "Wyoming");
//		assertEquals(map.get("total taxes"), "2,550,991");
//		assertEquals(map.get("property taxes"), "316,734");
//		assertEquals(map.get("sales and gross receipts taxes"), "1,120,203");
//		assertEquals(map.get("license taxes"), "140,045");
//		assertEquals(map.get("year"), "2012");
//	}
//	
//}
