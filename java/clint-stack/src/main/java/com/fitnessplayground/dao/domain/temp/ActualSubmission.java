package com.fitnessplayground.dao.domain.temp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by micheal on 4/03/2017.
 */
public class ActualSubmission {

    private String gymName;
    @SerializedName("FormID") private String formId;
    @SerializedName("UniqueID") private String uniqueId;
    @SerializedName("Enrolment Form") private String form;

    @SerializedName("Name") private SubmissionName name;
    @SerializedName("Address") private SubmissionAddress submissionAddress;

    @SerializedName("Phone") private String phone;
    @SerializedName("Email") private String email;
    @SerializedName("Date of Birth") private String dateOfBirth;

    @SerializedName("Gender") private String gender;
    @SerializedName("Emergency Contact Name") private String emergencyContactName;
    @SerializedName("Emergency Contact Phone") private String emergencyContactPhone;
    @SerializedName("How Did You Hear About Us?") private String referralHow;
    @SerializedName("Who Referred You?") private String referralWho;
    @SerializedName("Phone Number") private String referPhone;
//    @SerializedName("Payment Details") private String paymentDetails;
//    @SerializedName("Credit Card") private String creditCardNumber;
//    @SerializedName("Expiration Date") private String creditCardExpiry;
//    @SerializedName("Card Verification Code") private String creditCardVerification;
//    @SerializedName("Account Holder Name") private SubmissionName accountHolderName;
//    @SerializedName("Branch") private String branch;
//    @SerializedName("Financial Institution") private String financialInstitution;
//    @SerializedName("BSB") private String bsb;
//    @SerializedName("Account Number") private String accountNumber;
//    @SerializedName("Account Type") private String accountType;
//    @SerializedName("Presentation") private String presentation;
//    @SerializedName("MEMBER IS UNDER 18 YEARS") private String underEighteen;
    @SerializedName("Staff Name") private String staffName;
    @SerializedName("Access Key Number") private String accessKeyNumber;
//    @SerializedName("CHOOSE MEMBERSHIP TYPE") private String submissionMembership;
//    @SerializedName("No Minimum Term") private String minimumTerm;
//    @SerializedName("Creche") private String creche;
//    @SerializedName("Start Date") private String startDate;
//    @SerializedName("Debit Date") private String debitDate;
//    @SerializedName("Pro Rata Days") private String proRataDays;
//    @SerializedName("Access Key") private String accessKey;
//    @SerializedName("TRAINING STARTER PACK") private String trainingStarterPack;
    @SerializedName("TRAINER NAME") private String trainerName;

    private String temp_trainer_1;
    private String temp_trainer_2;
    private String temp_trainer_3;

//    @SerializedName("Pro Rata") private String proRata;
//    @SerializedName("TODAY'S ONE OFF TOTAL") private String todayOneOffTotal;
//    @SerializedName("First Payment Options") private String firstPaymentOptions;
//    @SerializedName("Pay Today") private String payToday;
//    @SerializedName("ONGOING FORTNIGHTLY TOTAL") private String onGoingFortnightlyTotal;
//    @SerializedName("Coupon") private String coupon;
//    @SerializedName("Free PT Pack") private String freePTPack;
//    @SerializedName("Managers Signature") private String managersSignature;
//    @SerializedName("Please Initial to Confirm You Understand this is a 12 Months Agreement") private String initial;
//    @SerializedName("Signature") private String signature;
//    @SerializedName("Manager Signature") private String managerSignature; // mapping name could be wrong
//    @SerializedName("By signing this form, I\\/We authorise Ezidebt acting on behalf of the business, to debit payments from my specified bank account or credit card above, and I\\/We acknowledge that Ezidebit will appear as the merchant on my statement. Furthermore I\\/We agree to reimburse and imdemify Ezidebit for any successful claims made by the Card Holder through their Financial Institution against Ezidebit.   I\\/We authorise Ezidebit Pty Ltd ACN: 096 902 813 (User ID No. 165969) to debit my\\/our account at the Financial Institution identified above through the Bulk Electronic Clearing (BECS) in accordance with the Debit Arrangement Stated above and this direct debt request and as per the Ezidebit DDR Service Agreement provided.  This direct debit authorisation is to remain in force in accordance with the terms and conditions of the Direct Debit request, the provided Ezidebit DDR Service Agreement and I\\/We have read and understood same. I have received a copy, read and fully understood the FITNESS PLAYGROUND (M&E)\\/FITNESS PLAYGROUND SURRY HILLS PTY LTD pricing policy as well as the Terms and Conditions and I agree to be bound by them. *ABOVE MEMBERSHIPS ARE BASED ON A 12 MONTH MINIMUM TERM, FOLLOWED BY MONTH TO MONTH UNLESS NO COMMITMENT TERMS ARE AGREED TO. *ALL MEMBERSHIPS REQUIRE 30 DAYS WRITTEN NOTICE OF YOUR INTENT TO CANCEL.") private String termsAndConditions;
    //@SerializedName("") private String confirmUnderstandAgreement;
//    @SerializedName("Guardian Signature") private String guardianSignature;
//    @SerializedName("Is there anyone you want to refer to The Fitness Playground?") private String anyoneToRefer;
    @SerializedName("Referral Name") private String referralName;
    @SerializedName("Referral1 Phone") private String referral1Phone;
//    @SerializedName("Referral2 Phone") private String referral2Phone;
//    @SerializedName("Pre Exercise Questionnaire") private String preExerciseQuestionnaire;
//    @SerializedName("Add Member Photo") private String memberPhoto;
    @SerializedName("Notes") private String notes;

