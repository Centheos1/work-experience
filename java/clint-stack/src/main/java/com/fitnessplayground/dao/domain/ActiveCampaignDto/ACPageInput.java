package com.fitnessplayground.dao.domain.ActiveCampaignDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ACPageInput {

    @JsonProperty("segmentid")
    private int segmentid;//":0,
    @JsonProperty("formid")
    private int formid;//":0,
    @JsonProperty("listid")
    private int listid;//:0,
    @JsonProperty("tagid")
    private int tagid;// 0,
    @JsonProperty("limit")
    private int limit;//:20,
    @JsonProperty("offset")
    private int offset;// 0,
    @JsonProperty("search")
    private String search;//null,
    @JsonProperty("sort")
    private String sort;//null,
    @JsonProperty("seriesid")
    private int seriesid;////:0,
    @JsonProperty("waitid")
    private int waitid;//:0,
    @JsonProperty("status")
    private int status;//-1,
    @JsonProperty("forceQuery")
    private int forceQuery;//0,
    @JsonProperty("cacheid")
    private String cacheid;//"895202850f4ca4144513c0962812f951"

    public ACPageInput() {
    }

    public int getSegmentid() {
        return segmentid;
    }

    public void setSegmentid(int segmentid) {
        this.segmentid = segmentid;
    }

    public int getFormid() {
        return formid;
    }

    public void setFormid(int formid) {
        this.formid = formid;
    }

    public int getListid() {
        return listid;
    }

    public void setListid(int listid) {
        this.listid = listid;
    }

    public int getTagid() {
        return tagid;
    }

    public void setTagid(int tagid) {
        this.tagid = tagid;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getSeriesid() {
        return seriesid;
    }

    public void setSeriesid(int seriesid) {
        this.seriesid = seriesid;
    }

    public int getWaitid() {
        return waitid;
    }

    public void setWaitid(int waitid) {
        this.waitid = waitid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getForceQuery() {
        return forceQuery;
    }

    public void setForceQuery(int forceQuery) {
        this.forceQuery = forceQuery;
    }

    public String getCacheid() {
        return cacheid;
    }

    public void setCacheid(String cacheid) {
        this.cacheid = cacheid;
    }

    @Override
    public String toString() {
        return "ACPageInput{" +
                "segmentid=" + segmentid +
                ", formid=" + formid +
                ", listid=" + listid +
                ", tagid=" + tagid +
                ", limit=" + limit +
                ", offset=" + offset +
                ", search='" + search + '\'' +
                ", sort='" + sort + '\'' +
                ", seriesid=" + seriesid +
                ", waitid=" + waitid +
                ", status=" + status +
                ", forceQuery=" + forceQuery +
                ", cacheid='" + cacheid + '\'' +
                '}';
    }
}
