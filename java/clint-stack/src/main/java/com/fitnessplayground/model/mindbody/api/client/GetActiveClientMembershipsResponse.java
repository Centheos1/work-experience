
package com.fitnessplayground.model.mindbody.api.client;

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
 *         &lt;element name="GetActiveClientMembershipsResult" type="{http://clients.mindbodyonline.com/api/0_5_1}GetActiveClientMembershipsResult" minOccurs="0"/>
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
    "getActiveClientMembershipsResult"
})
@XmlRootElement(name = "GetActiveClientMembershipsResponse")
public class GetActiveClientMembershipsResponse {

    @XmlElement(name = "GetActiveClientMembershipsResult")
    protected GetActiveClientMembershipsResult getActiveClientMembershipsResult;

    /**
     * Gets the value of the getActiveClientMembershipsResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetActiveClientMembershipsResult }
     *     
     */
    public GetActiveClientMembershipsResult getGetActiveClientMembershipsResult() {
        return getActiveClientMembershipsResult;
    }

    /**
     * Sets the value of the getActiveClientMembershipsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetActiveClientMembershipsResult }
     *     
     */
    public void setGetActiveClientMembershipsResult(GetActiveClientMembershipsResult value) {
        this.getActiveClientMembershipsResult = value;
    }

}
