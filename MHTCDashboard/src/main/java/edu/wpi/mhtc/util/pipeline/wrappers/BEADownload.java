/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.util.pipeline.wrappers;


public class BEADownload extends URLDownload{
	/**
	 * 
	 * @param targeturl
	 * @param filename
	 */
	public BEADownload() {}
	
	// Download class
	public void download(String targeturl, String filename) {
		this.setRequestData("cookie", "JSESSIONID=2c30becf0113000ef9204f67413c4b547e40; __utma=125733781.915281630.1395942821.1397160595.1397166506.5; __utmb=125733781.10.10.1397166506; __utmc=125733781; __utmz=125733781.1395942821.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); WT_FPC=id=b754455a-eda4-4ecc-93ef-bc80e6b72fe7:lv=1397163087944:ss=1397162906063");
		this.HTTPDownload(targeturl, filename);
	}
}
