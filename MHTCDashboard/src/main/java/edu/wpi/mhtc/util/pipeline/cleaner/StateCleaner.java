/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */

package edu.wpi.mhtc.util.pipeline.cleaner;

import java.util.List;

import com.swabunga.spell.engine.Word;

import edu.wpi.mhtc.model.dashboard.State;

/**
 * 
 * Cleaner to validate and repair state names.
 *
 */
public class StateCleaner implements ICleaner {
	private List<State> states;
	
	public StateCleaner(List<State> states) {
		this.states = states;
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
		for (State state : states) {
			if (val.equalsIgnoreCase(state.getName())) {
				return true;
			}
			if (val.equalsIgnoreCase(state.getAbbr())) {
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
		for (State state : states) {
			if (val.equalsIgnoreCase(state.getName())) {
				return state.getName();
			}
			if (val.equalsIgnoreCase(state.getAbbr())) {
				return state.getAbbr();
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

		for (int i = 0; i < stateArray.length; i++) {
			for (State state : states) {
				if (stateArray[i].contains(state.getName())) {
					return state.getName();
				}
				if (stateArray[i].equalsIgnoreCase(state.getAbbr())) {
					return state.getAbbr();
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

		for (State state : states) {
			if (val.equals(state.getName())) {
				return state.getName();
			}
			if (val.equals(state.getAbbr())) {
				return state.getAbbr();
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
			for(State state : states){
				if(((Word) o).getWord().equalsIgnoreCase(state.getName())){
					return state.getName();
				}
			}
		}
		return null;
	}

}
