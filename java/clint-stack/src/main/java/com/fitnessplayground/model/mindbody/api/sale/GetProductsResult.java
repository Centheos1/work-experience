
package com.fitnessplayground.model.mindbody.api.sale;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetProductsResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetProductsResult">
 *   &lt;complexContent>
 *     &lt;extension base="{http://clients.mindbodyonline.com/api/0_5_1}MBResult">
 *       &lt;sequence>
 *         &lt;element name="Products" type="{http://clients.mindbodyonline.com/api/0_5_1}ArrayOfProduct" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetProductsResult", propOrder = {
    "products"
})
public class GetProductsResult
    extends MBResult
{

    @XmlElement(name = "Products")
    protected ArrayOfProduct products;

    /**
     * Gets the value of the products property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProduct }
     *     
     */
    public ArrayOfProduct getProducts() {
        return products;
    }

    /**
     * Sets the value of the products property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProduct }
     *     
     */
    public void setProducts(ArrayOfProduct value) {
        this.products = value;
    }

}
