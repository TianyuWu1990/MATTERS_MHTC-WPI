package edu.wpi.mhtc.httpexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Throw this exception if you want the server to return a 404 error.
 * 
 * @author Stokes
 */
@SuppressWarnings("serial")
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such resource.")
public class ResourceNotFoundException extends RuntimeException
{
	
}