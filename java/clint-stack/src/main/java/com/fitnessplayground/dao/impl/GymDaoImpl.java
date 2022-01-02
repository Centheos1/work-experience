package com.fitnessplayground.dao.impl;

import com.fitnessplayground.dao.GymDao;
import com.fitnessplayground.dao.domain.Gym;
import com.fitnessplayground.dao.domain.Gym_MembershipConsultant;
import com.fitnessplayground.dao.domain.Gym_PersonalTrainer;
import com.fitnessplayground.dao.domain.StaffMember;
import com.fitnessplayground.dao.repository.GymRepository;
import com.fitnessplayground.dao.repository.Gym_MembershipConsultantRepository;
import com.fitnessplayground.dao.repository.Gym_PersonalTrainerRepository;
import com.fitnessplayground.exception.DatabaseException;
import com.fitnessplayground.util.Constants;
import com.fitnessplayground.util.Helpers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.mail.Header;
import java.util.List;

@Repository
public class GymDaoImpl implements GymDao {

    private static final Logger logger = LoggerFactory.getLogger(GymDaoImpl.class);

    private GymRepository gymRepository;
    private Gym_MembershipConsultantRepository gym_membershipConsultantRepository;
    private Gym_PersonalTrainerRepository gym_personalTrainerRepository;


    @Override
    public Gym save(Gym gym) {
        try {
//            logger.info("About to save Gym: [{}]", gym.getLocationId());
            gym = getGymRepository().save(gym);
            logger.info("Saved Gym: [{}]", gym.getLocationId());
        } catch (Exception ex) {
            String errorMsg = "Exception saving member: "+gym.getLocationId();
            logger.error("Exception saving member: [{}] Error: [{}]", gym.getLocationId(), ex.getMessage());
            throw new DatabaseException(errorMsg, ex);
        }

        return gym;
    }

    @Override
    public Gym getGymByLocationId(Integer locationId) {
        return getGymRepository().getGymByLocationId(locationId);

    }

    @Override
    public Iterable<Gym> findAllGyms() {
        return getGymRepository().findAll();
    }

    @Override
    public List<Gym_MembershipConsultant> findUIMembershipConsultantsByLocation(Integer locationId) {
        return getGym_membershipConsultantRepository().findByLocationId(locationId);
    }

    @Override
    public List<Gym_PersonalTrainer> findUIPersonalTrainersByLocation(Integer locationId) {
        return getGym_personalTrainerRepository().findByLocationId(locationId);
    }

    @Override
    public Iterable<Gym_MembershipConsultant> findAllUIMembershipConsultants() {
        return getGym_membershipConsultantRepository().findAll();
    }

    @Override
    public Iterable<Gym_PersonalTrainer> findAllUIPersonalTrainers() {
        return getGym_personalTrainerRepository().findAll();
    }

    @Override
    public Gym_PersonalTrainer save(Gym_PersonalTrainer gym_personalTrainer) {
        try {
            logger.info("About to save Gym_PersonalTrainer: [{}]", gym_personalTrainer.getName());
            gym_personalTrainer = getGym_personalTrainerRepository().save(gym_personalTrainer);
            logger.info("Saved Gym_PersonalTrainer: [{}]", gym_personalTrainer.getName());
        } catch (Exception ex) {
            String errorMsg = "Exception saving member: "+gym_personalTrainer.getName();
            logger.error("Exception saving member: [{}] Error: [{}]", gym_personalTrainer.getName(), ex.getMessage());
            throw new DatabaseException(errorMsg, ex);
        }

        return gym_personalTrainer;
    }

    @Override
    public Gym_MembershipConsultant save(Gym_MembershipConsultant gym_membershipConsultant) {
        try {
//            logger.info("About to save Gym_MembershipConsultant: [{}]", gym_membershipConsultant.getName());
            gym_membershipConsultant = getGym_membershipConsultantRepository().save(gym_membershipConsultant);
            logger.info("Saved Gym_PersonalTrainer: [{}]", gym_membershipConsultant.getName());
        } catch (Exception ex) {
            String errorMsg = "Exception saving member: "+gym_membershipConsultant.getName();
            logger.error("Exception saving member: [{}] Error: [{}]", gym_membershipConsultant.getName(), ex.getMessage());
            throw new DatabaseException(errorMsg, ex);
        }

        return gym_membershipConsultant;
    }

    @Override
    public void delete(Gym_PersonalTrainer gym_personalTrainer) {
        getGym_personalTrainerRepository().delete(gym_personalTrainer);
    }

    @Override
    public void delete(Gym_MembershipConsultant gym_membershipConsultant) {
        getGym_membershipConsultantRepository().delete(gym_membershipConsultant);
    }

    @Override
    public void promotionsHubReset() {

        String dateNow = Helpers.getDateNow();
        Iterable<Gym> gyms = getGymRepository().findAll();
        for (Gym gym : gyms) {
            gym.setFirstNDaysDiscountUsed(0);
            gym.setAccessKeyDiscountUsed(0);
            gym.setSatisfactionPeriodDiscountUsed(0);
            gym.setLastResetDate(dateNow);
            getGymRepository().save(gym);
        }
    }

    public GymRepository getGymRepository() {
        return gymRepository;
    }

    @Autowired
    public void setGymRepository(GymRepository gymRepository) {
        this.gymRepository = gymRepository;
    }

    public Gym_MembershipConsultantRepository getGym_membershipConsultantRepository() {
        return gym_membershipConsultantRepository;
    }

    @Autowired
    public void setGym_membershipConsultantRepository(Gym_MembershipConsultantRepository gym_membershipConsultantRepository) {
        this.gym_membershipConsultantRepository = gym_membershipConsultantRepository;
    }

    public Gym_PersonalTrainerRepository getGym_personalTrainerRepository() {
        return gym_personalTrainerRepository;
    }

    @Autowired
    public void setGym_personalTrainerRepository(Gym_PersonalTrainerRepository gym_personalTrainerRepository) {
        this.gym_personalTrainerRepository = gym_personalTrainerRepository;
    }
}
