/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dashboard.pipeline.data;

import java.util.List;

/**
 * Corresponds to tuples in categories table in db.
 * @author cakuhlman
 * @version December 2014
 *
 */
public class Category {
	
	private int id;
	private String name;
	private int parentId;
	private String source;
	private String URL;
	private boolean isVisible;
		
	public Category() {
		this.id = 0;
	}

	/**
	 * Return the ID of the category
	 * @return
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Return the name of the category
	 * @return
	 */
	public String getName() {
		return name;
	}

	public String getSource() {
		return source;
	}

	public String getURL() {
		return URL;
	}

	public int getParentId() {
		return parentId;
	}

	public boolean getVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setURL(String uRL) {
		this.URL = uRL;
	}

}


