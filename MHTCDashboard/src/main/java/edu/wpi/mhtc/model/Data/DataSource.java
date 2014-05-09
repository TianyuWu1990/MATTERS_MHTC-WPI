package edu.wpi.mhtc.model.Data;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class DataSource
{
	private final String name;
	private final String urlFrom;
	private final String sourceName;
	private final String trend;
	private final String binName;
	private final LinkedList<Data> data;
	private final int id;
	private final boolean tabbed;
	private double dataAverage;
	private Data recent;
	
	
	
	
	public DataSource(int id, String name, String urlFrom, String trend, String sourceName, String binName, boolean tabbed, Data...datas )
	{
	    this.id = id;
		this.name = name;
		this.urlFrom = urlFrom;
		this.trend = trend==null?"?":trend;
		this.data = new LinkedList<Data>();
		this.sourceName = sourceName;
		this.binName = binName;
		this.tabbed = tabbed;
		int i;
		for(i = 0; i<datas.length-1; i++)
		{
			this.data.add(datas[i]);
			if (recent == null || recent.getYear() < datas[i].getYear())
			    recent = datas[i];
		}
		if (datas.length > 0)
		{
			this.addData(datas[i]);
		}
		
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
		
		if (recent == null || recent.getYear() < d.getYear())
            recent = d;
		
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

	public DataSource sort()
	{
		Collections.sort(data, new Comparator<Data>(){

			@Override
			public int compare(Data o1, Data o2)
			{
				return o1.getYear().compareTo(o2.getYear());
			}

		});

		return this;
	}

	public String getBinName() {
		return binName;
	}
	
	public Data getRecent() {
	    return recent;
	}
	
	public int getId() {
	    return id;
	}
	
	public boolean getTabbed() {
	    return tabbed;
	}
	
	public String getTabString() {
	    if (tabbed)
	        return "tabbed_metric";
	    else
	        return "";
	}
}
