
package com.fitnessplayground.model.mindbody.api.site;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GetSessionTypesResult" type="{http://clients.mindbodyonline.com/api/0_5}GetSessionTypesResult" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getSessionTypesResult"
})
@XmlRootElement(name = "GetSessionTypesResponse")
public class GetSessionTypesResponse {

    @XmlElement(name = "GetSessionTypesResult")
    protected GetSessionTypesResult getSessionTypesResult;

    /**
     * Gets the value of the getSessionTypesResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetSessionTypesResult }
     *     
     */
    public GetSessionTypesResult getGetSessionTypesResult() {
        return getSessionTypesResult;
    }

    /**
     * Sets the value of the getSessionTypesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetSessionTypesResult }
     *     
     */
    public void setGetSessionTypesResult(GetSessionTypesResult value) {
        this.getSessionTypesResult = value;
    }

}
