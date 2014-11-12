package edu.wpi.mhtc.dashboard.pipeline.parser;

import edu.wpi.mhtc.dashboard.pipeline.main.MHTCException;

public class UnifiedFormatException extends MHTCException {

	public UnifiedFormatException(String message) {
		super(message);
	}

	public UnifiedFormatException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
