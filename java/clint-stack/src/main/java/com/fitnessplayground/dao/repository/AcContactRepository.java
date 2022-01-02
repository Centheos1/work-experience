package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.AcContact;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AcContactRepository extends CrudRepository<AcContact, Long> {

    String FIND_BY_AC_CONTACT_ID = "SELECT * FROM AcContact WHERE acContactId = :acContactId";

    String FIND_BY_EMAIL = "SELECT * FROM AcContact WHERE email = :email";

    @Query(value = FIND_BY_AC_CONTACT_ID, nativeQuery = true)
    List<AcContact> findAcContactByAcContactId(@Param("acContactId") String acContactId);

    @Query(value = FIND_BY_EMAIL, nativeQuery = true)
    List<AcContact> findByEmail(@Param("email") String email);

}
