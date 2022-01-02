package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Client {

    @JsonProperty("AppointmentGenderPreference")
    private String appointmentGenderPreference; //: "None",
    @JsonProperty("BirthDate")
    private String birthDate; // null,
    @JsonProperty("Country")
    private String country; // "AU",
    @JsonProperty("CreationDate")
    private String creationDate; //"2015-05-17T22:53:43.02",
    @JsonProperty("ClientCreditCard")
    private ClientCreditCard clientCreditCard; // null,
//    @JsonProperty("")
//    "ClientIndexes": [],
//    @JsonProperty("")
//    "ClientRelationships": [],
    @JsonProperty("FirstAppointmentDate")
    private String firstAppointmentDate; //"2015-05-18T00:00:00",
    @JsonProperty("FirstName")
    private String firstName; // " ",
    @JsonProperty("Id")
    private String accessKeyNumber; //"100004684",
    @JsonProperty("IsCompany")
    private boolean isCompany; // false,
    @JsonProperty("IsProspect")
    private boolean isProspect;// true,
    @JsonProperty("LastName")
    private String lastName;// " ",
    @JsonProperty("Liability")
    private Liability liability;
    @JsonProperty("LiabilityRelease")
    private boolean liabilityRelease;// false,
//    @JsonProperty("")
//    "MembershipIcon": 0,
//    @JsonProperty("")
//    "MobileProvider": 0,
    @JsonProperty("Notes")
    private String notes;// " ",
    @JsonProperty("State")
    private String state;// null,
    @JsonProperty("UniqueId")
    private String  uniqueId;// 100004684,
    @JsonProperty("LastModifiedDateTime")
    private String lastModifiedDateTime;// "2018-03-06T17:32:33.1Z",
//    @JsonProperty("")
//    "RedAlert": null,
//    @JsonProperty("")
//    "YellowAlert": null,
    @JsonProperty("MiddleName")
    private String middleName;// null,
//    @JsonProperty("")
//    "ProspectStage": null,
    @JsonProperty("Email")
    private String email;// null,
    @JsonProperty("MobilePhone")
    private String phone;// null,
//    @JsonProperty("")
//    "HomePhone": null,
//    @JsonProperty("")
//    "WorkPhone": null,
    @JsonProperty("AccountBalance")
    private Float accountBalance;// 0,
    @JsonProperty("AddressLine1")
    private String addressLine1;// null,
    @JsonProperty("AddressLine2")
    private String addressLine2;// null,
    @JsonProperty("City")
    private String city;// null,
    @JsonProperty("PostalCode")
    private String postalCode;// null,
//    @JsonProperty("")
//    "WorkExtension": null,
//    @JsonProperty("")
//    "ReferredBy": null,
//    @JsonProperty("")
//    "PhotoUrl": null,
    @JsonProperty("EmergencyContactInfoName")
    private String emergencyContactName;// null,
    @JsonProperty("EmergencyContactInfoEmail")
    private String emergencyContactEmail;// null,
    @JsonProperty("EmergencyContactInfoPhone")
    private String emergencyContactPhone;// null,
//    @JsonProperty("")
//    "EmergencyContactInfoRelationship": null,
    @JsonProperty("Gender")
    private String gender;// "None",
//    @JsonProperty("")
//    "LastFormulaNotes": null,
    @JsonProperty("Active")
    private boolean active;// true,
    @JsonProperty("SalesReps")
    private ArrayList<SalesRep> salesReps; // [],
    @JsonProperty("Status")
    private String status;// "Non-Member",
    @JsonProperty("Action")
    private String action; // "None",
    @JsonProperty("SendAccountEmails")
    private boolean sendAccountEmails;// true,
    @JsonProperty("SendAccountTexts")
    private boolean sendAccountTexts;// false,
    @JsonProperty("SendPromotionalEmails")
    private boolean sendPromotionalEmails;// false,
    @JsonProperty("SendPromotionalTexts")
    private boolean sendPromotionalTexts;// false,
    @JsonProperty("SendScheduleEmails")
    private boolean sendScheduleEmails;// true,
    @JsonProperty("SendScheduleTexts")
    private boolean sendScheduleTexts;// false

    public Client() {
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

    public ClientCreditCard getClientCreditCard() {
        return clientCreditCard;
    }

    public void setClientCreditCard(ClientCreditCard clientCreditCard) {
        this.clientCreditCard = clientCreditCard;
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

    public Liability getLiability() {
        return liability;
    }

    public void setLiability(Liability liability) {
        this.liability = liability;
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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

    public ArrayList<SalesRep> getSalesReps() {
        return salesReps;
    }

    public void setSalesReps(ArrayList<SalesRep> salesReps) {
        this.salesReps = salesReps;
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

    public Float getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Float accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "Client{" +
                "appointmentGenderPreference='" + appointmentGenderPreference + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", country='" + country + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", clientCreditCard=" + clientCreditCard +
                ", firstAppointmentDate='" + firstAppointmentDate + '\'' +
                ", firstName='" + firstName + '\'' +
                ", accessKeyNumber='" + accessKeyNumber + '\'' +
                ", isCompany=" + isCompany +
                ", isProspect=" + isProspect +
                ", lastName='" + lastName + '\'' +
                ", liability=" + liability +
                ", liabilityRelease=" + liabilityRelease +
                ", notes='" + notes + '\'' +
                ", state='" + state + '\'' +
                ", uniqueId='" + uniqueId + '\'' +
                ", lastModifiedDateTime='" + lastModifiedDateTime + '\'' +
                ", middleName='" + middleName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", accountBalance=" + accountBalance +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", emergencyContactName='" + emergencyContactName + '\'' +
                ", emergencyContactEmail='" + emergencyContactEmail + '\'' +
                ", emergencyContactPhone='" + emergencyContactPhone + '\'' +
                ", gender='" + gender + '\'' +
                ", active=" + active +
                ", salesReps=" + salesReps +
                ", status='" + status + '\'' +
                ", action='" + action + '\'' +
                ", sendAccountEmails=" + sendAccountEmails +
                ", sendAccountTexts=" + sendAccountTexts +
                ", sendPromotionalEmails=" + sendPromotionalEmails +
                ", sendPromotionalTexts=" + sendPromotionalTexts +
                ", sendScheduleEmails=" + sendScheduleEmails +
                ", sendScheduleTexts=" + sendScheduleTexts +
                '}';
    }
}
