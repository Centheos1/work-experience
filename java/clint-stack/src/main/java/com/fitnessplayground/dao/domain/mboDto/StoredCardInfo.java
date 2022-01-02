package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StoredCardInfo {

    @JsonProperty("LastFour")
    private String lastFour;

    public StoredCardInfo() {
    }

    public String getLastFour() {
        return lastFour;
    }

    public void setLastFour(String lastFour) {
        this.lastFour = lastFour;
    }

    @Override
    public String toString() {
        return "StoredCardInfo{" +
                "lastFour='" + lastFour + '\'' +
                '}';
    }
}
