package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MboRequestItem {

    @JsonProperty("Type")
    private String type;// "Product",
    @JsonProperty("MetaData")
    private MboMetaData metadata;//

    public MboRequestItem() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MboMetaData getMetadata() {
        return metadata;
    }

    public void setMetadata(MboMetaData metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "MboRequestItem{" +
                "type='" + type + '\'' +
                ", metadata=" + metadata +
                '}';
    }
}
