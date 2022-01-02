package com.fitnessplayground.dao.domain.temp;

public class MemberDetails {
    
    private String address1; // 120A Devonshire Street,
    private String address2; // ,
    private String city; // Surry Hills,
    private String state; // NSW,
    private String postcode; // 2010,
    private String dob; // 2005-03-03,
    private String gender; // male,
    private String emergencyContactName; // em,
    private String emergencyContactPhone; // 0413506306,
    private String occupation; // ,
    private String employer; // Fitness Playground,
    private String howDidYouHearAboutUs; // internet,
    private String numberOneGoal; // feelBetter

    public MemberDetails() {
    }

    public MemberDetails(String address1, String address2, String city, String state, String postcode, String dob, String gender, String emergencyContactName, String emergencyContactPhone, String occupation, String employer, String howDidYouHearAboutUs, String numberOneGoal) {
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.postcode = postcode;
        this.dob = dob;
        this.gender = gender;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactPhone = emergencyContactPhone;
        this.occupation = occupation;
        this.employer = employer;
        this.howDidYouHearAboutUs = howDidYouHearAboutUs;
        this.numberOneGoal = numberOneGoal;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getHowDidYouHearAboutUs() {
        return howDidYouHearAboutUs;
    }

    public void setHowDidYouHearAboutUs(String howDidYouHearAboutUs) {
        this.howDidYouHearAboutUs = howDidYouHearAboutUs;
    }

    public String getNumberOneGoal() {
        return numberOneGoal;
    }

    public void setNumberOneGoal(String numberOneGoal) {
        this.numberOneGoal = numberOneGoal;
    }

    @Override
    public String toString() {
        return "MemberDetails{" +
                "address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postcode='" + postcode + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", emergencyContactName='" + emergencyContactName + '\'' +
                ", emergencyContactPhone='" + emergencyContactPhone + '\'' +
                ", occupation='" + occupation + '\'' +
                ", employer='" + employer + '\'' +
                ", howDidYouHearAboutUs='" + howDidYouHearAboutUs + '\'' +
                ", numberOneGoal='" + numberOneGoal + '\'' +
                '}';
    }
}
