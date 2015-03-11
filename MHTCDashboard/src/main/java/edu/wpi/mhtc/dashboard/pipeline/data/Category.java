/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dashboard.pipeline.data;

import java.util.List;

/**
 * Corresponds to tuples in categories table in db.
 * @author cakuhlman
 *
 */
public class Category {
	
	private int id;
	private List<Metric> metrics;
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

	/**
	 * 
	 * @param name
	 * @return Metric
	 * @throws Exception if the name does not match any metrics associated with this Category in the database
	 */
	public Metric getMetric(String name) throws CategoryException {
		
		for (Metric metric: metrics) {
			if (name.equalsIgnoreCase(metric.getName())) {

				return metric;
			}
		}
		
		// Metric wasn't found in the DB, let user know
		CategoryException c = new CategoryException("No metric in category \"" + this.name + "\" matches metric \""+name+"\".");
		c.setSolution("The possible metrics for category \"" + this.name + "\" are:<ul>");
		
		for (Metric metric: metrics) {
			c.setSolution("<li>" + metric.getName() + "</li>");
		}
		
		c.setSolution("</ul>Please confirm that you are uploading the right metric to the right category.");
				
		throw c;
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


