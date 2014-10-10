package edu.wpi.mhtc.pipeline.cleaner;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.event.SpellChecker;

public class SpellCheckManager {
	protected static SpellDictionaryHashMap dictionary = null;
	protected static SpellChecker spellChecker = null;

	static {
		try {
			dictionary = new SpellDictionaryHashMap(new File(
					"dictionary/statedictionary.0"));
		} catch (IOException e) {

			e.printStackTrace();
		}
		spellChecker = new SpellChecker(dictionary);
	}

	public static List getSuggestions(String word, int threshold) {
		return spellChecker.getSuggestions(word, threshold);
	}
}