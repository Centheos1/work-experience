package com.fitnessplayground.service.impl;

import com.fitnessplayground.dao.*;
import com.fitnessplayground.dao.domain.*;
import com.fitnessplayground.dao.domain.Staff;
import com.fitnessplayground.dao.domain.fpSourceDto.*;
import com.fitnessplayground.dao.domain.mboDto.*;
import com.fitnessplayground.dao.domain.ops.FPOpsConfig;
import com.fitnessplayground.dao.domain.reportDto.PtFeedbackReportData;
import com.fitnessplayground.dao.domain.temp.*;
import com.fitnessplayground.model.mindbody.api.result.MboApiResultEnrolmentData;
import com.fitnessplayground.service.*;
import com.fitnessplayground.util.*;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Created by micheal on 25/02/2017.
 */
@Service
public class FitnessPlaygroundServiceImpl implements FitnessPlaygroundService {

    private static final Logger logger = LoggerFactory.getLogger(FitnessPlaygroundServiceImpl.class);

    private ArrayList<UIGym> uiGyms;
    private ArrayList<UIStaff> uiStaffs;
    private List<Gym_PersonalTrainer> gym_personalTrainers;
    private List<Gym_MembershipConsultant> gym_membershipConsultants;
    private HashMap<String, ArrayList<EnrolmentSubmissionError>> manualSubmissionsMap = new HashMap<>();
    private HashMap<String, Iterable<EnrolmentData>> submissionsMap = new HashMap<>();
    private int retryCount;
    private final int RETRY_BREAK = 3;
    private boolean isValid;
    private int requestLimit = 100;
    private int requestOffset;
    private int count;
    private int totalResults;

    @Value("${fp.source.handshake.key}")
    private String HANDSHAKE_KEY;

    @Value("${fp.reports.baseurl}")
    private String REPORTS_BASE_URL;

    private RestTemplate restTemplate;

    @Value("${mindbody.service.siteid}")
    private String SITE_ID;

    @Value("${mindbody.service.siteid.darwin}")
    private String GW_SITE_ID;

    @Value("${mindbody.product.access.key.price}")
    private String ACCESS_KEY_PRICE;

    @Value("${access.key.site.code}")
    private String ACCESS_KEY_SITE_CODE_DEFAULT;

    @Value("${access.key.site.code.bunker}")
    private String ACCESS_KEY_SITE_CODE_BUNKER;

    @Value("${access.key.site.code.gateway}")
    private String ACCESS_KEY_SITE_CODE_GATEWAY;

    @Value("${access.key.site.code.newtown}")
    private String ACCESS_KEY_SITE_CODE_NEWTOWN;

    @Value("${access.key.site.code.marrickville}")
    private String ACCESS_KEY_SITE_CODE_MARRICKVILLE;

    @Value("${access.key.site.code.surryhills}")
    private String ACCESS_KEY_SITE_CODE_SURRY_HILLS;

    @Value("${communications.pt.early.feedback.session.count}")
    private Integer PT_EARLY_FEEDBACK_SESSION_COUNT;

    @Value("${communications.pt.feedback.session.count}")
    private Integer PT_FEEDBACK_SESSION_COUNT;

    private MindBodyClientService mindBodyClientService;
    private MindBodySaleService mindBodySaleService;
    private MindBodyStaffService mindBodyStaffService;
    private MindBodyService mindBodyService;
    private EncryptionService encryptionService;
    private EmailService emailService;
    private ActiveCampaignService activeCampaignService;
    private MemberDao memberDao;
    private FPOpsConfigDao fpOpsConfigDao;
    private SubmissionErrorDao submissionErrorDao;
    private TestDao testDao;
    private StaffDao staffDao;
    private SaleDao saleDao;
    private ClientDao clientDao;
    private GymDao gymDao;
    private MonitoringDao monitoringDao;

    @Override
    public void processTest(Test test) {
        testDao.saveTest(test);
    }

    @Override
    public boolean isMindBodyOnline() {
        FPOpsConfig fpOpsConfig = getFpOpsConfigDao().findByName("MindBodyOnline");
        logger.info("isMindBodyOnline() {}", fpOpsConfig.getValue());
        return isOperationOnline(fpOpsConfig);
    }

    @Override
    public boolean isSyncMboClientsOn() {
        logger.info("SyncMboClientsOn: {}",isOperationOnline(getFpOpsConfigDao().findByName("SyncMboClientsOn")));
        return isOperationOnline(getFpOpsConfigDao().findByName("SyncMboClientsOn"));
    }

    @Override
    public boolean isSyncMboClientContractsOn() {
        return isOperationOnline(getFpOpsConfigDao().findByName("SyncMboClientContractsOn"));
    }

    @Override
    public boolean isSyncMboClientMembershipsOn() {
        return isOperationOnline(getFpOpsConfigDao().findByName("SyncMboClientMembershipsOn"));
    }

    @Override
    public boolean isSyncMboContractsOn() {
        return isOperationOnline(getFpOpsConfigDao().findByName("SyncMboContractsOn"));
    }

    @Override
    public boolean isSyncMboServicesOn() {
        return isOperationOnline(getFpOpsConfigDao().findByName("SyncMboServicesOn"));
    }

    @Override
    public boolean isSyncMboStaffOn() {
        return isOperationOnline(getFpOpsConfigDao().findByName("SyncMboStaffOn"));
    }

    @Override
    public boolean isProcessPDFs() {
        return isOperationOnline(getFpOpsConfigDao().findByName("ProcessPDFs"));
    }

    @Override
    public boolean isProcessEnrolmentsOn() {
        return isOperationOnline(fpOpsConfigDao.findByName("ProcessEnrolmentsOn"));
    }

    @Override
    public boolean isCancellationRemindersOn() {
        return isOperationOnline(getFpOpsConfigDao().findByName("CancellationRemindersOn"));
    }

    @Override
    public boolean isMembershipChangeRemindersOn() {
        return isOperationOnline(getFpOpsConfigDao().findByName("MembershipChangeRemindersOn"));
    }

    @Override
    public boolean isSyncMboProductsOn() {
        return isOperationOnline(getFpOpsConfigDao().findByName("SyncMboProductOn"));
    }

    @Override
    public boolean isParqRemindersOn() {
        return isOperationOnline(getFpOpsConfigDao().findByName("ParqRemindersOn"));
    }

    @Override
    public boolean isUpdatePtSessionCountOn() {
        return isOperationOnline(getFpOpsConfigDao().findByName("isUpdatePtSessionCountOn"));
    }

    @Override
    public boolean isNotifyUnservicedPtOn() {
        return isOperationOnline(getFpOpsConfigDao().findByName("isNotifyUnservicedPtOn"));
    }

    @Override
    public boolean isRefreshEncryptionKeysOn() {
        return isOperationOnline(getFpOpsConfigDao().findByName("isRefreshEncryptionKeysOn"));
    }


    @Override
    public boolean isDataWarehouseSyncOn() {
        return isOperationOnline(getFpOpsConfigDao().findByName("DataWarehouseSyncOn"));
    }

    private boolean isOperationOnline(FPOpsConfig fpOpsConfig) {
        if(null != fpOpsConfig && fpOpsConfig.getValue().equals("true")) {
            return true;
        } else {
            return false;
        }
    }



    @Async
    @Override
//    public CompletableFuture<String> testAsync(int i) {
    public void testAsync(int i) {
        logger.info("Service i = {} going to sleep",i);

        CompletableFuture<String> completableFuture = getMindBodyService().processTestAsync();

        CompletableFuture<String> result = completableFuture.thenApplyAsync(s -> s);

        try {
            logger.info("Back from processing i = {} -> {}",i,result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        logger.info("Service i = {} is awake",i);

//        return CompletableFuture.completedFuture(result);
    }

    @Override
    public AccessKeySiteCode findSiteCodeByLocationId(String UID, Integer locationId) {

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Submission User");
            return null;
        }

        return getFpOpsConfigDao().findSiteCodeByLocationId(locationId);
    }

    @Override
    public String cleanEncodedStaffName(String encodedStaff) {

        if (encodedStaff == null) return "";

        String[] split = encodedStaff.split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);

        Staff staff = getStaffDao().getStaffByMboId(Long.parseLong(split[0]), Long.parseLong(split[1]));

        return staff.getName();
    }

    private CompletableFuture<String> processTestAsync() {

        logger.info("processTestAsync()");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error("ERROR: Test Async was interrupted");
        }

        Random random = new Random();
        String result = "";
        if (random.nextInt(10) % 3 == 0) {
            result = MemberStatus.ERROR.getStatus();
        } else {
            result = MemberStatus.COMPLETE.getStatus();
        }
        return CompletableFuture.completedFuture(result);
    }

    @Async
    @Override
    public Test handleTestProcess(Test t) throws InterruptedException {

        Thread.sleep(1000);

//        logger.info("Inside handleTestProcess {}",t);
        t = setStatus(t);

        return getSaleDao().saveTest(t);
    }

    //    For testing async process
    private Test setStatus(Test t) throws InterruptedException {

        Thread.sleep(10000);

        Random random = new Random();
        if (random.nextInt(10) % 7 == 0) {
            t.setStatus(MemberStatus.ERROR.getStatus());
        } else {
            t.setStatus(MemberStatus.COMPLETE.getStatus());
        }
        return t;
    }


    @Override
    public List<PersonalTrainer> getPersonalTrainersByLocation(String locationId) {
        Iterable<PersonalTrainer> allPT = getStaffDao().getAllPersonalTrainer();
        List<PersonalTrainer> sortedPT = new ArrayList<>();

        locationId = GymName.convertLocationId(locationId);

        for (PersonalTrainer pt : allPT) {
            if (pt.getLocationId().contains(locationId)) {
                sortedPT.add(pt);
            }
        }
        return sortedPT;
    }

    @Override
    public List<StaffMember> getStaffsByLocation(String locationId) {
        Iterable<StaffMember> allStaff = getStaffDao().getAllStaffMember();
        List<StaffMember> sortedStaff = new ArrayList<>();

        locationId = GymName.convertLocationId(locationId);

//        This generates a list of staff members for the location with permission of Sales and Manager plus Administration and Jeremy if Darwin
        for (StaffMember s : allStaff) {
            if ((s.getLocationId().contains(locationId) || s.getPermissionLevel().equals(Constants.MBO_PERMISSION_ADMINISTRATION))
                    && (!s.getPermissionLevel().equals(Constants.MBO_PERMISSION_COOWNER) || s.getMboID() == 100000209)
                    && !s.getPermissionLevel().equals(Constants.MBO_PERMISSION_LOCATION)
                    && !s.getPermissionLevel().equals(Constants.MBO_PERMISSION_TESTER)
            ) {
                sortedStaff.add(s);
            }
        }
        return  sortedStaff;
    }

    @Override
    public StaffMember getStaffMemberByMboId(long mboId, long siteId) {
        return getStaffDao().getStaffMemberByMboId(mboId, siteId);
    }


//    TODO: NEEDS TO BE IMPLEMENTED WITH UiStaff
    @Override
    public Iterable<StaffMember> getAllStaffMembers() {
        return getStaffDao().getAllStaffMember();
    }


