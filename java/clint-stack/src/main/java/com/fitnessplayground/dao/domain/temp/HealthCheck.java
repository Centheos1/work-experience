package com.fitnessplayground.dao.domain.temp;

import java.util.Arrays;

public class HealthCheck {

    private String[] injuries; //[],
    private String[] medical; //[],
    private String medicalClearance; //,
    private String[] trainingAvailability; //[fri,tues]
    private String[] timeAvailability; //[afterWork]

    public HealthCheck() {
    }

    public HealthCheck(String[] injuries, String[] medical, String medicalClearance, String[] trainingAvailability, String[] timeAvailability) {
        this.injuries = injuries;
        this.medical = medical;
        this.medicalClearance = medicalClearance;
        this.trainingAvailability = trainingAvailability;
        this.timeAvailability = timeAvailability;
    }

    public String[] getInjuries() {
        return injuries;
    }

    public void setInjuries(String[] injuries) {
        this.injuries = injuries;
    }

    public String[] getMedical() {
        return medical;
    }

    public void setMedical(String[] medical) {
        this.medical = medical;
    }

    public String getMedicalClearance() {
        return medicalClearance;
    }

    public void setMedicalClearance(String medicalClearance) {
        this.medicalClearance = medicalClearance;
    }

    public String[] getTrainingAvailability() {
        return trainingAvailability;
    }

    public void setTrainingAvailability(String[] trainingAvailability) {
        this.trainingAvailability = trainingAvailability;
    }

    public String[] getTimeAvailability() {
        return timeAvailability;
    }

    public void setTimeAvailability(String[] timeAvailability) {
        this.timeAvailability = timeAvailability;
    }

    @Override
    public String toString() {
        return "HealthCheck{" +
                "injuries=" + Arrays.toString(injuries) +
                ", medical=" + Arrays.toString(medical) +
                ", medicalClearance='" + medicalClearance + '\'' +
                ", trainingAvailability=" + Arrays.toString(trainingAvailability) +
                ", timeAvailability=" + Arrays.toString(timeAvailability) +
                '}';
    }
}
