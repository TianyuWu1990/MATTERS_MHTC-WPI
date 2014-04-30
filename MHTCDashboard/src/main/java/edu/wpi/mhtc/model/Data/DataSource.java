package edu.wpi.mhtc.model.Data;

import java.util.LinkedList;

public class DataSource
{
	private final String name;
	private final String urlFrom;
	private final String sourceName;
	private final String trend;
	private final String binName;
	private final LinkedList<Data> data;
	private double dataAverage;
	
	
	
	public DataSource(String name, String urlFrom, String trend, String sourceName, String binName, Data...datas )
	{
		this.name = name;
		this.urlFrom = urlFrom;
		this.trend = trend==null?"?":trend;
		this.data = new LinkedList<Data>();
		this.sourceName = sourceName;
		this.binName = binName;
		int i;
		for(i = 0; i<datas.length-1; i++)
		{
			this.data.add(datas[i]);
		}
		if (datas.length > 0)
			this.addData(datas[i]);
		
	}
	
	public DataSource addData(Data d)
	{
		this.data.add(d);
		Integer count = 0;
		dataAverage = 0;
		for(Data dd : data)
		{
			dataAverage += dd.getValue();
			count++;
		}
		if (count > 0)
		{
			dataAverage /= count;
		}
		return this;
	}

	public String getName() {
		return name;
	}

	public String getUrlFrom() {
		return urlFrom;
	}

	public String getSourceName() {
		return sourceName;
	}

	public String getTrend() {
		return trend;
	}

	public LinkedList<Data> getData() {
		return data;
	}

	public double getDataAverage() {
		return dataAverage;
	}
	public String getBinName() {
		return binName;
	}
}
