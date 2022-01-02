package com.fitnessplayground.dao.domain.fpSourceDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fitnessplayground.dao.domain.CancellationData;
import com.fitnessplayground.dao.domain.EnrolmentData;
import com.fitnessplayground.dao.domain.FpCoachEnrolmentData;
import com.fitnessplayground.dao.domain.PreExData;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyDashboard {


    @JsonProperty("enrolments")
    private ArrayList<EnrolmentData> enrolments;

    @JsonProperty("cancellations")
    private ArrayList<CancellationData> cancellations;

    @JsonProperty("preExs")
    private ArrayList<PreExData> preExs;

    @JsonProperty("fpCoachEnrolments")
    private ArrayList<FpCoachEnrolmentData> fpCoachEnrolments;

    public CompanyDashboard() {
    }

    public CompanyDashboard(ArrayList<EnrolmentData> enrolments, ArrayList<CancellationData> cancellations, ArrayList<PreExData> preExs, ArrayList<FpCoachEnrolmentData> fpCoachEnrolments) {

        for (EnrolmentData e : enrolments) {
            e.setMemberCreditCard(null);
            e.setMemberBankDetail(null);
            e.setUID(null);
        }

        this.enrolments = enrolments;
        this.cancellations = cancellations;
        this.preExs = preExs;
        this.fpCoachEnrolments = fpCoachEnrolments;
    }

    public ArrayList<EnrolmentData> getEnrolments() {
        return enrolments;
    }

    public void setEnrolments(ArrayList<EnrolmentData> enrolments) {
        this.enrolments = enrolments;
    }

    public ArrayList<CancellationData> getCancellations() {
        return cancellations;
    }

    public void setCancellations(ArrayList<CancellationData> cancellations) {
        this.cancellations = cancellations;
    }

    public ArrayList<PreExData> getPreExs() {
        return preExs;
    }

    public void setPreExs(ArrayList<PreExData> preExs) {
        this.preExs = preExs;
    }

    public ArrayList<FpCoachEnrolmentData> getFpCoachEnrolments() {
        return fpCoachEnrolments;
    }

    public void setFpCoachEnrolments(ArrayList<FpCoachEnrolmentData> fpCoachEnrolments) {
        this.fpCoachEnrolments = fpCoachEnrolments;
    }

    @Override
    public String toString() {
        return "CompanyDashboard{" +
                "enrolments=" + enrolments +
                ", cancellations=" + cancellations +
                ", preExs=" + preExs +
                ", fpCoachEnrolments=" + fpCoachEnrolments +
                '}';
    }
}

