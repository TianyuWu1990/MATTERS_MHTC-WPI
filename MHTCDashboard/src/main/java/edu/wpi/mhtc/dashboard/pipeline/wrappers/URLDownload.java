package edu.wpi.mhtc.dashboard.pipeline.wrappers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.MalformedInputException;

public class URLDownload {

	/**
	 * 
	 * @param targeturl
	 * @param filename
	 */
	public void HTTPDownload(String targeturl, String filename) {
		URL url = null;
		URLConnection con = null;
		int i;
		try {
			url = new URL(targeturl);
			con = url.openConnection();
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
