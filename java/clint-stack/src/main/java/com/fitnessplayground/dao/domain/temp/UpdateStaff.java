package com.fitnessplayground.dao.domain.temp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateStaff {

    @JsonProperty("mboIdAndSiteId")
    private String mboIdAndSiteId;
    @JsonProperty("locationIds")
    private ArrayList<String> locationIds;
    @JsonProperty("permissionLevel")
    private String permissionLevel;
    @JsonProperty("role")
    private String[] role;

    public UpdateStaff() {
    }

    public String getMboIdAndSiteId() {
        return mboIdAndSiteId;
    }

    public void setMboIdAndSiteId(String mboIdAndSiteId) {
        this.mboIdAndSiteId = mboIdAndSiteId;
    }

    public ArrayList<String> getLocationIds() {
        return locationIds;
    }

    public void setLocationIds(ArrayList<String> locationIds) {
        this.locationIds = locationIds;
    }

    public String getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(String permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public String[] getRole() {
        return role;
    }

    public void setRole(String[] role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UpdateStaff{" +
                "mboIdAndSiteId='" + mboIdAndSiteId + '\'' +
                ", locationIds=" + locationIds +
                ", permissionLevel='" + permissionLevel + '\'' +
                ", role=" + Arrays.toString(role) +
                '}';
    }
}
