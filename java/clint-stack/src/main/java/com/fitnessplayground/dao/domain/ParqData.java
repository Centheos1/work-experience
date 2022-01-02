package com.fitnessplayground.dao.domain;

import javax.persistence.*;

@Entity
public class ParqData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String status;
    private String fs_uniqueId;
    private String fs_formId;
    private String createDate;
    private Long ptTrackerId;
    private String submission_datetime;
    private String firstName;
    private String lastName;
    private String personalTrainerName;
    private String gymName;
    private String hasPreviousCoach;
    private String previousCoachDuration;
    private String previousCoachFrequency;
    private String previousCoachPreferences;
    private String comfortInGym;
    private String isCurrentlyExercising;
    private String numberDaysPerWeekExercise;
    private String hasProgram;
    private String areaOfFocus;
    private String areaOfFocusDetails;
    private String significanceScale;
    private String significanceDetail;
    private String roadblocks;
    private String hasPhysicalJob;
    private String sleepPerNight;
    private String energyLevelRating;
    private String stressLevelRating;
    private String destressMethod;
    private String nutritionRating;
    private String nutritionHelpRequest;
    private String painOrInjuriesAreas;
    private String aggrevationOfAilmentsRisk;
    private String aggrevationOfAilmentsRiskDetails;
    private String healthConcerns;
    private String healthConcernsDetails;
    private String howToHelp;

    public ParqData() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Long getPtTrackerId() {
        return ptTrackerId;
    }

    public void setPtTrackerId(Long ptTrackerId) {
        this.ptTrackerId = ptTrackerId;
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

    public String getPersonalTrainerName() {
        return personalTrainerName;
    }

    public void setPersonalTrainerName(String personalTrainerName) {
        this.personalTrainerName = personalTrainerName;
    }

    public String getGymName() {
        return gymName;
    }

    public void setGymName(String gymName) {
        this.gymName = gymName;
    }

    public String getHasPreviousCoach() {
        return hasPreviousCoach;
    }

    public void setHasPreviousCoach(String hasPreviousCoach) {
        this.hasPreviousCoach = hasPreviousCoach;
    }

    public String getPreviousCoachDuration() {
        return previousCoachDuration;
    }

    public void setPreviousCoachDuration(String previousCoachDuration) {
        this.previousCoachDuration = previousCoachDuration;
    }

    public String getPreviousCoachFrequency() {
        return previousCoachFrequency;
    }

    public void setPreviousCoachFrequency(String previousCoachFrequency) {
        this.previousCoachFrequency = previousCoachFrequency;
    }

    public String getPreviousCoachPreferences() {
        return previousCoachPreferences;
    }

    public void setPreviousCoachPreferences(String previousCoachPreferences) {
        this.previousCoachPreferences = previousCoachPreferences;
    }

    public String getComfortInGym() {
        return comfortInGym;
    }

    public void setComfortInGym(String comfortInGym) {
        this.comfortInGym = comfortInGym;
    }

    public String getIsCurrentlyExercising() {
        return isCurrentlyExercising;
    }

    public void setIsCurrentlyExercising(String isCurrentlyExercising) {
        this.isCurrentlyExercising = isCurrentlyExercising;
    }

    public String getNumberDaysPerWeekExercise() {
        return numberDaysPerWeekExercise;
    }

    public void setNumberDaysPerWeekExercise(String numberDaysPerWeekExercise) {
        this.numberDaysPerWeekExercise = numberDaysPerWeekExercise;
    }

    public String getHasProgram() {
        return hasProgram;
    }

    public void setHasProgram(String hasProgram) {
        this.hasProgram = hasProgram;
    }

    public String getAreaOfFocus() {
        return areaOfFocus;
    }

    public void setAreaOfFocus(String areaOfFocus) {
        this.areaOfFocus = areaOfFocus;
    }

    public String getAreaOfFocusDetails() {
        return areaOfFocusDetails;
    }

    public void setAreaOfFocusDetails(String areaOfFocusDetails) {
        this.areaOfFocusDetails = areaOfFocusDetails;
    }

    public String getSignificanceScale() {
        return significanceScale;
    }

    public void setSignificanceScale(String significanceScale) {
        this.significanceScale = significanceScale;
    }

    public String getSignificanceDetail() {
        return significanceDetail;
    }

    public void setSignificanceDetail(String significanceDetail) {
        this.significanceDetail = significanceDetail;
    }

    public String getRoadblocks() {
        return roadblocks;
    }

    public void setRoadblocks(String roadblocks) {
        this.roadblocks = roadblocks;
    }

    public String getHasPhysicalJob() {
        return hasPhysicalJob;
    }

    public void setHasPhysicalJob(String hasPhysicalJob) {
        this.hasPhysicalJob = hasPhysicalJob;
    }

    public String getSleepPerNight() {
        return sleepPerNight;
    }

    public void setSleepPerNight(String sleepPerNight) {
        this.sleepPerNight = sleepPerNight;
    }

    public String getEnergyLevelRating() {
        return energyLevelRating;
    }

    public void setEnergyLevelRating(String energyLevelRating) {
        this.energyLevelRating = energyLevelRating;
    }

    public String getStressLevelRating() {
        return stressLevelRating;
    }

    public void setStressLevelRating(String stressLevelRating) {
        this.stressLevelRating = stressLevelRating;
    }

    public String getDestressMethod() {
        return destressMethod;
    }

    public void setDestressMethod(String destressMethod) {
        this.destressMethod = destressMethod;
    }

    public String getNutritionRating() {
        return nutritionRating;
    }

    public void setNutritionRating(String nutritionRating) {
        this.nutritionRating = nutritionRating;
    }

    public String getNutritionHelpRequest() {
        return nutritionHelpRequest;
    }

    public void setNutritionHelpRequest(String nutritionHelpRequest) {
        this.nutritionHelpRequest = nutritionHelpRequest;
    }

    public String getPainOrInjuriesAreas() {
        return painOrInjuriesAreas;
    }

    public void setPainOrInjuriesAreas(String painOrInjuriesAreas) {
        this.painOrInjuriesAreas = painOrInjuriesAreas;
    }

    public String getAggrevationOfAilmentsRisk() {
        return aggrevationOfAilmentsRisk;
    }

    public void setAggrevationOfAilmentsRisk(String aggrevationOfAilmentsRisk) {
        this.aggrevationOfAilmentsRisk = aggrevationOfAilmentsRisk;
    }

    public String getAggrevationOfAilmentsRiskDetails() {
        return aggrevationOfAilmentsRiskDetails;
    }

    public void setAggrevationOfAilmentsRiskDetails(String aggrevationOfAilmentsRiskDetails) {
        this.aggrevationOfAilmentsRiskDetails = aggrevationOfAilmentsRiskDetails;
    }

    public String getHealthConcerns() {
        return healthConcerns;
    }

    public void setHealthConcerns(String healthConcerns) {
        this.healthConcerns = healthConcerns;
    }

    public String getHealthConcernsDetails() {
        return healthConcernsDetails;
    }

    public void setHealthConcernsDetails(String healthConcernsDetails) {
        this.healthConcernsDetails = healthConcernsDetails;
    }

    public String getHowToHelp() {
        return howToHelp;
    }

    public void setHowToHelp(String howToHelp) {
        this.howToHelp = howToHelp;
    }

    @Override
    public String toString() {
        return "ParqData{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", fs_uniqueId='" + fs_uniqueId + '\'' +
                ", fs_formId='" + fs_formId + '\'' +
                ", createDate='" + createDate + '\'' +
                ", ptTrackerId=" + ptTrackerId +
                ", submission_datetime='" + submission_datetime + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", personalTrainerName='" + personalTrainerName + '\'' +
                ", gymName='" + gymName + '\'' +
                ", hasPreviousCoach='" + hasPreviousCoach + '\'' +
                ", previousCoachDuration='" + previousCoachDuration + '\'' +
                ", previousCoachFrequency='" + previousCoachFrequency + '\'' +
                ", previousCoachPreferences='" + previousCoachPreferences + '\'' +
                ", comfortInGym='" + comfortInGym + '\'' +
                ", isCurrentlyExercising='" + isCurrentlyExercising + '\'' +
                ", numberDaysPerWeekExercise='" + numberDaysPerWeekExercise + '\'' +
                ", hasProgram='" + hasProgram + '\'' +
                ", areaOfFocus='" + areaOfFocus + '\'' +
                ", areaOfFocusDetails='" + areaOfFocusDetails + '\'' +
                ", significanceScale='" + significanceScale + '\'' +
                ", significanceDetail='" + significanceDetail + '\'' +
                ", roadblocks='" + roadblocks + '\'' +
                ", hasPhysicalJob='" + hasPhysicalJob + '\'' +
                ", sleepPerNight='" + sleepPerNight + '\'' +
                ", energyLevelRating='" + energyLevelRating + '\'' +
                ", stressLevelRating='" + stressLevelRating + '\'' +
                ", destressMethod='" + destressMethod + '\'' +
                ", nutritionRating='" + nutritionRating + '\'' +
                ", nutritionHelpRequest='" + nutritionHelpRequest + '\'' +
                ", painOrInjuriesAreas='" + painOrInjuriesAreas + '\'' +
                ", aggrevationOfAilmentsRisk='" + aggrevationOfAilmentsRisk + '\'' +
                ", aggrevationOfAilmentsRiskDetails='" + aggrevationOfAilmentsRiskDetails + '\'' +
                ", healthConcerns='" + healthConcerns + '\'' +
                ", healthConcernsDetails='" + healthConcernsDetails + '\'' +
                ", howToHelp='" + howToHelp + '\'' +
                '}';
    }
}
