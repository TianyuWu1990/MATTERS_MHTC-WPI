package edu.wpi.mhtc.pipeline.parser;

import edu.wpi.mhtc.pipeline.data.DataSource;
import edu.wpi.mhtc.pipeline.data.UnifiedDataSource;

public class ParserFactory {

	private ParserFactory() {
	}

	public static IParser getInstance(DataSource source) throws Exception {
		switch (source.getParserType()) {
			case excel:
				return new ExcelParser(source);
			case type1:
				return new Type1Parser(source);
			case type2:
				return new Type2Parser(source);
			case type3:
				return new Type3Parser(source);
			case type4:
				return new Type4Parser(source);
			case type5:
				return new Type5Parser(source);
			case type6:
				return new Type6Parser(source);
			case type7:
				return new Type7Parser(source);
			default:
				break;
		}
		return null;
	}
	
	public static IParser getInstance(UnifiedDataSource unifiedSource) throws Exception {
		return new UnifiedParser(unifiedSource);
	}
}
