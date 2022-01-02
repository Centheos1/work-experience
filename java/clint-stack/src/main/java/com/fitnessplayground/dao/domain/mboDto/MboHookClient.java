package com.fitnessplayground.dao.domain.mboDto;

public class MboHookClient {

    private String messageId; // "AVVqMDbCFfgvKskdNtBkQa",
    private String eventId; // "client.updated"
    private String eventSchemaVersion; // 1.0
    private String eventInstanceOriginationDateTime; // "2020-05-23T03:12:09Z"
    private EventDataClient eventData;

    public MboHookClient() {
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

    public String getEventSchemaVersion() {
        return eventSchemaVersion;
    }

    public void setEventSchemaVersion(String eventSchemaVersion) {
        this.eventSchemaVersion = eventSchemaVersion;
    }

    public String getEventInstanceOriginationDateTime() {
        return eventInstanceOriginationDateTime;
    }

    public void setEventInstanceOriginationDateTime(String eventInstanceOriginationDateTime) {
        this.eventInstanceOriginationDateTime = eventInstanceOriginationDateTime;
    }

    public EventDataClient getEventData() {
        return eventData;
    }

    public void setEventData(EventDataClient eventData) {
        this.eventData = eventData;
    }

    @Override
    public String toString() {
        return "MboHookClient{" +
                "messageId='" + messageId + '\'' +
                ", eventId='" + eventId + '\'' +
                ", eventSchemaVersion='" + eventSchemaVersion + '\'' +
                ", eventInstanceOriginationDateTime='" + eventInstanceOriginationDateTime + '\'' +
                ", eventData=" + eventData +
                '}';
    }
}
