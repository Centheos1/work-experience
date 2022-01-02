package com.fitnessplayground.dao.domain.mboDto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MboAddNewClientRequest {


    @JsonProperty("AccountBalance")
    private Double accountBalance;
    @JsonProperty("Action")
    private String action;
    @JsonProperty("AddressLine1")
    private String addressLine1;
    @JsonProperty("AddressLine2")
    private String addressLine2;
    @JsonProperty("AppointmentGenderPrefMale")
    private Boolean appointmentGenderPrefMale;
    @JsonProperty("BirthDate")
    private String birthDate;
    @JsonProperty("City")
    private String city;
    @JsonProperty("ClientCreditCard")
    private ClientCreditCard clientCreditCard;
    @JsonProperty("ClientIndexes")
    private ArrayList<AssignedClientIndex> clientIndexes;
    @JsonProperty("ClientRelationships")
    private ArrayList<ClientRelationship> clientRelationships;
    @JsonProperty("Country")
    private String country;
    @JsonProperty("CreationDate")
    private String createDate;
    @JsonProperty("Email")
    private String email;
    @JsonProperty("EmergencyContactInfoName")
    private String emergencyContactInfoName;
    @JsonProperty("EmergencyContactInfoRelationship")
    private String emergencyContactInfoRelationship;
    @JsonProperty("EmergencyContactInfoPhone")
    private String emergencyContactInfoPhone;
    @JsonProperty("EmergencyContactInfoEmail")
    private String emergencyContactInfoEmail;
    @JsonProperty("FirstAppointmentDate")
    private String firstAppointmentDate;
    @JsonProperty("FirstName")
    private String firstName;
    @JsonProperty("MiddleName")
    private String middleName;
    @JsonProperty("LastName")
    private String lastName;
    @JsonProperty("Gender")
    private String gender;
    @JsonProperty("HomeLocation")
    private Location homeLocation;
    @JsonProperty("HomePhone")
    private String homePhone;
    @JsonProperty("IsCompany")
    private Boolean isCompany;
    @JsonProperty("IsProspect")
    private Boolean isProspect;
    @JsonProperty("LastModifiedDateTime")
    private String lastModifiedDateTime;
    @JsonProperty("Liability")
    private Liability liability;
    @JsonProperty("LiabilityRelease")
    private Boolean liabilityRelease;
    @JsonProperty("MembershipIcon")
    private Integer membershipIcon;
    @JsonProperty("MobilePhone")
    private String mobilePhone;
    @JsonProperty("Notes")
    private String notes;
    @JsonProperty("PhotoURL")
    private String photoURL;
    @JsonProperty("PostalCode")
    private String postCode;
    @JsonProperty("ProspectStage")
    private ProspectStage prospectStage;
    @JsonProperty("SalesReps")
    private ArrayList<SalesRep> saleReps;
    @JsonProperty("SendAccountEmails")
    private Boolean sendAccountEmails;// true,
    @JsonProperty("SendAccountTexts")
    private Boolean sendAccountTexts;// false,
    @JsonProperty("SendPromotionalEmails")
    private Boolean sendPromotionalEmails;// false,
    @JsonProperty("SendPromotionalTexts")
    private Boolean sendPromotionalTexts;// false,
    @JsonProperty("SendScheduleEmails")
    private Boolean sendScheduleEmails;// true,
    @JsonProperty("SendScheduleTexts")
    private Boolean sendScheduleTexts;// false
    @JsonProperty("SiteId")
    private Integer siteId;
    @JsonProperty("State")
    private String state;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("Test")
    private Boolean test;
    @JsonProperty("YellowAlert")
    private String yellowAlert;
    @JsonProperty("RedAlert")
    private String redAlert;

    public MboAddNewClientRequest() {
    }


    public Double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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

    public Boolean getAppointmentGenderPrefMale() {
        return appointmentGenderPrefMale;
    }

    public void setAppointmentGenderPrefMale(Boolean appointmentGenderPrefMale) {
        this.appointmentGenderPrefMale = appointmentGenderPrefMale;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ClientCreditCard getClientCreditCard() {
        return clientCreditCard;
    }

    public void setClientCreditCard(ClientCreditCard clientCreditCard) {
        this.clientCreditCard = clientCreditCard;
    }

    public ArrayList<AssignedClientIndex> getClientIndexes() {
        return clientIndexes;
    }

    public void setClientIndexes(ArrayList<AssignedClientIndex> clientIndexes) {
        this.clientIndexes = clientIndexes;
    }

    public ArrayList<ClientRelationship> getClientRelationships() {
        return clientRelationships;
    }

    public void setClientRelationships(ArrayList<ClientRelationship> clientRelationships) {
        this.clientRelationships = clientRelationships;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmergencyContactInfoName() {
        return emergencyContactInfoName;
    }

    public void setEmergencyContactInfoName(String emergencyContactInfoName) {
        this.emergencyContactInfoName = emergencyContactInfoName;
    }

    public String getEmergencyContactInfoRelationship() {
        return emergencyContactInfoRelationship;
    }

    public void setEmergencyContactInfoRelationship(String emergencyContactInfoRelationship) {
        this.emergencyContactInfoRelationship = emergencyContactInfoRelationship;
    }

    public String getEmergencyContactInfoPhone() {
        return emergencyContactInfoPhone;
    }

    public void setEmergencyContactInfoPhone(String emergencyContactInfoPhone) {
        this.emergencyContactInfoPhone = emergencyContactInfoPhone;
    }

    public String getEmergencyContactInfoEmail() {
        return emergencyContactInfoEmail;
    }

    public void setEmergencyContactInfoEmail(String emergencyContactInfoEmail) {
        this.emergencyContactInfoEmail = emergencyContactInfoEmail;
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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Location getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(Location homeLocation) {
        this.homeLocation = homeLocation;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public Boolean getCompany() {
        return isCompany;
    }

    public void setCompany(Boolean company) {
        isCompany = company;
    }

    public Boolean getProspect() {
        return isProspect;
    }

    public void setProspect(Boolean prospect) {
        isProspect = prospect;
    }

    public String getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    public void setLastModifiedDateTime(String lastModifiedDateTime) {
        this.lastModifiedDateTime = lastModifiedDateTime;
    }

    public Liability getLiability() {
        return liability;
    }

    public void setLiability(Liability liability) {
        this.liability = liability;
    }

    public Boolean getLiabilityRelease() {
        return liabilityRelease;
    }

    public void setLiabilityRelease(Boolean liabilityRelease) {
        this.liabilityRelease = liabilityRelease;
    }

    public Integer getMembershipIcon() {
        return membershipIcon;
    }

    public void setMembershipIcon(Integer membershipIcon) {
        this.membershipIcon = membershipIcon;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public ProspectStage getProspectStage() {
        return prospectStage;
    }

    public void setProspectStage(ProspectStage prospectStage) {
        this.prospectStage = prospectStage;
    }

    public ArrayList<SalesRep> getSaleReps() {
        return saleReps;
    }

    public void setSaleReps(ArrayList<SalesRep> saleReps) {
        this.saleReps = saleReps;
    }

    public Boolean getSendAccountEmails() {
        return sendAccountEmails;
    }

    public void setSendAccountEmails(Boolean sendAccountEmails) {
        this.sendAccountEmails = sendAccountEmails;
    }

    public Boolean getSendAccountTexts() {
        return sendAccountTexts;
    }

    public void setSendAccountTexts(Boolean sendAccountTexts) {
        this.sendAccountTexts = sendAccountTexts;
    }

    public Boolean getSendPromotionalTexts() {
        return sendPromotionalTexts;
    }

    public void setSendPromotionalTexts(Boolean sendPromotionalTexts) {
        this.sendPromotionalTexts = sendPromotionalTexts;
    }

    public Boolean getSendScheduleEmails() {
        return sendScheduleEmails;
    }

    public void setSendScheduleEmails(Boolean sendScheduleEmails) {
        this.sendScheduleEmails = sendScheduleEmails;
    }

    public Boolean getSendScheduleTexts() {
        return sendScheduleTexts;
    }

    public void setSendScheduleTexts(Boolean sendScheduleTexts) {
        this.sendScheduleTexts = sendScheduleTexts;
    }

    public Boolean getSendPromotionalEmails() {
        return sendPromotionalEmails;
    }

    public void setSendPromotionalEmails(Boolean sendPromotionalEmails) {
        this.sendPromotionalEmails = sendPromotionalEmails;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getTest() {
        return test;
    }

    public void setTest(Boolean test) {
        this.test = test;
    }

    public String getYellowAlert() {
        return yellowAlert;
    }

    public void setYellowAlert(String yellowAlert) {
        this.yellowAlert = yellowAlert;
    }

    public String getRedAlert() {
        return redAlert;
    }

    public void setRedAlert(String redAlert) {
        this.redAlert = redAlert;
    }

    @Override
    public String toString() {
        return "MboAddNewClientRequest{" +
                ", accountBalance=" + accountBalance +
                ", action='" + action + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", appointmentGenderPrefMale=" + appointmentGenderPrefMale +
                ", birthDate='" + birthDate + '\'' +
                ", city='" + city + '\'' +
                ", clientCreditCard=" + clientCreditCard +
                ", clientIndexes=" + clientIndexes +
                ", clientRelationships=" + clientRelationships +
                ", country='" + country + '\'' +
                ", createDate='" + createDate + '\'' +
                ", email='" + email + '\'' +
                ", emergencyContactInfoName='" + emergencyContactInfoName + '\'' +
                ", emergencyContactInfoRelationship='" + emergencyContactInfoRelationship + '\'' +
                ", emergencyContactInfoPhone='" + emergencyContactInfoPhone + '\'' +
                ", emergencyContactInfoEmail='" + emergencyContactInfoEmail + '\'' +
                ", firstAppointmentDate='" + firstAppointmentDate + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", homeLocation=" + homeLocation +
                ", homePhone='" + homePhone + '\'' +
                ", isCompany=" + isCompany +
                ", isProspect=" + isProspect +
                ", lastModifiedDateTime='" + lastModifiedDateTime + '\'' +
                ", liability=" + liability +
                ", liabilityRelease=" + liabilityRelease +
                ", membershipIcon=" + membershipIcon +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", notes='" + notes + '\'' +
                ", photoURL='" + photoURL + '\'' +
                ", postCode='" + postCode + '\'' +
                ", prospectStage=" + prospectStage +
                ", saleReps=" + saleReps +
                ", sendAccountEmail=" + sendAccountEmails +
                ", sendScheduleEmail=" + sendScheduleEmails +
                ", siteId=" + siteId +
                ", state='" + state + '\'' +
                ", status='" + status + '\'' +
                ", test=" + test +
                ", yellowAlert='" + yellowAlert + '\'' +
                ", redAlert='" + redAlert + '\'' +
                '}';
    }
}
