package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientContract {

    @JsonProperty("AgreementDate")
    private String agreementDate;//2019-05-01T00:00:00",
    @JsonProperty("AutopayStatus")
    private String autopayStatus;//"Active",
    @JsonProperty("ContractName")
    private String contractName;//"Play: Membership ($28.95)",
    @JsonProperty("EndDate")
    private String endDate;//"2020-04-30T00:00:00",
    @JsonProperty("Id")
    private long clientContractId;//167006,
    @JsonProperty("OriginationLocationId")
    private int originationLocationId;// 2,
    @JsonProperty("StartDate")
    private String startDate;//"2019-05-02T00:00:00",
    @JsonProperty("SiteId")
    private int siteId;//152065,
    @JsonProperty("UpcomingAutopayEvents")
    private ArrayList<AutopayEvent> upcomingAutopayEvents;// [

    public ClientContract() {
    }

    public String getAgreementDate() {
        return agreementDate;
    }

    public void setAgreementDate(String agreementDate) {
        this.agreementDate = agreementDate;
    }

    public String getAutopayStatus() {
        return autopayStatus;
    }

    public void setAutopayStatus(String autopayStatus) {
        this.autopayStatus = autopayStatus;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public long getClientContractId() {
        return clientContractId;
    }

    public void setClientContractId(long clientContractId) {
        this.clientContractId = clientContractId;
    }

    public int getOriginationLocationId() {
        return originationLocationId;
    }

    public void setOriginationLocationId(int originationLocationId) {
        this.originationLocationId = originationLocationId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public ArrayList<AutopayEvent> getUpcomingAutopayEvents() {
        return upcomingAutopayEvents;
    }

    public void setUpcomingAutopayEvents(ArrayList<AutopayEvent> upcomingAutopayEvents) {
        this.upcomingAutopayEvents = upcomingAutopayEvents;
    }

    @Override
    public String toString() {
        return "ClientContract{" +
                "agreementDate='" + agreementDate + '\'' +
                ", autopayStatus='" + autopayStatus + '\'' +
                ", contractName='" + contractName + '\'' +
                ", endDate='" + endDate + '\'' +
                ", clientContractId=" + clientContractId +
                ", originationLocationId=" + originationLocationId +
                ", startDate='" + startDate + '\'' +
                ", siteId=" + siteId +
                ", upcomingAutopayEvents=" + upcomingAutopayEvents +
                '}';
    }
}
