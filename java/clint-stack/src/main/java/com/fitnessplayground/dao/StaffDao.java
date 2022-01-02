package com.fitnessplayground.dao;


import com.fitnessplayground.dao.domain.PersonalTrainer;
import com.fitnessplayground.dao.domain.Staff;
import com.fitnessplayground.dao.domain.StaffAccess;
import com.fitnessplayground.dao.domain.StaffMember;

public interface StaffDao {

//    TODO: to be deleted
    StaffMember saveStaff(StaffMember staffMember);
    PersonalTrainer savePersonalTrainer(PersonalTrainer personalTrainer);
    void deleteAllPersonalTrainer(Iterable<PersonalTrainer> trainer);
    void deleteAllStaffMember(Iterable<StaffMember> staffMember);
    Iterable<PersonalTrainer> getAllPersonalTrainer();
    Iterable<StaffMember> getAllStaffMember();
//    TODO: _____

    StaffMember getStaffMemberByMboId(long mboId, long siteId);
    StaffMember getStaffMemberByEmail(String email);
    StaffMember getStaffMemberByFirebaseId(String firebaseId);

    PersonalTrainer getPersonalTrainerByMboId(long mboId, long siteId);
    PersonalTrainer getPersonalTrainerByEmail(String email);
    PersonalTrainer getPersonalTrainerByName(String name);

    Iterable<StaffAccess> getAllStaffAccess();
    void deleteStaffAccess(StaffAccess access);

    Staff getStaffByMboId(Long mboId, Long siteId);
    Staff getStaffByName(String name);
    Staff saveStaff(Staff staff);
    Staff getStaffByEmail(String email);
    Staff getStaffByFirebaseId(String firebaseId);
    Iterable<Staff> getAllStaff();
//    Staff getStaffByMboIdAndSiteId(String mboId, String siteId);

}
