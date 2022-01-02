package com.fitnessplayground.dao.domain.ActiveCampaignDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ACCreateContactResponse {

    @JsonProperty("contact")
    private ACContact contact;

    public ACCreateContactResponse() {
    }


    public ACContact getContact() {
        return contact;
    }

    public void setContact(ACContact contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "ACCreateContactResponse{" +
                "contact=" + contact +
                '}';
    }
}
