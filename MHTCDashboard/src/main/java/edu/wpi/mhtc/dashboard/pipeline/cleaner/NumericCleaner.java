package edu.wpi.mhtc.dashboard.pipeline.cleaner;

import java.util.regex.Pattern;

public class NumericCleaner implements ICleaner {

	@Override
	public String clean(String val) throws Exception {
		val.trim();
		val = CleanComma(val);
		val = CleanCharValue(val);
		val = CleanDollarValue(val);
		val = CleanPercentValue(val);
		val = CleanOtherSign(val);
		return val;
	}

	/*
	 * Clean all the commas in the value
	 */
	public String CleanComma(String val) {
		val = val.replace(",", "");
		return val;
	}

	/*
	 * Clean all the value if it contains characters
	 */
	public String CleanCharValue(String val) {
		if (Pattern.compile(".*[a-zA-Z]+.*").matcher(val).matches()) {
			return "-1";
		} else {
			return val;
		}
	}

	/*
	 * Clean all the value if it contains "$" sign
	 */
	public String CleanDollarValue(String val) {
		if (val.contains("$")) {
			val = val.replace("$", "");
			if (val.matches("-*\\d+\\.?\\d*")) {
				return val;
			} else {
				return "-1";
			}
		} else {
			return val;
		}
	}

	/*
	 * Clean all the value if it contains "%" sign
	 */
	public String CleanPercentValue(String val) {
		if (val.contains("%")) {
			val = val.replace("%", "");
			if (val.matches("-*\\d+\\.?\\d*")) {
				double dd = Double.parseDouble(val) / 100;
				val = Float.toString((float) dd);
				return val;
			} else {
				return "-1";
			}
		} else {
			return val;
		}
	}

	/*
	 * Clean all the value if it is not pure number
	 */
	public String CleanOtherSign(String val) {
		if (val.matches("-*\\d+\\.?\\d*")) {
			return val;
		} else {
			return "-1";
		}
	}

}
