package com.fitnessplayground.dao.domain.lambdaDto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TwilioSendSmsRequest {

    @JsonProperty("to_number")
    private String to_number;
    @JsonProperty("message")
    private String message;

    public TwilioSendSmsRequest() {
    }

    public String getTo_number() {
        return to_number;
    }

    public void setTo_number(String to_number) {
        this.to_number = to_number;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "TwilioSendSmsRequest{" +
                "to_number='" + to_number + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
