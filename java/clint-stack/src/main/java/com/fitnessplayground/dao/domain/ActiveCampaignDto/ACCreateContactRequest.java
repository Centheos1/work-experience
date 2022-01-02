package com.fitnessplayground.dao.domain.ActiveCampaignDto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ACCreateContactRequest {

    @JsonProperty("contact")
    private ACCreateContactRequestBody body;

    public ACCreateContactRequest() {
    }

    public ACCreateContactRequest(ACCreateContactRequestBody body) {
        this.body = body;
    }

    public ACCreateContactRequestBody getBody() {
        return body;
    }

    public void setBody(ACCreateContactRequestBody body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "ACCreateContactRequest{" +
                "body=" + body +
                '}';
    }
}
