package com.fitnessplayground.dao.domain;

import com.fitnessplayground.dao.domain.mboDto.Product;

import javax.persistence.*;

@Entity
public class MboProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private Double price;
    private Double taxIncluded;
    private Double taxRate;
    private String mboId;
    private Integer groupId;
    private String name;
    private Double onlinePrice;
    private String shortDescription;
    private String longDescription;
    private Long colorId;
    private String colorName;
    private Long sizeId;
    private String sizeName;
    private String locationId;

    public MboProduct() {
    }

    public static MboProduct save(Product product, String locationId) {
        MboProduct mboProduct = new MboProduct();
        return build(product, mboProduct, locationId);
    }

    public static MboProduct update(Product product, MboProduct mboProduct, String locationId) {
        return build(product, mboProduct, locationId);
    }

    private static MboProduct build(Product product, MboProduct mboProduct, String locationId) {

        mboProduct.setPrice(product.getPrice());
        mboProduct.setTaxIncluded(product.getTaxIncluded());
        mboProduct.setMboId(product.getMboId());
        mboProduct.setGroupId(product.getGroupId());
        mboProduct.setName(product.getName());
        mboProduct.setOnlinePrice(product.getOnlinePrice());
        mboProduct.setShortDescription(product.getShortDescription());
        mboProduct.setLongDescription(product.getLongDescription());
        mboProduct.setColorId(product.getColor().getMboId());
        mboProduct.setColorName(product.getColor().getName());
        mboProduct.setSizeId(product.getSize().getMboId());
        mboProduct.setSizeName(product.getSize().getName());
        mboProduct.setLocationId(locationId);

        return mboProduct;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Long getColorId() {
        return colorId;
    }

    public void setColorId(Long colorId) {
        this.colorId = colorId;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public Long getSizeId() {
        return sizeId;
    }

    public void setSizeId(Long sizeId) {
        this.sizeId = sizeId;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    @Override
    public String toString() {
        return "MboProduct{" +
                "id=" + id +
                ", price=" + price +
                ", taxIncluded=" + taxIncluded +
                ", taxRate=" + taxRate +
                ", mboId='" + mboId + '\'' +
                ", groupId=" + groupId +
                ", name='" + name + '\'' +
                ", onlinePrice=" + onlinePrice +
                ", shortDescription='" + shortDescription + '\'' +
                ", longDescription='" + longDescription + '\'' +
                ", colorId=" + colorId +
                ", colorName='" + colorName + '\'' +
                ", sizeId=" + sizeId +
                ", sizeName='" + sizeName + '\'' +
                ", locationId='" + locationId + '\'' +
                '}';
    }
}
