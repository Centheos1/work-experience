package com.fitnessplayground.dao.domain.fpSourceDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FPReassignPersonalTrainerRequest {

    @JsonProperty("handshakeKey")
    private String handshakeKey;
    @JsonProperty("ACID")
    private String ACID;
    @JsonProperty("trainerName")
    private String trainerName;
    @JsonProperty("trainerFirstName")
    private String trainerFirstName;
    @JsonProperty("trainerLastName")
    private String trainerLastName;
    @JsonProperty("trainerEmail")
    private String trainerEmail;
    @JsonProperty("trainerPhone")
    private String trainerPhone;

    public FPReassignPersonalTrainerRequest() {
    }

    public String getHandshakeKey() {
        return handshakeKey;
    }

    public void setHandshakeKey(String handshakeKey) {
        this.handshakeKey = handshakeKey;
    }

    public String getACID() {
        return ACID;
    }

    public void setACID(String ACID) {
        this.ACID = ACID;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public String getTrainerFirstName() {
        return trainerFirstName;
    }

    public void setTrainerFirstName(String trainerFirstName) {
        this.trainerFirstName = trainerFirstName;
    }

    public String getTrainerLastName() {
        return trainerLastName;
    }

    public void setTrainerLastName(String trainerLastName) {
        this.trainerLastName = trainerLastName;
    }

    public String getTrainerEmail() {
        return trainerEmail;
    }

    public void setTrainerEmail(String trainerEmail) {
        this.trainerEmail = trainerEmail;
    }

    public String getTrainerPhone() {
        return trainerPhone;
    }

    public void setTrainerPhone(String trainerPhone) {
        this.trainerPhone = trainerPhone;
    }

    @Override
    public String toString() {
        return "FPReassignPersonalTrainerRequest{" +
                "handshakeKey='" + handshakeKey + '\'' +
                ", ACID='" + ACID + '\'' +
                ", trainerName='" + trainerName + '\'' +
                ", trainerEmail='" + trainerEmail + '\'' +
                ", trainerPhone='" + trainerPhone + '\'' +
                '}';
    }
}
