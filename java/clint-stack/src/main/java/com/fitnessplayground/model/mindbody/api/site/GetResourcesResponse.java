
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
 *         &lt;element name="GetResourcesResult" type="{http://clients.mindbodyonline.com/api/0_5}GetResourcesResult" minOccurs="0"/>
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
    "getResourcesResult"
})
@XmlRootElement(name = "GetResourcesResponse")
public class GetResourcesResponse {

    @XmlElement(name = "GetResourcesResult")
    protected GetResourcesResult getResourcesResult;

    /**
     * Gets the value of the getResourcesResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetResourcesResult }
     *     
     */
    public GetResourcesResult getGetResourcesResult() {
        return getResourcesResult;
    }

    /**
     * Sets the value of the getResourcesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetResourcesResult }
     *     
     */
    public void setGetResourcesResult(GetResourcesResult value) {
        this.getResourcesResult = value;
    }

}
