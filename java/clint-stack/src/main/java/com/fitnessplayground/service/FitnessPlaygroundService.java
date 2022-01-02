package com.fitnessplayground.service;

import com.fitnessplayground.dao.domain.*;
import com.fitnessplayground.dao.domain.Staff;
import com.fitnessplayground.dao.domain.fpSourceDto.CompanyDashboard;
import com.fitnessplayground.dao.domain.fpSourceDto.DigitalPreExData;
import com.fitnessplayground.dao.domain.mboDto.*;
import com.fitnessplayground.dao.domain.reportDto.PtFeedbackReportData;
import com.fitnessplayground.dao.domain.temp.*;
import com.fitnessplayground.model.mindbody.api.result.MboApiResultEnrolmentData;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by micheal on 25/02/2017.
 */
public interface FitnessPlaygroundService {

    boolean isMindBodyOnline();
    boolean isSyncMboClientsOn();
    boolean isSyncMboClientContractsOn();
    boolean isSyncMboClientMembershipsOn();
    boolean isSyncMboContractsOn();
    boolean isSyncMboServicesOn();
    boolean isSyncMboStaffOn();
    boolean isProcessPDFs();
    boolean isProcessEnrolmentsOn();
    boolean isCancellationRemindersOn();
    boolean isMembershipChangeRemindersOn();
    boolean isSyncMboProductsOn();
    boolean isParqRemindersOn();
    boolean isUpdatePtSessionCountOn();
    boolean isNotifyUnservicedPtOn();
    boolean isRefreshEncryptionKeysOn();
    boolean isDataWarehouseSyncOn();

    void processTest(Test test);

    boolean validateStaffId(String UID);

    List<MboContract> getContracts(String locationId);
    List<MboContract> getAllContracts(String locationId);
    List<MboService> getAllServices(String locationId);
    List<MboProduct> getAllProducts(String locationId);

    List<PersonalTrainer> getPersonalTrainersByLocation(String locationId);
    List<StaffMember> getStaffsByLocation(String locationId);
    StaffMember getStaffMemberByMboId(long mboId, long siteId);
    StaffMember signUpStaffMemberFirebase(StaffLookUp staffLookUp);


    Iterable<StaffMember> getAllStaffMembers();

    List<MboClient> searchForExistingClient(PrimaryDetails primaryDetails);
    List<MboClient> searchForExistingClients(String UID, String firstName, String lastName, String email);

//    v1
    void handleEnrolmentSubmission(EnrolmentFormSubmission enrolmentFormSubmission);


    boolean isDuplicateUsername(String userName);
    MboAddNewClientResponse addNewClient(EnrolmentData enrolmentData, boolean isTestSubmission, boolean skipKey, boolean skipEmail);
    MboUpdateClientResponse updateClient(EnrolmentData enrolmentData, Client client, boolean isTestSubmission, boolean isDuplicateKey);
    MboApiResultEnrolmentData purchaseTempMembership(String serviceId, EnrolmentData enrolmentData);
    MboAddClientDirectDebitInfoResponse addClientDirectDebit(EnrolmentData enrolmentData, Boolean isTestSubmission);
//    MBO Version 6
    MboPurchaseContractResponse purcaseMboContract(MboContract contract, EnrolmentData data, Boolean isTestSubmission);
//    MBO Version 5.1
    MboApiResultEnrolmentData purchaseContract(MboContract contract, EnrolmentData data);

    MboShoppingCartResponse purchaseMboProduct(EnrolmentData enrolmentData, Boolean isTestSubmission);

    void handleTestSubmission(String submission);

    Iterable<EnrolmentSubmissionError> getEnrolmentErrorSubmissions(String UID, String location);
    void completeEnrolmentErrorSubmissions(String UID, String enrolmentSubmissionErrorId);

    Iterable<EnrolmentData> getEnrolments(String UID, String location);
    Iterable<EnrolmentData> searchEnrolments(String UID, EnrolmentLookUp enrolmentLookUp);
    List<EnrolmentData> findEnrolments(String UID, FindEnrolment findEnrolment);
    EnrolmentData getPhoneEnrolment(String UID, String fsFormId, String fsUniqueId);

    TermsAndConditions getTermsAndConditionsHtml();

    ArrayList<UIGym> updateGym(String UID, UpdateGym updateGym);
    void updateStaff(String UID, UpdateStaff updateStaff);
    UIGym getGymByLocation(String UID, String location);
    Staff getStaffByMboIdAndSiteId(String UID, Long mboId, Long siteId);
    Staff getStaffByMboIdAndSiteId(String UID, String mboIdAndSiteId);
    void refreshGyms();
    void refreshUIStaffs();
    public void refreshManualSubmissionsMap();
    void refreshSubmissionsMap();
    Staff getStaffByFirebaseId(StaffLookUp staffLookUp);
    ArrayList<UIGym> getAllUiGyms(String UID);
    Iterable<Gym> getGyms();
    ArrayList<UIStaff> getAllUIStaff(String UID);
    Iterable<Staff> getAllStaff(String UID);

    Test handleTestProcess(Test t) throws InterruptedException;

//    CompletableFuture<String> testAsync(int i);
    void testAsync(int i);

    AccessKeySiteCode findSiteCodeByLocationId(String UID, Integer locationId);
    String cleanEncodedStaffName(String encodedStaff);

    EnrolmentDataDocument getDataDocByFindMember(FindEnrolment findEnrolment);

    PtFeedbackData handleFeedbackSubmission(PtFeedbackData ptFeedbackData);
    PtFeedbackReportData getPtFeedbackReportData();

    void handleManualSubmission(ManualSubmission manualSubmission);

    DigitalPreExData getDigitalPreExData(EnrolmentData enrolmentData);
    PreExData getPreExData(String firstName, String lastName, String phone, String email);

    void handleWebReferrals(EnrolmentData enrolmentData);

    void updatePtSessionsCount();

    ArrayList<PtTracker> getPtTrackerByPersonalTrainer(String personalTrainer);
    ArrayList<PtTracker> getPtTrackerByNoFirstSession();
    PtTracker updatePtTracker(PtTracker ptTracker);
    PtTracker handlePtTracker(EnrolmentData enrolmentData, PreExData preExData, Client mboClient);
    PtTracker savePtTracker(PtTracker ptTracker);

    CompanyDashboard getCompanyDashboardData();

    void promotionsHubReset();

    Gym getGymsByLocation(String locationId); // returns Gym

}
