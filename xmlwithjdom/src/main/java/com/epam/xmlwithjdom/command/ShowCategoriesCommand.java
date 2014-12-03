package com.epam.xmlwithjdom.command;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;

import com.epam.xmlwithjdom.exception.CommandException;
import com.epam.xmlwithjdom.util.ProductsReadWriteLock;
import com.epam.xmlwithjdom.util.ResultCreator;
import com.epam.xmlwithjdom.util.SourceCreator;

/**
 * Command for showing categories from XML file
 * 
 */

public final class ShowCategoriesCommand implements ICommand {
	private static final String SOURCE_PATH = "/WEB-INF/classes/products.xml";
	private static final String XSLT_SOURCE_PATH = "/xslt/category_list.xsl";
	
	// Exception messages
	
	private static final String TRANSFORMER_CONFIGURATION_EXCEPTION_MESSAGE = "Erorr in configurating transformer";
	private static final String TRANSFORMER_EXCEPTION_MESSAGE = "Can't perform transformation";
	private static final String IO_EXCEPTION_MESSAGE = "Can't perform output";
	
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
			
			// Execute transformation
			ProductsReadWriteLock readWriteLock = ProductsReadWriteLock.getInstance();
			ReadLock readLock = readWriteLock.readLock();
			readLock.lock();
			try {
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
