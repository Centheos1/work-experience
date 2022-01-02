package com.fitnessplayground.dao.domain;

import com.fitnessplayground.model.mindbody.api.sale.Contract;
import com.fitnessplayground.util.MboContractsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.HashMap;

@Entity
public class MboContract {

    private static final Logger logger = LoggerFactory.getLogger(MboContract.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private int mboId;
    private String name;
    private int assignsMembershipId;
    private String assignsMembershipName;
    private boolean soldOnline;
    private String autoPayScheduleFrequencyType;
    private int autoPayScheduleValue;
    private String autoPayScheduleTimeUnit;
    private int numberOfAutoPays;
    private String autoPayTriggerType;
    private String actionUponCompletionAutoPays;
    private String clientChargedOn;
    private float discountAmount;
    private String locationId;
    private float firstPaymentAmountSubtotal;
    private float firstPaymentAmountTax;
    private float firstPaymentAmountTotal;
    private float reoccuringPaymentAmountSubtotal;
    private float reoccuringPaymentAmountTax;
    private float reoccuringPaymentAmountTotal;
    private float totalContractPaymentAmountSubtotal;
    private float totalContractPaymentAmountTax;
    private float totalContractPaymentAmountTotal;
    private String contractType;

    public MboContract() {
    }

    public static MboContract save(com.fitnessplayground.dao.domain.mboDto.Contract contract) {
        MboContract mboContract = new MboContract();
        return v6Build(contract, mboContract);
    }

    public static MboContract update(com.fitnessplayground.dao.domain.mboDto.Contract contract, MboContract mboContract) {
        return v6Build(contract, mboContract);
    }

//    public static MboContract save(Contract contract) {
//        MboContract mboContract = new MboContract();
//
//        return build(contract, mboContract);
//    }

    private static MboContract v6Build(com.fitnessplayground.dao.domain.mboDto.Contract contract, MboContract mboContract) {

        boolean isSydney = true;

        for (int locId : contract.getLocationIds()) {
            if (locId == 4) {
                isSydney = false;
            }
        }

        if (isSydney) {
            HashMap<Integer, String> contractTypeMap_Sydney = MboContractsUtil.buildContractsMap_Sydney();
            mboContract.setContractType(contractTypeMap_Sydney.get(mboContract.getMboId()));
        } else {
            HashMap<Integer, String> contractTypeMap_Darwin = MboContractsUtil.buildContractsMap_Darwin();
            mboContract.setContractType(contractTypeMap_Darwin.get(mboContract.getMboId()));
        }

        mboContract.setMboId(contract.getMboId());
        mboContract.setName(contract.getName());
        mboContract.setAssignsMembershipName(contract.getAssignsMembershipName());
        mboContract.setAssignsMembershipId(contract.getAssignsMembershipId());
        mboContract.setSoldOnline(contract.isSoldOnline());
        mboContract.setAutoPayScheduleFrequencyType(contract.getAutopaySchedule().getFrequencyType());
        mboContract.setAutoPayScheduleValue(contract.getAutopaySchedule().getFrequencyValue());
        mboContract.setAutoPayScheduleTimeUnit(contract.getAutopaySchedule().getFrequencyTimeUnit());
        mboContract.setNumberOfAutoPays(contract.getNumberOfAutoPays());
        mboContract.setAutoPayTriggerType(contract.getAutoPayTriggerType());
        mboContract.setActionUponCompletionAutoPays(contract.getActionUponCompletionAutoPays());
        mboContract.setClientChargedOn(contract.getClientChargedOn());
        mboContract.setDiscountAmount(contract.getDiscountAmount());
        String loc = "";
        if (contract.getLocationIds() != null) {
            for (int l : contract.getLocationIds()) {
                loc += l + ",";
            }
            loc = loc.substring(0, loc.length() - 1);
        }
        mboContract.setLocationId(loc);
        mboContract.setFirstPaymentAmountSubtotal(contract.getFirstPaymentAmountSubtotal());
        mboContract.setFirstPaymentAmountTax(contract.getFirstPaymentAmountTax());
        mboContract.setFirstPaymentAmountTotal(contract.getFirstPaymentAmountTotal());
        mboContract.setReoccuringPaymentAmountSubtotal(contract.getReoccuringPaymentAmountSubtotal());
        mboContract.setReoccuringPaymentAmountTax(contract.getReoccuringPaymentAmountTax());
        mboContract.setReoccuringPaymentAmountTotal(contract.getReoccuringPaymentAmountTotal());
        mboContract.setTotalContractPaymentAmountSubtotal(contract.getTotalContractPaymentAmountSubtotal());
        mboContract.setTotalContractPaymentAmountTax(contract.getTotalContractPaymentAmountTax());
        mboContract.setTotalContractPaymentAmountTotal(contract.getTotalContractPaymentAmountTotal());

        return mboContract;
    }

//    public static MboContract update(Contract contract, MboContract mboContract) {
//        return build(contract, mboContract);
//    }

    private static MboContract build(Contract contract, MboContract mboContract) {

//        MboContract mboContract = MboContract.from(c);

        if (!contract.getLocationPurchaseRestrictionIds().getInt().isEmpty() && contract.getLocationPurchaseRestrictionIds().getInt().contains(4)) {
            HashMap<Integer, String> contractTypeMap_Darwin = MboContractsUtil.buildContractsMap_Darwin();
            mboContract.setContractType(contractTypeMap_Darwin.get(mboContract.getMboId()));

//            logger.info(" DARWIN -> Contract Type = {} : mboId = {} : name = {} : contractType {} : locationId {}",contractTypeMap_Darwin.get(mboContract.getMboId()), mboContract.getMboId(), contract.getName(), mboContract.getContractType(), contract.getLocationPurchaseRestrictionIds().getInt() );
        } else {
            HashMap<Integer, String> contractTypeMap_Sydney = MboContractsUtil.buildContractsMap_Sydney();
            mboContract.setContractType(contractTypeMap_Sydney.get(mboContract.getMboId()));

//            logger.info("SYDNEY -> Contract Type = {} : mboId = {} : name = {} : contractType {} : locationId {}",contractTypeMap_Sydney.get(mboContract.getMboId()), mboContract.getMboId(), contract.getName(), mboContract.getContractType(), contract.getLocationPurchaseRestrictionIds().getInt() );

        }

        mboContract.setMboId(contract.getID().intValue());
        mboContract.setName(contract.getName());
        if (contract.getAssignsMembershipId().getValue() != null) {
            mboContract.setAssignsMembershipId(contract.getAssignsMembershipId().getValue());
        }
        mboContract.setAssignsMembershipName(contract.getAssignsMembershipName());
        mboContract.setSoldOnline(contract.isSoldOnline());
        mboContract.setAutoPayScheduleFrequencyType(contract.getAutopaySchedule().getFrequencyType());
        mboContract.setAutoPayScheduleValue(contract.getAutopaySchedule().getFrequencyValue() != null ? contract.getAutopaySchedule().getFrequencyValue() : 0);
        mboContract.setAutoPayScheduleTimeUnit(contract.getAutopaySchedule().getFrequencyTimeUnit());
        mboContract.setNumberOfAutoPays(contract.getNumberOfAutopays());
        mboContract.setAutoPayTriggerType(contract.getAutopayTriggerType());
        mboContract.setActionUponCompletionAutoPays(contract.getActionUponCompletionOfAutopays());
        mboContract.setClientChargedOn(contract.getClientsChargedOn());
        mboContract.setDiscountAmount(contract.getDiscountAmount().floatValue());

        String loc = "";
        if (contract.getLocationPurchaseRestrictionIds() != null) {
            for (int l : contract.getLocationPurchaseRestrictionIds().getInt()) {
                loc += l + ",";
            }
            loc = loc.substring(0, loc.length() - 1);
        }

        mboContract.setLocationId(loc);
        mboContract.setFirstPaymentAmountSubtotal(contract.getFirstPaymentAmountSubtotal().floatValue());
        mboContract.setFirstPaymentAmountTax(contract.getFirstPaymentAmountTax().floatValue());
        mboContract.setFirstPaymentAmountTotal(contract.getFirstPaymentAmountTotal().floatValue());
        mboContract.setReoccuringPaymentAmountSubtotal(contract.getRecurringPaymentAmountSubtotal().floatValue());
        mboContract.setReoccuringPaymentAmountTax(contract.getRecurringPaymentAmountTax().floatValue());
        mboContract.setReoccuringPaymentAmountTotal(contract.getRecurringPaymentAmountTotal().floatValue());
        mboContract.setTotalContractPaymentAmountSubtotal(contract.getTotalContractAmountSubtotal().floatValue());
        mboContract.setTotalContractPaymentAmountTax(contract.getTotalContractAmountTax().floatValue());
        mboContract.setTotalContractPaymentAmountTotal(contract.getTotalContractAmountTotal().floatValue());

        return mboContract;
    }

    public long getId() {
        return id;
    }

    public int getMboId() {
        return mboId;
    }

    public void setMboId(int mboId) {
        this.mboId = mboId;
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

    public String getAutoPayScheduleFrequencyType() {
        return autoPayScheduleFrequencyType;
    }

    public void setAutoPayScheduleFrequencyType(String autoPayScheduleFrequencyType) {
        this.autoPayScheduleFrequencyType = autoPayScheduleFrequencyType;
    }

    public int getAutoPayScheduleValue() {
        return autoPayScheduleValue;
    }

    public void setAutoPayScheduleValue(int autoPayScheduleValue) {
        this.autoPayScheduleValue = autoPayScheduleValue;
    }

    public String getAutoPayScheduleTimeUnit() {
        return autoPayScheduleTimeUnit;
    }

    public void setAutoPayScheduleTimeUnit(String autoPayScheduleTimeUnit) {
        this.autoPayScheduleTimeUnit = autoPayScheduleTimeUnit;
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

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public void setTotalContractPaymentAmountTotal(float totalContractPaymentAmountTotal) {
        this.totalContractPaymentAmountTotal = totalContractPaymentAmountTotal;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    @Override
    public String toString() {
        return "MboContract{" +
                "id=" + id +
                ", mboId=" + mboId +
                ", name='" + name + '\'' +
                ", assignsMembershipId=" + assignsMembershipId +
                ", assignsMembershipName='" + assignsMembershipName + '\'' +
                ", soldOnline=" + soldOnline +
                ", autoPayScheduleFrequencyType='" + autoPayScheduleFrequencyType + '\'' +
                ", autoPayScheduleValue=" + autoPayScheduleValue +
                ", autoPayScheduleTimeUnit='" + autoPayScheduleTimeUnit + '\'' +
                ", numberOfAutoPays=" + numberOfAutoPays +
                ", autoPayTriggerType='" + autoPayTriggerType + '\'' +
                ", actionUponCompletionAutoPays='" + actionUponCompletionAutoPays + '\'' +
                ", clientChargedOn='" + clientChargedOn + '\'' +
                ", discountAmount=" + discountAmount +
                ", locationId='" + locationId + '\'' +
                ", firstPaymentAmountSubtotal=" + firstPaymentAmountSubtotal +
                ", firstPaymentAmountTax=" + firstPaymentAmountTax +
                ", firstPaymentAmountTotal=" + firstPaymentAmountTotal +
                ", reoccuringPaymentAmountSubtotal=" + reoccuringPaymentAmountSubtotal +
                ", reoccuringPaymentAmountTax=" + reoccuringPaymentAmountTax +
                ", reoccuringPaymentAmountTotal=" + reoccuringPaymentAmountTotal +
                ", totalContractPaymentAmountSubtotal=" + totalContractPaymentAmountSubtotal +
                ", totalContractPaymentAmountTax=" + totalContractPaymentAmountTax +
                ", totalContractPaymentAmountTotal=" + totalContractPaymentAmountTotal +
                ", contractType='" + contractType + '\'' +
                '}';
    }
}
