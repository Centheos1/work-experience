package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Color {

    @JsonProperty("Id")
    private Long mboId;
    @JsonProperty("Name")
    private String name;

    public Color() {
    }

    public Long getMboId() {
        return mboId;
    }

    public void setMboId(Long mboId) {
        this.mboId = mboId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Color{" +
                "mboId=" + mboId +
                ", name='" + name + '\'' +
                '}';
    }
}
