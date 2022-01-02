package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MboMetaData {

    @JsonProperty("Id")
    private String id;
    @JsonProperty("Amount")
    private String amount;
    @JsonProperty("Notes")
    private String notes;
    @JsonProperty("StaffId")
    private String staffId;

    public MboMetaData() {
    }

    public MboMetaData(String id, String amount, String notes, String staffId) {
        this.id = id;
        this.amount = amount;
        this.notes = notes;
        this.staffId = staffId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    @Override
    public String toString() {
        return "MboMetaData{" +
                "id='" + id + '\'' +
                ", amount='" + amount + '\'' +
                ", notes='" + notes + '\'' +
                ", staffId='" + staffId + '\'' +
                '}';
    }
}
