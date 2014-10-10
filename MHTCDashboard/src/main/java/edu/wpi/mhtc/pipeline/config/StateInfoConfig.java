package edu.wpi.mhtc.pipeline.config;

import java.util.List;

import edu.wpi.mhtc.pipeline.data.State;
import edu.wpi.mhtc.pipeline.db.DBLoader;


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
	
	@Override
	public void loadConfig() throws Exception {
		this.stateList = DBLoader.getStateMapper();
	}
	
}
