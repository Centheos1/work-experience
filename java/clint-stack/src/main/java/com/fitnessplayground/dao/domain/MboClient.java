package com.fitnessplayground.dao.domain;

import com.fitnessplayground.dao.domain.mboDto.Client;
import com.fitnessplayground.dao.domain.mboDto.EventDataClient;
import com.fitnessplayground.dao.domain.mboDto.SalesRep;
import com.fitnessplayground.util.GymName;
import com.fitnessplayground.util.Helpers;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class MboClient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String appointmentGenderPreference; //: "None",
    private String birthDate; // null,
    private String country; // "AU",
    private String creationDate; //"2015-05-17T22:53:43.02",
    private String billingAddress;
    private String cardHolder;
    private String cardNumber;
    private String cardType;
    private String billingCity;
    private String expMonth;
    private String expYear;
    private String lastFour;
    private String billingPostCode;
    private String billingState;
    private String firstAppointmentDate; //"2015-05-18T00:00:00",
    private String firstName; // " ",
    private String accessKeyNumber;
    private boolean isCompany;
    private boolean isProspect;
    private String lastName;
    private String agreementDate;
    private boolean isReleased;
    private String releasedBy;
    private boolean liabilityRelease;
    private String notes;
    private String state;
    private String uniqueId;// 100004684
    private String lastModifiedDateTime;// "2018-03-06T17:32:33.1Z",
    private String middleName;// null,
    private String email;// null,
    private String phone;// null,
    private String addressLine1;
    private String addressLine2;
    private String city;// null,
    private String postCode;// null,
    private String emergencyContactName;// null,
    private String emergencyContactEmail;// null,
    private String emergencyContactPhone;// null,
    private String gender;
    private boolean active;// true,
    private String salesRep1;
    private String salesRep2;
    private String status;// "Non-Member",
    private boolean sendAccountEmails;
    private boolean sendAccountTexts;
    private boolean sendPromotionalEmails;
    private boolean sendPromotionalTexts;
    private boolean sendScheduleEmails;
    private boolean sendScheduleTexts;// false
    private int homeLocationID;
    private String membershipName;
    private Long siteId;
