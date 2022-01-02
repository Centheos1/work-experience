package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StaffPermission {

    @JsonProperty("PermissionGroupName")
    private String permissionGroupName;// "Coowner",

    public StaffPermission() {
    }

    public String getPermissionGroupName() {
        return permissionGroupName;
    }

    public void setPermissionGroupName(String permissionGroupName) {
        this.permissionGroupName = permissionGroupName;
    }

    @Override
    public String toString() {
        return "StaffPermission{" +
                "permissionGroupName='" + permissionGroupName + '\'' +
                '}';
    }
}
