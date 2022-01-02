package com.fitnessplayground.dao.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
public class MemberStatusReport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    int newClients;
    int terminatedClients;
    int suspendedClients;
    int declinedClients;
    int activeClients;
    int expiredClients;
    int nonMemberClients;

    int locationId;
    String createDate;

    public MemberStatusReport() {
    }

    public MemberStatusReport(int newClients,
                              int terminatedClients,
                              int suspendedClients,
                              int declinedClients,
                              int activeClients,
                              int expiredClients,
                              int nonMemberClients,
                              int locationId) {

        this.newClients = newClients;
        this.terminatedClients = terminatedClients;
        this.suspendedClients = suspendedClients;
        this.declinedClients = declinedClients;
        this.activeClients = activeClients;
        this.expiredClients = expiredClients;
        this.nonMemberClients = nonMemberClients;
        this.locationId = locationId;
        this.createDate = LocalDateTime.now(ZoneId.of("Australia/Sydney")).toString();
    }

    public long getId() {
        return id;
    }

    public int getNewClients() {
        return newClients;
    }

    public void setNewClients(int newClients) {
        this.newClients = newClients;
    }

    public int getTerminatedClients() {
        return terminatedClients;
    }

    public void setTerminatedClients(int terminatedClients) {
        this.terminatedClients = terminatedClients;
    }

    public int getSuspendedClients() {
        return suspendedClients;
    }

    public void setSuspendedClients(int suspendedClients) {
        this.suspendedClients = suspendedClients;
    }

    public int getDeclinedClients() {
        return declinedClients;
    }

    public void setDeclinedClients(int declinedClients) {
        this.declinedClients = declinedClients;
    }

    public int getActiveClients() {
        return activeClients;
    }

    public void setActiveClients(int activeClients) {
        this.activeClients = activeClients;
    }

    public int getExpiredClients() {
        return expiredClients;
    }

    public void setExpiredClients(int expiredClients) {
        this.expiredClients = expiredClients;
    }

    public int getNonMemberClients() {
        return nonMemberClients;
    }

    public void setNonMemberClients(int nonMemberClients) {
        this.nonMemberClients = nonMemberClients;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "MemberStatusReport{" +
                "id=" + id +
                ", newClients=" + newClients +
                ", terminatedClients=" + terminatedClients +
                ", suspendedClients=" + suspendedClients +
                ", declinedClients=" + declinedClients +
                ", activeClients=" + activeClients +
                ", expiredClients=" + expiredClients +
                ", nonMemberClients=" + nonMemberClients +
                ", locationId=" + locationId +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
