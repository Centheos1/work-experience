package com.fitnessplayground.service;

import com.fitnessplayground.dao.domain.FpCoachEnrolmentData;
import com.fitnessplayground.dao.domain.temp.FindEnrolment;
import com.fitnessplayground.dao.domain.temp.FpCoachEnrolmentSubmission;

import java.util.List;

public interface FpCoachService {

    List<FpCoachEnrolmentData> findCoachEnrolment(String UID, FindEnrolment findEnrolment);
    FpCoachEnrolmentData saveFpCoachEnrolmentData(FpCoachEnrolmentSubmission fpCoachEnrolmentSubmission);
    FpCoachEnrolmentData updateFpCoachEnrolmentData(FpCoachEnrolmentSubmission fpCoachEnrolmentSubmission, Boolean encrypted);
    FpCoachEnrolmentData getFpCoachEnrolmentData(Long id, String UID, Boolean encrypted);
}
