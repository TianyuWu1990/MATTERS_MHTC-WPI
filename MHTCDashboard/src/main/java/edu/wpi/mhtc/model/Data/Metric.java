/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.model.Data;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

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
	
	public Metric(int id, String name, int binId, String binName, String type, String trendType, String urlFrom, String sourceName)
	{
	    this.id = id;
		this.name = name;
		this.urlFrom = urlFrom;
		this.sourceName = sourceName;
		this.binName = binName;
		this.binId = binId;
		this.type = type;
		this.trendType = trendType;
		
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
