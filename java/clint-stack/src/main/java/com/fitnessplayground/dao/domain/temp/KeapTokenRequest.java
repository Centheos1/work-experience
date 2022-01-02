package com.fitnessplayground.dao.domain.temp;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KeapTokenRequest {

    @JsonProperty("refresh_token")
    private String refreshtoken;

    public KeapTokenRequest(String refreshtoken) {
        this.refreshtoken = refreshtoken;
    }

    public String getRefreshtoken() {
        return refreshtoken;
    }

    public void setRefreshtoken(String refreshtoken) {
        this.refreshtoken = refreshtoken;
    }

    @Override
    public String toString() {
        return "KeapTokenRequest{" +
                "refreshtoken='" + refreshtoken + '\'' +
                '}';
    }
}
