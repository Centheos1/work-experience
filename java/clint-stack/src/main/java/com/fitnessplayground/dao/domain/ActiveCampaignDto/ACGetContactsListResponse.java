package com.fitnessplayground.dao.domain.ActiveCampaignDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ACGetContactsListResponse {

    @JsonProperty("contacts")
    private ArrayList<ACContact> contacts;
    @JsonProperty("meta")
    private ACMetaData metaData;

    public ACGetContactsListResponse() {
    }

    public ArrayList<ACContact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<ACContact> contacts) {
        this.contacts = contacts;
    }

    public ACMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(ACMetaData metaData) {
        this.metaData = metaData;
    }

    @Override
    public String toString() {
        return "ACGetContactsListResponse{" +
                "contacts=" + contacts +
                ", metaData=" + metaData +
                '}';
    }
}
