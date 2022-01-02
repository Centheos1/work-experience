package com.fitnessplayground.dao.repository;


import com.fitnessplayground.dao.domain.MembershipChangeData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface MembershipChangeDataRepository extends CrudRepository<MembershipChangeData, Long> {

    String FIND_BY_FORMSTACK_IDS = "SELECT * FROM MembershipChangeData WHERE fs_formId = :fsFormId AND fs_uniqueId = :fsUniqueId ORDER BY id DESC LIMIT 1";

    String FIND_BY_FORM_ID_AND_EMAIL = "SELECT * FROM MembershipChangeData WHERE fs_formId = :fs_formId AND email = :email";

    String FIND_BY_STATUS = "SELECT * FROM MembershipChangeData WHERE status = :status";

    @Query(value = FIND_BY_FORMSTACK_IDS, nativeQuery = true)
    MembershipChangeData findByFormstackIds(@Param("fsFormId") String fsFormId, @Param("fsUniqueId") String fsUniqueId);

    @Query(value = FIND_BY_FORM_ID_AND_EMAIL, nativeQuery = true)
    List<MembershipChangeData> findByFormIdAndEmail(@Param("fs_formId") String fs_formId, @Param("email") String email);

    @Query(value = FIND_BY_STATUS, nativeQuery = true)
    ArrayList<MembershipChangeData> findByStatus(@Param("status") String status);
}
