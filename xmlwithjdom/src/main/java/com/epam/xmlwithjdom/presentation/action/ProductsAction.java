package com.epam.xmlwithjdom.presentation.action;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.epam.xmlwithjdom.exception.ServletException;
import com.epam.xmlwithjdom.presentation.form.ProductsForm;
import com.epam.xmlwithjdom.util.JDOMHelper;
import com.epam.xmlwithjdom.util.ProductsReadWriteLock;
/**
 * Action class for product-related requests processing
 * 
 */
public final class ProductsAction extends DispatchAction {
	
	private static final String SOURCE_XML_PATH = "/WEB-INF/classes/products.xml";
	
	// Forward names
	
	private static final String CATEGORIES_FORWARD = "categories";
	private static final String SUBCATEGORIES_FORWARD = "subcategories";
	private static final String PRODUCTS_FORWARD = "products";
	
	// Request parameters
	
	private static final String CATEGORY_NAME_PARAM = "categoryname";
	private static final String SUBCATEGORY_NAME_PARAM = "subcategoryname";
	private static final String PAGE_PARAM = "page";
	
	//Session attributes
	
	private static final String PREV_CATEGORY_ATTR = "prev_category";
	private static final String PREV_SUBCATEGORY_ATTR = "prev_subcategory";
	
	// XML element names
	
	private static final String PRICE_ELEMENT = "price";
	private static final String NOT_IN_STOCK_ELEMENT = "notinstock";
	
	// Exception messages
	
	private static final String CANT_WRITE_EXCEPTION = "Can't write to file";
	
	// ReentrantReadWriteLock for locking xml file
	
	private static final ProductsReadWriteLock readWriteLock = ProductsReadWriteLock.getInstance();
	
	/**
	 * Method for showing categories list from xml file.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return forward to categories jsp
	 */
	public ActionForward showCategories(ActionMapping actionMapping, 
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		ProductsForm form = (ProductsForm) actionForm;
		JDOMHelper productsHelper = JDOMHelper.getInstance();
		
		// Get document and count of products inside each category
		ReadLock readLock = readWriteLock.readLock();
		Document document;
		readLock.lock();
		try {
			document = productsHelper.getDocument(request, SOURCE_XML_PATH);
		}  finally {
			readLock.unlock();	
		}
		
		form.setProductsDocument(document);
		
		return actionMapping.findForward(CATEGORIES_FORWARD);
	}
	
