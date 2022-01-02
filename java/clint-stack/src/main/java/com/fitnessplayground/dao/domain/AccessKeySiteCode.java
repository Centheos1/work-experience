package com.fitnessplayground.dao.domain;

import javax.persistence.*;

@Entity
public class AccessKeySiteCode {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private int locationId; // integer,
    private String siteCode; //  varchar(255),

    public AccessKeySiteCode() {
    }

    public long getId() {
        return id;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    @Override
    public String toString() {
        return "AccessKeySiteCode{" +
                "id=" + id +
                ", locationId=" + locationId +
                ", siteCode='" + siteCode + '\'' +
                '}';
    }
}
