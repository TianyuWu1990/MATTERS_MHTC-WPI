package edu.wpi.mhtc.dashboard.pipeline.parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import parser.IParser;
import parser.ParserFactory;
import parser.ParserType;
import fileInfo.FileInfo;
import fileInfo.FileType;
import fileInfo.LoadInfo;

public class TestOutputType2Metrics {

	private String dir = "testfiles" + File.separator + "Type 2";

	@Test
	public void testOutput() throws Exception {
		List<String> list = new LinkedList<String>();
		File directory = new File(this.dir);
		for (File f : directory.listFiles()) {
			LoadInfo loadInfo = new LoadInfo(-1, -1, false);
			FileInfo fileInfo = new FileInfo(f.getAbsolutePath(), FileType.csv,
					ParserType.type2, loadInfo);
			IParser parser = ParserFactory.getInstance(fileInfo);
			List<String> l = parser.getColumnNames();
			for (String s : l) {
				if (!list.contains(s)) {
					list.add(s);
				}
			}
		}
		Collections.sort(list);
		BufferedWriter bw = new BufferedWriter(new FileWriter("E:"
				+ File.separator + "MHTC" + File.separator + "output.txt"));
		for (String s : list) {
			bw.write(s + "\r\n");
		}
		bw.close();
	}
}