//    TODO: NEEDS TO BE IMPLEMENTED WITH Staff -> This is the be used when signing UP a new Staff Member via Front End
    @Override
    public StaffMember signUpStaffMemberFirebase(StaffLookUp staffLookUp) {
        StaffMember staffMember = getStaffDao().getStaffMemberByEmail(staffLookUp.getEmail());

        if (staffMember == null) {
            return null;
        }

        if (staffMember.getFirebaseId() == null) {
            staffMember.setFirebaseId(staffLookUp.getFireBaseId());
            getStaffDao().saveStaff(staffMember);
        }
        return staffMember;
    }

    @Override
    public List<MboContract> getContracts(String locationId) {

        logger.info("getContracts locationId: {}",locationId);

        Iterable<MboContract> allContracts = getSaleDao().getAllContracts();
        List<MboContract> contracts = new ArrayList<>();

        locationId = GymName.convertLocationId(locationId);

        for (MboContract c : allContracts) {
            if (c.getContractType() != null && c.getLocationId().contains(locationId)) {
                contracts.add(c);
            }
        }

        logger.info("returning {} contracts", contracts.size());

        return contracts;
    }

    @Override
    public List<MboContract> getAllContracts(String locationId) {
        Iterable<MboContract> allContracts = getSaleDao().getAllContracts();
        List<MboContract> contracts = new ArrayList<>();

        for (MboContract c : allContracts) {
            if (c.getLocationId().contains(locationId)) {
                contracts.add(c);
            }
        }

        logger.info("returning {} contracts", contracts.size());

        return contracts;
    }

    @Override
    public List<MboService> getAllServices(String locationId) {
        Iterable<MboService> allServices = getSaleDao().getAllServices();
        List<MboService> services = new ArrayList<>();

        for (MboService s : allServices) {
            if (s.getLocationId().contains(locationId)) {
                services.add(s);
            }
        }
        logger.info("returning {} services", services.size());
        return services;
    }

    @Override
    public List<MboProduct> getAllProducts(String locationId) {
        Iterable<MboProduct> allProducts = getSaleDao().getAllProducts();
        List<MboProduct> products = new ArrayList<>();

        for (MboProduct p : allProducts) {
            if (p.getLocationId().contains(locationId)) {
                products.add(p);
            }
        }
        logger.info("returning {} products", products.size());
        return products;
    }


    @Override
    public List<MboClient> searchForExistingClient(PrimaryDetails primaryDetails) {
//        return clientDao.searchForExistingClient(primaryDetails.getPhone(), primaryDetails.getFirstName(), primaryDetails.getLastName(), primaryDetails.getEmail());
        return getClientDao().searchForExistingClient(primaryDetails.getFirstName(), primaryDetails.getLastName(), primaryDetails.getEmail());
    }

    @Override
    public List<MboClient> searchForExistingClients(String UID, String firstName, String lastName, String email) {

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised User");
            return null;
        }

        return getClientDao().searchForExistingClient(firstName, lastName, email);
    }


    @Override
    public boolean isDuplicateUsername(String userName) {
        boolean isDuplicateUsername = false;

        Iterable<MboClient> clients = getClientDao().getAllClients();
        for (MboClient c : clients) {
            if (c.getEmail() != null && c.getEmail().equals(userName)) {
                isDuplicateUsername = true;
                break;
            }
        }
        return isDuplicateUsername;
    }


    @Override
    public MboAddNewClientResponse addNewClient(EnrolmentData enrolmentData, boolean isTestSubmission, boolean skipKey, boolean skipEmail) {
//        This is using version 6 or the MBO API
        MboAddNewClientRequest request = getMindBodyService().buildAddNewClient(enrolmentData, isTestSubmission, skipKey, skipEmail);
        return getMindBodyService().addMboClient(request);
    }


    @Override
    public MboUpdateClientResponse updateClient(EnrolmentData enrolmentData, Client mboClient, boolean isTestSubmission, boolean skipkey) {
        MboUpdateClientRequest request = getMindBodyService().buildUpdateClient(enrolmentData, mboClient, isTestSubmission, skipkey);
        return getMindBodyService().updateMboClient(request, getMindBodyService().isSydney(enrolmentData.getLocationId()));
    }

    @Override
    public MboAddClientDirectDebitInfoResponse addClientDirectDebit(EnrolmentData enrolmentData, Boolean isTestSubmission) {
        MboAddClientDirectDebitInfoRequest request = getMindBodyService().buildAddClientDirectDebitInfo(enrolmentData, isTestSubmission);
        return request == null ? null : getMindBodyService().addClientDirectDebitInfo(request, enrolmentData.getLocationId());
    }

    @Override
    public MboPurchaseContractResponse purcaseMboContract(MboContract contract, EnrolmentData data, Boolean isTestSubmission) {
        MboPurchaseContractRequest request = getMindBodyService().buildPurchaseContract(contract, data, isTestSubmission);
        if (request == null) return null;
//        this will return null if error
        return getMindBodyService().purchaseMboContract(request, Helpers.isSydney(data.getLocationId()));
    }

    @Override
    public MboShoppingCartResponse purchaseMboProduct(EnrolmentData enrolmentData, Boolean isTestSubmission) {
        MboShoppingCartRequest request = getMindBodyService().buildAccessKeyPOSCart(enrolmentData, isTestSubmission);
        return getMindBodyService().checkoutShoppingCart(request, getMindBodyService().isSydney(enrolmentData.getLocationId()));
    }

    @Override
    public MboApiResultEnrolmentData purchaseContract(MboContract contract, EnrolmentData data) {
        return getMindBodySaleService().purchaseContract(contract, data);
    }

    @Override
    public MboApiResultEnrolmentData purchaseTempMembership(String serviceId, EnrolmentData enrolmentData) {
        return getMindBodySaleService().purchaseTempMembership(serviceId, enrolmentData);
    }

    @Override
    public void handleTestSubmission(String submission) {
        TestSubmissionData data = TestSubmissionData.from(submission);
        getTestDao().saveTestSubmissionData(data);
    }

//    FIXME: TEST ME
    @Override
    public ArrayList<EnrolmentSubmissionError> getEnrolmentErrorSubmissions(String UID, String location) {
        //      Security check
        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Submission User");
            return null;
        }

        HashMap<String, ArrayList<EnrolmentSubmissionError>> map = getManualSubmissionsMap();
        return map.get(location);
    }

//    FIXME: TEST ME
    @Override
    public void refreshManualSubmissionsMap() {
//        ArrayList<EnrolmentSubmissionError> errors = new ArrayList<>();
        HashMap<String, ArrayList<EnrolmentSubmissionError>> manualSubmissions = new HashMap<>();

        ArrayList<EnrolmentSubmissionError> all = new ArrayList<>();
        ArrayList<EnrolmentSubmissionError> gw = new ArrayList<>();
        ArrayList<EnrolmentSubmissionError> bk = new ArrayList<>();
        ArrayList<EnrolmentSubmissionError> sh = new ArrayList<>();
        ArrayList<EnrolmentSubmissionError> nt = new ArrayList<>();
        ArrayList<EnrolmentSubmissionError> mk = new ArrayList<>();

        Iterable<EnrolmentSubmissionError> errorList = getSubmissionErrorDao().getAllErrorData();
        String[] strArr;
        String name;
        if (errorList != null) {
            for (EnrolmentSubmissionError e : errorList) {

                if (!e.getStatus().equals(MemberStatus.SUCCESS.getStatus())) {

                    if (e.getEnrolmentData() != null) {

                        if (e.getEnrolmentData().getStaffMember() != null) {
                            strArr = e.getEnrolmentData().getStaffMember().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
                            if (strArr.length > 0) {
                                name = getMindBodyService().getNameFromMboIdAndLocationId(strArr[0], e.getEnrolmentData().getLocationId());
                                e.getEnrolmentData().setStaffMember(name);
                            }
                        }

                        if (e.getEnrolmentData().getPersonalTrainer() != null) {
                            strArr = e.getEnrolmentData().getPersonalTrainer().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);

                            if (strArr.length > 0) {
                                name = getMindBodyService().getNameFromMboIdAndLocationId(strArr[0], e.getEnrolmentData().getLocationId());
                                e.getEnrolmentData().setPersonalTrainer(name);
                            }
                        }

//                    logger.info("Error Submission Id =  {} | Enrolment Data Id = {}", e.getId(), e.getEnrolmentData().getId());
                        //                if (location.equals("0") || e.getEnrolmentData().getLocationId().equals(location)) {
                        //                    if (e.getEnrolmentData().getStatus().equals(MemberStatus.PENDING.getStatus()) || e.getEnrolmentData().getStatus().equals(MemberStatus.DUPLICATE_KEY_DIFF_MEMBER.getStatus())) {
                        if (e.getStatus().equals(MemberStatus.PENDING.getStatus())
                                || e.getStatus().equals(MemberStatus.SAVED.getStatus())
                                || e.getStatus().equals(MemberStatus.ADD_DIRECT_DEBIT_INFO_ERROR.getStatus())
                        ) {
                            if (e.getEnrolmentData().getPaymentType().equals(Constants.SUBMISSION_PAYMENT_TYPE_CREDIT_CARD)
                                    || e.getEnrolmentData().getPaymentType().equals(Constants.SUBMISSION_PAYMENT_TYPE_CREDIT_CARD_2)) {
                                e.getEnrolmentData().getMemberCreditCard().setNumber(getEncryptionService().decrypt(e.getEnrolmentData().getMemberCreditCard().getNumber()));
                                e.getEnrolmentData().getMemberCreditCard().setVerificationCode(getEncryptionService().decrypt(e.getEnrolmentData().getMemberCreditCard().getVerificationCode()));
                            }

                            if (e.getEnrolmentData().getPaymentType().equals(Constants.SUBMISSION_PAYMENT_TYPE_BANK_ACCOUNT)
                                    || e.getEnrolmentData().getPaymentType().equals(Constants.SUBMISSION_PAYMENT_TYPE_BANK_ACCOUNT_2)
                                    || e.getEnrolmentData().getPaymentType().equals(Constants.SUBMISSION_PAYMENT_TYPE_DIRECT_DEBIT)) {
                                e.getEnrolmentData().getMemberBankDetail().setAccountNumber(getEncryptionService().decrypt(e.getEnrolmentData().getMemberBankDetail().getAccountNumber()));
                            }
                        }

                        //                errors.add(e);
                        if (!e.getStatus().equals(MemberStatus.SUCCESS.getStatus())) {

                            all.add(e);

                            switch (Integer.parseInt(e.getEnrolmentData().getLocationId())) {
                                case Constants.BUNKER_LOCATION_ID:
                                    bk.add(e);
                                    break;
                                case Constants.GATEWAY_LOCATION_ID:
                                    gw.add(e);
                                    break;
                                case Constants.MARRICKVILLE_LOCATION_ID:
                                    mk.add(e);
                                    break;
                                case Constants.NEWTOWN_LOCATION_ID:
                                    nt.add(e);
                                    break;
                                case Constants.SURRY_HILLS_LOCATION_ID:
                                    sh.add(e);
                                    break;
                            }
                        }
                    } else if (e.getCancellationData() != null) {
//                        logger.info("NEED TO HANDLE CANCELLATION DATA IN MANUAL SUBMISSIONS");
                        if (e.getStatus().equals(MemberStatus.MEMBERSHIP_TRANSFER.getStatus())) {
                            if (e.getCancellationData().getCreditCard() != null) {
                                e.getCancellationData().getCreditCard().setNumber(getEncryptionService().decrypt(e.getCancellationData().getCreditCard().getNumber()));
                                e.getCancellationData().getCreditCard().setVerificationCode(getEncryptionService().decrypt(e.getCancellationData().getCreditCard().getVerificationCode()));
                            }

                            if (e.getCancellationData().getMemberBankDetail() != null) {
                                e.getCancellationData().getMemberBankDetail().setAccountNumber(getEncryptionService().decrypt(e.getCancellationData().getMemberBankDetail().getAccountNumber()));
                            }
                        }

                        //                errors.add(e);
                        if (!e.getStatus().equals(MemberStatus.SUCCESS.getStatus())) {

                            all.add(e);

                            switch (Integer.parseInt(e.getCancellationData().getLocationId())) {
                                case Constants.BUNKER_LOCATION_ID:
                                    bk.add(e);
                                    break;
                                case Constants.GATEWAY_LOCATION_ID:
                                    gw.add(e);
                                    break;
                                case Constants.MARRICKVILLE_LOCATION_ID:
                                    mk.add(e);
                                    break;
                                case Constants.NEWTOWN_LOCATION_ID:
                                    nt.add(e);
                                    break;
                                case Constants.SURRY_HILLS_LOCATION_ID:
                                    sh.add(e);
                                    break;
                            }
                        }
                    }
                }
            }

            manualSubmissions.put("0", all);
            manualSubmissions.put(Integer.toString(Constants.BUNKER_LOCATION_ID), bk);
            manualSubmissions.put(Integer.toString(Constants.GATEWAY_LOCATION_ID), gw);
            manualSubmissions.put(Integer.toString(Constants.MARRICKVILLE_LOCATION_ID), mk);
            manualSubmissions.put(Integer.toString(Constants.NEWTOWN_LOCATION_ID), nt);
            manualSubmissions.put(Integer.toString(Constants.SURRY_HILLS_LOCATION_ID), sh);

            setManualSubmissionsMap(manualSubmissions);
        }
    }

    //    FIXME: update the clean staff and pt methods
    @Override
    public void completeEnrolmentErrorSubmissions(String UID, String enrolmentSubmissionErrorId) {
        logger.info("completeEnrolmentErrorSubmissions UID: {} enrolmentSubmissionErrorId: {}", UID, enrolmentSubmissionErrorId);
        //      Security check
        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Submission User");
            return;
        }
        EnrolmentSubmissionError submissionError = getSubmissionErrorDao().getSubmissionErrorById(Long.parseLong(enrolmentSubmissionErrorId));
        if (submissionError != null) {

            submissionError.setStatus(MemberStatus.SUCCESS.getStatus());
            submissionError.setSubmissionDate(Helpers.getDateNow(Constants.SYDNEY_SITE_ID));
            Staff staffMember = getStaffDao().getStaffByFirebaseId(UID);

            if (staffMember != null) {
                String s = staffMember.getFirstName() + " " + staffMember.getLastName();
                submissionError.setStaffMember(s);
            }

            if (submissionError.getEnrolmentData() != null) {

                submissionError.getEnrolmentData().setStatus(MemberStatus.SUCCESS.getStatus());
                submissionError.getEnrolmentData().setStaffMember(getMindBodyService().getNameFromMboIdAndLocationId(submissionError.getEnrolmentData().getStaffMember(), submissionError.getEnrolmentData().getLocationId()));
                submissionError.getEnrolmentData().setPersonalTrainer(getMindBodyService().getNameFromMboIdAndLocationId(submissionError.getEnrolmentData().getPersonalTrainer(), submissionError.getEnrolmentData().getLocationId()));
                submissionError.setEnrolmentData(getEncryptionService().decryptAndClean(submissionError.getEnrolmentData()));
                submissionError.setEnrolmentData(getEncryptionService().encryptDetails(submissionError.getEnrolmentData()));
                getMemberDao().updateEnrolmentData(submissionError.getEnrolmentData());
            }

            if (submissionError.getCancellationData() != null) {
                submissionError.getCancellationData().setStatus(MemberStatus.CANCELLATION_COMPLETE.getStatus());
                submissionError.getCancellationData().setCreditCard(getEncryptionService().decryptAndCleanMemberCreditCard(submissionError.getCancellationData().getCreditCard()));
                submissionError.getCancellationData().setCreditCard(getEncryptionService().encryptCMemberCreditCard(submissionError.getCancellationData().getCreditCard()));
                submissionError.getCancellationData().setUpdateDate(Helpers.getDateNow());
                getMemberDao().updateCancellationData(submissionError.getCancellationData());
            }

            getSubmissionErrorDao().saveSubmissionError(submissionError);
            refreshManualSubmissionsMap();
        }
    }

