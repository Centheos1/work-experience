package com.fitnessplayground.dao.domain.ActiveCampaignDto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ACAddTagToContactRequestBody {

    @JsonProperty("contact")
    private String contactId;
    @JsonProperty("tag")
    private String tagId;

    public ACAddTagToContactRequestBody() {
    }

    public ACAddTagToContactRequestBody(String contactId, String tagId) {
        this.contactId = contactId;
        this.tagId = tagId;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    @Override
    public String toString() {
        return "ACAddTagToContactRequestBody{" +
                "contactId='" + contactId + '\'' +
                ", tagId='" + tagId + '\'' +
                '}';
    }
}
