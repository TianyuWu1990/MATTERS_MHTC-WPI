package edu.wpi.mhtc.model.Data;

import java.util.LinkedList;

import edu.wpi.mhtc.rson.ParseException;
import edu.wpi.mhtc.rson.RSON;

public class MassRanking
{
	public LinkedList<DataSource> data;
	private transient Data d12;
	private transient Data d10;
	private transient Data d8;
	private transient Data d4;
	
	public MassRanking()
	{
		data = new LinkedList<DataSource>();
		
		makeup();
		DataSource ds1 = new DataSource("State Science and Technology", "http://google.com", "up", "Melkin Institute", d12, d10, d8, d4);
		makeup();
		DataSource ds2 = new DataSource("Fourth and Eight Grade Math and Reading", "http://google.com", "up", "NAEP", d12, d10, d8, d4);
		makeup();
		DataSource ds3 = new DataSource("Science and Engineering Indicators", "http://google.com", "up", "MTC", d12, d10, d8, d4);
		makeup();
		DataSource ds4 = new DataSource("Percent of High Tech Workers in Workforce", "http://google.com", "up", "Melkin Institute", d12, d10, d8, d4);
		
		data.add(ds1);
		data.add(ds2);
		data.add(ds3);
		data.add(ds4);
	}
	
	private void makeup()
	{
		d12 = new Data(12, R(50));
		d10 = new Data(10, R(50));
		d8 = new Data(8, R(50));
		d4 = new Data(4, R(50));
	}
	
	public int R(int a)
	{
		return (int) (a*Math.random());
	}
	
	public static void main(String[] args) throws ParseException
	{
		System.out.println(RSON.parse(new MassRanking().data));
	}
}
