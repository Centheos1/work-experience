package com.fitnessplayground.service;

import com.fitnessplayground.dao.domain.ClassReviewData;
import com.fitnessplayground.dao.domain.LeadData;
import com.fitnessplayground.dao.domain.WebFlowDto.WebflowApiLeadSubmissionData;
import com.fitnessplayground.dao.domain.WebReferralData;
import com.fitnessplayground.dao.domain.temp.GravityFormsWebhookReferral;
import com.fitnessplayground.dao.domain.temp.WebLead;

public interface FpWebsiteService {

    LeadData handleLead(String submissionData);
    void processFormSubmission(LeadData data);
    void processWebflowApiFormSubmission(WebflowApiLeadSubmissionData submission);
    void handleWebLead(WebLead webLead);
    void handleClassReview(ClassReviewData classReviewData);
    WebReferralData handleWebReferral(String submissionData);
    void processWebReferral(WebReferralData webReferralData);
    WebReferralData handleGravityFormsWebReferral(GravityFormsWebhookReferral gravityFormsWebhookReferral);
}