	/**
	 * Method for showing subcategories of chosen category.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return forward to subcategories jsp
	 */
	public ActionForward showSubcategories (ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws ServletException{
		
		ProductsForm form = (ProductsForm) actionForm;
		JDOMHelper productsHelper = JDOMHelper.getInstance();
		
		// Get document, index of current category and count of products
		// in each subcategory of chosen category
		String categoryName = request.getParameter(CATEGORY_NAME_PARAM);
		Document document;
		int currCategoryIndex; 
		ReadLock readLock = readWriteLock.readLock();
		readLock.lock();
		try {
			document = productsHelper.getDocument(request, SOURCE_XML_PATH);
			currCategoryIndex = productsHelper.getCategoryIndex(categoryName);
		} finally {
			readLock.unlock();
		}
		
		form.setProductsDocument(document);
		form.setCurrCategoryName(categoryName);
		form.setCurrCategoryIndex(currCategoryIndex);
	
		return actionMapping.findForward(SUBCATEGORIES_FORWARD);
	}
	
	/**
	 * Method for showing products in chosen subcategory and editing it.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showProducts (ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws ServletException{
		
		ProductsForm form = (ProductsForm) actionForm;
		JDOMHelper productsHelper = JDOMHelper.getInstance();
		
		String categoryName = form.getCurrCategoryName();
		
		// Put chosen subcategory name in session for correct working of "Back" button
		String subcategoryName = request.getParameter(SUBCATEGORY_NAME_PARAM);
		if (subcategoryName == null) {
			HttpSession session = request.getSession();
			subcategoryName = (String)session.getAttribute(PREV_SUBCATEGORY_ATTR);
		}
		
		// Get document, current subcategory index and string with existing models from xml file.
		Document document;
		int currSubcategoryIndex;
		String models;
		ReadLock readLock = readWriteLock.readLock();
		readLock.lock();
		try {
			document = productsHelper.getDocument(request, SOURCE_XML_PATH);
			currSubcategoryIndex = productsHelper.getSubcategoryIndex(categoryName, subcategoryName);
			models = productsHelper.getModelsExceptChosenSubcategory(subcategoryName);
		} finally {
			readLock.unlock();
		}
		form.setProductsDocument(document);
		form.setCurrSubcategoryName(subcategoryName);
		form.setCurrSubcategoryIndex(currSubcategoryIndex);
		form.setModels(models);
		
		HttpSession session = request.getSession();
		session.setAttribute(PREV_CATEGORY_ATTR, categoryName);
		session.setAttribute(PREV_SUBCATEGORY_ATTR, subcategoryName);
		
		return actionMapping.findForward(PRODUCTS_FORWARD);
	}
	
	/**
	 * Method for saving edition results to xml
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward save(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws ServletException{
		
		ProductsForm form = (ProductsForm) actionForm;
		// Format price for writing correct xml file
		formatPrice(form);
		Document productsDocument = form.getProductsDocument();
		XMLOutputter outputter = new XMLOutputter();
		outputter.setFormat(Format.getPrettyFormat());
		HttpSession session = request.getSession();
		ServletContext servletContext = session.getServletContext();
		String realPath = servletContext.getRealPath(SOURCE_XML_PATH);
		File sourceFile = new File(realPath);
		
		// Write xml file
		WriteLock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		try (FileWriter writer = new FileWriter(sourceFile);){
			outputter.output(productsDocument, writer);
		} catch (IOException e) {
			throw new ServletException(CANT_WRITE_EXCEPTION, e);
		} finally {
			writeLock.unlock();
		}
		
		return actionMapping.findForward(PRODUCTS_FORWARD);
	}

	
	/**
	 * Method for showing previous shown page. It gets forward name as request's parameter
	 * and returns forward assigned to that name.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward back(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		
		String backPage = request.getParameter(PAGE_PARAM);
		
		return actionMapping.findForward(backPage);
	}
	
	
	/**
	 * Method for formating price element. 
	 * If element is empty, it changes it to "notinstock" element.
	 * If element is "notinstock" and contain some text inside itself, 
	 * then changes it to "price" element.
	 * @param form
	 */
	private void formatPrice(ProductsForm form) {
		Document productsDocument = form.getProductsDocument();
		Element rootElement = productsDocument.getRootElement();
		
		// Get list of categories elements
		List<Element> categories = rootElement.getChildren();
		int currCategoryIndex = form.getCurrCategoryIndex();
		// Get needed category element by it's index
		Element currCategory = categories.get(currCategoryIndex);
		int currSubcategoryIndex = form.getCurrSubcategoryIndex();
		// Get needed subcategory element by it's index
		Element currSubcategory = currCategory.getChildren().get(currSubcategoryIndex);
		// Get list of products
		List<Element> products = currSubcategory.getChildren();
		for (Element product: products) {
			Element priceElement = product.getChild(PRICE_ELEMENT);
			if (priceElement == null) {
				priceElement = product.getChild(NOT_IN_STOCK_ELEMENT);
				String priceValue = priceElement.getText();
				if (!priceValue.isEmpty()) {
					priceElement.setName(PRICE_ELEMENT);
				}
			} else {
				String priceValue = priceElement.getText();
				if (priceValue.isEmpty()) {
					priceElement.setName(NOT_IN_STOCK_ELEMENT);
				}
			}
		}
	}
}