package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MboUpdateClientRequest {

    @JsonProperty("Client")
    private Client client;
    @JsonProperty("CrossRegionalUpdate")
    private Boolean crossRegionalUpdate;
    @JsonProperty("NewId")
    private String newId;
    @JsonProperty("Test")
    private Boolean isTestSubmission;

    public MboUpdateClientRequest() {
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Boolean getCrossRegionalUpdate() {
        return crossRegionalUpdate;
    }

    public void setCrossRegionalUpdate(Boolean crossRegionalUpdate) {
        this.crossRegionalUpdate = crossRegionalUpdate;
    }

    public String getNewId() {
        return newId;
    }

    public void setNewId(String newId) {
        this.newId = newId;
    }

    public Boolean getTestSubmission() {
        return isTestSubmission;
    }

    public void setTestSubmission(Boolean testSubmission) {
        isTestSubmission = testSubmission;
    }

    @Override
    public String toString() {
        return "MboUpdateClientRequest{" +
                "client=" + client +
                ", crossRegionalUpdate=" + crossRegionalUpdate +
                ", newId='" + newId + '\'' +
                ", isTestSubmission=" + isTestSubmission +
                '}';
    }
}
