package edu.wpi.mhtc.pipeline.config.generator;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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

import edu.wpi.mhtc.pipeline.config.FileInfoConfig;
import edu.wpi.mhtc.pipeline.config.IConfigFilePath;
import edu.wpi.mhtc.pipeline.parser.IParser;
import edu.wpi.mhtc.pipeline.parser.ParserFactory;

public class CatInfoGenerator {
	private DocumentBuilderFactory docFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private Element catInfos;
	private List<FileInfo> fileInfos;
	private String outputFileName = IConfigFilePath.CatInfoConfigPath;

	public CatInfoGenerator() {
		this.docFactory = DocumentBuilderFactory.newInstance();
		try {
			this.docBuilder = this.docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		this.doc = this.docBuilder.newDocument();
		this.catInfos = this.doc.createElement("config");
		this.fileInfos = new LinkedList<FileInfo>();
	}

	public void generate() throws Exception {
		this.doc.appendChild(this.catInfos);

		this.fileInfos = FileInfoConfig.getInstance().getFileInfos();

		for (FileInfo fileInfo : this.fileInfos) {
			this.generateCatInfo(fileInfo);
		}

		// write the content into XML file
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(
					"{http://xml.apache.org/xslt}indent-amount", "4");
			DOMSource source = new DOMSource(this.doc);

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

	void generateCatInfo(FileInfo fileInfo) throws Exception {
		String absoluteName = fileInfo.getFileName();
		String name = absoluteName.substring(
				absoluteName.lastIndexOf(File.separator) + 1,
				absoluteName.length());

		Element catInfo = this.doc.createElement("catInfo");
		this.catInfos.appendChild(catInfo);

		// catID elements//
		Element catID = this.doc.createElement("catID");
		catID.appendChild(this.doc.createTextNode("null"));
		catInfo.appendChild(catID);

		// catName elements
		Element catName = this.doc.createElement("catName");
		catName.appendChild(this.doc.createTextNode(name.substring(0,
				name.lastIndexOf("."))));
		catInfo.appendChild(catName);

		// fileName elements
		Element fileName = this.doc.createElement("fileName");
		fileName.appendChild(this.doc.createTextNode(absoluteName));
		catInfo.appendChild(fileName);

		// metric elements
		IParser parser = ParserFactory.getInstance(fileInfo);
		List<String> list = parser.validateMetrics();

		for (String str : list) {

			// System.out.println(str);
			Element metric = this.doc.createElement("metric");

			// columnName in files
			Element columnName = this.doc.createElement("columnName");
			columnName.appendChild(this.doc.createTextNode(str));
			metric.appendChild(columnName);

			// metricName in DB
			Element metricName = this.doc.createElement("metricName");
			metricName.appendChild(this.doc.createTextNode(str));
			metric.appendChild(metricName);

			// type for cleaners
			Element type = this.doc.createElement("type");
			if (str.equalsIgnoreCase("state")) {
				type.appendChild(doc.createTextNode("state"));
			} else if (str.equalsIgnoreCase("year")) {
				type.appendChild(doc.createTextNode("year"));
			} else {
				type.appendChild(doc.createTextNode("numeric"));
			}

			metric.appendChild(type);

			catInfo.appendChild(metric);
		}

	}
}
