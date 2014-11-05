package com.epam.xmltransforming.dao.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.epam.xmltransforming.dao.IXMLDAO;
import com.epam.xmltransforming.entity.ProductCategory;
import com.epam.xmltransforming.exception.XMLDAOException;

public class SaxDAO implements IXMLDAO {
	private static final String EXC_SAX = "SAX exception";
	private static final String EXC_IO = "I/O exception";
	public List<ProductCategory> getCategories() throws XMLDAOException{
		List<ProductCategory> categories = new ArrayList<ProductCategory>();
		try {
			XMLReader reader = XMLReaderFactory.createXMLReader();
			ProductHandler handler = new ProductHandler();
			reader.setContentHandler(handler);
			reader.parse("products.xml");
			categories = handler.getProductCategories();
		} catch (SAXException e) {
			e.printStackTrace();
			throw new XMLDAOException(EXC_SAX, e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new XMLDAOException(EXC_IO, e);
		}
		return categories;
	}
}
