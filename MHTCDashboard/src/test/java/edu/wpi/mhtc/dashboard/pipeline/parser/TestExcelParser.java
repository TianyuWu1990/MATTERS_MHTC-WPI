//package edu.wpi.mhtc.dashboard.pipeline.parser;
//
//import static org.junit.Assert.assertNotNull;
//
//import java.io.File;
//import java.util.Iterator;
//import java.util.Map;
//
//import org.junit.Test;
//
//import edu.wpi.mhtc.dashboard.pipeline.data.FileType;
//import edu.wpi.mhtc.dashboard.pipeline.data.LoadInfo;
//import edu.wpi.mhtc.dashboard.pipeline.parser.IParser;
//import edu.wpi.mhtc.dashboard.pipeline.parser.ParserFactory;
//import edu.wpi.mhtc.dashboard.pipeline.parser.ParserType;
//
//@SuppressWarnings("unused")
//public class TestExcelParser {
//
//	public String fileName = "testfiles" + File.separator + "business friendliness ranking.xlsx";
//	
//	@Test
//	public void testFile1ParseAll() throws Exception {
//		LoadInfo loadInfo = new LoadInfo(2, 52, true);
//		FileInfo fileInfo = new FileInfo(this.fileName, FileType.xlsx,
//				ParserType.excel, loadInfo);
//		IParser parser = ParserFactory.getInstance(fileInfo);
//		FileData fileData = parser.parseAll();
//		Iterator<LineData> iter = fileData.getLineDataList().iterator();
//		Map<String, String> map = iter.next().getMap();
//		assertNotNull(map);
////		assertEquals(map.get("state"), "South Dakota");
////		assertEquals(map.get("year"), "2013");
////		assertEquals(map.get("business friendliness"), "2");
//		while(iter.hasNext()){
//			map = iter.next().getMap();
//			assertNotNull(map);
//		}
////		assertEquals(map.get("state"), "Hawaii");
////		assertEquals(map.get("year"), "2013");
////		assertEquals(map.get("business friendliness"), "40");
//	}
//	
//}
