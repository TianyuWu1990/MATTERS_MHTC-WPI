/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.util.pipeline.main;

/**
 * MHTCException is the exception that is thrown for any error that occurs
 * during the pipeline execution
 * 
 * @author Alex Fortier
 * @version Oct 15, 2014
 *
 */
@SuppressWarnings("serial")
public class MHTCException extends Exception {
	
	private StringBuffer solution = new StringBuffer();
	
	/**
	 * Every instance of this exception must have a message describing the exception.
	 * @param message the string describing the error causing the exception
	 */
	public MHTCException(String message) {
		super(message);
	}
	
	/**
	 * An exception that was caused by some other exception.
	 * @param message the string describing the error
	 * @param cause the error that cause this exception
	 */
	public MHTCException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Setter for solution. Should contain possible solution for fixing error.
	 * @param message
	 */
	public void setSolution(String message) {
		solution.append(message);
	}
	
	/**
	 * Gets the possible solution for the given error.
	 * @return
	 */
	public String getSolution() {
		return solution.toString();
	}
	
}
