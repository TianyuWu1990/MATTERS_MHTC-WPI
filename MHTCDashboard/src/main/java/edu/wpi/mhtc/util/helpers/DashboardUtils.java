/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.util.helpers;

import java.util.Collection;
import java.util.Iterator;

import edu.wpi.mhtc.util.persistence.GenericPredicate;

public class DashboardUtils {

	/**
	 * Returns the first item in the list that matches the given predicate
	 */
	public static <T> T find(Collection<T> collection, GenericPredicate<T> predicate) {
		if (collection != null && predicate != null) {
            for (Iterator<T> iter = collection.iterator(); iter.hasNext();) {
                T item = iter.next();
                if (predicate.evaluate(item)) {
                    return item;
                }
            }
        }
        return null;
	}
	
	
	/**
	 * Returns true if the given string can be parsed as an integer, false otherwise.
	 */
	public static boolean integerTryParse(String value) {
		// Why java doesn't have this by default I will never know
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}
	
}
