package edu.wpi.mhtc.dashboard.pipeline.wrappers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.MalformedInputException;

public class BEADownload {
	/**
	 * 
	 * @param targeturl
	 * @param filename
	 */
	public void getBEA(String targeturl, String filename) {

		URL url = null;
		URLConnection con = null;
		int i;
		try {
			url = new URL(targeturl);
			con = url.openConnection();
			con.setRequestProperty(
					"Cookie",
					"JSESSIONID=2c30becf0113000ef9204f67413c4b547e40; __utma=125733781.915281630.1395942821.1397160595.1397166506.5; __utmb=125733781.10.10.1397166506; __utmc=125733781; __utmz=125733781.1395942821.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); WT_FPC=id=b754455a-eda4-4ecc-93ef-bc80e6b72fe7:lv=1397163087944:ss=1397162906063");
			File file = new File(filename);
			BufferedInputStream bis = new BufferedInputStream(
					con.getInputStream());
			@SuppressWarnings("resource")
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(file.getAbsoluteFile()));
			while ((i = bis.read()) != -1) {
				bos.write(i);
			}
			bos.flush();
			bis.close();
		} catch (MalformedInputException malformedInputException) {
			malformedInputException.printStackTrace();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}

	}
}
