package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.ReferralData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReferralDataRepository extends CrudRepository<ReferralData, Long> {

    String FIND_REFERRALS_BY_ENROLMENT_DATA_ID = "SELECT * FROM ReferralData WHERE enrolmentDataId = :enrolmentDataId";

    @Query(value = FIND_REFERRALS_BY_ENROLMENT_DATA_ID, nativeQuery = true)
    List<ReferralData> getReferralsByEnrolmentDataId(@Param("enrolmentDataId") Long enrolmentDataId);
}
