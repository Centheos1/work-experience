package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.DebtPortal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface DebtPortalRepository extends CrudRepository<DebtPortal, Long> {


    String FIND_CURRENT = "SELECT * FROM DebtPortal WHERE status IN ('NAB_UNPROCESSED','NAB_PARTIAL_PAYMENT','NAB_COMMS_SEQUENCE_ACTIVE')";

    String FIND_ACTIVE_COMMS = "SELECT * FROM DebtPortal WHERE status = 'NAB_COMMS_SEQUENCE_ACTIVE' OR communications_status = 'COMMS_PENDING'";

    @Query(value = FIND_CURRENT, nativeQuery = true)
    ArrayList<DebtPortal> findAllCurrent();

    @Query(value = FIND_ACTIVE_COMMS, nativeQuery = true)
    ArrayList<DebtPortal> getCommsList();
}
