package com.fitnessplayground.dao.domain;

import com.fitnessplayground.dao.domain.fpSourceDto.MboStaff;

import javax.persistence.*;

@Entity
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String name;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String permissionLevel;
    private String role;
    private String locationId;
    private String mboId;
    private String siteId;
    private String firebaseId;

    public Staff() {
    }

    public static Staff create(MboStaff mboStaff) {
        Staff staff = new Staff();
        return build(staff, mboStaff);
    }

    public static Staff update(MboStaff mboStaff, Staff staff) {
        return build(staff, mboStaff);
    }

    private static Staff build(Staff staff, MboStaff mboStaff) {

        staff.setName(mboStaff.getName());
        staff.setFirstName(mboStaff.getFirstName());
        staff.setLastName(mboStaff.getLastName());
        staff.setPhone(mboStaff.getPhone());
        staff.setEmail(mboStaff.getEmail());
        staff.setMboId(mboStaff.getMboId());
        staff.setSiteId(mboStaff.getSiteId());

        return staff;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String mobile) {
        this.phone = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(String permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMboId() {
        return mboId;
    }

    public void setMboId(String mboId) {
        this.mboId = mboId;
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

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", permissionLevel='" + permissionLevel + '\'' +
                ", role='" + role + '\'' +
                ", locationId='" + locationId + '\'' +
                ", mboId='" + mboId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", firebaseId='" + firebaseId + '\'' +
                '}';
    }
}