//    TODO: TEST ME
    @Override
    public Iterable<EnrolmentData> getEnrolments(String UID, String location) {

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Submission User");
            return null;
        }

        HashMap<String, Iterable<EnrolmentData>> submissions = getSubmissionsMap();
        return submissions.get(location);

//        location = GymName.convertLocationId(location);
//
//        logger.info("Inside getEnrolments {}",location);
//
//        Iterable<EnrolmentData> enrolmentData = memberDao.get100EnrolmentsByLocation(location);
//        return cleanAndSanitiseEnrolmentData(enrolmentData);
    }

//    TODO: TEST ME
    @Override
    public void refreshSubmissionsMap() {

        HashMap<String, Iterable<EnrolmentData>> submissions = new HashMap<>();

        Iterable<EnrolmentData> all = cleanAndSanitiseEnrolmentData(getMemberDao().get100EnrolmentsByLocation("0"));
        Iterable<EnrolmentData> bk = cleanAndSanitiseEnrolmentData(getMemberDao().get100EnrolmentsByLocation(Integer.toString(Constants.BUNKER_LOCATION_ID)));
        Iterable<EnrolmentData> gw = cleanAndSanitiseEnrolmentData(getMemberDao().get100EnrolmentsByLocation(Integer.toString(Constants.GATEWAY_LOCATION_ID)));
        Iterable<EnrolmentData> mk = cleanAndSanitiseEnrolmentData(getMemberDao().get100EnrolmentsByLocation(Integer.toString(Constants.MARRICKVILLE_LOCATION_ID)));
        Iterable<EnrolmentData> nt = cleanAndSanitiseEnrolmentData(getMemberDao().get100EnrolmentsByLocation(Integer.toString(Constants.NEWTOWN_LOCATION_ID)));
        Iterable<EnrolmentData> sh = cleanAndSanitiseEnrolmentData(getMemberDao().get100EnrolmentsByLocation(Integer.toString(Constants.SURRY_HILLS_LOCATION_ID)));

        submissions.put("0", all);
        submissions.put(Integer.toString(Constants.BUNKER_LOCATION_ID), bk);
        submissions.put(Integer.toString(Constants.GATEWAY_LOCATION_ID), gw);
        submissions.put(Integer.toString(Constants.MARRICKVILLE_LOCATION_ID), mk);
        submissions.put(Integer.toString(Constants.NEWTOWN_LOCATION_ID), nt);
        submissions.put(Integer.toString(Constants.SURRY_HILLS_LOCATION_ID), sh);

        setSubmissionsMap(submissions);

    }

    @Override
    public Iterable<EnrolmentData> searchEnrolments(String UID, EnrolmentLookUp enrolmentLookUp) {

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Submission User");
            return null;
        }



        Iterable<EnrolmentData> enrolmentData = getMemberDao().searchEnrolmentData(enrolmentLookUp);
        return cleanAndSanitiseEnrolmentData(enrolmentData);
    }

    @Override
    public List<EnrolmentData> findEnrolments(String UID, FindEnrolment findEnrolment) {
        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Submission User");
            return null;
        }

        List<EnrolmentData> enrolmentData = getMemberDao().findEnrolments(findEnrolment);
        return cleanAndSanitiseEnrolmentData(enrolmentData);
    }

    @Override
    public EnrolmentData getPhoneEnrolment(String UID, String fsFormId, String fsUniqueId) {

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Submission User");
            return null;
        }

        return getMemberDao().getEnrolmentDataByFormstackIds(fsFormId, fsUniqueId);
    }

    @Override
    public TermsAndConditions getTermsAndConditionsHtml() {
        return getMemberDao().getTermsAndConditionsHtml();
    }


    @Override
    public ArrayList<UIGym> updateGym(String UID, UpdateGym updateGym) {

        logger.info("Inside updateGym()");

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Staff: updateGym()");
            return null;
        }

        try {
            Gym gym = getGymDao().getGymByLocationId(Integer.parseInt(updateGym.getLocationId()));

            if (gym != null) {

                String s = updateGym.getClubManagerMboId().isEmpty() ? null : updateGym.getClubManagerMboId();
                gym.setClubManagerId(s);
//                logger.info("Saved club manager {} at location {}", updateGym.getClubManagerMboId(), gym.getName());

                s = updateGym.getPersonalTrainerManagerMboId().isEmpty() ? null : updateGym.getPersonalTrainerManagerMboId();
                gym.setPersonalTrainingManagerId(s);
//                logger.info("Saved PT manager {} at location {}", updateGym.getPersonalTrainerManagerMboId(), gym.getName());

                s = updateGym.getGroupFitnessManagerMboId().isEmpty() ? null : updateGym.getGroupFitnessManagerMboId();
                gym.setGroupFitnessManagerId(s);
//                logger.info("Saved GF manager {} at location {}", updateGym.getGroupFitnessManagerMboId(), gym.getName());

                getGymDao().save(gym);

                refreshGyms();
            }
//            TODO: Test This
//            return uiGyms;
            return getUiGyms();

        } catch (Exception e) {
            logger.error("Error updating gym at {}",e.getMessage());
            return null;
        }
    }


    @Override
    public com.fitnessplayground.dao.domain.Staff getStaffByFirebaseId(StaffLookUp staffLookUp) {
        return getStaffDao().getStaffByFirebaseId(staffLookUp.getFireBaseId());
    }

    @Override
    public ArrayList<UIGym> getAllUiGyms(String UID) {

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Staff: getAllUiGyms()");
            return null;
        }

//        if (uiGyms == null || uiGyms.isEmpty()) {
        if (getUiGyms() == null || getUiGyms().isEmpty()) {
            refreshGyms();
        }

        try {
//            return uiGyms;
            return getUiGyms();
        } catch (Exception e) {
            logger.error("Error fetching all UIGym {}",e.getMessage());
            return null;
        }
    }

    @Override
    public Iterable<Gym> getGyms() {
        return getGymDao().findAllGyms();
    }

    @Override
    public Gym getGymsByLocation(String locationId) {
        if (locationId == null) return null;
        return getGymDao().getGymByLocationId(Integer.parseInt(locationId));
    }

    @Override
    public ArrayList<UIStaff> getAllUIStaff(String UID) {

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Staff: getAllUIStaff()");
            return null;
        }

