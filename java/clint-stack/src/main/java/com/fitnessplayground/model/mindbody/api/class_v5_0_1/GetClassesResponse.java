
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
 *         &lt;element name="GetClassesResult" type="{http://clients.mindbodyonline.com/api/0_5_1}GetClassesResult" minOccurs="0"/>
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
    "getClassesResult"
})
@XmlRootElement(name = "GetClassesResponse")
public class GetClassesResponse {

    @XmlElement(name = "GetClassesResult")
    protected GetClassesResult getClassesResult;

    /**
     * Gets the value of the getClassesResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetClassesResult }
     *     
     */
    public GetClassesResult getGetClassesResult() {
        return getClassesResult;
    }

    /**
     * Sets the value of the getClassesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetClassesResult }
     *     
     */
    public void setGetClassesResult(GetClassesResult value) {
        this.getClassesResult = value;
    }

}
