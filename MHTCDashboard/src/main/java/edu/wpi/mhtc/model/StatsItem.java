package edu.wpi.mhtc.model;

public class StatsItem {

	private StatsMeta stat;
	private String state;
	private int year;
	private double value;
	
	public StatsItem(StatsMeta stat, String state, int year, double value) {
		this.stat = stat;
		this.state = state;
		this.year = year;
		this.value = value;
	}

	public StatsMeta getStat() {
		return stat;
	}

	public void setStat(StatsMeta stat) {
		this.stat = stat;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
}
