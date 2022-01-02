package com.fitnessplayground.dao.domain.temp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GravityFormsWebhookReferral {

    @JsonProperty("date_created")
    private String createDate;
    @JsonProperty("gymName")
    private String gymName;
    @JsonProperty("referral_source")
    private String referralSource;
    @JsonProperty("campaignOffer")
    private String campaignOffer;
    @JsonProperty("redirectUrl")
    private String redirectUrl;
    @JsonProperty("memberFullName")
    private String memberFullName;
//    @JsonProperty("memberLastName")
//    private String memberLastName;
    @JsonProperty("memberEmail")
    private String memberEmail;
    @JsonProperty("referrerMboId")
    private String referrerMboId;
    @JsonProperty("referMboUniqueId")
    private String referMboUniqueId;
    @JsonProperty("referralOneFullName")
    private String referralOneFullName;
//    @JsonProperty("referralOneLastName")
//    private String referralOneLastName;
    @JsonProperty("referralOnePhone")
    private String referralOnePhone;
    @JsonProperty("referralTwoFullName")
    private String referralTwoFullName;
//    @JsonProperty("referralTwoLastName")
//    private String referralTwoLastName;
    @JsonProperty("referralTwoPhone")
    private String referralTwoPhone;
    @JsonProperty("referralThreeFullName")
    private String referralThreeFullName;
//    @JsonProperty("referralThreeLastName")
//    private String referralThreeLastName;
    @JsonProperty("referralThreePhone")
    private String referralThreePhone;

    @JsonProperty("fbclid")
    private String facebookCampaignId;
    @JsonProperty("gclid")
    private String googleClickId;

    public GravityFormsWebhookReferral() {
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getGymName() {
        return gymName;
    }

    public void setGymName(String gymName) {
        this.gymName = gymName;
    }

    public String getReferralSource() {
        return referralSource;
    }

    public void setReferralSource(String referralSource) {
        this.referralSource = referralSource;
    }

    public String getCampaignOffer() {
        return campaignOffer;
    }

    public void setCampaignOffer(String campaignOffer) {
        this.campaignOffer = campaignOffer;
    }

    public String getGoogleClickId() {
        return googleClickId;
    }

    public void setGoogleClickId(String googleClickId) {
        this.googleClickId = googleClickId;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getMemberFullName() {
        return memberFullName;
    }

    public void setMemberFullName(String memberFullName) {
        this.memberFullName = memberFullName;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getReferrerMboId() {
        return referrerMboId;
    }

    public void setReferrerMboId(String referrerMboId) {
        this.referrerMboId = referrerMboId;
    }

    public String getReferMboUniqueId() {
        return referMboUniqueId;
    }

    public void setReferMboUniqueId(String referMboUniqueId) {
        this.referMboUniqueId = referMboUniqueId;
    }

    public String getReferralOneFullName() {
        return referralOneFullName;
    }

    public void setReferralOneFullName(String referralOneFullName) {
        this.referralOneFullName = referralOneFullName;
    }

    public String getReferralOnePhone() {
        return referralOnePhone;
    }

    public void setReferralOnePhone(String referralOnePhone) {
        this.referralOnePhone = referralOnePhone;
    }

    public String getReferralTwoFullName() {
        return referralTwoFullName;
    }

    public void setReferralTwoFullName(String referralTwoFullName) {
        this.referralTwoFullName = referralTwoFullName;
    }

    public String getReferralTwoPhone() {
        return referralTwoPhone;
    }

    public void setReferralTwoPhone(String referralTwoPhone) {
        this.referralTwoPhone = referralTwoPhone;
    }

    public String getReferralThreeFullName() {
        return referralThreeFullName;
    }

    public void setReferralThreeFullName(String referralThreeFullName) {
        this.referralThreeFullName = referralThreeFullName;
    }

    public String getReferralThreePhone() {
        return referralThreePhone;
    }

    public void setReferralThreePhone(String referralThreePhone) {
        this.referralThreePhone = referralThreePhone;
    }

    public String getFacebookCampaignId() {
        return facebookCampaignId;
    }

    public void setFacebookCampaignId(String facebookCampaignId) {
        this.facebookCampaignId = facebookCampaignId;
    }

    @Override
    public String toString() {
        return "GravityFormsWebhookReferral{" +
                "createDate='" + createDate + '\'' +
                ", gymName='" + gymName + '\'' +
                ", referralSource='" + referralSource + '\'' +
                ", campaignOffer='" + campaignOffer + '\'' +
                ", googleClickId='" + googleClickId + '\'' +
                ", redirectUrl='" + redirectUrl + '\'' +
                ", memberFullName='" + memberFullName + '\'' +
                ", memberEmail='" + memberEmail + '\'' +
                ", referrerMboId='" + referrerMboId + '\'' +
                ", referMboUniqueId='" + referMboUniqueId + '\'' +
                ", referralOneFullName='" + referralOneFullName + '\'' +
                ", referralOnePhone='" + referralOnePhone + '\'' +
                ", referralTwoFullName='" + referralTwoFullName + '\'' +
                ", referralTwoPhone='" + referralTwoPhone + '\'' +
                ", referralThreeFullName='" + referralThreeFullName + '\'' +
                ", referralThreePhone='" + referralThreePhone + '\'' +
                '}';
    }
}
