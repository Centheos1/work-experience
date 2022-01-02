package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class MboGetSessionTypesResponse {

    @JsonProperty("PaginationResponse")
    PaginationResponse paginationResponse;
    @JsonProperty("SessionTypes")
    ArrayList<SessionType> sessionTypes;

    public PaginationResponse getPaginationResponse() {
        return paginationResponse;
    }

    public void setPaginationResponse(PaginationResponse paginationResponse) {
        this.paginationResponse = paginationResponse;
    }

    public ArrayList<SessionType> getSessionTypes() {
        return sessionTypes;
    }

    public void setSessionTypes(ArrayList<SessionType> sessionTypes) {
        this.sessionTypes = sessionTypes;
    }

    @Override
    public String toString() {
        return "MboGetSessionTypesResponse{" +
                "paginationResponse=" + paginationResponse +
                ", sessionTypes=" + sessionTypes +
                '}';
    }
}
