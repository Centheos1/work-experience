package com.fitnessplayground.dao.domain.temp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

/**
 * Created by micheal on 4/03/2017.
 */
public class SubmissionName {

//    @SerializedName("first") private String firstName;
//    @SerializedName("last") private String lastName;

    @JsonProperty("first") private String firstName;
    @JsonProperty("last") private String lastName;

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

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "SubmissionName{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
