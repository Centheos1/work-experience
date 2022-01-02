package com.fitnessplayground.dao.domain;

import com.fitnessplayground.dao.domain.mboDto.Service;

import javax.persistence.*;

@Entity
public class MboService {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private double price;
    private double onlinePrice;
    private double taxIncluded;
    private int programId;
    private double taxRate;
    private int productId;
    private int mboId;
    private String name;
    private int visitCount;
    private String locationId;

    public MboService() {
    }

    public static MboService save(Service service, String locationId) {
        MboService mboService = new MboService();
        return build(service, mboService, locationId);
    }

    public static MboService update(Service service, MboService mboService, String locationId) {
        return build(service, mboService, locationId);
    }

    private static MboService build(Service service, MboService mboService, String locationId) {
        mboService.setPrice(service.getPrice());
        mboService.setOnlinePrice(service.getOnlinePrice());
        mboService.setTaxIncluded(service.getTaxIncluded());
        mboService.setProgramId(service.getProgramId());
        mboService.setTaxRate(service.getTaxRate());
        mboService.setProductId(service.getProductId());
        mboService.setMboId(Integer.parseInt(service.getMboId()));
        mboService.setName(service.getName());
        mboService.setVisitCount(service.getVisitCount());
        if (!locationId.equals("4")) {
            locationId = "1,2,3,5";
        }
        mboService.setLocationId(locationId);
        return mboService;
    }

    public long getId() {
        return id;
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

    public int getMboId() {
        return mboId;
    }

    public void setMboId(int mboId) {
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

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    @Override
    public String toString() {
        return "MboService{" +
                "id=" + id +
                ", price=" + price +
                ", onlinePrice=" + onlinePrice +
                ", taxIncluded=" + taxIncluded +
                ", programId=" + programId +
                ", taxRate=" + taxRate +
                ", productId=" + productId +
                ", mboId='" + mboId + '\'' +
                ", name='" + name + '\'' +
                ", visitCount=" + visitCount +
                ", locationId='" + locationId + '\'' +
                '}';
    }
}
