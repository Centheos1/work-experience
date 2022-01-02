package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.Staff;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface StaffRepository extends CrudRepository<Staff, Long> {

    String FIND_BY_MBO_ID_AND_SITE_ID = "SELECT * FROM Staff WHERE mboId = :mboId AND siteId = :siteId LIMIT 1";

    String FIND_BY_EMAIL = "SELECT * FROM Staff WHERE email = :email LIMIT 1";

    String FIND_BY_FIREBASE_ID = "SELECT * FROM Staff WHERE firebaseId = :firebaseId LIMIT 1";

    String FIND_STAFF_BY_NAME = "SELECT * FROM Staff WHERE name = :name LIMIT 1";

    @Query(value = FIND_BY_MBO_ID_AND_SITE_ID, nativeQuery = true)
    Staff getStaffByMboIdAndSiteId(@Param("mboId") Long mboId, @Param("siteId") Long siteId);

    @Query(value = FIND_BY_EMAIL, nativeQuery = true)
    Staff getStaffByEmail(@Param("email") String email);

    @Query(value = FIND_BY_FIREBASE_ID, nativeQuery = true)
    Staff getStaffByFirebaseId(@Param("firebaseId") String firebaseId);

    @Query(value = FIND_STAFF_BY_NAME, nativeQuery = true)
    Staff getStaffByName(@Param("name") String name);
}
