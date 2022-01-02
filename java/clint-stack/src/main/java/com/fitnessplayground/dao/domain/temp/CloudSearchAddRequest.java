package com.fitnessplayground.dao.domain.temp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fitnessplayground.dao.domain.CloudSearch;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CloudSearchAddRequest {

    @JsonProperty("members")
    private ArrayList<CloudSearch> members;

    public CloudSearchAddRequest() {
    }

    public ArrayList<CloudSearch> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<CloudSearch> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "CloudSearchAddRequest{" +
                "members=" + members +
                '}';
    }
}
