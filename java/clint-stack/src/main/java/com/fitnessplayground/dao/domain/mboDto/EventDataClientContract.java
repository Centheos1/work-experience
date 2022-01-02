package com.fitnessplayground.dao.domain.mboDto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDataClientContract {

    private Integer siteId; // 123,
    private String clientId; // "100000009",
    private Integer clientUniqueId; // 100000009,
    private String clientFirstName; // "John",
    private String clientLastName; // "Smith",
    private String clientEmail; //"john.smith@gmail.com",
    private String agreementDateTime; // "2018-03-20T10:29:42Z",
    private Integer contractSoldByStaffId; // 12,
    private String contractSoldByStaffFirstName; // "Jane",
    private String contractSoldByStaffLastName; // "Doe",
    private Integer contractOriginationLocation; // 1,
    private Integer contractId; // 3,
    private String contractName; // "Gold Membership Contract",
    private Integer clientContractId; // 117,
    private String contractStartDateTime; // "2018-03-20T00:00:00Z",
    private String contractEndDateTime; // "2019-03-20T00:00:00Z",
    private Boolean isAutoRenewing; // true

    public EventDataClientContract() {
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getClientUniqueId() {
        return clientUniqueId;
    }

    public void setClientUniqueId(Integer clientUniqueId) {
        this.clientUniqueId = clientUniqueId;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getAgreementDateTime() {
        return agreementDateTime;
    }

    public void setAgreementDateTime(String agreementDateTime) {
        this.agreementDateTime = agreementDateTime;
    }

    public Integer getContractSoldByStaffId() {
        return contractSoldByStaffId;
    }

    public void setContractSoldByStaffId(Integer contractSoldByStaffId) {
        this.contractSoldByStaffId = contractSoldByStaffId;
    }

    public String getContractSoldByStaffFirstName() {
        return contractSoldByStaffFirstName;
    }

    public void setContractSoldByStaffFirstName(String contractSoldByStaffFirstName) {
        this.contractSoldByStaffFirstName = contractSoldByStaffFirstName;
    }

    public String getContractSoldByStaffLastName() {
        return contractSoldByStaffLastName;
    }

    public void setContractSoldByStaffLastName(String contractSoldByStaffLastName) {
        this.contractSoldByStaffLastName = contractSoldByStaffLastName;
    }

    public Integer getContractOriginationLocation() {
        return contractOriginationLocation;
    }

    public void setContractOriginationLocation(Integer contractOriginationLocation) {
        this.contractOriginationLocation = contractOriginationLocation;
    }

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public Integer getClientContractId() {
        return clientContractId;
    }

    public void setClientContractId(Integer clientContractId) {
        this.clientContractId = clientContractId;
    }

    public String getContractStartDateTime() {
        return contractStartDateTime;
    }

    public void setContractStartDateTime(String contractStartDateTime) {
        this.contractStartDateTime = contractStartDateTime;
    }

    public String getContractEndDateTime() {
        return contractEndDateTime;
    }

    public void setContractEndDateTime(String contractEndDateTime) {
        this.contractEndDateTime = contractEndDateTime;
    }

    public Boolean getAutoRenewing() {
        return isAutoRenewing;
    }

    public void setAutoRenewing(Boolean autoRenewing) {
        isAutoRenewing = autoRenewing;
    }

    @Override
    public String toString() {
        return "EventDataClientContract{" +
                "siteId=" + siteId +
                ", clientId='" + clientId + '\'' +
                ", clientUniqueId=" + clientUniqueId +
                ", clientFirstName='" + clientFirstName + '\'' +
                ", clientLastName='" + clientLastName + '\'' +
                ", clientEmail='" + clientEmail + '\'' +
                ", agreementDateTime='" + agreementDateTime + '\'' +
                ", contractSoldByStaffId=" + contractSoldByStaffId +
                ", contractSoldByStaffFirstName='" + contractSoldByStaffFirstName + '\'' +
                ", contractSoldByStaffLastName='" + contractSoldByStaffLastName + '\'' +
                ", contractOriginationLocation=" + contractOriginationLocation +
                ", contractId=" + contractId +
                ", contractName='" + contractName + '\'' +
                ", clientContractId=" + clientContractId +
                ", contractStartDateTime='" + contractStartDateTime + '\'' +
                ", contractEndDateTime='" + contractEndDateTime + '\'' +
                ", isAutoRenewing=" + isAutoRenewing +
                '}';
    }
}
