package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AutopaySchedule {

    @JsonProperty("FrequencyType")
    private String frequencyType;
    @JsonProperty("FrequencyValue")
    private int frequencyValue;
    @JsonProperty("FrequencyTimeUnit")
    private String frequencyTimeUnit;

    public AutopaySchedule() {
    }

    public String getFrequencyType() {
        return frequencyType;
    }

    public void setFrequencyType(String frequencyType) {
        this.frequencyType = frequencyType;
    }

    public int getFrequencyValue() {
        return frequencyValue;
    }

    public void setFrequencyValue(int frequencyValue) {
        this.frequencyValue = frequencyValue;
    }

    public String getFrequencyTimeUnit() {
        return frequencyTimeUnit;
    }

    public void setFrequencyTimeUnit(String frequencyTimeUnit) {
        this.frequencyTimeUnit = frequencyTimeUnit;
    }

    @Override
    public String toString() {
        return "AutopaySchedule{" +
                "frequencyType='" + frequencyType + '\'' +
                ", frequencyValue=" + frequencyValue +
                ", frequencyTimeUnit='" + frequencyTimeUnit + '\'' +
                '}';
    }
}
