package edu.wpi.mhtc.rson;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class RSONTest
{
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, ParseException
	{
		//StringBuilder b = RSON.parse(new TestObj(1, "one"), TestObj.class);
		
		List<TestObj> a = new LinkedList<>();
		a.add(new TestObj(1, "one"));
		a.add(new TestObj(2, "two"));
		a.add(new TestObj(3, "three"));
		
		Map<String, TestObj> map = new HashMap<String, TestObj>();
		map.put("first",new TestObj(1, "one"));
		map.put("second",new TestObj(2, "two"));
		map.put("third",new TestObj(3, "three"));
		
		List<State> states = new LinkedList<>();
		states.add(new State("Massachusetts"));
		states.add(new State("Connecticut"));
		states.add(new State("California"));
		
		String b = RSON.parse(states);
		System.out.println(b.toString());
	}
}

class TestObj
{
	private Integer num;
	private String cli;
	
	public TestObj(int n, String c)
	{
		num = n;
		cli = c;
	}
}

class State
{
	private String name;
	public State(String n)
	{
		this.name = n;
	}
}
