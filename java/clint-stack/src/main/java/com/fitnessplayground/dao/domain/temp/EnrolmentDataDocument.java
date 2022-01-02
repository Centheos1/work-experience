package com.fitnessplayground.dao.domain.temp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fitnessplayground.dao.domain.EnrolmentData;

public class EnrolmentDataDocument {

//    Auth Info
    @JsonProperty("MBO_API_KEY")
    private String MBO_API_KEY;
    @JsonProperty("TOKEN")
    private String TOKEN;
    @JsonProperty("MBO_SITE_ID")
    private String MBO_SITE_ID;
    @JsonProperty("SENDER_EMAIL_ADDRESS")
    private String SENDER_EMAIL_ADDRESS;
    @JsonProperty("SENDER_EMAIL_PASSWORD")
    private String SENDER_EMAIL_PASSWORD;

    private String clubManager;

    private String enrolmentDataId;
    private String gymName;
    private String contractNamesToBeActivated;

    private String primarySignatureURL;
    private String paymentAuthSignatureURL;
    private String under18SignatureURL;
    private String firstBillingDate;
    private String createDate;
    private String locationId;

    //    Primary Details
    private String phone;
    private String firstName;
    private String lastName;
    private String email;

    //    Member Details
    private String address1; // 120A Devonshire Street,
    private String address2; // ,
    private String city; // Surry Hills,
    private String state; // NSW,
    private String postcode; // 2010,
    private String dob; // 2005-03-03,
    private String gender; // male,
    private String emergencyContactName; // em,
    private String emergencyContactPhone; // 0413506306,
    private String occupation; // ,
    private String employer; // Fitness Playground,
    private String howDidYouHearAboutUs; // internet,
    private String numberOneGoal; // feelBetter

    //    Payment Details
    private String useExistingDetails;
    private String paymentType;
    private String getAuthorisation;

    //    Membership Details

    private String noCommitment;
    private String startDate; //2019-04-11
    private String membershipTermsAcknowledgment;
    private String trainingStarterPack;
    private String personalTrainer;
    private String personalTrainingStartDate; //2019-04-22
    private String personalTrainingTermsAcknowledgment;
    private String couponCode; //
    private String daysFree; //
    private String accessKeyDiscount; //
    private String freePTPack; //
    private String accessKeyPaymentOptions;
    private String staffMember; //100000386

    //    Legal Details
    private String agreement;
    private String contractCommitment;
    private String pt6SessionCommitment;
    private String accessKeyNumber; //54323
    private String notes; //

    //    Health Check
    private String injuries;
    private String medical;
    private String medicalClearance;
    private String trainingAvailability;
    private String timeAvailability;

//    Bank Details
    private String bdAccountHolderName;
    private String bdBranch;
    private String bdFinancialInstitution;
    private String bdBsb;
    private String bdAccountNumber;
    private String bdAccountType;

//    Credit Card Details
    private String ccNumber;
    private String ccHolder;
    private String ccCity;
    private String ccAddress;
    private String ccState;
    private String ccPostcode;
    private String ccExpiry;

    private String termAndConditions;


    public EnrolmentDataDocument() {
    }

