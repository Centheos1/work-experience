package com.fitnessplayground.dao.impl;

import com.fitnessplayground.dao.SubmissionErrorDao;
import com.fitnessplayground.dao.domain.SubmissionError;
import com.fitnessplayground.dao.domain.EnrolmentSubmissionError;
import com.fitnessplayground.dao.repository.SubmissionErrorDataRepository;
import com.fitnessplayground.dao.repository.SubmissionErrorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by micheal on 18/03/2017.
 */
@Repository
public class SubmissionErrorDaoImpl implements SubmissionErrorDao {

    @Autowired
    SubmissionErrorRepository submissionErrorRepository;

    @Autowired
    SubmissionErrorDataRepository submissionErrorDataRepository;

    @Override
    public SubmissionError saveSubmissionError(SubmissionError submissionError) {
        return submissionErrorRepository.save(submissionError);
    }

    @Override
    public EnrolmentSubmissionError saveSubmissionError(EnrolmentSubmissionError enrolmentSubmissionError) {
        return submissionErrorDataRepository.save(enrolmentSubmissionError);
    }

    @Override
    public Iterable<EnrolmentSubmissionError> getAllErrorData() {
        return submissionErrorDataRepository.findAll();
    }

    @Override
    public void deleteSubmissionErrorData(EnrolmentSubmissionError data) {
        submissionErrorDataRepository.delete(data);
    }

    @Override
    public EnrolmentSubmissionError getSubmissionErrorById(long id) {
        return submissionErrorDataRepository.findOne(id);
    }
}
