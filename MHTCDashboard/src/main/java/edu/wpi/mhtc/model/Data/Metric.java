package edu.wpi.mhtc.model.Data;

public class Metric
{

	public static final int TALENT_BIN_ID = 20;
	public static final int NATIONAL_BIN_ID = 21;
	public static final int COST_BIN_ID = 37;
	public static final int ECONOMY_BIN_ID = 29;

	public enum Bin
	{

		NATIONAL(NATIONAL_BIN_ID), TALENT(TALENT_BIN_ID), COST(COST_BIN_ID), ECONOMY(ECONOMY_BIN_ID);

		Bin(int binId)
		{
			this.binId = binId;
		}

		public static Bin fromId(int id)
		{
			switch (id)
			{
			case TALENT_BIN_ID:
				return TALENT;
			case NATIONAL_BIN_ID:
				return NATIONAL;
			case COST_BIN_ID:
				return COST;
			case ECONOMY_BIN_ID:
				return ECONOMY;
			default:
				throw new RuntimeException("There is no bin with id " + id);
			}
		}

		private int binId;

		public int getId()
		{
			return binId;
		}
	}

	private int id;
	private String name;
	private String displayName;
	private Bin bin;

	private String urlFrom;
	private String sourceName;

	private String type;
	private String trendType;

	private boolean visible;
	private int parentId;

	public Metric(int id, String name, String displayName, Bin bin, String urlFrom, String sourceName, String type,
			String trendType, boolean visible, int parentId)
	{
		this.id = id;
		this.name = name;
		this.displayName = displayName;
		this.bin = bin;
		this.urlFrom = urlFrom;
		this.sourceName = sourceName;
		this.type = type;
		this.trendType = trendType;
		if (this.trendType == null)
			this.trendType = "";
		this.visible = visible;
		this.parentId = parentId;
	}

	public int getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public Bin getBin()
	{
		return bin;
	}

	public String getUrlFrom()
	{
		return urlFrom;
	}

	public String getSourceName()
	{
		return sourceName;
	}

	public String getType()
	{
		return type;
	}

	public String getTrendType()
	{
		return trendType;
	}

	public boolean isVisible()
	{
		return visible;
	}

	public int getParentId()
	{
		return parentId;
	}
}
