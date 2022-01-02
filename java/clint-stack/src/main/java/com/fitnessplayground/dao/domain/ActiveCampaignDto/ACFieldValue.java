package com.fitnessplayground.dao.domain.ActiveCampaignDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ACFieldValue {

    @JsonProperty("contact")
    private Long contactId;
    @JsonProperty("field")
    private Integer fieldId;
    @JsonProperty("value")
    private String value;
    @JsonProperty("cdate")
    private String cdate;
    @JsonProperty("udate")
    private String udate;

    public ACFieldValue() {
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

    public String getUdate() {
        return udate;
    }

    public void setUdate(String udate) {
        this.udate = udate;
    }

    @Override
    public String toString() {
        return "ACFieldValue{" +
                "contactId=" + contactId +
                ", fieldId=" + fieldId +
                ", value='" + value + '\'' +
                ", cdate='" + cdate + '\'' +
                ", udate='" + udate + '\'' +
                '}';
    }
}
