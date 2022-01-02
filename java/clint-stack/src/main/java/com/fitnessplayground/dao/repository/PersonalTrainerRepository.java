package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.PersonalTrainer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PersonalTrainerRepository extends CrudRepository<PersonalTrainer, Long> {

    String FIND_BY_MBO_ID = "SELECT * FROM PersonalTrainer WHERE mboId = :mboId AND siteId = :siteId LIMIT 1";

    String FIND_BY_EMAIL = "SELECT * FROM PersonalTrainer WHERE email = ':email' LIMIT 1";

    String FIND_BY_NAME = "SELECT * FROM PersonalTrainer WHERE name = ':name' LIMIT 1";

    @Query(value = FIND_BY_MBO_ID, nativeQuery = true)
    PersonalTrainer getPersonalTrainerByMboId(@Param("mboId") long mboId, @Param("siteId") long siteId);

    @Query(value = FIND_BY_EMAIL, nativeQuery = true)
    PersonalTrainer getPersonalTrainerByEmail(@Param("email") String email);

    @Query(value = FIND_BY_NAME, nativeQuery = true)
    PersonalTrainer getPersonalTrainerByName(@Param("name") String name);

}
