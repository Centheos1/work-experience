
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
 *         &lt;element name="GetSalesResult" type="{http://clients.mindbodyonline.com/api/0_5_1}GetSalesResult" minOccurs="0"/>
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
    "getSalesResult"
})
@XmlRootElement(name = "GetSalesResponse")
public class GetSalesResponse {

    @XmlElement(name = "GetSalesResult")
    protected GetSalesResult getSalesResult;

    /**
     * Gets the value of the getSalesResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetSalesResult }
     *     
     */
    public GetSalesResult getGetSalesResult() {
        return getSalesResult;
    }

    /**
     * Sets the value of the getSalesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetSalesResult }
     *     
     */
    public void setGetSalesResult(GetSalesResult value) {
        this.getSalesResult = value;
    }

}
