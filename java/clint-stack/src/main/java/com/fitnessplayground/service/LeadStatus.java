package com.fitnessplayground.service;

public enum LeadStatus {

    RECEIVED("RECEIVED", "Submission has been received",102),
    SAVED("SAVED", "Data has been saved to the database",102),
    EMAIL_CAMPAIGN_TRIGGERED("EMAIL_CAMPAIGN_TRIGGERED", "Communications has been triggered", 200),
    PROCESSED("PROCESSED", "Lead has been recieved and added to lead pipeline", 200),
    CONVERSION("CONVERSION", "Purchase has been received", 200),
    ERROR("ERROR", "Error has occured", 401);

    private String status;
    private String description;
    private Integer statusCode;

    LeadStatus(String status, String description, Integer statusCode) {
        this.status = status;
        this.description = description;
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
