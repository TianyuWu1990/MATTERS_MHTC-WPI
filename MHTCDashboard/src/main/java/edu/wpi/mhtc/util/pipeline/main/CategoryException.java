/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.util.pipeline.main;


/**
 * Exceptions pertaining to categories.
 * @author caitlin kuhlman
 * @version December 2014
 *
 */
public class CategoryException extends MHTCException {

	private static final long serialVersionUID = -3828626945546206402L;

	public CategoryException(String message) {
        super(message);
    }

    public CategoryException(String message, Throwable throwable) {
        super(message, throwable);
    }
}