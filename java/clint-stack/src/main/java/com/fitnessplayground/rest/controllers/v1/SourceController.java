package com.fitnessplayground.rest.controllers.v1;

import com.fitnessplayground.dao.domain.*;
import com.fitnessplayground.dao.domain.fpSourceDto.DigitalPreExData;
import com.fitnessplayground.dao.domain.mboDto.Client;
import com.fitnessplayground.dao.domain.reportDto.PtFeedbackReportData;
import com.fitnessplayground.dao.domain.temp.*;
import com.fitnessplayground.service.*;
import com.fitnessplayground.util.Constants;
import com.fitnessplayground.util.GymName;
import com.fitnessplayground.util.TestEntities;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@CrossOrigin(origins = "${forms.origin}")
//@CrossOrigin(origins = "*")
@RequestMapping("/v1/source")
public class SourceController {

    private static final Logger logger = LoggerFactory.getLogger(SourceController.class);

    private FitnessPlaygroundService fitnessPlaygroundService;
    private FpCoachService fpCoachService;
    private FpEnrolmentService fpEnrolmentService;
    private MindBodyService mindBodyService;
    private FormsService formsService;
    private FpAcademyService fpAcademyService;
    private ActiveCampaignService activeCampaignService;
    private EmailService emailService;
    private KeapService keapService;

    @Value("${fp.authorisation.header}")
    private String FP_AUTHORIZATION_HEADER;

    // http://localhost:8080/v1/source/test2?formId=test2
    @RequestMapping(value = "test2", method = RequestMethod.GET)
    public void receiveTestEnrolment(@RequestParam String auth) {
        logger.info("In receiveTestEnrolment **********{}", auth);
        if (!auth.equals("1984")) return;
        EnrolmentFormSubmission submission = TestEntities.getEnrolmentFormTestSubmission();
        logger.info("\n"+submission.toString());
        getFitnessPlaygroundService().handleEnrolmentSubmission(submission);
    }

    @RequestMapping(value = "test1", method = RequestMethod.GET)
    public EnrolmentFormSubmission getTestEnrolment(@RequestParam String auth) {
        if (!auth.equals("1984")) return null;
        EnrolmentFormSubmission submission = TestEntities.getEnrolmentFormTestSubmission();
        String siteId = submission.getLocationId().contains("4") ? Long.toString(Constants.DARWIN_SITE_ID) : Long.toString(Constants.SYDNEY_SITE_ID);
        submission.getMembershipDetails().setStaffMembers(getMindBodyService().getStaffOrPersonalTrainerName(submission.getMembershipDetails().getStaffMembers(), siteId));
        submission.getMembershipDetails().setPersonalTrainers(getMindBodyService().getStaffOrPersonalTrainerName(submission.getMembershipDetails().getPersonalTrainers(), siteId));
        submission.setLocationId(GymName.convertLocationId(submission.getLocationId()));
        return submission;
    }

    @RequestMapping(value = "searchMboClient/{searchText}", method = RequestMethod.GET)
    public List<Client> searchMboClient(@PathVariable String searchText) {
        logger.info("Inside searchMboClient: {}",searchText);
//        FIXME - this is throwing a 406 error in testing
        return  getMindBodyService().searchMboClient(searchText);
    }

    @RequestMapping(value = "syncClients", method = RequestMethod.GET)
    public String syncClients(@RequestParam String auth) {
        logger.info("Inside syncClients auth: {}",auth);
        if (!auth.equals("1984")) return "FAIL!";
        getMindBodyService().syncAllClients();
        return "DONE!";
    }

    @RequestMapping(value = "TestSubmission", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public String purchaseMembership(@RequestBody EnrolmentFormSubmission enrolmentFormSubmission) {
    public void testSubmission(@RequestParam String auth, @RequestBody String submission) {
        if (!auth.equals("1984")) return;
        logger.info("\nTest Enrolment Submissionn Received\n"+submission);
        getFitnessPlaygroundService().handleTestSubmission(submission);

    }

    @RequestMapping(value = "testFpFormsController", method = RequestMethod.GET)
    public void testFpFormsController() {
        logger.info("In FpFormsController **********");

    }

    @RequestMapping(value = "getAllContracts/{locationId}", method = RequestMethod.GET)
    public List<MboContract> getAllContracts(@RequestHeader Map<String, String> headers, @PathVariable String locationId) {

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return null;
        }

        List<MboContract> contracts = getFitnessPlaygroundService().getAllContracts(locationId);

        return contracts;
    }

    @RequestMapping(value = "getAllServices/{locationId}", method = RequestMethod.GET)
    public List<MboService> getAllServices(@RequestHeader Map<String, String> headers, @PathVariable String locationId) {

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return null;
        }

        List<MboService> services = getFitnessPlaygroundService().getAllServices(locationId);

        return services;
    }


