package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MboContractResponse {

    @JsonProperty("PaginationResponse")
    PaginationResponse paginationResponse;
    @JsonProperty("Contracts")
    ArrayList<Contract> contracts;

    public MboContractResponse() {
    }

    public PaginationResponse getPaginationResponse() {
        return paginationResponse;
    }

    public void setPaginationResponse(PaginationResponse paginationResponse) {
        this.paginationResponse = paginationResponse;
    }

    public ArrayList<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(ArrayList<Contract> contracts) {
        this.contracts = contracts;
    }

    @Override
    public String toString() {
        return "MboContractResponse{" +
                "paginationResponse=" + paginationResponse +
                ", contracts=" + contracts +
                '}';
    }
}
