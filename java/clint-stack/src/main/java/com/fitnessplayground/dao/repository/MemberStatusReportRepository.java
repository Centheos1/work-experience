package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.MemberStatusReport;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MemberStatusReportRepository extends CrudRepository<MemberStatusReport, Long> {

    String GET_LATEST_REPORT = "SELECT * FROM MemberStatusReport WHERE locationId = :locationId ORDER BY createDate DESC LIMIT 1";

    @Query(value = GET_LATEST_REPORT, nativeQuery = true)
    MemberStatusReport getLastestReport(@Param("locationId") int locationId);

}

