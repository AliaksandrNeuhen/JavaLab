package com.epam.xmlwithjdom.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.Element;

import com.epam.xmlwithjdom.exception.ServletException;
/**
 * Class for getting some information about xml file's contents.
 * 
 */
public final class JDOMHelper {
	
	private static final String NAME_ATTR = "name";
	private static final String MODEL_ELEMENT = "model";
	
	private Document currDocument;
	private long lastModified;
	private static volatile JDOMHelper instance = null;
	
	public static JDOMHelper getInstance() {
		if (instance == null) {
			JDOMHelper localInstance = instance;
			if (localInstance == null) {
				synchronized (JDOMHelper.class) {
					localInstance = instance;
					if (localInstance == null) {
						instance = localInstance = new JDOMHelper();
					}
				}
			}
			instance = new JDOMHelper();
		}
		return instance;
	}
	
	private JDOMHelper() {
		currDocument = new Document();
		lastModified = 0L;
	}
	
	/**
	 * Method for getting XML document. It builds a document if
	 * it was changed since last method usage, else returns cached document. 
	 * @param request
	 * @param path
	 * @return current using document
	 */
	public Document getDocument(HttpServletRequest request, 
			String path) throws ServletException{
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		String sourcePath = context.getRealPath(path);
		File sourceFile = new File(sourcePath);
		if (lastModified < sourceFile.lastModified()) {
			SAXBuilder builder = new SAXBuilder();
			try {
				currDocument = builder.build(sourceFile);
				lastModified = sourceFile.lastModified();
			} catch (JDOMException e) {
				throw new ServletException("Can't build xml document", e);
			} catch (IOException e) {
				throw new ServletException("Can't open xml file", e);
			}
		}				
		return currDocument;
	}

	/**
	 * Method for getting category index by it's name.
	 * @param categoryName
	 * @return
	 */
	public int getCategoryIndex(String categoryName) {
		Element rootElement = currDocument.getRootElement();
		List<Element> categories = rootElement.getChildren();
		int index = 0;
		for (Element category: categories) {
			String currCategoryName = category.getAttributeValue(NAME_ATTR);
			if (categoryName.equalsIgnoreCase(currCategoryName)) {
				break;
			} else {
				index++;
			}
		}
		return index;
	}
	
	/**
	 * Method for getting subcategory index by it's name.
	 * @param categoryName
	 * @param subcategoryName
	 * @return
	 */
	public int getSubcategoryIndex(String categoryName, String subcategoryName) {
		int categoryIndex = getCategoryIndex(categoryName);
		Element rootElement = currDocument.getRootElement();
		List<Element> categories = rootElement.getChildren();
		Element categoryElement = categories.get(categoryIndex);
		
		List<Element> subcategories = categoryElement.getChildren();
		int index = 0;
		for (Element subcategory: subcategories) {
			String currSubcategoryName = subcategory.getAttributeValue(NAME_ATTR);
			if (subcategoryName.equalsIgnoreCase(currSubcategoryName)) {
				break;
			} else {
				index++;
			}
		}
		return index;
	}

	/**
	 * Method for getting string with all model names in document
	 * except for chosen subcategory. It was done for correct validating uniqueness
	 * of each model name. Model names in chosen subcategory can be changed by user, that's why this
	 * information is not needed.
	 * @param subcategoryName
	 * @return
	 */
	public String getModelsExceptChosenSubcategory(String subcategoryName){
		StringBuilder models = new StringBuilder();
		Element rootElement = currDocument.getRootElement();
		List<Element> categories = rootElement.getChildren();
		for (Element category: categories) {
			List<Element> subcategories = category.getChildren();
			for (Element subcategory: subcategories) {
				Attribute nameAttribute = subcategory.getAttribute(NAME_ATTR);
				// Skip the chosen subcategory
				if (nameAttribute.getValue().equals(subcategoryName)) {
					continue;
				}
				
				List<Element> products = subcategory.getChildren();
				for (Element product: products) { 
					String currModel = product.getChildText(MODEL_ELEMENT) + " ";
					models.append(currModel);
				}
			}
		}
		return models.toString();
	}
}
