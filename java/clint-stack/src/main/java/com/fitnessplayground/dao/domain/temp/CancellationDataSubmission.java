package com.fitnessplayground.dao.domain.temp;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CancellationDataSubmission {

    @JsonProperty("fs_formId")
    private String fs_formId;
    @JsonProperty("fs_uniqueId")
    private String fs_uniqueId;
    @JsonProperty("fs_formUrl")
    private String fs_formUrl;
    @JsonProperty("createDate")
    private String createDate;
    @JsonProperty("updateDate")
    private String updateDate;
    @JsonProperty("communicationStatus")
    private String communicationStatus;
    @JsonProperty("status")
    private String status;
    @JsonProperty("submission_datetime")
    private String submission_datetime;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("email")
    private String email;
    @JsonProperty("gymName")
    private String gymName;
    @JsonProperty("locationId")
    private String locationId;
    @JsonProperty("cancellationOptions")
    private String cancellationOptions;
    @JsonProperty("membershipType")
    private String membershipType;
    @JsonProperty("eligibilityCheck")
    private String eligibilityCheck;

    //    Transferring to a Friend; - Section
    @JsonProperty("friendFirstName")
    private String friendFirstName;
    @JsonProperty("friendLastName")
    private String friendLastName;
    @JsonProperty("friendPhone")
    private String friendPhone;
    @JsonProperty("friendEmail")
    private String friendEmail;
    @JsonProperty("friendTransferDate")
    private String friendTransferDate;
    @JsonProperty("friendMembershipName")
    private String friendMembershipName;
    @JsonProperty("friendNumberOutstandingDebits")
    private String friendNumberOutstandingDebits;
    @JsonProperty("friendOngoingFortnightlyDirectDebit")
    private String friendOngoingFortnightlyDirectDebit;
    @JsonProperty("friendTermsAndConditions")
    private String friendTermsAndConditions;
    @JsonProperty("friendSignature")
    private String friendSignature;
    @JsonProperty("friendAccessKeyNumber")
    private String friendAccessKeyNumber;
    @JsonProperty("friendPaymentAuthorisation")
    private String friendPaymentAuthorisation;
    @JsonProperty("friendTransferType")
    private String friendTransferType;
    @JsonProperty("friendMembershipNamePif")
    private String friendMembershipNamePif;
    @JsonProperty("friendMembershipExpires")
    private String friendMembershipExpires;
    @JsonProperty("friendMembershipNamePt")
    private String friendMembershipNamePt;
    @JsonProperty("friendNumberOutstandingDebitsPt")
    private String friendNumberOutstandingDebitsPt;
    @JsonProperty("friendPaymentMethod")
    private String friendPaymentMethod;

    //    Let Us Help You; - Section
    @JsonProperty("reasonForCancellation")
    private String reasonForCancellation;
    @JsonProperty("reasonMotivation")
    private String reasonMotivation;
    @JsonProperty("reasonPoorExperience")
    private String reasonPoorExperience;
    @JsonProperty("reasonFinancial")
    private String reasonFinancial;
    @JsonProperty("reasonMedical")
    private String reasonMedical;
    @JsonProperty("virtualPlaygroundTrigger")
    private String virtualPlaygroundTrigger;
    @JsonProperty("saveUpgradeMembership")
    private String saveUpgradeMembership;
    @JsonProperty("saveDowngradeMembership")
    private String saveDowngradeMembership;
    @JsonProperty("saveMotivationSparkAmount")
    private String saveMotivationSparkAmount;
    @JsonProperty("saveMotivationFromDate")
    private String saveMotivationFromDate;
    @JsonProperty("saveMotivationToDate")
    private String saveMotivationToDate;
    @JsonProperty("saveMotivationNextDebitDate")
    private String saveMotivationNextDebitDate;

    //    Membership Freeze; - Section
    @JsonProperty("saveFreezeFromDate")
    private String saveFreezeFromDate;
    @JsonProperty("saveFreezeToDate")
    private String saveFreezeToDate;
    @JsonProperty("saveMedicalDocumentation")
    private String saveMedicalDocumentation;

    //    Proceed With Cancellation; - Section
    @JsonProperty("cancellationDate")
    private String cancellationDate;
    @JsonProperty("cancellationLastDebitDate")
    private String cancellationLastDebitDate;
    @JsonProperty("cancellationChecklist")
    private String cancellationChecklist;
    @JsonProperty("cancellationSupportingDocumentation")
    private String cancellationSupportingDocumentation;
    @JsonProperty("hasCoach")
    private Boolean hasCoach;

    //    Membership Cancellation Feedback; - Section
    @JsonProperty("netPromoterScore")
    private String netPromoterScore;
    @JsonProperty("isReferrer")
    private Boolean isReferrer;
    @JsonProperty("referralName")
    private String referralName;
    @JsonProperty("referralEmail")
    private String referralEmail;
    @JsonProperty("referralPhone")
    private String referralPhone;
    @JsonProperty("feedback")
    private String feedback;

    //    Personal Training ; - Section
    @JsonProperty("ptReasonForCancelling")
    private String ptReasonForCancelling;
    @JsonProperty("personalTrainer")
    private String personalTrainer;
    @JsonProperty("personalTrainerName")
    private String personalTrainerName;
    @JsonProperty("ptNumberSessionsCompleted")
    private String ptNumberSessionsCompleted;
    @JsonProperty("ptSaveOptions")
    private String ptSaveOptions;
    @JsonProperty("ptSaveSuspensionFromDate")
    private String ptSaveSuspensionFromDate;
    @JsonProperty("ptSaveSuspensionToDate")
    private String ptSaveSuspensionToDate;
    @JsonProperty("ptSaveSuspensionNextDebitDate")
    private String ptSaveSuspensionNextDebitDate;
    @JsonProperty("ptCancellationDate")
    private String ptCancellationDate;
    @JsonProperty("ptLastDebitDate")
    private String ptLastDebitDate;

    //    Personal Training Cancellation Feedback; - Section
    @JsonProperty("ptCancellationChecklist")
    private String ptCancellationChecklist;
    @JsonProperty("didYouAchieveYourGoal")
    private String didYouAchieveYourGoal;
    @JsonProperty("reasonGoalNotAchieved")
    private String reasonGoalNotAchieved;
    @JsonProperty("ptNetPromoterScore")
    private String ptNetPromoterScore;
    @JsonProperty("coachFeedbackPositive")
    private String coachFeedbackPositive;
    @JsonProperty("coachFeedbackNegative")
    private String coachFeedbackNegative;

    //    Authorisation - Section
    @JsonProperty("directDebitAcknowledgement")
    private String directDebitAcknowledgement;
    @JsonProperty("signature")
    private String signature;
    @JsonProperty("staffMember")
    private String staffMember;
    @JsonProperty("staffName")
    private String staffName;
    @JsonProperty("notes")
    private String notes;

    // Version 1 Added Fields
    @JsonProperty("acknowledgement")
    private String acknowledgement;
    @JsonProperty("timeAvailability")
    private String timeAvailability;
    @JsonProperty("outcome")
    private String outcome;
    @JsonProperty("saveFreezePeriod")
    private String saveFreezePeriod;
    @JsonProperty("newPersonalTrainer")
    private String newPersonalTrainer;
    @JsonProperty("newPersonalTrainerName")
    private String newPersonalTrainerName;
    @JsonProperty("savePersonalTrainer")
    private String savePersonalTrainer;
    @JsonProperty("savePersonalTrainerName")
    private String savePersonalTrainerName;
    @JsonProperty("savePersonalTraining")
    private String savePersonalTraining;
    @JsonProperty("saveUpgradeType")
    private String saveUpgradeType;
    @JsonProperty("saveDowngradeType")
    private String saveDowngradeType;
    @JsonProperty("saveOptions")
    private String saveOptions;
    @JsonProperty("fairPlayStartDate")
    private String fairPlayStartDate;
    @JsonProperty("fairPlayToDate")
    private String fairPlayToDate;
    @JsonProperty("friendTransferAppointmentDate")
    private String friendTransferAppointmentDate;
    @JsonProperty("saveBuddyName")
    private String saveBuddyName;
    @JsonProperty("saveBuddyPhone")
    private String saveBuddyPhone;
    @JsonProperty("saveBuddyEmail")
    private String saveBuddyEmail;
    @JsonProperty("cancellationLastAccessDate")
    private String cancellationLastAccessDate;
    @JsonProperty("accountPaymentsToBeDebited")
    private String accountPaymentsToBeDebited;
    @JsonProperty("totalDebitAmount")
    private String totalDebitAmount;
    @JsonProperty("totalAmountWaived")
    private String totalAmountWaived;
    @JsonProperty("paymentWaivedReason")
    private String paymentWaivedReason;
    @JsonProperty("managerApproval")
    private String managerApproval;

    @JsonProperty("memberBankDetail")
    private BankDetailSubmission memberBankDetail;
    @JsonProperty("creditCard")
    private CreditCardSubmission memberCreditCard;

    public CancellationDataSubmission() {
    }

    public String getFs_formId() {
        return fs_formId;
    }

    public void setFs_formId(String fs_formId) {
        this.fs_formId = fs_formId;
    }

    public String getFs_uniqueId() {
        return fs_uniqueId;
    }

    public void setFs_uniqueId(String fs_uniqueId) {
        this.fs_uniqueId = fs_uniqueId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGymName() {
        return gymName;
    }

    public void setGymName(String gymName) {
        this.gymName = gymName;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getCancellationOptions() {
        return cancellationOptions;
    }

    public void setCancellationOptions(String cancellationOptions) {
        this.cancellationOptions = cancellationOptions;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public String getEligibilityCheck() {
        return eligibilityCheck;
    }

    public void setEligibilityCheck(String eligibilityCheck) {
        this.eligibilityCheck = eligibilityCheck;
    }

    public String getFriendFirstName() {
        return friendFirstName;
    }

    public void setFriendFirstName(String friendFirstName) {
        this.friendFirstName = friendFirstName;
    }

    public String getFriendLastName() {
        return friendLastName;
    }

    public void setFriendLastName(String friendLastName) {
        this.friendLastName = friendLastName;
    }

    public String getFriendPhone() {
        return friendPhone;
    }

    public void setFriendPhone(String friendPhone) {
        this.friendPhone = friendPhone;
    }

    public String getFriendEmail() {
        return friendEmail;
    }

    public void setFriendEmail(String friendEmail) {
        this.friendEmail = friendEmail;
    }

    public String getFriendTransferDate() {
        return friendTransferDate;
    }

    public void setFriendTransferDate(String friendTransferDate) {
        this.friendTransferDate = friendTransferDate;
    }

    public String getFriendMembershipName() {
        return friendMembershipName;
    }

    public void setFriendMembershipName(String friendMembershipName) {
        this.friendMembershipName = friendMembershipName;
    }

    public String getFriendNumberOutstandingDebits() {
        return friendNumberOutstandingDebits;
    }

    public void setFriendNumberOutstandingDebits(String friendNumberOutstandingDebits) {
        this.friendNumberOutstandingDebits = friendNumberOutstandingDebits;
    }

    public String getFriendOngoingFortnightlyDirectDebit() {
        return friendOngoingFortnightlyDirectDebit;
    }

    public void setFriendOngoingFortnightlyDirectDebit(String friendOngoingFortnightlyDirectDebit) {
        this.friendOngoingFortnightlyDirectDebit = friendOngoingFortnightlyDirectDebit;
    }

    public String getFriendTermsAndConditions() {
        return friendTermsAndConditions;
    }

    public void setFriendTermsAndConditions(String friendTermsAndConditions) {
        this.friendTermsAndConditions = friendTermsAndConditions;
    }

    public String getFriendSignature() {
        return friendSignature;
    }

    public void setFriendSignature(String friendSignature) {
        this.friendSignature = friendSignature;
    }

    public String getFriendAccessKeyNumber() {
        return friendAccessKeyNumber;
    }

    public void setFriendAccessKeyNumber(String friendAccessKeyNumber) {
        this.friendAccessKeyNumber = friendAccessKeyNumber;
    }

    public String getFriendPaymentAuthorisation() {
        return friendPaymentAuthorisation;
    }

    public void setFriendPaymentAuthorisation(String friendPaymentAuthorisation) {
        this.friendPaymentAuthorisation = friendPaymentAuthorisation;
    }

    public String getFriendTransferType() {
        return friendTransferType;
    }

    public void setFriendTransferType(String friendTransferType) {
        this.friendTransferType = friendTransferType;
    }

    public String getFriendMembershipNamePif() {
        return friendMembershipNamePif;
    }

    public void setFriendMembershipNamePif(String friendMembershipNamePif) {
        this.friendMembershipNamePif = friendMembershipNamePif;
    }

    public String getFriendMembershipExpires() {
        return friendMembershipExpires;
    }

    public void setFriendMembershipExpires(String friendMembershipExpires) {
        this.friendMembershipExpires = friendMembershipExpires;
    }

    public String getFriendMembershipNamePt() {
        return friendMembershipNamePt;
    }

    public void setFriendMembershipNamePt(String friendMembershipNamePt) {
        this.friendMembershipNamePt = friendMembershipNamePt;
    }

    public String getFriendNumberOutstandingDebitsPt() {
        return friendNumberOutstandingDebitsPt;
    }

    public void setFriendNumberOutstandingDebitsPt(String friendNumberOutstandingDebitsPt) {
        this.friendNumberOutstandingDebitsPt = friendNumberOutstandingDebitsPt;
    }

    public String getFriendPaymentMethod() {
        return friendPaymentMethod;
    }

    public void setFriendPaymentMethod(String friendPaymentMethod) {
        this.friendPaymentMethod = friendPaymentMethod;
    }

    public String getReasonForCancellation() {
        return reasonForCancellation;
    }

    public void setReasonForCancellation(String reasonForCancellation) {
        this.reasonForCancellation = reasonForCancellation;
    }

    public String getReasonMotivation() {
        return reasonMotivation;
    }

    public void setReasonMotivation(String reasonMotivation) {
        this.reasonMotivation = reasonMotivation;
    }

    public String getReasonPoorExperience() {
        return reasonPoorExperience;
    }

    public void setReasonPoorExperience(String reasonPoorExperience) {
        this.reasonPoorExperience = reasonPoorExperience;
    }

    public String getReasonFinancial() {
        return reasonFinancial;
    }

    public void setReasonFinancial(String reasonFinancial) {
        this.reasonFinancial = reasonFinancial;
    }

    public String getReasonMedical() {
        return reasonMedical;
    }

    public void setReasonMedical(String reasonMedical) {
        this.reasonMedical = reasonMedical;
    }

    public String getVirtualPlaygroundTrigger() {
        return virtualPlaygroundTrigger;
    }

    public void setVirtualPlaygroundTrigger(String virtualPlaygroundTrigger) {
        this.virtualPlaygroundTrigger = virtualPlaygroundTrigger;
    }

    public String getSaveUpgradeMembership() {
        return saveUpgradeMembership;
    }

    public void setSaveUpgradeMembership(String saveUpgradeMembership) {
        this.saveUpgradeMembership = saveUpgradeMembership;
    }

    public String getSaveDowngradeMembership() {
        return saveDowngradeMembership;
    }

    public void setSaveDowngradeMembership(String saveDowngradeMembership) {
        this.saveDowngradeMembership = saveDowngradeMembership;
    }

    public String getSaveMotivationSparkAmount() {
        return saveMotivationSparkAmount;
    }

    public void setSaveMotivationSparkAmount(String saveMotivationSparkAmount) {
        this.saveMotivationSparkAmount = saveMotivationSparkAmount;
    }

    public String getSaveMotivationFromDate() {
        return saveMotivationFromDate;
    }

    public void setSaveMotivationFromDate(String saveMotivationFromDate) {
        this.saveMotivationFromDate = saveMotivationFromDate;
    }

    public String getSaveMotivationToDate() {
        return saveMotivationToDate;
    }

    public void setSaveMotivationToDate(String saveMotivationToDate) {
        this.saveMotivationToDate = saveMotivationToDate;
    }

    public String getSaveMotivationNextDebitDate() {
        return saveMotivationNextDebitDate;
    }

    public void setSaveMotivationNextDebitDate(String saveMotivationNextDebitDate) {
        this.saveMotivationNextDebitDate = saveMotivationNextDebitDate;
    }

    public String getSaveFreezeFromDate() {
        return saveFreezeFromDate;
    }

    public void setSaveFreezeFromDate(String saveFreezeFromDate) {
        this.saveFreezeFromDate = saveFreezeFromDate;
    }

    public String getSaveFreezeToDate() {
        return saveFreezeToDate;
    }

    public void setSaveFreezeToDate(String saveFreezeToDate) {
        this.saveFreezeToDate = saveFreezeToDate;
    }

    public String getSaveMedicalDocumentation() {
        return saveMedicalDocumentation;
    }

    public void setSaveMedicalDocumentation(String saveMedicalDocumentation) {
        this.saveMedicalDocumentation = saveMedicalDocumentation;
    }

    public String getCancellationDate() {
        return cancellationDate;
    }

    public void setCancellationDate(String cancellationDate) {
        this.cancellationDate = cancellationDate;
    }

    public String getCancellationLastDebitDate() {
        return cancellationLastDebitDate;
    }

    public void setCancellationLastDebitDate(String cancellationLastDebitDate) {
        this.cancellationLastDebitDate = cancellationLastDebitDate;
    }

    public String getCancellationChecklist() {
        return cancellationChecklist;
    }

    public void setCancellationChecklist(String cancellationChecklist) {
        this.cancellationChecklist = cancellationChecklist;
    }

    public String getCancellationSupportingDocumentation() {
        return cancellationSupportingDocumentation;
    }

    public void setCancellationSupportingDocumentation(String cancellationSupportingDocumentation) {
        this.cancellationSupportingDocumentation = cancellationSupportingDocumentation;
    }

    public Boolean getHasCoach() {
        return hasCoach;
    }

    public void setHasCoach(Boolean hasCoach) {
        this.hasCoach = hasCoach;
    }

    public String getNetPromoterScore() {
        return netPromoterScore;
    }

    public void setNetPromoterScore(String netPromoterScore) {
        this.netPromoterScore = netPromoterScore;
    }

    public Boolean getReferrer() {
        return isReferrer;
    }

    public void setReferrer(Boolean referrer) {
        isReferrer = referrer;
    }

    public String getReferralName() {
        return referralName;
    }

    public void setReferralName(String referralName) {
        this.referralName = referralName;
    }

    public String getReferralEmail() {
        return referralEmail;
    }

    public void setReferralEmail(String referralEmail) {
        this.referralEmail = referralEmail;
    }

    public String getReferralPhone() {
        return referralPhone;
    }

    public void setReferralPhone(String referralPhone) {
        this.referralPhone = referralPhone;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getPtReasonForCancelling() {
        return ptReasonForCancelling;
    }

    public void setPtReasonForCancelling(String ptReasonForCancelling) {
        this.ptReasonForCancelling = ptReasonForCancelling;
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

    public String getPtNumberSessionsCompleted() {
        return ptNumberSessionsCompleted;
    }

    public void setPtNumberSessionsCompleted(String ptNumberSessionsCompleted) {
        this.ptNumberSessionsCompleted = ptNumberSessionsCompleted;
    }

    public String getPtSaveOptions() {
        return ptSaveOptions;
    }

    public void setPtSaveOptions(String ptSaveOptions) {
        this.ptSaveOptions = ptSaveOptions;
    }

    public String getPtSaveSuspensionFromDate() {
        return ptSaveSuspensionFromDate;
    }

    public void setPtSaveSuspensionFromDate(String ptSaveSuspensionFromDate) {
        this.ptSaveSuspensionFromDate = ptSaveSuspensionFromDate;
    }

    public String getPtSaveSuspensionToDate() {
        return ptSaveSuspensionToDate;
    }

    public void setPtSaveSuspensionToDate(String ptSaveSuspensionToDate) {
        this.ptSaveSuspensionToDate = ptSaveSuspensionToDate;
    }

    public String getPtSaveSuspensionNextDebitDate() {
        return ptSaveSuspensionNextDebitDate;
    }

    public void setPtSaveSuspensionNextDebitDate(String ptSaveSuspensionNextDebitDate) {
        this.ptSaveSuspensionNextDebitDate = ptSaveSuspensionNextDebitDate;
    }

    public String getPtCancellationDate() {
        return ptCancellationDate;
    }

    public void setPtCancellationDate(String ptCancellationDate) {
        this.ptCancellationDate = ptCancellationDate;
    }

    public String getPtLastDebitDate() {
        return ptLastDebitDate;
    }

    public void setPtLastDebitDate(String ptLastDebitDate) {
        this.ptLastDebitDate = ptLastDebitDate;
    }

    public String getPtCancellationChecklist() {
        return ptCancellationChecklist;
    }

    public void setPtCancellationChecklist(String ptCancellationChecklist) {
        this.ptCancellationChecklist = ptCancellationChecklist;
    }

    public String getDidYouAchieveYourGoal() {
        return didYouAchieveYourGoal;
    }

    public void setDidYouAchieveYourGoal(String didYouAchieveYourGoal) {
        this.didYouAchieveYourGoal = didYouAchieveYourGoal;
    }

    public String getReasonGoalNotAchieved() {
        return reasonGoalNotAchieved;
    }

    public void setReasonGoalNotAchieved(String reasonGoalNotAchieved) {
        this.reasonGoalNotAchieved = reasonGoalNotAchieved;
    }

    public String getPtNetPromoterScore() {
        return ptNetPromoterScore;
    }

    public void setPtNetPromoterScore(String ptNetPromoterScore) {
        this.ptNetPromoterScore = ptNetPromoterScore;
    }

    public String getCoachFeedbackPositive() {
        return coachFeedbackPositive;
    }

    public void setCoachFeedbackPositive(String coachFeedbackPositive) {
        this.coachFeedbackPositive = coachFeedbackPositive;
    }

    public String getCoachFeedbackNegative() {
        return coachFeedbackNegative;
    }

    public void setCoachFeedbackNegative(String coachFeedbackNegative) {
        this.coachFeedbackNegative = coachFeedbackNegative;
    }

    public String getDirectDebitAcknowledgement() {
        return directDebitAcknowledgement;
    }

    public void setDirectDebitAcknowledgement(String directDebitAcknowledgement) {
        this.directDebitAcknowledgement = directDebitAcknowledgement;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public BankDetailSubmission getMemberBankDetail() {
        return memberBankDetail;
    }

    public void setMemberBankDetail(BankDetailSubmission memberBankDetail) {
        this.memberBankDetail = memberBankDetail;
    }

    public CreditCardSubmission getMemberCreditCard() {
        return memberCreditCard;
    }

    public void setMemberCreditCard(CreditCardSubmission memberCreditCard) {
        this.memberCreditCard = memberCreditCard;
    }

    public String getAcknowledgement() {
        return acknowledgement;
    }

    public void setAcknowledgement(String acknowledgement) {
        this.acknowledgement = acknowledgement;
    }

    public String getTimeAvailability() {
        return timeAvailability;
    }

    public void setTimeAvailability(String timeAvailability) {
        this.timeAvailability = timeAvailability;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getSaveFreezePeriod() {
        return saveFreezePeriod;
    }

    public void setSaveFreezePeriod(String saveFreezePeriod) {
        this.saveFreezePeriod = saveFreezePeriod;
    }

    public String getNewPersonalTrainer() {
        return newPersonalTrainer;
    }

    public void setNewPersonalTrainer(String newPersonalTrainer) {
        this.newPersonalTrainer = newPersonalTrainer;
    }

    public String getNewPersonalTrainerName() {
        return newPersonalTrainerName;
    }

    public void setNewPersonalTrainerName(String newPersonalTrainerName) {
        this.newPersonalTrainerName = newPersonalTrainerName;
    }

    public String getSavePersonalTrainer() {
        return savePersonalTrainer;
    }

    public void setSavePersonalTrainer(String savePersonalTrainer) {
        this.savePersonalTrainer = savePersonalTrainer;
    }

    public String getSavePersonalTrainerName() {
        return savePersonalTrainerName;
    }

    public void setSavePersonalTrainerName(String savePersonalTrainerName) {
        this.savePersonalTrainerName = savePersonalTrainerName;
    }

    public String getSavePersonalTraining() {
        return savePersonalTraining;
    }

    public void setSavePersonalTraining(String savePersonalTraining) {
        this.savePersonalTraining = savePersonalTraining;
    }

    public String getSaveUpgradeType() {
        return saveUpgradeType;
    }

    public void setSaveUpgradeType(String saveUpgradeType) {
        this.saveUpgradeType = saveUpgradeType;
    }

    public String getSaveDowngradeType() {
        return saveDowngradeType;
    }

    public void setSaveDowngradeType(String saveDowngradeType) {
        this.saveDowngradeType = saveDowngradeType;
    }

    public String getSaveOptions() {
        return saveOptions;
    }

    public void setSaveOptions(String saveOptions) {
        this.saveOptions = saveOptions;
    }

    public String getFairPlayStartDate() {
        return fairPlayStartDate;
    }

    public void setFairPlayStartDate(String fairPlayStartDate) {
        this.fairPlayStartDate = fairPlayStartDate;
    }

    public String getFairPlayToDate() {
        return fairPlayToDate;
    }

    public void setFairPlayToDate(String fairPlayToDate) {
        this.fairPlayToDate = fairPlayToDate;
    }

    public String getFriendTransferAppointmentDate() {
        return friendTransferAppointmentDate;
    }

    public void setFriendTransferAppointmentDate(String friendTransferAppointmentDate) {
        this.friendTransferAppointmentDate = friendTransferAppointmentDate;
    }

    public String getSaveBuddyName() {
        return saveBuddyName;
    }

    public void setSaveBuddyName(String saveBuddyName) {
        this.saveBuddyName = saveBuddyName;
    }

    public String getSaveBuddyPhone() {
        return saveBuddyPhone;
    }

    public void setSaveBuddyPhone(String saveBuddyPhone) {
        this.saveBuddyPhone = saveBuddyPhone;
    }

    public String getSaveBuddyEmail() {
        return saveBuddyEmail;
    }

    public void setSaveBuddyEmail(String saveBuddyEmail) {
        this.saveBuddyEmail = saveBuddyEmail;
    }

    public String getCancellationLastAccessDate() {
        return cancellationLastAccessDate;
    }

    public void setCancellationLastAccessDate(String cancellationLastAccessDate) {
        this.cancellationLastAccessDate = cancellationLastAccessDate;
    }

    public String getAccountPaymentsToBeDebited() {
        return accountPaymentsToBeDebited;
    }

    public void setAccountPaymentsToBeDebited(String accountPaymentsToBeDebited) {
        this.accountPaymentsToBeDebited = accountPaymentsToBeDebited;
    }

    public String getTotalDebitAmount() {
        return totalDebitAmount;
    }

    public void setTotalDebitAmount(String totalDebitAmount) {
        this.totalDebitAmount = totalDebitAmount;
    }

    public String getTotalAmountWaived() {
        return totalAmountWaived;
    }

    public void setTotalAmountWaived(String totalAmountWaived) {
        this.totalAmountWaived = totalAmountWaived;
    }

    public String getPaymentWaivedReason() {
        return paymentWaivedReason;
    }

    public void setPaymentWaivedReason(String paymentWaivedReason) {
        this.paymentWaivedReason = paymentWaivedReason;
    }

    public String getManagerApproval() {
        return managerApproval;
    }

    public void setManagerApproval(String managerApproval) {
        this.managerApproval = managerApproval;
    }

    @Override
    public String toString() {
        return "CancellationDataSubmission{" +
                "fs_formId='" + fs_formId + '\'' +
                ", fs_uniqueId='" + fs_uniqueId + '\'' +
                ", fs_formUrl='" + fs_formUrl + '\'' +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", communicationStatus='" + communicationStatus + '\'' +
                ", status='" + status + '\'' +
                ", submission_datetime='" + submission_datetime + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", gymName='" + gymName + '\'' +
                ", locationId='" + locationId + '\'' +
                ", cancellationOptions='" + cancellationOptions + '\'' +
                ", membershipType='" + membershipType + '\'' +
                ", eligibilityCheck='" + eligibilityCheck + '\'' +
                ", friendFirstName='" + friendFirstName + '\'' +
                ", friendLastName='" + friendLastName + '\'' +
                ", friendPhone='" + friendPhone + '\'' +
                ", friendEmail='" + friendEmail + '\'' +
                ", friendTransferDate='" + friendTransferDate + '\'' +
                ", friendMembershipName='" + friendMembershipName + '\'' +
                ", friendNumberOutstandingDebits='" + friendNumberOutstandingDebits + '\'' +
                ", friendOngoingFortnightlyDirectDebit='" + friendOngoingFortnightlyDirectDebit + '\'' +
                ", friendTermsAndConditions='" + friendTermsAndConditions + '\'' +
                ", friendSignature='" + friendSignature + '\'' +
                ", friendAccessKeyNumber='" + friendAccessKeyNumber + '\'' +
                ", friendPaymentAuthorisation='" + friendPaymentAuthorisation + '\'' +
                ", friendTransferType='" + friendTransferType + '\'' +
                ", friendMembershipNamePif='" + friendMembershipNamePif + '\'' +
                ", friendMembershipExpires='" + friendMembershipExpires + '\'' +
                ", friendMembershipNamePt='" + friendMembershipNamePt + '\'' +
                ", friendNumberOutstandingDebitsPt='" + friendNumberOutstandingDebitsPt + '\'' +
                ", friendPaymentMethod='" + friendPaymentMethod + '\'' +
                ", reasonForCancellation='" + reasonForCancellation + '\'' +
                ", reasonMotivation='" + reasonMotivation + '\'' +
                ", reasonPoorExperience='" + reasonPoorExperience + '\'' +
                ", reasonFinancial='" + reasonFinancial + '\'' +
                ", reasonMedical='" + reasonMedical + '\'' +
                ", virtualPlaygroundTrigger='" + virtualPlaygroundTrigger + '\'' +
                ", saveUpgradeMembership='" + saveUpgradeMembership + '\'' +
                ", saveDowngradeMembership='" + saveDowngradeMembership + '\'' +
                ", saveMotivationSparkAmount='" + saveMotivationSparkAmount + '\'' +
                ", saveMotivationFromDate='" + saveMotivationFromDate + '\'' +
                ", saveMotivationToDate='" + saveMotivationToDate + '\'' +
                ", saveMotivationNextDebitDate='" + saveMotivationNextDebitDate + '\'' +
                ", saveFreezeFromDate='" + saveFreezeFromDate + '\'' +
                ", saveFreezeToDate='" + saveFreezeToDate + '\'' +
                ", saveMedicalDocumentation='" + saveMedicalDocumentation + '\'' +
                ", cancellationDate='" + cancellationDate + '\'' +
                ", cancellationLastDebitDate='" + cancellationLastDebitDate + '\'' +
                ", cancellationChecklist='" + cancellationChecklist + '\'' +
                ", cancellationSupportingDocumentation='" + cancellationSupportingDocumentation + '\'' +
                ", hasCoach=" + hasCoach +
                ", netPromoterScore='" + netPromoterScore + '\'' +
                ", isReferrer=" + isReferrer +
                ", referralName='" + referralName + '\'' +
                ", referralEmail='" + referralEmail + '\'' +
                ", referralPhone='" + referralPhone + '\'' +
                ", feedback='" + feedback + '\'' +
                ", ptReasonForCancelling='" + ptReasonForCancelling + '\'' +
                ", personalTrainer='" + personalTrainer + '\'' +
                ", personalTrainerName='" + personalTrainerName + '\'' +
                ", ptNumberSessionsCompleted='" + ptNumberSessionsCompleted + '\'' +
                ", ptSaveOptions='" + ptSaveOptions + '\'' +
                ", ptSaveSuspensionFromDate='" + ptSaveSuspensionFromDate + '\'' +
                ", ptSaveSuspensionToDate='" + ptSaveSuspensionToDate + '\'' +
                ", ptSaveSuspensionNextDebitDate='" + ptSaveSuspensionNextDebitDate + '\'' +
                ", ptCancellationDate='" + ptCancellationDate + '\'' +
                ", ptLastDebitDate='" + ptLastDebitDate + '\'' +
                ", ptCancellationChecklist='" + ptCancellationChecklist + '\'' +
                ", didYouAchieveYourGoal='" + didYouAchieveYourGoal + '\'' +
                ", reasonGoalNotAchieved='" + reasonGoalNotAchieved + '\'' +
                ", ptNetPromoterScore='" + ptNetPromoterScore + '\'' +
                ", coachFeedbackPositive='" + coachFeedbackPositive + '\'' +
                ", coachFeedbackNegative='" + coachFeedbackNegative + '\'' +
                ", directDebitAcknowledgement='" + directDebitAcknowledgement + '\'' +
                ", signature='" + signature + '\'' +
                ", staffMember='" + staffMember + '\'' +
                ", staffName='" + staffName + '\'' +
                ", notes='" + notes + '\'' +
                ", acknowledgement='" + acknowledgement + '\'' +
                ", timeAvailability='" + timeAvailability + '\'' +
                ", outcome='" + outcome + '\'' +
                ", saveFreezePeriod='" + saveFreezePeriod + '\'' +
                ", newPersonalTrainer='" + newPersonalTrainer + '\'' +
                ", newPersonalTrainerName='" + newPersonalTrainerName + '\'' +
                ", savePersonalTrainer='" + savePersonalTrainer + '\'' +
                ", savePersonalTrainerName='" + savePersonalTrainerName + '\'' +
                ", savePersonalTraining='" + savePersonalTraining + '\'' +
                ", saveUpgradeType='" + saveUpgradeType + '\'' +
                ", saveDowngradeType='" + saveDowngradeType + '\'' +
                ", saveOptions='" + saveOptions + '\'' +
                ", fairPlayStartDate='" + fairPlayStartDate + '\'' +
                ", fairPlayToDate='" + fairPlayToDate + '\'' +
                ", friendTransferAppointmentDate='" + friendTransferAppointmentDate + '\'' +
                ", saveBuddyName='" + saveBuddyName + '\'' +
                ", saveBuddyPhone='" + saveBuddyPhone + '\'' +
                ", saveBuddyEmail='" + saveBuddyEmail + '\'' +
                ", cancellationLastAccessDate='" + cancellationLastAccessDate + '\'' +
                ", accountPaymentsToBeDebited='" + accountPaymentsToBeDebited + '\'' +
                ", totalDebitAmount='" + totalDebitAmount + '\'' +
                ", totalAmountWaived='" + totalAmountWaived + '\'' +
                ", paymentWaivedReason='" + paymentWaivedReason + '\'' +
                ", managerApproval='" + managerApproval + '\'' +
                ", memberBankDetail=" + memberBankDetail +
                ", memberCreditCard=" + memberCreditCard +
                '}';
    }
}
