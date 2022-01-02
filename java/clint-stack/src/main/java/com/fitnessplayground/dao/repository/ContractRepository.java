package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.MboContract;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ContractRepository extends CrudRepository<MboContract, Long> {

    String FIND_BY_MBO_ID = "SELECT * FROM MboContract WHERE mboId = :mboId AND locationId LIKE '%:locationId%' LIMIT 1";

    String FIND_FRONTEND_CONTRACTS = "SELECT * FROM MboContract WHERE contractType IS NOT NULL";

    @Query(value = FIND_BY_MBO_ID, nativeQuery = true)
    MboContract findByMboId(@Param("mboId") int mboId, @Param("locationId") String locationId);

    @Query(value = FIND_BY_MBO_ID, nativeQuery = true)
    MboContract findByMboIdAndLocation(@Param("mboId") int mboId, @Param("locationId") String locationId);

    @Query(value = FIND_FRONTEND_CONTRACTS, nativeQuery = true)
    Iterable<MboContract> getFrontEndContracts();

}
