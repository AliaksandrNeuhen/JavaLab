package com.epam.xmlwithjdom.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.Element;

public class JDOMHelper {
	private Document currDocument;
	private long lastModified;
	private static JDOMHelper instance = null;
	
	public synchronized static JDOMHelper getInstance() {
		if (instance == null) {
			instance = new JDOMHelper();
		}
		return instance;
	}
	
	private JDOMHelper() {
		currDocument = new Document();
		lastModified = 0L;
	}
	
	public Document getDocument(HttpServletRequest request, 
			String path){
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		String sourcePath = context.getRealPath(path);
		File sourceFile = new File(sourcePath);
		if (lastModified < sourceFile.lastModified()) {
			System.out.println("Building document");
			SAXBuilder builder = new SAXBuilder();
			try {
				currDocument = builder.build(sourceFile);
				lastModified = sourceFile.lastModified();
			} catch (JDOMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Geting existing document");
		}
				
		return currDocument;
	}
	
	public List<String> getCountOfProductsForCategories() {
		List<String> countOfProducts = new ArrayList<String>();
		Element root = currDocument.getRootElement();
		List<Element> categories = root.getChildren();
		for (Element category: categories) {
			int countOfProductsInCurrCategory = 0;
			List<Element> subcategories = category.getChildren();
			for (Element subcategory: subcategories) {
				countOfProductsInCurrCategory += subcategory.getChildren().size();
			}
			String countString = String.valueOf(countOfProductsInCurrCategory);
			countOfProducts.add(countString);
		}
		return countOfProducts;
	}
	
	public List<String> getCountOfProductsForSubcategories(String categoryName) {
		List<String> countOfProducts = new ArrayList<String>();
		Element rootElement = currDocument.getRootElement();
		List<Element> categories = rootElement.getChildren();
		Element categoryElement = new Element("category");
		categoryElement.setAttribute("name", "");
		for (Element currCategory: categories) {
			String currCategoryName = currCategory.getAttributeValue("name");
			if (categoryName.equals(currCategoryName)) {
				categoryElement = currCategory;
				break;
			}
		}
		List<Element> subcategories = categoryElement.getChildren();
		for (Element subcategory: subcategories) {
			int countOfProductsInCurrentSubcategory = 0;
			countOfProductsInCurrentSubcategory = subcategory.getChildren().size();
			String countString = String.valueOf(countOfProductsInCurrentSubcategory);
			countOfProducts.add(countString);
		}
		return countOfProducts;
	}
	
	public int getCategoryIndex(String categoryName) {
		Element rootElement = currDocument.getRootElement();
		List<Element> categories = rootElement.getChildren();
		int index = 0;
		for (Element category: categories) {
			String currCategoryName = category.getAttributeValue("name");
			if (categoryName.equalsIgnoreCase(currCategoryName)) {
				break;
			} else {
				index++;
			}
		}
		return index;
	}
	
	public int getSubcategoryIndex(String categoryName, String subcategoryName) {
		int categoryIndex = getCategoryIndex(categoryName);
		Element rootElement = currDocument.getRootElement();
		List<Element> categories = rootElement.getChildren();
		Element categoryElement = categories.get(categoryIndex);
		
		List<Element> subcategories = categoryElement.getChildren();
		int index = 0;
		for (Element subcategory: subcategories) {
			String currSubcategoryName = subcategory.getAttributeValue("name");
			if (subcategoryName.equalsIgnoreCase(currSubcategoryName)) {
				break;
			} else {
				index++;
			}
		}
		return index;
	}
}
