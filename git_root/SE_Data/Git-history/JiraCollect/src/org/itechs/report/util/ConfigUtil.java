package org.itechs.report.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConfigUtil {

	private static Document doc;
	private static XPath xpath;
	public static List<String> getProperties(String path){
		List<String> results = new ArrayList<String>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			doc = db.parse(new FileInputStream(new File("./config/report_global.xml")));
			
			XPathFactory factory = XPathFactory.newInstance();
	        xpath = factory.newXPath();
	        
	        NodeList nodeList = (NodeList) xpath.evaluate(path,doc, XPathConstants.NODESET);
	        for(int i=0; i< nodeList.getLength(); i++){
	        	
	        	Node node = nodeList.item(i);
	        	results.add(node.getTextContent());
	        	//System.out.println(node.getTextContent());
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
		String value = "";
		
		return results;
		
	}
	public static String getFileType(String fileName){
		
		String type = "";
		type = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
		return type;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConfigUtil.getProperties("/config/fileTypes/file");
	}

}
