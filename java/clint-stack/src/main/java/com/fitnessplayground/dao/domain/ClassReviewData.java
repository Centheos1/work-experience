package com.fitnessplayground.dao.domain;

import com.fitnessplayground.dao.domain.temp.ClassReviewSubmission;
import com.fitnessplayground.dao.domain.temp.EpochTimeStamp;

import javax.persistence.*;

@Entity
public class ClassReviewData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String classInfo;
    private String instructorName;
    private String className;
    private String userFirstName;
    private Integer ratingScore;
    private String reviewerName;
    private String reviewDate;
    private String userLastName;
    private String scheduleID;
    private String reviewText;
    private Long instructorID;
    private String timestamp;

    public ClassReviewData() {
    }

    public static ClassReviewData from(ClassReviewSubmission submission) {

        ClassReviewData data = new ClassReviewData();

        data.setClassInfo(submission.getClassInfo());
        data.setInstructorName(submission.getInstructorName());
        data.setClassName(submission.getClassName());
        data.setUserFirstName(submission.getUserFirstName());
        data.setRatingScore(submission.getRatingScore());
        data.setReviewerName(submission.getReviewerName());
        data.setReviewDate(submission.getReviewDate());
        data.setUserLastName(submission.getUserLastName());
        data.setScheduleID(submission.getScheduleID());
        data.setReviewText(submission.getReviewText());
        data.setInstructorID(submission.getInstructorID());
        data.setTimestamp(submission.getTimestamp().toString());

        return data;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ClassReviewData{" +
                "id=" + id +
                ", classInfo='" + classInfo + '\'' +
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
