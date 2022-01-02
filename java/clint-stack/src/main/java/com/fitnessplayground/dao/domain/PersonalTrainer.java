package com.fitnessplayground.dao.domain;

import com.fitnessplayground.model.mindbody.api.staff.Location;
import com.fitnessplayground.model.mindbody.api.staff.Staff;
import com.fitnessplayground.util.Constants;

import javax.persistence.*;

@Entity
public class PersonalTrainer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String name;
    private String firstName;
    private String lastName;
    private String mobile;
    private String email;
    private String locationId;
    private long mboID;
    private String siteId;

    public PersonalTrainer() {
    }

    public static PersonalTrainer from(Staff staff) {
        PersonalTrainer personalTrainer = new PersonalTrainer();
        return build(staff,personalTrainer);
    }

    public static PersonalTrainer update(Staff staff,  PersonalTrainer personalTrainer) {
        return build(staff,personalTrainer);
    }

    public static PersonalTrainer build(Staff staff, PersonalTrainer personalTrainer) {
        personalTrainer.setName(staff.getName());
        personalTrainer.setFirstName(staff.getFirstName());
        personalTrainer.setLastName(staff.getLastName());
        personalTrainer.setMobile(staff.getMobilePhone());
        personalTrainer.setEmail(staff.getEmail());

        String loc = "";

        for (Location l : staff.getLoginLocations().getLocation()) {
            loc += l.getID().getValue() + ",";
        }

        loc = loc.substring(0, loc.length() - 1);

        personalTrainer.setLocationId(loc);
        personalTrainer.setMboID(staff.getID().getValue());
        String siteId = loc.contains("4") ? Long.toString(Constants.DARWIN_SITE_ID) : Long.toString(Constants.SYDNEY_SITE_ID);
        personalTrainer.setSiteId(siteId);
//        System.out.println("PERSONAL TRAINER " +siteId);

        return personalTrainer;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public long getMboID() {
        return mboID;
    }

    public void setMboID(long mboID) {
        this.mboID = mboID;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    @Override
    public String toString() {
        return "PersonalTrainer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", locationId='" + locationId + '\'' +
                ", mboID=" + mboID +
                ", siteId=" + siteId +
                '}';
    }
}
