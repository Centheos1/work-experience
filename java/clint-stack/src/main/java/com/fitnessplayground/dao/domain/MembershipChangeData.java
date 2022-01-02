package com.fitnessplayground.dao.domain;

import javax.persistence.*;

@Entity
public class MembershipChangeData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String fs_uniqueId;
    private String fs_formId;
    private String fs_formUrl;
    private String createDate;
    private String updateDate;
    private String communicationStatus;
    private String status;
    private String submission_datetime;
    private String coachGymName;
    private String staffMemberGymName;
    private String locationId;
    private String phone;
    private String firstName;
    private String lastName;
    private String email;
    private String changeTypeMembership;
    private String changeMembership;
    private String toGymName;
    private String fromMembership;
    private String toMembership;
    private String fromGymName;
    private String membershipNextDebitDate;
    private String membershipSuspensionDuration;
    private String hasPreviouslySuspended;
    private String membershipSuspensionFromDate;
    private String membershipReturnDate;
    private String personalTrainer;
    private String personalTrainerName;
    private String changeCoaching;
    private String coachingModality;
    private String sessionLength;
    private String trainingPackage;
    private String coachingNextDebitDate;
    private String coachingSuspensionDuration;
    private String coachingSuspensionFromDate;
    private String coachingReturnDate;
    private String changeTypeAddOns;
    private String changeCreche;
    private String crecheMembership;
    private String crecheNextDebitDate;
    private String crecheSuspensionFromDate;
    private String crecheReturnDate;
    private String crecheSuspensionDuration;
    private String virtualPlayground;
    private String membershipCurrentDebitAmount;
    private String membershipDebitAmount;
    private String crecheDebitAmount;
    private String virtualPlaygroundDebitAmount;
    private String coachingDebitAmount;
    private String coachingMonthlyDebitAmount;
    private String staffMember;
    private String staffName;
    private String staffChecklist;
    private String notes;
    private String suspensionFeeAcknowledgement;
    private String agreement;
    private String agreementDirectDebit;
    private String signature;

    public MembershipChangeData() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFs_uniqueId() {
        return fs_uniqueId;
    }

    public void setFs_uniqueId(String fs_uniqueId) {
        this.fs_uniqueId = fs_uniqueId;
    }

    public String getFs_formId() {
        return fs_formId;
    }

    public void setFs_formId(String fs_formId) {
        this.fs_formId = fs_formId;
    }

    public String getFs_formUrl() {
        return fs_formUrl;
    }

    public void setFs_formUrl(String fs_formUrl) {
        this.fs_formUrl = fs_formUrl;
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

    public String getCommunicationStatus() {
        return communicationStatus;
    }

    public void setCommunicationStatus(String communicationStatus) {
        this.communicationStatus = communicationStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubmission_datetime() {
        return submission_datetime;
    }

    public void setSubmission_datetime(String submission_datetime) {
        this.submission_datetime = submission_datetime;
    }

    public String getCoachGymName() {
        return coachGymName;
    }

    public void setCoachGymName(String coachGymName) {
        this.coachGymName = coachGymName;
    }

    public String getStaffMemberGymName() {
        return staffMemberGymName;
    }

    public void setStaffMemberGymName(String staffMemberGymName) {
        this.staffMemberGymName = staffMemberGymName;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getChangeTypeMembership() {
        return changeTypeMembership;
    }

    public void setChangeTypeMembership(String changeTypeMembership) {
        this.changeTypeMembership = changeTypeMembership;
    }

    public String getChangeMembership() {
        return changeMembership;
    }

    public void setChangeMembership(String changeMembership) {
        this.changeMembership = changeMembership;
    }

    public String getToGymName() {
        return toGymName;
    }

    public void setToGymName(String toGymName) {
        this.toGymName = toGymName;
    }

    public String getFromMembership() {
        return fromMembership;
    }

    public void setFromMembership(String fromMembership) {
        this.fromMembership = fromMembership;
    }

    public String getToMembership() {
        return toMembership;
    }

    public void setToMembership(String toMembership) {
        this.toMembership = toMembership;
    }

    public String getFromGymName() {
        return fromGymName;
    }

    public void setFromGymName(String fromGymName) {
        this.fromGymName = fromGymName;
    }

    public String getMembershipNextDebitDate() {
        return membershipNextDebitDate;
    }

    public void setMembershipNextDebitDate(String membershipNextDebitDate) {
        this.membershipNextDebitDate = membershipNextDebitDate;
    }

    public String getMembershipSuspensionDuration() {
        return membershipSuspensionDuration;
    }

    public void setMembershipSuspensionDuration(String membershipSuspensionDuration) {
        this.membershipSuspensionDuration = membershipSuspensionDuration;
    }

    public String getHasPreviouslySuspended() {
        return hasPreviouslySuspended;
    }

    public void setHasPreviouslySuspended(String hasPreviouslySuspended) {
        this.hasPreviouslySuspended = hasPreviouslySuspended;
    }

    public String getMembershipSuspensionFromDate() {
        return membershipSuspensionFromDate;
    }

    public void setMembershipSuspensionFromDate(String membershipSuspensionFromDate) {
        this.membershipSuspensionFromDate = membershipSuspensionFromDate;
    }

    public String getMembershipReturnDate() {
        return membershipReturnDate;
    }

    public void setMembershipReturnDate(String membershipReturnDate) {
        this.membershipReturnDate = membershipReturnDate;
    }

    public String getPersonalTrainer() {
        return personalTrainer;
    }

    public void setPersonalTrainer(String personalTrainer) {
        this.personalTrainer = personalTrainer;
    }

    public String getPersonalTrainerName() {
        return personalTrainerName;
    }

    public void setPersonalTrainerName(String personalTrainerName) {
        this.personalTrainerName = personalTrainerName;
    }

    public String getChangeCoaching() {
        return changeCoaching;
    }

    public void setChangeCoaching(String changeCoaching) {
        this.changeCoaching = changeCoaching;
    }

    public String getCoachingModality() {
        return coachingModality;
    }

    public void setCoachingModality(String coachingModality) {
        this.coachingModality = coachingModality;
    }

    public String getSessionLength() {
        return sessionLength;
    }

    public void setSessionLength(String sessionLength) {
        this.sessionLength = sessionLength;
    }

    public String getTrainingPackage() {
        return trainingPackage;
    }

    public void setTrainingPackage(String trainingPackage) {
        this.trainingPackage = trainingPackage;
    }

    public String getCoachingNextDebitDate() {
        return coachingNextDebitDate;
    }

    public void setCoachingNextDebitDate(String coachingNextDebitDate) {
        this.coachingNextDebitDate = coachingNextDebitDate;
    }

    public String getCoachingSuspensionDuration() {
        return coachingSuspensionDuration;
    }

    public void setCoachingSuspensionDuration(String coachingSuspensionDuration) {
        this.coachingSuspensionDuration = coachingSuspensionDuration;
    }

    public String getCoachingSuspensionFromDate() {
        return coachingSuspensionFromDate;
    }

    public void setCoachingSuspensionFromDate(String coachingSuspensionFromDate) {
        this.coachingSuspensionFromDate = coachingSuspensionFromDate;
    }

    public String getCoachingReturnDate() {
        return coachingReturnDate;
    }

    public void setCoachingReturnDate(String coachingReturnDate) {
        this.coachingReturnDate = coachingReturnDate;
    }

    public String getChangeTypeAddOns() {
        return changeTypeAddOns;
    }

    public void setChangeTypeAddOns(String changeTypeAddOns) {
        this.changeTypeAddOns = changeTypeAddOns;
    }

    public String getChangeCreche() {
        return changeCreche;
    }

    public void setChangeCreche(String changeCreche) {
        this.changeCreche = changeCreche;
    }

    public String getCrecheMembership() {
        return crecheMembership;
    }

    public void setCrecheMembership(String crecheMembership) {
        this.crecheMembership = crecheMembership;
    }

    public String getCrecheNextDebitDate() {
        return crecheNextDebitDate;
    }

    public void setCrecheNextDebitDate(String crecheNextDebitDate) {
        this.crecheNextDebitDate = crecheNextDebitDate;
    }

    public String getCrecheSuspensionFromDate() {
        return crecheSuspensionFromDate;
    }

    public void setCrecheSuspensionFromDate(String crecheSuspensionFromDate) {
        this.crecheSuspensionFromDate = crecheSuspensionFromDate;
    }

    public String getCrecheReturnDate() {
        return crecheReturnDate;
    }

    public void setCrecheReturnDate(String crecheReturnDate) {
        this.crecheReturnDate = crecheReturnDate;
    }

    public String getCrecheSuspensionDuration() {
        return crecheSuspensionDuration;
    }

    public void setCrecheSuspensionDuration(String crecheSuspensionDuration) {
        this.crecheSuspensionDuration = crecheSuspensionDuration;
    }

    public String getVirtualPlayground() {
        return virtualPlayground;
    }

    public void setVirtualPlayground(String virtualPlayground) {
        this.virtualPlayground = virtualPlayground;
    }

    public String getMembershipCurrentDebitAmount() {
        return membershipCurrentDebitAmount;
    }

    public void setMembershipCurrentDebitAmount(String membershipCurrentDebitAmount) {
        this.membershipCurrentDebitAmount = membershipCurrentDebitAmount;
    }

    public String getMembershipDebitAmount() {
        return membershipDebitAmount;
    }

    public void setMembershipDebitAmount(String membershipDebitAmount) {
        this.membershipDebitAmount = membershipDebitAmount;
    }

    public String getCrecheDebitAmount() {
        return crecheDebitAmount;
    }

    public void setCrecheDebitAmount(String crecheDebitAmount) {
        this.crecheDebitAmount = crecheDebitAmount;
    }

    public String getVirtualPlaygroundDebitAmount() {
        return virtualPlaygroundDebitAmount;
    }

    public void setVirtualPlaygroundDebitAmount(String virtualPlaygroundDebitAmount) {
        this.virtualPlaygroundDebitAmount = virtualPlaygroundDebitAmount;
    }

    public String getCoachingDebitAmount() {
        return coachingDebitAmount;
    }

    public void setCoachingDebitAmount(String coachingDebitAmount) {
        this.coachingDebitAmount = coachingDebitAmount;
    }

    public String getCoachingMonthlyDebitAmount() {
        return coachingMonthlyDebitAmount;
    }

    public void setCoachingMonthlyDebitAmount(String coachingMonthlyDebitAmount) {
        this.coachingMonthlyDebitAmount = coachingMonthlyDebitAmount;
    }

    public String getStaffMember() {
        return staffMember;
    }

    public void setStaffMember(String staffMember) {
        this.staffMember = staffMember;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffChecklist() {
        return staffChecklist;
    }

    public void setStaffChecklist(String staffChecklist) {
        this.staffChecklist = staffChecklist;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getSuspensionFeeAcknowledgement() {
        return suspensionFeeAcknowledgement;
    }

    public void setSuspensionFeeAcknowledgement(String suspensionFeeAcknowledgement) {
        this.suspensionFeeAcknowledgement = suspensionFeeAcknowledgement;
    }

    public String getAgreement() {
        return agreement;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }

    public String getAgreementDirectDebit() {
        return agreementDirectDebit;
    }

    public void setAgreementDirectDebit(String agreementDirectDebit) {
        this.agreementDirectDebit = agreementDirectDebit;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "MembershipChangeData{" +
                "id=" + id +
                ", fs_uniqueId='" + fs_uniqueId + '\'' +
                ", fs_formId='" + fs_formId + '\'' +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", communicationStatus='" + communicationStatus + '\'' +
                ", status='" + status + '\'' +
                ", submission_datetime='" + submission_datetime + '\'' +
                ", coachGymName='" + coachGymName + '\'' +
                ", staffMemberGymName='" + staffMemberGymName + '\'' +
                ", locationId='" + locationId + '\'' +
                ", phone='" + phone + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", changeTypeMembership='" + changeTypeMembership + '\'' +
                ", changeMembership='" + changeMembership + '\'' +
                ", toGymName='" + toGymName + '\'' +
                ", fromMembership='" + fromMembership + '\'' +
                ", toMembership='" + toMembership + '\'' +
                ", fromGymName='" + fromGymName + '\'' +
                ", membershipNextDebitDate='" + membershipNextDebitDate + '\'' +
                ", membershipSuspensionDuration='" + membershipSuspensionDuration + '\'' +
                ", hasPreviouslySuspended='" + hasPreviouslySuspended + '\'' +
                ", membershipSuspensionFromDate='" + membershipSuspensionFromDate + '\'' +
                ", membershipReturnDate='" + membershipReturnDate + '\'' +
                ", personalTrainer='" + personalTrainer + '\'' +
                ", personalTrainerName='" + personalTrainerName + '\'' +
                ", changeCoaching='" + changeCoaching + '\'' +
                ", coachingModality='" + coachingModality + '\'' +
                ", sessionLength='" + sessionLength + '\'' +
                ", trainingPackage='" + trainingPackage + '\'' +
                ", coachingNextDebitDate='" + coachingNextDebitDate + '\'' +
                ", coachingSuspensionDuration='" + coachingSuspensionDuration + '\'' +
                ", coachingSuspensionFromDate='" + coachingSuspensionFromDate + '\'' +
                ", coachingReturnDate='" + coachingReturnDate + '\'' +
                ", changeTypeAddOns='" + changeTypeAddOns + '\'' +
                ", changeCreche='" + changeCreche + '\'' +
                ", crecheMembership='" + crecheMembership + '\'' +
                ", crecheNextDebitDate='" + crecheNextDebitDate + '\'' +
                ", crecheSuspensionFromDate='" + crecheSuspensionFromDate + '\'' +
                ", crecheReturnDate='" + crecheReturnDate + '\'' +
                ", crecheSuspensionDuration='" + crecheSuspensionDuration + '\'' +
                ", virtualPlayground='" + virtualPlayground + '\'' +
                ", membershipCurrentDebitAmount='" + membershipCurrentDebitAmount + '\'' +
                ", membershipDebitAmount='" + membershipDebitAmount + '\'' +
                ", crecheDebitAmount='" + crecheDebitAmount + '\'' +
                ", virtualPlaygroundDebitAmount='" + virtualPlaygroundDebitAmount + '\'' +
                ", coachingDebitAmount='" + coachingDebitAmount + '\'' +
                ", coachingMonthlyDebitAmount='" + coachingMonthlyDebitAmount + '\'' +
                ", staffMember='" + staffMember + '\'' +
                ", staffName='" + staffName + '\'' +
                ", staffChecklist='" + staffChecklist + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
