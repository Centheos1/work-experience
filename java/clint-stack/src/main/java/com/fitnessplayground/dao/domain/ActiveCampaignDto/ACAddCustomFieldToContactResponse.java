package com.fitnessplayground.dao.domain.ActiveCampaignDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ACAddCustomFieldToContactResponse {

    @JsonProperty("contacts")
    private ArrayList<ACContact> contacts;
    @JsonProperty("fieldValue")
    private ACFieldValue acFieldValue;

    public ACAddCustomFieldToContactResponse() {
    }

    public ArrayList<ACContact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<ACContact> contacts) {
        this.contacts = contacts;
    }

    public ACFieldValue getAcFieldValue() {
        return acFieldValue;
    }

    public void setAcFieldValue(ACFieldValue acFieldValue) {
        this.acFieldValue = acFieldValue;
    }

    @Override
    public String toString() {
        return "ACAddCustomFieldToContactResponse{" +
                "contacts=" + contacts +
                ", acFieldValue=" + acFieldValue +
                '}';
    }
}
