package edu.wpi.mhtc.persistence;

public class DBMetric {

	private int id;
	private String name;
	private String dataType;
	private int binId;
	private String binName;
	private String URL;
	private String source;
	private String trendType;

	public DBMetric(int id, String name, String dataType, int binId, String binName, String URL, String source, String trendType) {
		this.id = id;
		this.name = name;
		this.dataType = dataType;
		this.binName = binName;
		this.URL = URL;
		this.source = source;
		this.trendType = trendType;
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
	
	public String getURL() {
		return URL;
	}
	
	public String getSource() {
		return source;
	}

    public String getTrendType() {
        return trendType;
    }

}
