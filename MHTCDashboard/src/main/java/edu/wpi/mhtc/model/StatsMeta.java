package edu.wpi.mhtc.model;

public class StatsMeta {

	private String name;
	private String displayName;
	
	public StatsMeta(String name, String displayName) {
		this.setName(name);
		this.setDisplayName(displayName);
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
}
