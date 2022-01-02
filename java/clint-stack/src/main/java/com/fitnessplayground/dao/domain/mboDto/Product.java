package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    @JsonProperty("Price")
    private Double price;
    @JsonProperty("TaxIncluded")
    private Double taxIncluded;
    @JsonProperty("TaxRate")
    private Double taxRate;
    @JsonProperty("Id")
    private String mboId;
    @JsonProperty("GroupId")
    private Integer groupId;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("OnlinePrice")
    private Double onlinePrice;
    @JsonProperty("ShortDescription")
    private String shortDescription;
    @JsonProperty("LongDescription")
    private String longDescription;
    @JsonProperty("Color")
    private Color color;
    @JsonProperty("Size")
    private Size size;

    public Product() {
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTaxIncluded() {
        return taxIncluded;
    }

    public void setTaxIncluded(Double taxIncluded) {
        this.taxIncluded = taxIncluded;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public String getMboId() {
        return mboId;
    }

    public void setMboId(String mboId) {
        this.mboId = mboId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getOnlinePrice() {
        return onlinePrice;
    }

    public void setOnlinePrice(Double onlinePrice) {
        this.onlinePrice = onlinePrice;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Product{" +
                "price=" + price +
                ", taxIncluded=" + taxIncluded +
                ", taxRate=" + taxRate +
                ", mboId='" + mboId + '\'' +
                ", groupId=" + groupId +
                ", name='" + name + '\'' +
                ", onlinePrice=" + onlinePrice +
                ", shortDescription='" + shortDescription + '\'' +
                ", longDescription='" + longDescription + '\'' +
                ", color=" + color +
                ", size=" + size +
                '}';
    }
}
