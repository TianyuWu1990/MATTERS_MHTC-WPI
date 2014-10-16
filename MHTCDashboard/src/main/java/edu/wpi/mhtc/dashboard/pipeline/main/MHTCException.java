package edu.wpi.mhtc.dashboard.pipeline.main;

/**
 * MHTCException is the exception that is thrown for any error that occurs
 * during the pipeline execution
 * 
 * @author Alex Fortier
 * @version Oct 15, 2014
 *
 */
public class MHTCException  extends Exception {
	
	/**
	 * Every instance of this exception must have a message describing the exception.
	 * @param message the string describing the error causing the exception
	 */
	public MHTCException(String message) {
		super(message);
	}
	
	/**
	 * An excaption that was caused by some other exception
	 * @param message the string describing the error
	 * @param cause the error that cause this exception
	 */
	public MHTCException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
