package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AutopayEvent {

    @JsonProperty("ClientContractId")
    private long clientContractId;//167006,
    @JsonProperty("ChargeAmount")
    private double chargeAmount;// 59.4,
    @JsonProperty("PaymentMethod")
    private String paymentMethod;//"CreditCard",
    @JsonProperty("ScheduleDate")
    private String scheduleDate;//"2019-05-16T00:00:00"

    public AutopayEvent() {
    }

    public long getClientContractId() {
        return clientContractId;
    }

    public void setClientContractId(long clientContractId) {
        this.clientContractId = clientContractId;
    }

    public double getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(double chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    @Override
    public String toString() {
        return "AutopayEvent{" +
                "clientContractId=" + clientContractId +
                ", chargeAmount=" + chargeAmount +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", scheduleDate='" + scheduleDate + '\'' +
                '}';
    }
}
