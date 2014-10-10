package edu.wpi.mhtc.pipeline.config.generator;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.wpi.mhtc.pipeline.config.IConfigFilePath;
import edu.wpi.mhtc.pipeline.data.FileType;

public class FileInfoConfigGenerator {
	private File directory;
	private File[] files;
	private DocumentBuilderFactory docFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private Element fileInfos;
	private String directoryName;
	private String outputFileName = IConfigFilePath.FileInfoConfigPath;

	public FileInfoConfigGenerator(String directoryName) {
		this.directoryName = directoryName;
		this.directory = new File(this.directoryName);
		this.files = directory.listFiles();
		this.docFactory = DocumentBuilderFactory.newInstance();
		try {
			this.docBuilder = this.docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		this.doc = docBuilder.newDocument();
		this.fileInfos = doc.createElement("config");
	}

	public void generate() {
		doc.appendChild(fileInfos);

		getFiles(this.directory, this.files, this.directory.getName());
		// write the content into XML file
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(
					"{http://xml.apache.org/xslt}indent-amount", "4");
			DOMSource source = new DOMSource(doc);

			File outputFile = new File(this.outputFileName);
			if (!outputFile.exists()) {
				try {
					outputFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			StreamResult result = new StreamResult(outputFile);
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	void getFiles(File directory, File[] files, String parentDirectory) {
		for (File file : files) {
			if (file.isDirectory()) {
				getFiles(file, file.listFiles(), parentDirectory
						+ File.separator + file.getName());
			} else {
				getFileInfo(file, parentDirectory);
			}
		}
	}

	void getFileInfo(File file, String currentDirectory) {
		String name = file.getName();
		String type = name.substring(name.lastIndexOf(".") + 1, name.length());
		if (FileType.isMember(type)) {
			Element fileInfo = doc.createElement("fileInfo");
			fileInfos.appendChild(fileInfo);

			// fileName elements
			Element absoluteName = doc.createElement("absoluteName");
			absoluteName.appendChild(doc.createTextNode(currentDirectory
					+ File.separator + name));
			fileInfo.appendChild(absoluteName);

			// fileType elements
			Element fileType = doc.createElement("fileType");
			fileType.appendChild(doc.createTextNode(type));
			fileInfo.appendChild(fileType);

			// need Cleaning
			Element needCleaning = doc.createElement("isRowSpecified");
			needCleaning.appendChild(doc.createTextNode("true"));
			fileInfo.appendChild(needCleaning);

			// startRow elements
			Element startRow = doc.createElement("startRow");
			startRow.appendChild(doc.createTextNode("null"));
			fileInfo.appendChild(startRow);

			// endRow elements
			Element endRow = doc.createElement("endRow");
			endRow.appendChild(doc.createTextNode("null"));
			fileInfo.appendChild(endRow);

			// parserType elements
			Element parserType = doc.createElement("parserType");
			parserType.appendChild(doc.createTextNode("type1"));
			fileInfo.appendChild(parserType);
		} else {
			System.out.println("Unacceptable file type:" + type);
		}
	}
}