    @RequestMapping(value = "getAllProducts/{locationId}", method = RequestMethod.GET)
    public List<MboProduct> getAllProducts(@RequestHeader Map<String, String> headers, @PathVariable String locationId) {

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return null;
        }

        if (!locationId.equals("4")) {
            locationId = "1";
        }

        List<MboProduct> products = getFitnessPlaygroundService().getAllProducts(locationId);

        return products;
    }

    @RequestMapping(value = "getContracts/{locationId}", method = RequestMethod.GET)
    public List<MboContract> _getContracts(@PathVariable String locationId) {

        List<MboContract> contracts = getFitnessPlaygroundService().getContracts(locationId);
//        logger.info("Retrieved {} Contracts", IterableUtil.sizeOf(contracts));

        return contracts;
    }

    @RequestMapping(value = "getPersonalTrainers/{locationId}", method = RequestMethod.GET)
    public List<PersonalTrainer> _getPersonalTrainers(@PathVariable String locationId) {
        List<PersonalTrainer> personalTrainerList = getFitnessPlaygroundService().getPersonalTrainersByLocation(locationId);
        logger.info("Retrieved {} Personal Trainers", personalTrainerList.size());

        return personalTrainerList;
    }

    @RequestMapping(value = "getStaffMembers/{locationId}", method = RequestMethod.GET)
    public List<StaffMember> _getStaffMembers(@PathVariable String locationId) {
        List<StaffMember> staffMemberList = getFitnessPlaygroundService().getStaffsByLocation(locationId);
        logger.info("Retrieved {} Personal Trainers", staffMemberList.size());

        return staffMemberList;
    }


    @RequestMapping(value = "searchForExistingClient", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<MboClient> _searchForExistingClient(@RequestBody PrimaryDetails primaryDetails) {
        List<MboClient> existingClients = getFitnessPlaygroundService().searchForExistingClient(primaryDetails);
//        logger.info("Found {} Existing Clients", existingClients.size());
        return existingClients;
    }

    @RequestMapping(value = "searchDuplicateKey", method = RequestMethod.GET)
    public boolean _searchDuplicateKey(@PathVariable String accessKeyNumber) {
        boolean duplicateKey = getMindBodyService().isDuplicateKey(accessKeyNumber);
//        logger.info("Found {} Duplicate Key", duplicateKey);
        return duplicateKey;
    }


    @RequestMapping(value = "purchaseMembership", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void purchaseMembership(@RequestBody EnrolmentFormSubmission enrolmentFormSubmission) {
        logger.info("Enrolment Submissionn Received");
        getFitnessPlaygroundService().handleEnrolmentSubmission(enrolmentFormSubmission);
    }

//    FIXME: changed this from getAllStaff to getAllStaffMembers: check if this will break anything and if not delete
    @RequestMapping(value = "getAllStaffMembers", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<StaffMember> syncAuthUsers(@RequestBody StaffLookUp staffLookUp) {
        Iterable<StaffMember> staffMembers = getFitnessPlaygroundService().getAllStaffMembers();
//        logger.info("Retrieved {} Staff Members", IterableUtil.sizeOf(staffMembers));
        return staffMembers;
    }


    @RequestMapping(value = "getManualSubmissions", method = RequestMethod.GET)
    public Iterable<EnrolmentSubmissionError> getManualSubmissions(@RequestParam String UID, @RequestParam String location) {
        Iterable<EnrolmentSubmissionError> errorSubmissions = getFitnessPlaygroundService().getEnrolmentErrorSubmissions(UID, location);
//        logger.info("Retrieved {} Error Submissions", IterableUtil.sizeOf(errorSubmissions));
        return errorSubmissions;
    }

    @RequestMapping(value = "completeManualSubmissions", method = RequestMethod.GET)
    public void completeManualSubmissions(@RequestParam String UID, @RequestParam String enrolmentSubmissionErrorId) {
        getFitnessPlaygroundService().completeEnrolmentErrorSubmissions(UID, enrolmentSubmissionErrorId);
    }

    @RequestMapping(value = "getEnrolments", method = RequestMethod.GET)
    public Iterable<EnrolmentData> getEnrolments(@RequestParam String UID, @RequestParam String location) {
        return getFitnessPlaygroundService().getEnrolments(UID, location);
    }


    @RequestMapping(value = "searchEnrolments", method = RequestMethod.POST)
    public Iterable<EnrolmentData> searchEnrolments(@RequestParam String UID, @RequestBody EnrolmentLookUp enrolmentLookUp) {
        return getFitnessPlaygroundService().searchEnrolments(UID, enrolmentLookUp);
    }

    /*
    * Updates Staff
    * - Permission
    * - Location
    * - Role
    * */
    @RequestMapping(value = "updateStaff", method = RequestMethod.POST)
    public void updateStaff(@RequestParam String UID, @RequestBody UpdateStaff updateStaff) {
//        logger.info("Called source/updateStaff {}",updateStaff.toString());
        getFitnessPlaygroundService().updateStaff(UID, updateStaff);
    }

    /*
    * Updates Gym
    * - Club Manager
    * - Personal Training Manager
    * - Group Fitness Manager
    * */
    @RequestMapping(value = "updateGym", method = RequestMethod.POST)
    public ArrayList<UIGym> updateGym(@RequestParam String UID, @RequestBody UpdateGym updateGym) {
//        logger.info("Called source/updateGym {}",updateGym.toString());
        return getFitnessPlaygroundService().updateGym(UID, updateGym);
    }

    @RequestMapping(value = "getGym", method = RequestMethod.GET)
    public UIGym getGym(@RequestParam String UID, @RequestParam String location) {
        return getFitnessPlaygroundService().getGymByLocation(UID, location);
    }

    @RequestMapping(value = "getStaff/{siteId}/{mboId}", method = RequestMethod.GET)
    public Staff getStaff(@RequestParam String UID, @PathVariable Long siteId, @PathVariable Long mboId) {
        return getFitnessPlaygroundService().getStaffByMboIdAndSiteId(UID, mboId, siteId);
    }


    @RequestMapping(value = "getStaffById/{siteId}/{mboId}", method = RequestMethod.GET)
    public Staff getStaff(@RequestHeader Map<String, String> headers, @PathVariable Long siteId, @PathVariable Long mboId) {

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        //      Security check
        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Submission User");
            return null;
        }

        return getFitnessPlaygroundService().getStaffByMboIdAndSiteId(UID, mboId, siteId);
    }

    @RequestMapping(value = "getStaffByFirebaseId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Staff getStaffByFirebaseId(@RequestBody StaffLookUp staffLookUp) {
        return getFitnessPlaygroundService().getStaffByFirebaseId(staffLookUp);
    }


    @RequestMapping(value = "getAllUIStaff", method = RequestMethod.GET)
    public ArrayList<UIStaff> getAllUIStaff(@RequestParam String UID) {
        return getFitnessPlaygroundService().getAllUIStaff(UID);
    }

    @RequestMapping(value = "getAllStaff", method = RequestMethod.GET)
    public Iterable<Staff> getAllStaff(@RequestParam String UID) {
        return getFitnessPlaygroundService().getAllStaff(UID);
    }

    @RequestMapping(value = "getAllGyms", method = RequestMethod.GET)
    public ArrayList<UIGym> getAllGyms(@RequestParam String UID) {
        return getFitnessPlaygroundService().getAllUiGyms(UID);
    }


    //        TODO: IMPLEMENT ME!!
    @RequestMapping(value = "removeUiStaff",method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void removeUiStaff(@RequestParam String UID, @PathVariable Long siteId, @PathVariable Long mboId) {
//        TODO: IMPLEMENT ME!!
    }


    @RequestMapping(value = "getEnrolmentDataDocument", method = RequestMethod.POST)
    public EnrolmentDataDocument getEnrolmentDataDocument(@RequestHeader Map<String, String> headers, @RequestBody FindEnrolment findEnrolment) {

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        //      Security check
        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Submission User");
            return null;
        }

        return getFitnessPlaygroundService().getDataDocByFindMember(findEnrolment);
    }


    @RequestMapping(value = "searchClients", method = RequestMethod.POST)
    public List<MboClient> searchClients(@RequestHeader Map<String, String> headers, @RequestBody FindEnrolment findEnrolment) {

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        return getFitnessPlaygroundService().searchForExistingClients(UID, findEnrolment.getFirstName(), findEnrolment.getLastName(), findEnrolment.getEmail());
    }


    @RequestMapping(value = "getAccessKeySiteCode/{locationId}", method = RequestMethod.GET)
    public AccessKeySiteCode findSiteCodeByLocationId(@RequestHeader Map<String, String> headers, @PathVariable Integer locationId) {

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        //      Security check
        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Submission User");
            return null;
        }

        return getFitnessPlaygroundService().findSiteCodeByLocationId(UID, locationId);
    }


    @RequestMapping(value = "enrolmentData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EnrolmentData saveEnrolmentData(@RequestHeader Map<String, String> headers, @RequestBody EnrolmentDataSubmission body) {

        logger.info("Inside Save Enrolment Data Controller");

//        EnrolmentDataSubmission enrolmentDataSubmission = new Gson().fromJson(body, EnrolmentDataSubmission.class);

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        //      Security check
        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Submission User");
            return null;
        }

        EnrolmentData enrolmentData = getFpEnrolmentService().saveEnrolmentDataSubmission(body);

//        if (enrolmentData != null) {
//            getActiveCampaignService().handleEnrolmentCommunications(enrolmentData);
//        }

        return enrolmentData;
    }


    @RequestMapping(value = "enrolmentData/{encrypted}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EnrolmentData updateEnrolmentData(@RequestHeader Map<String, String> headers, @RequestBody EnrolmentDataSubmission body, @PathVariable Boolean encrypted) {

        logger.info("Inside Update Enrolment Data Controller");

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return null;
        }

        return getFpEnrolmentService().updateEnrolmentData(body, encrypted);
    }


    @RequestMapping(value = "enrolmentData/{id}/{encrypted}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EnrolmentData getEnrolmentData(@RequestHeader Map<String, String> headers, @PathVariable Long id, @PathVariable Boolean encrypted) {

        logger.info("Inside Get Enrolment Data Controller");

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        //      Security check
        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Submission User");
            return null;
        }

        return getFpEnrolmentService().getEnrolmentData(id, encrypted);
    }


    @RequestMapping(value = "findEnrolment", method = RequestMethod.POST)
    public List<EnrolmentData> findEnrolments(@RequestHeader Map<String, String> headers, @RequestBody FindEnrolment findEnrolment) {

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        //      Security check
        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Submission User");
            return null;
        }

        List<EnrolmentData> enrolmentData = getFpEnrolmentService().findEnrolments(findEnrolment);
        return enrolmentData;
    }


    @RequestMapping(value = "getPhoneEnrolment/{fsFormId}/{fsUniqueId}", method = RequestMethod.GET)
    public EnrolmentData getPhoneEnrolment(@RequestHeader Map<String, String> headers, @PathVariable String fsFormId, @PathVariable String fsUniqueId) {

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        //      Security check
        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Submission User");
            return null;
        }

        return getFitnessPlaygroundService().getPhoneEnrolment(UID, fsFormId, fsUniqueId);
    }


    @RequestMapping(value = "findCoachEnrolment", method = RequestMethod.POST)
    public List<FpCoachEnrolmentData> findCoachEnrolments(@RequestHeader Map<String, String> headers, @RequestBody FindEnrolment findEnrolment) {

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        List<FpCoachEnrolmentData> fpCoachEnrolmentData = getFpCoachService().findCoachEnrolment(UID, findEnrolment);
        return fpCoachEnrolmentData;
    }


    @RequestMapping(value = "fpCoachEnrolment", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FpCoachEnrolmentData saveFpCoachEnrolmentSubmission(@RequestHeader Map<String, String> headers, @RequestBody FpCoachEnrolmentSubmission fpCoachEnrolmentSubmission) {

        logger.info("Inside Save FP Coach Enrolment");

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        //      Security check
        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Submission User");
            return null;
        }

//        Save to database
        FpCoachEnrolmentData fpCoachEnrolmentData = getFpCoachService().saveFpCoachEnrolmentData(fpCoachEnrolmentSubmission);


        return fpCoachEnrolmentData;
    }



    @RequestMapping(value = "fpCoachEnrolment/{encrypted}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FpCoachEnrolmentData updateFpCoachEnrolmentData(@RequestHeader Map<String, String> headers, @RequestBody FpCoachEnrolmentSubmission body, @PathVariable Boolean encrypted) {

        logger.info("Inside Update FP Coach Data Controller");


        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        //      Security check
        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Submission User");
            return null;
        }

        return getFpCoachService().updateFpCoachEnrolmentData(body, encrypted);
    }


    @RequestMapping(value = "fpCoachEnrolment/{id}/{encrypted}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FpCoachEnrolmentData getFpCoachEnrolmentData(@RequestHeader Map<String, String> headers, @PathVariable Long id, @PathVariable Boolean encrypted) {

        logger.info("Inside Get FP Coach Data Controller");

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        return getFpCoachService().getFpCoachEnrolmentData(id, UID, encrypted);
    }


    @RequestMapping(value = "getPhoneCancellation/{fsFormId}/{fsUniqueId}", method = RequestMethod.GET)
    public CancellationData getPhoneCancellation(@RequestHeader Map<String, String> headers, @PathVariable String fsFormId, @PathVariable String fsUniqueId) {

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        logger.info("Inside Get Phone Cancellation Controller");

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return null;
        }

        return getFormsService().getPhoneCancellation(fsFormId, fsUniqueId);
    }


    @RequestMapping(value = "getPart1Cancellation/{lastName}/{fsUniqueId}", method = RequestMethod.GET)
    public CancellationData getPart1CancellationByLastNameAndUniqueId(@RequestHeader Map<String, String> headers, @PathVariable String lastName, @PathVariable String fsUniqueId) {

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        logger.info("Inside Get Part 1 Cancellation by Last Name and UniqueId Controller");

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return null;
        }

        return getFormsService().getPart1CancellationByLastNameAndUniqueId(lastName, fsUniqueId);
    }

    @RequestMapping(value = "cancellation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CancellationData saveCancellationData(@RequestHeader Map<String, String> headers, @RequestBody CancellationDataSubmission submission) {

        logger.info("Inside Save Cancellation Request");

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return null;
        }

        return  getFormsService().handleCancellationRequest(submission);

    }


    @RequestMapping(value = "cancellation", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CancellationData updateCancellation(@RequestHeader Map<String, String> headers, @RequestBody String body) {

        logger.info("Inside Update Cancellation Data Controller");

        CancellationData cancellationData = new Gson().fromJson(body, CancellationData.class);

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return null;
        }

        return getFormsService().updateCancellationData(cancellationData);
    }


    @RequestMapping(value = "membershipChange", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MembershipChangeData saveMembershipChangeData(@RequestHeader Map<String, String> headers, @RequestBody String body) {

        logger.info("Inside save Membership Change Data Controller");

        MembershipChangeData membershipChangeData = new Gson().fromJson(body, MembershipChangeData.class);

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return null;
        }

        membershipChangeData = getFormsService().handleMembershipChange(membershipChangeData);

        return membershipChangeData;
    }


    @RequestMapping(value = "membershipChange", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MembershipChangeData updateMembershipChange(@RequestHeader Map<String, String> headers, @RequestBody String body) {

        logger.info("Inside Update Membership Change Data Controller");

        MembershipChangeData membershipChangeData = new Gson().fromJson(body, MembershipChangeData.class);

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return null;
        }

        return getFormsService().updateMembershipChangeData(membershipChangeData);
    }


    @RequestMapping(value = "getPhoneMembershipChange/{fsFormId}/{fsUniqueId}", method = RequestMethod.GET)
    public MembershipChangeData getPhoneMembershipChange(@RequestHeader Map<String, String> headers, @PathVariable String fsFormId, @PathVariable String fsUniqueId) {

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        logger.info("Inside Get Phone Membership Change Controller");

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return null;
        }

        return getFormsService().getPhoneMembershipChange(fsFormId, fsUniqueId);
    }

    @RequestMapping(value = "fpAcademy", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FpAcademyEnrolmentData saveFpAcademyEnrolment(@RequestHeader Map<String, String> headers, @RequestBody FpAcademyEnrolmentSubmission submission) {

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        logger.info("Inside Get Phone Membership Change Controller");

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return null;
        }

        return getFpAcademyService().saveFpAcademyEnrolmentData(submission);
    }

    @RequestMapping(value = "fpAcademy/{encrypted}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FpAcademyEnrolmentData updateFpAcademyEnrolment(@RequestHeader Map<String, String> headers, @RequestBody FpAcademyEnrolmentSubmission submission, @PathVariable Boolean encrypted, @RequestParam Boolean isNewPaymentDetail) {

        logger.info("Inside Update FP Academy Enrolment Controller");

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return null;
        }

