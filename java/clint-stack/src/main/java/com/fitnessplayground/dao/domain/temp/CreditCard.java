package com.fitnessplayground.dao.domain.temp;

import com.google.gson.annotations.SerializedName;

public class CreditCard {

    @SerializedName("card") private String card;
    @SerializedName("cardexp") private String cardexp;
    @SerializedName("cvv") private String cvv;

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getCardexp() {
        return cardexp;
    }

    public void setCardexp(String cardexp) {
        this.cardexp = cardexp;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "card='" + card + '\'' +
                ", cardexp='" + cardexp + '\'' +
                ", cvv='" + cvv + '\'' +
                '}';
    }
}
