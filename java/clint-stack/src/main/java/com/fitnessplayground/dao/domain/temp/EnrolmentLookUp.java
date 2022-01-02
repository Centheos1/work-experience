package com.fitnessplayground.dao.domain.temp;

public class EnrolmentLookUp {

    private String location;
    private String search;

    public EnrolmentLookUp() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    @Override
    public String toString() {
        return "EnrolmentLookUp{" +
                "location='" + location + '\'' +
                ", search='" + search + '\'' +
                '}';
    }
}
