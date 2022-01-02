package com.fitnessplayground.dao.domain.temp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GravityFormsWebhookLead {

//    {"id":"13",
//            "form_id":"2",
//            "post_id":null,
//            "date_created":"2021-07-19 06:24:02",
//            "date_updated":"2021-07-19 06:24:02",
//            "is_starred":"0",
//            "is_read":"0",
//            "ip":"159.196.101.210",
//            "source_url":"https:\/\/trainers.thebunkergym.com.au\/",
//            "user_agent":"Mozilla\/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit\/537.36 (KHTML, like Gecko) Chrome\/91.0.4472.114 Safari\/537.36",
//            "currency":"AUD",
//            "payment_status":null,
//            "payment_date":null,
//            "payment_amount":null,
//            "payment_method":null,
//            "transaction_id":null,
//            "is_fulfilled":null,
//            "created_by":"4",
//            "transaction_type":null,
//            "status":"active",
//            "1":"Clint Test",
//    "2":"clint@centheos.com",
//            "3":"0413506306",
//            "4":"Marrickville",
//            "5":"offer_general"}

    @JsonProperty("date_created")
    private String createDate;
    @JsonProperty("1")
    private String name;
    @JsonProperty("fullName")
    private String fullName;
    @JsonProperty("2")
    private String email;
    @JsonProperty("emailAddress")
    private String emailAddress;
    @JsonProperty("3")
    private String phone;
    @JsonProperty("mobilePhone")
    private String mobilePhone;
    @JsonProperty("4")
    private String gymName;
    @JsonProperty("location")
    private String location;
    @JsonProperty("5")
    private String sourceName;
    @JsonProperty("leadSource")
    private String leadSource;
    @JsonProperty("6")
    private String gymName2;
    @JsonProperty("uyg_category")
    private String uyg_category;
    @JsonProperty("uyg_is_member")
    private String uyg_is_member;
    @JsonProperty("uyg_has_coach_interest")
    private String uyg_has_coach_interest;
    @JsonProperty("notes")
    private String notes;
    @JsonProperty("referred_by_name")
    private String referred_by_name;
    @JsonProperty("referred_by_phone")
    private String referred_by_phone;
    @JsonProperty("referred_by_email")
    private String referred_by_email;
    @JsonProperty("fbclid")
    private String facebookCampaignId;
    @JsonProperty("gclid")
    private String googleClickId;
    @JsonProperty("isMember")
    private String isMember;


    public GravityFormsWebhookLead() {
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getGymName() {
        return gymName;
    }

    public void setGymName(String gymName) {
        this.gymName = gymName;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getGymName2() {
        return gymName2;
    }

    public void setGymName2(String gymName2) {
        this.gymName2 = gymName2;
    }

    public String getUyg_category() {
        return uyg_category;
    }

    public void setUyg_category(String uyg_category) {
        this.uyg_category = uyg_category;
    }

    public String getUyg_is_member() {
        return uyg_is_member;
    }

    public void setUyg_is_member(String uyg_is_member) {
        this.uyg_is_member = uyg_is_member;
    }

    public String getUyg_has_coach_interest() {
        return uyg_has_coach_interest;
    }

    public void setUyg_has_coach_interest(String uyg_has_coach_interest) {
        this.uyg_has_coach_interest = uyg_has_coach_interest;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLeadSource() {
        return leadSource;
    }

    public void setLeadSource(String leadSource) {
        this.leadSource = leadSource;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getReferred_by_name() {
        return referred_by_name;
    }

    public void setReferred_by_name(String referred_by_name) {
        this.referred_by_name = referred_by_name;
    }

    public String getReferred_by_phone() {
        return referred_by_phone;
    }

    public void setReferred_by_phone(String referred_by_phone) {
        this.referred_by_phone = referred_by_phone;
    }

    public String getReferred_by_email() {
        return referred_by_email;
    }

    public void setReferred_by_email(String referred_by_email) {
        this.referred_by_email = referred_by_email;
    }

    public String getFacebookCampaignId() {
        return facebookCampaignId;
    }

    public void setFacebookCampaignId(String facebookCampaignId) {
        this.facebookCampaignId = facebookCampaignId;
    }

    public String getGoogleClickId() {
        return googleClickId;
    }

    public void setGoogleClickId(String googleClickId) {
        this.googleClickId = googleClickId;
    }

    public String getIsMember() {
        return isMember;
    }

    public void setIsMember(String isMember) {
        this.isMember = isMember;
    }

    @Override
    public String toString() {
        return "GravityFormsWebhookLead{" +
                "createDate='" + createDate + '\'' +
                ", name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", phone='" + phone + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", gymName='" + gymName + '\'' +
                ", location='" + location + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", leadSource='" + leadSource + '\'' +
                ", gymName2='" + gymName2 + '\'' +
                ", uyg_category='" + uyg_category + '\'' +
                ", uyg_is_member='" + uyg_is_member + '\'' +
                ", uyg_has_coach_interest='" + uyg_has_coach_interest + '\'' +
                ", notes='" + notes + '\'' +
                ", referred_by_name='" + referred_by_name + '\'' +
                ", referred_by_phone='" + referred_by_phone + '\'' +
                ", referred_by_email='" + referred_by_email + '\'' +
                ", facebookCampaignId='" + facebookCampaignId + '\'' +
                ", googleClickId='" + googleClickId + '\'' +
                ", isMember='" + isMember + '\'' +
                '}';
    }
}
