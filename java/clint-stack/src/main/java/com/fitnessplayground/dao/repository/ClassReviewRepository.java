package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.ClassReviewData;
import org.springframework.data.repository.CrudRepository;

public interface ClassReviewRepository extends CrudRepository<ClassReviewData, Long> {
}
