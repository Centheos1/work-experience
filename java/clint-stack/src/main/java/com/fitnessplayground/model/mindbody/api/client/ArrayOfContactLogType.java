
package com.fitnessplayground.model.mindbody.api.client;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfContactLogType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfContactLogType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ContactLogType" type="{http://clients.mindbodyonline.com/api/0_5_1}ContactLogType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfContactLogType", propOrder = {
    "contactLogType"
})
public class ArrayOfContactLogType {

    @XmlElement(name = "ContactLogType", nillable = true)
    protected List<ContactLogType> contactLogType;

    /**
     * Gets the value of the contactLogType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contactLogType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContactLogType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContactLogType }
     * 
     * 
     */
    public List<ContactLogType> getContactLogType() {
        if (contactLogType == null) {
            contactLogType = new ArrayList<ContactLogType>();
        }
        return this.contactLogType;
    }

}
