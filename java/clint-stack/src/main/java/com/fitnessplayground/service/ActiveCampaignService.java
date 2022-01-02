package com.fitnessplayground.service;

import com.fitnessplayground.dao.domain.*;
import com.fitnessplayground.dao.domain.ActiveCampaignDto.ACContact;
import com.fitnessplayground.dao.domain.ActiveCampaignDto.ACTag;
import com.fitnessplayground.dao.domain.ActiveCampaignDto.ACWebhookContactBody;
import com.fitnessplayground.dao.domain.formstackDto.PtReassignSubmission;
import com.fitnessplayground.dao.domain.fpSourceDto.DigitalPreExData;
import com.fitnessplayground.dao.domain.fpSourceDto.FPPTParQData;
import com.fitnessplayground.dao.domain.fpSourceDto.FPReassignPersonalTrainerRequest;

import java.util.ArrayList;
import java.util.List;

public interface ActiveCampaignService {

    ArrayList<ACContact> getAllContacts();
    List<AcContact> getContactByEmail(String email);
    ArrayList<AcEmailTag> getAllTags();
    ArrayList<AcCustomField> getAllCustomFields();
    EnrolmentData createContact(EnrolmentData enrolmentData, PreExData preExData, PtTracker ptTracker);
    Boolean addTags(EnrolmentData enrolmentData);
    Boolean addCustomFields(EnrolmentData enrolmentData, PreExData preExData, PtTracker ptTracker);
    void processPTParQ(FPPTParQData data);
    void processReassignPersonalTrainer(FPReassignPersonalTrainerRequest fpReassignPersonalTrainerRequest);
    void processPtReassign(Long ACID, Staff staff, String queryParams);
    boolean isActiveCampaignOn();
    boolean isAddContactOn();
    void handleWebhookContact(String body);

    Iterable<AcEmailTag> getTags();
    Iterable<AcCustomField> getCustomFields();

    LeadData addOrUpdateContact(LeadData leadData);

    void handleFpCoachCommunications(FpCoachEnrolmentData fpCoachEnrolmentData);
    void handleEnrolmentCommunications(EnrolmentData enrolmentData, DigitalPreExData preExData);

    void handleReferredByMemberComms(EnrolmentData enrolmentData, WebReferralData webReferralData);
}
