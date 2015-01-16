/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dashboard.pipeline.wrappers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

/**
 * Text extractor for pdf files.
 * @author cakuhlman
 *
 */
public class PDFReader {

	/**
	 * Extract Unicode characters from pdf and write to a text file.
	 * @param input The PDF file from where you would like to extract.
	 */
	public void readPDF(File input){
		String name = FilenameUtils.removeExtension(input.getPath());
		System.out.println(name + "");
		File output = new File(name+".txt"); // The text file where you are going to store the extracted data
		

		try(BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output))); PDDocument pd = PDDocument.load(input)){

			int numPages = pd.getNumberOfPages();
			PDFTextStripper stripper = new PDFTextStripper();
			stripper.setStartPage(1); 
			stripper.setEndPage(numPages); 
			stripper.setWordSeparator(";");
			stripper.writeText(pd, wr);
		} catch (Exception e){
			e.printStackTrace();
		} 
	}
	
	public static void main(String[] args){
		new PDFReader().readPDF(new File("aetr-2013est.pdf"));
	}

}
