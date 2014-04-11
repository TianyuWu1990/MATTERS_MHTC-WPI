package edu.wpi.mhtc.model.Data;

public class Data
{
	private final Integer year; //after 2000... so 2014 is 14, 1999 is -1;
	private final Integer value;
	
	public Data(int year, int value)
	{
		this.year = year;
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
}
