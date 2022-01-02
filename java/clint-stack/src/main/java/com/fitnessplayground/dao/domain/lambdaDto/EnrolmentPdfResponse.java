package com.fitnessplayground.dao.domain.lambdaDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrolmentPdfResponse {

    @JsonProperty("message")
    private String message;

    public EnrolmentPdfResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "EnrolmentPdfResponse{" +
                "message='" + message + '\'' +
                '}';
    }
}
