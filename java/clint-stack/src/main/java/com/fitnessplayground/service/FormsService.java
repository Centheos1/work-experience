package com.fitnessplayground.service;

import com.fitnessplayground.dao.domain.*;
import com.fitnessplayground.dao.domain.formstackDto.PtReassignSubmission;
import com.fitnessplayground.dao.domain.temp.CancellationDataSubmission;

import java.util.ArrayList;

public interface FormsService {

    CancellationData handleCancellationRequest(CancellationDataSubmission submission);
    CancellationData getPhoneCancellation(String fsFormId, String fsUniqueId);
    CancellationData getPart1CancellationByLastNameAndUniqueId(String lastName, String fsUniqueId);
    CancellationData updateCancellationData(CancellationData cancellationData);
    ArrayList<CancellationData> getPendingCancellations();
    CancellationData sendCancellationReminder(CancellationData cancellationData);

    MembershipChangeData handleMembershipChange(MembershipChangeData membershipChangeData);
    MembershipChangeData getPhoneMembershipChange(String fsFormId, String fsUniqueId);
    MembershipChangeData updateMembershipChangeData(MembershipChangeData membershipChangeData);
    ArrayList<MembershipChangeData> getPendingMembershipChange();
    MembershipChangeData sendMembershipChangeReminder (MembershipChangeData membershipChangeData);

    void handlePtReassign(PtReassignSubmission ptReassignSubmission);

    void handleDigitalPreExSubmission(PreExData preExData);

    void handleParqSubmission(ParqData parqData);
    ArrayList<PtTracker> getPendingParq();
    PtTracker updatePtTracker(PtTracker ptTracker);
}
