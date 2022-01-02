package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MboGetClassesResponse {

    @JsonProperty("PaginationResponse")
    private PaginationResponse paginationResponse;
    @JsonProperty("Classes")
    private ArrayList<Class> classes;

    public MboGetClassesResponse() {
    }

    public PaginationResponse getPaginationResponse() {
        return paginationResponse;
    }

    public void setPaginationResponse(PaginationResponse paginationResponse) {
        this.paginationResponse = paginationResponse;
    }

    public ArrayList<Class> getClasses() {
        return classes;
    }

    public void setClasses(ArrayList<Class> classes) {
        this.classes = classes;
    }

    @Override
    public String toString() {
        return "MboGetClassesRequest{" +
                "paginationResponse=" + paginationResponse +
                ", classes=" + classes +
                '}';
    }
}
