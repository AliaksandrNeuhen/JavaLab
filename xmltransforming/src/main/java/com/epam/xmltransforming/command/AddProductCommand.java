package com.epam.xmltransforming.command;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.epam.xmltransforming.exception.CommandException;
import com.epam.xmltransforming.logic.ResultCreator;
import com.epam.xmltransforming.logic.SourceCreator;
import com.epam.xmltransforming.util.ProductFieldsCheck;

public class AddProductCommand implements ICommand {	
	private static final String SOURCE_PATH = "/WEB-INF/classes/products.xml";
	private static final String ADD_PRODUCT_SOURCE_PATH = "/xslt/add_product.xslt";
	private static final String PRODUCTS_LIST_SOURCE_PATH = "/xslt/product_list.xslt";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws CommandException {
		try {			
			// Set input	
			Source source = SourceCreator.createFileSource(request, SOURCE_PATH);
			// Create transformer
			Source xsltSource = SourceCreator.createFileSource(request, ADD_PRODUCT_SOURCE_PATH);
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer(xsltSource);

			// Set output
			StreamResult result = ResultCreator.createByteArrayOutputStreamResult();
			
			
			HttpSession session = request.getSession();
			String subcategory = (String)session.getAttribute("prev_subcategory");
			String category = (String)session.getAttribute("prev_category");
			if (request.getParameter("name") != null) {
				transformer.setParameter("name", request.getParameter("name"));
				transformer.setParameter("provider", request.getParameter("provider"));
				transformer.setParameter("model", request.getParameter("model"));
				transformer.setParameter("dateOfIssue", request.getParameter("dateOfIssue"));
				transformer.setParameter("color", request.getParameter("color"));
				transformer.setParameter("price", request.getParameter("price"));
			}
			if (request.getParameter("notInStock") != null) {
				String notInStockState = request.getParameter("notInStock");
				Boolean notInStockBoolean = false;
				if ("checked".equals(notInStockState)){
					notInStockBoolean = true;
				}
				transformer.setParameter("notInStock",notInStockBoolean);
			}
			
			transformer.setParameter("subcategoryname", subcategory);
			transformer.setParameter("categoryname", category);
			
			Boolean tryingAgain = false;
			if (request.getParameter("tryingAgain") != null){
				tryingAgain = Boolean.valueOf(request.getParameter("tryingAgain"));			
			}
			
			ProductFieldsCheck validator = new ProductFieldsCheck();
			transformer.setParameter("validatorObject", validator);
			transformer.setParameter("tryingAgain", tryingAgain);
			transformer.transform(source, result);
		
			Boolean isValid = validator.getResultOfValidation();
			System.out.println("Is trying again: " + tryingAgain);
			System.out.println("Is valid: " + isValid);
			
			ByteArrayOutputStream byteArrayOutputStream = 
					(ByteArrayOutputStream)result.getOutputStream();
			if (isValid) {
				//Copy new product to xml
//				source = new StreamSource(sourceFile);
				System.out.println("Copying to xml");
				byteArrayOutputStream.reset();
				transformer.transform(source, result);
				
				String xmlPath = SourceCreator.getRealPath(request, SOURCE_PATH);
				OutputStream fileOutputStream = new FileOutputStream(xmlPath);
				byte [] outputStreamContent = byteArrayOutputStream.toByteArray();
				fileOutputStream.write(outputStreamContent);
				fileOutputStream.close();
				
				// Show list of products
				Source xsltProductListSource = SourceCreator
						.createFileSource(request, PRODUCTS_LIST_SOURCE_PATH);
				
				transformer = tFactory.newTransformer(xsltProductListSource);
				transformer.setParameter("subcategoryname", subcategory);
				transformer.setParameter("categoryname", category);
				StreamResult resultList = ResultCreator.createResponseOutputStreamResult(response);
				transformer.transform(source, resultList);
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
