package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.MemberTermsAndConditions;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MemberTermsAndConditionsRepository extends CrudRepository<MemberTermsAndConditions, Long> {

    String FIND_UNPROCESSED = "SELECT * FROM MemberTermsAndConditions WHERE status = 'SAVED' LIMIT 20";

    @Query(value = FIND_UNPROCESSED, nativeQuery = true)
    List<MemberTermsAndConditions> getUnprocessed();
}
