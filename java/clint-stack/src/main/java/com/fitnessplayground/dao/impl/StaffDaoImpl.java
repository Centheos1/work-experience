package com.fitnessplayground.dao.impl;

import com.fitnessplayground.dao.StaffDao;
import com.fitnessplayground.dao.domain.PersonalTrainer;
import com.fitnessplayground.dao.domain.Staff;
import com.fitnessplayground.dao.domain.StaffAccess;
import com.fitnessplayground.dao.domain.StaffMember;
import com.fitnessplayground.dao.repository.PersonalTrainerRepository;
import com.fitnessplayground.dao.repository.StaffAccessRepository;
import com.fitnessplayground.dao.repository.StaffMemberRepository;
import com.fitnessplayground.dao.repository.StaffRepository;
import com.fitnessplayground.exception.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StaffDaoImpl implements StaffDao {

    private static final Logger logger = LoggerFactory.getLogger(StaffDaoImpl.class);

    private StaffMemberRepository staffMemberRepository;
    private PersonalTrainerRepository personalTrainerRepository;
    private StaffAccessRepository staffAccessRepository;
    private StaffRepository staffRepository;


    @Override
    public StaffMember saveStaff(StaffMember staff) {
        try {
//            logger.info("About to save Member: [{}]", staff.getName());
            staff = getStaffMemberRepository().save(staff);
//            logger.info("Saved Staff: [{}]", staff.getName());
        } catch (Exception ex) {
            String errorMsg = "Exception Staff member: "+staff.getName();
            logger.error("Exception saving Staff: [{}] Error: [{}]", staff.getName(), ex.getMessage());
            throw new DatabaseException(errorMsg, ex);
        }
        return staff;
    }

    @Override
    public PersonalTrainer savePersonalTrainer(PersonalTrainer personalTrainer) {
        try {
//            logger.info("About to save Staff: [{}]", personalTrainer.getName());
            personalTrainer = getPersonalTrainerRepository().save(personalTrainer);
//            logger.info("Saved Personal Trainer: [{}]", personalTrainer.getName());
        } catch (Exception ex) {
            String errorMsg = "Exception saving Personal Trainer: "+personalTrainer.getName();
            logger.error("Exception saving Personal Trainer: [{}] Error: [{}]", personalTrainer.getName(), ex.getMessage());
            throw new DatabaseException(errorMsg, ex);
        }
        return personalTrainer;
    }

    @Override
    public void deleteAllPersonalTrainer(Iterable<PersonalTrainer> trainerList) {
        getPersonalTrainerRepository().delete(trainerList);
    }

    @Override
    public void deleteAllStaffMember(Iterable<StaffMember> staffMemberList) {
        getStaffMemberRepository().delete(staffMemberList);
    }


    @Override
    public Iterable<PersonalTrainer> getAllPersonalTrainer() {
        return getPersonalTrainerRepository().findAll();
    }

    @Override
    public Iterable<StaffMember> getAllStaffMember() {
        return getStaffMemberRepository().findAll();
    }

    @Override
    public StaffMember getStaffMemberByMboId(long mboId, long siteId) {
        return getStaffMemberRepository().getStaffMemberByMboId(mboId, siteId);
    }

    @Override
    public StaffMember getStaffMemberByEmail(String email) { return getStaffMemberRepository().getStaffMemberByEmail(email); }

    @Override
    public StaffMember getStaffMemberByFirebaseId(String firebaseId) {
        return getStaffMemberRepository().getStaffMemberByFirebaseId(firebaseId);
    }

    @Override
    public PersonalTrainer getPersonalTrainerByMboId(long mboId, long siteId) {
        return getPersonalTrainerRepository().getPersonalTrainerByMboId(mboId, siteId);
    }

    @Override
    public PersonalTrainer getPersonalTrainerByEmail(String email) {

        PersonalTrainer trainer = null;
        Iterable<PersonalTrainer> trainers = getPersonalTrainerRepository().findAll();

        for (PersonalTrainer t : trainers) {
            if (t.getEmail().equals(email)) {
                trainer = t;
            }
         }
        return trainer;
//        return personalTrainerRepository.getPersonalTrainerByEmail(email);
    }

    @Override
    public PersonalTrainer getPersonalTrainerByName(String name) {

        PersonalTrainer trainer = null;
        Iterable<PersonalTrainer> trainers = getPersonalTrainerRepository().findAll();

        for (PersonalTrainer t : trainers) {
            if (t.getName().equalsIgnoreCase(name)) {
                trainer = t;
            }
        }
        return trainer;
//        return personalTrainerRepository.getPersonalTrainerByName(name);
    }

    @Override
    public Iterable<StaffAccess> getAllStaffAccess() {
        return getStaffAccessRepository().findAll();
    }

    @Override
    public void deleteStaffAccess(StaffAccess access) {
        getStaffAccessRepository().delete(access);
    }

    @Override
    public Staff getStaffByMboId(Long mboId, Long siteId) {
        return getStaffRepository().getStaffByMboIdAndSiteId(mboId, siteId);
    }

    @Override
    public Staff getStaffByName(String name) {
        return getStaffRepository().getStaffByName(name);
    }


    @Override
    public Staff saveStaff(Staff staff) {
        try {
//            logger.info("About to save Staff: [{}]", staff.getName());
            staff = getStaffRepository().save(staff);
            logger.info("Saved Staff: [{}]", staff.getName());
        } catch (Exception ex) {
            String errorMsg = "Exception Staff: "+staff.getName();
            logger.error("Exception saving Staff: [{}] Error: [{}]", staff.getName(), ex.getMessage());
            throw new DatabaseException(errorMsg, ex);
        }
        return staff;
    }

    @Override
    public Staff getStaffByEmail(String email) { return getStaffRepository().getStaffByEmail(email); }

    @Override
    public Staff getStaffByFirebaseId(String firebaseId) {
        return getStaffRepository().getStaffByFirebaseId(firebaseId);
    }

    @Override
    public Iterable<Staff> getAllStaff() {
        return getStaffRepository().findAll();
    }


    public StaffMemberRepository getStaffMemberRepository() {
        return staffMemberRepository;
    }

    @Autowired
    public void setStaffMemberRepository(StaffMemberRepository staffMemberRepository) {
        this.staffMemberRepository = staffMemberRepository;
    }

    public PersonalTrainerRepository getPersonalTrainerRepository() {
        return personalTrainerRepository;
    }

    @Autowired
    public void setPersonalTrainerRepository(PersonalTrainerRepository personalTrainerRepository) {
        this.personalTrainerRepository = personalTrainerRepository;
    }

    public StaffAccessRepository getStaffAccessRepository() {
        return staffAccessRepository;
    }

    @Autowired
    public void setStaffAccessRepository(StaffAccessRepository staffAccessRepository) {
        this.staffAccessRepository = staffAccessRepository;
    }

    public StaffRepository getStaffRepository() {
        return staffRepository;
    }

    @Autowired
    public void setStaffRepository(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }
}
