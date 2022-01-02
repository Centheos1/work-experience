package com.fitnessplayground.dao.domain.temp;

import java.util.Arrays;

public class PaymentDetails {
    
    private String useExistingDetails; //no
    private String firstName; //Justin
    private String lastName; //Ashley
    private String address1; //429 Elizabeth St
    private String address2; //
    private String city; //Surry Hills
    private String state; //NSW
    private String postcode; //2010
    private String paymentType; //creditCard
    private String creditCardNumber; //4111 1111 1111 1111
    private String creditCardExpiry; //12/22
    private String cardVerificationCode; //123
    private String financialInstitution; //
    private String bsb; //private String 
    private String accountNumber; //
    private String accountType; //
    private String[] getAuthorisation; //[true],
    private String signature; //

    public PaymentDetails() {
    }

    public PaymentDetails(String useExistingDetails, String firstName, String lastName, String address1, String address2, String city, String state, String postcode, String paymentType, String creditCardNumber, String creditCardExpiry, String cardVerificationCode, String financialInstitution, String bsb, String accountNumber, String accountType, String[] getAuthorisation, String signature) {
        this.useExistingDetails = useExistingDetails;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.postcode = postcode;
        this.paymentType = paymentType;
        this.creditCardNumber = creditCardNumber;
        this.creditCardExpiry = creditCardExpiry;
        this.cardVerificationCode = cardVerificationCode;
        this.financialInstitution = financialInstitution;
        this.bsb = bsb;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.getAuthorisation = getAuthorisation;
        this.signature = signature;
    }

    public String getUseExistingDetails() {
        return useExistingDetails;
    }

    public void setUseExistingDetails(String useExistingDetails) {
        this.useExistingDetails = useExistingDetails;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getCreditCardExpiry() {
        return creditCardExpiry;
    }

    public void setCreditCardExpiry(String creditCardExpiry) {
        this.creditCardExpiry = creditCardExpiry;
    }

    public String getCardVerificationCode() {
        return cardVerificationCode;
    }

    public void setCardVerificationCode(String cardVerificationCode) {
        this.cardVerificationCode = cardVerificationCode;
    }

    public String getFinancialInstitution() {
        return financialInstitution;
    }

    public void setFinancialInstitution(String financialInstitution) {
        this.financialInstitution = financialInstitution;
    }

    public String getBsb() {
        return bsb;
    }

    public void setBsb(String bsb) {
        this.bsb = bsb;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String[] getGetAuthorisation() {
        return getAuthorisation;
    }

    public void setGetAuthorisation(String[] getAuthorisation) {
        this.getAuthorisation = getAuthorisation;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "PaymentDetails{" +
                "useExistingDetails='" + useExistingDetails + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postcode='" + postcode + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", creditCardNumber='" + creditCardNumber + '\'' +
                ", creditCardExpiry='" + creditCardExpiry + '\'' +
                ", cardVerificationCode='" + cardVerificationCode + '\'' +
                ", financialInstitution='" + financialInstitution + '\'' +
                ", bsb='" + bsb + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountType='" + accountType + '\'' +
                ", getAuthorisation=" + Arrays.toString(getAuthorisation) +
                ", signature='" + signature + '\'' +
                '}';
    }
}
