
package com.epam.testappservice.service;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for news complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="news">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="brief" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="content" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="editionDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="markedForDeletion" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "news", propOrder = {
    "brief",
    "content",
    "editionDate",
    "id",
    "markedForDeletion",
    "title"
})
public class News {

    protected String brief;
    protected String content;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar editionDate;
    protected int id;
    protected Boolean markedForDeletion;
    protected String title;

    /**
     * Gets the value of the brief property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrief() {
        return brief;
    }

    /**
     * Sets the value of the brief property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrief(String value) {
        this.brief = value;
    }

    /**
     * Gets the value of the content property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContent(String value) {
        this.content = value;
    }

    /**
     * Gets the value of the editionDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public Date getEditionDate() {
    	if (editionDate != null) {
    		return editionDate.toGregorianCalendar().getTime();
    	} else {
    		return null;
    	}
    }

    /**
     * Sets the value of the editionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEditionDate(XMLGregorianCalendar value) {
        this.editionDate = value;
    }
    
    public void setEditionDate(Date editionDate) {
    	if (editionDate == null) {
    		return;
    	}
    	GregorianCalendar gregorianCalendar = new GregorianCalendar();
    	gregorianCalendar.setTime(editionDate);
    	XMLGregorianCalendar xmlCalendar = null;
    	try {
    		xmlCalendar = DatatypeFactory.newInstance()
    				.newXMLGregorianCalendar(gregorianCalendar);
    	} catch (DatatypeConfigurationException e) {
    		e.printStackTrace();
    	}
    	this.editionDate = xmlCalendar;
    }

    /**
     * Gets the value of the id property.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Gets the value of the markedForDeletion property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getMarkedForDeletion() {
        return markedForDeletion;
    }

    /**
     * Sets the value of the markedForDeletion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMarkedForDeletion(Boolean value) {
        this.markedForDeletion = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

}
