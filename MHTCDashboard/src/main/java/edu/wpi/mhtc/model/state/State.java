package edu.wpi.mhtc.model.state;

import edu.wpi.mhtc.persistence.DBState;

public class State
{
    private final int id;
	private final String name;
	private final String abbr;

	public State(int id, String name, String abbr)
	{
		this.id = id;
	    this.name = name;
		this.abbr = abbr;
	}

	public State(DBState state)
	{
		this.id = state.getId();
	    this.name = state.getName();
		this.abbr = state.getInitial();
	}

	public int getId() {
	    return id;
	}

	public String getName() {
		return name;
	}

	public String getAbbr() {
		return abbr;
	}
}