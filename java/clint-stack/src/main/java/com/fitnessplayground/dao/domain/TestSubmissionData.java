package com.fitnessplayground.dao.domain;

import javax.persistence.*;

@Entity
public class TestSubmissionData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String submission;

    public TestSubmissionData() {
    }

    public static TestSubmissionData from(String submission) {
        TestSubmissionData data = new TestSubmissionData();
        data.setSubmission(submission);

        return data;
    }

    public long getId() {
        return id;
    }

    public String getSubmission() {
        return submission;
    }

    public void setSubmission(String submission) {
        this.submission = submission;
    }

    @Override
    public String toString() {
        return "TestSubmissionData{" +
                "id=" + id +
                ", submission='" + submission + '\'' +
                '}';
    }
}
