package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SalesRep {

    @JsonProperty("FirstName")
    private String firstName;
    @JsonProperty("Id")
    private long mboId;
    @JsonProperty("LastName")
    private String lastName;
    @JsonProperty("SalesRepNumber")
    private int salesRepNumber;

    public SalesRep() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public long getMboId() {
        return mboId;
    }

    public void setMboId(long mboId) {
        this.mboId = mboId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getSalesRepNumber() {
        return salesRepNumber;
    }

    public void setSalesRepNumber(int salesRepNumber) {
        this.salesRepNumber = salesRepNumber;
    }

    @Override
    public String toString() {
        return "SalesRep{" +
                "firstName='" + firstName + '\'' +
                ", mboId=" + mboId +
                ", lastName='" + lastName + '\'' +
                ", salesRepNumber=" + salesRepNumber +
                '}';
    }
}
