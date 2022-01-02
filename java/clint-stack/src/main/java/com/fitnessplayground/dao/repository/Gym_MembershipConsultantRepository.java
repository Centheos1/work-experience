package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.Gym_MembershipConsultant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Gym_MembershipConsultantRepository extends CrudRepository<Gym_MembershipConsultant, Long> {

    String FIND_BY_LOCATION_ID = "SELECT * FROM Gym_MembershipConsultant WHERE locationId = :locationId";

    @Query(value = FIND_BY_LOCATION_ID, nativeQuery = true)
    List<Gym_MembershipConsultant> findByLocationId(@Param("locationId") Integer locationId);
}
