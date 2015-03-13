//package edu.wpi.mhtc.dashboard.pipeline.parser;
//
//import static org.junit.Assert.assertEquals;
//
//import java.io.File;
//import java.io.IOException;
//
//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import edu.wpi.mhtc.dashboard.pipeline.data.Category;
//import edu.wpi.mhtc.dashboard.pipeline.data.CategoryException;
//import edu.wpi.mhtc.dashboard.pipeline.data.UnifiedDataSource;
//
//public class TestUnifiedParser {
//	
//	//category Id = 14
//		//parent Id = 29
//		//metric = "Top States for Business: Friendliness"
//		//metric Id = 29
//		UnifiedDataSource badfile1; 
//		UnifiedDataSource badfile2;
//		UnifiedDataSource badfile3;
//		UnifiedDataSource badfile4;
//		UnifiedDataSource badfile5;
//		UnifiedDataSource badfile6;
//
//		UnifiedDataSource goodfile1;
//		UnifiedDataSource goodfile2;
//		UnifiedDataSource goodfile3;
//		
//		Category category;
//		UnifiedParser parser;	
//		
//		
//		@Before
//		public void setUp() throws Exception {
//			
//			category = new Category(14);
//				
//			badfile1 = new UnifiedDataSource (new File("testfiles" + File.separator + "business friendliness ranking BAD METRIC NAME.xlsx"), category); 
//			badfile2 = new UnifiedDataSource (new File("testfiles" + File.separator + "business friendliness ranking BAD HEADER.xlsx"), category);
//			
//			badfile3 = new UnifiedDataSource (new File("testfiles" + File.separator + "business friendliness ranking BAD YEAR 11111.xls"), category);
//			badfile4 = new UnifiedDataSource (new File("testfiles" + File.separator + "business friendliness ranking BAD STATE NAME JUPITER.xls"), category);
//			badfile5 = new UnifiedDataSource (new File("testfiles" + File.separator + "business friendliness ranking BAD VALUE.xls"), category);
//				
//			goodfile1 = new UnifiedDataSource (new File("testfiles" + File.separator + "business friendliness ranking GOOD BLANK ROWS.xls"), category);
//			goodfile2 = new UnifiedDataSource (new File("testfiles" + File.separator + "business friendliness ranking GOOD EXTRA COLUMNS.xlsx"), category);
//			goodfile3 = new UnifiedDataSource (new File("testfiles" + File.separator + "business friendliness ranking GOOD EXTRA HEAD FOOT.xls"), category);
//			
//		}
//		
//		@After
//		public void tearDown() throws Exception {
//		}
//		
//		@Test
//		public void testValidateMetrics() throws Exception{
//			parser = new UnifiedParser(goodfile1);
//			parser.validateMetrics(category);
//			assertEquals(0, (int) parser.columnNames.get("state"));
//			assertEquals(1, (int) parser.columnNames.get("year"));
//			assertEquals(2, (int) parser.columnNames.get("Top States for Business: Friendliness".toLowerCase()));
//		}
//
//		@Test(expected = CategoryException.class)  
//		public void testBadMetric() throws CategoryException{
//			try {
//				parser = new UnifiedParser(badfile1);
//			} catch (UnifiedFormatException | InvalidFormatException
//					| IOException e) {
//				e.printStackTrace();
//			}
//			
//			
////			file contains bad metric, throws exception
//			parser.validateMetrics(category);
//		}
//		
//		@Test(expected = UnifiedFormatException.class)  
//		public void testBadHeader() throws UnifiedFormatException{
//			try {
//				parser = new UnifiedParser(badfile2);
//			} catch (CategoryException | InvalidFormatException | IOException e) {
//				e.printStackTrace();
//			}
//			
////			file contains "Location" not "State" in header, throws exception
//			parser.findHeader(); 
//		}
//
//		@Test
//		public void testGoodFiles() throws Exception {
//			parser = new UnifiedParser(goodfile1);
//			parser.parseAll();
//			assertEquals(50, parser.getLines().size());
//			parser = new UnifiedParser(goodfile2);
//			parser.parseAll();
//			assertEquals(50, parser.getLines().size());
//			parser = new UnifiedParser(goodfile3);
//			parser.parseAll();
//			assertEquals(50, parser.getLines().size());
////			TODO: What about those "bad row" messages??
//		}
//
//}
