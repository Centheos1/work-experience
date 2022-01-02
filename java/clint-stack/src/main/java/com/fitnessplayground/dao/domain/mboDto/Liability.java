package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Liability {

    /*
    * When LiabilityRelease true:
    *   setReleased(true);
    *   setAgreementDate(<dateNow>);
    *   setReleasedBy(<0> = Business Owner <null> = client <StaffId>);
    *
    *when LiabilityRelease false;
    *   setReleased(false);
    *   setAgreementDate(null);
    *   setReleasedBy(null);
    * */

    @JsonProperty("AgreementDate")
    private String agreementDate; //: null,
    @JsonProperty("IsReleased")
    private boolean isReleased; //": false,
    @JsonProperty("ReleasedBy")
    private String releasedBy; //": null

    public Liability() {
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

    @Override
    public String toString() {
        return "Liability{" +
                "agreementDate='" + agreementDate + '\'' +
                ", isReleased=" + isReleased +
                ", releasedBy='" + releasedBy + '\'' +
                '}';
    }
}
