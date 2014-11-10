package com.epam.xmltransforming.command;

import java.io.File;
import java.io.FileOutputStream;
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

import com.epam.xmltransforming.entity.Product;
import com.epam.xmltransforming.exception.CommandException;

public class SaveProductCommand implements ICommand {

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
			
			
			// TODO
			//StreamResult result = new StreamResult(responseOutputStream);
			File outputXML = new File(xmlPath + "ch");
			OutputStream outputStream = new FileOutputStream(outputXML);
			StreamResult result = new StreamResult(outputStream);
			
			
			// Create transformer
			String xsltPath = context.getRealPath("/xslt/save_product.xslt");
			File xsltFile = new File(xsltPath);
			Source xslt = new StreamSource(xsltFile);
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer(xslt);
			// Execute transformation
			
			Product product = new Product();
			product.setColor(request.getParameter("color"));
			product.setDateOfIssue(request.getParameter("date-of-issue"));
			product.setModel(request.getParameter("model"));
			product.setName(request.getParameter("name"));
			product.setPrice(Integer.valueOf(request.getParameter("price")));
			product.setProvider(request.getParameter("provider"));
			
			transformer.setParameter("product", product);
			String categoryName = (String)session.getAttribute("prev_category");
			String subcategoryName = (String)session.getAttribute("prev_subcategory");
			transformer.setParameter("categoryname", categoryName);
			transformer.setParameter("subcategoryname", subcategoryName);
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
