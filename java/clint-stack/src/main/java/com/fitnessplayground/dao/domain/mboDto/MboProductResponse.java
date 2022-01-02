package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MboProductResponse {

    @JsonProperty("PaginationResponse")
    private PaginationResponse paginationResponse;
    @JsonProperty("Products")
    private ArrayList<Product> products;

    public MboProductResponse() {
    }

    public PaginationResponse getPaginationResponse() {
        return paginationResponse;
    }

    public void setPaginationResponse(PaginationResponse paginationResponse) {
        this.paginationResponse = paginationResponse;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "MboProductResponse{" +
                "paginationResponse=" + paginationResponse +
                ", products=" + products +
                '}';
    }
}
