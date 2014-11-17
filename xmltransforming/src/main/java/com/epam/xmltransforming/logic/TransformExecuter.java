package com.epam.xmltransforming.logic;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
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

public final class TransformExecuter {
	public void execute(HttpServletRequest request, HttpServletResponse response,
			String sourcePath, String transformPath) throws IOException, 
			TransformerConfigurationException, TransformerException {
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		
		String sourceXmlPath = context.getRealPath(sourcePath);
		File sourceFile = new File(sourceXmlPath);
		Source source = new StreamSource(sourceFile);
		
		OutputStream responseOutputStream = response.getOutputStream();
		StreamResult result = new StreamResult(responseOutputStream);
		
		String xsltPath = context.getRealPath(transformPath);
		File xsltFile = new File(xsltPath);
		Source xsltSource = new StreamSource(xsltFile);
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer(xsltSource);
		
		transformer.transform(source, result);
		
	}
	
}