    public static EnrolmentDataDocument from(EnrolmentData enrolmentData) {
        EnrolmentDataDocument dataDocument = new EnrolmentDataDocument();

        dataDocument.setEnrolmentDataId(Long.toString(enrolmentData.getId()));
        dataDocument.setGymName(enrolmentData.getGymName());
        dataDocument.setContractNamesToBeActivated(enrolmentData.getContractNamesToBeActivated());
        dataDocument.setPrimarySignatureURL(enrolmentData.getPrimarySignatureURL());
        dataDocument.setPaymentAuthSignatureURL(enrolmentData.getPaymentAuthSignatureURL());
        dataDocument.setUnder18SignatureURL(enrolmentData.getUnder18SignatureURL());
        dataDocument.setFirstBillingDate(enrolmentData.getFirstBillingDate());
        dataDocument.setCreateDate(enrolmentData.getCreateDate());
        dataDocument.setLocationId(enrolmentData.getLocationId());
        dataDocument.setPhone(enrolmentData.getPhone());
        dataDocument.setFirstName(enrolmentData.getFirstName());
        dataDocument.setLastName(enrolmentData.getLastName());
        dataDocument.setEmail(enrolmentData.getEmail());
        dataDocument.setAddress1(enrolmentData.getAddress1());
        dataDocument.setAddress2(enrolmentData.getAddress2()); // ,
        dataDocument.setCity(enrolmentData.getCity());
        dataDocument.setState(enrolmentData.getState());
        dataDocument.setPostcode(enrolmentData.getPostcode());
        dataDocument.setDob(enrolmentData.getDob());
        dataDocument.setGender(enrolmentData.getGender());
        dataDocument.setEmergencyContactName(enrolmentData.getEmergencyContactName());
        dataDocument.setEmergencyContactPhone(enrolmentData.getEmergencyContactPhone());
        dataDocument.setOccupation(enrolmentData.getOccupation());
        dataDocument.setEmployer(enrolmentData.getEmployer());
        dataDocument.setHowDidYouHearAboutUs(enrolmentData.getHowDidYouHearAboutUs());
        dataDocument.setNumberOneGoal(enrolmentData.getNumberOneGoal());
        dataDocument.setUseExistingDetails(enrolmentData.getUseExistingDetails());
        dataDocument.setPaymentType(enrolmentData.getPaymentType());
        dataDocument.setGetAuthorisation(enrolmentData.getGetAuthorisation());
        dataDocument.setNoCommitment(enrolmentData.getNoCommitment());
        dataDocument.setStartDate(enrolmentData.getStartDate()); //2019-04-11
        dataDocument.setMembershipTermsAcknowledgment(enrolmentData.getMembershipTermsAcknowledgment());
        dataDocument.setTrainingStarterPack(enrolmentData.getTrainingStarterPack());
        dataDocument.setPersonalTrainer(enrolmentData.getPersonalTrainer());
        dataDocument.setPersonalTrainingStartDate(enrolmentData.getPersonalTrainingStartDate());
        dataDocument.setPersonalTrainingTermsAcknowledgment(enrolmentData.getPersonalTrainingTermsAcknowledgment());
        dataDocument.setCouponCode(enrolmentData.getCouponCode());
        dataDocument.setDaysFree(enrolmentData.getDaysFree());
        dataDocument.setAccessKeyDiscount(enrolmentData.getAccessKeyDiscount());
        dataDocument.setFreePTPack(enrolmentData.getFreePTPack());
        dataDocument.setAccessKeyPaymentOptions(enrolmentData.getAccessKeyPaymentOptions());
        dataDocument.setStaffMember(enrolmentData.getStaffMember());
        dataDocument.setAgreement(enrolmentData.getAgreement());
        dataDocument.setContractCommitment(enrolmentData.getContractCommitment());
        dataDocument.setPt6SessionCommitment(enrolmentData.getPt6SessionCommitment());
        dataDocument.setAccessKeyNumber(enrolmentData.getAccessKeyNumber()); //54323
        dataDocument.setNotes(enrolmentData.getNotes());
        dataDocument.setInjuries(enrolmentData.getInjuries());
        dataDocument.setMedical(enrolmentData.getMedical());
        dataDocument.setMedicalClearance(enrolmentData.getMedicalClearance());
        dataDocument.setTrainingAvailability(enrolmentData.getTrainingAvailability());
        dataDocument.setTimeAvailability(enrolmentData.getTimeAvailability());

        if (enrolmentData.getMemberBankDetail() != null) {
//    Bank Details
            dataDocument.setBdAccountHolderName(enrolmentData.getMemberBankDetail().getAccountHolderName());
            dataDocument.setBdBranch(enrolmentData.getMemberBankDetail().getBranch());
            dataDocument.setBdFinancialInstitution(enrolmentData.getMemberBankDetail().getFinancialInstitution());
            dataDocument.setBdBsb(enrolmentData.getMemberBankDetail().getBsb());
            dataDocument.setBdAccountNumber(enrolmentData.getMemberBankDetail().getAccountNumber());
            dataDocument.setBdAccountType(enrolmentData.getMemberBankDetail().getAccountType());
        }

        if (enrolmentData.getMemberCreditCard() != null) {
//    Credit Card Details
            dataDocument.setCcNumber(enrolmentData.getMemberCreditCard().getNumber());
            dataDocument.setCcHolder(enrolmentData.getMemberCreditCard().getHolder());
            dataDocument.setCcCity(enrolmentData.getMemberCreditCard().getCity());
            dataDocument.setCcAddress(enrolmentData.getMemberCreditCard().getAddress());
            dataDocument.setCcState(enrolmentData.getMemberCreditCard().getState());
            dataDocument.setCcPostcode(enrolmentData.getMemberCreditCard().getPostcode());
            dataDocument.setCcExpiry(enrolmentData.getMemberCreditCard().getExpMonth()+"/"+enrolmentData.getMemberCreditCard().getExpYear());
        }

        return dataDocument;
    }