//        if encrypted == true, return entity with encrypted details
        return getFpAcademyService().updateFpAcademyEnrolmentData(submission, encrypted, isNewPaymentDetail);
    }


    @RequestMapping(value = "fpAcademy/{id}/{encrypted}", method = RequestMethod.GET)
    public FpAcademyEnrolmentData getFpAcademyEnrolmentData(@RequestHeader Map<String, String> headers, @PathVariable Long id, @PathVariable Boolean encrypted) {

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        logger.info("Inside Get FP Academy Enrolment Data Controller");

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return null;
        }

        return getFpAcademyService().getFpAcademyEnrolmentData(id, encrypted);
    }


    @RequestMapping(value = "getPhoneFpAcademyEnrolment/{fsFormId}/{fsUniqueId}", method = RequestMethod.GET)
    public FpAcademyEnrolmentData getPhoneFpAcademyEnrolment(@RequestHeader Map<String, String> headers, @PathVariable String fsFormId, @PathVariable String fsUniqueId) {

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        //      Security check
        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Submission User");
            return null;
        }

        return getFpAcademyService().getPhoneFpAcademyEnrolmentData(fsFormId, fsUniqueId);
    }


    @RequestMapping(value = "feedback/{feedbackType}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveFpPtFeedback(@RequestHeader Map<String, String> headers, @RequestBody String body, @PathVariable String feedbackType) {

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        logger.info("Inside Receive Feedback Controller");

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        PtFeedbackData data;
        switch (feedbackType) {
            case "ptEarlyFeedback":
                data = new Gson().fromJson(body, PtFeedbackData.class);
                getFitnessPlaygroundService().handleFeedbackSubmission(data);
                break;
            case "ptFeedback":
                data = new Gson().fromJson(body, PtFeedbackData.class);
                getFitnessPlaygroundService().handleFeedbackSubmission(data);
                break;
            default:
                logger.error("Feedback type {} not found",feedbackType);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


    @RequestMapping(value = "ptFeedbackReport", method = RequestMethod.GET)
    public PtFeedbackReportData getPtFeedbackReportData(@RequestHeader Map<String, String> headers) {

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        logger.info("Inside Get PT Feedback Report Controller");

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return null;
        }

        return getFitnessPlaygroundService().getPtFeedbackReportData();
    }


//    TODO: TEST ME
    @RequestMapping(value = "getPtTrackerByCoach/{personalTrainer}", method = RequestMethod.GET)
    public ArrayList<PtTracker> getPtTrackerByCoach(@RequestHeader Map<String, String> headers, @PathVariable String personalTrainer) {

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        logger.info("Inside Get PT Tracker by Personal Trainer Controller");

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return null;
        }

        return getFitnessPlaygroundService().getPtTrackerByPersonalTrainer(personalTrainer);
    }


    @RequestMapping(value = "getPtTrackerByNoFirstSession", method = RequestMethod.GET)
    public ArrayList<PtTracker> getPtTrackerByNoFirstSession(@RequestHeader Map<String, String> headers) {

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        logger.info("Inside Get PT Tracker by No First Session Controller");

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return null;
        }

        return getFitnessPlaygroundService().getPtTrackerByNoFirstSession();
    }


    @RequestMapping(value = "updatePtTracker", method = RequestMethod.PUT)
    public PtTracker updatePtTracker(@RequestHeader Map<String, String> headers, @RequestBody String body) {

        logger.info("Inside updatePtTracker");

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return null;
        }

        PtTracker ptTracker = new Gson().fromJson(body, PtTracker.class);

        return getFitnessPlaygroundService().updatePtTracker(ptTracker);

    }


    @RequestMapping(value = "savePtTracker", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PtTracker savePtTracker(@RequestHeader Map<String, String> headers, @RequestBody String body) {

        logger.info("Inside handlePtTracker");

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return null;
        }

        PtTracker ptTracker = new Gson().fromJson(body, PtTracker.class);

        return getFitnessPlaygroundService().savePtTracker(ptTracker);
    }


    @RequestMapping(value = "manualSubmission", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity receiveManualSubmission(@RequestHeader Map<String, String> headers, @RequestBody ManualSubmission body) {

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        logger.info("Inside Handle Manual Submission Controller");

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        getFitnessPlaygroundService().handleManualSubmission(body);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value = "gym/{location}", method = RequestMethod.GET)
    public UIGym getGymData(@RequestHeader Map<String, String> headers, @PathVariable String location) {

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return null;
        }

        return getFitnessPlaygroundService().getGymByLocation(UID, location);
    }

    @RequestMapping(value = "triggerCommunications/{entityType}/{entityId}", method = RequestMethod.PATCH)
    public ResponseEntity handleCommunications(@RequestHeader Map<String, String> headers, @PathVariable String entityType, @PathVariable Long entityId) {

        logger.info("Inside handleCommunications: {} {}",entityType,entityId);

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        switch (entityType) {
            case "enrolmentData":
                EnrolmentData enrolmentData = getFpEnrolmentService().getEnrolmentData(entityId, false);
                if (enrolmentData != null) {
//                    TODO: This is v1 and is depreciated need to update the active campaign method and
//                    DigitalPreExData digitalPreExData = getFitnessPlaygroundService().getDigitalPreExData(enrolmentData);

//                    TODO: This needs to be implemented with Gmail with the contract writer, should be able to remove this step
//                    getActiveCampaignService().handleEnrolmentCommunications(enrolmentData, digitalPreExData);

                    PreExData preExData =  getFitnessPlaygroundService().getPreExData(enrolmentData.getFirstName(), enrolmentData.getLastName(), enrolmentData.getPhone(), enrolmentData.getEmail());

                    getEmailService().sendEnrolmentDataNotificationToCoach(enrolmentData, preExData,  null, null);
                    getFitnessPlaygroundService().handleWebReferrals(enrolmentData);
                }
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            case "fpCoachEnrolment":
                FpCoachEnrolmentData fpCoachEnrolmentData = getFpCoachService().getFpCoachEnrolmentData(entityId, UID, false);
                if (fpCoachEnrolmentData != null) {
                    getActiveCampaignService().handleFpCoachCommunications(fpCoachEnrolmentData);
                    getEmailService().sendFpCoachNotificationToCoach(fpCoachEnrolmentData);
                }
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            default:
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }



    @RequestMapping(value = "referral", method = RequestMethod.POST)
    public ResponseEntity saveReferral(@RequestHeader Map<String, String> headers, @RequestBody ReferralSubmission submission) {

        logger.info("Inside saveReferral");

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        getFpEnrolmentService().saveReferral(submission);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    @RequestMapping(value = "referral", method = RequestMethod.PUT)
    public ResponseEntity updateReferral(@RequestHeader Map<String, String> headers, @RequestBody ReferralSubmission submission) {

        logger.info("Inside updateReferral");

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        getFpEnrolmentService().updateReferral(submission);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    @RequestMapping(value = "referral/{enrolmentDataId}", method = RequestMethod.GET)
    public List<ReferralData> getReferrals(@RequestHeader Map<String, String> headers, @PathVariable Long enrolmentDataId) {

        logger.info("Inside getReferrals");

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return null;
        }

        List<ReferralData> data = getFpEnrolmentService().getReferrals(enrolmentDataId);

        for (ReferralData d : data) {
            d.setEnrolmentData(null);
        }

        return data;
    }



    @RequestMapping(value = "preEx/{preExType}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity savePreEx(@RequestHeader Map<String, String> headers, @RequestBody String body, @PathVariable String preExType) {

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        logger.info("Inside Receive Pre Ex Controller");

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        switch (preExType) {
            case "digitalPreEx":
                PreExData data = new Gson().fromJson(body, PreExData.class);
                getFormsService().handleDigitalPreExSubmission(data);
                break;
            default:
                logger.error("Pre Ex type {} not found",preExType);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


    @RequestMapping(value = "searchPreEx", method = RequestMethod.POST)
    public PreExData searchPreExData(@RequestHeader Map<String, String> headers, @RequestBody FindEnrolment searchBody) {

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        logger.info("Inside Search Pre Ex");

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return null;
        }

        return getFitnessPlaygroundService().getPreExData(searchBody.getFirstName(), searchBody.getLastName(), searchBody.getPhone(), searchBody.getEmail());
    }


    @RequestMapping(value = "parq", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveParq(@RequestHeader Map<String, String> headers, @RequestBody String body) {

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        logger.info("Inside Receive Par-Q");

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        ParqData parqData = new Gson().fromJson(body, ParqData.class);

//        logger.info("BODY:\n{}",body);
//        logger.info("NEW PAR Q:\n{}",parqData.toString());

        getFormsService().handleParqSubmission(parqData);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


    @RequestMapping(value = "getGyms", method = RequestMethod.GET)
    public Iterable<Gym> getGyms(@RequestHeader Map<String, String> headers) {

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        logger.info("Inside getGyms");

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return null;
        }

        return getFitnessPlaygroundService().getGyms();
    }


    @RequestMapping(value = "gyms/{location}", method = RequestMethod.GET)
    public Gym getGym(@RequestHeader Map<String, String> headers, @PathVariable String location) {

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return null;
        }

        return getFitnessPlaygroundService().getGymsByLocation(location);
    }


    @RequestMapping(value = "promotionsHubReset", method = RequestMethod.GET)
    public ResponseEntity promotionsHubReset(@RequestHeader Map<String, String> headers) {

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        logger.info("Inside promotionsHubReset");

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        getFitnessPlaygroundService().promotionsHubReset();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value = "keapAuthToken", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Credentials getKeapAuthToken(@RequestHeader Map<String, String> headers) {
        logger.info("\nInside getKeapAuthToken");
        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return null;
        }

        return getKeapService().getKeapToken();
    }


    @RequestMapping(value = "keapAuthToken", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateKeapAuthToken(@RequestHeader Map<String, String> headers, @RequestBody KeapTokenResponse keapTokenResponse) {
        logger.info("\nInside updateKeapAuthToken");
        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        try {
            Boolean flag = getKeapService().updateKeapCredential(keapTokenResponse);
            if (flag) {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity(HttpStatus.I_AM_A_TEAPOT);
            }

        } catch (Exception ex) {
            logger.error("Error updating Keap token: {}",ex.getMessage());
            return new ResponseEntity(HttpStatus.I_AM_A_TEAPOT);
        }
    }


    private boolean validateStaffId(String UID) {
        return getFitnessPlaygroundService().validateStaffId(UID);
    }


    public FitnessPlaygroundService getFitnessPlaygroundService() {
        return fitnessPlaygroundService;
    }

    @Autowired
    public void setFitnessPlaygroundService(FitnessPlaygroundService fitnessPlaygroundService) {
        this.fitnessPlaygroundService = fitnessPlaygroundService;
    }

    public MindBodyService getMindBodyService() {
        return mindBodyService;
    }

    @Autowired
    public void setMindBodyService(MindBodyService mindBodyService) {
        this.mindBodyService = mindBodyService;
    }

    public FpCoachService getFpCoachService() {
        return fpCoachService;
    }

    @Autowired
    public void setFpCoachService(FpCoachService fpCoachService) {
        this.fpCoachService = fpCoachService;
    }

    public FpEnrolmentService getFpEnrolmentService() {
        return fpEnrolmentService;
    }

    @Autowired
    public void setFpEnrolmentService(FpEnrolmentService fpEnrolmentService) {
        this.fpEnrolmentService = fpEnrolmentService;
    }

    public FormsService getFormsService() {
        return formsService;
    }

    @Autowired
    public void setFormsService(FormsService formsService) {
        this.formsService = formsService;
    }

    public FpAcademyService getFpAcademyService() {
        return fpAcademyService;
    }

    @Autowired
    public void setFpAcademyService(FpAcademyService fpAcademyService) {
        this.fpAcademyService = fpAcademyService;
    }

    public ActiveCampaignService getActiveCampaignService() {
        return activeCampaignService;
    }

    @Autowired
    public void setActiveCampaignService(ActiveCampaignService activeCampaignService) {
        this.activeCampaignService = activeCampaignService;
    }

    public EmailService getEmailService() {
        return emailService;
    }

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    public KeapService getKeapService() {
        return keapService;
    }

    @Autowired
    public void setKeapService(KeapService keapService) {
        this.keapService = keapService;
    }
}


