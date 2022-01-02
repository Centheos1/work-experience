package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CartItem {

    @JsonProperty("Item")
    private Item item;

    public CartItem() {
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "item=" + item +
                '}';
    }
}
