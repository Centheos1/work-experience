package com.fitnessplayground.dao.domain.ActiveCampaignDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ACTag {

    @JsonProperty("tagType")
    private String tagType;// "contact",
    @JsonProperty("tag")
    private String tag;// "Opened 2 weeks free Bunker Email",
    @JsonProperty("description")
    private String description;// "",
    @JsonProperty("subscriber_count")
    private String subscriberCount;// "1",
    @JsonProperty("cdate")
    private String createDate;// "2019-06-10T22:27:22-05:00",
    @JsonProperty("links")
    private ACLinks links;// {"contactGoalTags": "https://thefitnessplayground.api-us1.com/api/3/tags/79/contactGoalTags"},
    @JsonProperty("id")
    private String tagId;// "79"

    public ACTag() {
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubscriberCount() {
        return subscriberCount;
    }

    public void setSubscriberCount(String subscriberCount) {
        this.subscriberCount = subscriberCount;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public ACLinks getLinks() {
        return links;
    }

    public void setLinks(ACLinks links) {
        this.links = links;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    @Override
    public String toString() {
        return "ACTag{" +
                "tagType='" + tagType + '\'' +
                ", tag='" + tag + '\'' +
                ", description='" + description + '\'' +
                ", subscriberCount='" + subscriberCount + '\'' +
                ", createDate='" + createDate + '\'' +
                ", links=" + links +
                ", tagId='" + tagId + '\'' +
                '}';
    }
}
