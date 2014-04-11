package edu.wpi.mhtc.model.Data;

import java.util.LinkedList;

public class DataSource
{
	private final String name;
	private final String urlFrom;
	private final String sourceName;
	private final String trend;
	private final LinkedList<Data> data;
	private Integer dataAverage;
	
	
	
	public DataSource(String name, String urlFrom, String trend, String sourceName, Data...datas )
	{
		this.name = name;
		this.urlFrom = urlFrom;
		this.trend = trend;
		this.data = new LinkedList<Data>();
		this.sourceName = sourceName;
		int i;
		for(i = 0; i<datas.length-1; i++)
		{
			this.data.add(datas[i]);
		}
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
}
