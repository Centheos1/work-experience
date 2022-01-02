package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MboServiceResponse {

    @JsonProperty("PaginationResponse")
    private PaginationResponse paginationResponse;
    @JsonProperty("Services")
    private ArrayList<Service> services;

    public MboServiceResponse() {
    }

    public PaginationResponse getPaginationResponse() {
        return paginationResponse;
    }

    public void setPaginationResponse(PaginationResponse paginationResponse) {
        this.paginationResponse = paginationResponse;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }

    @Override
    public String toString() {
        return "MboServiceResponse{" +
                "paginationResponse=" + paginationResponse +
                ", services=" + services +
                '}';
    }
}
