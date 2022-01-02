package com.fitnessplayground.dao.domain;

import com.fitnessplayground.dao.domain.temp.FpCoachEnrolmentSubmission;
import com.fitnessplayground.dao.domain.temp.PaymentDetails;
import com.fitnessplayground.util.Constants;
import com.fitnessplayground.util.Helpers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Entity
public class FpCoachEnrolmentData {

    private static final Logger logger = LoggerFactory.getLogger(FpCoachEnrolmentData.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String status;
    private String updateDate;

    private String createDate;
    private String fs_formId;
    private String fs_uniqueId;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String gymName;
    private Integer locationId;
    private String personalTrainer;
    private String personalTrainerName;
    private String coachingModality;
    private String trainingOptions;
    private String sessionLength;
    private Boolean externalClient;
    private String externalPTSessionPrice;
    private String numberSessioinsPerWeek;
    private String mboContractNames;
    private String mboContractIds;
    private String couponCode;
    private String fnDD;
    private String mtDD;
    private String oneOffTotal;
    private String startDate;
    private String paymentType;
    private String agreement;
    private String signature;
    private String staffMember;
    private String staffName;
    private Boolean isOrganisedByCoach;
    private String notes;

    private String UID;
    private String activeCampaignId;
    private String communicationsStatus;
    private String useExistingDetails;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "creditCardId")
    private MemberCreditCard memberCreditCard;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bankDetailId")
    private MemberBankDetail memberBankDetail;

    public FpCoachEnrolmentData() {
    }

    public static FpCoachEnrolmentData create(FpCoachEnrolmentSubmission submission) {
        FpCoachEnrolmentData fpCoachEnrolmentData = new FpCoachEnrolmentData();
        return from(fpCoachEnrolmentData, submission, true);
    }

    public static FpCoachEnrolmentData update(FpCoachEnrolmentData data, FpCoachEnrolmentSubmission submission, boolean isNewPaymentDetails) {
        return from(data, submission, isNewPaymentDetails);
    }

    private static FpCoachEnrolmentData from(FpCoachEnrolmentData data, FpCoachEnrolmentSubmission submission, boolean isNewPaymentDetails) {

        data.setUpdateDate(Helpers.getDateNow());
        data.setCreateDate(submission.getCreateDate());
        data.setFs_formId(submission.getFs_formId());
        data.setFs_uniqueId(submission.getFs_uniqueId());
        data.setFirstName(submission.getFirstName());
        data.setLastName(submission.getLastName());
        data.setPhone(submission.getPhone());
        data.setEmail(submission.getEmail());
        data.setGymName(submission.getGymName());
        data.setLocationId(submission.getLocationId());
        data.setPersonalTrainer(submission.getPersonalTrainer());
        data.setPersonalTrainerName(submission.getPersonalTrainerName());
        data.setCoachingModality(submission.getCoachingModality());
        data.setTrainingOptions(submission.getTrainingOptions());
        data.setSessionLength(submission.getSessionLength());
        data.setExternalClient(submission.getExternalClient());
        data.setExternalPTSessionPrice(submission.getExternalPTSessionPrice());
        data.setNumberSessioinsPerWeek(submission.getNumberSessioinsPerWeek());
        data.setMboContractNames(submission.getMboContractNames());
        data.setMboContractIds(submission.getMboContractIds());
        data.setCouponCode(submission.getCouponCode());
        data.setFnDD(submission.getFnDD());
        data.setMtDD(submission.getMtDD());
        data.setOneOffTotal(submission.getOneOffTotal());
        data.setStartDate(submission.getStartDate());
        data.setPaymentType(submission.getPaymentType());
        data.setAgreement(submission.getAgreement());
        data.setSignature(submission.getSignature());
        data.setStaffMember(submission.getStaffMember());
        data.setStaffName(submission.getStaffName());
        data.setOrganisedByCoach(submission.getOrganisedByCoach());
        data.setNotes(submission.getNotes());

        if (isNewPaymentDetails) {
            if (submission.getMemberBankDetail() != null) {
                data.setMemberBankDetail(MemberBankDetail.create(submission.getMemberBankDetail()));
            }

            if (submission.getMemberCreditCard() != null) {
                data.setMemberCreditCard(MemberCreditCard.create(submission.getMemberCreditCard()));
            }
        } else {
            if (submission.getMemberBankDetail() != null) {
                data.setMemberBankDetail(MemberBankDetail.update(submission.getMemberBankDetail(), data.getMemberBankDetail()));
            }

            if (submission.getMemberCreditCard() != null) {
                data.setMemberCreditCard(MemberCreditCard.update(submission.getMemberCreditCard(), data.getMemberCreditCard()));
            }
        }

        data.setUID(submission.getUID());
        data.setActiveCampaignId(submission.getActiveCampaignId());
        data.setCommunicationsStatus(submission.getCommunicationsStatus());
        data.setStatus(submission.getStatus());
        data.setUseExistingDetails(submission.getUseExistingDetails());

        return data;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Boolean getOrganisedByCoach() {
        return isOrganisedByCoach;
    }

    public void setOrganisedByCoach(Boolean organisedByCoach) {
        isOrganisedByCoach = organisedByCoach;
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

    public String getUseExistingDetails() {
        return useExistingDetails;
    }

    public void setUseExistingDetails(String useExistingDetails) {
        this.useExistingDetails = useExistingDetails;
    }

    public MemberCreditCard getMemberCreditCard() {
        return memberCreditCard;
    }

    public void setMemberCreditCard(MemberCreditCard memberCreditCard) {
        this.memberCreditCard = memberCreditCard;
    }

    public MemberBankDetail getMemberBankDetail() {
        return memberBankDetail;
    }

    public void setMemberBankDetail(MemberBankDetail memberBankDetail) {
        this.memberBankDetail = memberBankDetail;
    }


    @Override
    public String toString() {
        return "FpCoachEnrolmentData{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", updateDate='" + updateDate + '\'' +
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
                ", agreement='" + agreement + '\'' +
                ", signature='" + signature + '\'' +
                ", staffMember='" + staffMember + '\'' +
                ", staffName='" + staffName + '\'' +
                ", isOrganisedByCoach=" + isOrganisedByCoach +
                ", notes='" + notes + '\'' +
                ", UID='" + UID + '\'' +
                ", activeCampaignId='" + activeCampaignId + '\'' +
                ", communicationStatus='" + communicationsStatus + '\'' +
                ", useExistingDetails='" + useExistingDetails + '\'' +
                ", memberCreditCard=" + memberCreditCard +
                ", memberBankDetail=" + memberBankDetail +
                '}';
    }
}
