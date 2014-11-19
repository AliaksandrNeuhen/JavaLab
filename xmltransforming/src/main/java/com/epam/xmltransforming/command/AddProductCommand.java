package com.epam.xmltransforming.command;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import com.epam.xmltransforming.exception.CommandException;
import com.epam.xmltransforming.logic.ResultCreator;
import com.epam.xmltransforming.logic.SourceCreator;
import com.epam.xmltransforming.util.ProductFieldsCheck;

public final class AddProductCommand implements ICommand {	
	private static final String SOURCE_PATH = "/WEB-INF/classes/products.xml";
	private static final String ADD_PRODUCT_SOURCE_PATH = "/xslt/add_product.xslt";
	private static final String PRODUCTS_LIST_SOURCE_PATH = "/xslt/product_list.xslt";
	
	private static final String PREV_SUBCATEGORY_SESSION_ATTR = "prev_subcategory";
	private static final String PREV_CATEGORY_SESSION_ATTR = "prev_category";
	
	private static final String TRYING_AGAIN_PARAM = "tryingAgain";
	private static final String VALIDATOR_OBJECT_PARAM = "validatorObject";
	private static final String CHECKED = "checked";
	private static final String CATEGORY_NAME_PARAM = "categoryname";
	private static final String SUBCATEGORY_NAME_PARAM = "subcategoryname";
	private static final String NAME_PARAM = "name";
	private static final String PROVIDER_PARAM = "provider";
	private static final String MODEL_PARAM = "model";
	private static final String DATE_OF_ISSUE_PARAM = "dateOfIssue";
	private static final String COLOR_PARAM = "color";
	private static final String PRICE_PARAM = "price";
	private static final String NOT_IN_STOCK_PARAM = "notInStock";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws CommandException {
		ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
		ReadLock readLock = readWriteLock.readLock();
		WriteLock writeLock = readWriteLock.writeLock();
		try {			
			// Set input	
			Source source = SourceCreator.createFileSource(request, SOURCE_PATH);
			// Create transformer
			Source xsltSource = SourceCreator.createFileSource(request, ADD_PRODUCT_SOURCE_PATH);
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer(xsltSource);

			// Set output
			// At this moment, don't really know either to add product to XML
			// or to show page with input errors.
			// That's why we just need to create ByteArrayOutputStream. It will be
			// forwarded to the correct output stream after checking for validation
			StreamResult result = ResultCreator.createByteArrayOutputStreamResult();
			
			// Get required parameters from session and request
			// and add the to transformer
			HttpSession session = request.getSession();
			String subcategory = (String)session.getAttribute(PREV_SUBCATEGORY_SESSION_ATTR);
			String category = (String)session.getAttribute(PREV_CATEGORY_SESSION_ATTR);
			transformer.setParameter(SUBCATEGORY_NAME_PARAM, subcategory);
			transformer.setParameter(CATEGORY_NAME_PARAM, category);
			if (request.getParameter(NAME_PARAM) != null) {
				transformer.setParameter(NAME_PARAM, request.getParameter(NAME_PARAM));
				transformer.setParameter(PROVIDER_PARAM, request.getParameter(PROVIDER_PARAM));
				transformer.setParameter(MODEL_PARAM, request.getParameter(MODEL_PARAM));
				transformer.setParameter(DATE_OF_ISSUE_PARAM, request.getParameter(DATE_OF_ISSUE_PARAM));
				transformer.setParameter(COLOR_PARAM, request.getParameter(COLOR_PARAM));
				transformer.setParameter(PRICE_PARAM, request.getParameter(PRICE_PARAM));
			}
			if (request.getParameter(NOT_IN_STOCK_PARAM) != null) {
				String notInStockState = request.getParameter(NOT_IN_STOCK_PARAM);
				Boolean notInStockBoolean = false;
				if (CHECKED.equals(notInStockState)){
					notInStockBoolean = true;
				}
				transformer.setParameter(NOT_IN_STOCK_PARAM,notInStockBoolean);
			}			
			
			// Variable for checking if user already tried to add product but failed
			// When tryingAgain == false, error messages aren't shown on the page
			Boolean tryingAgain = false;
			if (request.getParameter(TRYING_AGAIN_PARAM) != null){
				tryingAgain = Boolean.valueOf(request.getParameter(TRYING_AGAIN_PARAM));			
			}
			transformer.setParameter(TRYING_AGAIN_PARAM, tryingAgain);
			
			// Object used for validating input fields using xalan while transforming
			ProductFieldsCheck validator = new ProductFieldsCheck();
			transformer.setParameter(VALIDATOR_OBJECT_PARAM, validator);

			readLock.lock();
			try {
				transformer.transform(source, result);	
			} finally {
				readLock.unlock();
			}
		
			Boolean isValid = validator.getResultOfValidation();
			
			ByteArrayOutputStream byteArrayOutputStream = 
					(ByteArrayOutputStream)result.getOutputStream();
			
			// If validation succeded, create a new xml file with new product copied to it
			// and show the page with list of products in current subcategory.
			// Else show page with error messages.
			if (isValid) {
				//Copy new product to xml
				writeLock.lock();
				try {
					byteArrayOutputStream.reset();
					transformer.transform(source, result);
					
					String xmlPath = SourceCreator.getRealPath(request, SOURCE_PATH);
					OutputStream fileOutputStream = new FileOutputStream(xmlPath);
					byte [] outputStreamContent = byteArrayOutputStream.toByteArray();
					fileOutputStream.write(outputStreamContent);
					fileOutputStream.close();	
				} finally {
					writeLock.unlock();
				}
				
				// Show list of products
				Source xsltProductListSource = SourceCreator
						.createFileSource(request, PRODUCTS_LIST_SOURCE_PATH);
				
				transformer = tFactory.newTransformer(xsltProductListSource);
				transformer.setParameter(SUBCATEGORY_NAME_PARAM, subcategory);
				transformer.setParameter(CATEGORY_NAME_PARAM, category);
				StreamResult resultList = ResultCreator.createResponseOutputStreamResult(response);
				readLock.lock();
				try {
					transformer.transform(source, resultList);	
				} finally {
					readLock.unlock();
				}
			} else {
				OutputStream respoOutputStream = response.getOutputStream();
				byteArrayOutputStream.writeTo(respoOutputStream);
			}
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
