package com.fitnessplayground.dao.domain;

import com.fitnessplayground.dao.domain.mboDto.ClientContract;
import com.fitnessplayground.dao.domain.mboDto.EventDataClientContract;
import com.fitnessplayground.util.Helpers;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class MboClientContract {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String agreementDate;//2019-05-01T00:00:00",
    private String autopayStatus;//"Active",
    private String contractName;//"Play: Membership ($28.95)",
    private String endDate;//"2020-04-30T00:00:00",
    private long clientContractId;//167006,
    private int originationLocationId;// 2,
    private String startDate;//"2019-05-02T00:00:00",
    private int siteId;//152065,


    public MboClientContract() {
    }

    public static MboClientContract create(EventDataClientContract eventData) {
        MboClientContract mboClientContract = new MboClientContract();

        mboClientContract.setAgreementDate(Helpers.cleanDateTime(eventData.getAgreementDateTime()));
        mboClientContract.setContractName(eventData.getContractName());
        mboClientContract.setEndDate(Helpers.cleanDateTime(eventData.getContractEndDateTime()));
        mboClientContract.setClientContractId(eventData.getClientContractId());
        mboClientContract.setOriginationLocationId(eventData.getContractOriginationLocation());
        mboClientContract.setStartDate(Helpers.cleanDateTime(eventData.getContractStartDateTime()));
        mboClientContract.setSiteId(eventData.getSiteId());

        return mboClientContract;
    }

    public static MboClientContract save(ClientContract clientContract, MboClient mboClient) {
        MboClientContract mboClientContract = new MboClientContract();
        return build(clientContract, mboClientContract, mboClient);
    }

    public static MboClientContract update(ClientContract clientContract, MboClientContract mboClientContract, MboClient mboClient) {
        return build(clientContract, mboClientContract, mboClient);
    }

    private static MboClientContract build(ClientContract clientContract, MboClientContract mboClientContract, MboClient mboClient) {
        mboClientContract.setAgreementDate(Helpers.cleanDateTime(clientContract.getAgreementDate()));
        mboClientContract.setAutopayStatus(clientContract.getAutopayStatus());
        mboClientContract.setContractName(clientContract.getContractName());
        mboClientContract.setEndDate(Helpers.cleanDateTime(clientContract.getEndDate()));
        mboClientContract.setClientContractId(clientContract.getClientContractId());
        mboClientContract.setOriginationLocationId(clientContract.getOriginationLocationId());
        mboClientContract.setStartDate(Helpers.cleanDateTime(clientContract.getStartDate()));
        mboClientContract.setSiteId(clientContract.getSiteId());
//        mboClientContract.setUpcomingAutopayEvents(clientContract.getUpcomingAutopayEvents());

        return mboClientContract;
    }

    public long getId() {
        return id;
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

    @Override
    public String toString() {
        return "MboClientContract{" +
                "id=" + id +
                ", agreementDate='" + agreementDate + '\'' +
                ", autopayStatus='" + autopayStatus + '\'' +
                ", contractName='" + contractName + '\'' +
                ", endDate='" + endDate + '\'' +
                ", clientContractId=" + clientContractId +
                ", originationLocationId=" + originationLocationId +
                ", startDate='" + startDate + '\'' +
                ", siteId=" + siteId +
                '}';
    }
}
