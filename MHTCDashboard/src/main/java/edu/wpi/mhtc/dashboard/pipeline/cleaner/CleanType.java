package edu.wpi.mhtc.dashboard.pipeline.cleaner;

public enum CleanType {
	numeric, state, year;

	public static boolean isMember(String cleanType) {
		for (CleanType type : CleanType.values()) {
			if (type.name().equalsIgnoreCase((cleanType))) {
				return true;
			}
		}
		return false;
	}
}
