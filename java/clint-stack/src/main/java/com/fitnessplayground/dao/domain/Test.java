package com.fitnessplayground.dao.domain;

import com.fitnessplayground.dao.domain.temp.TestSubmission;

import javax.persistence.*;

@Entity
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String createDate;
    private String uniqueId;
    private String firstName;
    private String lastName;
    private String card;
    private String cardexp;
    private String cvv;
    private String status;

    public static Test from(TestSubmission testSubmission) {
        Test test = new Test();
        test.setCreateDate(testSubmission.getCreateDate().toString());
        test.setUniqueId(testSubmission.getUniqueId());
        test.setFirstName(testSubmission.getName().getFirstName());
        test.setLastName(testSubmission.getName().getLastName());
        test.setCard(testSubmission.getCreditCard().getCard());
        test.setCardexp(testSubmission.getCreditCard().getCardexp());
        test.setCvv(testSubmission.getCreditCard().getCvv());

        return test;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

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

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getCardexp() {
        return cardexp;
    }

    public void setCardexp(String cardexp) {
        this.cardexp = cardexp;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}
