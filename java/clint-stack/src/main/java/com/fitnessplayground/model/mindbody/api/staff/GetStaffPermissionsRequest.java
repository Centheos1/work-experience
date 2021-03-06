
package com.fitnessplayground.model.mindbody.api.staff;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetStaffPermissionsRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetStaffPermissionsRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://clients.mindbodyonline.com/api/0_5_1}MBRequest">
 *       &lt;sequence>
 *         &lt;element name="StaffID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetStaffPermissionsRequest", propOrder = {
    "staffID"
})
public class GetStaffPermissionsRequest
    extends MBRequest
{

    @XmlElement(name = "StaffID")
    protected long staffID;

    /**
     * Gets the value of the staffID property.
     * 
     */
    public long getStaffID() {
        return staffID;
    }

    /**
     * Sets the value of the staffID property.
     * 
     */
    public void setStaffID(long value) {
        this.staffID = value;
    }

}
