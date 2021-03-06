
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
 *         &lt;element name="GetGendersResult" type="{http://clients.mindbodyonline.com/api/0_5}GetGendersResult" minOccurs="0"/>
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
    "getGendersResult"
})
@XmlRootElement(name = "GetGendersResponse")
public class GetGendersResponse {

    @XmlElement(name = "GetGendersResult")
    protected GetGendersResult getGendersResult;

    /**
     * Gets the value of the getGendersResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetGendersResult }
     *     
     */
    public GetGendersResult getGetGendersResult() {
        return getGendersResult;
    }

    /**
     * Sets the value of the getGendersResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetGendersResult }
     *     
     */
    public void setGetGendersResult(GetGendersResult value) {
        this.getGendersResult = value;
    }

}
