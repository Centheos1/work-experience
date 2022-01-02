package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MboAddClientDirectDebitInfoResponse {

    @JsonProperty("ClientId")
    private String clientId;
    @JsonProperty("NameOnAccount")
    private String nameOnAccount;
    @JsonProperty("RoutingNumber")
    private String bsb;
    @JsonProperty("AccountNumber")
    private String accountNumber;
    @JsonProperty("AccountType")
    private String accountType;

    public MboAddClientDirectDebitInfoResponse() {
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getNameOnAccount() {
        return nameOnAccount;
    }

    public void setNameOnAccount(String nameOnAccount) {
        this.nameOnAccount = nameOnAccount;
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

    @Override
    public String toString() {
        return "MboAddClientDirectDebitInfoResponse{" +
                "clientId='" + clientId + '\'' +
                ", nameOnAccount='" + nameOnAccount + '\'' +
                ", bsb='" + bsb + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountType='" + accountType + '\'' +
                '}';
    }
}
