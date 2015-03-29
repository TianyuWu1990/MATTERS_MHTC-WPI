/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */

package edu.wpi.mhtc.util.pipeline.cleaner;

import java.io.File;
import java.util.List;

import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.event.SpellChecker;

public class SpellCheckManager {
	protected static SpellDictionaryHashMap dictionary = null;
	protected static SpellChecker spellChecker = null;

	static {
		File dict = new File("statedictionary.txt");

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