package com.fitnessplayground.dao.domain;

import javax.persistence.*;

@Entity
public class TermsAndConditions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String termsAndConditions;

    public TermsAndConditions() {
    }

    public long getId() {
        return id;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    @Override
    public String toString() {
        return "TermsAndConditions{" +
                "id=" + id +
                ", termsAndConditions='" + termsAndConditions + '\'' +
                '}';
    }
}
