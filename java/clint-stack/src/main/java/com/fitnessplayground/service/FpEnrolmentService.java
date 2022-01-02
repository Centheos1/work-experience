package com.fitnessplayground.service;

import com.fitnessplayground.dao.domain.EnrolmentData;
import com.fitnessplayground.dao.domain.ReferralData;
import com.fitnessplayground.dao.domain.temp.EnrolmentDataSubmission;
import com.fitnessplayground.dao.domain.temp.FindEnrolment;
import com.fitnessplayground.dao.domain.temp.ReferralSubmission;

import java.util.List;

public interface FpEnrolmentService {

    List<EnrolmentData> findEnrolments(FindEnrolment findEnrolment);
    EnrolmentData saveEnrolmentDataSubmission(EnrolmentDataSubmission enrolmentDataSubmission);
    EnrolmentData updateEnrolmentData(EnrolmentDataSubmission submission, Boolean encrypted);
    EnrolmentData getEnrolmentData(Long id, Boolean encrypted);
    void saveReferral(ReferralSubmission submission);
    void updateReferral(ReferralSubmission submission);
    List<ReferralData> getReferrals(Long enrolmentDataId);
}
