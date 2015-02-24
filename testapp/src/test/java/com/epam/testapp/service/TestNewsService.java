package com.epam.testapp.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;

import org.junit.Test;

import static org.junit.Assert.*;

import com.epam.testapp.exception.ServiceException;
import com.epam.testapp.model.News;
import com.epam.testapp.service.impl.NewsService;

public class TestNewsService {

	@Test
	public void serviceTest() throws MalformedURLException{
		Endpoint endpoint = Endpoint.publish("http://localhost:1710/newsService",
				new NewsService());
		assertTrue(endpoint.isPublished());
		assertEquals("http://schemas.xmlsoap.org/wsdl/soap/http",
				endpoint.getBinding().getBindingID());

		String namespaceURI = "http://impl.service.testapp.epam.com/";
		String servicePart = "newsService";
		String portName = "NewsServicePort";
		QName serviceQN = new QName(namespaceURI, servicePart);
		QName portQN = new QName(namespaceURI, portName);
		URL wsdlDocumentLocation = new URL("http://localhost:1710/newsService?wsdl");
		Service service = Service.create(wsdlDocumentLocation, serviceQN);

		while(true) {}

//		INewsService newsService = service.getPort(portQN, INewsService.class);
//		List<News> newsList = new ArrayList<News>();
//		try {
//			newsList = newsService.getList();
//		} catch (ServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
