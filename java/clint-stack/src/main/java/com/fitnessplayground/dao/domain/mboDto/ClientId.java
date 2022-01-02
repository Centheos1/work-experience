package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientId {

    @JsonProperty("Id")
    private String id;

    public ClientId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ClientId{" +
                "id='" + id + '\'' +
                '}';
    }
}
