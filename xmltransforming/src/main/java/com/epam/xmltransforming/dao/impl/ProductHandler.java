package com.epam.xmltransforming.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import com.epam.xmltransforming.entity.Product;
import com.epam.xmltransforming.entity.ProductCategory;
import com.epam.xmltransforming.entity.ProductSubcategory;

enum CurrElement {	
	PROVIDER("PROVIDER"),
	MODEL("MODEL"),
	DATE_OF_ISSUE("DATE-OF-ISSUE"),
	COLOR("COLOR"),
	PRICE("PRICE");
	
	private String tagName;
	
	private CurrElement(String tagName) {
		this.tagName = tagName;
	}
	
	public String getTagName() {
		return tagName;
	}
}

public final class ProductHandler implements ContentHandler {
	private static final String CATEGORY_TAG = "category";
	private static final String SUBCATEGORY_TAG = "subcategory";
	private static final String PRODUCT_TAG = "product";
	private static final String SHOP_TAG = "p:shop";
	private static final String DATE_OF_ISSUE_TAG = "date-of-issue";
	
	private List<ProductCategory> productCategories = new ArrayList<ProductCategory>();
	private List<ProductSubcategory> productSubcategories = new ArrayList<ProductSubcategory>();
	private List<Product> products = new ArrayList<Product>();
	private ProductCategory currCategory = null;
	private ProductSubcategory currSubcategory = null;
	private Product currProduct = null;
	private CurrElement currElement = null;

	public List<ProductCategory> getProductCategories() {
		return productCategories;
	}
	
	public void startElement(String uri, String localName, 
			String qName, Attributes attrs) {
		if (CATEGORY_TAG.equalsIgnoreCase(qName)) {
			// Category tag found
			productSubcategories.clear();
			currCategory = new ProductCategory();
			String categoryName = attrs.getValue(0);
			currCategory.setName(categoryName);
		} else if (SUBCATEGORY_TAG.equalsIgnoreCase(qName)) {
			// Subcategory tag found
			products.clear();
			currSubcategory = new ProductSubcategory();
			String subcategoryName = attrs.getValue(0);
			currSubcategory.setName(subcategoryName);
		} else if (PRODUCT_TAG.equalsIgnoreCase(qName)) {
			// Product tag found
			currProduct = new Product();
			String productName = attrs.getValue(0);
			currProduct.setName(productName);
		} else if (!SHOP_TAG.equalsIgnoreCase(qName)) {
			// Other tag except root tag found
			if (DATE_OF_ISSUE_TAG.equalsIgnoreCase(qName)) {
				currElement = CurrElement.DATE_OF_ISSUE;
			} else {
				currElement = CurrElement.valueOf(qName.toUpperCase());
			}
		}
	}
	
	public void endElement(String uri, String localName, 
			String qName) {
		if (PRODUCT_TAG.equalsIgnoreCase(qName)) {
			// Product tag ends
			System.out.println(currProduct.toString());
			try {
				products.add(currProduct.clone());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			
		} else if (SUBCATEGORY_TAG.equalsIgnoreCase(qName)) {
			// Subcategory tag ends
			currSubcategory.setProducts(products);
			try {
				productSubcategories.add(currSubcategory.clone());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		} else if (CATEGORY_TAG.equalsIgnoreCase(qName)) {
			// Category tag ends
			currCategory.setSubcategories(productSubcategories);
			try {
				productCategories.add(currCategory.clone());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		currElement = null;
	}
	
	public void characters(char[] ch, int start, int length) {
		String tagContent = new String(ch, start, length).trim();
		if (currElement == null) {
			return;
		}
		switch (currElement) {
			case PROVIDER:
				currProduct.setProvider(tagContent);
				break;
			case MODEL:
				currProduct.setModel(tagContent);
				break;
			case COLOR:
				currProduct.setColor(tagContent);
				break;
			case DATE_OF_ISSUE:
				currProduct.setDateOfIssue(tagContent);
				break;
			case PRICE:
				currProduct.setPrice(Integer.valueOf(tagContent));
				break;
		}
		
	}

	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void endPrefixMapping(String prefix) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void processingInstruction(String target, String data)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void setDocumentLocator(Locator locator) {
		// TODO Auto-generated method stub
		
	}

	public void skippedEntity(String name) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}
	
	
}
