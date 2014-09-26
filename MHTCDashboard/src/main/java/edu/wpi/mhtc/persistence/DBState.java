package edu.wpi.mhtc.persistence;

public class DBState {

	private int id;
	private String name;
	private String initial;
	private boolean ispeer;
	
	public DBState(int id, String name, String initial, boolean ispeer) {
		this.id = id;
		this.name = name;
		this.initial = initial;
		this.ispeer = ispeer;
		
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getInitial() {
		return initial;
	}

	public boolean isIspeer() {
		return ispeer;
	}
	
}
