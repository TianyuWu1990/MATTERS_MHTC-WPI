package edu.wpi.mhtc.dashboard.pipeline.data;

import java.util.LinkedList;
import java.util.List;

import edu.wpi.mhtc.dashboard.pipeline.fileInfo.FileInfo;

/*
 * this class contains all the line data for a file
 */
public class FileData {
	protected List<LineData> lineDataList;
	protected FileInfo fileInfo;
	
	public FileData(FileInfo fileInfo){
		this.lineDataList = new LinkedList<LineData>();
		this.fileInfo = fileInfo;
	}

	public List<LineData> getLineDataList() {
		return lineDataList;
	}

	public void setLineDataList(List<LineData> lineDataList) {
		this.lineDataList = lineDataList;
	}

}