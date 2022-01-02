package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MboGetClientContractsResponse {

    @JsonProperty("PaginationResponse")
    private PaginationResponse paginationResponse;
    @JsonProperty("Contracts")
    private ArrayList<ClientContract> clientContracts;

    public MboGetClientContractsResponse() {
    }

    public PaginationResponse getPaginationResponse() {
        return paginationResponse;
    }

    public void setPaginationResponse(PaginationResponse paginationResponse) {
        this.paginationResponse = paginationResponse;
    }

    public ArrayList<ClientContract> getClientContracts() {
        return clientContracts;
    }

    public void setClientContracts(ArrayList<ClientContract> clientContracts) {
        this.clientContracts = clientContracts;
    }

    @Override
    public String toString() {
        return "MboGetClientContractsResponse{" +
                "paginationResponse=" + paginationResponse +
                ", clientContracts=" + clientContracts +
                '}';
    }
}
