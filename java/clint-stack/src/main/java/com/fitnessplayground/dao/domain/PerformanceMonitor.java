package com.fitnessplayground.dao.domain;

import com.fitnessplayground.util.Helpers;

import javax.persistence.*;

@Entity
public class PerformanceMonitor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String createDate;
    private String resultStatus;
    private String milliSecToComplete;
    private String actionEntity;
    private String actionEntityId;
    private String actionType;

    public PerformanceMonitor() {
    }

    public PerformanceMonitor(String resultStatus, String milliSecToComplete, String actionEntity, String actionEntityId, String actionType) {
        this.createDate = Helpers.getDateNow();
        this.resultStatus = resultStatus;
        this.milliSecToComplete = milliSecToComplete;
        this.actionEntity = actionEntity;
        this.actionEntityId = actionEntityId;
        this.actionType = actionType;
    }

    public long getId() {
        return id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getMilliSecToComplete() {
        return milliSecToComplete;
    }

    public void setMilliSecToComplete(String milliSecToComplete) {
        this.milliSecToComplete = milliSecToComplete;
    }

    public String getActionEntity() {
        return actionEntity;
    }

    public void setActionEntity(String actionEntity) {
        this.actionEntity = actionEntity;
    }

    public String getActionEntityId() {
        return actionEntityId;
    }

    public void setActionEntityId(String actionEntityId) {
        this.actionEntityId = actionEntityId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    @Override
    public String toString() {
        return "PerformanceMonitor{" +
                "id=" + id +
                ", createDate='" + createDate + '\'' +
                ", resultStatus='" + resultStatus + '\'' +
                ", milliSecToComplete='" + milliSecToComplete + '\'' +
                ", actionEntity='" + actionEntity + '\'' +
                ", actionEntityId='" + actionEntityId + '\'' +
                ", actionType='" + actionType + '\'' +
                '}';
    }
}
