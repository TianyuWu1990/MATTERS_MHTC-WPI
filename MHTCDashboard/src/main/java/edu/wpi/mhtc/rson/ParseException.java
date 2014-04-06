package edu.wpi.mhtc.rson;

public class ParseException extends Exception
{

	public ParseException(String string) {
		super(string);
	}

	public ParseException(StackTraceElement[] stackTrace) {
		super.setStackTrace(stackTrace);
	}
	
}