//    TODO - this needs to be implemented
//    private Float accountBalance;
    private String previousStatus;
    private String updateDate;


    private Integer clientNumberOfVisitsAtSite; //0
    private String creditCardLastFour; //"1111"
    private String creditCardExpDate; //"2022-09-30T00:00:00Z"
    private String directDebitLastFour; //"4321"
    private String photoUrl; //null
    private String previousEmail; //null


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "mboClientId")
    private List<MboClientContract> mboClientContractList;

    public MboClient() {
    }


    public static MboClient save(com.fitnessplayground.model.mindbody.api.client.Client client, String siteId) {
        MboClient mboClient = new MboClient();
        mboClient.setHomeLocationID(client.getHomeLocation().getID().getValue());
        mboClient.setSiteId(Long.parseLong(siteId));
        return build(client, mboClient);
    }

    public static MboClient update(com.fitnessplayground.model.mindbody.api.client.Client client, MboClient mboClient, String siteId) {
        mboClient.setHomeLocationID(client.getHomeLocation().getID().getValue());
        mboClient.setSiteId(Long.parseLong(siteId));
        return build(client, mboClient);
    }

    public static MboClient save(Client client, String locId) {
        MboClient mboClient = new MboClient();
        GymName gym = GymName.getGymName(Integer.parseInt(locId));
        mboClient.setHomeLocationID(gym.getLocationId());
        mboClient.setSiteId(gym.getSiteId());
        return build(client, mboClient);
    }

    public static MboClient update(Client client, MboClient mboClient, String locId) {
        GymName gym = GymName.getGymName(Integer.parseInt(locId));
        mboClient.setSiteId(gym.getSiteId());
        return build(client, mboClient);
    }

    public static MboClient build(com.fitnessplayground.model.mindbody.api.client.Client c, MboClient mboClient) {

        mboClient.setAppointmentGenderPreference(c.getAppointmentGenderPreference());
        mboClient.setBirthDate(c.getBirthDate().getValue().toString());
        mboClient.setCountry(c.getCountry());
        mboClient.setCreationDate(c.getCreationDate().getValue().toString());
        if (c.getClientCreditCard() != null) {
            mboClient.setBillingAddress(c.getClientCreditCard().getAddress());
            mboClient.setCardHolder(c.getClientCreditCard().getCardHolder());
            mboClient.setCardNumber(c.getClientCreditCard().getCardNumber());
            mboClient.setCardType(c.getClientCreditCard().getCardType());
            mboClient.setBillingCity(c.getClientCreditCard().getCity());
            mboClient.setBillingState(c.getClientCreditCard().getState());
            mboClient.setBillingPostCode(c.getClientCreditCard().getPostalCode());
            mboClient.setExpMonth(c.getClientCreditCard().getExpMonth());
            mboClient.setExpYear(c.getClientCreditCard().getExpYear());
        }
        mboClient.setFirstAppointmentDate(c.getFirstAppointmentDate().getValue().toString());
        mboClient.setFirstName(c.getFirstName());
        mboClient.setAccessKeyNumber(c.getID());
        mboClient.setCompany(c.getIsCompany().getValue());
        mboClient.setProspect(c.getIsProspect().getValue());
        mboClient.setLastName(c.getLastName());
        mboClient.setAgreementDate(c.getLiability().getAgreementDate().toString());
//        mboClient.setReleased(c.getLiability().isIsReleased());
        mboClient.setReleasedBy(c.getLiability().getReleasedBy().toString());
        mboClient.setLiabilityRelease(c.getLiability().isIsReleased());
        mboClient.setNotes(c.getNotes());
        mboClient.setState(c.getState());
        mboClient.setUniqueId(c.getUniqueID().toString());
        mboClient.setLastModifiedDateTime(Helpers.cleanDateTime(c.getLastModifiedDateTime().toString()));
        mboClient.setMiddleName(c.getMiddleName());
        mboClient.setEmail(c.getEmail());
        mboClient.setPhone(c.getMobilePhone());
        mboClient.setAddressLine1(c.getAddressLine1());
        mboClient.setAddressLine2(c.getAddressLine2());
        mboClient.setCity(c.getCity());
        mboClient.setPostCode(c.getPostalCode());
        mboClient.setEmergencyContactName(c.getEmergencyContactInfoName());
        mboClient.setEmergencyContactEmail(c.getEmergencyContactInfoEmail());
        mboClient.setEmergencyContactPhone(c.getEmergencyContactInfoPhone());
        mboClient.setGender(c.getGender());
        mboClient.setActive(c.getInactive().getValue());
        if (c.getSaleReps() != null) {
            for (com.fitnessplayground.model.mindbody.api.client.SalesRep r : c.getSaleReps().getSalesRep()) {
                if (r.getSalesRepNumber().getValue() == 1) {
                    mboClient.setSalesRep1(r.getFirstName() + " " + r.getLastName());
                }
                if (r.getSalesRepNumber().getValue() == 2) {
                    mboClient.setSalesRep2(r.getFirstName()+" "+r.getLastName());
                }
            }
        }
        mboClient.setPreviousStatus(mboClient.getStatus());
        mboClient.setStatus(c.getStatus());
        mboClient.setSendAccountEmails(c.getEmailOptIn().getValue());
        mboClient.setSendAccountTexts(c.getEmailOptIn().getValue());
        mboClient.setSendPromotionalEmails(c.getPromotionalEmailOptIn().getValue());
        mboClient.setSendPromotionalTexts(c.getPromotionalEmailOptIn().getValue());
        mboClient.setSendScheduleEmails(c.getEmailOptIn().getValue());
        mboClient.setSendScheduleTexts(c.getEmailOptIn().getValue());
        mboClient.setUpdateDate(Helpers.getDateNow());

        return mboClient;
    }


    private static MboClient build(Client client, MboClient mboClient) {

        mboClient.setAppointmentGenderPreference(client.getAppointmentGenderPreference());
        mboClient.setBirthDate(client.getBirthDate());
        mboClient.setCountry(client.getCountry());
        mboClient.setCreationDate(Helpers.cleanDateTime(client.getCreationDate()));
        if (client.getClientCreditCard() != null) {
            mboClient.setBillingAddress(client.getClientCreditCard().getAddress());
            mboClient.setCardHolder(client.getClientCreditCard().getCardHolder());
            mboClient.setCardNumber(client.getClientCreditCard().getCardNumber());
            mboClient.setCardType(client.getClientCreditCard().getCardType());
            mboClient.setBillingCity(client.getClientCreditCard().getCity());
            mboClient.setBillingState(client.getClientCreditCard().getState());
            mboClient.setBillingPostCode(client.getClientCreditCard().getPostCode());
            mboClient.setExpMonth(client.getClientCreditCard().getExpMonth());
            mboClient.setExpYear(client.getClientCreditCard().getExpYear());
        }
        mboClient.setFirstAppointmentDate(client.getFirstAppointmentDate());
        mboClient.setFirstName(client.getFirstName());
        mboClient.setAccessKeyNumber(client.getAccessKeyNumber());
        mboClient.setCompany(client.isCompany());
        mboClient.setProspect(client.isProspect());
        mboClient.setLastName(client.getLastName());
        mboClient.setAgreementDate(client.getLiability().getAgreementDate());
        mboClient.setReleased(client.getLiability().isReleased());
        mboClient.setReleasedBy(client.getLiability().getReleasedBy());
        mboClient.setLiabilityRelease(client.isLiabilityRelease());
        mboClient.setNotes(client.getNotes());
        mboClient.setState(client.getState());
        mboClient.setUniqueId(client.getUniqueId());
        mboClient.setLastModifiedDateTime(Helpers.cleanDateTime(client.getLastModifiedDateTime()));
        mboClient.setMiddleName(client.getMiddleName());
        mboClient.setEmail(client.getEmail());
        mboClient.setPhone(client.getPhone());
        mboClient.setAddressLine1(client.getAddressLine1());
        mboClient.setAddressLine2(client.getAddressLine2());
        mboClient.setCity(client.getCity());
        mboClient.setPostCode(client.getPostalCode());
        mboClient.setEmergencyContactName(client.getEmergencyContactName());
        mboClient.setEmergencyContactEmail(client.getEmergencyContactEmail());
        mboClient.setEmergencyContactPhone(client.getEmergencyContactPhone());
        mboClient.setGender(client.getGender());
        mboClient.setActive(client.isActive());
        if (!client.getSalesReps().isEmpty()) {
            for (SalesRep r : client.getSalesReps()) {
                if (r.getSalesRepNumber() == 1) {
                    mboClient.setSalesRep1(r.getFirstName() + " " + r.getLastName());
                }
                if (r.getSalesRepNumber() == 2) {
                    mboClient.setSalesRep2(r.getFirstName()+" "+r.getLastName());
                }
            }
        }
        mboClient.setPreviousStatus(mboClient.getStatus());
        mboClient.setStatus(client.getStatus());
        mboClient.setSendAccountEmails(client.isSendAccountEmails());
        mboClient.setSendAccountTexts(client.isSendAccountTexts());
        mboClient.setSendPromotionalEmails(client.isSendPromotionalEmails());
        mboClient.setSendPromotionalTexts(client.isSendPromotionalTexts());
        mboClient.setSendScheduleEmails(client.isSendScheduleEmails());
        mboClient.setSendScheduleTexts(client.isSendScheduleTexts());
        mboClient.setUpdateDate(Helpers.getDateNow());

        return mboClient;
    }

    public static MboClient create(EventDataClient client) {
        MboClient mboClient = new MboClient();
        return build(client, mboClient);
    }

    public static MboClient update(EventDataClient client, MboClient mboClient) {
        return build(client, mboClient);
    }

    public static MboClient build(EventDataClient client, MboClient mboClient) {

        mboClient.setAppointmentGenderPreference(client.getAppointmentGenderPreference());
        mboClient.setBirthDate(Helpers.cleanDateTime(client.getBirthDateTime()));
        mboClient.setCountry(client.getCountry());
        mboClient.setCreationDate(Helpers.cleanDateTime(client.getCreationDateTime()));

        if (client.getCreditCardExpDate() != null) {
            try {
                String[] split = client.getCreditCardExpDate().split("-");
                mboClient.setExpMonth(split[1]);
                mboClient.setExpYear(split[0]);
            } catch (Exception e) {}
        }

        mboClient.setLastFour(client.getCreditCardLastFour());
        mboClient.setFirstAppointmentDate(Helpers.cleanDateTime(client.getFirstAppointmentDateTime()));

        mboClient.setFirstName(client.getFirstName());
        mboClient.setAccessKeyNumber(client.getClientId());
        mboClient.setCompany(client.getCompany());
        mboClient.setProspect(client.getProspect());
        mboClient.setLastName(client.getLastName());
        mboClient.setAgreementDate(Helpers.cleanDateTime(client.getLiabilityAgreementDateTime()));
        mboClient.setReleased(client.getLiabilityReleased());
        mboClient.setNotes(client.getNotes());
        mboClient.setState(client.getState());
        mboClient.setUniqueId(String.valueOf(client.getClientUniqueId()));
        mboClient.setLastModifiedDateTime(Helpers.getDateNow(client.getSiteId()));
        mboClient.setEmail(client.getEmail());
        mboClient.setPhone(client.getMobilePhone());
        mboClient.setAddressLine1(client.getAddressLine1());
        mboClient.setAddressLine2(client.getAddressLine2());
        mboClient.setCity(client.getCity());
        mboClient.setPostCode(client.getPostalCode());
        mboClient.setGender(client.getGender());
        mboClient.setPreviousStatus(mboClient.getStatus());
        mboClient.setStatus(client.getStatus());
        mboClient.setSendAccountEmails(client.getSendAccountEmails());
        mboClient.setSendAccountTexts(client.getSendAccountTexts());
        mboClient.setSendPromotionalEmails(client.getSendPromotionalEmails());
        mboClient.setSendPromotionalTexts(client.getSendPromotionalTexts());
        mboClient.setSendScheduleEmails(client.getSendScheduleEmails());
        mboClient.setSendScheduleTexts(client.getSendScheduleTexts());
        mboClient.setHomeLocationID(client.getHomeLocation());
        mboClient.setSiteId(client.getSiteId());
        mboClient.setUpdateDate(Helpers.getDateNow());
        mboClient.setClientNumberOfVisitsAtSite(client.getClientNumberOfVisitsAtSite());
        mboClient.setCreditCardLastFour(client.getCreditCardLastFour());
        mboClient.setCreditCardExpDate(Helpers.cleanDateTime(client.getCreditCardExpDate()));
        mboClient.setDirectDebitLastFour(client.getDirectDebitLastFour());
        mboClient.setPhotoUrl(client.getPhotoUrl());
        mboClient.setPreviousEmail(client.getPreviousEmail());

        return mboClient;
    }


    public long getId() {
        return id;
    }

    public String getAppointmentGenderPreference() {
        return appointmentGenderPreference;
    }

    public void setAppointmentGenderPreference(String appointmentGenderPreference) {
        this.appointmentGenderPreference = appointmentGenderPreference;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public String getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(String expMonth) {
        this.expMonth = expMonth;
    }

    public String getExpYear() {
        return expYear;
    }

    public void setExpYear(String expYear) {
        this.expYear = expYear;
    }

    public String getLastFour() {
        return lastFour;
    }

    public void setLastFour(String lastFour) {
        this.lastFour = lastFour;
    }

    public String getBillingPostCode() {
        return billingPostCode;
    }

    public void setBillingPostCode(String billingPostCode) {
        this.billingPostCode = billingPostCode;
    }

    public String getBillingState() {
        return billingState;
    }

    public void setBillingState(String billingState) {
        this.billingState = billingState;
    }

    public String getFirstAppointmentDate() {
        return firstAppointmentDate;
    }

    public void setFirstAppointmentDate(String firstAppointmentDate) {
        this.firstAppointmentDate = firstAppointmentDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAccessKeyNumber() {
        return accessKeyNumber;
    }

    public void setAccessKeyNumber(String accessKeyNumber) {
        this.accessKeyNumber = accessKeyNumber;
    }

    public boolean isCompany() {
        return isCompany;
    }

    public void setCompany(boolean company) {
        isCompany = company;
    }

    public boolean isProspect() {
        return isProspect;
    }

    public void setProspect(boolean prospect) {
        isProspect = prospect;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAgreementDate() {
        return agreementDate;
    }

    public void setAgreementDate(String agreementDate) {
        this.agreementDate = agreementDate;
    }

    public boolean isReleased() {
        return isReleased;
    }

    public void setReleased(boolean released) {
        isReleased = released;
    }

    public String getReleasedBy() {
        return releasedBy;
    }

    public void setReleasedBy(String releasedBy) {
        this.releasedBy = releasedBy;
    }

    public boolean isLiabilityRelease() {
        return liabilityRelease;
    }

    public void setLiabilityRelease(boolean liabilityRelease) {
        this.liabilityRelease = liabilityRelease;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    public void setLastModifiedDateTime(String lastModifiedDateTime) {
        this.lastModifiedDateTime = lastModifiedDateTime;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
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

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactEmail() {
        return emergencyContactEmail;
    }

    public void setEmergencyContactEmail(String emergencyContactEmail) {
        this.emergencyContactEmail = emergencyContactEmail;
    }

    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getSalesRep1() {
        return salesRep1;
    }

    public void setSalesRep1(String salesRep1) {
        this.salesRep1 = salesRep1;
    }

    public String getSalesRep2() {
        return salesRep2;
    }

    public void setSalesRep2(String salesRep2) {
        this.salesRep2 = salesRep2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSendAccountEmails() {
        return sendAccountEmails;
    }

    public void setSendAccountEmails(boolean sendAccountEmails) {
        this.sendAccountEmails = sendAccountEmails;
    }

    public boolean isSendAccountTexts() {
        return sendAccountTexts;
    }

    public void setSendAccountTexts(boolean sendAccountTexts) {
        this.sendAccountTexts = sendAccountTexts;
    }

    public boolean isSendPromotionalEmails() {
        return sendPromotionalEmails;
    }

    public void setSendPromotionalEmails(boolean sendPromotionalEmails) {
        this.sendPromotionalEmails = sendPromotionalEmails;
    }

    public boolean isSendPromotionalTexts() {
        return sendPromotionalTexts;
    }

    public void setSendPromotionalTexts(boolean sendPromotionalTexts) {
        this.sendPromotionalTexts = sendPromotionalTexts;
    }

    public boolean isSendScheduleEmails() {
        return sendScheduleEmails;
    }

    public void setSendScheduleEmails(boolean sendScheduleEmails) {
        this.sendScheduleEmails = sendScheduleEmails;
    }

    public boolean isSendScheduleTexts() {
        return sendScheduleTexts;
    }

    public void setSendScheduleTexts(boolean sendScheduleTexts) {
        this.sendScheduleTexts = sendScheduleTexts;
    }

    public int getHomeLocationID() {
        return homeLocationID;
    }

    public void setHomeLocationID(int homeLocationID) {
        this.homeLocationID = homeLocationID;
    }

    public String getMembershipName() {
        return membershipName;
    }

    public void setMembershipName(String membershipName) {
        this.membershipName = membershipName;
    }

    public List<MboClientContract> getMboClientContractList() {
        return mboClientContractList;
    }

    public void setMboClientContractList(List<MboClientContract> mboClientContractList) {
        this.mboClientContractList = mboClientContractList;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }


    public String getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(String previousStatus) {
        this.previousStatus = previousStatus;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }



    public Integer getClientNumberOfVisitsAtSite() {
        return clientNumberOfVisitsAtSite;
    }

    public void setClientNumberOfVisitsAtSite(Integer clientNumberOfVisitsAtSite) {
        this.clientNumberOfVisitsAtSite = clientNumberOfVisitsAtSite;
    }

    public String getCreditCardLastFour() {
        return creditCardLastFour;
    }

    public void setCreditCardLastFour(String creditCardLastFour) {
        this.creditCardLastFour = creditCardLastFour;
    }

    public String getCreditCardExpDate() {
        return creditCardExpDate;
    }

    public void setCreditCardExpDate(String creditCardExpDate) {
        this.creditCardExpDate = creditCardExpDate;
    }

    public String getDirectDebitLastFour() {
        return directDebitLastFour;
    }

    public void setDirectDebitLastFour(String directDebitLastFour) {
        this.directDebitLastFour = directDebitLastFour;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPreviousEmail() {
        return previousEmail;
    }

    public void setPreviousEmail(String previousEmail) {
        this.previousEmail = previousEmail;
    }

    @Override
    public String toString() {
        return "MboClient{" +
                "id=" + id +
                ", appointmentGenderPreference='" + appointmentGenderPreference + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", country='" + country + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", billingAddress='" + billingAddress + '\'' +
                ", cardHolder='" + cardHolder + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", cardType='" + cardType + '\'' +
                ", billingCity='" + billingCity + '\'' +
                ", expMonth='" + expMonth + '\'' +
                ", expYear='" + expYear + '\'' +
                ", lastFour='" + lastFour + '\'' +
                ", billingPostCode='" + billingPostCode + '\'' +
                ", billingState='" + billingState + '\'' +
                ", firstAppointmentDate='" + firstAppointmentDate + '\'' +
                ", firstName='" + firstName + '\'' +
                ", accessKeyNumber='" + accessKeyNumber + '\'' +
                ", isCompany=" + isCompany +
                ", isProspect=" + isProspect +
                ", lastName='" + lastName + '\'' +
                ", agreementDate='" + agreementDate + '\'' +
                ", isReleased=" + isReleased +
                ", releasedBy='" + releasedBy + '\'' +
                ", liabilityRelease=" + liabilityRelease +
                ", notes='" + notes + '\'' +
                ", state='" + state + '\'' +
                ", uniqueId='" + uniqueId + '\'' +
                ", lastModifiedDateTime='" + lastModifiedDateTime + '\'' +
                ", middleName='" + middleName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", city='" + city + '\'' +
                ", postCode='" + postCode + '\'' +
                ", emergencyContactName='" + emergencyContactName + '\'' +
                ", emergencyContactEmail='" + emergencyContactEmail + '\'' +
                ", emergencyContactPhone='" + emergencyContactPhone + '\'' +
                ", gender='" + gender + '\'' +
                ", active=" + active +
                ", salesRep1='" + salesRep1 + '\'' +
                ", salesRep2='" + salesRep2 + '\'' +
                ", status='" + status + '\'' +
                ", sendAccountEmails=" + sendAccountEmails +
                ", sendAccountTexts=" + sendAccountTexts +
                ", sendPromotionalEmails=" + sendPromotionalEmails +
                ", sendPromotionalTexts=" + sendPromotionalTexts +
                ", sendScheduleEmails=" + sendScheduleEmails +
                ", sendScheduleTexts=" + sendScheduleTexts +
                ", homeLocationID=" + homeLocationID +
                ", membershipName='" + membershipName + '\'' +
                ", siteId=" + siteId +
                ", mboClientContractList=" + mboClientContractList +
                '}';
    }
}
