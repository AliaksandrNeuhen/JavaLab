
package com.epam.testapp.service.impl;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6b21 
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "FetchByIdException", targetNamespace = "http://service.testapp.epam.com/")
public class FetchByIdException
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private com.epam.testapp.service.FetchByIdException faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public FetchByIdException(String message, com.epam.testapp.service.FetchByIdException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public FetchByIdException(String message, com.epam.testapp.service.FetchByIdException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: com.epam.testapp.service.FetchByIdException
     */
    public com.epam.testapp.service.FetchByIdException getFaultInfo() {
        return faultInfo;
    }

}
