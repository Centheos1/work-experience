package com.fitnessplayground.dao.domain;

import com.fitnessplayground.util.FitnessPlaygroundUtil;
import com.fitnessplayground.util.Helpers;

import javax.persistence.*;

/**
 * Created by micheal on 18/03/2017.
 */
@Entity
public class SubmissionError {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    private String formId;
    private String submissionId;
    private String errorDetails;
    private String submissionDate;

    public SubmissionError(String formId, String submissionId, String errorDetails) {
        this.formId = formId;
        this.submissionId = submissionId;
        this.errorDetails = errorDetails;
        this.submissionDate = Helpers.getDateNow();
    }
}
