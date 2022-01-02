package com.fitnessplayground.dao.domain.fpSourceDto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FPGetDigitalPreExResponse {

    @JsonProperty("DigitalPreExData")
    private DigitalPreExData digitalPreExData;

    public FPGetDigitalPreExResponse() {
    }

    public DigitalPreExData getDigitalPreExData() {
        return digitalPreExData;
    }

    public void setDigitalPreExData(DigitalPreExData digitalPreExData) {
        this.digitalPreExData = digitalPreExData;
    }

    @Override
    public String toString() {
        return "FPGetDigitalPreExResponse{" +
                "digitalPreExData=" + digitalPreExData +
                '}';
    }
}