//        if (uiStaffs == null) {
        if (getUiStaffs() == null) {
            refreshUIStaffs();
        }
        try {
//            return uiStaffs;
            return getUiStaffs();
        } catch (Exception e) {
            logger.error("Error fetching all UIStaff");
            return null;
        }
    }


    @Override
    public Iterable<Staff> getAllStaff(String UID) {
        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Staff: getAllUIStaff()");
            return null;
        }

        return getStaffDao().getAllStaff();
    }


    @Override
    public void updateStaff(String UID, UpdateStaff updateStaff) {

        logger.info("Inside updateStaff()");

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised User: updateStaff()");
            return;
        }

        logger.info(updateStaff.toString());

        try {
            String[] mboIdAndSiteId = Helpers.splitStaffMboIdAndSiteId(updateStaff.getMboIdAndSiteId());
            com.fitnessplayground.dao.domain.Staff staff = getStaffDao().getStaffByMboId(Long.parseLong(mboIdAndSiteId[0]), Long.parseLong(mboIdAndSiteId[1]));

            if (staff != null) {
//                String[] locIds = updateStaff.getLocationIds().split(",");

                List<Gym_MembershipConsultant> allMcs = Lists.newArrayList(getGymDao().findAllUIMembershipConsultants());
                List<Gym_MembershipConsultant> mcs = allMcs.stream().filter(mc -> mc.getMboIdAndSiteId().equals(Helpers.staffToMboIdAndSiteId(staff))).collect(Collectors.toList());

                List<Gym_PersonalTrainer> allPts = Lists.newArrayList(getGymDao().findAllUIPersonalTrainers());
                List<Gym_PersonalTrainer> pts = allPts.stream().filter(pt -> pt.getMboIdAndSiteId().equals(Helpers.staffToMboIdAndSiteId(staff))).collect(Collectors.toList());

//                FIXME: I should do this last. Delete the table and rebuild it with a fresh build entity in memory
    //           flush list of existing entries
                if (!pts.isEmpty()) {
                    for (Gym_PersonalTrainer pt : pts) {
                        getGymDao().delete(pt);
                    }
                }

                if (!mcs.isEmpty()) {
                    for (Gym_MembershipConsultant mc : mcs) {
                        getGymDao().delete(mc);
                    }
                }

                if (updateStaff.getLocationIds() != null && !updateStaff.getLocationIds().isEmpty()) {
                    String tempLocationIds = "";
                    for (String l : updateStaff.getLocationIds()) {
                        Integer loc = Integer.parseInt(l.trim());
                        tempLocationIds += l+",";
//                    String[] roles = updateStaff.getRole() != null ? updateStaff.getRole().split(",") : null;
                        if (updateStaff.getRole() != null && updateStaff.getRole().length > 0) {
                            String tempRole = "";
                            for (String r : updateStaff.getRole()) {
                                r = r.trim();

//                                FIXME: This is where the assignment before definition is happening
//                                FIXME: Should this be looking at the updateStaff entity, not the Staff entity ???
//                                FIXME: What about Exercise Physiologist ???

                                //  add to list
                                if (r.equals(Constants.ROLE_PERSONAL_TRAINER)) {
//                                    FIXME - I think this is setting the role to null in the the UI entities
//                                    Gym_PersonalTrainer pt = new Gym_PersonalTrainer(loc, Helpers.staffToMboIdAndSiteId(staff), staff.getName(), staff.getRole());
                                    Gym_PersonalTrainer pt = new Gym_PersonalTrainer(loc, Helpers.staffToMboIdAndSiteId(staff), staff.getName(), Arrays.toString(updateStaff.getRole()));
                                    getGymDao().save(pt);
                                }

                                if (r.equals(Constants.ROLE_MEMBERSHIP_CONSULTANT)) {
//                                    FIXME - I think this is setting the role to null in the the UI entities
//                                    Gym_MembershipConsultant mc = new Gym_MembershipConsultant(loc, Helpers.staffToMboIdAndSiteId(staff), staff.getName(), staff.getRole());
                                    Gym_MembershipConsultant mc = new Gym_MembershipConsultant(loc, Helpers.staffToMboIdAndSiteId(staff), staff.getName(), Arrays.toString(updateStaff.getRole()));
                                    getGymDao().save(mc);
                                }
                                tempRole += r+",";
                            }
                            staff.setRole(Helpers.removeLastCharacter(tempRole));
                        }
                    }
                    staff.setLocationId(Helpers.removeLastCharacter(tempLocationIds));
                }

                staff.setPermissionLevel(updateStaff.getPermissionLevel());
                getStaffDao().saveStaff(staff);
                refreshGyms();
            }

        } catch (Exception e) {
            logger.error("Error updating staff {}",e.getMessage());
        }
    }

    @Override
    public UIGym getGymByLocation(String UID, String location) {

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Staff: updateGym()");
            return null;
        }

        return getUIGymByLocation(location);
    }

    private UIGym getUIGymByLocation(String location) {
        UIGym uiGym = null;

        if (uiGyms == null) {
            refreshGyms();
        }

        try {
            for (UIGym g : uiGyms) {
                if (g.getLocationId() == Integer.parseInt(location)) {
                    uiGym = g;
                }
            }
        } catch (Exception e) {
            logger.error("Error fetching UIGym {}",e.getMessage());
            return null;
        }
        return uiGym;
    }


//    FIXME: work on an independant entity (UIGym) then set the updated UIGym when completed
    @Override
    public void refreshGyms() {

        ArrayList<Gym_MembershipConsultant> temp_membershipConsultants = Lists.newArrayList(getGymDao().findAllUIMembershipConsultants());
        ArrayList<Gym_PersonalTrainer> temp_personalTrainers = Lists.newArrayList(getGymDao().findAllUIPersonalTrainers());
        ArrayList<UIGym> temp_uiGyms = new ArrayList<>();
        com.fitnessplayground.dao.domain.Staff staff;

//        if (uiGyms == null) {
//            uiGyms = new ArrayList<>();
//        } else {
//            uiGyms.clear();
//        }
        String[] mboIdAndSiteId;

        Iterable<Gym> gyms = getGymDao().findAllGyms();
        for (Gym g : gyms) {
            UIGym uiGym = new UIGym();
            uiGym.setName(g.getName());
            uiGym.setLocationId(g.getLocationId());
            uiGym.setSiteId(g.getSiteId());

            if (g.getClubManagerId() != null && !g.getClubManagerId().isEmpty()) {
                mboIdAndSiteId = Helpers.splitStaffMboIdAndSiteId(g.getClubManagerId());
                staff = getStaffDao().getStaffByMboId(Long.parseLong(mboIdAndSiteId[0]), Long.parseLong(mboIdAndSiteId[1]));

                if (staff != null) {
                    UIStaff uiStaff = new UIStaff(staff.getName(), Helpers.staffToMboIdAndSiteId(staff), staff.getRole());
                    uiGym.setClubManager(uiStaff);
                }
            }

            if (g.getPersonalTrainingManagerId() != null && !g.getPersonalTrainingManagerId().isEmpty()) {
                mboIdAndSiteId = Helpers.splitStaffMboIdAndSiteId(g.getPersonalTrainingManagerId());
                staff = getStaffDao().getStaffByMboId(Long.parseLong(mboIdAndSiteId[0]), Long.parseLong(mboIdAndSiteId[1]));

                if (staff != null) {
                    UIStaff uiStaff = new UIStaff(staff.getName(), Helpers.staffToMboIdAndSiteId(staff), staff.getRole());
                    uiGym.setPersonalTrainingManager(uiStaff);
                }
            }

            if (g.getGroupFitnessManagerId() != null && !g.getGroupFitnessManagerId().isEmpty()) {
                mboIdAndSiteId = Helpers.splitStaffMboIdAndSiteId(g.getGroupFitnessManagerId());
                staff = getStaffDao().getStaffByMboId(Long.parseLong(mboIdAndSiteId[0]), Long.parseLong(mboIdAndSiteId[1]));

                if (staff != null) {
                    UIStaff uiStaff = new UIStaff(staff.getName(), Helpers.staffToMboIdAndSiteId(staff), staff.getRole());
                    uiGym.setGroupFitnessManager(uiStaff);
                }
            }

            List<UIStaff> mcs = new ArrayList<>();
            for (Gym_MembershipConsultant mc : temp_membershipConsultants.stream().filter(mc -> mc.getLocationId().equals(g.getLocationId())).collect(Collectors.toList())) {
                UIStaff s = new UIStaff(mc.getName(), mc.getMboIdAndSiteId(), mc.getRole());
                mcs.add(s);
            }

            Collections.sort(mcs, UIStaff.UIStaffComparator);
            uiGym.setMembershipConsultants(mcs);

            List<UIStaff> pts = new ArrayList<>();
            for (Gym_PersonalTrainer pt : temp_personalTrainers.stream().filter(pt -> pt.getLocationId().equals(g.getLocationId())).collect(Collectors.toList())) {
                UIStaff s = new UIStaff(pt.getName(), pt.getMboIdAndSiteId(), pt.getRole());
                pts.add(s);
            }

            Collections.sort(pts, UIStaff.UIStaffComparator);
            uiGym.setPersonalTrainers(pts);

            temp_uiGyms.add(uiGym);
        }
        Collections.sort(temp_uiGyms, UIGym.UIGymComparator);
        setUiGyms(temp_uiGyms);
        setGym_membershipConsultants(temp_membershipConsultants);
        setGym_personalTrainers(temp_personalTrainers);
    }

    @Override
    public void refreshUIStaffs() {
        Iterable<Staff> staff = getStaffDao().getAllStaff();

//        if (uiStaffs == null) {
//            uiStaffs = new ArrayList<>();
//        } else {
//            uiStaffs.clear();
//        }
//
//        for (Staff s : staff) {
//            UIStaff uiStaff = new UIStaff();
//            uiStaff.setMboIdAndSiteId(Long.parseLong(s.getMboId()), Long.parseLong(s.getSiteId()));
//            uiStaff.setName(s.getName());
//            uiStaff.setRole(s.getRole());
//            uiStaffs.add(uiStaff);
//        }
//
//        Collections.sort(uiStaffs, UIStaff.UIStaffComparator);

        ArrayList<UIStaff> temp_uiStaffs = new ArrayList<>();

        for (Staff s : staff) {
            UIStaff uiStaff = new UIStaff();
            uiStaff.setMboIdAndSiteId(Long.parseLong(s.getMboId()), Long.parseLong(s.getSiteId()));
            uiStaff.setName(s.getName());
            uiStaff.setRole(s.getRole());
            temp_uiStaffs.add(uiStaff);
        }

        Collections.sort(temp_uiStaffs, UIStaff.UIStaffComparator);

        setUiStaffs(temp_uiStaffs);
    }

