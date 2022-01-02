package com.fitnessplayground.dao.domain.ActiveCampaignDto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ACAddTagToContactResponse {

    @JsonProperty("contactTag")
    private ACContactTag contactTag;

    public ACAddTagToContactResponse() {
    }

    public ACContactTag getContactTag() {
        return contactTag;
    }

    public void setContactTag(ACContactTag contactTag) {
        this.contactTag = contactTag;
    }

    @Override
    public String toString() {
        return "ACAddTagToContactResponse{" +
                "contactTag=" + contactTag +
                '}';
    }
}
