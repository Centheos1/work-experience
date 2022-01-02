package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Service {

    @JsonProperty("Price")
    private double price;
    @JsonProperty("OnlinePrice")
    private double onlinePrice;
    @JsonProperty("TaxIncluded")
    private double taxIncluded;
    @JsonProperty("ProgramId")
    private int programId;
    @JsonProperty("TaxRate")
    private double taxRate;
    @JsonProperty("ProductId")
    private int productId;
    @JsonProperty("Id")
    private String mboId;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Count")
    private int visitCount;

    public Service() {
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getOnlinePrice() {
        return onlinePrice;
    }

    public void setOnlinePrice(double onlinePrice) {
        this.onlinePrice = onlinePrice;
    }

    public double getTaxIncluded() {
        return taxIncluded;
    }

    public void setTaxIncluded(double taxIncluded) {
        this.taxIncluded = taxIncluded;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getMboId() {
        return mboId;
    }

    public void setMboId(String mboId) {
        this.mboId = mboId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    @Override
    public String toString() {
        return "Service{" +
                "price=" + price +
                ", onlinePrice=" + onlinePrice +
                ", taxIncluded=" + taxIncluded +
                ", programId=" + programId +
                ", taxRate=" + taxRate +
                ", productId=" + productId +
                ", mboId='" + mboId + '\'' +
                ", name='" + name + '\'' +
                ", visitCount=" + visitCount +
                '}';
    }
}
