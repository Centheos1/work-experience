package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MboGetClientResponse {

    @JsonProperty("PaginationResponse")
    PaginationResponse paginationResponse;
    @JsonProperty("Clients")
    ArrayList<Client> clients;

    public MboGetClientResponse() {
    }

    public PaginationResponse getPaginationResponse() {
        return paginationResponse;
    }

    public void setPaginationResponse(PaginationResponse paginationResponse) {
        this.paginationResponse = paginationResponse;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }

    @Override
    public String toString() {
        return "MboGetClientResponse{" +
                "paginationResponse=" + paginationResponse +
                ", clients=" + clients +
                '}';
    }
}
