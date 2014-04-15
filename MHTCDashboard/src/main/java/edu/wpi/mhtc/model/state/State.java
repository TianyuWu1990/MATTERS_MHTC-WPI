package edu.wpi.mhtc.model.state;

import java.util.LinkedList;
import java.util.List;

import edu.wpi.mhtc.model.Data.DataSource;

public class State
{
	private final String name;

	private final String abbr;
	private LinkedList<DataSource> params;


	public State(String n, String a, DataSource... optional)
	{
		this.name = n;
		this.abbr = a;
		this.params = new LinkedList<DataSource>();
		for(DataSource sp : optional)
		{
			this.addParam(sp);
		}
	}


	public State addParam(DataSource sp)
	{
		this.params.add(sp);
		return this;
	}

	public String getName() {
		return name;
	}

	public String getAbbr() {
		return abbr;
	}

	public List<DataSource> getParams() {
		return params;
	}
}