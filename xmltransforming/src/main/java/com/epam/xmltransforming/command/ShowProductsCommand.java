package com.epam.xmltransforming.command;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;

import com.epam.xmltransforming.exception.CommandException;
import com.epam.xmltransforming.util.ProductsReadWriteLock;
import com.epam.xmltransforming.util.ResultCreator;
import com.epam.xmltransforming.util.SourceCreator;

/**
 * Command for showing products from XML file
 * 
 */

public final class ShowProductsCommand implements ICommand {
	private static final String SOURCE_PATH = "/WEB-INF/classes/products.xml";
	private static final String XSLT_SOURCE_PATH = "/xslt/product_list.xsl";
	private static final String SUBCATEGORY_NAME_REQUEST_PARAM = "subcategoryname";
	private static final String PREV_SUBCATEGORY_SESSION_ATTR = "prev_subcategory";
	
	// Exception messages
	
	private static final String TRANSFORMER_CONFIGURATION_EXCEPTION_MESSAGE = "Erorr in configurating transformer";
	private static final String TRANSFORMER_EXCEPTION_MESSAGE = "Can't perform transformation";
	private static final String IO_EXCEPTION_MESSAGE = "Can't perform output";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws CommandException {
	try {	
		// Set input
		Source source = SourceCreator.createFileSource(request, SOURCE_PATH);
		
		// Set output
		Result result = ResultCreator.createResponseOutputStreamResult(response);
		
		// Create transformer
		Source xsltSource = SourceCreator.createFileSource(request, XSLT_SOURCE_PATH);
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer(xsltSource);
		
		// Get request parameter with name of category
		String subcategoryName = request.getParameter(SUBCATEGORY_NAME_REQUEST_PARAM);
		
		// If subcategory name parameter is empty,
		// then get this parameter from session,
		// else save this parameter to session
		HttpSession session = request.getSession();
		if (subcategoryName == null) {
			subcategoryName = (String)session.getAttribute(PREV_SUBCATEGORY_SESSION_ATTR); 
		} else {
			session.setAttribute(PREV_SUBCATEGORY_SESSION_ATTR, subcategoryName);
		}
		transformer.setParameter(SUBCATEGORY_NAME_REQUEST_PARAM, subcategoryName);
		ProductsReadWriteLock readWriteLock = ProductsReadWriteLock.getInstance();
		ReadLock readLock = readWriteLock.readLock();
		readLock.lock();
		try {
			// Execute transformation
			transformer.transform(source, result);
		} finally {
			readLock.unlock();
		}

	} catch (TransformerConfigurationException e) {
		throw new CommandException(TRANSFORMER_CONFIGURATION_EXCEPTION_MESSAGE, e);
	} catch (TransformerException e) {
		throw new CommandException(TRANSFORMER_EXCEPTION_MESSAGE, e);
	} catch (IOException e) {
		throw new CommandException(IO_EXCEPTION_MESSAGE, e);
	}

	}

}
