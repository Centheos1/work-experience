package com.fitnessplayground.service;

import com.fitnessplayground.dao.domain.*;
import com.fitnessplayground.dao.domain.mboDto.Client;

public interface EmailService {

    void sendErrorNotification(String errorMessage, String method);
    void sendEmail(String toEmail, String subject, String text);
    void sendEmailFromForms(String toEmail, String subject, String text, boolean isHtml);
    void sendEmailFromCoaching(String toEmail, String subject, String text, boolean isHtml, String replyTo);
    void sendEnrolmentDataNotificationToCoach(EnrolmentData enrolmentData, PreExData preExData, ParqData parqData, PtTracker ptTracker);
    void sendCancellationNotificationToCoach(CancellationData cancellationData);
    void sendSuspensionNotificationToCoach(MembershipChangeData membershipChangeData);
    void sendChangeNotificationToCoach(MembershipChangeData membershipChangeData);
    void sendFpCoachNotificationToCoach(FpCoachEnrolmentData fpCoachEnrolmentData);
    void resendParq(Long ptTrackerId);
    void sendParqReminder(PtTracker ptTracker);
    void sendPtEarlyFeedbackRequest(PtTracker ptTracker, Client mboClient);
    void sendPtFeedbackRequest(PtTracker ptTracker, Client mboClient);
    void sendUnservicedLeadToCoach(PtTracker ptTracker);
    void sendUnservicedLeadToHeadCoach(PtTracker ptTracker);
    void sendParqTrainerNotification(ParqData parqData);
}
