
package com.fitnessplayground.model.mindbody.api.appointment;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRep complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRep">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Rep" type="{http://clients.mindbodyonline.com/api/0_5_1}Rep" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRep", propOrder = {
    "rep"
})
public class ArrayOfRep {

    @XmlElement(name = "Rep", nillable = true)
    protected List<Rep> rep;

    /**
     * Gets the value of the rep property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rep property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRep().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Rep }
     * 
     * 
     */
    public List<Rep> getRep() {
        if (rep == null) {
            rep = new ArrayList<Rep>();
        }
        return this.rep;
    }

}
