package edu.wpi.mhtc.persistence;

public class DBMetric {

	private int id;
	private String name;
	private String dataType;
	private int binId;
	private String binName;

	public DBMetric(int id, String name, String dataType, int binId, String binName) {
		this.id = id;
		this.name = name;
		this.dataType = dataType;
		this.binName = binName;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDataType() {
		return dataType;
	}
	
	public int getBinId() {
		return binId;
	}
	
	public String getBinName() {
		return binName;
	}

}
