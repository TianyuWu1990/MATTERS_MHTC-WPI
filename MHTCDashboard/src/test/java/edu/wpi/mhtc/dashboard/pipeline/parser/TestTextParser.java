
package edu.wpi.mhtc.dashboard.pipeline.parser;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import edu.wpi.mhtc.dashboard.pipeline.data.Category;
import edu.wpi.mhtc.dashboard.pipeline.data.DataSource;
import edu.wpi.mhtc.dashboard.pipeline.data.UnifiedCSVDataSource;

public class TestTextParser {
	
// category 6 in db unemployment rate
//	metrics:
//	15;"minimum rate"
//	16;"maximum rate"
//	17;"new employer rate"


	@Test
	public void test() throws Exception {
		
		Category cat = new Category(6);
		File file = new File("testfiles/TextParser/test.csv");
		
		//missing test file
		
//		assertTrue(file.exists());
//		DataSource source = new UnifiedCSVDataSource(file, cat);
//		IParser parser = new TextParser(source); 
//		
//		parser.parseAll();
//		
//		assertTrue(parser.iterator().hasNext());
	}

}