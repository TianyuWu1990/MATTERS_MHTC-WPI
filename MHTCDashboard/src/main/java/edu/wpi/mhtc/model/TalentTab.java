package edu.wpi.mhtc.model;

public class TalentTab
{
	private String name;
	private Integer mass_rank;
	private String datasource;
	private String datasourceURL;
	
	
	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
	
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * @return the mass_rank
	 */
	public Integer getMass_rank()
	{
		return mass_rank;
	}
	
	
	/**
	 * @param mass_rank the mass_rank to set
	 */
	public void setMass_rank(Integer mass_rank)
	{
		this.mass_rank = mass_rank;
	}
	
	
	/**
	 * @return the datasource
	 */
	public String getDatasource()
	{
		return datasource;
	}
	
	
	/**
	 * @param datasource the datasource to set
	 */
	public void setDatasource(String datasource)
	{
		this.datasource = datasource;
	}
	
	
	/**
	 * @return the datasourceURL
	 */
	public String getDatasourceURL()
	{
		return datasourceURL;
	}
	
	
	/**
	 * @param datasourceURL the datasourceURL to set
	 */
	public void setDatasourceURL(String datasourceURL)
	{
		this.datasourceURL = datasourceURL;
	}
	
}
