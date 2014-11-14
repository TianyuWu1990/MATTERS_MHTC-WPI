package edu.wpi.mhtc.dashboard.pipeline.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.SQLException;

import org.junit.Test;

import edu.wpi.mhtc.dashboard.pipeline.main.MHTCException;

public class TestCategory {
	
	private Category c;
	
	// Should probably create a mock object, but this at least allows us to test
	// within our local database
	@Test
	public void testConstructor() {
		try {
			// 37 is the ID for category Cost
			c = new Category(37);
		} catch (Exception e) {
			fail("Something went wrong!");
		}
		
		assertEquals("Cost", c.getName());
	}
	
	@Test(expected=MHTCException.class)
	public void testConstructorWithBadID() throws SQLException, CategoryException {
		c = new Category(1000);
	}

}
