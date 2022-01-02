package com.fitnessplayground.dao.domain.ActiveCampaignDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ACContact {

    @JsonProperty("cdate")
    private String cdate;//"2017-01-25T23:58:14-06:00",
    @JsonProperty("email")
    private String email;//"janedoe@example.com",
    @JsonProperty("phone")
    private String phone;//"3120000000",
    @JsonProperty("firstName")
    private String firstName;//"John",
    @JsonProperty("lastName")
    private String lastName;//"Doe",
    @JsonProperty("orgid")
    private String orgid;//"0",
    @JsonProperty("segmentio_id")
    private String segmentio_id;//"",
    @JsonProperty("bounced_hard")
    private String bounced_hard;//"0",
    @JsonProperty("bounced_soft")
    private String bounced_soft;//"0",
    @JsonProperty("bounced_date")
    private String bounced_date;//"0000-00-00",
    @JsonProperty("ip")
    private String ip;//"0",
    @JsonProperty("ua")
    private String ua;//"",
    @JsonProperty("hash")
    private String hash;//"31e076c964f4262817f9ba302c96e1c6",
    @JsonProperty("socialdata_lastcheck")
    private String socialdata_lastcheck;//"0000-00-00 00:00:00",
    @JsonProperty("email_local")
    private String email_local;//"",
    @JsonProperty("email_domain")
    private String email_domain;//"",
    @JsonProperty("sentcnt")
    private String sentcnt;//"0",
    @JsonProperty("rating_tstamp")
    private String rating_tstamp;//"0000-00-00",
    @JsonProperty("gravatar")
    private String gravatar;//"3",
    @JsonProperty("deleted")
    private String deleted;//"0",
    @JsonProperty("adate")
    private String adate;//"2017-02-22 15:26:24",
    @JsonProperty("udate")
    private String udate;//"2017-01-25T23:58:14-06:00",
    @JsonProperty("edate")
    private String edate;//"2017-01-27 14:44:13",
//    @JsonProperty("")
//     "scoreValues":[ ],
    @JsonProperty("links")
    private ACLinks links;
    @JsonProperty("id")
    private String id;//"68",
    @JsonProperty("organization")
    private String organization;//null

    public ACContact() {
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    public String getSegmentio_id() {
        return segmentio_id;
    }

    public void setSegmentio_id(String segmentio_id) {
        this.segmentio_id = segmentio_id;
    }

    public String getBounced_hard() {
        return bounced_hard;
    }

    public void setBounced_hard(String bounced_hard) {
        this.bounced_hard = bounced_hard;
    }

    public String getBounced_soft() {
        return bounced_soft;
    }

    public void setBounced_soft(String bounced_soft) {
        this.bounced_soft = bounced_soft;
    }

    public String getBounced_date() {
        return bounced_date;
    }

    public void setBounced_date(String bounced_date) {
        this.bounced_date = bounced_date;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSocialdata_lastcheck() {
        return socialdata_lastcheck;
    }

    public void setSocialdata_lastcheck(String socialdata_lastcheck) {
        this.socialdata_lastcheck = socialdata_lastcheck;
    }

    public String getEmail_local() {
        return email_local;
    }

    public void setEmail_local(String email_local) {
        this.email_local = email_local;
    }

    public String getEmail_domain() {
        return email_domain;
    }

    public void setEmail_domain(String email_domain) {
        this.email_domain = email_domain;
    }

    public String getSentcnt() {
        return sentcnt;
    }

    public void setSentcnt(String sentcnt) {
        this.sentcnt = sentcnt;
    }

    public String getRating_tstamp() {
        return rating_tstamp;
    }

    public void setRating_tstamp(String rating_tstamp) {
        this.rating_tstamp = rating_tstamp;
    }

    public String getGravatar() {
        return gravatar;
    }

    public void setGravatar(String gravatar) {
        this.gravatar = gravatar;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getAdate() {
        return adate;
    }

    public void setAdate(String adate) {
        this.adate = adate;
    }

    public String getUdate() {
        return udate;
    }

    public void setUdate(String udate) {
        this.udate = udate;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

    public ACLinks getLinks() {
        return links;
    }

    public void setLinks(ACLinks links) {
        this.links = links;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }


    @Override
    public String toString() {
        return "ACContact{" +
                "cdate='" + cdate + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", orgid='" + orgid + '\'' +
                ", segmentio_id='" + segmentio_id + '\'' +
                ", bounced_hard='" + bounced_hard + '\'' +
                ", bounced_soft='" + bounced_soft + '\'' +
                ", bounced_date='" + bounced_date + '\'' +
                ", ip='" + ip + '\'' +
                ", ua='" + ua + '\'' +
                ", hash='" + hash + '\'' +
                ", socialdata_lastcheck='" + socialdata_lastcheck + '\'' +
                ", email_local='" + email_local + '\'' +
                ", email_domain='" + email_domain + '\'' +
                ", sentcnt='" + sentcnt + '\'' +
                ", rating_tstamp='" + rating_tstamp + '\'' +
                ", gravatar='" + gravatar + '\'' +
                ", deleted='" + deleted + '\'' +
                ", adate='" + adate + '\'' +
                ", udate='" + udate + '\'' +
                ", edate='" + edate + '\'' +
                ", links=" + links +
                ", id='" + id + '\'' +
                ", organization='" + organization + '\'' +
                '}';
    }
}
