package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.ParqData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface ParqDataRepository extends CrudRepository<ParqData, Long> {

    String SEARCH_PAR_Q_DATA = "SELECT * FROM ParqData WHERE (firstName = :firstName AND lastName = :lastName) OR email = :email OR phone = :phone";

    @Query(value = SEARCH_PAR_Q_DATA, nativeQuery = true)
    ArrayList<ParqData> searchParqData(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("phone") String phone, @Param("email") String email);


}
