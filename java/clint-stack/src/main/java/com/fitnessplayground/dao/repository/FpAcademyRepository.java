package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.FpAcademyEnrolmentData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FpAcademyRepository extends CrudRepository<FpAcademyEnrolmentData, Long> {

    String FIND_BY_FS_IDS = "SELECT * FROM FpAcademyEnrolmentData WHERE fs_formId = :fs_formId AND fs_uniqueId = :fs_uniqueId";

    @Query(value = FIND_BY_FS_IDS, nativeQuery = true)
    FpAcademyEnrolmentData findByFormstackIds(@Param("fs_formId") String fs_formId, @Param("fs_uniqueId") String fs_uniqueId);
}
