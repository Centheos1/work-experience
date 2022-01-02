package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCart {

    @JsonProperty("Id")
    private String shoppingCartId;// "01458120-38c2-47b2-8afd-c5fe2845c7a8",
    @JsonProperty("CartItems")
    private ArrayList<CartItem> cartItems;
    @JsonProperty("SubTotal")
    private double subTotal;// 99,
    @JsonProperty("DiscountTotal")
    private double discountTotal;// 70,
    @JsonProperty("TaxTotal")
    private double taxTotal;// 0,
    @JsonProperty("GrandTotal")
    private double grandTotal;// 29

    public ShoppingCart() {
    }

    public ShoppingCart(String shoppingCartId, ArrayList<CartItem> cartItems, double subTotal, double discountTotal, double taxTotal, double grandTotal) {
        this.shoppingCartId = shoppingCartId;
        this.cartItems = cartItems;
        this.subTotal = subTotal;
        this.discountTotal = discountTotal;
        this.taxTotal = taxTotal;
        this.grandTotal = grandTotal;
    }

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getDiscountTotal() {
        return discountTotal;
    }

    public void setDiscountTotal(double discountTotal) {
        this.discountTotal = discountTotal;
    }

    public double getTaxTotal() {
        return taxTotal;
    }

    public void setTaxTotal(double taxTotal) {
        this.taxTotal = taxTotal;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "shoppingCartId='" + shoppingCartId + '\'' +
                ", cartItems=" + cartItems +
                ", subTotal=" + subTotal +
                ", discountTotal=" + discountTotal +
                ", taxTotal=" + taxTotal +
                ", grandTotal=" + grandTotal +
                '}';
    }
}
