package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.EnrolmentSubmissionError;
import org.springframework.data.repository.CrudRepository;

public interface SubmissionErrorDataRepository extends CrudRepository<EnrolmentSubmissionError, Long> {
}
