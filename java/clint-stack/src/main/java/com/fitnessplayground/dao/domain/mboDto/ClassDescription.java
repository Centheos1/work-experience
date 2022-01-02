package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClassDescription {

    @JsonProperty("Active")
    private Boolean active;// true,
    @JsonProperty("Description")
    private String Description;// <div>An innovative and highly effective group training session, dedicated to increasing strength and improving body composition with weights based exercises and challenges. Tone up and get stronger.</div>",
    @JsonProperty("Id")
    private Integer classDescriptionId;// 4,
    @JsonProperty("ImageURL")
    private String imageURL;// null,
    @JsonProperty("LastUpdated")
    private String lastUpdated;// "2014-05-25T15:01:28.23",
    @JsonProperty("Level")
    private String level;// null,
    @JsonProperty("Name")
    private String name;// "ATHLETICA STRENGTH (play)",
    @JsonProperty("Notes")
    private String notes;// "",
    //    @JsonProperty("")
//     "Prereq": "",
    @JsonProperty("")
    private Program program;

    public ClassDescription() {
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Integer getClassDescriptionId() {
        return classDescriptionId;
    }

    public void setClassDescriptionId(Integer classDescriptionId) {
        this.classDescriptionId = classDescriptionId;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    @Override
    public String toString() {
        return "ClassDescription{" +
                "active=" + active +
                ", Description='" + Description + '\'' +
                ", classDescriptionId=" + classDescriptionId +
                ", imageURL='" + imageURL + '\'' +
                ", lastUpdated='" + lastUpdated + '\'' +
                ", level='" + level + '\'' +
                ", name='" + name + '\'' +
                ", notes='" + notes + '\'' +
                ", program=" + program +
                '}';
    }
}
