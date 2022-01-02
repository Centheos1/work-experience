package com.fitnessplayground.dao.domain;

import com.fitnessplayground.dao.domain.temp.FpAcademyEnrolmentSubmission;
import com.fitnessplayground.util.Helpers;

import javax.persistence.*;

@Entity
public class FpAcademyEnrolmentData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private String status;
    private String communicationsStatus;
    private String createDate;
    private String updateDate;
    private String fs_formId;
    private String fs_uniqueId;
    private String fs_formUrl;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String gender;
    private String dob;
    private String address1;
    private String city;
    private String state;
    private String postcode;
    private String kinName;
    private String kinPhone;
    private String kinRelationship;
    private String studyMethod;
    private String courseType;
    private String paymentOption;
    private String paymentPlan;
    private Double paymentAmountToday;
    private Double paymentAmountSplit;
    private Double paymentAmountWeekly;
    private String agreement;
    private String signature;
    private String staffName;
    private String notes;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "creditCardId")
    private MemberCreditCard creditCard;

    public FpAcademyEnrolmentData() {
    }

    public static FpAcademyEnrolmentData create(FpAcademyEnrolmentSubmission submission) {
        FpAcademyEnrolmentData data = new FpAcademyEnrolmentData();
        return build(data, submission, true);
    }

    public static FpAcademyEnrolmentData update(FpAcademyEnrolmentData data, FpAcademyEnrolmentSubmission submission, boolean isNewPaymentDetails) {
        return build(data,submission,isNewPaymentDetails);
    }

    private static FpAcademyEnrolmentData build(FpAcademyEnrolmentData data, FpAcademyEnrolmentSubmission submission, boolean isNewPaymentDetails) {

        data.setStatus(submission.getStatus());
        data.setCommunicationsStatus(submission.getCommunicationsStatus());
        data.setCreateDate(submission.getCreateDate());
        data.setUpdateDate(Helpers.getDateNow());
        data.setFs_formId(submission.getFs_formId());
        data.setFs_uniqueId(submission.getFs_uniqueId());
        data.setFs_formUrl(submission.getFs_formUrl());
        data.setFirstName(submission.getFirstName());
        data.setLastName(submission.getLastName());
        data.setEmail(submission.getEmail());
        data.setPhone(submission.getPhone());
        data.setGender(submission.getGender());
        data.setDob(submission.getDob());
        data.setAddress1(submission.getAddress1());
        data.setCity(submission.getCity());
        data.setState(submission.getState());
        data.setPostcode(submission.getPostcode());
        data.setKinName(submission.getKinName());
        data.setKinPhone(submission.getKinPhone());
        data.setKinRelationship(submission.getKinRelationship());
        data.setStudyMethod(submission.getStudyMethod());
        data.setCourseType(submission.getCourseType());
        data.setPaymentOption(submission.getPaymentOption());
        data.setPaymentPlan(submission.getPaymentPlan());
        data.setPaymentAmountToday(submission.getPaymentAmountToday());
        data.setPaymentAmountSplit(submission.getPaymentAmountSplit());
        data.setPaymentAmountWeekly(submission.getPaymentAmountWeekly());
        data.setAgreement(submission.getAgreement());
        data.setSignature(submission.getSignature());
        data.setStaffName(submission.getStaffName());
        data.setNotes(submission.getNotes());

        if (isNewPaymentDetails) {
            if (submission.getCreditCard() != null) {
                data.setCreditCard(MemberCreditCard.create(submission.getCreditCard()));
            }
        }

        return data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCommunicationsStatus() {
        return communicationsStatus;
    }

    public void setCommunicationsStatus(String communicationsStatus) {
        this.communicationsStatus = communicationsStatus;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getFs_formId() {
        return fs_formId;
    }

    public void setFs_formId(String fs_formId) {
        this.fs_formId = fs_formId;
    }

    public String getFs_uniqueId() {
        return fs_uniqueId;
    }

    public void setFs_uniqueId(String fs_uniqueId) {
        this.fs_uniqueId = fs_uniqueId;
    }

    public String getFs_formUrl() {
        return fs_formUrl;
    }

    public void setFs_formUrl(String fs_formUrl) {
        this.fs_formUrl = fs_formUrl;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getKinName() {
        return kinName;
    }

    public void setKinName(String kinName) {
        this.kinName = kinName;
    }

    public String getKinPhone() {
        return kinPhone;
    }

    public void setKinPhone(String kinPhone) {
        this.kinPhone = kinPhone;
    }

    public String getKinRelationship() {
        return kinRelationship;
    }

    public void setKinRelationship(String kinRelationship) {
        this.kinRelationship = kinRelationship;
    }

    public String getStudyMethod() {
        return studyMethod;
    }

    public void setStudyMethod(String studyMethod) {
        this.studyMethod = studyMethod;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
    }

    public String getPaymentPlan() {
        return paymentPlan;
    }

    public void setPaymentPlan(String paymentPlan) {
        this.paymentPlan = paymentPlan;
    }

    public Double getPaymentAmountToday() {
        return paymentAmountToday;
    }

    public void setPaymentAmountToday(Double paymentAmountToday) {
        this.paymentAmountToday = paymentAmountToday;
    }

    public Double getPaymentAmountSplit() {
        return paymentAmountSplit;
    }

    public void setPaymentAmountSplit(Double paymentAmountSplit) {
        this.paymentAmountSplit = paymentAmountSplit;
    }

    public Double getPaymentAmountWeekly() {
        return paymentAmountWeekly;
    }

    public void setPaymentAmountWeekly(Double paymentAmountWeekly) {
        this.paymentAmountWeekly = paymentAmountWeekly;
    }

    public String getAgreement() {
        return agreement;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public MemberCreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(MemberCreditCard creditCard) {
        this.creditCard = creditCard;
    }

    @Override
    public String toString() {
        return "FpAcademyEnrolmentData{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", communicationsStatus='" + communicationsStatus + '\'' +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", fs_formId='" + fs_formId + '\'' +
                ", fs_uniqueId='" + fs_uniqueId + '\'' +
                ", fs_formUrl='" + fs_formUrl + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", dob='" + dob + '\'' +
                ", address1='" + address1 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postcode='" + postcode + '\'' +
                ", kinName='" + kinName + '\'' +
                ", kinPhone='" + kinPhone + '\'' +
                ", kinRelationship='" + kinRelationship + '\'' +
                ", studyMethod='" + studyMethod + '\'' +
                ", courseType='" + courseType + '\'' +
                ", paymentOption='" + paymentOption + '\'' +
                ", paymentPlan='" + paymentPlan + '\'' +
                ", paymentAmountToday=" + paymentAmountToday +
                ", paymentAmountSplit=" + paymentAmountSplit +
                ", paymentAmountWeekly=" + paymentAmountWeekly +
                ", agreement='" + agreement + '\'' +
                ", signature='" + signature + '\'' +
                ", staffName='" + staffName + '\'' +
                ", notes='" + notes + '\'' +
                ", creditCard=" + creditCard +
                '}';
    }
}
