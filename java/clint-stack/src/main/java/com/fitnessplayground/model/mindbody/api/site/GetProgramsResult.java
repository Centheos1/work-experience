
package com.fitnessplayground.model.mindbody.api.site;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetProgramsResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetProgramsResult">
 *   &lt;complexContent>
 *     &lt;extension base="{http://clients.mindbodyonline.com/api/0_5}MBResult">
 *       &lt;sequence>
 *         &lt;element name="Programs" type="{http://clients.mindbodyonline.com/api/0_5}ArrayOfProgram" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetProgramsResult", propOrder = {
    "programs"
})
public class GetProgramsResult
    extends MBResult
{

    @XmlElement(name = "Programs")
    protected ArrayOfProgram programs;

    /**
     * Gets the value of the programs property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProgram }
     *     
     */
    public ArrayOfProgram getPrograms() {
        return programs;
    }

    /**
     * Sets the value of the programs property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProgram }
     *     
     */
    public void setPrograms(ArrayOfProgram value) {
        this.programs = value;
    }

}
