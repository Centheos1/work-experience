
package com.fitnessplayground.model.mindbody.api.sale;

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
 *         &lt;element name="GetServicesResult" type="{http://clients.mindbodyonline.com/api/0_5_1}GetServicesResult" minOccurs="0"/>
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
    "getServicesResult"
})
@XmlRootElement(name = "GetServicesResponse")
public class GetServicesResponse {

    @XmlElement(name = "GetServicesResult")
    protected GetServicesResult getServicesResult;

    /**
     * Gets the value of the getServicesResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetServicesResult }
     *     
     */
    public GetServicesResult getGetServicesResult() {
        return getServicesResult;
    }

    /**
     * Sets the value of the getServicesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetServicesResult }
     *     
     */
    public void setGetServicesResult(GetServicesResult value) {
        this.getServicesResult = value;
    }

}
