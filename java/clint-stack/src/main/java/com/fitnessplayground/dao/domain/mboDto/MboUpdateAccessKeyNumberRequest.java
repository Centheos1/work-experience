package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MboUpdateAccessKeyNumberRequest {

    @JsonProperty("Client")
    private ClientId clientId;
    @JsonProperty("CrossRegionalUpdate")
    private Boolean crossResionalUpdate;
    @JsonProperty("Test")
    private Boolean isTestSubmission;
    @JsonProperty("NewId")
    private String updatedAccessKeyNumber;

    public MboUpdateAccessKeyNumberRequest() {
    }

    public ClientId getClientId() {
        return clientId;
    }

    public void setClientId(ClientId clientId) {
        this.clientId = clientId;
    }

    public Boolean getCrossResionalUpdate() {
        return crossResionalUpdate;
    }

    public void setCrossResionalUpdate(Boolean crossResionalUpdate) {
        this.crossResionalUpdate = crossResionalUpdate;
    }

    public Boolean getTestSubmission() {
        return isTestSubmission;
    }

    public void setTestSubmission(Boolean testSubmission) {
        isTestSubmission = testSubmission;
    }

    public String getUpdatedAccessKeyNumber() {
        return updatedAccessKeyNumber;
    }

    public void setUpdatedAccessKeyNumber(String updatedAccessKeyNumber) {
        this.updatedAccessKeyNumber = updatedAccessKeyNumber;
    }

    @Override
    public String toString() {
        return "MboUpdateAccessKeyNumberRequest{" +
                "clientId=" + clientId +
                ", crossResionalUpdate=" + crossResionalUpdate +
                ", isTestSubmission=" + isTestSubmission +
                ", updatedAccessKeyNumber='" + updatedAccessKeyNumber + '\'' +
                '}';
    }
}
