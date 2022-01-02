package com.fitnessplayground.dao.domain;

import javax.persistence.*;

@Entity
public class MboClientProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

//    Credentials
    @Column(unique=true)
    private String username; // this is the email - and it's the unique id within mindbody
    private String clientId; //  mindbody id (access key number)
    private String password;
    private int homeLocation;

//    Personal Details
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private String birthDate; // dateTime

//    Contact Details
    private String mobilePhone;
    private String workPhone;
    private String homePhone;
    private String email;

//    Legal
    private boolean isLiabilityRelease;
    private boolean isEmailOptIn;
    private boolean promotionalEmailOptIn;

//    Emergency Contact Info
    private String emergencyContactInfoEmail;
    private String emergencyContactInfoName;
    private String emergencyContactInfoPhone;
    private String emergencyContactInfoRelationship;

//    Address
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postcode;
    private String country;

//    Payment Details
    private String cardNumber;
    private String expMonth;
    private String expYear;

//    Member Details

    private String referredBy;
    private boolean isCompany;
    private boolean isProspect;
    private boolean sendEmail; // sends welcome email from MBO

//    Meta Data
    private String status;
    private String createDate;
    private String gymName; // This needs to be converted to an int and set in the home location. Also needed to switcht the siteId for the MBO API Call


//    ????
    private String firstAppointmentDate; //dateTime
    private String yellowAlter;
    private String redAlert;
//    private ArrayList<ClientIndex> clientIndixes;
//    private ArrayList<ClientRelationship> clientRelationships;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(int homeLocation) {
        this.homeLocation = homeLocation;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLiabilityRelease() {
        return isLiabilityRelease;
    }

    public void setLiabilityRelease(boolean liabilityRelease) {
        isLiabilityRelease = liabilityRelease;
    }

    public boolean isEmailOptIn() {
        return isEmailOptIn;
    }

    public void setEmailOptIn(boolean emailOptIn) {
        isEmailOptIn = emailOptIn;
    }

    public boolean isPromotionalEmailOptIn() {
        return promotionalEmailOptIn;
    }

    public void setPromotionalEmailOptIn(boolean promotionalEmailOptIn) {
        this.promotionalEmailOptIn = promotionalEmailOptIn;
    }

    public String getEmergencyContactInfoEmail() {
        return emergencyContactInfoEmail;
    }

    public void setEmergencyContactInfoEmail(String emergencyContactInfoEmail) {
        this.emergencyContactInfoEmail = emergencyContactInfoEmail;
    }

    public String getEmergencyContactInfoName() {
        return emergencyContactInfoName;
    }

    public void setEmergencyContactInfoName(String emergencyContactInfoName) {
        this.emergencyContactInfoName = emergencyContactInfoName;
    }

    public String getEmergencyContactInfoPhone() {
        return emergencyContactInfoPhone;
    }

    public void setEmergencyContactInfoPhone(String emergencyContactInfoPhone) {
        this.emergencyContactInfoPhone = emergencyContactInfoPhone;
    }

    public String getEmergencyContactInfoRelationship() {
        return emergencyContactInfoRelationship;
    }

    public void setEmergencyContactInfoRelationship(String emergencyContactInfoRelationship) {
        this.emergencyContactInfoRelationship = emergencyContactInfoRelationship;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
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

    public String getReferredBy() {
        return referredBy;
    }

    public void setReferredBy(String referredBy) {
        this.referredBy = referredBy;
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

    public boolean isSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getFirstAppointmentDate() {
        return firstAppointmentDate;
    }

    public void setFirstAppointmentDate(String firstAppointmentDate) {
        this.firstAppointmentDate = firstAppointmentDate;
    }

    public String getYellowAlter() {
        return yellowAlter;
    }

    public void setYellowAlter(String yellowAlter) {
        this.yellowAlter = yellowAlter;
    }

    public String getRedAlert() {
        return redAlert;
    }

    public void setRedAlert(String redAlert) {
        this.redAlert = redAlert;
    }

//    public ArrayList<ClientIndex> getClientIndixes() {
//        return clientIndixes;
//    }
//
//    public void setClientIndixes(ArrayList<ClientIndex> clientIndixes) {
//        this.clientIndixes = clientIndixes;
//    }
//
//    public ArrayList<ClientRelationship> getClientRelationships() {
//        return clientRelationships;
//    }
//
//    public void setClientRelationships(ArrayList<ClientRelationship> clientRelationships) {
//        this.clientRelationships = clientRelationships;
//    }

    public String getGymName() {
        return gymName;
    }

    public void setGymName(String gymName) {
        this.gymName = gymName;
    }

    @Override
    public String toString() {
        return "MboClientProfile{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", clientId='" + clientId + '\'' +
                ", password='" + password + '\'' +
                ", homeLocation=" + homeLocation +
                ", firstName='" + firstName + '\'' +
//                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
//                ", gender='" + gender + '\'' +
//                ", birthDate='" + birthDate + '\'' +
//                ", mobilePhone='" + mobilePhone + '\'' +
//                ", workPhone='" + workPhone + '\'' +
//                ", homePhone='" + homePhone + '\'' +
                ", email='" + email + '\'' +
//                ", isLiabilityRelease=" + isLiabilityRelease +
//                ", isEmailOptIn=" + isEmailOptIn +
//                ", promotionalEmailOptIn=" + promotionalEmailOptIn +
//                ", emergencyContactInfoEmail='" + emergencyContactInfoEmail + '\'' +
//                ", emergencyContactInfoName='" + emergencyContactInfoName + '\'' +
//                ", emergencyContactInfoPhone='" + emergencyContactInfoPhone + '\'' +
//                ", emergencyContactInfoRelationship='" + emergencyContactInfoRelationship + '\'' +
//                ", addressLine1='" + addressLine1 + '\'' +
//                ", addressLine2='" + addressLine2 + '\'' +
//                ", city='" + city + '\'' +
//                ", state='" + state + '\'' +
//                ", postcode='" + postcode + '\'' +
//                ", country='" + country + '\'' +
//                ", cardNumber='" + cardNumber + '\'' +
//                ", expMonth='" + expMonth + '\'' +
//                ", expYear='" + expYear + '\'' +
//                ", referredBy='" + referredBy + '\'' +
//                ", isCompany=" + isCompany +
//                ", isProspect=" + isProspect +
//                ", sendEmail=" + sendEmail +
                ", status='" + status + '\'' +
                ", createDate='" + createDate + '\'' +
                ", gymName='" + gymName + '\'' +
//                ", firstAppointmentDate='" + firstAppointmentDate + '\'' +
//                ", yellowAlter='" + yellowAlter + '\'' +
//                ", redAlert='" + redAlert + '\'' +
//                ", clientIndixes=" + clientIndixes +
//                ", clientRelationships=" + clientRelationships +
                '}';
    }
}
