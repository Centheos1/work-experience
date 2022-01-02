package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaginationResponse {

    @JsonProperty("RequestedLimit")
    private int requestLimit;
    @JsonProperty("RequestedOffset")
    private int requestLimitOffset;
    @JsonProperty("PageSize")
    private int pageSize;
    @JsonProperty("TotalResults")
    private int totalResults;

    public PaginationResponse() {
    }

    public int getRequestLimit() {
        return requestLimit;
    }

    public void setRequestLimit(int requestLimit) {
        this.requestLimit = requestLimit;
    }

    public int getRequestLimitOffset() {
        return requestLimitOffset;
    }

    public void setRequestLimitOffset(int requestLimitOffset) {
        this.requestLimitOffset = requestLimitOffset;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    @Override
    public String toString() {
        return "PaginationResponse{" +
                "requestLimit=" + requestLimit +
                ", requestLimitOffset=" + requestLimitOffset +
                ", pageSize=" + pageSize +
                ", totalResults=" + totalResults +
                '}';
    }
}
