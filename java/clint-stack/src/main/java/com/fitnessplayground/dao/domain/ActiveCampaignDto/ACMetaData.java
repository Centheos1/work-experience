package com.fitnessplayground.dao.domain.ActiveCampaignDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ACMetaData {

    @JsonProperty("total")
    private String total;//"2",
    @JsonProperty("page_input")
    private ACPageInput page_input;//{

    public ACMetaData() {
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ACPageInput getPage_input() {
        return page_input;
    }

    public void setPage_input(ACPageInput page_input) {
        this.page_input = page_input;
    }

    @Override
    public String toString() {
        return "ACMetaData{" +
                "total='" + total + '\'' +
                ", page_input=" + page_input +
                '}';
    }
}
