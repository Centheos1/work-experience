package com.fitnessplayground.dao;

import com.fitnessplayground.dao.domain.SubmissionError;
import com.fitnessplayground.dao.domain.EnrolmentSubmissionError;

/**
 * Created by micheal on 18/03/2017.
 */
public interface SubmissionErrorDao {
    SubmissionError saveSubmissionError(SubmissionError submissionError);
    EnrolmentSubmissionError saveSubmissionError(EnrolmentSubmissionError enrolmentSubmissionError);

    Iterable<EnrolmentSubmissionError> getAllErrorData();
    void deleteSubmissionErrorData(EnrolmentSubmissionError data);
    EnrolmentSubmissionError getSubmissionErrorById(long id);
}
