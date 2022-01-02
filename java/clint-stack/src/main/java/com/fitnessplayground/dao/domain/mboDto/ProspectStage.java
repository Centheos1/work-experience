package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProspectStage {

    @JsonProperty("ID")
    private Integer prospectId;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("Active")
    private boolean active;

    public ProspectStage() {
    }

    public Integer getProspectId() {
        return prospectId;
    }

    public void setProspectId(Integer prospectId) {
        this.prospectId = prospectId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "ProspectStage{" +
                "prospectId=" + prospectId +
                ", description='" + description + '\'' +
                ", active=" + active +
                '}';
    }
}
