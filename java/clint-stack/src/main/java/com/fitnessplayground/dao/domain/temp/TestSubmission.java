package com.fitnessplayground.dao.domain.temp;

import java.time.LocalDateTime;

public class TestSubmission {

    private LocalDateTime createDate;
    private String uniqueId;
    private SubmissionName name;
    private CreditCard creditCard;

    public TestSubmission() {
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public SubmissionName getName() {
        return name;
    }

    public void setName(SubmissionName name) {
        this.name = name;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    @Override
    public String toString() {
        return "TestSubmissionData{" +
                "createDate=" + createDate +
                ", uniqueId='" + uniqueId + '\'' +
                ", name=" + name +
                ", creditCard=" + creditCard +
                '}';
    }
}
