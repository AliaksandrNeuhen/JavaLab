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

public class ShowSubcategoriesCommand implements ICommand {

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
			String xsltPath = context.getRealPath("/xslt/subcategory_list.xslt");
			File xsltFile = new File(xsltPath);
			Source xslt = new StreamSource(xsltFile);
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer(xslt);

			// Get request parameter with name of category
			String categoryName = request.getParameter("categoryname");
			
			// If category name parameter is empty,
			// then get this parameter from session,
			// else save this parameter to session
			System.out.println(categoryName);
			if (categoryName == null) {
				categoryName = (String)session.getAttribute("prev_category");
			} else {
				session.setAttribute("prev_category", categoryName);
			}
			transformer.setParameter("categoryname", categoryName);

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
