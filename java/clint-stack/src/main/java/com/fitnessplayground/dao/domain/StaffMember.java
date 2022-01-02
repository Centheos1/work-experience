package com.fitnessplayground.dao.domain;

import com.fitnessplayground.model.mindbody.api.staff.Location;
import com.fitnessplayground.model.mindbody.api.staff.Staff;
import com.fitnessplayground.util.Constants;

import javax.persistence.*;

@Entity
public class StaffMember {

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
    private String permissionLevel;
    private long mboID;
    private String siteId;
    private String firebaseId;


    public static StaffMember from(Staff staff, String permissionLevel) {
        StaffMember staffMember = new StaffMember();

        return build(staff, permissionLevel, staffMember);
    }

    public static StaffMember update(Staff staff, String permissionLevel, StaffMember staffMember) {
        return build(staff, permissionLevel, staffMember);
    }

    private static StaffMember build(Staff staff, String permissionLevel, StaffMember staffMember) {

        staffMember.setName(staff.getName());
        staffMember.setFirstName(staff.getFirstName());
        staffMember.setLastName(staff.getLastName());
        staffMember.setMobile(staff.getMobilePhone());
        staffMember.setEmail(staff.getEmail());

        String loc = "";

        for (Location l : staff.getLoginLocations().getLocation()) {
            loc += l.getID().getValue() + ",";
        }

        loc = loc.substring(0, loc.length() - 1);

        staffMember.setLocationId(loc);
        staffMember.setPermissionLevel(permissionLevel);
        staffMember.setMboID(staff.getID().getValue());
        String siteId = loc.contains("4") ? Long.toString(Constants.DARWIN_SITE_ID) : Long.toString(Constants.SYDNEY_SITE_ID);
        staffMember.setSiteId(siteId);
        staffMember.setFirebaseId(staffMember.getFirebaseId());

//        System.out.println("STAFF MEMBER " +siteId);

        return  staffMember;
    }

    public StaffMember() {
    }

    public StaffMember(String name, String firstName, String lastName, String mobile, String email, String locationId, String permissionLevel, long mboID, String siteId, String firebaseId) {
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.email = email;
        this.locationId = locationId;
        this.permissionLevel = permissionLevel;
        this.mboID = mboID;
        this.siteId = siteId;
        this.firebaseId = firebaseId;
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

    public String getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(String permissionLevel) {
        this.permissionLevel = permissionLevel;
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

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }
}
