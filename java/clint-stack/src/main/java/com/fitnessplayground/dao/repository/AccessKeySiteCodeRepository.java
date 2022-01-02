package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.AccessKeySiteCode;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AccessKeySiteCodeRepository extends CrudRepository<AccessKeySiteCode, Long> {

    String FIND_BY_LOCATION_ID = "SELECT * FROM AccessKeySiteCode WHERE locationId = :locationId";

    @Query(value = FIND_BY_LOCATION_ID, nativeQuery = true)
    AccessKeySiteCode findByLocationId(@Param("locationId") Integer locationId);
}
