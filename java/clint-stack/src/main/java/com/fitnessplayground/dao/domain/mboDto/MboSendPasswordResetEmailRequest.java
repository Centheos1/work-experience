package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MboSendPasswordResetEmailRequest {

    @JsonProperty("UserEmail")
    private String userEmail;
    @JsonProperty("UserFirstName")
    private String userFirstName;
    @JsonProperty("UserLastName")
    private String userLastName;

    public MboSendPasswordResetEmailRequest() {
    }

    public MboSendPasswordResetEmailRequest(String userEmail, String userFirstName, String userLastName) {
        this.userEmail = userEmail;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    @Override
    public String toString() {
        return "MboSendPasswordResetEmailRequest{" +
                "userEmail='" + userEmail + '\'' +
                ", userFirstName='" + userFirstName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                '}';
    }
}
