package com.fitnessplayground.dao.CommunicationsDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fitnessplayground.dao.domain.EnrolmentData;
import com.fitnessplayground.dao.domain.Staff;

public class MCNotesData {

    @JsonProperty("ACID")
    private String ACID;
    @JsonProperty("memberFirstName")
    private String memberFirstName;
    @JsonProperty("memberLastName")
    private String memberLastName;
    @JsonProperty("membershipConsultantFirstName")
    private String membershipConsultantFirstName;
    @JsonProperty("membershipConsultantLastName")
    private String membershipConsultantLastName;
    @JsonProperty("toAddress")
    private String toAddress;
    @JsonProperty("personalTrainerName")
    private String personalTrainerName;
    @JsonProperty("trainingStarterPack")
    private String trainingStarterPack;

    public MCNotesData() {
    }

    public static MCNotesData from(EnrolmentData enrolmentData, Staff pt, Staff mc) {
        MCNotesData data = new MCNotesData();

        data.setACID(enrolmentData.getActiveCampaignId());
        data.setMemberFirstName(enrolmentData.getFirstName());
        data.setMemberLastName(enrolmentData.getLastName());
        data.setMembershipConsultantFirstName(mc.getFirstName());
        data.setMembershipConsultantLastName(mc.getLastName());
        data.setPersonalTrainerName(pt.getName());
        data.setTrainingStarterPack(enrolmentData.getTrainingStarterPack());
        data.setToAddress(mc.getEmail());

        return data;
    }

    public String getACID() {
        return ACID;
    }

    public void setACID(String ACID) {
        this.ACID = ACID;
    }

    public String getMemberFirstName() {
        return memberFirstName;
    }

    public void setMemberFirstName(String memberFirstName) {
        this.memberFirstName = memberFirstName;
    }

    public String getMemberLastName() {
        return memberLastName;
    }

    public void setMemberLastName(String memberLastName) {
        this.memberLastName = memberLastName;
    }

    public String getMembershipConsultantFirstName() {
        return membershipConsultantFirstName;
    }

    public void setMembershipConsultantFirstName(String membershipConsultantFirstName) {
        this.membershipConsultantFirstName = membershipConsultantFirstName;
    }

    public String getMembershipConsultantLastName() {
        return membershipConsultantLastName;
    }

    public void setMembershipConsultantLastName(String membershipConsultantLastName) {
        this.membershipConsultantLastName = membershipConsultantLastName;
    }

    public String getPersonalTrainerName() {
        return personalTrainerName;
    }

    public void setPersonalTrainerName(String personalTrainerName) {
        this.personalTrainerName = personalTrainerName;
    }

    public String getTrainingStarterPack() {
        return trainingStarterPack;
    }

    public void setTrainingStarterPack(String trainingStarterPack) {
        this.trainingStarterPack = trainingStarterPack;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    @Override
    public String toString() {
        return "MCNotesData{" +
                "ACID='" + ACID + '\'' +
                ", memberFirstName='" + memberFirstName + '\'' +
                ", memberLastName='" + memberLastName + '\'' +
                ", membershipConsultantFirstName='" + membershipConsultantFirstName + '\'' +
                ", membershipConsultantLastName='" + membershipConsultantLastName + '\'' +
                ", personalTrainerName='" + personalTrainerName + '\'' +
                ", trainingStarterPack='" + trainingStarterPack + '\'' +
                '}';
    }
}
