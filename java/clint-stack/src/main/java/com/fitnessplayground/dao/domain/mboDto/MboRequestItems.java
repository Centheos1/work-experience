package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MboRequestItems {

    @JsonProperty("Item")
    private MboRequestItem mboRequestItem;
    @JsonProperty("Quantity")
    private int quantity;// 1

    public MboRequestItems() {
    }

    public MboRequestItem getMboRequestItem() {
        return mboRequestItem;
    }

    public void setMboRequestItem(MboRequestItem mboRequestItem) {
        this.mboRequestItem = mboRequestItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "MboRequestItems{" +
                "mboRequestItems=" + mboRequestItem +
                ", quantity=" + quantity +
                '}';
    }
}
