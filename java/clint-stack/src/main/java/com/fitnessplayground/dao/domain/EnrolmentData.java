package com.fitnessplayground.dao.domain;

import com.fitnessplayground.dao.domain.temp.EnrolmentDataSubmission;
import com.fitnessplayground.dao.domain.temp.EnrolmentFormSubmission;
import com.fitnessplayground.service.MemberStatus;
import com.fitnessplayground.util.Constants;
import com.fitnessplayground.util.GymName;
import com.fitnessplayground.util.Helpers;

import javax.persistence.*;
import java.util.Arrays;

@Entity
public class EnrolmentData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

//    Metadata
    private String updateDate;
    private String status;
    private String gymName;
    private String contractNamesToBeActivated;
    private String fs_formId;
    private String fs_uniqueId;
    private String gymSalesId;
    private String googleClickId;
    private String facebookCampaignId;
    private String fs_formUrl;
    private Boolean isExternalPt;
    private Boolean hasCoach;

    private String primarySignatureURL;
    private String paymentAuthSignatureURL;
    private String under18SignatureURL;
    private String memberContracts; // todo Array
    private boolean existingClient;
    private String firstBillingDate;
    private String createDate;
    private String locationId;
    private String UID;

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
    private String getAuthorisation; // todo Array

//    Membership Details
    private String fpOrBunker; //fitnessPlayground
    private String gymOrPlay; //gym
    private String ddOrPif; //dd
    private String noCommitment; //12Month
    private String pifOptions; //
    private String startDate; //2019-04-11
    private String membershipTermsAcknowledgment; // todo Array
    private String trainingStarterPack; //ongoingPersonalTraining
    private String personalTrainer; //100000571
    private String lifestylePersonalTraining; // Array
    private String numberSessionsPerWeek; //2wk
    private String sessionLength; //30
    private String externalPTSessionPrice; //
    private String personalTrainingStartDate; //2019-04-22
    private String personalTrainingTermsAcknowledgment; // todo Array
    private String couponCode; //
    private String daysFree; //
    private String accessKeyDiscount; //
    private String freePTPack; //
    private String accessKeyPaymentOptions;
    private String accessKeyPaymentMethod;
    private String staffMember; //100000386
    private String creche;
    private String staffName;
    private String personalTrainerName;

//    Legal Details
    private String agreement; // todo Array
    private String contractCommitment; // todo Array
    private String pt6SessionCommitment;
    private String accessKeyNumber; //54323
    private String notes; //

//    Health Check
    private String injuries; // todo Array,
    private String medical; // todo Array,
    private String medicalClearance; //,
    private String trainingAvailability; // todo Array
    private String timeAvailability; // todo Array

    private String activeCampaignId;

    private String trainingPackageSoldBy;
    private String trainingPackageConsultantLocationId;
    private String trainingPackageConsultantName;
    private String trainingPackageConsultantMboId;

    private String communicationsStatus;

    private String serviceNamesToBeActivated;
    private String renewalStatus;
    private Boolean hasReferral;
    private String referralName;
    private String referralPhone;
    private String referralEmail;
    private Float dollarValue;
    private String memberPhotoURL;

    private String virtualPlaygroundCommencement;

    private String mboUniqueId;
    private String preExId;

    private String covidVerificationURL;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "creditCardId")
    private MemberCreditCard memberCreditCard;
