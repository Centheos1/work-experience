package com.fitnessplayground.dao.domain.ActiveCampaignDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ACGetAllTagsResponse {

    @JsonProperty("tags")
    private ArrayList<ACTag> tags;
    @JsonProperty("meta")
    private ACMetaData metaData;

    public ACGetAllTagsResponse() {
    }

    public ArrayList<ACTag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<ACTag> tags) {
        this.tags = tags;
    }

    public ACMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(ACMetaData metaData) {
        this.metaData = metaData;
    }

    @Override
    public String toString() {
        return "ACGetAllTagsResponse{" +
                "tags=" + tags +
                ", metaData=" + metaData +
                '}';
    }
}
