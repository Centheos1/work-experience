package com.fitnessplayground.dao.domain.temp;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BankDetailSubmission {

    @JsonProperty("accountHolderName")
    private String accountHolderName;
    @JsonProperty("branch")
    private String branch;
    @JsonProperty("financialInstitution")
    private String financialInstitution;
    @JsonProperty("bsb")
    private String bsb;
    @JsonProperty("accountNumber")
    private String accountNumber;
    @JsonProperty("accountType")
    private String accountType;

    public BankDetailSubmission() {
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
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

    @Override
    public String toString() {
        return "BankDetailSubmission{" +
                "accountHolderName='" + accountHolderName + '\'' +
                ", branch='" + branch + '\'' +
                ", financialInstitution='" + financialInstitution + '\'' +
                ", bsb='" + bsb + '\'' +
//                ", accountNumber='" + accountNumber + '\'' +
                ", accountType='" + accountType + '\'' +
                '}';
    }
}
