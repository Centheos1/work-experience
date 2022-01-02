package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class MboGetLocationsResponse {


    @JsonProperty("PaginationResponse")
    PaginationResponse paginationResponse;
    @JsonProperty("Locations")
    ArrayList<Location> locations;

    public MboGetLocationsResponse() {
    }

    public PaginationResponse getPaginationResponse() {
        return paginationResponse;
    }

    public void setPaginationResponse(PaginationResponse paginationResponse) {
        this.paginationResponse = paginationResponse;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }

    @Override
    public String toString() {
        return "MboGetLocationsResponse{" +
                "paginationResponse=" + paginationResponse +
                ", locations=" + locations +
                '}';
    }
}
