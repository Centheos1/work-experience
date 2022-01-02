package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.ops.FPOpsConfig;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by micheal on 11/03/2017.
 */
public interface FPOpsConfigRepository extends CrudRepository<FPOpsConfig, Long> {
    FPOpsConfig findById(long id);
    FPOpsConfig findByName(String name);
}

