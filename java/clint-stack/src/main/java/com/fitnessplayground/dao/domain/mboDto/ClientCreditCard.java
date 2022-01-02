package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientCreditCard {

    @JsonProperty("Address")
    private String address;
    @JsonProperty("CardHolder")
    private String cardHolder;
    @JsonProperty("CardNumber")
    private String cardNumber;
    @JsonProperty("CardType")
    private String cardType;
    @JsonProperty("City")
    private String city;
    @JsonProperty("ExpMonth")
    private String expMonth;
    @JsonProperty("ExpYear")
    private String expYear;
    @JsonProperty("LastFour")
    private String lastFour;
    @JsonProperty("PostalCode")
    private  String postCode;
    @JsonProperty("State")
    private String state;

    public ClientCreditCard() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(String expMonth) {
        this.expMonth = expMonth;
    }

    public String getExpYear() {
        return expYear;
    }

    public void setExpYear(String expYear) {
        this.expYear = expYear;
    }

    public String getLastFour() {
        return lastFour;
    }

    public void setLastFour(String lastFour) {
        this.lastFour = lastFour;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "ClientCreditCard{" +
                "address='" + address + '\'' +
                ", cardHolder='" + cardHolder + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", cardType='" + cardType + '\'' +
                ", city='" + city + '\'' +
                ", expMonth='" + expMonth + '\'' +
                ", expYear='" + expYear + '\'' +
                ", lastFour='" + lastFour + '\'' +
                ", postCode='" + postCode + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
