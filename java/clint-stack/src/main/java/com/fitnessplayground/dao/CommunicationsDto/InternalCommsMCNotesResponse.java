package com.fitnessplayground.dao.CommunicationsDto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class InternalCommsMCNotesResponse {

    @JsonProperty("data")
    private List<MCNotesData> data;

    public InternalCommsMCNotesResponse() {
    }

    public InternalCommsMCNotesResponse(List<MCNotesData> data) {
        this.data = data;
    }

    public List<MCNotesData> getData() {
        return data;
    }

    public void setData(List<MCNotesData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "InternalCommsMCNotesResponse{" +
                "data=" + data +
                '}';
    }
}
