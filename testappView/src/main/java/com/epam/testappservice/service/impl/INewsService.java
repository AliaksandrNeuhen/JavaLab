
package com.epam.testappservice.service.impl;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import com.epam.testappservice.service.News;
import com.epam.testappservice.service.ObjectFactory;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "INewsService", targetNamespace = "http://service.testappservice.epam.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface INewsService {


    /**
     * 
     * @param arg0
     * @throws ServiceException
     */
    @WebMethod
    @RequestWrapper(localName = "remove", targetNamespace = "http://service.testappservice.epam.com/", className = "com.epam.testappservice.service.Remove")
    @ResponseWrapper(localName = "removeResponse", targetNamespace = "http://service.testappservice.epam.com/", className = "com.epam.testappservice.service.RemoveResponse")
    @Action(input = "http://service.testappservice.epam.com/INewsService/removeRequest", output = "http://service.testappservice.epam.com/INewsService/removeResponse", fault = {
        @FaultAction(className = ServiceException.class, value = "http://service.testappservice.epam.com/INewsService/remove/Fault/ServiceException")
    })
    public void remove(
        @WebParam(name = "arg0", targetNamespace = "")
        List<Integer> arg0)
        throws ServiceException
    ;

    /**
     * 
     * @param arg0
     * @return
     *     returns int
     * @throws ServiceException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "save", targetNamespace = "http://service.testappservice.epam.com/", className = "com.epam.testappservice.service.Save")
    @ResponseWrapper(localName = "saveResponse", targetNamespace = "http://service.testappservice.epam.com/", className = "com.epam.testappservice.service.SaveResponse")
    @Action(input = "http://service.testappservice.epam.com/INewsService/saveRequest", output = "http://service.testappservice.epam.com/INewsService/saveResponse", fault = {
        @FaultAction(className = ServiceException.class, value = "http://service.testappservice.epam.com/INewsService/save/Fault/ServiceException")
    })
    public int save(
        @WebParam(name = "arg0", targetNamespace = "")
        News arg0)
        throws ServiceException
    ;

    /**
     * 
     * @param arg0
     * @return
     *     returns com.epam.testappservice.service.News
     * @throws ServiceException
     * @throws FetchByIdException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "fetchByID", targetNamespace = "http://service.testappservice.epam.com/", className = "com.epam.testappservice.service.FetchByID")
    @ResponseWrapper(localName = "fetchByIDResponse", targetNamespace = "http://service.testappservice.epam.com/", className = "com.epam.testappservice.service.FetchByIDResponse")
    @Action(input = "http://service.testappservice.epam.com/INewsService/fetchByIDRequest", output = "http://service.testappservice.epam.com/INewsService/fetchByIDResponse", fault = {
        @FaultAction(className = ServiceException.class, value = "http://service.testappservice.epam.com/INewsService/fetchByID/Fault/ServiceException"),
        @FaultAction(className = FetchByIdException.class, value = "http://service.testappservice.epam.com/INewsService/fetchByID/Fault/FetchByIdException")
    })
    public News fetchByID(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0)
        throws FetchByIdException, ServiceException
    ;

    /**
     * 
     * @return
     *     returns java.util.List<com.epam.testappservice.service.News>
     * @throws ServiceException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getList", targetNamespace = "http://service.testappservice.epam.com/", className = "com.epam.testappservice.service.GetList")
    @ResponseWrapper(localName = "getListResponse", targetNamespace = "http://service.testappservice.epam.com/", className = "com.epam.testappservice.service.GetListResponse")
    @Action(input = "http://service.testappservice.epam.com/INewsService/getListRequest", output = "http://service.testappservice.epam.com/INewsService/getListResponse", fault = {
        @FaultAction(className = ServiceException.class, value = "http://service.testappservice.epam.com/INewsService/getList/Fault/ServiceException")
    })
    public List<News> getList()
        throws ServiceException
    ;

}
