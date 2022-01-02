package com.fitnessplayground.dao.domain;

import javax.persistence.*;

@Entity
public class StaffAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String name;
    private long mboId;
    private long siteId;
    private String firebaseId;

    public StaffAccess() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMboId() {
        return mboId;
    }

    public void setMboId(long mboId) {
        this.mboId = mboId;
    }

    public long getSiteId() {
        return siteId;
    }

    public void setSiteId(long siteId) {
        this.siteId = siteId;
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    @Override
    public String toString() {
        return "StaffAccess{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mboId=" + mboId +
                ", siteId=" + siteId +
                ", firebaseId='" + firebaseId + '\'' +
                '}';
    }
}
