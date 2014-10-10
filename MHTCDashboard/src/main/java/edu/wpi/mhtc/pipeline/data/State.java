package edu.wpi.mhtc.pipeline.data;

public class State implements Cloneable{
	private String stateID;
	private String fullName;
	private String initial;
	
	public State() {
	}

	public State(String stateID, String fullName){
		this.stateID = stateID;
		this.fullName = fullName;
	}
	
	public State(String stateID, String fullName, String initial){
		this(stateID, fullName);
		this.initial = initial;
	}
	
	/*
	 * clone object
	 */
	@Override
	public Object clone(){
		State state = new State(this.stateID, this.fullName, this.initial);
		return state;
	}
	
	/*
	 * getters and setters
	 */
	
	public String getStateID() {
		return stateID;
	}

	public void setStateID(String stateID) {
		this.stateID = stateID;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String stateName) {
		this.fullName = stateName;
	}

	public String getInitial() {
		return initial;
	}

	public void setInitial(String initial) {
		this.initial = initial;
	}
	

}
