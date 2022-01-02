package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SessionType {

    @JsonProperty("Type")
    private String type;// "All",
    @JsonProperty("DefaultTimeLength")
    private String defaultTimeLength;// null,
    @JsonProperty("Id")
    private  Integer Id;// 18,
    @JsonProperty("Name")
    private String name;// "Athletica Strength",
    @JsonProperty("NumDeducted")
    private Integer numDeducted;// 1,
    @JsonProperty("ProgramId")
    private Integer programId;// 24

    public SessionType() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDefaultTimeLength() {
        return defaultTimeLength;
    }

    public void setDefaultTimeLength(String defaultTimeLength) {
        this.defaultTimeLength = defaultTimeLength;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumDeducted() {
        return numDeducted;
    }

    public void setNumDeducted(Integer numDeducted) {
        this.numDeducted = numDeducted;
    }

    public Integer getProgramId() {
        return programId;
    }

    public void setProgramId(Integer programId) {
        this.programId = programId;
    }

    @Override
    public String toString() {
        return "SessionType{" +
                "type='" + type + '\'' +
                ", defaultTimeLength='" + defaultTimeLength + '\'' +
                ", Id=" + Id +
                ", name='" + name + '\'' +
                ", numDeducted=" + numDeducted +
                ", programId=" + programId +
                '}';
    }
}
