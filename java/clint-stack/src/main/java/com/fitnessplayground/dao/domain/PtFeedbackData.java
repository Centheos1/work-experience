package com.fitnessplayground.dao.domain;

import javax.persistence.*;

@Entity
public class PtFeedbackData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String ptTrackerId;
    private String fs_uniqueId;
    private String fs_formId;
    private String createDate;
    private String gymName;
    private String locationId;
    private String personalTrainer;
    private String personalTrainerName;
    private Boolean isAnonymous;
    private String firstName;
    private String lastName;
    private String trainingRating;
    private String trainingRequested;
    private String nutritionRating;
    private String nutritionRequested;
    private String assessmentRating;
    private String assessmentRequested;
    private String ptOperationsFeedback;
    private String progressionRating;
    private String sessionQualityRating;
    private String areasToImprove;
    private Boolean hasFeedback;
    private String positiveFeedback;
    private String negativeFeedback;
    private String netPromoterScore;
    private String feedbackType;
    private String trainingUpdated;
    private String nutritionUpdated;
    private String assessmentUpdated;
    private String goalAchieved;
    private String mostProudOf;
    private String roadBlocks;
    private String newAreaOfFocus;
    private String newAreaOfFocusDetails;
    private String supportDetails;
    private String hasGoogleReview;

    public PtFeedbackData() {
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getGymName() {
        return gymName;
    }

    public void setGymName(String gymName) {
        this.gymName = gymName;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getPersonalTrainer() {
        return personalTrainer;
    }

    public void setPersonalTrainer(String personalTrainer) {
        this.personalTrainer = personalTrainer;
    }

    public String getPersonalTrainerName() {
        return personalTrainerName;
    }

    public void setPersonalTrainerName(String personalTrainerName) {
        this.personalTrainerName = personalTrainerName;
    }

    public Boolean getAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        isAnonymous = anonymous;
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

    public String getTrainingRating() {
        return trainingRating;
    }

    public void setTrainingRating(String trainingRating) {
        this.trainingRating = trainingRating;
    }

    public String getTrainingRequested() {
        return trainingRequested;
    }

    public void setTrainingRequested(String trainingRequested) {
        this.trainingRequested = trainingRequested;
    }

    public String getNutritionRating() {
        return nutritionRating;
    }

    public void setNutritionRating(String nutritionRating) {
        this.nutritionRating = nutritionRating;
    }

    public String getNutritionRequested() {
        return nutritionRequested;
    }

    public void setNutritionRequested(String nutritionRequested) {
        this.nutritionRequested = nutritionRequested;
    }

    public String getAssessmentRating() {
        return assessmentRating;
    }

    public void setAssessmentRating(String assessmentRating) {
        this.assessmentRating = assessmentRating;
    }

    public String getAssessmentRequested() {
        return assessmentRequested;
    }

    public void setAssessmentRequested(String assessmentRequested) {
        this.assessmentRequested = assessmentRequested;
    }

    public String getPtOperationsFeedback() {
        return ptOperationsFeedback;
    }

    public void setPtOperationsFeedback(String ptOperationsFeedback) {
        this.ptOperationsFeedback = ptOperationsFeedback;
    }

    public String getProgressionRating() {
        return progressionRating;
    }

    public void setProgressionRating(String progressionRating) {
        this.progressionRating = progressionRating;
    }

    public String getSessionQualityRating() {
        return sessionQualityRating;
    }

    public void setSessionQualityRating(String sessionQualityRating) {
        this.sessionQualityRating = sessionQualityRating;
    }

    public String getAreasToImprove() {
        return areasToImprove;
    }

    public void setAreasToImprove(String areasToImprove) {
        this.areasToImprove = areasToImprove;
    }

    public Boolean getHasFeedback() {
        return hasFeedback;
    }

    public void setHasFeedback(Boolean hasFeedback) {
        this.hasFeedback = hasFeedback;
    }

    public String getPositiveFeedback() {
        return positiveFeedback;
    }

    public void setPositiveFeedback(String positiveFeedback) {
        this.positiveFeedback = positiveFeedback;
    }

    public String getNegativeFeedback() {
        return negativeFeedback;
    }

    public void setNegativeFeedback(String negativeFeedback) {
        this.negativeFeedback = negativeFeedback;
    }

    public String getNetPromoterScore() {
        return netPromoterScore;
    }

    public void setNetPromoterScore(String netPromoterScore) {
        this.netPromoterScore = netPromoterScore;
    }

    public String getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(String feedbackType) {
        this.feedbackType = feedbackType;
    }

    public String getTrainingUpdated() {
        return trainingUpdated;
    }

    public void setTrainingUpdated(String trainingUpdated) {
        this.trainingUpdated = trainingUpdated;
    }

    public String getNutritionUpdated() {
        return nutritionUpdated;
    }

    public void setNutritionUpdated(String nutritionUpdated) {
        this.nutritionUpdated = nutritionUpdated;
    }

    public String getAssessmentUpdated() {
        return assessmentUpdated;
    }

    public void setAssessmentUpdated(String assessmentUpdated) {
        this.assessmentUpdated = assessmentUpdated;
    }

    public String getGoalAchieved() {
        return goalAchieved;
    }

    public void setGoalAchieved(String goalAchieved) {
        this.goalAchieved = goalAchieved;
    }

    public String getMostProudOf() {
        return mostProudOf;
    }

    public void setMostProudOf(String mostProudOf) {
        this.mostProudOf = mostProudOf;
    }

    public String getRoadBlocks() {
        return roadBlocks;
    }

    public void setRoadBlocks(String roadBlocks) {
        this.roadBlocks = roadBlocks;
    }

    public String getNewAreaOfFocus() {
        return newAreaOfFocus;
    }

    public void setNewAreaOfFocus(String newAreaOfFocus) {
        this.newAreaOfFocus = newAreaOfFocus;
    }

    public String getNewAreaOfFocusDetails() {
        return newAreaOfFocusDetails;
    }

    public void setNewAreaOfFocusDetails(String newAreaOfFocusDetails) {
        this.newAreaOfFocusDetails = newAreaOfFocusDetails;
    }

    public String getSupportDetails() {
        return supportDetails;
    }

    public void setSupportDetails(String supportDetails) {
        this.supportDetails = supportDetails;
    }

    public String getHasGoogleReview() {
        return hasGoogleReview;
    }

    public void setHasGoogleReview(String hasGoogleReview) {
        this.hasGoogleReview = hasGoogleReview;
    }

    public String getPtTrackerId() {
        return ptTrackerId;
    }

    public void setPtTrackerId(String ptTrackerId) {
        this.ptTrackerId = ptTrackerId;
    }

    @Override
    public String toString() {
        return "PtFeedbackData{" +
                "id=" + id +
                ", ptTrackerId='" + ptTrackerId + '\'' +
                ", fs_uniqueId='" + fs_uniqueId + '\'' +
                ", fs_formId='" + fs_formId + '\'' +
                ", createDate='" + createDate + '\'' +
                ", gymName='" + gymName + '\'' +
                ", locationId='" + locationId + '\'' +
                ", personalTrainer='" + personalTrainer + '\'' +
                ", personalTrainerName='" + personalTrainerName + '\'' +
                ", isAnonymous=" + isAnonymous +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", trainingRating='" + trainingRating + '\'' +
                ", trainingRequested='" + trainingRequested + '\'' +
                ", nutritionRating='" + nutritionRating + '\'' +
                ", nutritionRequested='" + nutritionRequested + '\'' +
                ", assessmentRating='" + assessmentRating + '\'' +
                ", assessmentRequested='" + assessmentRequested + '\'' +
                ", ptOperationsFeedback='" + ptOperationsFeedback + '\'' +
                ", progressionRating='" + progressionRating + '\'' +
                ", sessionQualityRating='" + sessionQualityRating + '\'' +
                ", areasToImprove='" + areasToImprove + '\'' +
                ", hasFeedback=" + hasFeedback +
                ", positiveFeedback='" + positiveFeedback + '\'' +
                ", negativeFeedback='" + negativeFeedback + '\'' +
                ", netPromoterScore='" + netPromoterScore + '\'' +
                ", feedbackType='" + feedbackType + '\'' +
                ", trainingUpdated='" + trainingUpdated + '\'' +
                ", nutritionUpdated='" + nutritionUpdated + '\'' +
                ", assessmentUpdated='" + assessmentUpdated + '\'' +
                ", goalAchieved='" + goalAchieved + '\'' +
                ", mostProudOf='" + mostProudOf + '\'' +
                ", roadBlocks='" + roadBlocks + '\'' +
                ", newAreaOfFocus='" + newAreaOfFocus + '\'' +
                ", newAreaOfFocusDetails='" + newAreaOfFocusDetails + '\'' +
                ", supportDetails='" + supportDetails + '\'' +
                ", hasGoogleReview='" + hasGoogleReview + '\'' +
                '}';
    }
}
