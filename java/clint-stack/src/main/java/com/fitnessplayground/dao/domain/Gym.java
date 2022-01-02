package com.fitnessplayground.dao.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Gym {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private Integer locationId;
    private String name;
    private Long siteId;
    private String clubManagerId;
    private String personalTrainingManagerId;
    private String groupFitnessManagerId;
    private Integer firstNDaysDiscountQuota;
    private Integer firstNDaysDiscountUsed;
    private Integer accessKeyDiscountQuota;
    private Integer accessKeyDiscountUsed;
    private Integer satisfactionPeriodDiscountQuota;
    private Integer satisfactionPeriodDiscountUsed;
    private String updateDate;
    private String lastResetDate;

    public Gym() {
    }


    public long getId() {
        return id;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getClubManagerId() {
        return clubManagerId;
    }

    public void setClubManagerId(String clubManagerId) {
        this.clubManagerId = clubManagerId;
    }

    public String getPersonalTrainingManagerId() {
        return personalTrainingManagerId;
    }

    public void setPersonalTrainingManagerId(String personalTrainingManagerId) {
        this.personalTrainingManagerId = personalTrainingManagerId;
    }

    public String getGroupFitnessManagerId() {
        return groupFitnessManagerId;
    }

    public void setGroupFitnessManagerId(String groupFitnessManagerId) {
        this.groupFitnessManagerId = groupFitnessManagerId;
    }

    public Integer getFirstNDaysDiscountQuota() {
        return firstNDaysDiscountQuota;
    }

    public void setFirstNDaysDiscountQuota(Integer firstNDaysDiscountQuota) {
        this.firstNDaysDiscountQuota = firstNDaysDiscountQuota;
    }

    public Integer getFirstNDaysDiscountUsed() {
        return firstNDaysDiscountUsed;
    }

    public void setFirstNDaysDiscountUsed(Integer firstNDaysDiscountUsed) {
        this.firstNDaysDiscountUsed = firstNDaysDiscountUsed;
    }

    public Integer getAccessKeyDiscountQuota() {
        return accessKeyDiscountQuota;
    }

    public void setAccessKeyDiscountQuota(Integer accessKeyDiscountQuota) {
        this.accessKeyDiscountQuota = accessKeyDiscountQuota;
    }

    public Integer getAccessKeyDiscountUsed() {
        return accessKeyDiscountUsed;
    }

    public void setAccessKeyDiscountUsed(Integer accessKeyDiscountUsed) {
        this.accessKeyDiscountUsed = accessKeyDiscountUsed;
    }

    public Integer getSatisfactionPeriodDiscountQuota() {
        return satisfactionPeriodDiscountQuota;
    }

    public void setSatisfactionPeriodDiscountQuota(Integer satisfactionPeriodDiscountQuota) {
        this.satisfactionPeriodDiscountQuota = satisfactionPeriodDiscountQuota;
    }

    public Integer getSatisfactionPeriodDiscountUsed() {
        return satisfactionPeriodDiscountUsed;
    }

    public void setSatisfactionPeriodDiscountUsed(Integer satisfactionPeriodDiscountUsed) {
        this.satisfactionPeriodDiscountUsed = satisfactionPeriodDiscountUsed;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getLastResetDate() {
        return lastResetDate;
    }

    public void setLastResetDate(String lastResetDate) {
        this.lastResetDate = lastResetDate;
    }

    @Override
    public String toString() {
        return "Gym{" +
                "id=" + id +
                ", locationId=" + locationId +
                ", name='" + name + '\'' +
                ", siteId=" + siteId +
                ", clubManagerId='" + clubManagerId + '\'' +
                ", personalTrainingManagerId='" + personalTrainingManagerId + '\'' +
                ", groupFitnessManagerId='" + groupFitnessManagerId + '\'' +
                ", firstNDaysDiscountQuota=" + firstNDaysDiscountQuota +
                ", firstNDaysDiscountUsed=" + firstNDaysDiscountUsed +
                ", accessKeyDiscountQuota=" + accessKeyDiscountQuota +
                ", accessKeyDiscountUsed=" + accessKeyDiscountUsed +
                ", satisfactionPeriodDiscountQuota=" + satisfactionPeriodDiscountQuota +
                ", satisfactionPeriodDiscountUsed=" + satisfactionPeriodDiscountUsed +
                ", updateDate='" + updateDate + '\'' +
                ", lastResetDate='" + lastResetDate + '\'' +
                '}';
    }
}
