package com.fitnessplayground.dao.domain.temp;

import java.util.Arrays;

public class LegalDetails {
    
    private String[] agreement; //[true]
    private String[] contractCommitment; //[],
    private String[] pt6SessionCommitment;
    private String under18Signature; //,
    private String signature; //
    private String accessKeyNumber; //54323
    private String notes; //

    public LegalDetails() {
    }

    public LegalDetails(String[] agreement, String[] contractCommitment, String[] pt6SessionCommitment, String under18Signature, String signature, String accessKeyNumber, String notes) {
        this.agreement = agreement;
        this.contractCommitment = contractCommitment;
        this.pt6SessionCommitment = pt6SessionCommitment;
        this.under18Signature = under18Signature;
        this.signature = signature;
        this.accessKeyNumber = accessKeyNumber;
        this.notes = notes;
    }

    public String[] getAgreement() {
        return agreement;
    }

    public void setAgreement(String[] agreement) {
        this.agreement = agreement;
    }

    public String[] getContractCommitment() {
        return contractCommitment;
    }

    public void setContractCommitment(String[] contractCommitment) {
        this.contractCommitment = contractCommitment;
    }

    public String getUnder18Signature() {
        return under18Signature;
    }

    public void setUnder18Signature(String under18Signature) {
        this.under18Signature = under18Signature;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAccessKeyNumber() {
        return accessKeyNumber;
    }

    public void setAccessKeyNumber(String accessKeyNumber) {
        this.accessKeyNumber = accessKeyNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String[] getPt6SessionCommitment() {
        return pt6SessionCommitment;
    }

    public void setPt6SessionCommitment(String[] pt6SessionCommitment) {
        this.pt6SessionCommitment = pt6SessionCommitment;
    }

    @Override
    public String toString() {
        return "LegalDetails{" +
                "agreement=" + Arrays.toString(agreement) +
                ", contractCommitment=" + Arrays.toString(contractCommitment) +
                ", pt6SessionCommitment=" + Arrays.toString(pt6SessionCommitment) +
                ", under18Signature='" + under18Signature + '\'' +
                ", signature='" + signature + '\'' +
                ", accessKeyNumber='" + accessKeyNumber + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
