package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.PtTracker;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface PtTrackerRepository extends CrudRepository<PtTracker, Long> {

    String GET_BY_SESSION_COUNT = "SELECT * FROM PtTracker WHERE sessionCount <= :sessionCount AND status != 'PT_CANCELLED'";

    String GET_BY_NO_FIRST_SESSION = "SELECT * FROM PtTracker WHERE hasFirstSessionBooked = 0 AND status != 'PT_CANCELLED'";

    PtTracker findByEnrolmentDataId(Long enrolmentDataId);

    ArrayList<PtTracker> findByStatus(String status);

    ArrayList<PtTracker> findByPersonalTrainer(String personalTrainer);

    @Query(value = GET_BY_SESSION_COUNT, nativeQuery = true)
    ArrayList<PtTracker> findBySessionCount(@Param("sessionCount") Integer sessionCount);

    @Query(value = GET_BY_NO_FIRST_SESSION, nativeQuery = true)
    ArrayList<PtTracker> getPtTrackerByNoFirstSession();
}
