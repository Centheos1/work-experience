package com.fitnessplayground.dao.domain.temp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClassReviewSubmission {

    @JsonProperty("classInfo")
    private String classInfo;
    @JsonProperty("instructorName")
    private String instructorName;
    @JsonProperty("className")
    private String className;
    @JsonProperty("userFirstName")
    private String userFirstName;
    @JsonProperty("ratingScore")
    private Integer ratingScore;
    @JsonProperty("reviewerName")
    private String reviewerName;
    @JsonProperty("reviewDate")
    private String reviewDate;
    @JsonProperty("userLastName")
    private String userLastName;
    @JsonProperty("scheduleID")
    private String scheduleID;
    @JsonProperty("reviewText")
    private String reviewText;
    @JsonProperty("instructorID")
    private Long instructorID;
    @JsonProperty("timestamp")
    private EpochTimeStamp timestamp;

    public ClassReviewSubmission() {
    }

    public String getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(String classInfo) {
        this.classInfo = classInfo;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public Integer getRatingScore() {
        return ratingScore;
    }

    public void setRatingScore(Integer ratingScore) {
        this.ratingScore = ratingScore;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Long getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(Long instructorID) {
        this.instructorID = instructorID;
    }

    public EpochTimeStamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(EpochTimeStamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ClassReviewSubmission{" +
                "classInfo='" + classInfo + '\'' +
                ", instructorName='" + instructorName + '\'' +
                ", className='" + className + '\'' +
                ", userFirstName='" + userFirstName + '\'' +
                ", ratingScore=" + ratingScore +
                ", reviewerName='" + reviewerName + '\'' +
                ", reviewDate='" + reviewDate + '\'' +
                ", userLastName='" + userLastName + '\'' +
                ", scheduleID='" + scheduleID + '\'' +
                ", reviewText='" + reviewText + '\'' +
                ", instructorID=" + instructorID +
                ", timestamp=" + timestamp +
                '}';
    }
}
