
package com.fitnessplayground.model.mindbody.api.appointment;

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
 *         &lt;element name="GetBookableItemsResult" type="{http://clients.mindbodyonline.com/api/0_5_1}GetBookableItemsResult" minOccurs="0"/>
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
    "getBookableItemsResult"
})
@XmlRootElement(name = "GetBookableItemsResponse")
public class GetBookableItemsResponse {

    @XmlElement(name = "GetBookableItemsResult")
    protected GetBookableItemsResult getBookableItemsResult;

    /**
     * Gets the value of the getBookableItemsResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetBookableItemsResult }
     *     
     */
    public GetBookableItemsResult getGetBookableItemsResult() {
        return getBookableItemsResult;
    }

    /**
     * Sets the value of the getBookableItemsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetBookableItemsResult }
     *     
     */
    public void setGetBookableItemsResult(GetBookableItemsResult value) {
        this.getBookableItemsResult = value;
    }

}
