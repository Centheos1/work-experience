package com.fitnessplayground.dao.domain.temp;

public class StaffLookUp {

    private String email;
    private String fireBaseId;

    public StaffLookUp() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFireBaseId() {
        return fireBaseId;
    }

    public void setFireBaseId(String fireBaseId) {
        this.fireBaseId = fireBaseId;
    }

    @Override
    public String toString() {
        return "StaffLookUp{" +
                "email='" + email + '\'' +
                '}';
    }
}
