package com.fitnessplayground.dao.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DebtPortal {

    @Id
    Long mbo_unique_id;
    String status;
    String communications_status;
    String createDate;
    String updateDate;
    String client;
    String comms_schedule;
    String partial_payment_expiry;
    Double debt_amount;

    public DebtPortal() {
    }

    public Long getMbo_unique_id() {
        return mbo_unique_id;
    }

    public void setMbo_unique_id(Long mbo_unique_id) {
        this.mbo_unique_id = mbo_unique_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCommunications_status() {
        return communications_status;
    }

    public void setCommunications_status(String communications_status) {
        this.communications_status = communications_status;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getComms_schedule() {
        return comms_schedule;
    }

    public void setComms_schedule(String comms_schedule) {
        this.comms_schedule = comms_schedule;
    }

    public String getPartial_payment_expiry() {
        return partial_payment_expiry;
    }

    public void setPartial_payment_expiry(String partial_payment_expiry) {
        this.partial_payment_expiry = partial_payment_expiry;
    }

    public Double getDebt_amount() {
        return debt_amount;
    }

    public void setDebt_amount(Double debt_amount) {
        this.debt_amount = debt_amount;
    }

    @Override
    public String toString() {
        return "DebtPortal{" +
                "mbo_unique_id=" + mbo_unique_id +
                ", status='" + status + '\'' +
                ", communications_status='" + communications_status + '\'' +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", client='" + client + '\'' +
                ", comms_schedule='" + comms_schedule + '\'' +
                ", partial_payment_expiry='" + partial_payment_expiry + '\'' +
                ", debt_amount=" + debt_amount +
                '}';
    }
}
