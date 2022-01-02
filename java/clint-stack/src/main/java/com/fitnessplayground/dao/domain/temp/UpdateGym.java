package com.fitnessplayground.dao.domain.temp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateGym {

    @JsonProperty("locationId")
    private String locationId;
    @JsonProperty("clubManagerMboId")
    private String clubManagerMboId;
    @JsonProperty("personalTrainingManagerMboId")
    private String personalTrainerManagerMboId;
    @JsonProperty("groupFitnessManagerMboId")
    private String groupFitnessManagerMboId;

    public UpdateGym() {
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getClubManagerMboId() {
        return clubManagerMboId;
    }

    public void setClubManagerMboId(String clubManagerMboId) {
        this.clubManagerMboId = clubManagerMboId;
    }

    public String getPersonalTrainerManagerMboId() {
        return personalTrainerManagerMboId;
    }

    public void setPersonalTrainerManagerMboId(String personalTrainerManagerMboId) {
        this.personalTrainerManagerMboId = personalTrainerManagerMboId;
    }

    public String getGroupFitnessManagerMboId() {
        return groupFitnessManagerMboId;
    }

    public void setGroupFitnessManagerMboId(String groupFitnessManagerMboId) {
        this.groupFitnessManagerMboId = groupFitnessManagerMboId;
    }

    @Override
    public String toString() {
        return "UpdateGym{" +
                "locationId='" + locationId + '\'' +
                ", clubManagerMboId='" + clubManagerMboId + '\'' +
//                ", personalTrainerManagerMboId='" + personalTrainerManagerMboId + '\'' +
//                ", groupFitnessManagerMboId='" + groupFitnessManagerMboId + '\'' +
                '}';
    }
}

