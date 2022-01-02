package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MboUpdateClientResponse {

    @JsonProperty("Client")
    private Client client;

    public MboUpdateClientResponse() {
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "MboUpdateClientResponse{" +
                "client=" + client +
                '}';
    }
}
