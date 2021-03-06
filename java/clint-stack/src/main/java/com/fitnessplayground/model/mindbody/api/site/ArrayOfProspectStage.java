
package com.fitnessplayground.model.mindbody.api.site;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfProspectStage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfProspectStage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ProspectStage" type="{http://clients.mindbodyonline.com/api/0_5}ProspectStage" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfProspectStage", propOrder = {
    "prospectStage"
})
public class ArrayOfProspectStage {

    @XmlElement(name = "ProspectStage", nillable = true)
    protected List<ProspectStage> prospectStage;

    /**
     * Gets the value of the prospectStage property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the prospectStage property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProspectStage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProspectStage }
     * 
     * 
     */
    public List<ProspectStage> getProspectStage() {
        if (prospectStage == null) {
            prospectStage = new ArrayList<ProspectStage>();
        }
        return this.prospectStage;
    }

}
