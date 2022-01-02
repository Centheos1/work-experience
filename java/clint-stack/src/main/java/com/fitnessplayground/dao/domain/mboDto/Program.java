package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Program {

    @JsonProperty("Id")
    private long programId;// 24,
    @JsonProperty("Name")
    private String name;//"Mindbody & Athletica (Play)",
    @JsonProperty("ScheduleType")
    private String scheduleType;// "Class",
    @JsonProperty("CancelOffset")
    private int cancelOffset;// 0

    public Program() {
    }

    public long getProgramId() {
        return programId;
    }

    public void setProgramId(long programId) {
        this.programId = programId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public int getCancelOffset() {
        return cancelOffset;
    }

    public void setCancelOffset(int cancelOffset) {
        this.cancelOffset = cancelOffset;
    }

    @Override
    public String toString() {
        return "Program{" +
                "programId=" + programId +
                ", name='" + name + '\'' +
                ", scheduleType='" + scheduleType + '\'' +
                ", cancelOffset=" + cancelOffset +
                '}';
    }
}
