package edu.wpi.mhtc.dashboard.pipeline.parser;

import edu.wpi.mhtc.dashboard.pipeline.main.MHTCException;

public class UnifiedFormatException extends MHTCException {

	private static final long serialVersionUID = -744511727264601128L;

	public UnifiedFormatException(String message) {
		super(message);
	}

	public UnifiedFormatException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
