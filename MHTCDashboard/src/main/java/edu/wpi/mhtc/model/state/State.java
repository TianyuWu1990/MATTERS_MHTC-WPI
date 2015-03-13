/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.model.state;

public class State
{
	private int id;
	private String name;
	private String abbr;
	private boolean peerState;

	public State(int id, String name, String abbr, boolean peerState)
	{
		this.id = id;
		this.name = name;
		this.abbr = abbr;
		this.peerState = peerState;
	}

	public int getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public String getAbbr()
	{
		return abbr;
	}

	public boolean isPeerState()
	{
		return peerState;
	}
}