//    private MemberCreditCard creditCard;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bankDetailId")
    private MemberBankDetail memberBankDetail;

    public EnrolmentData() {
    }

    public static EnrolmentData from(EnrolmentFormSubmission submission) {

        EnrolmentData enrolmentData = new EnrolmentData();

        if (submission.getLocationId() != null) {
            enrolmentData.setUpdateDate(Helpers.getDateNow(submission.getLocationId()));
        }
        enrolmentData.setUpdateDate(Helpers.getDateNow());
        enrolmentData.setStatus(MemberStatus.RECEIVED.getStatus());

        enrolmentData.setPrimarySignatureURL(submission.getPrimarySignatureURL());
        enrolmentData.setUnder18SignatureURL(submission.getUnder18SignatureURL());
        enrolmentData.setPaymentAuthSignatureURL(submission.getPaymentAuthSignatureURL());
        enrolmentData.setMemberContracts(Helpers.intArrayToString(submission.getMemberContracts()));
        enrolmentData.setExistingClient(submission.isExistingClient());
        enrolmentData.setFirstBillingDate(submission.getFirstBillingDate());
        enrolmentData.setCreateDate(submission.getCreateDate());
        enrolmentData.setUID(submission.getUID());

        enrolmentData.setLocationId(submission.getLocationId());

        if (submission.getLocationId() != null) { enrolmentData.setGymName(GymName.getGymName(Integer.parseInt(submission.getLocationId())).getName()); }

        enrolmentData.setFirstName(Helpers.capitalise(submission.getPrimaryDetails().getFirstName()));
        enrolmentData.setLastName(Helpers.capitalise(submission.getPrimaryDetails().getLastName()));
        enrolmentData.setPhone(Helpers.cleanPhoneNumber(submission.getPrimaryDetails().getPhone()));
        enrolmentData.setEmail(submission.getPrimaryDetails().getEmail().trim());

        enrolmentData.setAddress1(Helpers.capitalise(submission.getMemberDetails().getAddress1()));
        enrolmentData.setAddress2(Helpers.capitalise(submission.getMemberDetails().getAddress2()));
        enrolmentData.setCity(Helpers.capitalise(submission.getMemberDetails().getCity()));
        enrolmentData.setState(Helpers.capitalise(submission.getMemberDetails().getState()));
        enrolmentData.setPostcode(submission.getMemberDetails().getPostcode());
        enrolmentData.setDob(submission.getMemberDetails().getDob());
        enrolmentData.setGender(submission.getMemberDetails().getGender());
        enrolmentData.setEmergencyContactName(Helpers.capitalise(submission.getMemberDetails().getEmergencyContactName()));
        enrolmentData.setEmergencyContactPhone(Helpers.cleanPhoneNumber(submission.getMemberDetails().getEmergencyContactPhone()));
        enrolmentData.setOccupation(Helpers.capitalise(submission.getMemberDetails().getOccupation()));
        enrolmentData.setEmployer(Helpers.capitalise(submission.getMemberDetails().getEmployer()));
        enrolmentData.setHowDidYouHearAboutUs(submission.getMemberDetails().getHowDidYouHearAboutUs());
        enrolmentData.setNumberOneGoal(submission.getMemberDetails().getNumberOneGoal());

        if (submission.getMembershipDetails().getFpOrBunker().equals("fitnessPlayground")) {
            enrolmentData.setFpOrBunker(submission.getMembershipDetails().getHomeClub());
        } else {
            enrolmentData.setFpOrBunker(submission.getMembershipDetails().getFpOrBunker());
        }

        enrolmentData.setGymOrPlay(submission.getMembershipDetails().getGymOrPlay());
        enrolmentData.setDdOrPif(submission.getMembershipDetails().getDdOrPif());
        enrolmentData.setNoCommitment(submission.getMembershipDetails().getNoCommitment());
        enrolmentData.setPifOptions(submission.getMembershipDetails().getPifOptions());
        enrolmentData.setStartDate(submission.getMembershipDetails().getStartDate());
        enrolmentData.setMembershipTermsAcknowledgment(Helpers.arrayToString(submission.getMembershipDetails().getMembershipTermsAcknowledgment()));
        enrolmentData.setTrainingStarterPack(submission.getMembershipDetails().getTrainingStarterPack());
        enrolmentData.setPersonalTrainer(submission.getMembershipDetails().getPersonalTrainers());
        enrolmentData.setLifestylePersonalTraining(Helpers.arrayToString(submission.getMembershipDetails().getLifestylePersonalTraining()));
        enrolmentData.setNumberSessionsPerWeek(submission.getMembershipDetails().getNumberSessionsPerWeek());
        enrolmentData.setSessionLength(submission.getMembershipDetails().getSessionLength());
        enrolmentData.setExternalPTSessionPrice(submission.getMembershipDetails().getExternalPTSessionPrice());
        enrolmentData.setPersonalTrainingStartDate(submission.getMembershipDetails().getPersonalTrainingStartDate());
        enrolmentData.setPersonalTrainingTermsAcknowledgment(Helpers.arrayToString(submission.getMembershipDetails().getPersonalTrainingTermsAcknowledgment()));
        enrolmentData.setCouponCode(submission.getMembershipDetails().getCouponCode());
        enrolmentData.setDaysFree(submission.getMembershipDetails().getDaysFree());
        enrolmentData.setAccessKeyDiscount(submission.getMembershipDetails().getAccessKeyDiscount());
        enrolmentData.setFreePTPack(submission.getMembershipDetails().getFreePTPack());
        enrolmentData.setAccessKeyPaymentOptions(submission.getMembershipDetails().getAccessKeyPaymentOptions());
        enrolmentData.setStaffMember(submission.getMembershipDetails().getStaffMembers());
        enrolmentData.setCreche(submission.getMembershipDetails().getCreche());

        enrolmentData.setAgreement(Helpers.arrayToString(submission.getLegalDetails().getAgreement()));
        enrolmentData.setContractCommitment(Helpers.arrayToString(submission.getLegalDetails().getContractCommitment()));
        enrolmentData.setPt6SessionCommitment(Helpers.arrayToString(submission.getLegalDetails().getPt6SessionCommitment()));
        enrolmentData.setAccessKeyNumber(submission.getLegalDetails().getAccessKeyNumber());
        enrolmentData.setNotes(submission.getLegalDetails().getNotes());

        enrolmentData.setInjuries(Helpers.arrayToString(submission.getHealthCheck().getInjuries()));
        enrolmentData.setMedical(Helpers.arrayToString(submission.getHealthCheck().getMedical()));
        enrolmentData.setMedicalClearance(submission.getHealthCheck().getMedicalClearance());
        enrolmentData.setTrainingAvailability(Helpers.arrayToString(submission.getHealthCheck().getTrainingAvailability()));
        enrolmentData.setTimeAvailability(Helpers.arrayToString(submission.getHealthCheck().getTimeAvailability()));

        enrolmentData.setPaymentType(submission.getPaymentDetails().getPaymentType());
        enrolmentData.setUseExistingDetails(submission.getPaymentDetails().getUseExistingDetails());
        enrolmentData.setGetAuthorisation(Helpers.arrayToString(submission.getPaymentDetails().getGetAuthorisation()));

        if (submission.getPaymentDetails().getPaymentType().equals(Constants.SUBMISSION_PAYMENT_TYPE_BANK_ACCOUNT)) {
            MemberBankDetail memberBankDetail = MemberBankDetail.from(submission);
            enrolmentData.setMemberBankDetail(memberBankDetail);
        }

        if (submission.getPaymentDetails().getPaymentType().equals(Constants.SUBMISSION_PAYMENT_TYPE_CREDIT_CARD)) {
            MemberCreditCard memberCreditCard = MemberCreditCard.from(submission);
            enrolmentData.setMemberCreditCard(memberCreditCard);
        }

        return enrolmentData;
    }

