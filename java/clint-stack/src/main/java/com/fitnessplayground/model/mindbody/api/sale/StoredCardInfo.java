
package com.fitnessplayground.model.mindbody.api.sale;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StoredCardInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StoredCardInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{http://clients.mindbodyonline.com/api/0_5_1}PaymentInfo">
 *       &lt;sequence>
 *         &lt;element name="Amount" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="LastFour" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StoredCardInfo", propOrder = {
    "amount",
    "lastFour"
})
public class StoredCardInfo
    extends PaymentInfo
{

    @XmlElement(name = "Amount", required = true)
    protected BigDecimal amount;
    @XmlElement(name = "LastFour")
    protected String lastFour;

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAmount(BigDecimal value) {
        this.amount = value;
    }

    /**
     * Gets the value of the lastFour property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastFour() {
        return lastFour;
    }

    /**
     * Sets the value of the lastFour property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastFour(String value) {
        this.lastFour = value;
    }

}
