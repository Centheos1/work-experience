package com.fitnessplayground.dao.domain.ActiveCampaignDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ACGetAllCustomFieldsResponse {

    @JsonProperty("fields")
    private ArrayList<ACField> customFields;
    @JsonProperty("meta")
    private ACMetaData metaData;

    public ACGetAllCustomFieldsResponse() {
    }

    public ArrayList<ACField> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(ArrayList<ACField> customFields) {
        this.customFields = customFields;
    }

    public ACMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(ACMetaData metaData) {
        this.metaData = metaData;
    }
}
