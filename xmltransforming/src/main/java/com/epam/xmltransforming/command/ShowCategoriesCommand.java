package com.epam.xmltransforming.command;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.epam.xmltransforming.exception.CommandException;

public class ShowCategoriesCommand implements ICommand {

	public String execute(HttpServletRequest request) throws CommandException {
		File stylesheet = new File("products.xml");
		File dataFile = new File("xsl/category_list.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(dataFile);
			
			// Use a Transformer for output
			TransformerFactory tFactory = TransformerFactory.newInstance();
			StreamSource stylesource = new StreamSource(stylesheet);
			Transformer transformer = tFactory.newTransformer(stylesource);
			
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(System.out);
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e){
			e.printStackTrace();
		}
		return "";
	}
}
