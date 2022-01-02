package com.fitnessplayground.dao.domain.fpSourceDto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class UpdateStaffRequestBody {


    @JsonProperty("staffMembers")
    private ArrayList<MboStaff> staffs;

    public UpdateStaffRequestBody() {
    }


    public ArrayList<MboStaff> getStaffs() {
        return staffs;
    }

    public void setStaffs(ArrayList<MboStaff> staffs) {
        this.staffs = staffs;
    }

    @Override
    public String toString() {
        return "UpdateStaffRequestBody{" +
                ", staffs=" + staffs +
                '}';
    }
}
