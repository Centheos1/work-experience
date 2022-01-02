package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.AcEmailTag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AcEmailTagRepository extends CrudRepository<AcEmailTag, Long> {

    String FIND_BY_TAG_ID = "SELECT * FROM AcEmailTag WHERE tagId = :tagId";

    @Query(value = FIND_BY_TAG_ID, nativeQuery = true)
    AcEmailTag findByTagId(@Param("tagId") String tagId);

}
