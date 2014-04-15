package edu.wpi.mhtc.persistence;

public class DBMetric {

	private int id;
	private String name;
	private String dataType;

	public DBMetric(int id, String name, String dataType) {
		this.id = id;
		this.name = name;
		this.dataType = dataType;
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

}
