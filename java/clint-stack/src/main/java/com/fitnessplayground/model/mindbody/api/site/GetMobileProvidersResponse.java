
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
 *         &lt;element name="GetMobileProvidersResult" type="{http://clients.mindbodyonline.com/api/0_5}GetMobileProvidersResult" minOccurs="0"/>
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
    "getMobileProvidersResult"
})
@XmlRootElement(name = "GetMobileProvidersResponse")
public class GetMobileProvidersResponse {

    @XmlElement(name = "GetMobileProvidersResult")
    protected GetMobileProvidersResult getMobileProvidersResult;

    /**
     * Gets the value of the getMobileProvidersResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetMobileProvidersResult }
     *     
     */
    public GetMobileProvidersResult getGetMobileProvidersResult() {
        return getMobileProvidersResult;
    }

    /**
     * Sets the value of the getMobileProvidersResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetMobileProvidersResult }
     *     
     */
    public void setGetMobileProvidersResult(GetMobileProvidersResult value) {
        this.getMobileProvidersResult = value;
    }

}
