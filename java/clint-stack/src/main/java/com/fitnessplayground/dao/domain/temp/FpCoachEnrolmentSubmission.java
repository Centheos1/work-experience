package com.fitnessplayground.dao.domain.temp;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FpCoachEnrolmentSubmission {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("createDate")
    private String createDate;
    @JsonProperty("fs_formId")
    private String fs_formId;
    @JsonProperty("fs_uniqueId")
    private String fs_uniqueId;
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
    private Integer locationId;
    @JsonProperty("personalTrainer")
    private String personalTrainer;
    @JsonProperty("personalTrainerName")
    private String personalTrainerName;
    @JsonProperty("coachingModality")
    private String coachingModality;
    @JsonProperty("trainingOptions")
    private String trainingOptions;
    @JsonProperty("sessionLength")
    private String sessionLength;
    @JsonProperty("externalClient")
    private Boolean externalClient;
    @JsonProperty("externalPTSessionPrice")
    private String externalPTSessionPrice;
    @JsonProperty("numberSessioinsPerWeek")
    private String numberSessioinsPerWeek;
    @JsonProperty("mboContractNames")
    private String mboContractNames;
    @JsonProperty("mboContractIds")
    private String mboContractIds;
    @JsonProperty("couponCode")
    private String couponCode;
    @JsonProperty("fnDD")
    private String fnDD;
    @JsonProperty("mtDD")
    private String mtDD;
    @JsonProperty("oneOffTotal")
    private String oneOffTotal;
    @JsonProperty("startDate")
    private String startDate;
    @JsonProperty("paymentType")
    private String paymentType;
    @JsonProperty("memberBankDetail")
    private BankDetailSubmission memberBankDetail;
    @JsonProperty("memberCreditCard")
    private CreditCardSubmission memberCreditCard;
    @JsonProperty("agreement")
    private String agreement;
    @JsonProperty("signature")
    private String signature;
    @JsonProperty("staffMember")
    private String staffMember;
    @JsonProperty("staffName")
    private String staffName;
    @JsonProperty("notes")
    private String notes;
    @JsonProperty("organisedByCoach")
    private Boolean isOrganisedByCoach;

    @JsonProperty("UID")
    private String UID;
    @JsonProperty("activeCampaignId")
    private String activeCampaignId;
    @JsonProperty("communicationsStatus")
    private String communicationsStatus;
    @JsonProperty("status")
    private String status;
    @JsonProperty("updateDate")
    private String updateDate;
    @JsonProperty("useExistingDetails")
    private String useExistingDetails;


    public FpCoachEnrolmentSubmission() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
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

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getCoachingModality() {
        return coachingModality;
    }

    public void setCoachingModality(String coachingModality) {
        this.coachingModality = coachingModality;
    }

    public String getTrainingOptions() {
        return trainingOptions;
    }

    public void setTrainingOptions(String trainingOptions) {
        this.trainingOptions = trainingOptions;
    }

    public String getSessionLength() {
        return sessionLength;
    }

    public void setSessionLength(String sessionLength) {
        this.sessionLength = sessionLength;
    }

    public Boolean getExternalClient() {
        return externalClient;
    }

    public void setExternalClient(Boolean externalClient) {
        this.externalClient = externalClient;
    }

    public String getExternalPTSessionPrice() {
        return externalPTSessionPrice;
    }

    public void setExternalPTSessionPrice(String externalPTSessionPrice) {
        this.externalPTSessionPrice = externalPTSessionPrice;
    }

    public String getNumberSessioinsPerWeek() {
        return numberSessioinsPerWeek;
    }

    public void setNumberSessioinsPerWeek(String numberSessioinsPerWeek) {
        this.numberSessioinsPerWeek = numberSessioinsPerWeek;
    }

    public String getMboContractNames() {
        return mboContractNames;
    }

    public void setMboContractNames(String mboContractNames) {
        this.mboContractNames = mboContractNames;
    }

    public String getMboContractIds() {
        return mboContractIds;
    }

    public void setMboContractIds(String mboContractIds) {
        this.mboContractIds = mboContractIds;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getFnDD() {
        return fnDD;
    }

    public void setFnDD(String fnDD) {
        this.fnDD = fnDD;
    }

    public String getMtDD() {
        return mtDD;
    }

    public void setMtDD(String mtDD) {
        this.mtDD = mtDD;
    }

    public String getOneOffTotal() {
        return oneOffTotal;
    }

    public void setOneOffTotal(String oneOffTotal) {
        this.oneOffTotal = oneOffTotal;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
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

    public String getAgreement() {
        return agreement;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getActiveCampaignId() {
        return activeCampaignId;
    }

    public void setActiveCampaignId(String activeCampaignId) {
        this.activeCampaignId = activeCampaignId;
    }

    public String getCommunicationsStatus() {
        return communicationsStatus;
    }

    public void setCommunicationsStatus(String communicationsStatus) {
        this.communicationsStatus = communicationsStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUseExistingDetails() {
        return useExistingDetails;
    }

    public void setUseExistingDetails(String useExistingDetails) {
        this.useExistingDetails = useExistingDetails;
    }

    public Boolean getOrganisedByCoach() {
        return isOrganisedByCoach;
    }

    public void setOrganisedByCoach(Boolean organisedByCoach) {
        isOrganisedByCoach = organisedByCoach;
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

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    @Override
    public String toString() {
        return "FpCoachEnrolmentSubmission{" +
                "id=" + id +
                ", createDate='" + createDate + '\'' +
                ", fs_formId='" + fs_formId + '\'' +
                ", fs_uniqueId='" + fs_uniqueId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", gymName='" + gymName + '\'' +
                ", locationId=" + locationId +
                ", personalTrainer='" + personalTrainer + '\'' +
                ", personalTrainerName='" + personalTrainerName + '\'' +
                ", coachingModality='" + coachingModality + '\'' +
                ", trainingOptions='" + trainingOptions + '\'' +
                ", sessionLength='" + sessionLength + '\'' +
                ", externalClient=" + externalClient +
                ", externalPTSessionPrice='" + externalPTSessionPrice + '\'' +
                ", numberSessioinsPerWeek='" + numberSessioinsPerWeek + '\'' +
                ", mboContractNames='" + mboContractNames + '\'' +
                ", mboContractIds='" + mboContractIds + '\'' +
                ", couponCode='" + couponCode + '\'' +
                ", fnDD='" + fnDD + '\'' +
                ", mtDD='" + mtDD + '\'' +
                ", oneOffTotal='" + oneOffTotal + '\'' +
                ", startDate='" + startDate + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", memberBankDetail=" + memberBankDetail +
                ", memberCreditCard=" + memberCreditCard +
                ", agreement='" + agreement + '\'' +
                ", signature='" + signature + '\'' +
                ", staffMember='" + staffMember + '\'' +
                ", staffName='" + staffName + '\'' +
                ", notes='" + notes + '\'' +
                ", isOrganisedByCoach=" + isOrganisedByCoach +
                ", UID='" + UID + '\'' +
                ", activeCampaignId='" + activeCampaignId + '\'' +
                ", communicationsStatus='" + communicationsStatus + '\'' +
                ", status='" + status + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", useExistingDetails='" + useExistingDetails + '\'' +
                '}';
    }
}