//    FORMSTACK
    public static EnrolmentData create(EnrolmentDataSubmission submission) {
        EnrolmentData enrolmentData = new EnrolmentData();
        return from(enrolmentData, submission, true);
    }

    public static EnrolmentData update(EnrolmentData enrolmentData, EnrolmentDataSubmission submission, boolean isNewPaymentDetails) {
        return from(enrolmentData, submission, isNewPaymentDetails);
    }

    private static EnrolmentData from(EnrolmentData enrolmentData, EnrolmentDataSubmission submission, boolean isNewPaymentDetails) {

        enrolmentData.setUpdateDate(Helpers.getDateNow());
        enrolmentData.setStatus(submission.getStatus());
        enrolmentData.setCommunicationsStatus(submission.getCommunicationsStatus());

        enrolmentData.setFs_formId(submission.getFs_formId());
        enrolmentData.setFs_uniqueId(submission.getFs_uniqueId());
        enrolmentData.setGymSalesId(submission.getGymSalesId());
        enrolmentData.setGoogleClickId(submission.getGoogleClickId());
        enrolmentData.setFacebookCampaignId(submission.getFacebookCampaignId());
        enrolmentData.setFs_formUrl(submission.getFs_formUrl());
        if (submission.getHasCoach() == null) {
            enrolmentData.setHasCoach(false);
        } else {
            enrolmentData.setHasCoach(submission.getHasCoach());
        }
        if (submission.getExternalPt() == null) {
            enrolmentData.setExternalPt(false);
        } else {
            enrolmentData.setExternalPt(submission.getExternalPt());
        }
        enrolmentData.setPrimarySignatureURL(submission.getPrimarySignatureURL());
        enrolmentData.setUnder18SignatureURL(submission.getUnder18SignatureURL());
        enrolmentData.setPaymentAuthSignatureURL(submission.getPaymentAuthSignatureURL());
        enrolmentData.setMemberContracts(submission.getMemberContracts());
        enrolmentData.setContractNamesToBeActivated(submission.getContractNamesToBeActivated());
        if (submission.getExistingClient() == null) {
            enrolmentData.setExistingClient(false);
        } else {
            enrolmentData.setExistingClient(submission.getExistingClient());
        }
        enrolmentData.setFirstBillingDate(submission.getFirstBillingDate());
        enrolmentData.setCreateDate(submission.getCreateDate());
        enrolmentData.setUID(submission.getUID());

        enrolmentData.setLocationId(submission.getLocationId());
        enrolmentData.setGymName(submission.getGymName());

        enrolmentData.setFirstName(submission.getFirstName());
        enrolmentData.setLastName(submission.getLastName());
        enrolmentData.setPhone(submission.getPhone());
        enrolmentData.setEmail(submission.getEmail());

        enrolmentData.setAddress1(Helpers.capitalise(submission.getAddress1()));
        enrolmentData.setAddress2(Helpers.capitalise(submission.getAddress2()));
        enrolmentData.setCity(submission.getCity());
        enrolmentData.setState(submission.getState());
        enrolmentData.setPostcode(submission.getPostcode());
        enrolmentData.setDob(submission.getDob());
        enrolmentData.setGender(submission.getGender());
        enrolmentData.setEmergencyContactName(submission.getEmergencyContactName());
        enrolmentData.setEmergencyContactPhone(submission.getEmergencyContactPhone());
        enrolmentData.setOccupation(submission.getOccupation());
        enrolmentData.setEmployer(submission.getEmployer());
        enrolmentData.setHowDidYouHearAboutUs(submission.getHowDidYouHearAboutUs());
        enrolmentData.setNumberOneGoal(submission.getNumberOneGoal());

        enrolmentData.setFpOrBunker(submission.getFpOrBunker());
        enrolmentData.setGymOrPlay(submission.getGymOrPlay());
        enrolmentData.setDdOrPif(submission.getDdOrPif());
        enrolmentData.setNoCommitment(submission.getNoCommitment());
        enrolmentData.setPifOptions(submission.getPifOptions());
        enrolmentData.setStartDate(submission.getStartDate());
        enrolmentData.setMembershipTermsAcknowledgment(submission.getMembershipTermsAcknowledgment());
        enrolmentData.setTrainingStarterPack(submission.getTrainingStarterPack());
        enrolmentData.setPersonalTrainer(submission.getPersonalTrainer());
        enrolmentData.setPersonalTrainerName(submission.getPersonalTrainerName());
        enrolmentData.setLifestylePersonalTraining(submission.getLifestylePersonalTraining());
        enrolmentData.setNumberSessionsPerWeek(submission.getNumberSessionsPerWeek());
        enrolmentData.setSessionLength(submission.getSessionLength());
        enrolmentData.setExternalPTSessionPrice(submission.getExternalPTSessionPrice());
        enrolmentData.setPersonalTrainingStartDate(submission.getPersonalTrainingStartDate());

        enrolmentData.setPersonalTrainingTermsAcknowledgment(submission.getPersonalTrainingTermsAcknowledgment());
        enrolmentData.setCouponCode(submission.getCouponCode());
        enrolmentData.setDaysFree(submission.getDaysFree());
        enrolmentData.setAccessKeyDiscount(submission.getAccessKeyDiscount());
        enrolmentData.setFreePTPack(submission.getFreePTPack());
        enrolmentData.setAccessKeyPaymentOptions(submission.getAccessKeyPaymentOptions());
        enrolmentData.setAccessKeyPaymentMethod(submission.getAccessKeyPaymentMethod());
        enrolmentData.setStaffMember(submission.getStaffMember());
        enrolmentData.setStaffName(submission.getStaffName());
        enrolmentData.setCreche(submission.getCreche());

        enrolmentData.setAgreement(submission.getAgreement());
        enrolmentData.setContractCommitment(submission.getContractCommitment());
        enrolmentData.setPt6SessionCommitment(submission.getPt6SessionCommitment());
        enrolmentData.setAccessKeyNumber(submission.getAccessKeyNumber());
        enrolmentData.setNotes(submission.getNotes());

        enrolmentData.setInjuries(submission.getInjuries());
        enrolmentData.setMedical(submission.getMedical());
        enrolmentData.setMedicalClearance(submission.getMedicalClearance());
        enrolmentData.setTrainingAvailability(submission.getTrainingAvailability());
        enrolmentData.setTimeAvailability(submission.getTimeAvailability());

        enrolmentData.setPaymentType(submission.getPaymentType());
        enrolmentData.setUseExistingDetails(submission.getUseExistingDetails());
        enrolmentData.setGetAuthorisation(submission.getGetAuthorisation());

        enrolmentData.setRenewalStatus(submission.getRenewalStatus());
        enrolmentData.setTrainingPackageSoldBy(submission.getTrainingPackageSoldBy());
        enrolmentData.setTrainingPackageConsultantLocationId(submission.getTrainingPackageConsultantLocationId());
        enrolmentData.setTrainingPackageConsultantName(submission.getTrainingPackageConsultantName());
        enrolmentData.setTrainingPackageConsultantMboId(submission.getTrainingPackageConsultantMboId());
        enrolmentData.setServiceNamesToBeActivated(submission.getServiceNamesToBeActivated());

        if (isNewPaymentDetails) {
            if (submission.getMemberBankDetail() != null) {
                enrolmentData.setMemberBankDetail(MemberBankDetail.create(submission.getMemberBankDetail()));
            }

            if (submission.getMemberCreditCard() != null) {
                enrolmentData.setMemberCreditCard(MemberCreditCard.create(submission.getMemberCreditCard()));
            }
        } else {
            if (submission.getMemberBankDetail() != null) {
                enrolmentData.setMemberBankDetail(MemberBankDetail.update(submission.getMemberBankDetail(), enrolmentData.getMemberBankDetail()));
            }

            if (submission.getMemberCreditCard() != null) {
                enrolmentData.setMemberCreditCard(MemberCreditCard.update(submission.getMemberCreditCard(), enrolmentData.getMemberCreditCard()));
            }
        }

        enrolmentData.setHasReferral(submission.getHasReferral());
        enrolmentData.setReferralName(submission.getReferralName());
        enrolmentData.setReferralEmail(submission.getReferralEmail());
        enrolmentData.setReferralPhone(submission.getReferralPhone());
        enrolmentData.setDollarValue(submission.getDollarValue());
        enrolmentData.setMemberPhotoURL(submission.getMemberPhotoURL());
        enrolmentData.setVirtualPlaygroundCommencement(submission.getVirtualPlaygroundCommencement());
        enrolmentData.setMboUniqueId(submission.getMboUniqueId());
        enrolmentData.setPreExId(submission.getPreExId());
        enrolmentData.setCovidVerificationURL(submission.getCovidVerificationURL());

        return enrolmentData;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public boolean isExistingClient() {
        return existingClient;
    }

    public void setExistingClient(boolean existingClient) {
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

    public String getGetAuthorisation() {
        return getAuthorisation;
    }

    public void setGetAuthorisation(String getAuthorisation) {
        this.getAuthorisation = getAuthorisation;
    }

    public String getFpOrBunker() {
        return fpOrBunker;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
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

    public MemberCreditCard getMemberCreditCard() {
        return memberCreditCard;
    }

    public void setMemberCreditCard(MemberCreditCard memberCreditCard) {
        this.memberCreditCard = memberCreditCard;
    }

    //    public MemberCreditCard getCreditCard() {
//        return creditCard;
//    }
//
//    public void setCreditCard(MemberCreditCard creditCard) {
//        this.creditCard = creditCard;
//    }

    public MemberBankDetail getMemberBankDetail() {
        return memberBankDetail;
    }

    public void setMemberBankDetail(MemberBankDetail memberBankDetail) {
        this.memberBankDetail = memberBankDetail;
    }

    public String getAccessKeyPaymentOptions() {
        return accessKeyPaymentOptions;
    }

    public void setAccessKeyPaymentOptions(String accessKeyPaymentOptions) {
        this.accessKeyPaymentOptions = accessKeyPaymentOptions;
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

    public String getPt6SessionCommitment() {
        return pt6SessionCommitment;
    }

    public void setPt6SessionCommitment(String pt6SessionCommitment) {
        this.pt6SessionCommitment = pt6SessionCommitment;
    }

    public String getCreche() {
        return creche;
    }

    public void setCreche(String creche) {
        this.creche = creche;
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
        return "EnrolmentData{" +
                "id=" + id +
                ", updateDate='" + updateDate + '\'' +
                ", status='" + status + '\'' +
                ", gymName='" + gymName + '\'' +
                ", contractNamesToBeActivated='" + contractNamesToBeActivated + '\'' +
                ", fs_formId='" + fs_formId + '\'' +
                ", fs_uniqueId='" + fs_uniqueId + '\'' +
                ", gymSalesId='" + gymSalesId + '\'' +
                ", googleClickId='" + googleClickId + '\'' +
                ", facebookCampaignId='" + facebookCampaignId + '\'' +
                ", fs_formUrl='" + fs_formUrl + '\'' +
                ", isExternalPt=" + isExternalPt +
                ", hasCoach=" + hasCoach +
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
                ", staffName='" + staffName + '\'' +
                ", personalTrainerName='" + personalTrainerName + '\'' +
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
                ", trainingPackageSoldBy='" + trainingPackageSoldBy + '\'' +
                ", trainingPackageConsultantLocationId='" + trainingPackageConsultantLocationId + '\'' +
                ", trainingPackageConsultantName='" + trainingPackageConsultantName + '\'' +
                ", trainingPackageConsultantMboId='" + trainingPackageConsultantMboId + '\'' +
                ", communicationsStatus='" + communicationsStatus + '\'' +
                ", serviceNamesToBeActivated='" + serviceNamesToBeActivated + '\'' +
                ", renewalStatus='" + renewalStatus + '\'' +
                ", hasReferral=" + hasReferral +
                ", referralName='" + referralName + '\'' +
                ", referralPhone='" + referralPhone + '\'' +
                ", referralEmail='" + referralEmail + '\'' +
                ", dollarValue=" + dollarValue +
                ", memberPhotoURL='" + memberPhotoURL + '\'' +
                ", virtualPlaygroundCommencement='" + virtualPlaygroundCommencement + '\'' +
                ", memberCreditCard=" + memberCreditCard +
                ", memberBankDetail=" + memberBankDetail +
                '}';
    }
}
