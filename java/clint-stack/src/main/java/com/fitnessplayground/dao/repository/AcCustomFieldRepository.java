package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.AcCustomField;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AcCustomFieldRepository extends CrudRepository<AcCustomField, Long> {

    String FIND_BY_FIELD_ID = "SELECT * FROM AcCustomField WHERE fieldId = :fieldId";

    @Query(value = FIND_BY_FIELD_ID, nativeQuery = true)
    AcCustomField findByCustomFieldById(@Param("fieldId") String fieldId);
}
