package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Contract {

    @JsonProperty("Id")
    private int mboId;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("AssignsMembershipId")
    private int assignsMembershipId;
    @JsonProperty("AssignsMembershipName")
    private String assignsMembershipName;
    @JsonProperty("SoldOnline")
    private boolean soldOnline;
    @JsonProperty("AutopaySchedule")
    private AutopaySchedule autopaySchedule;
    @JsonProperty("NumberOfAutopays")
    private int numberOfAutoPays;
    @JsonProperty("AutopayTriggerType")
    private String autoPayTriggerType;
    @JsonProperty("ActionUponCompletionOfAutopays")
    private String actionUponCompletionAutoPays;
    @JsonProperty("ClientsChargedOn")
    private String clientChargedOn;
    @JsonProperty("DiscountAmount")
    private float discountAmount;
    @JsonProperty("LocationPurchaseRestrictionIds")
    private int[] locationIds;
    @JsonProperty("FirstPaymentAmountSubtotal")
    private float firstPaymentAmountSubtotal;
    @JsonProperty("FirstPaymentAmountTax")
    private float firstPaymentAmountTax;
    @JsonProperty("FirstPaymentAmountTotal")
    private float firstPaymentAmountTotal;
    @JsonProperty("RecurringPaymentAmountSubtotal")
    private float reoccuringPaymentAmountSubtotal;
    @JsonProperty("RecurringPaymentAmountTax")
    private float reoccuringPaymentAmountTax;
    @JsonProperty("RecurringPaymentAmountTotal")
    private float reoccuringPaymentAmountTotal;
    @JsonProperty("TotalContractPaymentAmountSubtotal")
    private float totalContractPaymentAmountSubtotal;
    @JsonProperty("TotalContractPaymentAmountTax")
    private float totalContractPaymentAmountTax;
    @JsonProperty("TotalContractPaymentAmountTotal")
    private float totalContractPaymentAmountTotal;

    public Contract() {
    }

    public int getMboId() {
        return mboId;
    }

    public void setMboId(int id) {
        this.mboId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAssignsMembershipId() {
        return assignsMembershipId;
    }

    public void setAssignsMembershipId(int assignsMembershipId) {
        this.assignsMembershipId = assignsMembershipId;
    }

    public String getAssignsMembershipName() {
        return assignsMembershipName;
    }

    public void setAssignsMembershipName(String assignsMembershipName) {
        this.assignsMembershipName = assignsMembershipName;
    }

    public boolean isSoldOnline() {
        return soldOnline;
    }

    public void setSoldOnline(boolean soldOnline) {
        this.soldOnline = soldOnline;
    }

    public AutopaySchedule getAutopaySchedule() {
        return autopaySchedule;
    }

    public void setAutopaySchedule(AutopaySchedule autopaySchedule) {
        this.autopaySchedule = autopaySchedule;
    }

    public int getNumberOfAutoPays() {
        return numberOfAutoPays;
    }

    public void setNumberOfAutoPays(int numberOfAutoPays) {
        this.numberOfAutoPays = numberOfAutoPays;
    }

    public String getAutoPayTriggerType() {
        return autoPayTriggerType;
    }

    public void setAutoPayTriggerType(String autoPayTriggerType) {
        this.autoPayTriggerType = autoPayTriggerType;
    }

    public String getActionUponCompletionAutoPays() {
        return actionUponCompletionAutoPays;
    }

    public void setActionUponCompletionAutoPays(String actionUponCompletionAutoPays) {
        this.actionUponCompletionAutoPays = actionUponCompletionAutoPays;
    }

    public String getClientChargedOn() {
        return clientChargedOn;
    }

    public void setClientChargedOn(String clientChargedOn) {
        this.clientChargedOn = clientChargedOn;
    }

    public float getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(float discountAmount) {
        this.discountAmount = discountAmount;
    }

    public int[] getLocationIds() {
        return locationIds;
    }

    public void setLocationIds(int[] locationIds) {
        this.locationIds = locationIds;
    }

    public float getFirstPaymentAmountSubtotal() {
        return firstPaymentAmountSubtotal;
    }

    public void setFirstPaymentAmountSubtotal(float firstPaymentAmountSubtotal) {
        this.firstPaymentAmountSubtotal = firstPaymentAmountSubtotal;
    }

    public float getFirstPaymentAmountTax() {
        return firstPaymentAmountTax;
    }

    public void setFirstPaymentAmountTax(float firstPaymentAmountTax) {
        this.firstPaymentAmountTax = firstPaymentAmountTax;
    }

    public float getFirstPaymentAmountTotal() {
        return firstPaymentAmountTotal;
    }

    public void setFirstPaymentAmountTotal(float firstPaymentAmountTotal) {
        this.firstPaymentAmountTotal = firstPaymentAmountTotal;
    }

    public float getReoccuringPaymentAmountSubtotal() {
        return reoccuringPaymentAmountSubtotal;
    }

    public void setReoccuringPaymentAmountSubtotal(float reoccuringPaymentAmountSubtotal) {
        this.reoccuringPaymentAmountSubtotal = reoccuringPaymentAmountSubtotal;
    }

    public float getReoccuringPaymentAmountTax() {
        return reoccuringPaymentAmountTax;
    }

    public void setReoccuringPaymentAmountTax(float reoccuringPaymentAmountTax) {
        this.reoccuringPaymentAmountTax = reoccuringPaymentAmountTax;
    }

    public float getReoccuringPaymentAmountTotal() {
        return reoccuringPaymentAmountTotal;
    }

    public void setReoccuringPaymentAmountTotal(float reoccuringPaymentAmountTotal) {
        this.reoccuringPaymentAmountTotal = reoccuringPaymentAmountTotal;
    }

    public float getTotalContractPaymentAmountSubtotal() {
        return totalContractPaymentAmountSubtotal;
    }

    public void setTotalContractPaymentAmountSubtotal(float totalContractPaymentAmountSubtotal) {
        this.totalContractPaymentAmountSubtotal = totalContractPaymentAmountSubtotal;
    }

    public float getTotalContractPaymentAmountTax() {
        return totalContractPaymentAmountTax;
    }

    public void setTotalContractPaymentAmountTax(float totalContractPaymentAmountTax) {
        this.totalContractPaymentAmountTax = totalContractPaymentAmountTax;
    }

    public float getTotalContractPaymentAmountTotal() {
        return totalContractPaymentAmountTotal;
    }

    public void setTotalContractPaymentAmountTotal(float totalContractPaymentAmountTotal) {
        this.totalContractPaymentAmountTotal = totalContractPaymentAmountTotal;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + mboId +
                ", name='" + name + '\'' +
                ", assignsMembershipId=" + assignsMembershipId +
                ", assignsMembershipName='" + assignsMembershipName + '\'' +
                ", soldOnline=" + soldOnline +
                ", autopaySchedule=" + autopaySchedule +
                ", numberOfAutoPays=" + numberOfAutoPays +
                ", autoPayTriggerType='" + autoPayTriggerType + '\'' +
                ", actionUponCompletionAutoPays='" + actionUponCompletionAutoPays + '\'' +
                ", clientChargedOn='" + clientChargedOn + '\'' +
                ", discountAmount=" + discountAmount +
                ", locationIds=" + Arrays.toString(locationIds) +
                ", firstPaymentAmountSubtotal=" + firstPaymentAmountSubtotal +
                ", firstPaymentAmountTax=" + firstPaymentAmountTax +
                ", firstPaymentAmountTotal=" + firstPaymentAmountTotal +
                ", reoccuringPaymentAmountSubtotal=" + reoccuringPaymentAmountSubtotal +
                ", reoccuringPaymentAmountTax=" + reoccuringPaymentAmountTax +
                ", reoccuringPaymentAmountTotal=" + reoccuringPaymentAmountTotal +
                ", totalContractPaymentAmountSubtotal=" + totalContractPaymentAmountSubtotal +
                ", totalContractPaymentAmountTax=" + totalContractPaymentAmountTax +
                ", totalContractPaymentAmountTotal=" + totalContractPaymentAmountTotal +
                '}';
    }
}
