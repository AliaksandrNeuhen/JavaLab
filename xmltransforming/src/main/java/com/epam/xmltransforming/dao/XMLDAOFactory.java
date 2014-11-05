package com.epam.xmltransforming.dao;

import com.epam.xmltransforming.dao.impl.DomDAO;
import com.epam.xmltransforming.dao.impl.SaxDAO;
import com.epam.xmltransforming.dao.impl.StSaxDAO;
import com.epam.xmltransforming.exception.XMLDAOException;

public class XMLDAOFactory {
	
	// XML parser types
	
	private static final String TYPE_SAX = "sax";
	private static final String TYPE_DOM = "dom";
	private static final String TYPE_STSAX = "stsax";
	
	private static final XMLDAOFactory instance = new XMLDAOFactory();
	
	public static XMLDAOFactory getInstance() {
		return instance;
	}
	
	public IXMLDAO getDao(String type) throws XMLDAOException {
		if (TYPE_SAX.equalsIgnoreCase(type)) {
			return new SaxDAO();
		} else if (TYPE_DOM.equalsIgnoreCase(type)) {
			return new DomDAO();
		} else if (TYPE_STSAX.equalsIgnoreCase(type)) {
			return new StSaxDAO();
		} else return null;
	}
}
