
package com.fitnessplayground.model.mindbody.api.site;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetProspectStagesRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetProspectStagesRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://clients.mindbodyonline.com/api/0_5}MBRequest">
 *       &lt;sequence>
 *         &lt;element name="IncludeInactive" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetProspectStagesRequest", propOrder = {
    "includeInactive"
})
public class GetProspectStagesRequest
    extends MBRequest
{

    @XmlElement(name = "IncludeInactive", required = true, type = Boolean.class, nillable = true)
    protected Boolean includeInactive;

    /**
     * Gets the value of the includeInactive property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIncludeInactive() {
        return includeInactive;
    }

    /**
     * Sets the value of the includeInactive property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncludeInactive(Boolean value) {
        this.includeInactive = value;
    }

}
