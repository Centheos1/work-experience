package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditCardInfo {

    @JsonProperty("CreditCardNumber")
    private String creditCardNumber;
    @JsonProperty("ExpMonth")
    private String expMonth;
    @JsonProperty("ExpYear")
    private String expYear;
    @JsonProperty("BillingName")
    private String billingName;
    @JsonProperty("BillingAddress")
    private String billingAddress;
    @JsonProperty("BillingCity")
    private String billingCity;
    @JsonProperty("BillingState")
    private String billingState;
    @JsonProperty("BillingPostalCode")
    private String billingPostalCode;
    @JsonProperty("SaveInfo")
    private Boolean saveInfo;

    public CreditCardInfo() {
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
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

    public String getBillingName() {
        return billingName;
    }

    public void setBillingName(String billingName) {
        this.billingName = billingName;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public String getBillingState() {
        return billingState;
    }

    public void setBillingState(String billingState) {
        this.billingState = billingState;
    }

    public String getBillingPostalCode() {
        return billingPostalCode;
    }

    public void setBillingPostalCode(String billingPostalCode) {
        this.billingPostalCode = billingPostalCode;
    }

    public Boolean getSaveInfo() {
        return saveInfo;
    }

    public void setSaveInfo(Boolean saveInfo) {
        this.saveInfo = saveInfo;
    }
}