    public String getMBO_API_KEY() {
        return MBO_API_KEY;
    }

    public void setMBO_API_KEY(String MBO_API_KEY) {
        this.MBO_API_KEY = MBO_API_KEY;
    }

    public String getTOKEN() {
        return TOKEN;
    }

    public void setTOKEN(String TOKEN) {
        this.TOKEN = TOKEN;
    }

    public String getMBO_SITE_ID() {
        return MBO_SITE_ID;
    }

    public void setMBO_SITE_ID(String MBO_SITE_ID) {
        this.MBO_SITE_ID = MBO_SITE_ID;
    }

    public String getSENDER_EMAIL_ADDRESS() {
        return SENDER_EMAIL_ADDRESS;
    }

    public void setSENDER_EMAIL_ADDRESS(String SENDER_EMAIL_ADDRESS) {
        this.SENDER_EMAIL_ADDRESS = SENDER_EMAIL_ADDRESS;
    }

    public String getSENDER_EMAIL_PASSWORD() {
        return SENDER_EMAIL_PASSWORD;
    }

    public void setSENDER_EMAIL_PASSWORD(String SENDER_EMAIL_PASSWORD) {
        this.SENDER_EMAIL_PASSWORD = SENDER_EMAIL_PASSWORD;
    }

    public String getClubManager() {
        return clubManager;
    }

    public void setClubManager(String clubManager) {
        this.clubManager = clubManager;
    }

    public String getEnrolmentDataId() {
        return enrolmentDataId;
    }

    public void setEnrolmentDataId(String enrolmentDataId) {
        this.enrolmentDataId = enrolmentDataId;
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

    public String getNoCommitment() {
        return noCommitment;
    }

    public void setNoCommitment(String noCommitment) {
        this.noCommitment = noCommitment;
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

    public String getBdAccountHolderName() {
        return bdAccountHolderName;
    }

    public void setBdAccountHolderName(String bdAccountHolderName) {
        this.bdAccountHolderName = bdAccountHolderName;
    }

    public String getBdBranch() {
        return bdBranch;
    }

    public void setBdBranch(String bdBranch) {
        this.bdBranch = bdBranch;
    }

    public String getBdFinancialInstitution() {
        return bdFinancialInstitution;
    }

    public void setBdFinancialInstitution(String bdFinancialInstitution) {
        this.bdFinancialInstitution = bdFinancialInstitution;
    }

    public String getBdBsb() {
        return bdBsb;
    }

    public void setBdBsb(String bdBsb) {
        this.bdBsb = bdBsb;
    }

    public String getBdAccountNumber() {
        return bdAccountNumber;
    }

    public void setBdAccountNumber(String bdAccountNumber) {
        this.bdAccountNumber = bdAccountNumber;
    }

    public String getBdAccountType() {
        return bdAccountType;
    }

    public void setBdAccountType(String bdAccountType) {
        this.bdAccountType = bdAccountType;
    }

    public String getCcNumber() {
        return ccNumber;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    public String getCcHolder() {
        return ccHolder;
    }

    public void setCcHolder(String ccHolder) {
        this.ccHolder = ccHolder;
    }

    public String getCcCity() {
        return ccCity;
    }

    public void setCcCity(String ccCity) {
        this.ccCity = ccCity;
    }

    public String getCcAddress() {
        return ccAddress;
    }

    public void setCcAddress(String ccAddress) {
        this.ccAddress = ccAddress;
    }

    public String getCcState() {
        return ccState;
    }

    public void setCcState(String ccState) {
        this.ccState = ccState;
    }

    public String getCcPostcode() {
        return ccPostcode;
    }

    public void setCcPostcode(String ccPostcode) {
        this.ccPostcode = ccPostcode;
    }

    public String getCcExpiry() {
        return ccExpiry;
    }

    public void setCcExpiry(String ccExpiry) {
        this.ccExpiry = ccExpiry;
    }

    public String getTermAndConditions() {
        return termAndConditions;
    }

    public void setTermAndConditions(String termAndConditions) {
        this.termAndConditions = termAndConditions;
    }


}
