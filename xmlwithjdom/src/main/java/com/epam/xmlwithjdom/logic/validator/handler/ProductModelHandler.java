package com.epam.xmlwithjdom.logic.validator.handler;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Handler class for obtaining model names from XML file
 * 
 */

public final class ProductModelHandler extends DefaultHandler {
	private static final String MODEL_ELEMENT_NAME = "model";
	
	List<String> existingModels = new ArrayList<String>();
	private String currElement = null;
	
	public List<String> getExistingModels() {
		return existingModels;
	}
	
	@Override
	public void startElement(String namespaceURI, String localName, 
			String qName, Attributes atts) {
		currElement = qName;
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException{
		if (MODEL_ELEMENT_NAME.equals(currElement)) {			
			String currModel = new String(ch, start, length);
			existingModels.add(currModel);
		}
	}
}
