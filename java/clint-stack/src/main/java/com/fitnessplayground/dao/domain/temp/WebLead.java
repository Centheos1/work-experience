package com.fitnessplayground.dao.domain.temp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WebLead {

    @JsonProperty("fullname")
    private String name;
    @JsonProperty("mobile")
    private String phone;
    @JsonProperty("email")
    private String email;
    @JsonProperty("location")
    private String location;
    @JsonProperty("source")
    private String source;
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

    public WebLead() {
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    @Override
    public String toString() {
        return "WebLead{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", location='" + location + '\'' +
                ", source='" + source + '\'' +
                ", notes='" + notes + '\'' +
                ", referred_by_name='" + referred_by_name + '\'' +
                ", referred_by_phone='" + referred_by_phone + '\'' +
                ", referred_by_email='" + referred_by_email + '\'' +
                '}';
    }
}
