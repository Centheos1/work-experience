package com.fitnessplayground.dao.domain.ActiveCampaignDto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ACAddCustomFieldToContactRequest {

    @JsonProperty("fieldValue")
    private ACAddCustomFieldToContactRequestBody body;

    public ACAddCustomFieldToContactRequest() {
    }

    public ACAddCustomFieldToContactRequest(ACAddCustomFieldToContactRequestBody body) {
        this.body = body;
    }

    public ACAddCustomFieldToContactRequestBody getBody() {
        return body;
    }

    public void setBody(ACAddCustomFieldToContactRequestBody body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "ACAddCustomFieldToContactRequest{" +
                "body=" + body +
                '}';
    }
}
