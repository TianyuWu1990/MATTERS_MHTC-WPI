package edu.wpi.mhtc.dashboard.pipeline.config;

import java.util.LinkedList;
import java.util.List;

import edu.wpi.mhtc.dashboard.pipeline.data.State;
import edu.wpi.mhtc.dashboard.pipeline.db.DBLoader;


public class StateInfoConfig implements IConfig{
	private List<State> stateList;
	private static StateInfoConfig stateInfoConfig; 
	
	private StateInfoConfig() throws Exception{
		this.loadConfig();
	}
	
	public static StateInfoConfig getInstance() throws Exception{
		if(stateInfoConfig == null){
			stateInfoConfig = new StateInfoConfig();
		}
		return stateInfoConfig;
	}
	
	/*
	 * get the state by state full name
	 */
	public State getStateByFullName(String fullName){
		for(State state : stateList){
			if(fullName.equalsIgnoreCase(state.getFullName())){
				return (State)state.clone();
			}
		}
		return null;
	}
	
	/*
	 * get the state by state initial
	 */
	public State getStateByInitial(String initial){
		for(State state : stateList){
			if(initial.equalsIgnoreCase(state.getInitial())){
				return (State)state.clone();
			}
		}
		return null;
	}
	/*
	 * get the state list
	 */
	public List<State> getStateList(){
		return stateList;
	}
	
	/**
	 * Get state initial list
	 */
	public List<String> getStateInitialsList() {
		LinkedList<String> stateInitialList = new LinkedList<String>();
		
		for(State s: stateList) {
			stateInitialList.add(s.getInitial());
		}
		
		return stateInitialList;
	}
	
	@Override
	public void loadConfig() throws Exception {
		this.stateList = DBLoader.getStateMapper();
	}
	
}
