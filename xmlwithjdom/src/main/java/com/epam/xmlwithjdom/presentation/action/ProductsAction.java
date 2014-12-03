package com.epam.xmlwithjdom.presentation.action;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.epam.xmlwithjdom.exception.CommandException;
import com.epam.xmlwithjdom.presentation.form.ProductsForm;
import com.epam.xmlwithjdom.util.JDOMHelper;

public class ProductsAction extends DispatchAction {
	
	// Forward names
	
	private static final String CATEGORIES_FORWARD = "categories";
	private static final String SUBCATEGORIES_FORWARD = "subcategories";
	private static final String PRODUCTS_FORWARD = "products";
	private static final String ERROR_FORWARD = "error";
	private static final String ADD_PRODUCT_FORWARD = "add_product";
	
	private static final String SOURCE_XML_PATH = "/WEB-INF/classes/products.xml";
	
	public ActionForward showCategories(ActionMapping actionMapping, 
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		
		ProductsForm form = (ProductsForm) actionForm;
		JDOMHelper productsHelper = JDOMHelper.getInstance();
		Document document = productsHelper.getDocument(request, SOURCE_XML_PATH);
		form.setProductsDocument(document);
		List<String> countOfProducts = productsHelper.getCountOfProductsForCategories();
		
		form.setCountOfProducts(countOfProducts);
		
		return actionMapping.findForward(CATEGORIES_FORWARD);
	}
	
	public ActionForward showSubcategories (ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		
		ProductsForm form = (ProductsForm) actionForm;
		JDOMHelper productsHelper = JDOMHelper.getInstance();
		Document document = productsHelper.getDocument(request, SOURCE_XML_PATH);
		form.setProductsDocument(document);
		
		String categoryName = request.getParameter("categoryname");
		form.setCurrCategoryName(categoryName);
		
		int currCategoryIndex = productsHelper.getCategoryIndex(categoryName);
		form.setCurrCategoryIndex(currCategoryIndex);
		
		List<String> countOfProducts = productsHelper.getCountOfProductsForSubcategories(categoryName);
		form.setCountOfProducts(countOfProducts);
		
		return actionMapping.findForward(SUBCATEGORIES_FORWARD);
	}
	
	public ActionForward showProducts (ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		
		ProductsForm form = (ProductsForm) actionForm;
		JDOMHelper productsHelper = JDOMHelper.getInstance();
		Document document = productsHelper.getDocument(request, SOURCE_XML_PATH);
		form.setProductsDocument(document);
		
		String subcategoryName = request.getParameter("subcategoryname");
		if (subcategoryName == null) {
			HttpSession session = request.getSession();
			subcategoryName = (String)session.getAttribute("prev_subcategory");
		}
		form.setCurrSubcategoryName(subcategoryName);

		String categoryName = form.getCurrCategoryName();
		int currSubcategoryIndex = productsHelper.getSubcategoryIndex(categoryName, subcategoryName);
		form.setCurrSubcategoryIndex(currSubcategoryIndex);
		
		HttpSession session = request.getSession();
		session.setAttribute("prev_category", categoryName);
		session.setAttribute("prev_subcategory", subcategoryName);
		
		return actionMapping.findForward(PRODUCTS_FORWARD);
	}
	
	public ActionForward add(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		
		ProductsForm form = (ProductsForm) actionForm;
		
		try {
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return actionMapping.findForward(PRODUCTS_FORWARD);
	}
	
	public ActionForward save(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		
		ProductsForm form = (ProductsForm) actionForm;
		Document productsDocument = form.getProductsDocument();
		XMLOutputter outputter = new XMLOutputter();
		outputter.setFormat(Format.getPrettyFormat());
		
		HttpSession session = request.getSession();
		ServletContext servletContext = session.getServletContext();
		String realPath = servletContext.getRealPath(SOURCE_XML_PATH);
		File sourceFile = new File(realPath);
		try (FileWriter writer = new FileWriter(sourceFile);){
			outputter.output(productsDocument, writer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return actionMapping.findForward(PRODUCTS_FORWARD);
	}
	
	public ActionForward editProduct(ActionMapping actionMapping, 
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		// TODO implement editing existing product
		return actionMapping.findForward(PRODUCTS_FORWARD);
	}
}