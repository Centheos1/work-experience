package com.fitnessplayground.dao.domain.lambdaDto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GymSalesAddPeopleResponse {

    @JsonProperty("id") // 643634,
    private Long id;
    @JsonProperty("external_id") // 5433,
    private Long external_id;
    @JsonProperty("company_id") // 438,
    private Long company_id;
    @JsonProperty("company_external_id") // 2879,
    private Long company_external_id;
    @JsonProperty("first_name") // "Angelita",
    private String first_name;
    @JsonProperty("last_name") // "Bechtelar",
    private String last_name;
    @JsonProperty("address") // "6328 Becker Inlet",
    private String address;
    @JsonProperty("city") // "Batzburgh",
    private String city;
    @JsonProperty("state") // "CA",
    private String state;
    @JsonProperty("zip") // "81066",
    private String zip;
    @JsonProperty("phone_home") // "03  0751-3140",
    private String phone_home;
    @JsonProperty("phone_mobile") // "0411 033-381",
    private String phone_mobile;
    @JsonProperty("phone_work") //"03  1231-7621",
    private String phone_work;
    @JsonProperty("email") // "email@server.com",
    private String email;
    @JsonProperty("contact_method_name") // "Internet",
    private String contact_method_name;
    @JsonProperty("source_name") // "www.source.com",
    private String source_name;
    @JsonProperty("tag_list") // "Tag3, tag2, tag1",
    private String tag_list;
    @JsonProperty("avatar") // "https://www.example.com/avatar.jpg",
    private String avatar;
    @JsonProperty("social_link") // "https://twitter.com/username",
    private String social_link;
//    @JsonProperty("salesperson") // null,
//    private String salesperson;
    @JsonProperty("status") // "enquiry",
    private String status;
    @JsonProperty("trial_end_at") // null,
    private String trial_end_at;
    @JsonProperty("trial_days_quantity") // null,
    private String trial_days_quantity;
    @JsonProperty("gender") // "M",
    private String gender;
    @JsonProperty("birthday") // "1987-03-10"
    private String birthday;

    public GymSalesAddPeopleResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExternal_id() {
        return external_id;
    }

    public void setExternal_id(Long external_id) {
        this.external_id = external_id;
    }

    public Long getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Long company_id) {
        this.company_id = company_id;
    }

    public Long getCompany_external_id() {
        return company_external_id;
    }

    public void setCompany_external_id(Long company_external_id) {
        this.company_external_id = company_external_id;
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

    public String getPhone_mobile() {
        return phone_mobile;
    }

    public void setPhone_mobile(String phone_mobile) {
        this.phone_mobile = phone_mobile;
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

    public String getTag_list() {
        return tag_list;
    }

    public void setTag_list(String tag_list) {
        this.tag_list = tag_list;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSocial_link() {
        return social_link;
    }

    public void setSocial_link(String social_link) {
        this.social_link = social_link;
    }
//
//    public String getSalesperson() {
//        return salesperson;
//    }
//
//    public void setSalesperson(String salesperson) {
//        this.salesperson = salesperson;
//    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrial_end_at() {
        return trial_end_at;
    }

    public void setTrial_end_at(String trial_end_at) {
        this.trial_end_at = trial_end_at;
    }

    public String getTrial_days_quantity() {
        return trial_days_quantity;
    }

    public void setTrial_days_quantity(String trial_days_quantity) {
        this.trial_days_quantity = trial_days_quantity;
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

    @Override
    public String toString() {
        return "GymSalesAddPeopleResponse{" +
                "id=" + id +
                ", external_id=" + external_id +
                ", company_id=" + company_id +
                ", company_external_id=" + company_external_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", phone_home='" + phone_home + '\'' +
                ", phone_mobile='" + phone_mobile + '\'' +
                ", phone_work='" + phone_work + '\'' +
                ", email='" + email + '\'' +
                ", contact_method_name='" + contact_method_name + '\'' +
                ", source_name='" + source_name + '\'' +
                ", tag_list='" + tag_list + '\'' +
                ", avatar='" + avatar + '\'' +
                ", social_link='" + social_link + '\'' +
//                ", salesperson='" + salesperson + '\'' +
                ", status='" + status + '\'' +
                ", trial_end_at='" + trial_end_at + '\'' +
                ", trial_days_quantity='" + trial_days_quantity + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                '}';
    }
}
