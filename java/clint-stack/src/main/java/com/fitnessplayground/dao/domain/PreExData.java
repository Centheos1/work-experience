package com.fitnessplayground.dao.domain;

import javax.persistence.*;

@Entity

public class PreExData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String fs_uniqueId;
    private String fs_formId;
    private String gymSalesId;
    private String createDate;
    private String submission_datetime;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String gender;
    private String dob;
    private String howDidYouHearAboutUs;
    private String hasPreviousMembership;
    private String previousMembershipType;
    private String isCurrentlyExercising;
    private String currentExerciseType;
    private String lastExercisePeriod;
    private String changeMotivation;
    private String areaOfFocus;
    private String priorities;
    private String areaOfFocusDetails;
    private String areaOfFocusFirst30Days;
    private String roadBlocks;
    private String significantsScale;
    private String numberDaysPerWeekExercise;
    private Boolean hasMedicalCondition;
    private String medicalConditions;
    private Boolean hasClearanceMedicalCondition;
    private Boolean isOnMedication;
    private Boolean hasClearanceMedication;
    private String exerciseInterests;
    private String areasToHelp;
    private String signatureUrl;
    private String enquireType;
    private String gymName;
    private String assignedStaffMember;
    private String assignedStaffName;
    private String staffMember;
    private String staffName;
    private String notes;
    private String previousServicesUsed;

    public PreExData() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFs_uniqueId() {
        return fs_uniqueId;
    }

    public void setFs_uniqueId(String fs_uniqueId) {
        this.fs_uniqueId = fs_uniqueId;
    }

    public String getFs_formId() {
        return fs_formId;
    }

    public void setFs_formId(String fs_formId) {
        this.fs_formId = fs_formId;
    }

    public String getGymSalesId() {
        return gymSalesId;
    }

    public void setGymSalesId(String gymSalesId) {
        this.gymSalesId = gymSalesId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getSubmission_datetime() {
        return submission_datetime;
    }

    public void setSubmission_datetime(String submission_datetime) {
        this.submission_datetime = submission_datetime;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getHowDidYouHearAboutUs() {
        return howDidYouHearAboutUs;
    }

    public void setHowDidYouHearAboutUs(String howDidYouHearAboutUs) {
        this.howDidYouHearAboutUs = howDidYouHearAboutUs;
    }

    public String getHasPreviousMembership() {
        return hasPreviousMembership;
    }

    public void setHasPreviousMembership(String hasPreviousMembership) {
        this.hasPreviousMembership = hasPreviousMembership;
    }

    public String getPreviousMembershipType() {
        return previousMembershipType;
    }

    public void setPreviousMembershipType(String previousMembershipType) {
        this.previousMembershipType = previousMembershipType;
    }

    public String getIsCurrentlyExercising() {
        return isCurrentlyExercising;
    }

    public void setIsCurrentlyExercising(String isCurrentlyExercising) {
        this.isCurrentlyExercising = isCurrentlyExercising;
    }

    public String getCurrentExerciseType() {
        return currentExerciseType;
    }

    public void setCurrentExerciseType(String currentExerciseType) {
        this.currentExerciseType = currentExerciseType;
    }

    public String getLastExercisePeriod() {
        return lastExercisePeriod;
    }

    public void setLastExercisePeriod(String lastExercisePeriod) {
        this.lastExercisePeriod = lastExercisePeriod;
    }

    public String getChangeMotivation() {
        return changeMotivation;
    }

    public void setChangeMotivation(String changeMotivation) {
        this.changeMotivation = changeMotivation;
    }

    public String getAreaOfFocus() {
        return areaOfFocus;
    }

    public void setAreaOfFocus(String areaOfFocus) {
        this.areaOfFocus = areaOfFocus;
    }

    public String getPriorities() {
        return priorities;
    }

    public void setPriorities(String priorities) {
        this.priorities = priorities;
    }

    public String getAreaOfFocusDetails() {
        return areaOfFocusDetails;
    }

    public void setAreaOfFocusDetails(String areaOfFocusDetails) {
        this.areaOfFocusDetails = areaOfFocusDetails;
    }

    public String getAreaOfFocusFirst30Days() {
        return areaOfFocusFirst30Days;
    }

    public void setAreaOfFocusFirst30Days(String areaOfFocusFirst30Days) {
        this.areaOfFocusFirst30Days = areaOfFocusFirst30Days;
    }

    public String getRoadBlocks() {
        return roadBlocks;
    }

    public void setRoadBlocks(String roadBlocks) {
        this.roadBlocks = roadBlocks;
    }

    public String getSignificantsScale() {
        return significantsScale;
    }

    public void setSignificantsScale(String significantsScale) {
        this.significantsScale = significantsScale;
    }

    public String getNumberDaysPerWeekExercise() {
        return numberDaysPerWeekExercise;
    }

    public void setNumberDaysPerWeekExercise(String numberDaysPerWeekExercise) {
        this.numberDaysPerWeekExercise = numberDaysPerWeekExercise;
    }

    public Boolean getHasMedicalCondition() {
        return hasMedicalCondition;
    }

    public void setHasMedicalCondition(Boolean hasMedicalCondition) {
        this.hasMedicalCondition = hasMedicalCondition;
    }

    public String getMedicalConditions() {
        return medicalConditions;
    }

    public void setMedicalConditions(String medicalConditions) {
        this.medicalConditions = medicalConditions;
    }

    public Boolean getHasClearanceMedicalCondition() {
        return hasClearanceMedicalCondition;
    }

    public void setHasClearanceMedicalCondition(Boolean hasClearanceMedicalCondition) {
        this.hasClearanceMedicalCondition = hasClearanceMedicalCondition;
    }

    public Boolean getOnMedication() {
        return isOnMedication;
    }

    public void setOnMedication(Boolean onMedication) {
        isOnMedication = onMedication;
    }

    public Boolean getHasClearanceMedication() {
        return hasClearanceMedication;
    }

    public void setHasClearanceMedication(Boolean hasClearanceMedication) {
        this.hasClearanceMedication = hasClearanceMedication;
    }

    public String getExerciseInterests() {
        return exerciseInterests;
    }

    public void setExerciseInterests(String exerciseInterests) {
        this.exerciseInterests = exerciseInterests;
    }

    public String getAreasToHelp() {
        return areasToHelp;
    }

    public void setAreasToHelp(String areasToHelp) {
        this.areasToHelp = areasToHelp;
    }

    public String getSignatureUrl() {
        return signatureUrl;
    }

    public void setSignatureUrl(String signatureUrl) {
        this.signatureUrl = signatureUrl;
    }

    public String getEnquireType() {
        return enquireType;
    }

    public void setEnquireType(String enquireType) {
        this.enquireType = enquireType;
    }

    public String getGymName() {
        return gymName;
    }

    public void setGymName(String gymName) {
        this.gymName = gymName;
    }

    public String getAssignedStaffMember() {
        return assignedStaffMember;
    }

    public void setAssignedStaffMember(String assignedStaffMember) {
        this.assignedStaffMember = assignedStaffMember;
    }

    public String getAssignedStaffName() {
        return assignedStaffName;
    }

    public void setAssignedStaffName(String assignedStaffName) {
        this.assignedStaffName = assignedStaffName;
    }

    public String getStaffMember() {
        return staffMember;
    }

    public void setStaffMember(String staffMember) {
        this.staffMember = staffMember;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPreviousServicesUsed() {
        return previousServicesUsed;
    }

    public void setPreviousServicesUsed(String previousServicesUsed) {
        this.previousServicesUsed = previousServicesUsed;
    }

    @Override
    public String toString() {
        return "PreExData{" +
                "id=" + id +
                ", fs_uniqueId='" + fs_uniqueId + '\'' +
                ", fs_formId='" + fs_formId + '\'' +
                ", gymSalesId='" + gymSalesId + '\'' +
                ", createDate='" + createDate + '\'' +
                ", submission_datetime='" + submission_datetime + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", dob='" + dob + '\'' +
                ", howDidYouHearAboutUs='" + howDidYouHearAboutUs + '\'' +
                ", hasPreviousMembership='" + hasPreviousMembership + '\'' +
                ", previousMembershipType='" + previousMembershipType + '\'' +
                ", isCurrentlyExercising='" + isCurrentlyExercising + '\'' +
                ", currentExerciseType='" + currentExerciseType + '\'' +
                ", lastExercisePeriod='" + lastExercisePeriod + '\'' +
                ", changeMotivation='" + changeMotivation + '\'' +
                ", areaOfFocus='" + areaOfFocus + '\'' +
                ", priorities='" + priorities + '\'' +
                ", areaOfFocusDetails='" + areaOfFocusDetails + '\'' +
                ", areaOfFocusFirst30Days='" + areaOfFocusFirst30Days + '\'' +
                ", roadBlocks='" + roadBlocks + '\'' +
                ", significantsScale='" + significantsScale + '\'' +
                ", numberDaysPerWeekExercise='" + numberDaysPerWeekExercise + '\'' +
                ", hasMedicalCondition=" + hasMedicalCondition +
                ", medicalConditions='" + medicalConditions + '\'' +
                ", hasClearanceMedicalCondition=" + hasClearanceMedicalCondition +
                ", isOnMedication=" + isOnMedication +
                ", hasClearanceMedication=" + hasClearanceMedication +
                ", exerciseInterests='" + exerciseInterests + '\'' +
                ", areasToHelp='" + areasToHelp + '\'' +
                ", signatureUrl='" + signatureUrl + '\'' +
                ", enquireType='" + enquireType + '\'' +
                ", gymName='" + gymName + '\'' +
                ", assignedStaffMember='" + assignedStaffMember + '\'' +
                ", assignedStaffName='" + assignedStaffName + '\'' +
                ", staffMember='" + staffMember + '\'' +
                ", staffName='" + staffName + '\'' +
                ", notes='" + notes + '\'' +
                ", previousServicesUsed='" + previousServicesUsed + '\'' +
                '}';
    }
}
