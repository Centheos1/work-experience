package com.fitnessplayground.dao.domain.fpSourceDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;


@JsonIgnoreProperties(ignoreUnknown = true)
public class FPUpdateStaffRequest {

    @JsonProperty("handshakeKey")
    private String handshakeKey;
//    @JsonProperty("staffMembers")
//    private UpdateStaffRequestBody body;

    @JsonProperty("staffMembers")
    private ArrayList<MboStaff> staffs;

    public FPUpdateStaffRequest() {
    }

    public String getHandshakeKey() {
        return handshakeKey;
    }

    public void setHandshakeKey(String handshakeKey) {
        this.handshakeKey = handshakeKey;
    }

    public ArrayList<MboStaff> getStaffs() {
        return staffs;
    }

    public void setStaffs(ArrayList<MboStaff> staffs) {
        this.staffs = staffs;
    }

    @Override
    public String toString() {
        return "FPUpdateStaffRequest{" +
                "handshakeKey='" + handshakeKey + '\'' +
                ", staffs=" + staffs +
                '}';
    }
}
