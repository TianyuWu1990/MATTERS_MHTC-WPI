package edu.wpi.mhtc.pipeline.data;

public class LoadInfo {

	private int startRow;
	private int endRow;
	private boolean isRowSpecified;

	public LoadInfo(boolean isRowSpecified) {
		this.setIsRowSpecified(isRowSpecified);
	}

	public LoadInfo(int startRow, int endRow, boolean isRowSpecified) {
		super();
		this.startRow = startRow;
		this.endRow = endRow;
		this.setIsRowSpecified(isRowSpecified);
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public boolean isRowSpecified() {
		return isRowSpecified;
	}

	public void setIsRowSpecified(boolean needCleaning) {
		this.isRowSpecified = needCleaning;
	}

}
