package edu.wpi.mhtc.dashboard.pipeline.cleaner;

public class CleanerFactory {

	private CleanerFactory() {
	}

	public static ICleaner getInstance(CleanType cleanType) {
		switch (cleanType) {
		case numeric:
			return new NumericCleaner();
		case state:
			return new StateCleaner();
		case year:
			return new YearCleaner();
		}
		return null;
	}
}
