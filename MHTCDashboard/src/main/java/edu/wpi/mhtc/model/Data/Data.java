package edu.wpi.mhtc.model.Data;

public class Data
{
	private final Integer year; //after 2000... so 2014 is 14, 1999 is -1;
	private final double value;
	
	public Data(int year, double value)
	{
		this.year = year;
		this.value = value;
	}

	public double getValue() {
		return value;
	}
	
	public Integer getYear() {
		return year;
	}
}
