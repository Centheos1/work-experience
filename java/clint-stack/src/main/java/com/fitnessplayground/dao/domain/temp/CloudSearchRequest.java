package com.fitnessplayground.dao.domain.temp;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CloudSearchRequest {

    @JsonProperty("query")
    private String query;

    public CloudSearchRequest() {
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public String toString() {
        return "CloudSearchRequest{" +
                "query='" + query + '\'' +
                '}';
    }
}
