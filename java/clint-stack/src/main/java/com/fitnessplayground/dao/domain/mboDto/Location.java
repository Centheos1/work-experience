package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

    @JsonProperty("SiteID")
    private Integer siteID;
    @JsonProperty("BusinessDescription")
    private String businessDescription;
    @JsonProperty("AdditionalImageURLs")
    private ArrayList<String> additionalImageURLs; //list of urls
    @JsonProperty("HasClasses")
    private Boolean hasClasses;
    @JsonProperty("PhoneExtension")
    private String phoneExtension;
    @JsonProperty("ID")
    private Integer locId;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Address")
    private String address;
    @JsonProperty("Address2")
    private String address2;
    @JsonProperty("Tax1")
    private Float tax1;
    @JsonProperty("Tax2")
    private Float tax2;
    @JsonProperty("Tax3")
    private Float tax3;
    @JsonProperty("Tax4")
    private Float tax4;
    @JsonProperty("Tax5")
    private Float tax5;
    @JsonProperty("Phone")
    private String phone;
    @JsonProperty("City")
    private String city;
    @JsonProperty("StateProvCode")
    private String stateProvCode;
    @JsonProperty("PostalCode")
    private String postalCode;
    @JsonProperty("Latitude")
    private Double latitude;
    @JsonProperty("Longitude")
    private Double longitude;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("TotalNumberOfRatings")
    private Integer totalNumberOfRatings;
    @JsonProperty("AverageRating")
    private Float averageRating;
    @JsonProperty("TotalNumberOfDeals")
    private Integer totalNumberOfDeals;

    public Location() {
    }

    public Integer getSiteID() {
        return siteID;
    }

    public void setSiteID(Integer siteID) {
        this.siteID = siteID;
    }

    public String getBusinessDescription() {
        return businessDescription;
    }

    public void setBusinessDescription(String businessDescription) {
        this.businessDescription = businessDescription;
    }

    public ArrayList<String> getAdditionalImageURLs() {
        return additionalImageURLs;
    }

    public void setAdditionalImageURLs(ArrayList<String> additionalImageURLs) {
        this.additionalImageURLs = additionalImageURLs;
    }

    public Boolean getHasClasses() {
        return hasClasses;
    }

    public void setHasClasses(Boolean hasClasses) {
        this.hasClasses = hasClasses;
    }

    public String getPhoneExtension() {
        return phoneExtension;
    }

    public void setPhoneExtension(String phoneExtension) {
        this.phoneExtension = phoneExtension;
    }

    public Integer getLocId() {
        return locId;
    }

    public void setLocId(Integer locId) {
        this.locId = locId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public Float getTax1() {
        return tax1;
    }

    public void setTax1(Float tax1) {
        this.tax1 = tax1;
    }

    public Float getTax2() {
        return tax2;
    }

    public void setTax2(Float tax2) {
        this.tax2 = tax2;
    }

    public Float getTax3() {
        return tax3;
    }

    public void setTax3(Float tax3) {
        this.tax3 = tax3;
    }

    public Float getTax4() {
        return tax4;
    }

    public void setTax4(Float tax4) {
        this.tax4 = tax4;
    }

    public Float getTax5() {
        return tax5;
    }

    public void setTax5(Float tax5) {
        this.tax5 = tax5;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvCode() {
        return stateProvCode;
    }

    public void setStateProvCode(String stateProvCode) {
        this.stateProvCode = stateProvCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTotalNumberOfRatings() {
        return totalNumberOfRatings;
    }

    public void setTotalNumberOfRatings(Integer totalNumberOfRatings) {
        this.totalNumberOfRatings = totalNumberOfRatings;
    }

    public Float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Float averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getTotalNumberOfDeals() {
        return totalNumberOfDeals;
    }

    public void setTotalNumberOfDeals(Integer totalNumberOfDeals) {
        this.totalNumberOfDeals = totalNumberOfDeals;
    }

    @Override
    public String toString() {
        return "Location{" +
                "siteID=" + siteID +
                ", businessDescription='" + businessDescription + '\'' +
                ", additionalImageURLs=" + additionalImageURLs +
                ", hasClasses=" + hasClasses +
                ", phoneExtension='" + phoneExtension + '\'' +
                ", locId=" + locId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", address2='" + address2 + '\'' +
                ", tax1=" + tax1 +
                ", tax2=" + tax2 +
                ", tax3=" + tax3 +
                ", tax4=" + tax4 +
                ", tax5=" + tax5 +
                ", phone='" + phone + '\'' +
                ", city='" + city + '\'' +
                ", stateProvCode='" + stateProvCode + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", description='" + description + '\'' +
                ", totalNumberOfRatings=" + totalNumberOfRatings +
                ", averageRating=" + averageRating +
                ", totalNumberOfDeals=" + totalNumberOfDeals +
                '}';
    }
}
