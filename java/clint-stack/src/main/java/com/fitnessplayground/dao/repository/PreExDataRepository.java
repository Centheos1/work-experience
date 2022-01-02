package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.PreExData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface PreExDataRepository extends CrudRepository<PreExData, Long> {


    String SEARCH_PRE_EX_DATA = "SELECT * FROM PreExData WHERE (firstName = :firstName AND lastName = :lastName) OR email = :email OR phone = :phone";

    String GET_PRE_EXS_FROM_DATE = "SELECT * FROM PreExData WHERE submission_datetime >= :fromDate";

    @Query(value = SEARCH_PRE_EX_DATA, nativeQuery = true)
    ArrayList<PreExData> searchPreExData(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("phone") String phone, @Param("email") String email);


    @Query(value = GET_PRE_EXS_FROM_DATE, nativeQuery = true)
    ArrayList<PreExData> getMtdPreExsFromDate(@Param("fromDate") String fromDate);
}
