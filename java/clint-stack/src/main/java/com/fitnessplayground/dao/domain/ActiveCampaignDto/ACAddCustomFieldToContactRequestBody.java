package com.fitnessplayground.dao.domain.ActiveCampaignDto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ACAddCustomFieldToContactRequestBody {

    @JsonProperty("contact")
    private Long contactId;
    @JsonProperty("field")
    private Integer field;
    @JsonProperty("value")
    private String value;

    public ACAddCustomFieldToContactRequestBody() {
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Integer getField() {
        return field;
    }

    public void setField(Integer field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ACAddCustomFieldToContactRequestBody{" +
                "contactId=" + contactId +
                ", field=" + field +
                ", value='" + value + '\'' +
                '}';
    }
}
