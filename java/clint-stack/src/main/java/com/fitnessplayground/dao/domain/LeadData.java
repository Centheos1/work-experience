package com.fitnessplayground.dao.domain;

import javax.persistence.*;

@Entity
public class LeadData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private String company_id; // Required by Gymsales
    private String first_name; // Required by Gymsales
    private String last_name;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String phone_home;
    private String phone;
    private String phone_work;
    private String email;
    private String tag_list;
    private String contact_method_name; // Enum: Referrals, Internet, Walk In, Guest Register, Phone-in, Outreach, Ex Member, Corporate, Other
    private String source_name;
    private String notes;
    private String gender;
    private String birthday;
    private String referred_by_name;
    private String referred_by_phone;
    private String referred_by_email;
    private String external_id;
    private String googleClickId;
    private String facebookCampaignId;
    private String gymSalesId;
    private String activeCampaignId;
    private String status;
    private String previous_status;
    private String createDate;
    private String updateDate;
    private String gymName;

    public LeadData() {
    }

    public LeadData(String address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone_home() {
        return phone_home;
    }

    public void setPhone_home(String phone_home) {
        this.phone_home = phone_home;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone_work() {
        return phone_work;
    }

    public void setPhone_work(String phone_work) {
        this.phone_work = phone_work;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTag_list() {
        return tag_list;
    }

    public void setTag_list(String tag_list) {
        this.tag_list = tag_list;
    }

    public String getContact_method_name() {
        return contact_method_name;
    }

    public void setContact_method_name(String contact_method_name) {
        this.contact_method_name = contact_method_name;
    }

    public String getSource_name() {
        return source_name;
    }

    public void setSource_name(String source_name) {
        this.source_name = source_name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public String getExternal_id() {
        return external_id;
    }

    public void setExternal_id(String external_id) {
        this.external_id = external_id;
    }

    public String getGoogleClickId() {
        return googleClickId;
    }

    public void setGoogleClickId(String googleClickId) {
        this.googleClickId = googleClickId;
    }

    public String getFacebookCampaignId() {
        return facebookCampaignId;
    }

    public void setFacebookCampaignId(String facebookCampaignId) {
        this.facebookCampaignId = facebookCampaignId;
    }

    public String getGymSalesId() {
        return gymSalesId;
    }

    public void setGymSalesId(String gymSalesId) {
        this.gymSalesId = gymSalesId;
    }

    public String getActiveCampaignId() {
        return activeCampaignId;
    }

    public void setActiveCampaignId(String activeCampaignId) {
        this.activeCampaignId = activeCampaignId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrevious_status() {
        return previous_status;
    }

    public void setPrevious_status(String previous_status) {
        this.previous_status = previous_status;
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

    public String getGymName() {
        return gymName;
    }

    public void setGymName(String gymName) {
        this.gymName = gymName;
    }

    @Override
    public String toString() {
        return "LeadData{" +
                "id=" + id +
                ", company_id='" + company_id + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", phone_home='" + phone_home + '\'' +
                ", phone='" + phone + '\'' +
                ", phone_work='" + phone_work + '\'' +
                ", email='" + email + '\'' +
                ", tag_list='" + tag_list + '\'' +
                ", contact_method_name='" + contact_method_name + '\'' +
                ", source_name='" + source_name + '\'' +
                ", notes='" + notes + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                ", referred_by_name='" + referred_by_name + '\'' +
                ", referred_by_phone='" + referred_by_phone + '\'' +
                ", referred_by_email='" + referred_by_email + '\'' +
                ", external_id='" + external_id + '\'' +
                ", googleClickId='" + googleClickId + '\'' +
                ", facebookCampaignId='" + facebookCampaignId + '\'' +
                ", gymSalesId='" + gymSalesId + '\'' +
                ", activeCampaignId='" + activeCampaignId + '\'' +
                ", status='" + status + '\'' +
                ", previous_status='" + previous_status + '\'' +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                '}';
    }
}
