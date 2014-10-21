package edu.wpi.mhtc.dashboard.pipeline.parser;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

import parser.CSVParser;
import parser.ParserType;
import parser.Type2Parser;
import parser.Type5Parser;
import fileInfo.FileInfo;
import fileInfo.FileType;
import fileInfo.LoadInfo;

public class TestColumnNames {

	public String csvFileName = "testfiles" + File.separator + "CA_2009.csv";
	public String type5FileName = "testfiles" + File.separator
			+ "personal_income_percapita.xls";

	@Test
	public void testCSVColumnNames() {
		LoadInfo loadInfo = new LoadInfo(-1, -1, false);
		FileInfo fileInfo = new FileInfo(csvFileName, FileType.csv,
				ParserType.type1, loadInfo);
		CSVParser csvParser = new CSVParser(fileInfo);
		List<String> list = csvParser.getColumnNames();

		assertEquals(list.get(0), "unitid");
		assertEquals(list.get(1), "institution name");
		assertEquals(list.get(2), "year");
		assertEquals(list.get(3),
				"C2009_A.CIP Code -  2000 Classification".toLowerCase());
		assertEquals(list.get(4), "CipTitle".toLowerCase());
		assertEquals(list.get(5), "C2009_A.First or Second Major".toLowerCase());
		assertEquals(list.get(6), "C2009_A.Award Level code".toLowerCase());
		assertEquals(list.get(7), "C2009_A_RV.Grand total".toLowerCase());
		assertEquals(list.get(8), "IDX_C".toLowerCase());
	}

	@Test
	public void testType2ColumnNames() {
		LoadInfo loadInfo = new LoadInfo(-1, -1, false);
		FileInfo fileInfo = new FileInfo(this.csvFileName, FileType.csv,
				ParserType.type1, loadInfo);
		Type2Parser typeIIParser = new Type2Parser(fileInfo);
		List<String> list = typeIIParser.getColumnNames();

		for (String str : list) {
			System.out.println(str);
		}
	}

	@Test
	public void testType5ColumnNames() throws InvalidFormatException,
			IOException {
		LoadInfo loadInfo = new LoadInfo(-1, -1, false);
		FileInfo fileInfo = new FileInfo(this.type5FileName, FileType.xls,
				ParserType.type5, loadInfo);
		Type5Parser type5Parser = new Type5Parser(fileInfo);
		List<String> list = type5Parser.getColumnNames();

		assertEquals(list.get(0), "year");
		assertEquals(list.get(1), "state");
		assertEquals(list.get(2),
				"Per capita personal income (dollars)".toLowerCase());
	}

}
