package com.fitnessplayground.dao.domain.ActiveCampaignDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ACLinks {

    @JsonProperty("bounceLogs")
    private String bounceLogs; //"https://:account.api-us1.com/api/:version/contacts/68/bounceLogs",
    @JsonProperty("contactAutomations")
    private String contactAutomations; //https://:account.api-us1.com/api/:version/contacts/68/contactAutomations",
    @JsonProperty("contactData")
    private String contactData; // https://:account.api-us1.com/api/:version/contacts/68/contactData",
    @JsonProperty("contactGoals")
    private String contactGoals; //https://:account.api-us1.com/api/:version/contacts/68/contactGoals",
    @JsonProperty("contactLists")
    private String contactLists;// "https://:account.api-us1.com/api/:version/contacts/68/contactLists",
    @JsonProperty("contactLogs")
    private String contactLogs; //"https://:account.api-us1.com/api/:version/contacts/68/contactLogs",
    @JsonProperty("contactTags")
    private String contactTags;//"https://:account.api-us1.com/api/:version/contacts/68/contactTags",
    @JsonProperty("contactDeals")
    private String contactDeals;//"https://:account.api-us1.com/api/:version/contacts/68/contactDeals",
    @JsonProperty("deals")
    private String deals;//"https://:account.api-us1.com/api/:version/contacts/68/deals",
    @JsonProperty("fieldValues")
    private String  fieldValues;//"https://:account.api-us1.com/api/:version/contacts/68/fieldValues",
    @JsonProperty("geoIps")
    private String geoIps;//"https://:account.api-us1.com/api/:version/contacts/68/geoIps",
    @JsonProperty("notes")
    private String notes;//"https://:account.api-us1.com/api/:version/contacts/68/notes",
    @JsonProperty("organization")
    private String organization;//"https://:account.api-us1.com/api/:version/contacts/68/organization",
    @JsonProperty("plusAppend")
    private String plusAppend;//"https://:account.api-us1.com/api/:version/contacts/68/plusAppend",
    @JsonProperty("trackingLogs")
    private String trackingLogs;//"https://:account.api-us1.com/api/:version/contacts/68/trackingLogs",
    @JsonProperty("scoreValues")
    private String scoreValues;//"https://:account.api-us1.com/api/:version/contacts/68/scoreValues"
    @JsonProperty("contactGoalTags")
    private String contactGoalTags;
    @JsonProperty("field")
    private String field;


    public ACLinks() {
    }

    public String getBounceLogs() {
        return bounceLogs;
    }

    public void setBounceLogs(String bounceLogs) {
        this.bounceLogs = bounceLogs;
    }

    public String getContactAutomations() {
        return contactAutomations;
    }

    public void setContactAutomations(String contactAutomations) {
        this.contactAutomations = contactAutomations;
    }

    public String getContactData() {
        return contactData;
    }

    public void setContactData(String contactData) {
        this.contactData = contactData;
    }

    public String getContactGoals() {
        return contactGoals;
    }

    public void setContactGoals(String contactGoals) {
        this.contactGoals = contactGoals;
    }

    public String getContactLists() {
        return contactLists;
    }

    public void setContactLists(String contactLists) {
        this.contactLists = contactLists;
    }

    public String getContactLogs() {
        return contactLogs;
    }

    public void setContactLogs(String contactLogs) {
        this.contactLogs = contactLogs;
    }

    public String getContactTags() {
        return contactTags;
    }

    public void setContactTags(String contactTags) {
        this.contactTags = contactTags;
    }

    public String getContactDeals() {
        return contactDeals;
    }

    public void setContactDeals(String contactDeals) {
        this.contactDeals = contactDeals;
    }

    public String getDeals() {
        return deals;
    }

    public void setDeals(String deals) {
        this.deals = deals;
    }

    public String getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(String fieldValues) {
        this.fieldValues = fieldValues;
    }

    public String getGeoIps() {
        return geoIps;
    }

    public void setGeoIps(String geoIps) {
        this.geoIps = geoIps;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getPlusAppend() {
        return plusAppend;
    }

    public void setPlusAppend(String plusAppend) {
        this.plusAppend = plusAppend;
    }

    public String getTrackingLogs() {
        return trackingLogs;
    }

    public void setTrackingLogs(String trackingLogs) {
        this.trackingLogs = trackingLogs;
    }

    public String getScoreValues() {
        return scoreValues;
    }

    public void setScoreValues(String scoreValues) {
        this.scoreValues = scoreValues;
    }

    public String getContactGoalTags() {
        return contactGoalTags;
    }

    public void setContactGoalTags(String contactGoalTags) {
        this.contactGoalTags = contactGoalTags;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "ACLinks{" +
                "bounceLogs='" + bounceLogs + '\'' +
                ", contactAutomations='" + contactAutomations + '\'' +
                ", contactData='" + contactData + '\'' +
                ", contactGoals='" + contactGoals + '\'' +
                ", contactLists='" + contactLists + '\'' +
                ", contactLogs='" + contactLogs + '\'' +
                ", contactTags='" + contactTags + '\'' +
                ", contactDeals='" + contactDeals + '\'' +
                ", deals='" + deals + '\'' +
                ", fieldValues='" + fieldValues + '\'' +
                ", geoIps='" + geoIps + '\'' +
                ", notes='" + notes + '\'' +
                ", organization='" + organization + '\'' +
                ", plusAppend='" + plusAppend + '\'' +
                ", trackingLogs='" + trackingLogs + '\'' +
                ", scoreValues='" + scoreValues + '\'' +
                ", contactGoalTags='" + contactGoalTags + '\'' +
                '}';
    }
}
