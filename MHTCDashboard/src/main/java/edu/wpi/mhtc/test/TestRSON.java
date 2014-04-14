package edu.wpi.mhtc.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.mhtc.rson.ParseException;
import edu.wpi.mhtc.rson.RSON;

public class TestRSON {

	static List<TestRSONHelperClass> list;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		list = new LinkedList<TestRSONHelperClass>();
		list.add(new TestRSONHelperClass(1, "ted"));
		list.add(new TestRSONHelperClass(8675309, "Jenny"));
		list.add(new TestRSONHelperClass(3, "Oishi"));
		list.add(new TestRSONHelperClass(4, "HarryPotter"));
		list.add(new TestRSONHelperClass());
	}
	
	@Test
	public void testMap() throws ParseException
	{
		Map<String, TestRSONHelperClass> map = new HashMap<String, TestRSONHelperClass>();
		for(TestRSONHelperClass trhc : list)
		{
			if (trhc.getName() == null)
			{
				map.put("noname", trhc);
			}
			else
			{
				map.put(trhc.getName(), trhc);
			}
		}
		
		String perm1 = "{HarryPotter:{accountID:4, name:\"HarryPotter\"}, Jenny:{accountID:8675309, name:\"Jenny\"}, Oishi:{accountID:3, name:\"Oishi\"}, noname:{accountID:null, name:\"null\"}, ted:{accountID:1, name:\"ted\"}}";
		
		assertEquals(RSON.parse(map), perm1);
	}
	
	@Test
	public void testList() throws ParseException
	{
		String perm1 = "[{accountID:1, name:\"ted\"},{accountID:8675309, name:\"Jenny\"},{accountID:3, name:\"Oishi\"},{accountID:4, name:\"HarryPotter\"},{accountID:null, name:\"null\"}]";
		assertEquals(RSON.parse(list), perm1);
	}
	
	@Test
	public void testObj() throws ParseException
	{
		assertEquals(RSON.parse(list.get(0)), "{accountID:1, name:\"ted\"}");
	}

}
