package com.fitnessplayground.dao.domain;

import javax.persistence.*;

@Entity
public class ReferralData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String name;
    private String phone;
    private String email;
    private String status;
    private String createDate;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "enrolmentDataId")
    private EnrolmentData enrolmentData;

    public ReferralData() {
    }

    public ReferralData(String name, String phone, String email, String status, String createDate, EnrolmentData enrolmentData) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.status = status;
        this.createDate = createDate;
        this.enrolmentData = enrolmentData;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EnrolmentData getEnrolmentData() {
        return enrolmentData;
    }

    public void setEnrolmentData(EnrolmentData enrolmentData) {
        this.enrolmentData = enrolmentData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "ReferralData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                ", createDate='" + createDate + '\'' +
                ", enrolmentData=" + enrolmentData +
                '}';
    }
}
