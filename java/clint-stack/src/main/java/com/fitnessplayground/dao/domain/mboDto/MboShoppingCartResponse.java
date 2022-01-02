package com.fitnessplayground.dao.domain.mboDto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MboShoppingCartResponse {

    @JsonProperty("ShoppingCart")
    private ShoppingCart shoppingCart;

    public MboShoppingCartResponse() {
    }

    public MboShoppingCartResponse(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @Override
    public String toString() {
        return "MboShoppingCartResponse{" +
                "shoppingCart=" + shoppingCart +
                '}';
    }
}
