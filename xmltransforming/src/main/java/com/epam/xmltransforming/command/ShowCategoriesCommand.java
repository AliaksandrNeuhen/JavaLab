package com.epam.xmltransforming.command;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;

import com.epam.xmltransforming.exception.CommandException;
import com.epam.xmltransforming.logic.ResultCreator;
import com.epam.xmltransforming.logic.SourceCreator;

public final class ShowCategoriesCommand implements ICommand {
	private static final String SOURCE_PATH = "/WEB-INF/classes/products.xml";
	private static final String XSLT_SOURCE_PATH = "/xslt/category_list.xslt";
	
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
			ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
			ReadLock readLock = readWriteLock.readLock();
			readLock.lock();
			try {
				transformer.transform(source, result);	
			} finally {
				readLock.unlock();
			}
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
