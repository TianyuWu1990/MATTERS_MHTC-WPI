package edu.wpi.mhtc.dashboard.pipeline.data;

import java.sql.SQLException;
import java.util.List;

import edu.wpi.mhtc.dashboard.pipeline.db.DBLoader;

public class State implements Cloneable{
	private int id;
	private String fullName;
	private String initial;
	
	public State() {
	}

	public State(int id, String fullName){
		this.id = id;
		this.fullName = fullName;
	}
	
	public State(int id, String fullName, String initial){
		this(id, fullName);
		this.initial = initial;
	}
	
	/*
	 * clone object
	 */
	@Override
	public Object clone(){
		State state = new State(this.id, this.fullName, this.initial);
		return state;
	}
	
	/*
	 * getters and setters
	 */
	
	public int getStateID() {
		return id;
	}

	public String getFullName() {
		return fullName;
	}

	public String getInitial() {
		return initial;
	}
	
	static int getStateID(String stateName) throws SQLException{
		int id = -1;
		for(State state : DBLoader.getStateMapper()){
			if(stateName.equalsIgnoreCase(state.getFullName())){
				id = state.getStateID();
			}
		}
		return id;
	}

	public static List<State> getList() throws SQLException {
		return DBLoader.getStateMapper();
	}
	
	public static State getStateByName(String name) throws Exception {
		List<State> stateList = getList();
		for(State state : stateList){
			if(name.equalsIgnoreCase(state.getFullName())){
				return state;
			}
		}

		return null;
	}
	
	@Override
	public String toString(){
		return fullName;
	}
	

}
