package com.epam.xmlwithjdom.command;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import javax.xml.transform.stream.StreamSource;

import com.epam.xmlwithjdom.exception.CommandException;
import com.epam.xmlwithjdom.logic.validator.ProductFieldsCheck;
import com.epam.xmlwithjdom.util.ProductsReadWriteLock;
import com.epam.xmlwithjdom.util.ResultCreator;
import com.epam.xmlwithjdom.util.SourceCreator;

/**
 * Command for adding products to XML file.
 * If the validation was succeded, it makes a copy of XML with new product added and redirect
 * to the list of products in current subcategory.
 * Else, it shows a page with input errors.
 */

public final class AddProductCommand implements ICommand {
	
	// Redirect pages
	
	private static final String SHOW_PRODUCTS_REDIRECT = "shop.do?command=showProducts";
	
	// File pathes
	
	private static final String SOURCE_PATH = "/WEB-INF/classes/products.xml";
	private static final String ADD_PRODUCT_SOURCE_PATH = "/xslt/add_product.xsl";
	
	// Session attributes
	
	private static final String PREV_SUBCATEGORY_SESSION_ATTR = "prev_subcategory";
	private static final String PREV_CATEGORY_SESSION_ATTR = "prev_category";
	
	// Transformer parameters
	
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
	
	// Exception  messages

	private static final String TRANSFORMER_CONFIGURATION_EXCEPTION_MESSAGE = "Error in transformer configuration";
	private static final String TRANSFORM_EXCEPTION_MESSAGE = "Can't transform";
	private static final String IO_EXCEPTION_MESSAGE = "Can't perform output";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws CommandException {
		ProductsReadWriteLock readWriteLock = ProductsReadWriteLock.getInstance();
		ReadLock readLock = readWriteLock.readLock();
		WriteLock writeLock = readWriteLock.writeLock();
		HttpSession session = request.getSession();
		
		// Prevent an attempt to add duplicate products after reloading page
		
		try {			
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

			long lastModified;
			File sourceFile;
			Source source;
			readLock.lock();
			try {
				String sourcePath = SourceCreator.getRealPath(request, SOURCE_PATH);
				sourceFile = new File(sourcePath);
				source = new StreamSource(sourceFile);
				transformer.transform(source, result);
				lastModified = sourceFile.lastModified();
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
					if (lastModified != sourceFile.lastModified()) {
						source = new StreamSource(sourceFile);
						byteArrayOutputStream.reset();
						transformer.transform(source, result);
							
					}
					
					String xmlPath = SourceCreator.getRealPath(request, SOURCE_PATH);
					OutputStream fileOutputStream = new FileOutputStream(xmlPath);
					byte [] outputStreamContent = byteArrayOutputStream.toByteArray();
					fileOutputStream.write(outputStreamContent);
					fileOutputStream.close();
					
					// Show list of products
					response.sendRedirect(SHOW_PRODUCTS_REDIRECT);
				} finally {
					writeLock.unlock();
				}
			} else {
				OutputStream responseOutputStream = response.getOutputStream();
				byteArrayOutputStream.writeTo(responseOutputStream);
			}
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
			throw new CommandException(TRANSFORMER_CONFIGURATION_EXCEPTION_MESSAGE, e);
		} catch (TransformerException e) {
			e.printStackTrace();
			throw new CommandException(TRANSFORM_EXCEPTION_MESSAGE, e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CommandException(IO_EXCEPTION_MESSAGE, e);
		}
	}
}
