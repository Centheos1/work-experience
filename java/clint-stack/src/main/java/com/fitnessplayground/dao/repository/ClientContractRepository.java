package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.MboClientContract;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface ClientContractRepository extends CrudRepository<MboClientContract, Long> {

    String FIND_BY_CONTRACT_ID_AND_SITE_ID = "SELECT * FROM MboClientContract WHERE clientContractId = :clientContractId AND siteId = :siteId LIMIT 1";

    @Query(value = FIND_BY_CONTRACT_ID_AND_SITE_ID, nativeQuery = true)
    MboClientContract findByContractIdAndSiteId(@Param("clientContractId") long clientContractId, @Param("siteId") long siteId);

}
