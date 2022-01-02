package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.Gym;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface GymRepository extends CrudRepository<Gym, Long> {

    String FIND_BY_LOCATION_ID = "SELECT * FROM Gym WHERE locationId = :locationId LIMIT 1";

    @Query(value = FIND_BY_LOCATION_ID, nativeQuery = true)
    Gym getGymByLocationId(@Param("locationId") Integer locationId);

}
