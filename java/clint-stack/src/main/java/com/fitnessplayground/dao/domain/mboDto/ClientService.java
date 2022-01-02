package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientService {

    @JsonProperty("ActiveDate")
    private String activeDate;//"2019-05-02T00:00:00",
    @JsonProperty("Count")
    private int count;// 99999,
    @JsonProperty("Current")
    private boolean current;// true,
    @JsonProperty("ExpirationDate")
    private String expirationDate;//"2019-05-22T00:00:00",
    @JsonProperty("Id")
    private long clientServiceId;//1446633,
    @JsonProperty("Name")
    private String name;//"Play: Membership+",
    @JsonProperty("PaymentDate")
    private String paymentDate;//"2019-05-02T00:00:00",
    @JsonProperty("Program")
    private Program program;//
    @JsonProperty("Remaining")
    private int remaining;//99999,
    @JsonProperty("SiteId")
    private int siteId;//152065,
    @JsonProperty("Action")
    private String action;//"None"

    public ClientService() {
    }

    public String getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(String activeDate) {
        this.activeDate = activeDate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public long getClientServiceId() {
        return clientServiceId;
    }

    public void setClientServiceId(long clientServiceId) {
        this.clientServiceId = clientServiceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "ClientService{" +
                "activeDate='" + activeDate + '\'' +
                ", count=" + count +
                ", current=" + current +
                ", expirationDate='" + expirationDate + '\'' +
                ", clientServiceId=" + clientServiceId +
                ", name='" + name + '\'' +
                ", paymentDate='" + paymentDate + '\'' +
                ", program=" + program +
                ", remaining=" + remaining +
                ", siteId=" + siteId +
                ", action='" + action + '\'' +
                '}';
    }
}
