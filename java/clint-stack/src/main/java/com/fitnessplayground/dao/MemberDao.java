package com.fitnessplayground.dao;

import com.fitnessplayground.dao.domain.*;
import com.fitnessplayground.dao.domain.temp.EnrolmentDataDocument;
import com.fitnessplayground.dao.domain.temp.EnrolmentLookUp;
import com.fitnessplayground.dao.domain.temp.FindEnrolment;
import com.fitnessplayground.dao.domain.temp.SearchByPersDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by micheal on 25/02/2017.
 */
public interface MemberDao {

    EnrolmentData findEnrolmentDataById(long id);

    Iterable<MemberCreditCard> getAllCreditCards();
    MemberCreditCard save(MemberCreditCard memberCreditCard);

    Iterable<EnrolmentData> get100EnrolmentsByLocation(String location);
    Iterable<EnrolmentData> searchEnrolmentData(EnrolmentLookUp enrolmentLookUp);
    EnrolmentData getEnrolmentDataByFormstackIds(String fsFormId, String fsUniqueId);

    List<EnrolmentData> findEnrolments(FindEnrolment findEnrolment);
    List<EnrolmentData> findEnrolmentsByEmail(String email);
    List<EnrolmentData> searchEnrolmentDataByPersDetails(SearchByPersDetails persDetails);
    EnrolmentData getEnrolmentDataById(long id);
    EnrolmentData updateEnrolmentData(EnrolmentData enrolmentData);
    EnrolmentData saveEnrolmentData(EnrolmentData enrolmentData);

    TermsAndConditions getTermsAndConditionsHtml();
    List<EnrolmentData> getProcessingEnrolments();

    List<EnrolmentData> getSuccessEnrolments();

    List<EnrolmentData> getSavedEnrolments();
    List<EnrolmentData> getEmailCampaignPendingEnrolments();
    List<EnrolmentData> getInternalCommsMCNotesEnrolments();

    MemberTermsAndConditions saveMemberTermsAndConditions(MemberTermsAndConditions termsAndConditions);
    List<MemberTermsAndConditions> getMemberTermsAndConditionsList();

    List<FpCoachEnrolmentData> findCoachEnrolments(FindEnrolment findEnrolment);
    FpCoachEnrolmentData saveFpCoachEnrolmentData(FpCoachEnrolmentData fpCoachEnrolmentData);
    FpCoachEnrolmentData updateFpCoachEnrolmentData(FpCoachEnrolmentData fpCoachEnrolmentData);
    FpCoachEnrolmentData getFpCoachEnrolmentDataById(Long id);

    LeadData saveLeadData(LeadData leadData);
    LeadData getLeadDataById(Long id);

    CancellationData saveCancellationData(CancellationData cancellationData);
    CancellationData getCancellationDataByFormstackIds(String fsFormId, String fsUniqueId);
    CancellationData getCancellationDataByFsUniqueIdAndLastName(String lastName, String fsUniqueId);
    CancellationData updateCancellationData(CancellationData cancellationData);
    ArrayList<CancellationData> getCancellationsByStatus(String status);
    CancellationData getCancellationDataById(Long id);

    MembershipChangeData saveMembershipChangeData(MembershipChangeData membershipChangeData);
    MembershipChangeData getMembershipChangeDataByFormstackIds(String fsFormId, String fsUniqueId);
    MembershipChangeData updateMembershipChangeData(MembershipChangeData membershipChangeData);
    ArrayList<MembershipChangeData> getMembershipChangeDataByStatus(String status);
    MembershipChangeData getMembershipChangeDataById(Long id);

    EnrolmentData getEnrolmentDataByFindEnrolment(FindEnrolment findEnrolment);

    FpAcademyEnrolmentData saveFpAcademyEnrolmentData(FpAcademyEnrolmentData fpAcademyEnrolmentData);
    FpAcademyEnrolmentData getFpAcademyDataById(Long id);
    FpAcademyEnrolmentData getFpAcademyEnrolmentDataByFormstackIds(String fsFormId, String fsUniqueId);

    PtFeedbackData savePtFeedbackData(PtFeedbackData ptFeedbackData);
    ArrayList<PtFeedbackData> getPtFeedbackData();
    ArrayList<CancellationData> getPtCancellationData();

    ReferralData getReferralDataById(Long id);
    ReferralData saveReferralData(ReferralData referralData);
    List<ReferralData> getReferralsByEnrolmentDataId(Long enrolmentDataId);

    ClassReviewData saveClassReviewData(ClassReviewData classReviewData);

    WebReferralData saveWebReferralData(WebReferralData webReferralData);
    WebReferralData searchWebReferrals(String firstName, String lastName, String phone);

    PreExData savePreExData(PreExData preExData);
    PreExData getPreExDataById(Long id);
    PreExData searchPreExData(String firstName, String lastName, String phone, String email);

    ParqData saveParqData(ParqData parqData);
    ParqData searchParqData(String firstName, String lastName, String phone, String email);

    PtTracker savePtTracker(PtTracker ptTracker);
    PtTracker updatePtTracker(PtTracker ptTracker);
    PtTracker getPtTrackerById(Long id);
    PtTracker findPtTrackerByEnrolmentDataId(Long enrolmentDataId);
    ArrayList<PtTracker> getPtTrackerByStatus(String status);
    ArrayList<PtTracker> getPtTrackerBySessionCount(Integer sessionCount);
    ArrayList<PtTracker> getPtTrackerByPersonalTrainer(String personalTrainer);
    ArrayList<PtTracker> getPtTrackerByNoFirstSession();

    ArrayList<EnrolmentData> getMtdEnrolments();
    ArrayList<CancellationData> getMtdCancellations();
    ArrayList<PreExData> getMtdPreExs();
    ArrayList<FpCoachEnrolmentData> getMtdFpCoachEnrolments();

    Iterable<CloudSearch> addCloudSearchMembers(ArrayList<CloudSearch> members);
    Iterable<CloudSearch> getAllCloudSearch();

    Iterable<DebtPortal> getAllDebtPortal();
    ArrayList<DebtPortal> getAllCurrentDebtPortal();
    ArrayList<DebtPortal> getDebtPortalCommsList();
    DebtPortal getDebtPortalById(Long id);
    DebtPortal saveDebtPortal(DebtPortal debtPortal);
//    DebtPortal updateDebtPortal(DebtPortal debtPortal);

}
