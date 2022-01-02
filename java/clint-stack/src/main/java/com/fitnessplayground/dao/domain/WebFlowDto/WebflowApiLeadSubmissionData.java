package com.fitnessplayground.dao.domain.WebFlowDto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WebflowApiLeadSubmissionData {

//    {
//        "name":"Free PT session",
//        "site":"5ed46aed697783fcc0a86d90",
//        "data":{
//            "Name":"Ed Test",
//            "Email":"test@centheos.com",
//            "Phone":"0412345678",
//            "Location":"Marrickville",
//            "Fitness Challenge":"Difficult to build muscle",
//            "Training Experience":"Currently training"
//    },
//    "d":"2020-07-30T03:22:02.512Z",
//    "_id":"5f223cdafd51884f61ad7689"
//    }

    @JsonProperty("Name")
    private String Name;
    @JsonProperty("Test")
    private String Phone;
    @JsonProperty("Email")
    private String Email;
    @JsonProperty("Location")
    private String Location;
    @JsonProperty("campaign_offer")
    private String campaign_offer;
    @JsonProperty("Fitness Challenge")
    private String Fitness_Challenge;
    @JsonProperty("Training Experience")
    private String Training_Experience;
    @JsonProperty("Notes")
    private String notes;
    @JsonProperty("gclid")
    private String gclid;
    @JsonProperty("source_id")
    private String source_id;

    public WebflowApiLeadSubmissionData() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getCampaign_offer() {
        return campaign_offer;
    }

    public void setCampaign_offer(String campaign_offer) {
        this.campaign_offer = campaign_offer;
    }

    public String getFitness_Challenge() {
        return Fitness_Challenge;
    }

    public void setFitness_Challenge(String fitness_Challenge) {
        Fitness_Challenge = fitness_Challenge;
    }

    public String getTraining_Experience() {
        return Training_Experience;
    }

    public void setTraining_Experience(String training_Experience) {
        Training_Experience = training_Experience;
    }

    public String getGclid() {
        return gclid;
    }

    public void setGclid(String gclid) {
        this.gclid = gclid;
    }

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "WebflowApiLeadSubmissionData{" +
                "Name='" + Name + '\'' +
                ", Phone='" + Phone + '\'' +
                ", Email='" + Email + '\'' +
                ", Location='" + Location + '\'' +
                ", campaign_offer='" + campaign_offer + '\'' +
                ", Fitness_Challenge='" + Fitness_Challenge + '\'' +
                ", Training_Experience='" + Training_Experience + '\'' +
                ", googleClickId='" + gclid + '\'' +
                ", source_id='" + source_id + '\'' +
                '}';
    }
}
