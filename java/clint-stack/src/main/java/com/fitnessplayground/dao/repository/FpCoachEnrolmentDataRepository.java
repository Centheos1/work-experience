package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.FpCoachEnrolmentData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface FpCoachEnrolmentDataRepository extends CrudRepository<FpCoachEnrolmentData, Long> {

    String FIND_BY_FS_IDS = "SELECT * FROM FpCoachEnrolmentData WHERE fs_formId = :fs_formId AND fs_uniqueId = :fs_uniqueId";

    String FIND_BY_FORM_ID_AND_EMAIL = "SELECT * FROM FpCoachEnrolmentData WHERE fs_formId = :fs_formId AND email = :email";

    String FIND_ENROLMENTS = "SELECT * FROM FpCoachEnrolmentData WHERE firstName = :firstName AND lastName = :lastName AND email = :email ORDER BY id DESC LIMIT 1";

    String GET_ENROLMENTS_FROM_DATE = "SELECT * FROM FpCoachEnrolmentData WHERE createDate >= :fromDate";

    @Query(value = FIND_BY_FS_IDS, nativeQuery = true)
    FpCoachEnrolmentData findByFormstackIds(@Param("fs_formId") String fs_formId, @Param("fs_uniqueId") String fs_uniqueId);

    @Query(value = FIND_BY_FORM_ID_AND_EMAIL, nativeQuery = true)
    List<FpCoachEnrolmentData> findByFormIdAndEmail(@Param("fs_formId") String fs_formId, @Param("email") String email);

    @Query(value = FIND_ENROLMENTS, nativeQuery = true)
    List<FpCoachEnrolmentData> findEnrolments(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("email") String email);

    @Query(value = GET_ENROLMENTS_FROM_DATE, nativeQuery = true)
    ArrayList<FpCoachEnrolmentData> getMtdFpCoachEnrolmentsFromDate(@Param("fromDate") String fromDate);
}
