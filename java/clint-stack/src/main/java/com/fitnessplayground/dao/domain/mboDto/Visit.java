package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Visit {

    @JsonProperty("AppointmentId")
    private String appointmentId;// 0,
    @JsonProperty("AppointmentGenderPreference")
    private String appointmentGenderPreference;//"None",
    @JsonProperty("AppointmentStatus")
    private String appointmentStatus;// "None",
    @JsonProperty("ClassId")
    private String classId;// 0,
    @JsonProperty("ClientId")
    private String clientId;//"56244",
    @JsonProperty("StartDateTime")
    private String startDateTime;//"2019-05-13T10:10:00",
    @JsonProperty("EndDateTime")
    private String endDateTime; //"2019-05-13T10:10:00",
    @JsonProperty("Id")
    private Long visitId;// 2588196,
    @JsonProperty("LastModifiedDateTime")
    private String lastModifiedDateTime;// "0001-01-01T00:00:00Z",
    @JsonProperty("LateCancelled")
    private Boolean lateCancelled;//false,
    @JsonProperty("LocationId")
    private Integer locationId;// 1,
    @JsonProperty("MakeUp")
    private Boolean makeUp;// false,
    @JsonProperty("Name")
    private String name; //"Arrival",
    @JsonProperty("ServiceId")
    private Long serviceId;// null,
    @JsonProperty("ServiceName")
    private String serviceName;// null,
    @JsonProperty("SignedIn")
    private Boolean signedIn;// true,
    @JsonProperty("StaffId")
    private Long staffId;// 1,
    @JsonProperty("WebSignup")
    private Boolean webSignup;// false,
    @JsonProperty("Action")
    private String action;// "None"
    @JsonProperty("Missed")
    private Boolean missed;// null,
    @JsonProperty("VisitType")
    private Integer visitType;// null,
    @JsonProperty("TypeGroup")
    private Integer typeGrounp;// null,
    @JsonProperty("TypeTaken")
    private String typeTaken;// null,

    public Visit() {
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getAppointmentGenderPreference() {
        return appointmentGenderPreference;
    }

    public void setAppointmentGenderPreference(String appointmentGenderPreference) {
        this.appointmentGenderPreference = appointmentGenderPreference;
    }

    public String getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public long getVisitId() {
        return visitId;
    }

    public void setVisitId(long visitId) {
        this.visitId = visitId;
    }

    public String getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    public void setLastModifiedDateTime(String lastModifiedDateTime) {
        this.lastModifiedDateTime = lastModifiedDateTime;
    }

    public boolean isLateCancelled() {
        return lateCancelled;
    }

    public void setLateCancelled(boolean lateCancelled) {
        this.lateCancelled = lateCancelled;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public boolean isMakeUp() {
        return makeUp;
    }

    public void setMakeUp(boolean makeUp) {
        this.makeUp = makeUp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public boolean isSignedIn() {
        return signedIn;
    }

    public void setSignedIn(boolean signedIn) {
        this.signedIn = signedIn;
    }

    public long getStaffId() {
        return staffId;
    }

    public void setStaffId(long staffId) {
        this.staffId = staffId;
    }

    public boolean isWebSignup() {
        return webSignup;
    }

    public void setWebSignup(boolean webSignup) {
        this.webSignup = webSignup;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setVisitId(Long visitId) {
        this.visitId = visitId;
    }

    public Boolean getLateCancelled() {
        return lateCancelled;
    }

    public void setLateCancelled(Boolean lateCancelled) {
        this.lateCancelled = lateCancelled;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Boolean getMakeUp() {
        return makeUp;
    }

    public void setMakeUp(Boolean makeUp) {
        this.makeUp = makeUp;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Boolean getSignedIn() {
        return signedIn;
    }

    public void setSignedIn(Boolean signedIn) {
        this.signedIn = signedIn;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Boolean getWebSignup() {
        return webSignup;
    }

    public void setWebSignup(Boolean webSignup) {
        this.webSignup = webSignup;
    }

    public Boolean getMissed() {
        return missed;
    }

    public void setMissed(Boolean missed) {
        this.missed = missed;
    }

    public Integer getVisitType() {
        return visitType;
    }

    public void setVisitType(Integer visitType) {
        this.visitType = visitType;
    }

    public Integer getTypeGrounp() {
        return typeGrounp;
    }

    public void setTypeGrounp(Integer typeGrounp) {
        this.typeGrounp = typeGrounp;
    }

    public String getTypeTaken() {
        return typeTaken;
    }

    public void setTypeTaken(String typeTaken) {
        this.typeTaken = typeTaken;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "appointmentId='" + appointmentId + '\'' +
                ", appointmentGenderPreference='" + appointmentGenderPreference + '\'' +
                ", appointmentStatus='" + appointmentStatus + '\'' +
                ", classId='" + classId + '\'' +
                ", clientId='" + clientId + '\'' +
                ", startDateTime='" + startDateTime + '\'' +
                ", endDateTime='" + endDateTime + '\'' +
                ", visitId=" + visitId +
                ", lastModifiedDateTime='" + lastModifiedDateTime + '\'' +
                ", lateCancelled=" + lateCancelled +
                ", locationId=" + locationId +
                ", makeUp=" + makeUp +
                ", name='" + name + '\'' +
                ", serviceId=" + serviceId +
                ", serviceName=" + serviceName +
                ", signedIn=" + signedIn +
                ", staffId=" + staffId +
                ", webSignup=" + webSignup +
                ", action='" + action + '\'' +
                ", missed=" + missed +
                ", visitType=" + visitType +
                ", typeGrounp=" + typeGrounp +
                ", typeTaken=" + typeTaken +
                '}';
    }
}
