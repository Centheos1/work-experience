
package com.fitnessplayground.model.mindbody.api.appointment;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfStaff complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfStaff">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Staff" type="{http://clients.mindbodyonline.com/api/0_5_1}Staff" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfStaff", propOrder = {
    "staff"
})
public class ArrayOfStaff {

    @XmlElement(name = "Staff", nillable = true)
    protected List<Staff> staff;

    /**
     * Gets the value of the staff property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the staff property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStaff().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Staff }
     * 
     * 
     */
    public List<Staff> getStaff() {
        if (staff == null) {
            staff = new ArrayList<Staff>();
        }
        return this.staff;
    }

}
