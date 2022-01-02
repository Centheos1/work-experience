package com.fitnessplayground.dao.domain.mboDto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MboHookClientContract {

    @JsonProperty("messageId")
    private String messageId; // KzJVvQTVeAhonahPr1LfyY",
    @JsonProperty("eventId")
    private String eventId; // clientContract.updated",
    @JsonProperty("eventSchemaVersion")
    private Integer eventSchemaVersion; // 1.0,
    @JsonProperty("eventInstanceOriginationDateTime")
    private String eventInstanceOriginationDateTime; // "2020-05-21T07:54:24Z",
    @JsonProperty("eventData")
    private EventDataClientContract eventData;

    public MboHookClientContract() {
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Integer getEventSchemaVersion() {
        return eventSchemaVersion;
    }

    public void setEventSchemaVersion(Integer eventSchemaVersion) {
        this.eventSchemaVersion = eventSchemaVersion;
    }

    public String getEventInstanceOriginationDateTime() {
        return eventInstanceOriginationDateTime;
    }

    public void setEventInstanceOriginationDateTime(String eventInstanceOriginationDateTime) {
        this.eventInstanceOriginationDateTime = eventInstanceOriginationDateTime;
    }

    public EventDataClientContract getEventData() {
        return eventData;
    }

    public void setEventData(EventDataClientContract eventData) {
        this.eventData = eventData;
    }

    @Override
    public String toString() {
        return "MboHookClientContract{" +
                "messageId='" + messageId + '\'' +
                ", eventId='" + eventId + '\'' +
                ", eventSchemaVersion=" + eventSchemaVersion +
                ", eventInstanceOriginationDateTime='" + eventInstanceOriginationDateTime + '\'' +
                ", eventData=" + eventData +
                '}';
    }
}
