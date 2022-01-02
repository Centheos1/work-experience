package com.fitnessplayground.service;

import com.fitnessplayground.dao.domain.FpAcademyEnrolmentData;
import com.fitnessplayground.dao.domain.temp.FpAcademyEnrolmentSubmission;

public interface FpAcademyService {

//    List<FpAcademyEnrolmentData> findCoachEnrolment(String UID, FindEnrolment findEnrolment);
    FpAcademyEnrolmentData saveFpAcademyEnrolmentData(FpAcademyEnrolmentSubmission fpAcademyEnrolmentSubmission);
    FpAcademyEnrolmentData updateFpAcademyEnrolmentData(FpAcademyEnrolmentSubmission submission, Boolean encrypted, Boolean isNewPaymentDetails);
    FpAcademyEnrolmentData getFpAcademyEnrolmentData(Long id, Boolean encrypted);
    FpAcademyEnrolmentData getPhoneFpAcademyEnrolmentData(String fsFormId, String fsUniqueId);
}
