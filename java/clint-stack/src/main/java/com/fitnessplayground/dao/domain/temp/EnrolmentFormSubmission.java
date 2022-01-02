package com.fitnessplayground.dao.domain.temp;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class EnrolmentFormSubmission {

    @SerializedName("primaryDetails")
    private PrimaryDetails primaryDetails;
    @SerializedName("memberDetails")
    private MemberDetails memberDetails;
    @SerializedName("paymentDetails")
    private PaymentDetails paymentDetails;
    @SerializedName("membershipDetails")
    private MembershipDetails membershipDetails;
    @SerializedName("healthCheck")
    private HealthCheck healthCheck;
    @SerializedName("legalDetails")
    private LegalDetails legalDetails;

    @SerializedName("primarySignatureURL")
    private String primarySignatureURL;
    @SerializedName("paymentAuthSignatureURL")
    private String paymentAuthSignatureURL;
    @SerializedName("under18SignatureURL")
    private String under18SignatureURL;

    @SerializedName("memberContracts")
    private int[] memberContracts;
    @SerializedName("existingClient")
    private boolean existingClient;
    @SerializedName("firstBillingDate")
    private String firstBillingDate;
    @SerializedName("createDate")
    private String createDate;
    @SerializedName("locationId")
    private String locationId;
    @SerializedName("UID")
    private String UID;


    public EnrolmentFormSubmission() {
    }

    public PrimaryDetails getPrimaryDetails() {
        return primaryDetails;
    }

    public void setPrimaryDetails(PrimaryDetails primaryDetails) {
        this.primaryDetails = primaryDetails;
    }

    public MemberDetails getMemberDetails() {
        return memberDetails;
    }

    public void setMemberDetails(MemberDetails memberDetails) {
        this.memberDetails = memberDetails;
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public MembershipDetails getMembershipDetails() {
        return membershipDetails;
    }

    public void setMembershipDetails(MembershipDetails membershipDetails) {
        this.membershipDetails = membershipDetails;
    }

    public HealthCheck getHealthCheck() {
        return healthCheck;
    }

    public void setHealthCheck(HealthCheck healthCheck) {
        this.healthCheck = healthCheck;
    }

    public LegalDetails getLegalDetails() {
        return legalDetails;
    }

    public void setLegalDetails(LegalDetails legalDetails) {
        this.legalDetails = legalDetails;
    }

    public int[] getMemberContracts() {
        return memberContracts;
    }

    public void setMemberContracts(int[] memberContracts) {
        this.memberContracts = memberContracts;
    }

    public boolean isExistingClient() {
        return existingClient;
    }

    public void setExistingClient(boolean existingClient) {
        this.existingClient = existingClient;
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

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    @Override
    public String toString() {
        return "EnrolmentFormSubmission{" +
                "primaryDetails=" + primaryDetails +
                ", memberDetails=" + memberDetails +
                ", paymentDetails=" + paymentDetails +
                ", membershipDetails=" + membershipDetails +
                ", healthCheck=" + healthCheck +
                ", legalDetails=" + legalDetails +
//                ", primarySignatureURL='" + primarySignatureURL + '\'' +
//                ", paymentAuthSignatureURL='" + paymentAuthSignatureURL + '\'' +
//                ", under18SignatureURL='" + under18SignatureURL + '\'' +
                ", memberContracts=" + Arrays.toString(memberContracts) +
                ", existingClient=" + existingClient +
                ", firstBillingDate='" + firstBillingDate + '\'' +
                ", createDate='" + createDate + '\'' +
                ", locationId='" + locationId + '\'' +
                ", UID='" + UID + '\'' +
                '}';
    }
}
