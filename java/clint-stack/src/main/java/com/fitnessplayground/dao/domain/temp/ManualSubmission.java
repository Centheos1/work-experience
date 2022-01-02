package com.fitnessplayground.dao.domain.temp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ManualSubmission {

    @JsonProperty("status")
    private String status;
    @JsonProperty("errorDetails")
    private String errorDetails;
    @JsonProperty("errorCode")
    private Integer errorCode;
    @JsonProperty("submissionDate")
    private String submissionDate;// varchar(255),
    @JsonProperty("mboSubmissionCount")
    private Integer mboSubmissionCount;
    @JsonProperty("staffMember")
    private String staffMember;
    @JsonProperty("staffName")
    private String staffName;
    @JsonProperty("productId")
    private String productId;
    @JsonProperty("enrolmentDataId")
    private Long enrolmentDataId;
    @JsonProperty("cancellationDataId")
    private Long cancellationDataId;
    @JsonProperty("contractId")
    private Long contractId;
    @JsonProperty("serviceId")
    private Long serviceId;
    @JsonProperty("fpCoachEnrolmentDataId")
    private Long fpCoachEnrolmentDataId;

    public ManualSubmission() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Integer getMboSubmissionCount() {
        return mboSubmissionCount;
    }

    public void setMboSubmissionCount(Integer mboSubmissionCount) {
        this.mboSubmissionCount = mboSubmissionCount;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Long getEnrolmentDataId() {
        return enrolmentDataId;
    }

    public void setEnrolmentDataId(Long enrolmentDataId) {
        this.enrolmentDataId = enrolmentDataId;
    }

    public Long getCancellationDataId() {
        return cancellationDataId;
    }

    public void setCancellationDataId(Long cancellationDataId) {
        this.cancellationDataId = cancellationDataId;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getFpCoachEnrolmentDataId() {
        return fpCoachEnrolmentDataId;
    }

    public void setFpCoachEnrolmentDataId(Long fpCoachEnrolmentDataId) {
        this.fpCoachEnrolmentDataId = fpCoachEnrolmentDataId;
    }

    @Override
    public String toString() {
        return "ManualSubmission{" +
                "status='" + status + '\'' +
                ", errorDetails='" + errorDetails + '\'' +
                ", errorCode=" + errorCode +
                ", submissionDate='" + submissionDate + '\'' +
                ", mboSubmissionCount=" + mboSubmissionCount +
                ", staffMember='" + staffMember + '\'' +
                ", staffName='" + staffName + '\'' +
                ", productId='" + productId + '\'' +
                ", enrolmentDataId=" + enrolmentDataId +
                ", cancellationDataId=" + cancellationDataId +
                ", contractId=" + contractId +
                ", serviceId=" + serviceId +
                ", fpCoachEnrolmentDataId=" + fpCoachEnrolmentDataId +
                '}';
    }
}
