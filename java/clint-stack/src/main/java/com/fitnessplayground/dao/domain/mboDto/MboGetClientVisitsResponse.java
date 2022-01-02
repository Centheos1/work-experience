package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MboGetClientVisitsResponse {

    @JsonProperty("PaginationResponse")
    PaginationResponse paginationResponse;
    @JsonProperty("Visits")
    ArrayList<Visit> visits;

    public MboGetClientVisitsResponse() {
    }

    public PaginationResponse getPaginationResponse() {
        return paginationResponse;
    }

    public void setPaginationResponse(PaginationResponse paginationResponse) {
        this.paginationResponse = paginationResponse;
    }

    public ArrayList<Visit> getVisits() {
        return visits;
    }

    public void setVisits(ArrayList<Visit> visits) {
        this.visits = visits;
    }

    @Override
    public String toString() {
        return "MboGetClientVisitsResponse{" +
                "paginationResponse=" + paginationResponse +
                ", visits=" + visits +
                '}';
    }
}
