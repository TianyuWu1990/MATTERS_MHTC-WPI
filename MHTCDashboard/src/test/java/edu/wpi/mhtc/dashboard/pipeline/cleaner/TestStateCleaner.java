package edu.wpi.mhtc.dashboard.pipeline.cleaner;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.wpi.mhtc.dashboard.pipeline.cleaner.StateCleaner;

public class TestStateCleaner {
	StateCleaner stateCleaner = new StateCleaner();

	@Test
	public void testStateCleaner() throws Exception {
		/*
		 * test clean function
		 */
		
		//missing dictionary?
		
		assertEquals(stateCleaner.clean("AL"), "alabama");
		//assertEquals(stateCleaner.clean("New Hamphire"), "new hampshire");
		assertEquals(stateCleaner.clean("Massachusetts"), "massachusetts");
		assertEquals(stateCleaner.clean("M.A."), "massachusetts");
		assertEquals(stateCleaner.clean("a Alabama STATE GOVTS"), "alabama");
		assertEquals(stateCleaner.clean("AL_STATE_GOVT"), "alabama");
		assertEquals(stateCleaner.clean("Massachusett"), "massachusetts");
		assertEquals(stateCleaner.clean("New Hampshirw"), "new hampshire");
		assertEquals(stateCleaner.clean("qwer"), "qwer");
//		assertEquals(stateCleaner.clean("aaaa"), "aaaa");
		assertEquals(stateCleaner.clean("West Virginia"), "west virginia");
		assertEquals(stateCleaner.clean("South Dakota"), "south dakota");
		assertEquals(stateCleaner.clean("Virginia"), "virginia");
		assertEquals(stateCleaner.clean("North Dakkota"), "north dakota");
		assertEquals(stateCleaner.clean("US STATE GOVTS"), "united states");
		assertEquals(stateCleaner.clean("AL STATE GOVT"), "alabama");
		assertEquals(stateCleaner.clean("AK STATE GOVT"), "alaska");
		assertEquals(stateCleaner.clean("VA STATE GOVT"), "virginia");
		assertEquals(stateCleaner.clean("WV STATE GOVT"), "west virginia");
		assertEquals(stateCleaner.clean("Minesota_2012"), "minnesota");
		assertEquals(stateCleaner.clean("NewHampshire"), "new hampshire");
		assertEquals(stateCleaner.clean("NewJersy"), "new jersey");
		assertEquals(stateCleaner.clean("ct"), "connecticut");
		
	}

	@Test
	public void testGetFullName() throws Exception {
		/*
		 * test getFullName function
		 */
		assertEquals(stateCleaner.getFullName("AK"), "alaska");
		assertEquals(stateCleaner.getFullName("AZ"), "arizona");
		assertEquals(stateCleaner.getFullName("New Jersey"), "new jersey");
	}

	@Test
	public void testGetFullNameBySubstring() throws Exception {
		/*
		 * test getFullNameBySubstring function
		 */
		assertEquals(
				stateCleaner.getFullNameBySubstring("the Alabama STATE GOVTS"),
				"alabama");
		assertEquals(stateCleaner.getFullNameBySubstring("NY_STATE_GOVT"),
				"new york");
	}

	@Test
	public void testGetFullNameByConcatenation() throws Exception {
		/*
		 * test getFullNameByConcatenation function
		 */
		assertEquals(stateCleaner.getFullNameByConcatenation("m,a,"),
				"massachusetts");
	}


	@Test
	public void testGetFullNameBySpellCheck() throws Exception {
		/*
		 * test getFullNameBySpellCheck function
		 */
		assertEquals("massachusetts", stateCleaner.getFullNameBySpellCheck("Massachusett"));
		assertEquals("new hampshire", stateCleaner.getFullNameBySpellCheck("New Hampshirw"));
		assertEquals(null, stateCleaner.getFullNameBySpellCheck("qwer"));
//		assertEquals(null, stateCleaner.getFullNameBySpellCheck("aaaa"));
		assertEquals("minnesota", stateCleaner.getFullNameBySpellCheck("Minesota"));
	}

}