    public String getGymName() {
        return gymName;
    }

    public void setGymName(String gymName) {
        this.gymName = gymName;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public SubmissionName getName() {
        return name;
    }

    public void setName(SubmissionName name) {
        this.name = name;
    }

    public SubmissionAddress getSubmissionAddress() {
        return submissionAddress;
    }

    public void setSubmissionAddress(SubmissionAddress submissionAddress) {
        this.submissionAddress = submissionAddress;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public String getReferralHow() {
        return referralHow;
    }

    public void setReferralHow(String referralHow) {
        this.referralHow = referralHow;
    }

    public String getReferralWho() {
        return referralWho;
    }

    public void setReferralWho(String referralWho) {
        this.referralWho = referralWho;
    }

    public String getReferPhone() {
        return referPhone;
    }

    public void setReferPhone(String referPhone) {
        this.referPhone = referPhone;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getAccessKeyNumber() {
        return accessKeyNumber;
    }

    public void setAccessKeyNumber(String accessKeyNumber) {
        this.accessKeyNumber = accessKeyNumber;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public String getTemp_trainer_1() {
        return temp_trainer_1;
    }

    public void setTemp_trainer_1(String temp_trainer_1) {
        this.temp_trainer_1 = temp_trainer_1;
    }

    public String getTemp_trainer_2() {
        return temp_trainer_2;
    }

    public void setTemp_trainer_2(String temp_trainer_2) {
        this.temp_trainer_2 = temp_trainer_2;
    }

    public String getTemp_trainer_3() {
        return temp_trainer_3;
    }

    public void setTemp_trainer_3(String temp_trainer_3) {
        this.temp_trainer_3 = temp_trainer_3;
    }

    public String getReferralName() {
        return referralName;
    }

    public void setReferralName(String referralName) {
        this.referralName = referralName;
    }

    public String getReferral1Phone() {
        return referral1Phone;
    }

    public void setReferral1Phone(String referral1Phone) {
        this.referral1Phone = referral1Phone;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "ActualSubmission{" +
                "gymName='" + gymName + '\'' +
                ", formId='" + formId + '\'' +
                ", uniqueId='" + uniqueId + '\'' +
                ", form='" + form + '\'' +
                ", name=" + name +
                ", submissionAddress=" + submissionAddress +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", gender='" + gender + '\'' +
                ", emergencyContactName='" + emergencyContactName + '\'' +
                ", emergencyContactPhone='" + emergencyContactPhone + '\'' +
                ", referralHow='" + referralHow + '\'' +
                ", referralWho='" + referralWho + '\'' +
                ", referPhone='" + referPhone + '\'' +
                ", staffName='" + staffName + '\'' +
                ", accessKeyNumber='" + accessKeyNumber + '\'' +
                ", trainerName='" + trainerName + '\'' +
                ", temp_trainer_1='" + temp_trainer_1 + '\'' +
                ", temp_trainer_2='" + temp_trainer_2 + '\'' +
                ", temp_trainer_3='" + temp_trainer_3 + '\'' +
                ", referralName='" + referralName + '\'' +
                ", referral1Phone='" + referral1Phone + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
