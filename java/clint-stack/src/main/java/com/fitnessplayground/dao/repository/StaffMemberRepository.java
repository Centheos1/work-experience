package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.StaffMember;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface StaffMemberRepository extends CrudRepository<StaffMember, Long> {

    String FIND_BY_MBO_ID = "SELECT * FROM StaffMember WHERE mboId = :mboId AND siteId = :siteId LIMIT 1";

    String FIND_BY_EMAIL = "SELECT * FROM StaffMember WHERE email = :email LIMIT 1";

    String FIND_BY_FIREBASE_ID = "SELECT * FROM StaffMember WHERE firebaseId = :firebaseId LIMIT 1";


    @Query(value = FIND_BY_MBO_ID, nativeQuery = true)
    StaffMember getStaffMemberByMboId(@Param("mboId") long mboId, @Param("siteId") long siteId);

    @Query(value = FIND_BY_EMAIL, nativeQuery = true)
    StaffMember getStaffMemberByEmail(@Param("email") String email);

    @Query(value = FIND_BY_FIREBASE_ID, nativeQuery = true)
    StaffMember getStaffMemberByFirebaseId(@Param("firebaseId") String email);



}
