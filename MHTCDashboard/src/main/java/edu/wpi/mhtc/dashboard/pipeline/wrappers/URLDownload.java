package edu.wpi.mhtc.dashboard.pipeline.wrappers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.MalformedInputException;
import java.util.HashMap;

public class URLDownload {
	private HashMap<String, String> requestData = new HashMap<String, String>();
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
			// Set connection request data
			for (String key: requestData.keySet()) {
				con.setRequestProperty(key, requestData.get(key));
			}
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
	
	/**
	 * This function sets request data to a hashmap to be configured in HTTPDownload method.
	 * @param key: Request data key
	 * @param value: Request data value
	 */
	public void setRequestData(String key, String value) {
		requestData.put(key, value);
	}

}
