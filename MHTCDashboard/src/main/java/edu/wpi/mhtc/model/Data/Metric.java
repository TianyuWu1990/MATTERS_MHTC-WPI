/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.model.Data;


public class Metric
{
    private final int id;
    private final int binId;
    private final String binName;
	private final String name;
	
	private final String urlFrom;
	private final String sourceName;
	
    private final String type;
    private final String trendType;
    private final String desc;
	
	public Metric(int id, String name, int binId, String binName, String type, String trendType, String urlFrom, String sourceName, String desc)
	{
	    this.id = id;
		this.name = name;
		this.urlFrom = urlFrom;
		this.sourceName = sourceName;
		this.binName = binName;
		this.binId = binId;
		this.type = type;
		this.trendType = trendType;
		this.desc = desc+"\n Source: "+urlFrom;
		
	}
	
	public String getName() {
		return name;
	}
	
	public String getDesc(){
		return desc;
	}

	public String getUrlFrom() {
		return urlFrom;
	}

	public String getSourceName() {
		return sourceName;
	}

	public String getBinName() {
		return binName;
	}
	
	public int getId() {
	    return id;
	}
	
    public String getType() {
        return type;
    }

    public int getBinId() {
        return binId;
    }

    public String getTrendType() {
        return trendType;
    }
}
