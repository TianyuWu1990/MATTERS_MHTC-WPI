package edu.wpi.mhtc.model.state;

import java.util.LinkedList;

public class State
{
	private final String name;
	private final String abbr;
	private LinkedList<StateParam> params;


	public State(String n, String a, StateParam... optional)
	{
		this.name = n;
		this.abbr = a;
		this.params = new LinkedList<StateParam>();
		for(StateParam sp : optional)
		{
			this.addParam(sp);
		}
	}

	public State addParam(StateParam sp)
	{
		this.params.add(sp);
		return this;
	}
}