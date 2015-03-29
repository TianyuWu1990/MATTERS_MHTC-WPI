/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.model.dashboard;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class PeerStates {
	private LinkedList<State> states;

	public PeerStates(List<State> list)
	{
		states = (LinkedList<State>) list;
	}

	public LinkedList<HashMap<String, LinkedList<State>>> getAsGrid(int rows) {
		@SuppressWarnings("unchecked")
        LinkedList<State> queue = (LinkedList<State>) states.clone();
		LinkedList<HashMap<String, LinkedList<State>>> result = new LinkedList<HashMap<String, LinkedList<State>>>();
		for (int i = 0; i < rows; i++) {
			LinkedList<State> partial = new LinkedList<State>();
			for (int j = 0; j < states.size() / rows; j++) {
				partial.add(queue.pop());
			}
			HashMap<String, LinkedList<State>> tem = new HashMap<String, LinkedList<State>>();
			tem.put("row", partial);
			result.add(tem);
		}

		return result;
	}
	
	public List<State> getStates()
	{
		return states;
	}

}
