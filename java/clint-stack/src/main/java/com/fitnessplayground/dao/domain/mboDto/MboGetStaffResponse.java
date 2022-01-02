package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MboGetStaffResponse {

    @JsonProperty("PaginationResponse")
    PaginationResponse paginationResponse;
    @JsonProperty("StaffMembers")
    ArrayList<Staff> staffMembers;

    public MboGetStaffResponse() {
    }

    public PaginationResponse getPaginationResponse() {
        return paginationResponse;
    }

    public void setPaginationResponse(PaginationResponse paginationResponse) {
        this.paginationResponse = paginationResponse;
    }

    public ArrayList<Staff> getStaffMembers() {
        return staffMembers;
    }

    public void setStaffMembers(ArrayList<Staff> staffMembers) {
        this.staffMembers = staffMembers;
    }

    @Override
    public String toString() {
        return "MboGetStaffResponse{" +
                "paginationResponse=" + paginationResponse +
                ", staffMembers=" + staffMembers +
                '}';
    }
}
