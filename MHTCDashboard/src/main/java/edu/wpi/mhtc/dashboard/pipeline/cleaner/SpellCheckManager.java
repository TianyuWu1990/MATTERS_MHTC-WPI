/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */

package edu.wpi.mhtc.dashboard.pipeline.cleaner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.event.SpellChecker;

import edu.wpi.mhtc.dashboard.pipeline.data.State;

public class SpellCheckManager {
	protected static SpellDictionaryHashMap dictionary = null;
	protected static SpellChecker spellChecker = null;

	static {
		File dict = new File("statedictionary.txt");
		if(!dict.exists()){
			try(BufferedWriter writer = new BufferedWriter(new FileWriter(dict))){
				List<State> states = State.getList();
				for(State s : states){
					writer.write(s.toString());
					writer.write("\n");
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		} 
		try {
			dictionary = new SpellDictionaryHashMap(dict);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		spellChecker = new SpellChecker(dictionary);
	}

	public static List getSuggestions(String word, int threshold) {
		return spellChecker.getSuggestions(word, threshold);
	}
}