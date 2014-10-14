package pipeline.config;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cleaner.NumericCleaner;


public class TestNumericCleaner {
	NumericCleaner numericCleaner = new NumericCleaner();
	@Test
	public void testNumericCleaner() throws Exception {
		
		assertEquals(numericCleaner.clean("7982216.12"),"7982216.12");
		assertEquals(numericCleaner.clean("0"),"0");
		assertEquals(numericCleaner.clean("10.66676%"),"0.1066676");
		assertEquals(numericCleaner.clean("$2400.8"),"2400.8");
		assertEquals(numericCleaner.clean("-1200"),"-1200");
		assertEquals(numericCleaner.clean("#123"),"-1");
		assertEquals(numericCleaner.clean("*******"),"-1");
		assertEquals(numericCleaner.clean(""),"-1");
		assertEquals(numericCleaner.clean("X"),"-1");
		assertEquals(numericCleaner.clean("20,200 or $21,700 for high tax group employers"),"-1");
		assertEquals(numericCleaner.clean("$200,000,000"),"200000000");
	}
	@Test
	public void testCleanComma() {
		assertEquals(numericCleaner.CleanComma("200,000,000"),"200000000");
	}
	@Test
	public void testCleanCharValue() throws Exception {
		assertEquals(numericCleaner.CleanCharValue("total 200"),"-1");
		assertEquals(numericCleaner.CleanCharValue("20,200 or $21,700 for high tax group employers"),"-1");
		assertEquals(numericCleaner.CleanCharValue("$200"),"$200");
		assertEquals(numericCleaner.CleanCharValue("10.66676%"),"10.66676%");
	}
	@Test
	public void testCleanDollarValue() throws Exception {
		assertEquals(numericCleaner.CleanDollarValue("$2400.8dollar"),"-1");
		assertEquals(numericCleaner.CleanDollarValue("$2400.8"),"2400.8");
		assertEquals(numericCleaner.CleanDollarValue("10.66676%"),"10.66676%");
		assertEquals(numericCleaner.CleanDollarValue("-1"),"-1");
	}
	@Test
	public void testCleanPercentValue() throws Exception {
		assertEquals(numericCleaner.CleanPercentValue("10.66676%"),"0.1066676");
		assertEquals(numericCleaner.CleanPercentValue("a12%"),"-1");
		assertEquals(numericCleaner.CleanPercentValue("-1"),"-1");
	}
	@Test
	public void testCleanOtherSign() throws Exception {
		assertEquals(numericCleaner.CleanOtherSign("10.66"),"10.66");
		assertEquals(numericCleaner.CleanOtherSign("a^&12%"),"-1");
		assertEquals(numericCleaner.CleanOtherSign("$12"),"-1");
		assertEquals(numericCleaner.CleanOtherSign("-1"),"-1");
	}
	
}
