package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AssignedClientIndex {

    @JsonProperty("Id")
    private Integer indexId;
    @JsonProperty("ValueId")
    private Integer valueId;

    public AssignedClientIndex() {
    }

    public Integer getIndexId() {
        return indexId;
    }

    public void setIndexId(Integer indexId) {
        this.indexId = indexId;
    }

    public Integer getValueId() {
        return valueId;
    }

    public void setValueId(Integer valueId) {
        this.valueId = valueId;
    }

    @Override
    public String toString() {
        return "AssignedClientIndex{" +
                "indexId=" + indexId +
                ", valueId=" + valueId +
                '}';
    }
}
