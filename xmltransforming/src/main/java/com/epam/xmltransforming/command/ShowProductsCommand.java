package com.epam.xmltransforming.command;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

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

import com.epam.xmltransforming.exception.CommandException;

public class ShowProductsCommand implements ICommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws CommandException {
	try {	
		// Set input
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		
		String xmlPath = context.getRealPath("/WEB-INF/classes/products.xml");
		File sourceFile = new File(xmlPath);
		Source source = new StreamSource(sourceFile);
		
		// Set output
		OutputStream responseOutputStream = response.getOutputStream();
		StreamResult result = new StreamResult(responseOutputStream);
		// Create transformer
		String xsltPath = context.getRealPath("/xslt/product_list.xslt");
		File xsltFile = new File(xsltPath);
		Source xslt = new StreamSource(xsltFile);
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer(xslt);

		// Get request parameter with name of category
		String subcategoryName = request.getParameter("subcategoryname");
		
		// If subcategory name parameter is empty,
		// then get this parameter from session,
		// else save this parameter to session
		if (subcategoryName == null) {
			subcategoryName = (String)session.getAttribute("prev_subcategory"); 
		} else {
			session.setAttribute("prev_subcategory", subcategoryName);
		}
		
		
		transformer.setParameter("subcategoryname", subcategoryName);

		// Execute transformation
		transformer.transform(source, result);
	} catch (TransformerConfigurationException e) {
		e.printStackTrace();
	} catch (TransformerException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}

	}

}
