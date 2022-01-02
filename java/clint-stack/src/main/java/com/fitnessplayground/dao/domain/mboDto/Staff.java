package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Staff {

    @JsonProperty("Address")//: null,
    private String address;
    @JsonProperty("Bio")
    private String bio; //": "",
    @JsonProperty("City")//: null,
    private String city;
    @JsonProperty("Email")
    private String email;// "serra@thefitnessplayground.com.au",
    @JsonProperty("FirstName")
    private String firstName;// "Serra",
    @JsonProperty("Id")
    private long mboId;// 5,
    @JsonProperty("IndependentContractor")
    private boolean independentContractor;// false,
    @JsonProperty("IsMale")
    private boolean isMale;// false,
    @JsonProperty("LastName")
    private String lastName;// "Burmin",
    @JsonProperty("MobilePhone")
    private String mobilePhone;// "0419623727",
    @JsonProperty("Name")
    private String name;// null,
    @JsonProperty("PostalCode")
    private String postalCode;// null,
    @JsonProperty("ClassTeacher")
    private boolean isClassTeacher;// true,
    @JsonProperty("State")
    private String state;// "0",
    @JsonProperty("ImageUrl")
    private String imageUrl;// null,
    private String siteId;

    public Staff() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public long getMboId() {
        return mboId;
    }

    public void setMboId(long mboId) {
        this.mboId = mboId;
    }

    public boolean isIndependentContractor() {
        return independentContractor;
    }

    public void setIndependentContractor(boolean independentContractor) {
        this.independentContractor = independentContractor;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public boolean isClassTeacher() {
        return isClassTeacher;
    }

    public void setClassTeacher(boolean classTeacher) {
        isClassTeacher = classTeacher;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "address='" + address + '\'' +
                ", bio='" + bio + '\'' +
                ", city='" + city + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", mboId=" + mboId +
                ", independentContractor=" + independentContractor +
                ", isMale=" + isMale +
                ", lastName='" + lastName + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", name='" + name + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", isClassTeacher=" + isClassTeacher +
                ", state='" + state + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", siteId='" + siteId + '\'' +
                '}';
    }
}
