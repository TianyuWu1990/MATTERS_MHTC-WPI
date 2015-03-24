/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.model.dashboard;

public class Data
{
	private final int year;
	private final double value;
	
	public Data(int year, double value)
	{
		this.year = year;
		this.value = value;
	}

	public double getValue() {
		return value;
	}
	
	public int getYear() {
		return year;
	}
}
