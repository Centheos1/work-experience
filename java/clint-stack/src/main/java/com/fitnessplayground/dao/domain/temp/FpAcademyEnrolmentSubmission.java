package com.fitnessplayground.dao.domain.temp;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FpAcademyEnrolmentSubmission {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("status")
    private String status;
    @JsonProperty("communicationsStatus")
    private String communicationsStatus;
    @JsonProperty("createDate")
    private String createDate;
    @JsonProperty("updateDate")
    private String updateDate;
    @JsonProperty("fs_formId")
    private String fs_formId;
    @JsonProperty("fs_uniqueId")
    private String fs_uniqueId;
    @JsonProperty("fs_formUrl")
    private String fs_formUrl;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("dob")
    private String dob;
    @JsonProperty("address1")
    private String address1;
    @JsonProperty("city")
    private String city;
    @JsonProperty("state")
    private String state;
    @JsonProperty("postcode")
    private String postcode;
    @JsonProperty("kinName")
    private String kinName;
    @JsonProperty("kinPhone")
    private String kinPhone;
    @JsonProperty("kinRelationship")
    private String kinRelationship;
    @JsonProperty("studyMethod")
    private String studyMethod;
    @JsonProperty("courseType")
    private String courseType;
    @JsonProperty("paymentOption")
    private String paymentOption;
    @JsonProperty("paymentPlan")
    private String paymentPlan;
    @JsonProperty("paymentAmountToday")
    private Double paymentAmountToday;
    @JsonProperty("paymentAmountSplit")
    private Double paymentAmountSplit;
    @JsonProperty("paymentAmountWeekly")
    private Double paymentAmountWeekly;
    @JsonProperty("creditCard")
    private CreditCardSubmission creditCard;
    @JsonProperty("agreement")
    private String agreement;
    @JsonProperty("signature")
    private String signature;
    @JsonProperty("staffName")
    private String staffName;
    @JsonProperty("notes")
    private String notes;

    public FpAcademyEnrolmentSubmission() {
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

    public CreditCardSubmission getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCardSubmission creditCard) {
        this.creditCard = creditCard;
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

    @Override
    public String toString() {
        return "FpAcedemyEnrolmentSubmission{" +
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
                ", paymentAmountToday='" + paymentAmountToday + '\'' +
                ", paymentAmountSplit='" + paymentAmountSplit + '\'' +
                ", paymentAmountWeekly='" + paymentAmountWeekly + '\'' +
                ", memberCreditCard=" + creditCard +
                ", agreement='" + agreement + '\'' +
                ", signature='" + signature + '\'' +
                ", staffName='" + staffName + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
