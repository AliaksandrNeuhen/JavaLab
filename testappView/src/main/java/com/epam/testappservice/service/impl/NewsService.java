
package com.epam.testappservice.service.impl;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "newsService", targetNamespace = "http://impl.service.testappservice.epam.com/", wsdlLocation = "http://localhost:1710/newsService?wsdl")
public class NewsService
    extends Service
{

    private final static URL NEWSSERVICE_WSDL_LOCATION;
    private final static WebServiceException NEWSSERVICE_EXCEPTION;
    private final static QName NEWSSERVICE_QNAME = new QName("http://impl.service.testappservice.epam.com/", "newsService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:1710/newsService?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        NEWSSERVICE_WSDL_LOCATION = url;
        NEWSSERVICE_EXCEPTION = e;
    }

    public NewsService() {
        super(__getWsdlLocation(), NEWSSERVICE_QNAME);
    }

    public NewsService(WebServiceFeature... features) {
        super(__getWsdlLocation(), NEWSSERVICE_QNAME, features);
    }

    public NewsService(URL wsdlLocation) {
        super(wsdlLocation, NEWSSERVICE_QNAME);
    }

    public NewsService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, NEWSSERVICE_QNAME, features);
    }

    public NewsService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public NewsService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns INewsService
     */
    @WebEndpoint(name = "NewsServicePort")
    public INewsService getNewsServicePort() {
        return super.getPort(new QName("http://impl.service.testappservice.epam.com/", "NewsServicePort"), INewsService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns INewsService
     */
    @WebEndpoint(name = "NewsServicePort")
    public INewsService getNewsServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://impl.service.testappservice.epam.com/", "NewsServicePort"), INewsService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (NEWSSERVICE_EXCEPTION!= null) {
            throw NEWSSERVICE_EXCEPTION;
        }
        return NEWSSERVICE_WSDL_LOCATION;
    }

}
