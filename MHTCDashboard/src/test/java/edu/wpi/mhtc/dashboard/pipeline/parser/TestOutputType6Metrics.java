//package edu.wpi.mhtc.dashboard.pipeline.parser;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.util.Collections;
//import java.util.List;
//
//import org.junit.Test;
//
//import parser.IParser;
//import parser.ParserFactory;
//import parser.ParserType;
//import fileInfo.FileInfo;
//import fileInfo.FileType;
//import fileInfo.LoadInfo;
//
//public class TestOutputType6Metrics {
//
//	private String fileName = "testfiles" + File.separator
//			+ "Gov_Tax_Collections_Historical_DB.xls";
//
//	@Test
//	public void testOutput() throws Exception {
//		LoadInfo loadInfo = new LoadInfo(-1, -1, false);
//		FileInfo fileInfo = new FileInfo(this.fileName, FileType.xls,
//				ParserType.type6, loadInfo);
//		IParser parser = ParserFactory.getInstance(fileInfo);
//		List<String> list = parser.getColumnNames();
//		Collections.sort(list);
//		BufferedWriter bw = new BufferedWriter(new FileWriter("E:"
//				+ File.separator + "MHTC" + File.separator + "output1.txt"));
//		for (String s : list) {
//			bw.write(s + "\r\n");
//		}
//		bw.close();
//	}
//}
