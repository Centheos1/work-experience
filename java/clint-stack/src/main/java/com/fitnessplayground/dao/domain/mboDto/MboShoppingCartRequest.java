package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class MboShoppingCartRequest {

    @JsonProperty("ClientId")
    private String clientId;// "100042264",
    @JsonProperty("Test")
    private boolean test;// true,
    @JsonProperty("Items")
    private ArrayList<MboRequestItems> items;
    @JsonProperty("InStore")
    private boolean inStore;// true,
    @JsonProperty("PromotionCode")
    private String promotionCode;// "Key29",
    @JsonProperty("Payments")
    private ArrayList<MboRequestItem> payments;
    @JsonProperty("SendEmail")
    private boolean sendEmail;// false,
    @JsonProperty("LocationId")
    private int locationId;// 1

    public MboShoppingCartRequest() {
    }



    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public boolean isTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }

    public ArrayList<MboRequestItems> getItems() {
        return items;
    }

    public void setItems(ArrayList<MboRequestItems> items) {
        this.items = items;
    }

    public boolean isInStore() {
        return inStore;
    }

    public void setInStore(boolean inStore) {
        this.inStore = inStore;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public ArrayList<MboRequestItem> getPayments() {
        return payments;
    }

    public void setPayments(ArrayList<MboRequestItem> payments) {
        this.payments = payments;
    }

    public boolean isSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    @Override
    public String toString() {
        return "MboShoppingCartRequest{" +
                "clientId='" + clientId + '\'' +
                ", test=" + test +
                ", items=" + items +
                ", inStore=" + inStore +
                ", promotionCode='" + promotionCode + '\'' +
                ", payments=" + payments +
                ", sendEmail=" + sendEmail +
                ", locationId=" + locationId +
                '}';
    }
}
