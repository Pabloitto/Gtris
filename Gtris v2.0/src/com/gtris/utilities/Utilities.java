package com.gtris.utilities;

import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 * Tools
 * @author Pablo
 *
 */
public class Utilities {
	  
	 /**
	  * Url with the config 
	  */
	 private final InputStream url;
	 
	 private static Utilities utils;
	 
	 private InputStream xmlFile;
	 
	 private DocumentBuilderFactory dbFactory;
	 
	 private DocumentBuilder dBuilder;
	 
	 private static Document doc;
	 
	 private Utilities() throws Exception{
		url = getClass()
					.getClassLoader()
					.getResourceAsStream("com/gtris/utilities/config.xml");
		
		 xmlFile =  getUrl();
		 dbFactory = DocumentBuilderFactory.newInstance();
		 dBuilder = dbFactory.newDocumentBuilder();
		 doc = dBuilder.parse(xmlFile);
		 doc.getDocumentElement().normalize();
	 }
	 
	 /**
	  * Get random number 
	  * @param offset, first
	  * @param limit, last
	  * @return
	  */
	 public static int getRandomNumber(int offset , int limit){
	        return offset + (int)(Math.random() * ((limit - offset)));
	 }
	 
	 /**
	  * This method return a hash map with the config and the url of the resource
	  * @param tag attribute in xml
	  * @return
	  */
	 public static HashMap<String,String> readConfig(String tag){
		 HashMap<String,String> config = new HashMap<>();
		 try{
			 if(utils == null){
				 utils = new Utilities();
			 }
			 
			 NodeList nList = doc.getElementsByTagName(tag);
			 for(int i = 0;i<nList.getLength();i++){
				 Node nNode = nList.item(i);
				 if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					 Element eElement = (Element) nNode;
					 NamedNodeMap map = eElement.getAttributes();
					 for(int j = 0;j<map.getLength();j++){
						 config.put(eElement.getAttribute(map.item(j).getNodeName()),
								eElement.getTextContent().trim());
					 }
				 }
			 }
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 return config;
	 }
	 public InputStream getUrl(){
		 return this.url;
	 }
	
	
}
