package com.fitnessplayground.dao.domain;

import javax.persistence.*;

@Entity
public class Gym_MembershipConsultant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private Integer locationId;
    private String mboIdAndSiteId;
    private String name;
    private String role;

    public Gym_MembershipConsultant() {
    }

    public Gym_MembershipConsultant(Integer locationId, String mboIdAndSiteId, String name, String role) {
        this.locationId = locationId;
        this.mboIdAndSiteId = mboIdAndSiteId;
        this.name = name;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getMboIdAndSiteId() {
        return mboIdAndSiteId;
    }

    public void setMboIdAndSiteId(String mboIdAndSiteId) {
        this.mboIdAndSiteId = mboIdAndSiteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Gym_MembershipConsultant{" +
                "id=" + id +
                ", locationId=" + locationId +
                ", mboIdAndSiteId='" + mboIdAndSiteId + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
