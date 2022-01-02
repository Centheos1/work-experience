package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.CancellationData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface CancellationDataRepository extends CrudRepository<CancellationData, Long> {

    String FIND_BY_FORMSTACK_IDS = "SELECT * FROM CancellationData WHERE fs_formId = :fsFormId AND fs_uniqueId = :fsUniqueId ORDER BY id DESC LIMIT 1";

    String FIND_BY_FORM_ID_AND_EMAIL = "SELECT * FROM CancellationData WHERE fs_formId = :fs_formId AND email = :email";

    String FIND_BY_STATUS = "SELECT * FROM CancellationData WHERE status = :status";

    String GET_PT_LAST_30_DAYS = "SELECT * FROM CancellationData WHERE (hasCoach = 1 OR cancellationOptions = 'Personal Training') AND submission_datetime >= DATE_SUB(NOW(), INTERVAL 30 DAY)";

    String GET_CANCELLATIONS_FROM_DATE = "SELECT * FROM CancellationData WHERE submission_datetime >= :fromDate";

    String FIND_BY_UNIQUE_ID_AND_LAST_NAME = "SELECT * FROM CancellationData WHERE lastName = :lastName AND fs_uniqueId = :fsUniqueId ORDER BY id DESC LIMIT 1";

    @Query(value = FIND_BY_FORMSTACK_IDS, nativeQuery = true)
    CancellationData findByFormstackIds(@Param("fsFormId") String fsFormId, @Param("fsUniqueId") String fsUniqueId);

    @Query(value = FIND_BY_FORM_ID_AND_EMAIL, nativeQuery = true)
    List<CancellationData> findByFormIdAndEmail(@Param("fs_formId") String fs_formId, @Param("email") String email);

    @Query(value = FIND_BY_STATUS, nativeQuery = true)
    ArrayList<CancellationData> findCancellationsByStatus(@Param("status") String status);

    @Query(value = GET_PT_LAST_30_DAYS, nativeQuery = true)
    ArrayList<CancellationData> getLast30DaysPt();

    @Query(value = GET_CANCELLATIONS_FROM_DATE, nativeQuery = true)
    ArrayList<CancellationData> getMtdCancellationsFromDate(@Param("fromDate") String fromDate);

    @Query(value = FIND_BY_UNIQUE_ID_AND_LAST_NAME, nativeQuery = true)
    CancellationData findFsUniqueIdAndLastName(@Param("fsUniqueId") String fsUniqueId, @Param("lastName") String  lastName);
}
