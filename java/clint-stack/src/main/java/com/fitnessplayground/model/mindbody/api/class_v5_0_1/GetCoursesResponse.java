
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
 *         &lt;element name="GetCoursesResult" type="{http://clients.mindbodyonline.com/api/0_5_1}GetCoursesResult" minOccurs="0"/>
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
    "getCoursesResult"
})
@XmlRootElement(name = "GetCoursesResponse")
public class GetCoursesResponse {

    @XmlElement(name = "GetCoursesResult")
    protected GetCoursesResult getCoursesResult;

    /**
     * Gets the value of the getCoursesResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetCoursesResult }
     *     
     */
    public GetCoursesResult getGetCoursesResult() {
        return getCoursesResult;
    }

    /**
     * Sets the value of the getCoursesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetCoursesResult }
     *     
     */
    public void setGetCoursesResult(GetCoursesResult value) {
        this.getCoursesResult = value;
    }

}
