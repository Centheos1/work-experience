package com.fitnessplayground.dao.domain;

import javax.persistence.*;

@Entity
public class PtTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private Long enrolmentDataId;
    private Long fpCoachId;
    private Long preExId;
    private Long parqId;
    private Long cancellationDataId;
    private String locationId;
    private String feedbackIds;
    private String status;
    private String communicationsStatus;
    private String mboUniqueId;
    private String createDate;
    private String updateDate;
    private String communicationsUpdateDate;
    private String staffMember;
    private String personalTrainer;
    private Boolean isReassigned;
    private Integer sessionCount;
    private Boolean hasFirstSessionBooked;

    public PtTracker() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEnrolmentDataId() {
        return enrolmentDataId;
    }

    public void setEnrolmentDataId(Long enrolmentDataId) {
        this.enrolmentDataId = enrolmentDataId;
    }

    public Long getFpCoachId() {
        return fpCoachId;
    }

    public void setFpCoachId(Long fpCoachId) {
        this.fpCoachId = fpCoachId;
    }

    public Long getPreExId() {
        return preExId;
    }

    public void setPreExId(Long preExId) {
        this.preExId = preExId;
    }

    public Long getParqId() {
        return parqId;
    }

    public void setParqId(Long parqId) {
        this.parqId = parqId;
    }

    public Long getCancellationDataId() {
        return cancellationDataId;
    }

    public void setCancellationDataId(Long cancellationDataId) {
        this.cancellationDataId = cancellationDataId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getFeedbackIds() {
        return feedbackIds;
    }

    public void setFeedbackIds(String feedbackIds) {
        this.feedbackIds = feedbackIds;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCommunicationsStatus() {
        return communicationsStatus;
    }

    public void setCommunicationsStatus(String communicationsStatus) {
        this.communicationsStatus = communicationsStatus;
    }

    public String getMboUniqueId() {
        return mboUniqueId;
    }

    public void setMboUniqueId(String mboUniqueId) {
        this.mboUniqueId = mboUniqueId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public String getCommunicationsUpdateDate() {
        return communicationsUpdateDate;
    }

    public void setCommunicationsUpdateDate(String communicationsUpdateDate) {
        this.communicationsUpdateDate = communicationsUpdateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getStaffMember() {
        return staffMember;
    }

    public void setStaffMember(String staffMember) {
        this.staffMember = staffMember;
    }

    public String getPersonalTrainer() {
        return personalTrainer;
    }

    public void setPersonalTrainer(String personalTrainer) {
        this.personalTrainer = personalTrainer;
    }

    public Boolean getReassigned() {
        return isReassigned;
    }

    public void setReassigned(Boolean reassigned) {
        isReassigned = reassigned;
    }

    public Integer getSessionCount() {
        return sessionCount;
    }

    public void setSessionCount(Integer sessionCount) {
        this.sessionCount = sessionCount;
    }

    public Boolean getHasFirstSessionBooked() {
        return hasFirstSessionBooked;
    }

    public void setHasFirstSessionBooked(Boolean hasFirstSessionBooked) {
        this.hasFirstSessionBooked = hasFirstSessionBooked;
    }

    @Override
    public String toString() {
        return "PtTracker{" +
                "id=" + id +
                ", enrolmentDataId=" + enrolmentDataId +
                ", fpCoachId=" + fpCoachId +
                ", preExId=" + preExId +
                ", parqId=" + parqId +
                ", cancellationDataId=" + cancellationDataId +
                ", locationId='" + locationId + '\'' +
                ", feedbackIds='" + feedbackIds + '\'' +
                ", status='" + status + '\'' +
                ", communicationsStatus='" + communicationsStatus + '\'' +
                ", mboUniqueId='" + mboUniqueId + '\'' +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", communicationsUpdateDate='" + communicationsUpdateDate + '\'' +
                ", staffMember='" + staffMember + '\'' +
                ", personalTrainer='" + personalTrainer + '\'' +
                ", isReassigned=" + isReassigned +
                ", sessionCount=" + sessionCount +
                ", hasFirstSessionBooked=" + hasFirstSessionBooked +
                '}';
    }
}