// FIXME: this can use the staffMap for better performance
    @Override
    public com.fitnessplayground.dao.domain.Staff getStaffByMboIdAndSiteId(String UID, Long mboId, Long siteId) {

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Staff: getStaffByMboIdAndSiteId()");
            return null;
        }

        return getStaffDao().getStaffByMboId(mboId, siteId);
    }

    // FIXME: this can use the staffMap for better performance
    @Override
    public com.fitnessplayground.dao.domain.Staff getStaffByMboIdAndSiteId(String UID, String mboIdAndSiteId) {

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Staff: getStaffByMboIdAndSiteId()");
            return null;
        }

        String[] mboIdAndSiteIdArr = Helpers.splitStaffMboIdAndSiteId(mboIdAndSiteId);

        return getStaffDao().getStaffByMboId(Long.parseLong(mboIdAndSiteIdArr[0]), Long.parseLong(mboIdAndSiteIdArr[1]));
    }

    @Override
    public EnrolmentDataDocument getDataDocByFindMember(FindEnrolment findEnrolment) {

        EnrolmentData enrolmentData = getMemberDao().getEnrolmentDataByFindEnrolment(findEnrolment);


        if (enrolmentData != null) {

            enrolmentData = getEncryptionService().decryptAndClean(enrolmentData);

            EnrolmentDataDocument dataDocument = EnrolmentDataDocument.from(enrolmentData);

            UIGym gym = getUIGymByLocation(enrolmentData.getLocationId());

            dataDocument.setClubManager(gym.getClubManager().getName());

            String cleanedNumber = Helpers.cleanCreditCard(dataDocument.getBdAccountNumber());
            dataDocument.setBdAccountNumber(cleanedNumber);

            cleanedNumber = Helpers.cleanCreditCard(dataDocument.getCcNumber());
            dataDocument.setCcNumber(cleanedNumber);

            dataDocument.setStaffMember(getMindBodyService().getStaffName(enrolmentData.getStaffMember()));
            dataDocument.setPersonalTrainer(getMindBodyService().getStaffName(enrolmentData.getPersonalTrainer()));

            String termsHtml = getMemberDao().getTermsAndConditionsHtml().getTermsAndConditions();
            dataDocument.setTermAndConditions(termsHtml);

            dataDocument.setMBO_SITE_ID(SITE_ID);

            return dataDocument;

        } else {
            return null;
        }

    }

    @Async
    @Override
    public PtFeedbackData handleFeedbackSubmission(PtFeedbackData ptFeedbackData) {

        try {
            PtTracker ptTracker;

            if (ptFeedbackData.getPtTrackerId() != null) {
                ptTracker = getMemberDao().getPtTrackerById(Long.parseLong(ptFeedbackData.getPtTrackerId()));

                if (ptTracker != null) {

                    if (ptFeedbackData.getFeedbackType().equals(Constants.FEEDBACK_TYPE_PT_EARLY_FEEDBACK)) {
                        ptTracker.setCommunicationsStatus(CommunicationsStatus.PT_EARLY_FEEDBACK_RECEIVED.getStatus());
                    }

                    if (ptFeedbackData.getFeedbackType().equals(Constants.FEEDBACK_TYPE_PT_FEEDBACK)) {
                        ptTracker.setCommunicationsStatus(CommunicationsStatus.PT_FEEDBACK_RECEIVED.getStatus());
                    }

                    ptTracker.setCommunicationsUpdateDate(Helpers.getDateNow());
                    getMemberDao().updatePtTracker(ptTracker);
                }
            }
        } catch (Exception ex) {
            logger.error("Error finding PtTracker for PtFeedback: Form: {} - Form Unique Id: {} - {}", ptFeedbackData.getFs_formId(), ptFeedbackData.getFs_uniqueId(),ex.getMessage());
        }

        return getMemberDao().savePtFeedbackData(ptFeedbackData);
    }

    @Override
    public PtFeedbackReportData getPtFeedbackReportData() {

        PtFeedbackReportData data = new PtFeedbackReportData();
        data.setPtCancellationData(getMemberDao().getPtCancellationData());
        data.setPtFeedbackData(getMemberDao().getPtFeedbackData());

        return data;
    }

    @Async
    @Override
    public void handleManualSubmission(ManualSubmission manualSubmission) {

        EnrolmentSubmissionError submissionError = new EnrolmentSubmissionError();

        if (manualSubmission.getCancellationDataId() != null) {
            CancellationData cancellationData = getMemberDao().getCancellationDataById(manualSubmission.getCancellationDataId());
            submissionError.setCancellationData(cancellationData);
        }

        if (manualSubmission.getFpCoachEnrolmentDataId() != null) {
            FpCoachEnrolmentData fpCoachEnrolmentData = getMemberDao().getFpCoachEnrolmentDataById(manualSubmission.getFpCoachEnrolmentDataId());
            submissionError.setFpCoachEnrolmentData(fpCoachEnrolmentData);
        }

        if (manualSubmission.getEnrolmentDataId() != null) {
            EnrolmentData enrolmentData = getMemberDao().getEnrolmentDataById(manualSubmission.getEnrolmentDataId());
            submissionError.setEnrolmentData(enrolmentData);
        }

        submissionError.setErrorCode(manualSubmission.getErrorCode());
        submissionError.setErrorDetails(manualSubmission.getErrorDetails());
        submissionError.setStatus(manualSubmission.getStatus());
        submissionError.setMboSubmissionCount(manualSubmission.getMboSubmissionCount());

        getSubmissionErrorDao().saveSubmissionError(submissionError);
    }


    // This is the new version with MBO v6
    //            FIXME: Async - Testing
    @Async
    @Override
    public void handleEnrolmentSubmission(EnrolmentFormSubmission enrolmentFormSubmission) {

        logger.info("Inside handleEnrolmentSubmission(): {}",enrolmentFormSubmission.getLegalDetails().getAccessKeyNumber());

//      Security check
        if (!validateStaffId(enrolmentFormSubmission.getUID())) {
            logger.error("Unauthorised Submission User");
            return;
        }

        Instant start = Helpers.getInstant();
        Instant finish;
        Long elapsedTime;

//        FIXME: Async - Testing
        CompletableFuture<EnrolmentData> enrolmentDataCompletableFuture = processEnrolmentSubmission(enrolmentFormSubmission);
//        EnrolmentData enrolmentData = processEnrolmentSubmission_v2(enrolmentFormSubmission);
//        FIXME: Async - Testing
        CompletableFuture<EnrolmentData> result = enrolmentDataCompletableFuture.thenApplyAsync(enrolmentData -> enrolmentData);

//        FIXME: Async - Testing
        try {

            EnrolmentData enrolmentData = result.get();

//            I could fire the pdf writer here ???

            finish = Helpers.getInstant();
            elapsedTime = Helpers.getTimeElapsed(start, finish);
            PerformanceMonitor performanceMonitor = new PerformanceMonitor(enrolmentData.getStatus(),
                    elapsedTime != null ? Long.toString(elapsedTime) : null,
                    Constants.ENTITY_TYPE_ENROLMENT_DATA,
                    Long.toString(enrolmentData.getId()),
                    Constants.ACTION_TYPE_PROCESS_ENROLMENT);

            getMonitoringDao().save(performanceMonitor);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }


    //            FIXME: Async - Testing
    private CompletableFuture<EnrolmentData> processEnrolmentSubmission(EnrolmentFormSubmission enrolmentFormSubmission) {
//    private EnrolmentData processEnrolmentSubmission_v2(EnrolmentFormSubmission enrolmentFormSubmission) {

        boolean isTestSubmission = false;
        boolean isAccessKeyUploaded = true;
        String tempAccessKey;
        boolean isComplete = true;
        String status;

//        Test Submission Check
        Staff user = getStaffDao().getStaffByFirebaseId(enrolmentFormSubmission.getUID());

        logger.info("User: {} Permission Level: {}", user.getName(), user.getPermissionLevel());

        if (user.getPermissionLevel().equals(Constants.MBO_PERMISSION_TESTER)) {

            isTestSubmission = true;

            enrolmentFormSubmission.getLegalDetails().setAccessKeyNumber(TestEntities.getTestMemberId());
//            enrolmentFormSubmission.getLegalDetails().setAccessKeyNumber("TestingKey");

            logger.info("Test Submission - Access Key Number: {} - isTestSubmission = {}", enrolmentFormSubmission.getLegalDetails().getAccessKeyNumber(), isTestSubmission);
        }

//        Convert Submission into EnrolmentData
        long siteId = enrolmentFormSubmission.getLocationId().contains("4") ? Constants.DARWIN_SITE_ID : Constants.SYDNEY_SITE_ID;

//      Convert submission into enrolmentData
        if (enrolmentFormSubmission.getLocationId() != null) {
//            This returns the absolute value of the locationId
            enrolmentFormSubmission.setLocationId(GymName.convertLocationId(enrolmentFormSubmission.getLocationId()));
        } else {
            enrolmentFormSubmission.setLocationId("0");
        }

//        Convert Submission Data to EnrolmentData
        EnrolmentData enrolmentData = EnrolmentData.from(enrolmentFormSubmission);

//        Define isSydney
        boolean isSydney = Helpers.isSydney(enrolmentData.getLocationId());
//        logger.info("isSydney Enrolment = {}",isSydney);

//        Handle access key site code
        // TODO: 18/06/20 - get this from the database not config file
        enrolmentData = handleAccessKeySiteCode(enrolmentData);

//        Encrypt Data
        enrolmentData = getEncryptionService().encryptDetails(enrolmentData);

        enrolmentData.setUpdateDate(Helpers.getDateNow(siteId));

//        Encodes membership contracts for processing
        enrolmentData = setContractNames(enrolmentData);

//        Save to Database
        enrolmentData.setStatus(MemberStatus.SAVED.getStatus());
        enrolmentData = getMemberDao().saveEnrolmentData(enrolmentData);

//        AddClient
//        decrypt
        enrolmentData = getEncryptionService().decryptDetails(enrolmentData);
//
//        Check duplicate Key
        boolean isDuplicateKey = false;
//        Search MBO API for existing Client by accessKey
        List<Client> existingClient = getMindBodyService().findMboClientByClientId(enrolmentData.getAccessKeyNumber());
        if (existingClient != null || !existingClient.isEmpty()) {
            for (Client c : existingClient) {
                if (c.getAccessKeyNumber().equals(enrolmentData.getAccessKeyNumber())) {
                    isDuplicateKey = true;
                    isComplete = false;

//                    FIXME: there is no ID to upload the contracts with
                }
            }
        }
                
//        Check Duplicate Username
        boolean isDuplicateUsername = false;
//        Search MBO API for existing Client by email (username)
        existingClient = getMindBodyService().searchMboClient(enrolmentData.getEmail());
        Client mboClient = null;

        if (existingClient != null && !existingClient.isEmpty()) {

            for (Client c : existingClient) {

                if (c.getFirstName().equals(enrolmentData.getFirstName()) && c.getLastName().equals(enrolmentData.getLastName()) && c.getEmail().equals(enrolmentData.getEmail())) {
                    mboClient = c;
                }

//                if (c.getEmail().equals(enrolmentData.getEmail())) {
//                    isDuplicateUsername = true;
//                    isComplete = false;
//                }
            }
        }

//        FIXME: if is a duplicate username/accesskey this is where you could update the client in place of adding the client
        logger.info("isDuplicateKey = {} : {} | isDuplicateUserName = {} : {}",isDuplicateKey, enrolmentData.getAccessKeyNumber(), isDuplicateUsername, enrolmentData.getEmail());

        if (mboClient == null) {

//        Hit MBO API to Add Client
            MboAddNewClientResponse addNewClientResponse = addNewClient(enrolmentData, isTestSubmission, isDuplicateKey, isDuplicateUsername);

            if (addNewClientResponse == null) {
                logger.error("Error adding new client to MBO: {}", enrolmentData.getId());
                enrolmentData.setStatus(MemberStatus.PENDING.getStatus());
//          Save to Enrolment Errors
                saveEnrolmentErrorSubmission(enrolmentData, 1, null, null, null, MemberStatus.PENDING);
                enrolmentData = getEncryptionService().encryptDetails(enrolmentData);
                enrolmentData = getMemberDao().saveEnrolmentData(enrolmentData);

//            FIXME: Async - Testing
                return CompletableFuture.completedFuture(enrolmentData);
//            return enrolmentData;
            } else {
                mboClient = addNewClientResponse.getClient();
            }


        } else {
//        Hit MBO API to Update Client
            MboUpdateClientResponse updateClientResponse = updateClient(enrolmentData, mboClient, isTestSubmission, true);

            if (updateClientResponse == null) {
                logger.error("Error updating client to MBO: {}", enrolmentData.getId());
                enrolmentData.setStatus(MemberStatus.PENDING.getStatus());
//          Save to Enrolment Errors
                saveEnrolmentErrorSubmission(enrolmentData, 1, null, null, null, MemberStatus.PENDING);
                enrolmentData = getEncryptionService().encryptDetails(enrolmentData);
                enrolmentData = getMemberDao().saveEnrolmentData(enrolmentData);

                //            FIXME: Async - Testing
                return CompletableFuture.completedFuture(enrolmentData);
//            return enrolmentData;
            } else {
                mboClient = updateClientResponse.getClient();
            }

        }

//        Store access key number in a temp variable in case Access Key Upload Failed
        tempAccessKey = enrolmentData.getAccessKeyNumber();
        if (!isTestSubmission) {
            enrolmentData.setAccessKeyNumber(mboClient.getAccessKeyNumber());
        }


        logger.info("Temp Access Key: {} | addClient Access Key: {} | addClient UniqueId: {}", tempAccessKey, mboClient.getAccessKeyNumber(), mboClient.getUniqueId());

//        AddClientDirectDebitInfo
//        IF Bank Account
        if (enrolmentData.getPaymentType().equals(Constants.SUBMISSION_PAYMENT_TYPE_BANK_ACCOUNT)) {
            MboAddClientDirectDebitInfoResponse addClientDirectDebitInfoResponse = addClientDirectDebit(enrolmentData, isTestSubmission);
//            logger.info("Add DD Info response {}", addClientDirectDebitInfoResponse.toString());
            if (addClientDirectDebitInfoResponse == null) {
                logger.error("Error adding new client direct debit info to MBO: enrolmentDataId: {}",enrolmentData.getId());
                enrolmentData.setStatus(MemberStatus.ADD_DIRECT_DEBIT_INFO_ERROR.getStatus());
    //          Save to Enrolment Errors
                saveEnrolmentErrorSubmission(enrolmentData, 1, null, null, null,  MemberStatus.ADD_DIRECT_DEBIT_INFO_ERROR);
                enrolmentData = getEncryptionService().encryptDetails(enrolmentData);
                enrolmentData = getMemberDao().saveEnrolmentData(enrolmentData);

                //            FIXME: Async - Testing
                return CompletableFuture.completedFuture(enrolmentData);
//                return enrolmentData;
            }
        }

        status = MemberStatus.ADDED.getStatus();

//        Purchase Contracts
        MboContract contract;
        MboPurchaseContractResponse mboPurchaseContractResponse;

//        First N Days Contract
        if (enrolmentData.getDaysFree() != null && enrolmentData.getDaysFree().length() > 0) {

            Integer contractId;

            switch (enrolmentData.getDaysFree()) {
                case Constants.COUPON_CODE_7_DAYS:
                    contractId = isSydney ? Constants.COUPON_CODE_7_DAYS_ID : Constants.GW_COUPON_CODE_7_DAYS_ID;
                    contract = getSaleDao().getContractByMboIdAndLocation(contractId, enrolmentData.getLocationId());
                    break;
                case Constants.COUPON_CODE_14_DAYS:
                    contractId = isSydney ? Constants.COUPON_CODE_14_DAYS_ID : Constants.GW_COUPON_CODE_14_DAYS_ID;
                    contract = getSaleDao().getContractByMboIdAndLocation(contractId, enrolmentData.getLocationId());
                    break;
                case Constants.COUPON_CODE_21_DAYS:
                    contractId = Constants.COUPON_CODE_21_DAYS_ID ;
                    contract = getSaleDao().getContractByMboIdAndLocation(contractId, enrolmentData.getLocationId());
                    break;
                case Constants.COUPON_CODE_30_DAYS:
                    contractId = isSydney ? Constants.COUPON_CODE_30_DAYS_ID : Constants.GW_COUPON_CODE_30_DAYS_ID;
                    contract = getSaleDao().getContractByMboIdAndLocation(contractId, enrolmentData.getLocationId());
                    break;
                case Constants.COUPON_CODE_X_DAYS:
                    contractId = isSydney ? Constants.COUPON_CODE_30_DAYS_ID : Constants.GW_COUPON_CODE_30_DAYS_ID;
                    contract = getSaleDao().getContractByMboIdAndLocation(contractId, enrolmentData.getLocationId());
                    break;
                default:
                    contract = null;

            }

            if (contract != null) {
                logger.info("Trigger Free N Days Contract {} :: {}", contract.getName(), contract.getMboId());
                mboPurchaseContractResponse = purcaseMboContract(contract, enrolmentData, isTestSubmission);
                logger.info("Free N Days Purchase Contract return{}", mboPurchaseContractResponse != null ? mboPurchaseContractResponse.toString() : "NULL");
                if (mboPurchaseContractResponse == null) {
//                   Save Error Submission -> Client is inactive!
                    saveEnrolmentErrorSubmission(enrolmentData,1, null, contract, null, MemberStatus.PURCHASE_CONTRACT_ERROR);
                    isComplete = false;
                    status = MemberStatus.PURCHASE_CONTRACT_ERROR.getStatus();
                }
            }
        }

//        Get contracts
        String[] contractIds = enrolmentData.getMemberContracts().split(",");
//        This excludes First N Days Contracts and TwoFreePT contracts
        for (String contractId : contractIds) {

            contract = getSaleDao().getContractByMboIdAndLocation(Integer.parseInt(contractId), enrolmentData.getLocationId());
//            logger.info("ContractId: {}", contract.toString());

//            ACCESS_KEY
            if (Integer.parseInt(contractId) == Constants.ACCESS_KEY_ID) {
                logger.info("is Access Key Contract {} | id: {}",contract.getName(), contract.getMboId());
            }

            mboPurchaseContractResponse = purcaseMboContract(contract, enrolmentData, isTestSubmission);
            if (mboPurchaseContractResponse == null) {
                saveEnrolmentErrorSubmission(enrolmentData, 1, null, contract, null, MemberStatus.PURCHASE_CONTRACT_ERROR);
                isComplete = false;
                status = MemberStatus.PURCHASE_CONTRACT_ERROR.getStatus();
            }
        }

//        Handle TwoFreePT -> PTPack with free PTP coupon
        if (enrolmentData.getFreePTPack().equalsIgnoreCase(Constants.ENROLMENT_DATA_COUPON_2_FREE_PT)) {

            logger.info("2 Free PT");

            Integer contractId = Helpers.isSydney(enrolmentData.getLocationId()) ? Constants.STARTERPACK_PTPACK_ID : Constants.GW_STARTERPACK_PTPACK_ID;
            contract = getSaleDao().getContractByMboIdAndLocation(contractId, enrolmentData.getLocationId());
            mboPurchaseContractResponse = purcaseMboContract(contract, enrolmentData, isTestSubmission);
            if (mboPurchaseContractResponse == null) {
                saveEnrolmentErrorSubmission(enrolmentData, 1, null, contract,  null, MemberStatus.PURCHASE_CONTRACT_ERROR);
                isComplete = false;
                status = MemberStatus.PURCHASE_CONTRACT_ERROR.getStatus();
            }

            String tmp = enrolmentData.getContractNamesToBeActivated();
            tmp += " | Packs: PT Pack # 0.00";
            enrolmentData.setContractNamesToBeActivated(tmp);
        }

//        Handle Access Key Pay Today
        if (enrolmentData.getAccessKeyPaymentOptions().equals(Constants.ACCESS_KEY_PAYMENT_OPTION_PAY_TODAY)) {
            logger.info("Inside Access Key Pay Today");
            MboShoppingCartResponse mboShoppingCartResponse = purchaseMboProduct(enrolmentData, isTestSubmission);

//                logger.info("MboShoppingCartResponse: {}",mboShoppingCartResponse.toString());

            if (mboShoppingCartResponse == null) {
                logger.error("MboShoppingCartResponse ERROR");
                String productCode = enrolmentData.getLocationId().equals("4") ? Constants.GW_ACCESS_KEY_PRODUCT_CODE : Constants.ACCESS_KEY_PRODUCT_CODE;
                saveEnrolmentErrorSubmission(enrolmentData, 1, productCode, null, null, MemberStatus.PURCHASE_SERVICE_ERROR);
                isComplete = false;
                status = MemberStatus.PURCHASE_SERVICE_ERROR.getStatus();
            }

            String tmp = enrolmentData.getContractNamesToBeActivated();
            tmp += " | Access Key # " + String.format("%.2f", (Double.parseDouble(ACCESS_KEY_PRICE) - mindBodyService.getAccessKeyDiscountAmount(enrolmentData.getAccessKeyDiscount())));
            enrolmentData.setContractNamesToBeActivated(tmp);
        }

//        External PT
        if (enrolmentData.getLifestylePersonalTraining().equals(Constants.EXTERNAL_PT)) {
//            List contract names to be uploaded for manual upload
            String tmp = enrolmentData.getContractNamesToBeActivated();
//            tmp += " | External Personal Training "+enrolmentData.getNumberSessionsPerWeek()+" # " + String.format("%.2f", getExternalPTfnDDAmount(enrolmentData.getNumberSessionsPerWeek(), enrolmentData.getExternalPTSessionPrice())) + "/fortnight";
            enrolmentData.setContractNamesToBeActivated(tmp);
            saveEnrolmentErrorSubmission(enrolmentData, 1, null, null, null, MemberStatus.EXTERNAL_PT);
            isComplete = false;
            status = MemberStatus.EXTERNAL_PT.getStatus();
        }

//         Generate PDF
        UIGym gym = getUIGymByLocation(enrolmentData.getLocationId());
        if (gym != null) {
            try {
                getMindBodyService().writeEnrolmentPDF(enrolmentData, gym.getClubManager().getName());
            } catch (Exception e) {
                logger.error("Error writing PDF: {}",e.getMessage());
            }
        }

        //        TODO: TEST THIS
        if (!isDuplicateKey) {

            logger.info("About to Update Client/Add Access Key Number");
    //        Add Access Key Number
            MboUpdateClientResponse mboUpdateClientResponse = null;

            MboUpdateAccessKeyNumberRequest updateAccessKeyNumberRequest = new MboUpdateAccessKeyNumberRequest();
            ClientId clientId = new ClientId(enrolmentData.getAccessKeyNumber());
            updateAccessKeyNumberRequest.setClientId(clientId);
            updateAccessKeyNumberRequest.setCrossResionalUpdate(false);
            updateAccessKeyNumberRequest.setTestSubmission(isTestSubmission);
            updateAccessKeyNumberRequest.setUpdatedAccessKeyNumber(tempAccessKey);

            mboUpdateClientResponse = getMindBodyService().updateClientAccessKey(updateAccessKeyNumberRequest, isSydney);

            if (mboUpdateClientResponse == null) {
                logger.error("Error update client access key to MBO: {}",enrolmentData.getId());
                isAccessKeyUploaded = false;
            }
        }

//        Send email to member to set up login
        MboSendPasswordResetEmailRequest mboSendPasswordResetEmailRequest = new MboSendPasswordResetEmailRequest(enrolmentData.getEmail(), enrolmentData.getFirstName(), enrolmentData.getLastName());
        getMindBodyService().sendPasswordResetEmail(mboSendPasswordResetEmailRequest, isSydney);

//        Handle Error Submissions
        if (isDuplicateUsername) {
//                Save to Enrolment Errors
            saveEnrolmentErrorSubmission(enrolmentData, 1, null, null, null, MemberStatus.DUPLICATE_USERNAME);
            status = MemberStatus.DUPLICATE_USERNAME.getStatus();
        }

        if (isDuplicateKey) {
//                Save to Enrolment Errors
            saveEnrolmentErrorSubmission(enrolmentData, 1, null, null, null, MemberStatus.DUPLICATE_KEY);
            status = MemberStatus.DUPLICATE_KEY.getStatus();
        }

        if (!isAccessKeyUploaded) {
//                Save to Enrolment Errors
            saveEnrolmentErrorSubmission(enrolmentData, 1, null, null, null, MemberStatus.ACCESS_KEY_ERROR);
            status = MemberStatus.ACCESS_KEY_ERROR.getStatus();
        }


//        Clean and Sanitise Data
        if (isComplete) {
            status = MemberStatus.COMPLETE.getStatus();
            enrolmentData = getEncryptionService().encryptAndClean(enrolmentData);
//            logger.info("Data has been Encrypted");
        } else {
            enrolmentData = getEncryptionService().encryptDetails(enrolmentData);
        }

//        Set Access Key Number back to submission data
        enrolmentData.setAccessKeyNumber(tempAccessKey);

        PreExData preExData = getMemberDao().searchPreExData(enrolmentData.getFirstName(), enrolmentData.getLastName(), enrolmentData.getPhone(), enrolmentData.getEmail());

//        Handle PtTracker
        PtTracker ptTracker = null;
        if (enrolmentData.getTrainingStarterPack() != null && (
                enrolmentData.getTrainingStarterPack().equals(Constants.STARTER_PACK_FACE_TO_FACE) ||
                enrolmentData.getTrainingStarterPack().equals(Constants.STARTER_PACK_LIFESTYLE_PT ) ||
                enrolmentData.getTrainingStarterPack().equals(Constants.STARTER_PACK_EXTERNAL_PT) ||
                enrolmentData.getTrainingStarterPack().equals(Constants.STARTER_PACK_PT_ONGO )||
                enrolmentData.getTrainingStarterPack().equals(Constants.STARTER_PACK_PT_PACK)
        )) {
            ptTracker = handlePtTracker(enrolmentData, preExData, mboClient);
        }

        //        Send Internal Notifications
        getEmailService().sendEnrolmentDataNotificationToCoach(enrolmentData, preExData, null, ptTracker);

//        Active Campaign
        enrolmentData = getActiveCampaignService().createContact(enrolmentData, preExData, ptTracker);

//        Update Database
        enrolmentData.setStatus(status);
        enrolmentData = getMemberDao().saveEnrolmentData(enrolmentData);

//        Handle Web Referrals
        processWebReferrals(enrolmentData);

        logger.info("processEnrolmentSubmission_v2 Complete with status: {}",enrolmentData.getStatus());

        //            FIXME: Async - Testing
        return CompletableFuture.completedFuture(enrolmentData);
//        return enrolmentData;

    }

    @Override
    public PtTracker handlePtTracker(EnrolmentData enrolmentData, PreExData preExData, Client mboClient) {

        try {
            PtTracker ptTracker = new PtTracker();
            ptTracker.setEnrolmentDataId(enrolmentData.getId());
            if (preExData != null) {
                ptTracker.setPreExId(preExData.getId());
            }
            ptTracker.setLocationId(enrolmentData.getLocationId());
            ptTracker.setStatus(MemberStatus.PAR_Q_PENDING.getStatus());
            ptTracker.setCommunicationsStatus(CommunicationsStatus.EMAIL_CAMPAIGN_PENDING.getStatus());
            if (mboClient != null) {
                ptTracker.setMboUniqueId(mboClient.getUniqueId());
            }
            ptTracker.setCreateDate(Helpers.getDateNow());
            ptTracker.setCommunicationsUpdateDate(Helpers.getDateNow());
            ptTracker.setUpdateDate(Helpers.getDateNow());
            ptTracker.setStaffMember(enrolmentData.getStaffMember());
            ptTracker.setPersonalTrainer(enrolmentData.getPersonalTrainer());
            ptTracker.setReassigned(false);
            ptTracker.setSessionCount(0);
            ptTracker.setHasFirstSessionBooked(false);

            return getMemberDao().savePtTracker(ptTracker);

        } catch (Exception ex) {
            logger.error("Error creating PtTracker: {}",ex.getMessage());
            return null;
        }
    }

    @Override
    public PtTracker savePtTracker(PtTracker ptTracker) {

        return getMemberDao().savePtTracker(ptTracker);
    }

    @Override
    public CompanyDashboard getCompanyDashboardData() {

        ArrayList<EnrolmentData> enrolments = getMemberDao().getMtdEnrolments();
        ArrayList<CancellationData> cancellations = getMemberDao().getMtdCancellations();
        ArrayList<PreExData> preExs = getMemberDao().getMtdPreExs();
        ArrayList<FpCoachEnrolmentData> fpCoachEnrolments = getMemberDao().getMtdFpCoachEnrolments();

        for (EnrolmentData e : enrolments) {
            e.setUID(null);
            e.setMemberBankDetail(null);
            e.setMemberCreditCard(null);
            e.setUnder18SignatureURL(null);
            e.setPrimarySignatureURL(null);
            e.setPaymentAuthSignatureURL(null);
        }

        CompanyDashboard companyDashboard = new CompanyDashboard(enrolments, cancellations, preExs, fpCoachEnrolments);

        return companyDashboard;
    }

    @Async
    @Override
    public void promotionsHubReset() {
        getGymDao().promotionsHubReset();
    }


    @Override
    public void updatePtSessionsCount() {

//        Get Client By Unique Id

//        Get Client Visits between createDate and now

//        Update PtTracker Session Count

        ArrayList<PtTracker> ptTrackers = getMemberDao().getPtTrackerBySessionCount(PT_FEEDBACK_SESSION_COUNT);

        for (PtTracker p : ptTrackers) {

//            logger.info(p.getMboUniqueId());
            List<Client> mboClients = getMindBodyService().findMboClientByUniqueIds(Long.parseLong(p.getMboUniqueId()));

            for (Client c : mboClients) {
                logger.info("{} {} {} {}",c.getFirstName(), c.getLastName(), c.getAccessKeyNumber(), c.getStatus());

                ArrayList<Visit> visits = getMindBodyService().getClientVisits(c.getAccessKeyNumber(), p.getCreateDate(), Helpers.getDateNow());

                int sessionsCount = 0;
                for (Visit v : visits) {
                    logger.info("\n{}\n",v.toString());
                    for (String sessionType : Constants.MBO_PT_SESSIONS_TYPES) {
                        if (v.getName().equals(sessionType)) {
                            sessionsCount += 1;
                        }
                    }
                }

                p.setSessionCount(sessionsCount);
                p.setHasFirstSessionBooked(true);

                if (sessionsCount >= PT_EARLY_FEEDBACK_SESSION_COUNT && sessionsCount < PT_FEEDBACK_SESSION_COUNT &&
                        !p.getCommunicationsStatus().equals(CommunicationsStatus.PT_EARLY_FEEDBACK_RECEIVED.getStatus()) &&
                        !p.getCommunicationsStatus().equals(CommunicationsStatus.PT_EARLY_FEEDBACK_REMINDER_SENT.getStatus()) &&
                        !p.getCommunicationsStatus().equals(CommunicationsStatus.PT_EARLY_FEEDBACK_SENT.getStatus())
                ) {
//                    Send early feedback request
                    getEmailService().sendPtEarlyFeedbackRequest(p, c);
                    p.setCommunicationsStatus(CommunicationsStatus.PT_EARLY_FEEDBACK_SENT.getStatus());
                    p.setCommunicationsUpdateDate(Helpers.getDateNow());
                }

                if (sessionsCount >= PT_FEEDBACK_SESSION_COUNT &&
                        !p.getCommunicationsStatus().equals(CommunicationsStatus.PT_FEEDBACK_RECEIVED.getStatus()) &&
                        !p.getCommunicationsStatus().equals(CommunicationsStatus.PT_FEEDBACK_REMINDER_SENT.getStatus()) &&
                        !p.getCommunicationsStatus().equals(CommunicationsStatus.PT_FEEDBACK_SENT.getStatus())
                ) {
//                    Send Feedback Request
                    getEmailService().sendPtFeedbackRequest(p, c);
                    p.setCommunicationsStatus(CommunicationsStatus.PT_FEEDBACK_SENT.getStatus());
                    p.setCommunicationsUpdateDate(Helpers.getDateNow());
                }

                getMemberDao().updatePtTracker(p);
            }
        }
    }

    @Override
    public ArrayList<PtTracker> getPtTrackerByPersonalTrainer(String personalTrainer) {
        return getMemberDao().getPtTrackerByPersonalTrainer(personalTrainer);
    }

    @Override
    public ArrayList<PtTracker> getPtTrackerByNoFirstSession() {
        return getMemberDao().getPtTrackerByNoFirstSession();
    }

    @Override
    public PtTracker updatePtTracker(PtTracker ptTracker) {
        return getMemberDao().updatePtTracker(ptTracker);
    }


    @Override
    public DigitalPreExData getDigitalPreExData(EnrolmentData enrolmentData) {

        EntityLookUp entityLookUp = new EntityLookUp();
        entityLookUp.setAuth(HANDSHAKE_KEY);
        entityLookUp.setEmail(enrolmentData.getEmail());
        entityLookUp.setPhone(enrolmentData.getPhone());
        entityLookUp.setFirstName(enrolmentData.getFirstName());
        entityLookUp.setLastName(enrolmentData.getLastName());

        return getDigitalPreData(entityLookUp);
    }

    @Override
    public PreExData getPreExData(String firstName, String lastName, String phone, String email) {
        return getMemberDao().searchPreExData(firstName, lastName, phone, email);
    }

    @Async
    @Override
    public void handleWebReferrals(EnrolmentData enrolmentData) {
        processWebReferrals(enrolmentData);
    }

    private void processWebReferrals(EnrolmentData enrolmentData) {

        logger.info("Inside processWebReferrals({})",enrolmentData.getId());

        WebReferralData webReferralData = getMemberDao().searchWebReferrals(enrolmentData.getFirstName(), enrolmentData.getLastName(), enrolmentData.getPhone());

        if (webReferralData == null) {
            return;
        }

//        logger.info("WebReferralData {}",webReferralData.toString());
        getActiveCampaignService().handleReferredByMemberComms(enrolmentData, webReferralData);

    }

    private void saveEnrolmentErrorSubmission(EnrolmentData enrolmentData, int mboSubmissionCount, String productId, MboContract contract, MboService service, MemberStatus status) {

        EnrolmentSubmissionError error = new EnrolmentSubmissionError(
                status.getStatus(),
                status.getDescription(),
                status.getStatusCode(),
                Helpers.getDateNow(),
                mboSubmissionCount,
                null,
                productId,
                null,
                enrolmentData,
                null,
                contract,
                service,
                null);

        getSubmissionErrorDao().saveSubmissionError(error);
    }

    private EnrolmentData setContractNames(EnrolmentData enrolmentData) {
        String serviceId = getServiceId(enrolmentData);
        String contractNames = "";
        String[] contractIds = enrolmentData.getMemberContracts().split(",");

        if (serviceId != null) {
            MboService service = getSaleDao().getServiceByMboIdAndLocation(Integer.parseInt(serviceId), enrolmentData.getLocationId());
            if (service != null) {
                contractNames += service.getName() + " # ";
                contractNames += String.format("%.2f", service.getPrice()) + " | ";
            }
        }

        for (String contractId : contractIds) {
//            logger.info("setContractNames ContractId: {}", contractId);
            MboContract contract = getSaleDao().getContractByMboIdAndLocation(Integer.parseInt(contractId), enrolmentData.getLocationId());
            if (contract != null) {
                contractNames += contract.getName() + " # ";

                if ((Integer.parseInt(contractId) == Constants.STARTERPACK_PTPACK_ID || Integer.parseInt(contractId) == Constants.STARTERPACK_PTPACK_ID)
                        && (enrolmentData.getFreePTPack().equalsIgnoreCase(Constants.ENROLMENT_DATA_COUPON_2_FREE_PT) || enrolmentData.getFreePTPack().equalsIgnoreCase(Constants.COUPON_CODE_PTP))) {

                    contractNames += "0.00";
                } else if (Integer.parseInt(contractId) == Constants.ACCESS_KEY_ID || Integer.parseInt(contractId) == Constants.GW_ACCESS_KEY_ID) {

                    double amount = Double.parseDouble(ACCESS_KEY_PRICE);
                    if (enrolmentData.getAccessKeyDiscount() != null) {
                        amount -= Helpers.getAccessKeyDiscountAmount(enrolmentData.getAccessKeyDiscount());
                        contractNames += String.format("%.2f", amount);
                    }

                } else if (Integer.parseInt(contractId) == Constants.COUPON_CODE_7_DAYS_ID || Integer.parseInt(contractId) == Constants.COUPON_CODE_14_DAYS_ID || Integer.parseInt(contractId) == Constants.COUPON_CODE_21_DAYS_ID || Integer.parseInt(contractId) == Constants.COUPON_CODE_30_DAYS_ID ) {
                    contractNames += "0.00";

                } else {
                    contractNames += String.format("%.2f", contract.getReoccuringPaymentAmountTotal());
                }

                if (contract.getNumberOfAutoPays() > 1) {
                    contractNames += "/fortnight";
                }
                contractNames += " | ";
            }
        }

        contractNames = Helpers.removeLastNCharacters(contractNames, 3);
        logger.info("Contract Names: {}",contractNames);
        enrolmentData.setContractNamesToBeActivated(contractNames);
        return enrolmentData;
    }

    private String getServiceId(EnrolmentData enrolmentData) {
        String serviceId = null;

        if (enrolmentData.getStatus().equals(MemberStatus.MANUAL.getStatus()) || enrolmentData.getStatus().equals(MemberStatus.ERROR.getStatus())) {
            serviceId = enrolmentData.getLocationId().equals("4") ? Constants.GW_FIRST_7_DAYS_ID : Constants.FIRST_7_DAYS_ID;
        }

        if (enrolmentData.getDaysFree() != null && enrolmentData.getDaysFree().length() > 0) {
            switch (enrolmentData.getDaysFree()) {
                case Constants.COUPON_CODE_7_DAYS:
                    serviceId = enrolmentData.getLocationId().equals("4") ? Constants.GW_FIRST_7_DAYS_ID : Constants.FIRST_7_DAYS_ID;
                    break;
                case Constants.COUPON_CODE_14_DAYS:
                    serviceId = enrolmentData.getLocationId().equals("4") ? Constants.GW_FIRST_14_DAYS_ID : Constants.FIRST_14_DAYS_ID;
                    break;
                case Constants.COUPON_CODE_30_DAYS:
                    serviceId = enrolmentData.getLocationId().equals("4") ? Constants.GW_FIRST_30_DAYS_ID : Constants.FIRST_30_DAYS_ID;
                    break;
                default:
                    serviceId = enrolmentData.getLocationId().equals("4") ? Constants.GW_FIRST_7_DAYS_ID : Constants.FIRST_7_DAYS_ID;
            }
        }

        return serviceId;
    }


    private double getExternalPTfnDDAmount(String numSessionPerWk, String pricePerSession) {
        double amount = 0;
        double sessionPrice = Double.parseDouble(pricePerSession);
        switch (numSessionPerWk) {
            case Constants.PT_NUMBER_SESSIONS_PER_WEEK_1FN:
                amount = sessionPrice;
                break;
            case Constants.PT_NUMBER_SESSIONS_PER_WEEK_1WK:
                amount = sessionPrice * 2;
                break;
            case Constants.PT_NUMBER_SESSIONS_PER_WEEK_2WK:
                amount = sessionPrice * 4;
                break;
            case Constants.PT_NUMBER_SESSIONS_PER_WEEK_3WK:
                amount = sessionPrice * 6;
                break;
            case Constants.PT_NUMBER_SESSIONS_PER_WEEK_4WK:
                amount = sessionPrice * 8;

        }
        return amount;
    }

    private EnrolmentData cleanStaffNames(EnrolmentData e) {

        Map<String, String> staffMap = getMindBodyService().getAllStaffMap();

        if (e.getStaffMember() != null) {
            e.setStaffMember(staffMap.get(e.getStaffMember()));
        }

        if(e.getPersonalTrainer() != null) {
            e.setPersonalTrainer(staffMap.get(e.getPersonalTrainer()));
        }
        return e;
    }

    private Iterable<EnrolmentData> cleanAndSanitiseEnrolmentData(Iterable<EnrolmentData> enrolmentData) {

        for (EnrolmentData e: enrolmentData) {

            e = cleanStaffNames(e);

            e.setMemberCreditCard(null);
            e.setMemberBankDetail(null);
        }
        return enrolmentData;
    }

    private List<EnrolmentData> cleanAndSanitiseEnrolmentData(List<EnrolmentData> enrolmentData) {

        for (EnrolmentData e: enrolmentData) {

            e = cleanStaffNames(e);

            e.setMemberCreditCard(null);
            e.setMemberBankDetail(null);
        }
        return enrolmentData;
    }

    private EnrolmentData handleAccessKeySiteCode(EnrolmentData enrolmentData) {
        String locationId = enrolmentData.getLocationId();

        String accessKeySiteCode;

        switch (locationId) {
            case ("1"):
                accessKeySiteCode = ACCESS_KEY_SITE_CODE_SURRY_HILLS;
                break;
            case ("2"):
                accessKeySiteCode = ACCESS_KEY_SITE_CODE_NEWTOWN;
                break;
            case ("3"):
                accessKeySiteCode = ACCESS_KEY_SITE_CODE_MARRICKVILLE;
                break;
            case ("4"):
                accessKeySiteCode = ACCESS_KEY_SITE_CODE_GATEWAY;
                break;
            case ("5"):
                accessKeySiteCode = ACCESS_KEY_SITE_CODE_BUNKER;
                break;
            default:
                accessKeySiteCode = ACCESS_KEY_SITE_CODE_DEFAULT;
        }

        if (!accessKeySiteCode.equals("AA")) {
            String accessKey = accessKeySiteCode + enrolmentData.getAccessKeyNumber();
            enrolmentData.setAccessKeyNumber(accessKey);
        }
//        else {
//            enrolmentData.setAccessKeyNumber(enrolmentData.getAccessKeyNumber());
//        }


        return enrolmentData;
    }

    private DigitalPreExData getDigitalPreData(EntityLookUp entityLookUp) {

        DigitalPreExData digitalPreExData = null;
        boolean isSuccess;

        resetRequestVariables();
        FPGetDigitalPreExRequest request = new FPGetDigitalPreExRequest(entityLookUp);
        FPGetDigitalPreExResponse response = null;
        HttpHeaders headers = getHeaders();
        HttpEntity<FPGetDigitalPreExRequest> entity = new HttpEntity<>(request,headers);

//        logger.info("Request: {}\nurl: {}",entity.toString(),BASE_URL);

        do {
            isSuccess = true;
            try {
                response = getRestTemplate().exchange(REPORTS_BASE_URL + "getPreExData", HttpMethod.POST, entity, FPGetDigitalPreExResponse.class).getBody();
            } catch (Exception ex) {
                logger.error("Error getting Digital Pre Ex {}",ex.getMessage());
                setRetryCount(getRetryCount() + 1);
                isSuccess = false;
                setValid(getRetryCount() < RETRY_BREAK);
            }
            if (isSuccess) {
                if (response != null) {
                    digitalPreExData = response.getDigitalPreExData();
//                    logger.info("getDigitalPreExData response {}",response.toString());
                }
                setValid(false);
            }
        } while (isValid());

        return digitalPreExData;
    }



    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        headers.setContentType(MediaType.APPLICATION_JSON);
//        logger.info("Headers: {}", headers.toString());
        return headers;
    }

    private void resetRequestVariables() {
        setRequestOffset(0);
        setCount(0);
        setTotalResults(0);
        setValid(true);
        setRetryCount(0);
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public int getRequestLimit() {
        return requestLimit;
    }

    public void setRequestLimit(int requestLimit) {
        this.requestLimit = requestLimit;
    }

    public int getRequestOffset() {
        return requestOffset;
    }

    public void setRequestOffset(int requestOffset) {
        this.requestOffset = requestOffset;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    private boolean validateSubmissionID(String UID) {
        return staffDao.getStaffMemberByFirebaseId(UID) != null;
    }

    @Override
    public boolean validateStaffId(String UID) {
        return getStaffDao().getStaffByFirebaseId(UID) != null;
    }

    public HashMap<String, Iterable<EnrolmentData>> getSubmissionsMap() {
        return submissionsMap;
    }

    public void setSubmissionsMap(HashMap<String, Iterable<EnrolmentData>> submissionsMap) {
        this.submissionsMap = submissionsMap;
    }

    public ArrayList<UIGym> getUiGyms() {
        return uiGyms;
    }

    public void setUiGyms(ArrayList<UIGym> uiGyms) {
        this.uiGyms = uiGyms;
    }

    public ArrayList<UIStaff> getUiStaffs() {
        return uiStaffs;
    }

    public void setUiStaffs(ArrayList<UIStaff> uiStaffs) {
        this.uiStaffs = uiStaffs;
    }

    public List<Gym_PersonalTrainer> getGym_personalTrainers() {
        return gym_personalTrainers;
    }

    public void setGym_personalTrainers(List<Gym_PersonalTrainer> gym_personalTrainers) {
        this.gym_personalTrainers = gym_personalTrainers;
    }

    public List<Gym_MembershipConsultant> getGym_membershipConsultants() {
        return gym_membershipConsultants;
    }

    public void setGym_membershipConsultants(List<Gym_MembershipConsultant> gym_membershipConsultants) {
        this.gym_membershipConsultants = gym_membershipConsultants;
    }

    public HashMap<String, ArrayList<EnrolmentSubmissionError>> getManualSubmissionsMap() {
        return manualSubmissionsMap;
    }

    public void setManualSubmissionsMap(HashMap<String, ArrayList<EnrolmentSubmissionError>> manualSubmissionsMap) {
        this.manualSubmissionsMap = manualSubmissionsMap;
    }

    public MindBodyClientService getMindBodyClientService() {
        return mindBodyClientService;
    }

    @Autowired
    public void setMindBodyClientService(MindBodyClientService mindBodyClientService) {
        this.mindBodyClientService = mindBodyClientService;
    }

    public MindBodySaleService getMindBodySaleService() {
        return mindBodySaleService;
    }

    @Autowired
    public void setMindBodySaleService(MindBodySaleService mindBodySaleService) {
        this.mindBodySaleService = mindBodySaleService;
    }

    public MindBodyStaffService getMindBodyStaffService() {
        return mindBodyStaffService;
    }

    @Autowired
    public void setMindBodyStaffService(MindBodyStaffService mindBodyStaffService) {
        this.mindBodyStaffService = mindBodyStaffService;
    }

    public MindBodyService getMindBodyService() {
        return mindBodyService;
    }

    @Autowired
    public void setMindBodyService(MindBodyService mindBodyService) {
        this.mindBodyService = mindBodyService;
    }

    public EncryptionService getEncryptionService() {
        return encryptionService;
    }

    @Autowired
    public void setEncryptionService(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    public ActiveCampaignService getActiveCampaignService() {
        return activeCampaignService;
    }

    @Autowired
    public void setActiveCampaignService(ActiveCampaignService activeCampaignService) {
        this.activeCampaignService = activeCampaignService;
    }

    public StaffDao getStaffDao() {
        return staffDao;
    }

    @Autowired
    public void setStaffDao(StaffDao staffDao) {
        this.staffDao = staffDao;
    }

    public SaleDao getSaleDao() {
        return saleDao;
    }

    @Autowired
    public void setSaleDao(SaleDao saleDao) {
        this.saleDao = saleDao;
    }

    public ClientDao getClientDao() {
        return clientDao;
    }

    @Autowired
    public void setClientDao(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public GymDao getGymDao() {
        return gymDao;
    }

    @Autowired
    public void setGymDao(GymDao gymDao) {
        this.gymDao = gymDao;
    }

    public MemberDao getMemberDao() {
        return memberDao;
    }

    @Autowired
    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public FPOpsConfigDao getFpOpsConfigDao() {
        return fpOpsConfigDao;
    }

    @Autowired
    public void setFpOpsConfigDao(FPOpsConfigDao fpOpsConfigDao) {
        this.fpOpsConfigDao = fpOpsConfigDao;
    }

    public SubmissionErrorDao getSubmissionErrorDao() {
        return submissionErrorDao;
    }

    @Autowired
    public void setSubmissionErrorDao(SubmissionErrorDao submissionErrorDao) {
        this.submissionErrorDao = submissionErrorDao;
    }

    public TestDao getTestDao() {
        return testDao;
    }

    @Autowired
    public void setTestDao(TestDao testDao) {
        this.testDao = testDao;
    }

    public MonitoringDao getMonitoringDao() {
        return monitoringDao;
    }

    @Autowired
    public void setMonitoringDao(MonitoringDao monitoringDao) {
        this.monitoringDao = monitoringDao;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public EmailService getEmailService() {
        return emailService;
    }

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
}