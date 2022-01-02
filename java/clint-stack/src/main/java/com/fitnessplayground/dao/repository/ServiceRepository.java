package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.MboService;
import org.springframework.data.repository.CrudRepository;

public interface ServiceRepository extends CrudRepository<MboService, Long> {
}
