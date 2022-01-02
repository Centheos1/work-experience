package com.fitnessplayground.dao.domain.temp;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrolmentDataSubmission {

    @JsonProperty("id")
    private Long id;
    //    Metadata
    @JsonProperty("updateDate")
    private String updateDate;
    @JsonProperty("status")
    private String status;
    @JsonProperty("gymName")
    private String gymName;
    @JsonProperty("contractNamesToBeActivated")
    private String contractNamesToBeActivated;
    @JsonProperty("primarySignatureURL")
    private String primarySignatureURL;
    @JsonProperty("paymentAuthSignatureURL")
    private String paymentAuthSignatureURL;
    @JsonProperty("under18SignatureURL")
    private String under18SignatureURL;
    @JsonProperty("memberContracts")
    private String memberContracts; // todo Array
    @JsonProperty("existingClient")
    private Boolean existingClient;
    @JsonProperty("firstBillingDate")
    private String firstBillingDate;
    @JsonProperty("createDate")
    private String createDate;
    @JsonProperty("locationId")
    private String locationId;
    @JsonProperty("UID")
    private String UID;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("address1")
    private String address1;
    @JsonProperty("address2")
    private String address2;
    @JsonProperty("city")
    private String city;
    @JsonProperty("state")
    private String state;
    @JsonProperty("postcode")
    private String postcode;
    @JsonProperty("dob")
    private String dob;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("emergencyContactName")
    private String emergencyContactName;
    @JsonProperty("emergencyContactPhone")
    private String emergencyContactPhone;
    @JsonProperty("occupation")
    private String occupation;
    @JsonProperty("employer")
    private String employer;
    @JsonProperty("howDidYouHearAboutUs")
    private String howDidYouHearAboutUs;
    @JsonProperty("numberOneGoal")
    private String numberOneGoal;
    @JsonProperty("useExistingDetails")
    private String useExistingDetails;
    @JsonProperty("paymentType")
    private String paymentType;
    @JsonProperty("getAuthorisation")
    private String getAuthorisation;
    @JsonProperty("fpOrBunker")
    private String fpOrBunker;
    @JsonProperty("gymOrPlay")
    private String gymOrPlay;
    @JsonProperty("ddOrPif")
    private String ddOrPif;
    @JsonProperty("noCommitment")
    private String noCommitment;
    @JsonProperty("pifOptions")
    private String pifOptions;
    @JsonProperty("startDate")
    private String startDate;
    @JsonProperty("membershipTermsAcknowledgment")
    private String membershipTermsAcknowledgment;
    @JsonProperty("trainingStarterPack")
    private String trainingStarterPack;
    @JsonProperty("personalTrainer")
    private String personalTrainer;
    @JsonProperty("lifestylePersonalTraining")
    private String lifestylePersonalTraining;
    @JsonProperty("numberSessionsPerWeek")
    private String numberSessionsPerWeek;
    @JsonProperty("sessionLength")
    private String sessionLength;
    @JsonProperty("externalPTSessionPrice")
    private String externalPTSessionPrice;
    @JsonProperty("personalTrainingStartDate")
    private String personalTrainingStartDate;
    @JsonProperty("personalTrainingTermsAcknowledgment")
    private String personalTrainingTermsAcknowledgment;
    @JsonProperty("couponCode")
    private String couponCode;
    @JsonProperty("daysFree")
    private String daysFree;
    @JsonProperty("accessKeyDiscount")
    private String accessKeyDiscount;
    @JsonProperty("freePTPack")
    private String freePTPack;
    @JsonProperty("accessKeyPaymentOptions")
    private String accessKeyPaymentOptions;
    @JsonProperty("accessKeyPaymentMethod")
    private String accessKeyPaymentMethod;
    @JsonProperty("staffMember")
    private String staffMember;
    @JsonProperty("creche")
    private String creche;
    @JsonProperty("agreement")
    private String agreement;
    @JsonProperty("contractCommitment")
    private String contractCommitment;
    @JsonProperty("pt6SessionCommitment")
    private String pt6SessionCommitment;
    @JsonProperty("accessKeyNumber")
    private String accessKeyNumber;
    @JsonProperty("notes")
    private String notes;
    @JsonProperty("injuries")
    private String injuries;
    @JsonProperty("medical")
    private String medical;
    @JsonProperty("medicalClearance")
    private String medicalClearance;
    @JsonProperty("trainingAvailability")
    private String trainingAvailability;
    @JsonProperty("timeAvailability")
    private String timeAvailability;
    @JsonProperty("activeCampaignId")
    private String activeCampaignId;
    @JsonProperty("communicationsStatus")
    private String communicationsStatus;

    @JsonProperty("memberBankDetail")
    private BankDetailSubmission memberBankDetail;
    @JsonProperty("memberCreditCard")
    private CreditCardSubmission memberCreditCard;

    @JsonProperty("fs_formId")
    private String fs_formId;
    @JsonProperty("fs_uniqueId")
    private String fs_uniqueId;
    @JsonProperty("gymSalesId")
    private String gymSalesId;
    @JsonProperty("googleClickId")
    private String googleClickId;
    @JsonProperty("facebookCampaignId")
    private String facebookCampaignId;
    @JsonProperty("fs_formUrl")
    private String fs_formUrl;
    @JsonProperty("isExternalPt")
    private Boolean isExternalPt;
    @JsonProperty("hasCoach")
    private Boolean hasCoach;
    @JsonProperty("staffName")
    private String staffName;
    @JsonProperty("personalTrainerName")
    private String personalTrainerName;
    @JsonProperty("trainingPackageSoldBy")
    private String trainingPackageSoldBy;
    @JsonProperty("trainingPackageConsultantLocationId")
    private String trainingPackageConsultantLocationId;
    @JsonProperty("trainingPackageConsultantName")
    private String trainingPackageConsultantName;
    @JsonProperty("trainingPackageConsultantMboId")
    private String trainingPackageConsultantMboId;
    @JsonProperty("serviceNamesToBeActivated")
    private String serviceNamesToBeActivated;
    @JsonProperty("renewalStatus")
    private String renewalStatus;

    @JsonProperty("hasReferral")
    private Boolean hasReferral;
    @JsonProperty("referralName")
    private String referralName;
    @JsonProperty("referralPhone")
    private String referralPhone;
    @JsonProperty("referralEmail")
    private String referralEmail;
    @JsonProperty("dollarValue")
    private Float dollarValue;
    @JsonProperty("memberPhotoURL")
    private String memberPhotoURL;
    @JsonProperty("virtualPlaygroundCommencement")
    private String virtualPlaygroundCommencement;
    @JsonProperty("mboUniqueId")
    private String mboUniqueId;
    @JsonProperty("preExId")
    private String preExId;
    @JsonProperty("covidVerificationURL")
    private String covidVerificationURL;


    public EnrolmentDataSubmission() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGymName() {
        return gymName;
    }

    public void setGymName(String gymName) {
        this.gymName = gymName;
    }

    public String getContractNamesToBeActivated() {
        return contractNamesToBeActivated;
    }

    public void setContractNamesToBeActivated(String contractNamesToBeActivated) {
        this.contractNamesToBeActivated = contractNamesToBeActivated;
    }

    public String getPrimarySignatureURL() {
        return primarySignatureURL;
    }

    public void setPrimarySignatureURL(String primarySignatureURL) {
        this.primarySignatureURL = primarySignatureURL;
    }

    public String getPaymentAuthSignatureURL() {
        return paymentAuthSignatureURL;
    }

    public void setPaymentAuthSignatureURL(String paymentAuthSignatureURL) {
        this.paymentAuthSignatureURL = paymentAuthSignatureURL;
    }

    public String getUnder18SignatureURL() {
        return under18SignatureURL;
    }

    public void setUnder18SignatureURL(String under18SignatureURL) {
        this.under18SignatureURL = under18SignatureURL;
    }

    public String getMemberContracts() {
        return memberContracts;
    }

    public void setMemberContracts(String memberContracts) {
        this.memberContracts = memberContracts;
    }

    public Boolean getExistingClient() {
        return existingClient;
    }

    public void setExistingClient(Boolean existingClient) {
        this.existingClient = existingClient;
    }

    public String getFirstBillingDate() {
        return firstBillingDate;
    }

    public void setFirstBillingDate(String firstBillingDate) {
        this.firstBillingDate = firstBillingDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
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

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getHowDidYouHearAboutUs() {
        return howDidYouHearAboutUs;
    }

    public void setHowDidYouHearAboutUs(String howDidYouHearAboutUs) {
        this.howDidYouHearAboutUs = howDidYouHearAboutUs;
    }

    public String getNumberOneGoal() {
        return numberOneGoal;
    }

    public void setNumberOneGoal(String numberOneGoal) {
        this.numberOneGoal = numberOneGoal;
    }

    public String getUseExistingDetails() {
        return useExistingDetails;
    }

    public void setUseExistingDetails(String useExistingDetails) {
        this.useExistingDetails = useExistingDetails;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getGetAuthorisation() {
        return getAuthorisation;
    }

    public void setGetAuthorisation(String getAuthorisation) {
        this.getAuthorisation = getAuthorisation;
    }

    public String getFpOrBunker() {
        return fpOrBunker;
    }

    public void setFpOrBunker(String fpOrBunker) {
        this.fpOrBunker = fpOrBunker;
    }

    public String getGymOrPlay() {
        return gymOrPlay;
    }

    public void setGymOrPlay(String gymOrPlay) {
        this.gymOrPlay = gymOrPlay;
    }

    public String getDdOrPif() {
        return ddOrPif;
    }

    public void setDdOrPif(String ddOrPif) {
        this.ddOrPif = ddOrPif;
    }

    public String getNoCommitment() {
        return noCommitment;
    }

    public void setNoCommitment(String noCommitment) {
        this.noCommitment = noCommitment;
    }

    public String getPifOptions() {
        return pifOptions;
    }

    public void setPifOptions(String pifOptions) {
        this.pifOptions = pifOptions;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getMembershipTermsAcknowledgment() {
        return membershipTermsAcknowledgment;
    }

    public void setMembershipTermsAcknowledgment(String membershipTermsAcknowledgment) {
        this.membershipTermsAcknowledgment = membershipTermsAcknowledgment;
    }

    public String getTrainingStarterPack() {
        return trainingStarterPack;
    }

    public void setTrainingStarterPack(String trainingStarterPack) {
        this.trainingStarterPack = trainingStarterPack;
    }

    public String getPersonalTrainer() {
        return personalTrainer;
    }

    public void setPersonalTrainer(String personalTrainer) {
        this.personalTrainer = personalTrainer;
    }

    public String getLifestylePersonalTraining() {
        return lifestylePersonalTraining;
    }

    public void setLifestylePersonalTraining(String lifestylePersonalTraining) {
        this.lifestylePersonalTraining = lifestylePersonalTraining;
    }

    public String getNumberSessionsPerWeek() {
        return numberSessionsPerWeek;
    }

    public void setNumberSessionsPerWeek(String numberSessionsPerWeek) {
        this.numberSessionsPerWeek = numberSessionsPerWeek;
    }

    public String getSessionLength() {
        return sessionLength;
    }

    public void setSessionLength(String sessionLength) {
        this.sessionLength = sessionLength;
    }

    public String getExternalPTSessionPrice() {
        return externalPTSessionPrice;
    }

    public void setExternalPTSessionPrice(String externalPTSessionPrice) {
        this.externalPTSessionPrice = externalPTSessionPrice;
    }

    public String getPersonalTrainingStartDate() {
        return personalTrainingStartDate;
    }

    public void setPersonalTrainingStartDate(String personalTrainingStartDate) {
        this.personalTrainingStartDate = personalTrainingStartDate;
    }

    public String getPersonalTrainingTermsAcknowledgment() {
        return personalTrainingTermsAcknowledgment;
    }

    public void setPersonalTrainingTermsAcknowledgment(String personalTrainingTermsAcknowledgment) {
        this.personalTrainingTermsAcknowledgment = personalTrainingTermsAcknowledgment;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getDaysFree() {
        return daysFree;
    }

    public void setDaysFree(String daysFree) {
        this.daysFree = daysFree;
    }

    public String getAccessKeyDiscount() {
        return accessKeyDiscount;
    }

    public void setAccessKeyDiscount(String accessKeyDiscount) {
        this.accessKeyDiscount = accessKeyDiscount;
    }

    public String getFreePTPack() {
        return freePTPack;
    }

    public void setFreePTPack(String freePTPack) {
        this.freePTPack = freePTPack;
    }

    public String getAccessKeyPaymentOptions() {
        return accessKeyPaymentOptions;
    }

    public void setAccessKeyPaymentOptions(String accessKeyPaymentOptions) {
        this.accessKeyPaymentOptions = accessKeyPaymentOptions;
    }

    public String getStaffMember() {
        return staffMember;
    }

    public void setStaffMember(String staffMember) {
        this.staffMember = staffMember;
    }

    public String getCreche() {
        return creche;
    }

    public void setCreche(String creche) {
        this.creche = creche;
    }

    public String getAgreement() {
        return agreement;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }

    public String getContractCommitment() {
        return contractCommitment;
    }

    public void setContractCommitment(String contractCommitment) {
        this.contractCommitment = contractCommitment;
    }

    public String getPt6SessionCommitment() {
        return pt6SessionCommitment;
    }

    public void setPt6SessionCommitment(String pt6SessionCommitment) {
        this.pt6SessionCommitment = pt6SessionCommitment;
    }

    public String getAccessKeyNumber() {
        return accessKeyNumber;
    }

    public void setAccessKeyNumber(String accessKeyNumber) {
        this.accessKeyNumber = accessKeyNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getInjuries() {
        return injuries;
    }

    public void setInjuries(String injuries) {
        this.injuries = injuries;
    }

    public String getMedical() {
        return medical;
    }

    public void setMedical(String medical) {
        this.medical = medical;
    }

    public String getMedicalClearance() {
        return medicalClearance;
    }

    public void setMedicalClearance(String medicalClearance) {
        this.medicalClearance = medicalClearance;
    }

    public String getTrainingAvailability() {
        return trainingAvailability;
    }

    public void setTrainingAvailability(String trainingAvailability) {
        this.trainingAvailability = trainingAvailability;
    }

    public String getTimeAvailability() {
        return timeAvailability;
    }

    public void setTimeAvailability(String timeAvailability) {
        this.timeAvailability = timeAvailability;
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

    public String getGymSalesId() {
        return gymSalesId;
    }

    public void setGymSalesId(String gymSalesId) {
        this.gymSalesId = gymSalesId;
    }

    public String getGoogleClickId() {
        return googleClickId;
    }

    public void setGoogleClickId(String googleClickId) {
        this.googleClickId = googleClickId;
    }

    public String getFacebookCampaignId() {
        return facebookCampaignId;
    }

    public void setFacebookCampaignId(String facebookCampaignId) {
        this.facebookCampaignId = facebookCampaignId;
    }

    public String getFs_formUrl() {
        return fs_formUrl;
    }

    public void setFs_formUrl(String fs_formUrl) {
        this.fs_formUrl = fs_formUrl;
    }

    public Boolean getExternalPt() {
        return isExternalPt;
    }

    public void setExternalPt(Boolean externalPt) {
        isExternalPt = externalPt;
    }

    public Boolean getHasCoach() {
        return hasCoach;
    }

    public void setHasCoach(Boolean hasCoach) {
        this.hasCoach = hasCoach;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getPersonalTrainerName() {
        return personalTrainerName;
    }

    public void setPersonalTrainerName(String personalTrainerName) {
        this.personalTrainerName = personalTrainerName;
    }

    public String getTrainingPackageSoldBy() {
        return trainingPackageSoldBy;
    }

    public void setTrainingPackageSoldBy(String trainingPackageSoldBy) {
        this.trainingPackageSoldBy = trainingPackageSoldBy;
    }

    public String getTrainingPackageConsultantLocationId() {
        return trainingPackageConsultantLocationId;
    }

    public void setTrainingPackageConsultantLocationId(String trainingPackageConsultantLocationId) {
        this.trainingPackageConsultantLocationId = trainingPackageConsultantLocationId;
    }

    public String getTrainingPackageConsultantName() {
        return trainingPackageConsultantName;
    }

    public void setTrainingPackageConsultantName(String trainingPackageConsultantName) {
        this.trainingPackageConsultantName = trainingPackageConsultantName;
    }

    public String getTrainingPackageConsultantMboId() {
        return trainingPackageConsultantMboId;
    }

    public void setTrainingPackageConsultantMboId(String trainingPackageConsultantMboId) {
        this.trainingPackageConsultantMboId = trainingPackageConsultantMboId;
    }

    public String getServiceNamesToBeActivated() {
        return serviceNamesToBeActivated;
    }

    public void setServiceNamesToBeActivated(String serviceNamesToBeActivated) {
        this.serviceNamesToBeActivated = serviceNamesToBeActivated;
    }

    public String getRenewalStatus() {
        return renewalStatus;
    }

    public void setRenewalStatus(String renewalStatus) {
        this.renewalStatus = renewalStatus;
    }

    public Boolean getHasReferral() {
        return hasReferral;
    }

    public void setHasReferral(Boolean hasReferral) {
        this.hasReferral = hasReferral;
    }

    public String getReferralName() {
        return referralName;
    }

    public void setReferralName(String referralName) {
        this.referralName = referralName;
    }

    public String getReferralPhone() {
        return referralPhone;
    }

    public void setReferralPhone(String referralPhone) {
        this.referralPhone = referralPhone;
    }

    public String getReferralEmail() {
        return referralEmail;
    }

    public void setReferralEmail(String referralEmail) {
        this.referralEmail = referralEmail;
    }

    public Float getDollarValue() {
        return dollarValue;
    }

    public void setDollarValue(Float dollarValue) {
        this.dollarValue = dollarValue;
    }

    public String getMemberPhotoURL() {
        return memberPhotoURL;
    }

    public void setMemberPhotoURL(String memberPhotoURL) {
        this.memberPhotoURL = memberPhotoURL;
    }

    public String getVirtualPlaygroundCommencement() {
        return virtualPlaygroundCommencement;
    }

    public void setVirtualPlaygroundCommencement(String virtualPlaygroundCommencement) {
        this.virtualPlaygroundCommencement = virtualPlaygroundCommencement;
    }

    public String getAccessKeyPaymentMethod() {
        return accessKeyPaymentMethod;
    }

    public void setAccessKeyPaymentMethod(String accessKeyPaymentMethod) {
        this.accessKeyPaymentMethod = accessKeyPaymentMethod;
    }

    public String getMboUniqueId() {
        return mboUniqueId;
    }

    public void setMboUniqueId(String mboUniqueId) {
        this.mboUniqueId = mboUniqueId;
    }

    public String getPreExId() {
        return preExId;
    }

    public void setPreExId(String preExId) {
        this.preExId = preExId;
    }

    public String getCovidVerificationURL() {
        return covidVerificationURL;
    }

    public void setCovidVerificationURL(String covidVerificationURL) {
        this.covidVerificationURL = covidVerificationURL;
    }

    @Override
    public String toString() {
        return "EnrolmentDataSubmission{" +
                "id=" + id +
                ", updateDate='" + updateDate + '\'' +
                ", status='" + status + '\'' +
                ", gymName='" + gymName + '\'' +
                ", contractNamesToBeActivated='" + contractNamesToBeActivated + '\'' +
                ", primarySignatureURL='" + primarySignatureURL + '\'' +
                ", paymentAuthSignatureURL='" + paymentAuthSignatureURL + '\'' +
                ", under18SignatureURL='" + under18SignatureURL + '\'' +
                ", memberContracts='" + memberContracts + '\'' +
                ", existingClient=" + existingClient +
                ", firstBillingDate='" + firstBillingDate + '\'' +
                ", createDate='" + createDate + '\'' +
                ", locationId='" + locationId + '\'' +
                ", UID='" + UID + '\'' +
                ", phone='" + phone + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postcode='" + postcode + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", emergencyContactName='" + emergencyContactName + '\'' +
                ", emergencyContactPhone='" + emergencyContactPhone + '\'' +
                ", occupation='" + occupation + '\'' +
                ", employer='" + employer + '\'' +
                ", howDidYouHearAboutUs='" + howDidYouHearAboutUs + '\'' +
                ", numberOneGoal='" + numberOneGoal + '\'' +
                ", useExistingDetails='" + useExistingDetails + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", getAuthorisation='" + getAuthorisation + '\'' +
                ", fpOrBunker='" + fpOrBunker + '\'' +
                ", gymOrPlay='" + gymOrPlay + '\'' +
                ", ddOrPif='" + ddOrPif + '\'' +
                ", noCommitment='" + noCommitment + '\'' +
                ", pifOptions='" + pifOptions + '\'' +
                ", startDate='" + startDate + '\'' +
                ", membershipTermsAcknowledgment='" + membershipTermsAcknowledgment + '\'' +
                ", trainingStarterPack='" + trainingStarterPack + '\'' +
                ", personalTrainer='" + personalTrainer + '\'' +
                ", lifestylePersonalTraining='" + lifestylePersonalTraining + '\'' +
                ", numberSessionsPerWeek='" + numberSessionsPerWeek + '\'' +
                ", sessionLength='" + sessionLength + '\'' +
                ", externalPTSessionPrice='" + externalPTSessionPrice + '\'' +
                ", personalTrainingStartDate='" + personalTrainingStartDate + '\'' +
                ", personalTrainingTermsAcknowledgment='" + personalTrainingTermsAcknowledgment + '\'' +
                ", couponCode='" + couponCode + '\'' +
                ", daysFree='" + daysFree + '\'' +
                ", accessKeyDiscount='" + accessKeyDiscount + '\'' +
                ", freePTPack='" + freePTPack + '\'' +
                ", accessKeyPaymentOptions='" + accessKeyPaymentOptions + '\'' +
                ", accessKeyPaymentMethod='" + accessKeyPaymentMethod + '\'' +
                ", staffMember='" + staffMember + '\'' +
                ", creche='" + creche + '\'' +
                ", agreement='" + agreement + '\'' +
                ", contractCommitment='" + contractCommitment + '\'' +
                ", pt6SessionCommitment='" + pt6SessionCommitment + '\'' +
                ", accessKeyNumber='" + accessKeyNumber + '\'' +
                ", notes='" + notes + '\'' +
                ", injuries='" + injuries + '\'' +
                ", medical='" + medical + '\'' +
                ", medicalClearance='" + medicalClearance + '\'' +
                ", trainingAvailability='" + trainingAvailability + '\'' +
                ", timeAvailability='" + timeAvailability + '\'' +
                ", activeCampaignId='" + activeCampaignId + '\'' +
                ", communicationsStatus='" + communicationsStatus + '\'' +
                ", memberBankDetail=" + memberBankDetail +
                ", memberCreditCard=" + memberCreditCard +
                ", fs_formId='" + fs_formId + '\'' +
                ", fs_uniqueId='" + fs_uniqueId + '\'' +
                ", gymSalesId='" + gymSalesId + '\'' +
                ", googleClickId='" + googleClickId + '\'' +
                ", facebookCampaignId='" + facebookCampaignId + '\'' +
                ", fs_formUrl='" + fs_formUrl + '\'' +
                ", isExternalPt=" + isExternalPt +
                ", hasCoach=" + hasCoach +
                ", staffName='" + staffName + '\'' +
                ", personalTrainerName='" + personalTrainerName + '\'' +
                ", trainingPackageSoldBy='" + trainingPackageSoldBy + '\'' +
                ", trainingPackageConsultantLocationId='" + trainingPackageConsultantLocationId + '\'' +
                ", trainingPackageConsultantName='" + trainingPackageConsultantName + '\'' +
                ", trainingPackageConsultantMboId='" + trainingPackageConsultantMboId + '\'' +
                ", serviceNamesToBeActivated='" + serviceNamesToBeActivated + '\'' +
                ", renewalStatus='" + renewalStatus + '\'' +
                ", hasReferral=" + hasReferral +
                ", referralName='" + referralName + '\'' +
                ", referralPhone='" + referralPhone + '\'' +
                ", referralEmail='" + referralEmail + '\'' +
                ", dollarValue=" + dollarValue +
                ", memberPhotoURL='" + memberPhotoURL + '\'' +
                ", virtualPlaygroundCommencement='" + virtualPlaygroundCommencement + '\'' +
                '}';
    }
}
