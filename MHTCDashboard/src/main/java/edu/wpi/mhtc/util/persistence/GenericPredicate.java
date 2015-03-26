/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.util.persistence;

public interface GenericPredicate<T> {

	boolean evaluate(T object);
}
