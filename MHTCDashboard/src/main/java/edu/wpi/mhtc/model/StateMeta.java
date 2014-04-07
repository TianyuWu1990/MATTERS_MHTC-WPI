package edu.wpi.mhtc.model;

public class StateMeta {

	private int id;
	private String initial;
	private String fullname;
	
	public StateMeta(int id, String initial, String fullname) {
		this.id = id;
		this.initial = initial;
		this.fullname = fullname;
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInitial() {
		return initial;
	}

	public void setInitial(String initial) {
		this.initial = initial;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

}
