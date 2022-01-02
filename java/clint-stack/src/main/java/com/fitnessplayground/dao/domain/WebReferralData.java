package com.fitnessplayground.dao.domain;

import javax.persistence.*;

@Entity
public class WebReferralData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private String createDate;
    private String gymName;
    private String referralSource;
    private String campaignOffer;
    private String googleClickId;
    private String redirectUrl;
    private String memberFirstName;
    private String memberLastName;
    private String memberEmail;
    private String referrerMboId;
    private String referMboUniqueId;
    private String referralOneFirstName;
    private String referralOneLastName;
    private String referralOnePhone;
    private String referralTwoFirstName;
    private String referralTwoLastName;
    private String referralTwoPhone;
    private String referralThreeFirstName;
    private String referralThreeLastName;
    private String referralThreePhone;

    public WebReferralData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getMemberFirstName() {
        return memberFirstName;
    }

    public void setMemberFirstName(String memberFirstName) {
        this.memberFirstName = memberFirstName;
    }

    public String getMemberLastName() {
        return memberLastName;
    }

    public void setMemberLastName(String memberLastName) {
        this.memberLastName = memberLastName;
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

    public String getReferralOneFirstName() {
        return referralOneFirstName;
    }

    public void setReferralOneFirstName(String referralOneFirstName) {
        this.referralOneFirstName = referralOneFirstName;
    }

    public String getReferralOneLastName() {
        return referralOneLastName;
    }

    public void setReferralOneLastName(String referralOneLastName) {
        this.referralOneLastName = referralOneLastName;
    }

    public String getReferralOnePhone() {
        return referralOnePhone;
    }

    public void setReferralOnePhone(String referralOnePhone) {
        this.referralOnePhone = referralOnePhone;
    }

    public String getReferralTwoFirstName() {
        return referralTwoFirstName;
    }

    public void setReferralTwoFirstName(String referralTwoFirstName) {
        this.referralTwoFirstName = referralTwoFirstName;
    }

    public String getReferralTwoLastName() {
        return referralTwoLastName;
    }

    public void setReferralTwoLastName(String referralTwoLastName) {
        this.referralTwoLastName = referralTwoLastName;
    }

    public String getReferralTwoPhone() {
        return referralTwoPhone;
    }

    public void setReferralTwoPhone(String referralTwoPhone) {
        this.referralTwoPhone = referralTwoPhone;
    }

    public String getReferralThreeFirstName() {
        return referralThreeFirstName;
    }

    public void setReferralThreeFirstName(String referralThreeFirstName) {
        this.referralThreeFirstName = referralThreeFirstName;
    }

    public String getReferralThreeLastName() {
        return referralThreeLastName;
    }

    public void setReferralThreeLastName(String referralThreeLastName) {
        this.referralThreeLastName = referralThreeLastName;
    }

    public String getReferralThreePhone() {
        return referralThreePhone;
    }

    public void setReferralThreePhone(String referralThreePhone) {
        this.referralThreePhone = referralThreePhone;
    }

    @Override
    public String toString() {
        return "WebReferralData{" +
                "id=" + id +
                ", createDate='" + createDate + '\'' +
                ", gymName='" + gymName + '\'' +
                ", referralSource='" + referralSource + '\'' +
                ", campaignOffer='" + campaignOffer + '\'' +
                ", googleClickId='" + googleClickId + '\'' +
                ", redirectUrl='" + redirectUrl + '\'' +
                ", memberFirstName='" + memberFirstName + '\'' +
                ", memberLastName='" + memberLastName + '\'' +
                ", memberEmail='" + memberEmail + '\'' +
                ", referrerMboId='" + referrerMboId + '\'' +
                ", referMboUniqueId='" + referMboUniqueId + '\'' +
                ", referralOneFirstName='" + referralOneFirstName + '\'' +
                ", referralOneLastName='" + referralOneLastName + '\'' +
                ", referralOnePhone='" + referralOnePhone + '\'' +
                ", referralTwoFirstName='" + referralTwoFirstName + '\'' +
                ", referralTwoLastName='" + referralTwoLastName + '\'' +
                ", referralTwoPhone='" + referralTwoPhone + '\'' +
                ", referralThreeFirstName='" + referralThreeFirstName + '\'' +
                ", referralThreeLastName='" + referralThreeLastName + '\'' +
                ", referralThreePhone='" + referralThreePhone + '\'' +
                '}';
    }
}
