package edu.wpi.mhtc.rson;

@SuppressWarnings("serial")
public class ParseException extends Exception
{

	public ParseException(String string) {
		super(string);
	}

	public ParseException(StackTraceElement[] stackTrace) {
		super.setStackTrace(stackTrace);
	}
	
}