
package com.fitnessplayground.model.mindbody.api.class_v5_0_1;

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
 *         &lt;element name="GetEnrollmentsResult" type="{http://clients.mindbodyonline.com/api/0_5_1}GetEnrollmentsResult" minOccurs="0"/>
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
    "getEnrollmentsResult"
})
@XmlRootElement(name = "GetEnrollmentsResponse")
public class GetEnrollmentsResponse {

    @XmlElement(name = "GetEnrollmentsResult")
    protected GetEnrollmentsResult getEnrollmentsResult;

    /**
     * Gets the value of the getEnrollmentsResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetEnrollmentsResult }
     *     
     */
    public GetEnrollmentsResult getGetEnrollmentsResult() {
        return getEnrollmentsResult;
    }

    /**
     * Sets the value of the getEnrollmentsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetEnrollmentsResult }
     *     
     */
    public void setGetEnrollmentsResult(GetEnrollmentsResult value) {
        this.getEnrollmentsResult = value;
    }

}
