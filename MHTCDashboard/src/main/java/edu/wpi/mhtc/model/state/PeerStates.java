package edu.wpi.mhtc.model.state;

import java.util.HashMap;
import java.util.LinkedList;

import edu.wpi.mhtc.rson.ParseException;
import edu.wpi.mhtc.rson.RSON;

public class PeerStates
{
	private LinkedList<State> states;
	private transient static PeerStates instance;
	
	private PeerStates()
	{
		states = new LinkedList<State>();
		states.add(new State("Massachusetts", "MA"));
		states.add(new State("New Hampshire", "NH"));
		states.add(new State("New York", "NY"));
		states.add(new State("Connecticut", "CT"));
		states.add(new State("Pennsylvania", "PE"));
		states.add(new State("Maryland", "MD"));
		states.add(new State("Virginia", "VI"));
		states.add(new State("North Carolina", "NC"));
		states.add(new State("Georgia", "GE"));
		states.add(new State("Michigain", "MI"));
		states.add(new State("Washington", "WA"));
		states.add(new State("California", "CA"));
		states.add(new State("Texas", "TX"));
		states.add(new State("Colorado", "CO"));
		states.add(new State("Utah", "UT"));
	}
	
	public static PeerStates getInstance()
	{
		if (instance == null)
		{
			instance = new PeerStates();
		}
		return instance;
	}
	
	
	public LinkedList<HashMap<String, LinkedList<State>>> getAsGrid(int rows) throws ParseException
	{
		LinkedList<State> queue = (LinkedList<State>) states.clone();
		LinkedList<HashMap<String, LinkedList<State>>> result = new LinkedList<HashMap<String, LinkedList<State>>>();
		for(int i = 0; i < rows; i++)
		{
			LinkedList<State> partial = new LinkedList<>();
			for(int j = 0; j < states.size()/rows; j++)
			{
				partial.add(queue.pop());
			}
			HashMap<String, LinkedList<State>> tem = new HashMap<String, LinkedList<State>>();
			tem.put("row", partial);
			result.add(tem);
		}
		
		return result;
	}
	
	
	
	
	
	
}
