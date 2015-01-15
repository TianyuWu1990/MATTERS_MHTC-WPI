package edu.wpi.mhtc.dashboard.pipeline.data;

import edu.wpi.mhtc.dashboard.pipeline.main.MHTCException;

public class CategoryException extends MHTCException {

	public CategoryException(String message) {
        super(message);
    }

    public CategoryException(String message, Throwable throwable) {
        super(message, throwable);
    }
}