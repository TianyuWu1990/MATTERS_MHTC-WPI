package edu.wpi.mhtc.dashboard.pipeline.dao;

public class Metric {
	
	public Metric() {
		this.id = 0;
	}

	private int id;
	private String name;
	private int categoryId;
	private boolean isVisible;
	private boolean isCalculated;
	private String dataType;
	private String displayName;
	private String URL;
	private String source;
	private int displayOrder;
	private String trendType;
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the isVisible
	 */
	public boolean isVisible() {
		return isVisible;
	}
	
	/**
	 * @param isVisible the isVisible to set
	 */
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	/**
	 * @return the isCalculated
	 */
	public boolean isCalculated() {
		return isCalculated;
	}
	
	/**
	 * @param isCalculated the isCalculated to set
	 */
	public void setCalculated(boolean isCalculated) {
		this.isCalculated = isCalculated;
	}
	
	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}
	
	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}
	
	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	/**
	 * @return the uRL
	 */
	public String getURL() {
		return URL;
	}
	
	/**
	 * @param uRL the uRL to set
	 */
	public void setURL(String uRL) {
		URL = uRL;
	}
	
	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}
	
	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}
	
	/**
	 * @return the displayOrder
	 */
	public int getDisplayOrder() {
		return displayOrder;
	}
	
	/**
	 * @param displayOrder the displayOrder to set
	 */
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}
	
	/**
	 * @return the trendType
	 */
	public String getTrendType() {
		return trendType;
	}
	
	/**
	 * @param trendType the trendType to set
	 */
	public void setTrendType(String trendType) {
		this.trendType = trendType;
	}

	/**
	 * @return the categoryId
	 */
	public int getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
}
