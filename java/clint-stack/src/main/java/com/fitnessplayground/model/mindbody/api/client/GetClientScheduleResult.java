
package com.fitnessplayground.model.mindbody.api.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetClientScheduleResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetClientScheduleResult">
 *   &lt;complexContent>
 *     &lt;extension base="{http://clients.mindbodyonline.com/api/0_5_1}PagedBySitesMBResult">
 *       &lt;sequence>
 *         &lt;element name="Visits" type="{http://clients.mindbodyonline.com/api/0_5_1}ArrayOfVisit" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetClientScheduleResult", propOrder = {
    "visits"
})
public class GetClientScheduleResult
    extends PagedBySitesMBResult
{

    @XmlElement(name = "Visits")
    protected ArrayOfVisit visits;

    /**
     * Gets the value of the visits property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfVisit }
     *     
     */
    public ArrayOfVisit getVisits() {
        return visits;
    }

    /**
     * Sets the value of the visits property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfVisit }
     *     
     */
    public void setVisits(ArrayOfVisit value) {
        this.visits = value;
    }

}
