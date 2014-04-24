package edu.wpi.mhtc.dashboard.pipeline.parser;

import edu.wpi.mhtc.dashboard.pipeline.fileInfo.FileInfo;

public class ParserFactory {

	private ParserFactory() {
	}

	public static IParser getInstance(FileInfo fileInfo) throws Exception {
		switch (fileInfo.getParserType()) {
			case excel:
				return new ExcelParser(fileInfo);
			case type1:
				return new Type1Parser(fileInfo);
			case type2:
				return new Type2Parser(fileInfo);
			case type3:
				return new Type3Parser(fileInfo);
			case type4:
				return new Type4Parser(fileInfo);
			case type5:
				return new Type5Parser(fileInfo);
			case type6:
				return new Type6Parser(fileInfo);
			case type7:
				return new Type7Parser(fileInfo);
			default:
				break;
		}
		return null;
	}
}
