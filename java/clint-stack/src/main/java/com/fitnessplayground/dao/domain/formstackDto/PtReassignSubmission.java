package com.fitnessplayground.dao.domain.formstackDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fitnessplayground.dao.domain.temp.SubmissionArray;
import com.fitnessplayground.dao.domain.temp.SubmissionName;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PtReassignSubmission {

    @JsonProperty("103186083")
    private String enrolmentDataId;
    @JsonProperty("108394439")
    private String ptTrackerId;
    @JsonProperty("103186082")
    private String fpCoachEnrolmentId;
    @JsonProperty("102198416")
    private String acid;
    @JsonProperty("102198418")
    private String locationId;
    @JsonProperty("102198419")
    private String gymName;
    @JsonProperty("102198420")
    private SubmissionName name;
    @JsonProperty("102198421")
    private String phone;
    @JsonProperty("102198422")
    private String email;
    @JsonProperty("102198423")
    private String coachingPack;
    @JsonProperty("102198425")
    private String dayAvailability;
    @JsonProperty("102198426")
    private String timeAvailability;
    @JsonProperty("102198428")
    private String originallyAssignedCoach;
    @JsonProperty("102198429")
    private SubmissionArray[] coachSurryHills;
    @JsonProperty("102198430")
    private SubmissionArray[] coachMarrickville;
    @JsonProperty("102198431")
    private SubmissionArray[] coachNewtown;
    @JsonProperty("102198432")
    private SubmissionArray[] coachBunker;
    @JsonProperty("102198433")
    private String reasonForReassignment;
    @JsonProperty("102198434")
    private String otherReasonForReassignment;
    @JsonProperty("FormID")
    private String fs_formId;
    @JsonProperty("UniqueID")
    private String fs_uniqueId;
    @JsonProperty("104612128")
    private String notes;

    public PtReassignSubmission() {
    }

    public String getEnrolmentDataId() {
        return enrolmentDataId;
    }

    public void setEnrolmentDataId(String enrolmentDataId) {
        this.enrolmentDataId = enrolmentDataId;
    }

    public String getPtTrackerId() {
        return ptTrackerId;
    }

    public void setPtTrackerId(String ptTrackerId) {
        this.ptTrackerId = ptTrackerId;
    }

    public String getFpCoachEnrolmentId() {
        return fpCoachEnrolmentId;
    }

    public void setFpCoachEnrolmentId(String fpCoachEnrolmentId) {
        this.fpCoachEnrolmentId = fpCoachEnrolmentId;
    }

    public String getAcid() {
        return acid;
    }

    public void setAcid(String acid) {
        this.acid = acid;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getGymName() {
        return gymName;
    }

    public void setGymName(String gymName) {
        this.gymName = gymName;
    }

    public SubmissionName getName() {
        return name;
    }

    public void setName(SubmissionName name) {
        this.name = name;
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

    public String getCoachingPack() {
        return coachingPack;
    }

    public void setCoachingPack(String coachingPack) {
        this.coachingPack = coachingPack;
    }

    public String getDayAvailability() {
        return dayAvailability;
    }

    public void setDayAvailability(String dayAvailability) {
        this.dayAvailability = dayAvailability;
    }

    public String getTimeAvailability() {
        return timeAvailability;
    }

    public void setTimeAvailability(String timeAvailability) {
        this.timeAvailability = timeAvailability;
    }

    public String getOriginallyAssignedCoach() {
        return originallyAssignedCoach;
    }

    public void setOriginallyAssignedCoach(String originallyAssignedCoach) {
        this.originallyAssignedCoach = originallyAssignedCoach;
    }

    public SubmissionArray[] getCoachSurryHills() {
        return coachSurryHills;
    }

    public void setCoachSurryHills(SubmissionArray[] coachSurryHills) {
        this.coachSurryHills = coachSurryHills;
    }

    public SubmissionArray[] getCoachMarrickville() {
        return coachMarrickville;
    }

    public void setCoachMarrickville(SubmissionArray[] coachMarrickville) {
        this.coachMarrickville = coachMarrickville;
    }

    public SubmissionArray[] getCoachNewtown() {
        return coachNewtown;
    }

    public void setCoachNewtown(SubmissionArray[] coachNewtown) {
        this.coachNewtown = coachNewtown;
    }

    public SubmissionArray[] getCoachBunker() {
        return coachBunker;
    }

    public void setCoachBunker(SubmissionArray[] coachBunker) {
        this.coachBunker = coachBunker;
    }

    public String getReasonForReassignment() {
        return reasonForReassignment;
    }

    public void setReasonForReassignment(String reasonForReassignment) {
        this.reasonForReassignment = reasonForReassignment;
    }

    public String getOtherReasonForReassignment() {
        return otherReasonForReassignment;
    }

    public void setOtherReasonForReassignment(String otherReasonForReassignment) {
        this.otherReasonForReassignment = otherReasonForReassignment;
    }

    public String getFs_formId() {
        return fs_formId;
    }

    public void setFs_formId(String fs_formId) {
        this.fs_formId = fs_formId;
    }

    public String getFs_uniqueId() {
        return fs_uniqueId;
    }

    public void setFs_uniqueId(String fs_uniqueId) {
        this.fs_uniqueId = fs_uniqueId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "PtReassignSubmission{" +
                "enrolmentDataId='" + enrolmentDataId + '\'' +
                ", ptTrackerId='" + ptTrackerId + '\'' +
                ", fpCoachEnrolmentId='" + fpCoachEnrolmentId + '\'' +
                ", acid='" + acid + '\'' +
                ", locationId='" + locationId + '\'' +
                ", gymName='" + gymName + '\'' +
                ", name=" + name +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", coachingPack='" + coachingPack + '\'' +
                ", dayAvailability='" + dayAvailability + '\'' +
                ", timeAvailability='" + timeAvailability + '\'' +
                ", originallyAssignedCoach='" + originallyAssignedCoach + '\'' +
                ", coachSurryHills=" + Arrays.toString(coachSurryHills) +
                ", coachMarrickville=" + Arrays.toString(coachMarrickville) +
                ", coachNewtown=" + Arrays.toString(coachNewtown) +
                ", coachBunker=" + Arrays.toString(coachBunker) +
                ", reasonForReassignment='" + reasonForReassignment + '\'' +
                ", otherReasonForReassignment='" + otherReasonForReassignment + '\'' +
                ", fs_formId='" + fs_formId + '\'' +
                ", fs_uniqueId='" + fs_uniqueId + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
