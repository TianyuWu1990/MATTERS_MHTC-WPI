package edu.wpi.mhtc.dashboard.pipeline.cleaner;

import java.util.List;

import edu.wpi.mhtc.dashboard.pipeline.config.StateInfoConfig;
import edu.wpi.mhtc.dashboard.pipeline.data.State;

import com.swabunga.spell.engine.Word;

public class StateCleaner implements ICleaner {

	/*
	 * clean method must return the State full name String please call
	 * StateInfoConfig 's method to get the State according to the initial or
	 * full name
	 */
	
	
//	TODO: test cases for state cleaner. 
	private boolean flag;
	
	public StateCleaner(){
		this.flag = false;
	}
	
	@Override
	public String clean(String val) throws Exception {
		val = val.trim();
		val = val.toLowerCase();
		if(isStateName(val)) {	// if the val matches a state name
			val = getFullName(val);	// check if val is in the stateList, if so return state full name, if not return val
			return val;
		}
		
		String cleanedVal = getFullNameBySpellCheck(val); // concatenate all the substring together, do the spell check
		
		if(cleanedVal == null){
			cleanedVal = getFullNameBySubstring(val); // for every substring in val, check if they contain state full name or match state abbreviation
		}
		if(cleanedVal == null){
			cleanedVal = getFullNameByConcatenation(val); // concatenate all the substring together, and check if they match state full name state abbreviation
		}
		if(cleanedVal == null){
			return val;
		}
		return cleanedVal;
			
	}
	/*
	 * split string by commas
	 */
	public String splitStateString(String str) {
		str = str.trim();
		str = str.toLowerCase();
		str = str.replaceAll("[^a-z]", ",");
		if (str.length() == 0) {
			return "-1";
		}
		return str;
	}
	
	/*
	 * check if the string is in the stateList, if so return true, if not return false
	 */
	public boolean isStateName(String val) throws Exception {
		List<State> stateList = StateInfoConfig.getInstance().getStateList();
		for (State state : stateList) {
			if (val.equalsIgnoreCase(state.getFullName())) {
				return true;
			}
			if (val.equalsIgnoreCase(state.getInitial())) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * check if the string is in the stateList, if so return state full name, if
	 * not return string
	 */
	public String getFullName(String val) throws Exception {
		List<State> stateList = StateInfoConfig.getInstance().getStateList();
		for (State state : stateList) {
			if (val.equalsIgnoreCase(state.getFullName())) {
				return state.getFullName();
			}
			if (val.equalsIgnoreCase(state.getInitial())) {
				return state.getFullName();
			}
		}
		return val;
	}

	/*
	 * for every substring in stateArray, check if they contain state full name
	 * or match state initial name
	 */
	public String getFullNameBySubstring(String val) throws Exception {
		val = splitStateString(val); // split state string by commas
		String[] stateArray = val.split("\\,"); // put split substrings into stateArray
		List<State> stateList = StateInfoConfig.getInstance().getStateList();
		for (int i = 0; i < stateArray.length; i++) {
			for (State state : stateList) {
				if (stateArray[i].contains(state.getFullName())) {
					this.flag = true;
					return state.getFullName();
				}
				if (stateArray[i].equalsIgnoreCase(state.getInitial())) {
					this.flag = true;
					return state.getFullName();
				}
			}
		}
		return null;
	}

	/*
	 * concatenate all the substring together, and check if they match state
	 * full name state abbreviation
	 */
	public String getFullNameByConcatenation(String val) throws Exception {
		val = val.replaceAll("[^a-z]", "");
		List<State> stateList = StateInfoConfig.getInstance().getStateList();
		for (State state : stateList) {
			if (val.equals(state.getFullName())) {
				this.flag = true;
				return state.getFullName();
			}
			if (val.equals(state.getInitial())) {
				this.flag = true;
				return state.getFullName();
			}
		}
		return null;
	}

	/*
	 * concatenate all the substring together, do the spell check
	 */
	public String getFullNameBySpellCheck(String val) throws Exception {
		int thres = 10;
		List result = SpellCheckManager.getSuggestions(val, thres);
		
		
		for(Object o : result){
			State state = StateInfoConfig.getInstance().getStateByFullName(((Word) o).getWord());
			if (state != null){
				return state.getFullName();
			}
		}
		return null;
	}

}
