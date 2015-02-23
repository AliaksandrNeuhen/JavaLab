
package com.epam.testappservice.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.epam.testappservice.service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _FetchByIDResponse_QNAME = new QName("http://service.testappservice.epam.com/", "fetchByIDResponse");
    private final static QName _RemoveResponse_QNAME = new QName("http://service.testappservice.epam.com/", "removeResponse");
    private final static QName _SaveResponse_QNAME = new QName("http://service.testappservice.epam.com/", "saveResponse");
    private final static QName _Remove_QNAME = new QName("http://service.testappservice.epam.com/", "remove");
    private final static QName _Save_QNAME = new QName("http://service.testappservice.epam.com/", "save");
    private final static QName _FetchByIdException_QNAME = new QName("http://service.testappservice.epam.com/", "FetchByIdException");
    private final static QName _FetchByID_QNAME = new QName("http://service.testappservice.epam.com/", "fetchByID");
    private final static QName _GetList_QNAME = new QName("http://service.testappservice.epam.com/", "getList");
    private final static QName _GetListResponse_QNAME = new QName("http://service.testappservice.epam.com/", "getListResponse");
    private final static QName _ServiceException_QNAME = new QName("http://service.testappservice.epam.com/", "ServiceException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.epam.testappservice.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FetchByIDResponse }
     * 
     */
    public FetchByIDResponse createFetchByIDResponse() {
        return new FetchByIDResponse();
    }

    /**
     * Create an instance of {@link Save }
     * 
     */
    public Save createSave() {
        return new Save();
    }

    /**
     * Create an instance of {@link Remove }
     * 
     */
    public Remove createRemove() {
        return new Remove();
    }

    /**
     * Create an instance of {@link SaveResponse }
     * 
     */
    public SaveResponse createSaveResponse() {
        return new SaveResponse();
    }

    /**
     * Create an instance of {@link RemoveResponse }
     * 
     */
    public RemoveResponse createRemoveResponse() {
        return new RemoveResponse();
    }

    /**
     * Create an instance of {@link GetListResponse }
     * 
     */
    public GetListResponse createGetListResponse() {
        return new GetListResponse();
    }

    /**
     * Create an instance of {@link GetList }
     * 
     */
    public GetList createGetList() {
        return new GetList();
    }

    /**
     * Create an instance of {@link FetchByID }
     * 
     */
    public FetchByID createFetchByID() {
        return new FetchByID();
    }

    /**
     * Create an instance of {@link FetchByIdException }
     * 
     */
    public FetchByIdException createFetchByIdException() {
        return new FetchByIdException();
    }

    /**
     * Create an instance of {@link ServiceException }
     * 
     */
    public ServiceException createServiceException() {
        return new ServiceException();
    }

    /**
     * Create an instance of {@link News }
     * 
     */
    public News createNews() {
        return new News();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FetchByIDResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.testappservice.epam.com/", name = "fetchByIDResponse")
    public JAXBElement<FetchByIDResponse> createFetchByIDResponse(FetchByIDResponse value) {
        return new JAXBElement<FetchByIDResponse>(_FetchByIDResponse_QNAME, FetchByIDResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.testappservice.epam.com/", name = "removeResponse")
    public JAXBElement<RemoveResponse> createRemoveResponse(RemoveResponse value) {
        return new JAXBElement<RemoveResponse>(_RemoveResponse_QNAME, RemoveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.testappservice.epam.com/", name = "saveResponse")
    public JAXBElement<SaveResponse> createSaveResponse(SaveResponse value) {
        return new JAXBElement<SaveResponse>(_SaveResponse_QNAME, SaveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Remove }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.testappservice.epam.com/", name = "remove")
    public JAXBElement<Remove> createRemove(Remove value) {
        return new JAXBElement<Remove>(_Remove_QNAME, Remove.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Save }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.testappservice.epam.com/", name = "save")
    public JAXBElement<Save> createSave(Save value) {
        return new JAXBElement<Save>(_Save_QNAME, Save.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FetchByIdException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.testappservice.epam.com/", name = "FetchByIdException")
    public JAXBElement<FetchByIdException> createFetchByIdException(FetchByIdException value) {
        return new JAXBElement<FetchByIdException>(_FetchByIdException_QNAME, FetchByIdException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FetchByID }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.testappservice.epam.com/", name = "fetchByID")
    public JAXBElement<FetchByID> createFetchByID(FetchByID value) {
        return new JAXBElement<FetchByID>(_FetchByID_QNAME, FetchByID.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.testappservice.epam.com/", name = "getList")
    public JAXBElement<GetList> createGetList(GetList value) {
        return new JAXBElement<GetList>(_GetList_QNAME, GetList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.testappservice.epam.com/", name = "getListResponse")
    public JAXBElement<GetListResponse> createGetListResponse(GetListResponse value) {
        return new JAXBElement<GetListResponse>(_GetListResponse_QNAME, GetListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServiceException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.testappservice.epam.com/", name = "ServiceException")
    public JAXBElement<ServiceException> createServiceException(ServiceException value) {
        return new JAXBElement<ServiceException>(_ServiceException_QNAME, ServiceException.class, null, value);
    }

}
