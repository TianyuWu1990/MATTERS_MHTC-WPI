/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dashboard.pipeline.wrappers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.MalformedInputException;
import java.util.HashMap;

/**
 * Wrapper for direct download links.
 *
 */
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
	 * Get the HTML source code/content of the URL
	 * @param url: The URL
	 * @return: String HTML source code
	 * @throws Exception
	 */
    public static String getText(String url) throws Exception {
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                    connection.getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) 
            response.append(inputLine);

        in.close();

        return response.toString();
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
