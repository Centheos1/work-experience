package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.TestSubmissionData;
import org.springframework.data.repository.CrudRepository;

public interface TestDataRepository extends CrudRepository<TestSubmissionData, Long> {
}
