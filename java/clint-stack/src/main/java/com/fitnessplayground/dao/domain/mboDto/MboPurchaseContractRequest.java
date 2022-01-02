package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MboPurchaseContractRequest {

    @JsonProperty("Test")
    private Boolean isTest;
    @JsonProperty("LocationId")
    private Integer locationId;
    @JsonProperty("ClientId")
    private String clientId;
    @JsonProperty("ContractId")
    private Integer contractId;
    @JsonProperty("StartDate")
    private String startDate;
    @JsonProperty("FirstPaymentOccurs")
    private String firstPaymentOccurs;
    @JsonProperty("PromotionCode")
    private String promotionCode;
    @JsonProperty("UseDirectDebit")
    private Boolean useDirectDebit;
    @JsonProperty("CreditCardInfo")
    private CreditCardInfo creditCardInfo;
    @JsonProperty("StoredCardInfo")
    private StoredCardInfo storedCardInfo;
    @JsonProperty("SendNotifications")
    private Boolean sendNotifications;
    @JsonProperty("SalesRepId")
    private Long salesRepMboId;

    public MboPurchaseContractRequest() {
    }

    public Boolean getTest() {
        return isTest;
    }

    public void setTest(Boolean test) {
        isTest = test;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFirstPaymentOccurs() {
        return firstPaymentOccurs;
    }

    public void setFirstPaymentOccurs(String firstPaymentOccurs) {
        this.firstPaymentOccurs = firstPaymentOccurs;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public Boolean getUseDirectDebit() {
        return useDirectDebit;
    }

    public void setUseDirectDebit(Boolean useDirectDebit) {
        this.useDirectDebit = useDirectDebit;
    }

    public CreditCardInfo getCreditCardInfo() {
        return creditCardInfo;
    }

    public void setCreditCardInfo(CreditCardInfo creditCardInfo) {
        this.creditCardInfo = creditCardInfo;
    }

    public StoredCardInfo getStoredCardInfo() {
        return storedCardInfo;
    }

    public void setStoredCardInfo(StoredCardInfo storedCardInfo) {
        this.storedCardInfo = storedCardInfo;
    }

    public Boolean getSendNotifications() {
        return sendNotifications;
    }

    public void setSendNotifications(Boolean sendNotifications) {
        this.sendNotifications = sendNotifications;
    }

    public Long getSalesRepMboId() {
        return salesRepMboId;
    }

    public void setSalesRepMboId(Long salesRepMboId) {
        this.salesRepMboId = salesRepMboId;
    }

    @Override
    public String toString() {
        return "MboPurchaseContractRequest{" +
                "isTest=" + isTest +
                ", locationId=" + locationId +
                ", clientId='" + clientId + '\'' +
                ", contractId=" + contractId +
                ", startDate='" + startDate + '\'' +
                ", firstPaymentOccurs='" + firstPaymentOccurs + '\'' +
                ", promotionCode='" + promotionCode + '\'' +
                ", useDirectDebit=" + useDirectDebit +
                ", creditCardInfo=" + creditCardInfo +
                ", storedCardInfo=" + storedCardInfo +
                ", sendNotifications=" + sendNotifications +
                ", salesRepMboId=" + salesRepMboId +
                '}';
    }
}
