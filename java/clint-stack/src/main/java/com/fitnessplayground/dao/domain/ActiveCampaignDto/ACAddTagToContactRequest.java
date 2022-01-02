package com.fitnessplayground.dao.domain.ActiveCampaignDto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ACAddTagToContactRequest {

    @JsonProperty("contactTag")
    private ACAddTagToContactRequestBody body;

    public ACAddTagToContactRequest() {
    }

    public ACAddTagToContactRequest(ACAddTagToContactRequestBody body) {
        this.body = body;
    }

    public ACAddTagToContactRequestBody getBody() {
        return body;
    }

    public void setBody(ACAddTagToContactRequestBody body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "ACAddTagToContactRequest{" +
                "body=" + body +
                '}';
    }
}
