package com.fitnessplayground.dao;

import com.fitnessplayground.dao.domain.Gym;
import com.fitnessplayground.dao.domain.Gym_MembershipConsultant;
import com.fitnessplayground.dao.domain.Gym_PersonalTrainer;
import com.fitnessplayground.dao.domain.StaffMember;

import java.util.List;

public interface GymDao {

    Gym save(Gym gym);
    Gym getGymByLocationId(Integer locationId);
    Iterable<Gym> findAllGyms();
    List<Gym_MembershipConsultant> findUIMembershipConsultantsByLocation(Integer locationId);
    List<Gym_PersonalTrainer> findUIPersonalTrainersByLocation(Integer locationId);
    Iterable<Gym_MembershipConsultant> findAllUIMembershipConsultants();
    Iterable<Gym_PersonalTrainer> findAllUIPersonalTrainers();
    Gym_PersonalTrainer save(Gym_PersonalTrainer gym_personalTrainer);
    Gym_MembershipConsultant save(Gym_MembershipConsultant gym_membershipConsultant);
    void delete(Gym_PersonalTrainer gym_personalTrainer);
    void delete(Gym_MembershipConsultant gym_membershipConsultant);
    void promotionsHubReset();
}
