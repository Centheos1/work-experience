package com.fitnessplayground.dao.domain.ActiveCampaignDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ACContactTag {

    @JsonProperty("cdate")
    private String createDate;//"2017-06-08T16:11:53-05:00",
    @JsonProperty("contact")
    private String contactId;//"1",
    @JsonProperty("id")
    private String contactTagId;//"1",
    @JsonProperty("tag")
    private String tagId;//"20"

    public ACContactTag() {
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getContactTagId() {
        return contactTagId;
    }

    public void setContactTagId(String contactTagId) {
        this.contactTagId = contactTagId;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    @Override
    public String toString() {
        return "ACContractTag{" +
                "createDate='" + createDate + '\'' +
                ", contactId='" + contactId + '\'' +
                ", contactTagId='" + contactTagId + '\'' +
                ", tagId='" + tagId + '\'' +
                '}';
    }
}
