package pipeline.cleaner;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.wpi.mhtc.pipeline.cleaner.YearCleaner;

public class TestYearCleaner {

	YearCleaner cleaner = new YearCleaner();
	@Test
	public void test() {
		assertEquals(cleaner.clean("2013"), "2013");
		assertEquals(cleaner.clean("5555"), null);
	}

}
