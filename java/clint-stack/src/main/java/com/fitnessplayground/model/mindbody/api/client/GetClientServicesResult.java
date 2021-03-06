
package com.fitnessplayground.model.mindbody.api.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetClientServicesResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetClientServicesResult">
 *   &lt;complexContent>
 *     &lt;extension base="{http://clients.mindbodyonline.com/api/0_5_1}PagedBySitesMBResult">
 *       &lt;sequence>
 *         &lt;element name="ClientServices" type="{http://clients.mindbodyonline.com/api/0_5_1}ArrayOfClientService" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetClientServicesResult", propOrder = {
    "clientServices"
})
public class GetClientServicesResult
    extends PagedBySitesMBResult
{

    @XmlElement(name = "ClientServices")
    protected ArrayOfClientService clientServices;

    /**
     * Gets the value of the clientServices property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfClientService }
     *     
     */
    public ArrayOfClientService getClientServices() {
        return clientServices;
    }

    /**
     * Sets the value of the clientServices property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfClientService }
     *     
     */
    public void setClientServices(ArrayOfClientService value) {
        this.clientServices = value;
    }

}
