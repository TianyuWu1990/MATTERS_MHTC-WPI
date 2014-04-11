package edu.wpi.mhtc.model.state;

public class StateParam implements Comparable<StateParam>
{
	private final String name;
	private String value;

	public StateParam(String n, String v)
	{
		this.name = n;
		this.value = v;
	}

	public StateParam setValue(String value)
	{
		this.value = value;
		return this;
	}

	@Override
	public int compareTo(StateParam arg0) {
		return this.name.compareTo(arg0.name);
	}
}