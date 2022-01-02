package com.fitnessplayground.model.mindbody.api.result;

import com.fitnessplayground.dao.domain.EnrolmentData;

public class MboApiResultEnrolmentData {
    private Boolean success;
    private String mindBodyId;
    private EnrolmentData enrolmentData;
    private String errorMessage;
    private Integer errorCode;

    public MboApiResultEnrolmentData(Boolean success, String mindBodyId, EnrolmentData enrolmentData, String errorMessage, Integer errorCode) {
        this.success = success;
        this.mindBodyId = mindBodyId;
        this.enrolmentData = enrolmentData;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }


    public String getMindBodyId() {
        return mindBodyId;
    }

    public void setMindBodyId(String mindBodyId) {
        this.mindBodyId = mindBodyId;
    }

    public EnrolmentData getEnrolmentData() {
        return enrolmentData;
    }

    public void setEnrolmentData(EnrolmentData enrolmentData) {
        this.enrolmentData = enrolmentData;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "MboApiResultEnrolmentData{" +
                "success=" + success +
                ", mindBodyId='" + mindBodyId + '\'' +
                ", enrolmentData=" + enrolmentData +
                ", errorMessage='" + errorMessage + '\'' +
                ", errorCode=" + errorCode +
                '}';
    }
}
