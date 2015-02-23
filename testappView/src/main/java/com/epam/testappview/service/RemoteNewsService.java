package com.epam.testappview.service;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;

import com.epam.testappservice.service.impl.INewsService;

@WebServiceClient(name="RemoteNewsService",
		targetNamespace="http://impl.service.testappservice.epam.com/",
		wsdlLocation="http://localhost:1710/newsService?wsdl")
public class RemoteNewsService extends Service{
	private static final URL NEWS_SERVICE_WSDL_LOCATION;
	private static volatile RemoteNewsService instance;
	
	static {
        URL url = null;
        try {
            url = new URL("http://10.6.103.55:1710/newsService?wsdl");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        NEWS_SERVICE_WSDL_LOCATION = url;
    }
	
	private RemoteNewsService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}
	
	
	private RemoteNewsService() throws MalformedURLException {
		super(NEWS_SERVICE_WSDL_LOCATION, 
				new QName("http://impl.service.testappservice.epam.com/", "newsService"));
	}

	public static INewsService getNewsService() {
		if (instance == null) {
			synchronized(RemoteNewsService.class) {
				if (instance == null) {
					try {
						instance = new RemoteNewsService();
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return instance.getNewsWSPort();
	}
	
	@WebEndpoint(name="NewsServicePort")
	private INewsService getNewsWSPort() {
		return super.getPort(new QName("http://impl.service.testappservice.epam.com/", "NewsServicePort"),
				INewsService.class);
	}
	
}
