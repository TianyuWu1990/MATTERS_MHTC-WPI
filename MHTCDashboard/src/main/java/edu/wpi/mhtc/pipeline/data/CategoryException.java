package edu.wpi.mhtc.pipeline.data;

public class CategoryException extends Exception{

	public CategoryException(String message) {
        super(message);
    }

    public CategoryException(String message, Throwable throwable) {
        super(message, throwable);
    }
}