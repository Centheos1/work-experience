package com.fitnessplayground.dao.domain.ActiveCampaignDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ACWebhookContactBody {

    @JsonProperty("type")
    private String type;
    @JsonProperty("date_time")
    private String date_time;
    @JsonProperty("contact")
    private ACWebhookContact contact;

    public ACWebhookContactBody() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public ACWebhookContact getContact() {
        return contact;
    }

    public void setContact(ACWebhookContact contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "ACWebhookContactBody{" +
                "type='" + type + '\'' +
                ", date_time='" + date_time + '\'' +
                ", contact=" + contact +
                '}';
    }
}
