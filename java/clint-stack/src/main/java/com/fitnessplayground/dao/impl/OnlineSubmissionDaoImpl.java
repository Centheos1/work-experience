package com.fitnessplayground.dao.impl;

import com.fitnessplayground.dao.OnlineSubmissionDao;
import com.fitnessplayground.dao.domain.MboClientProfile;
import com.fitnessplayground.dao.repository.MboClientProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by micheal on 16/03/2017.
 */
@Repository
public class OnlineSubmissionDaoImpl implements OnlineSubmissionDao {


    private static final Logger logger = LoggerFactory.getLogger(OnlineSubmissionDaoImpl.class);

    private final MboClientProfileRepository mboClientProfileRepository;

    @Autowired
    public OnlineSubmissionDaoImpl(MboClientProfileRepository mboClientProfileRepository) {
        this.mboClientProfileRepository = mboClientProfileRepository;
    }


    @Override
    public MboClientProfile saveMboClientProfileSubmission(MboClientProfile mboClientProfile) {

        try {
            logger.info("About to save MBO Client Profile: [{}]", mboClientProfile.getEmail());
            mboClientProfile = mboClientProfileRepository.save(mboClientProfile);
            logger.info("__Profile Saved Successfully");
        } catch (Exception e) {
            logger.error("Exception saving MBO Client Profile: |{}|", mboClientProfile.getEmail());
        }
        return mboClientProfile;
    }
}
