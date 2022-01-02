package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.Test;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TestRepository extends CrudRepository<Test, Long> {

    String FIND_SAVED = "SELECT * FROM Test WHERE status = 'SAVED' LIMIT 30";

    String FIND_PENDING = "SELECT * FROM Test WHERE status = 'PROCESSING' LIMIT 30";

    @Query(value = FIND_SAVED, nativeQuery = true)
    List<Test> getSaved();

    @Query(value = FIND_PENDING, nativeQuery = true)
    List<Test> getPending();


}
