package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.Gym_PersonalTrainer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Gym_PersonalTrainerRepository extends CrudRepository<Gym_PersonalTrainer, Long> {

    String FIND_BY_LOCATION_ID = "SELECT * FROM Gym_MembershipConsultant WHERE locationId = :locationId";

    @Query(value = FIND_BY_LOCATION_ID, nativeQuery = true)
    List<Gym_PersonalTrainer> findByLocationId(@Param("locationId") Integer locationId);
}
