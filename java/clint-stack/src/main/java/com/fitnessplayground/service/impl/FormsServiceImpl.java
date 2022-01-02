package com.fitnessplayground.service.impl;

import com.fitnessplayground.dao.FPOpsConfigDao;
import com.fitnessplayground.dao.MemberDao;
import com.fitnessplayground.dao.StaffDao;
import com.fitnessplayground.dao.SubmissionErrorDao;
import com.fitnessplayground.dao.domain.*;
import com.fitnessplayground.dao.domain.formstackDto.PtReassignSubmission;
import com.fitnessplayground.dao.domain.ops.FPOpsConfig;
import com.fitnessplayground.dao.domain.temp.CancellationDataSubmission;
import com.fitnessplayground.dao.domain.temp.SearchByPersDetails;
import com.fitnessplayground.dao.domain.temp.SubmissionArray;
import com.fitnessplayground.service.*;
import com.fitnessplayground.util.Constants;
import com.fitnessplayground.util.Helpers;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FormsServiceImpl implements FormsService {

    private static final Logger logger = LoggerFactory.getLogger(FormsServiceImpl.class);

    private RestTemplate restTemplate;

    private MemberDao memberDao;
    private EncryptionService encryptionService;
    private SubmissionErrorDao submissionErrorDao;
    private StaffDao staffDao;
    private FPOpsConfigDao fpOpsConfigDao;
    private ActiveCampaignService activeCampaignService;
    private EmailService emailService;

    @Value("${lambda.fp.forms.cancellation.reminder}")
    private String SEND_CANCELLATION_REMINDER;

    @Value("${lambda.fp.forms.membership.change.reminder}")
    private String SEND_MEMBERSHIP_CHANGE_REMINDER;



    @Override
    public CancellationData handleCancellationRequest(CancellationDataSubmission submission) {

        CancellationData cancellationData = CancellationData.create(submission);

        if (cancellationData.getCreditCard() != null) {
            cancellationData.setCreditCard(getEncryptionService().encryptCMemberCreditCard(cancellationData.getCreditCard()));
        }

        if (cancellationData.getMemberBankDetail() != null) {
            cancellationData.setMemberBankDetail(getEncryptionService().encryptMemberBankDetail(cancellationData.getMemberBankDetail()));
        }

//        logger.info("handleCancellationRequest\n{}\n",cancellationData.toString());

        cancellationData = getMemberDao().saveCancellationData(cancellationData);

        if (cancellationData != null && cancellationData.getStatus().equals(MemberStatus.MEMBERSHIP_TRANSFER.getStatus())) {
            EnrolmentSubmissionError error = new EnrolmentSubmissionError(
                    MemberStatus.MEMBERSHIP_TRANSFER.getStatus(),
                    MemberStatus.MEMBERSHIP_TRANSFER.getDescription(),
                    MemberStatus.MEMBERSHIP_TRANSFER.getStatusCode(),
                    Helpers.getDateNow(),
                    1,
                    null,
                    null,
                    null,
                    null,
                    cancellationData,
                    null,
                    null,
                    null);

            getSubmissionErrorDao().saveSubmissionError(error);
        }

//        Send Internal Notifications here
        if (cancellationData.getHasCoach() != null
                && (cancellationData.getHasCoach() || cancellationData.getCancellationOptions().contains("Personal Training"))
                && cancellationData.getStatus().equals(MemberStatus.CANCELLATION_AUTHORISED.getStatus()) ) {

            if (isCancellationPtNotificationOn()) {
                getEmailService().sendCancellationNotificationToCoach(cancellationData);
            }

//            Update PtTracker
            try {
                SearchByPersDetails persDetails = new SearchByPersDetails(cancellationData.getFirstName(), cancellationData.getLastName(), cancellationData.getEmail(), cancellationData.getPhone());
                List<EnrolmentData> enrolments = getMemberDao().searchEnrolmentDataByPersDetails(persDetails);

                if (enrolments.size() > 0) {
                    PtTracker ptTracker;
                    for (EnrolmentData e : enrolments) {
                        ptTracker = getMemberDao().findPtTrackerByEnrolmentDataId(e.getId());
                        if (ptTracker != null) {
                            ptTracker.setCancellationDataId(cancellationData.getId());
                            ptTracker.setStatus(MemberStatus.PT_CANCELLED.getStatus());
                            getMemberDao().updatePtTracker(ptTracker);
                        }
                    }
                }
            } catch (Exception ex) {
                logger.error("Error updating PtTracker Cancellation: {}",ex.getMessage());
            }
        }

        return cancellationData;
    }

    @Override
    public CancellationData getPhoneCancellation(String fsFormId, String fsUniqueId) {
        return getMemberDao().getCancellationDataByFormstackIds(fsFormId, fsUniqueId);
    }

    @Override
    public CancellationData getPart1CancellationByLastNameAndUniqueId(String lastName, String fsUniqueId) {
        return getMemberDao().getCancellationDataByFsUniqueIdAndLastName(lastName, fsUniqueId);
    }

    @Override
    public CancellationData updateCancellationData(CancellationData cancellationData) {

//        Send Internal Notifications here
        if (cancellationData.getHasCoach() != null
                && (cancellationData.getHasCoach() || cancellationData.getCancellationOptions().contains("Personal Training"))
                && cancellationData.getStatus().equals(MemberStatus.CANCELLATION_AUTHORISED.getStatus()) ) {

            if (isCancellationPtNotificationOn()) {
                getEmailService().sendCancellationNotificationToCoach(cancellationData);
            }
        }

        return getMemberDao().updateCancellationData(cancellationData);
    }

    @Override
    public ArrayList<CancellationData> getPendingCancellations() {
        return getMemberDao().getCancellationsByStatus(MemberStatus.CANCELLATION_AUTHORISATION_PENDING.getStatus());
    }

    @Override
    public CancellationData sendCancellationReminder(CancellationData c) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CancellationData> entity = new HttpEntity<>(c);
//        CancellationReminderResponse response = null;
        try {
//            response = getRestTemplate().exchange(SEND_CANCELLATION_REMINDER, HttpMethod.POST, entity, CancellationReminderResponse.class).getBody();
            getRestTemplate().exchange(SEND_CANCELLATION_REMINDER, HttpMethod.POST, entity, String.class).getBody();
            c.setCommunicationStatus(CommunicationsStatus.CLIENT_AUTHORISATION_REMINDER_SENT.getStatus());

        } catch(Exception ex) {
            logger.error("Error Sending Cancellation Reminder: {} {}",c.getId(), ex.getMessage());
            c.setCommunicationStatus(CommunicationsStatus.CLIENT_AUTHORISATION_ERROR.getStatus());
        }

        return c;
    }


    @Override
    public MembershipChangeData handleMembershipChange(MembershipChangeData membershipChangeData) {
        logger.info("handleMembershipChange");

//        Send Internal Notifications here
        sendMembershipChangeInternalComms(membershipChangeData);

        return getMemberDao().saveMembershipChangeData(membershipChangeData);
    }

    @Override
    public MembershipChangeData getPhoneMembershipChange(String fsFormId, String fsUniqueId) {
        return getMemberDao().getMembershipChangeDataByFormstackIds(fsFormId, fsUniqueId);
    }

    @Override
    public MembershipChangeData updateMembershipChangeData(MembershipChangeData membershipChangeData) {

//        Send Internal Notifications here
        sendMembershipChangeInternalComms(membershipChangeData);

        return getMemberDao().updateMembershipChangeData(membershipChangeData);
    }

    private void sendMembershipChangeInternalComms(MembershipChangeData membershipChangeData) {
        if (membershipChangeData.getStatus().equals(MemberStatus.MEMBERSHIP_CHANGE_AUTHORISED.getStatus()) && membershipChangeData.getChangeTypeMembership().contains("Update My Coaching")) {

            if (membershipChangeData.getChangeCoaching().contains("Suspend" )) {
                getEmailService().sendSuspensionNotificationToCoach(membershipChangeData);
            }

            if (membershipChangeData.getChangeCoaching().contains("Upgrade") || membershipChangeData.getChangeCoaching().contains("Downgrade")) {
                getEmailService().sendChangeNotificationToCoach(membershipChangeData);
            }
        }
    }

    @Override
    public ArrayList<MembershipChangeData> getPendingMembershipChange() {
        return getMemberDao().getMembershipChangeDataByStatus(MemberStatus.MEMBERSHIP_CHANGE_AUTHORISATION_PENDING.getStatus());
    }

    @Override
    public MembershipChangeData sendMembershipChangeReminder(MembershipChangeData membershipChangeData) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MembershipChangeData> entity = new HttpEntity<>(membershipChangeData);
//        CancellationReminderResponse response = null;
        try {
            getRestTemplate().exchange(SEND_MEMBERSHIP_CHANGE_REMINDER, HttpMethod.POST, entity, String.class).getBody();
            membershipChangeData.setCommunicationStatus(CommunicationsStatus.CLIENT_AUTHORISATION_REMINDER_SENT.getStatus());

        } catch(Exception ex) {
            logger.error("Error Sending Membership Change Reminder: {} {}",membershipChangeData.getId(), ex.getMessage());
            membershipChangeData.setCommunicationStatus(CommunicationsStatus.CLIENT_AUTHORISATION_ERROR.getStatus());
        }

        return membershipChangeData;
    }

    @Async
    @Override
    public void handlePtReassign(PtReassignSubmission ptReassignSubmission) {

        EnrolmentData enrolmentData;
        FpCoachEnrolmentData fpCoachEnrolmentData;
        SubmissionArray coach;
        Staff staff;
        Long ACID = null;
        String queryParams = null;

        try {
            if (ptReassignSubmission.getCoachSurryHills() != null && ptReassignSubmission.getCoachSurryHills().length == 1) {
                coach = ptReassignSubmission.getCoachSurryHills()[0];
            } else if (ptReassignSubmission.getCoachNewtown() != null && ptReassignSubmission.getCoachNewtown().length == 1) {
                coach = ptReassignSubmission.getCoachNewtown()[0];
            } else if (ptReassignSubmission.getCoachMarrickville() != null && ptReassignSubmission.getCoachMarrickville().length == 1) {
                coach = ptReassignSubmission.getCoachMarrickville()[0];
            } else if (ptReassignSubmission.getCoachBunker() != null && ptReassignSubmission.getCoachBunker().length == 1) {
                coach = ptReassignSubmission.getCoachBunker()[0];
            } else {
                coach = null;
            }
        } catch (Exception ex) {
            logger.error("No Coach Data: {}",ex.getMessage());
            coach = null;
        }

//        Update EnrolmentData
        if (ptReassignSubmission.getEnrolmentDataId() != null && !ptReassignSubmission.getEnrolmentDataId().isEmpty()) {

            enrolmentData = getMemberDao().findEnrolmentDataById(Long.parseLong(ptReassignSubmission.getEnrolmentDataId()));

            if (ptReassignSubmission.getNotes() != null) {
                String notes = enrolmentData.getNotes() + "\n\nPT Reassign Notes:\n" + ptReassignSubmission.getNotes();
                enrolmentData.setNotes(notes);
            }

            PreExData preExData = getMemberDao().searchPreExData(enrolmentData.getFirstName(), enrolmentData.getLastName(), enrolmentData.getPhone(), enrolmentData.getEmail());
            ParqData parqData = getMemberDao().searchParqData(enrolmentData.getFirstName(), enrolmentData.getLastName(), enrolmentData.getPhone(), enrolmentData.getEmail());

            if (enrolmentData != null && coach != null) {
                ACID = enrolmentData.getActiveCampaignId() != null ?  Long.parseLong(enrolmentData.getActiveCampaignId()) : null;
                enrolmentData.setPersonalTrainer(coach.getValue());
                enrolmentData.setPersonalTrainerName(coach.getLabel());
                getMemberDao().updateEnrolmentData(enrolmentData);

                PtTracker ptTracker = null;
                if (ptReassignSubmission.getPtTrackerId() != null && !ptReassignSubmission.getPtTrackerId().isEmpty()) {
                    ptTracker = getMemberDao().getPtTrackerById(Long.parseLong(ptReassignSubmission.getPtTrackerId()));
                    ptTracker.setPersonalTrainer(coach.getValue());
                    ptTracker.setReassigned(true);
                    ptTracker = getMemberDao().updatePtTracker(ptTracker);
                }

                // Send Notification to Coach
                getEmailService().sendEnrolmentDataNotificationToCoach(enrolmentData, preExData, parqData, ptTracker);

            } else {
                getEmailService().sendErrorNotification("Trying to Re-assign PT Lead without Enrolment Ids\nEmail: "+ptReassignSubmission.getEmail(), "handlePtReassign");
            }
        }

//        Update FpCoachEnrolmentData
        if (ptReassignSubmission.getFpCoachEnrolmentId() != null && !ptReassignSubmission.getFpCoachEnrolmentId().isEmpty()) {

            fpCoachEnrolmentData = getMemberDao().getFpCoachEnrolmentDataById(Long.parseLong(ptReassignSubmission.getFpCoachEnrolmentId()));

            if (fpCoachEnrolmentData != null && coach != null) {
                ACID = fpCoachEnrolmentData.getActiveCampaignId() != null ? Long.parseLong(fpCoachEnrolmentData.getActiveCampaignId()) : null;
                fpCoachEnrolmentData.setPersonalTrainer(coach.getValue());
                fpCoachEnrolmentData.setPersonalTrainerName(coach.getLabel());
                getMemberDao().updateFpCoachEnrolmentData(fpCoachEnrolmentData);
                // Send Notification to Coach
//                getEmailService().setFpCoachEnrolmentDataNotificationToCoach(fpCoachEnrolmentData);
                try {
                    queryParams = "name-first=" + URLEncoder.encode(fpCoachEnrolmentData.getFirstName(), StandardCharsets.UTF_8.toString())
                            + "&name-last=" + URLEncoder.encode(fpCoachEnrolmentData.getLastName(), StandardCharsets.UTF_8.toString())
                            + "&email=" + URLEncoder.encode(fpCoachEnrolmentData.getEmail(), StandardCharsets.UTF_8.toString())
                            + "&phone=" + URLEncoder.encode(fpCoachEnrolmentData.getPhone(), StandardCharsets.UTF_8.toString())
                            + "&locationId=" + URLEncoder.encode(Integer.toString(fpCoachEnrolmentData.getLocationId()), StandardCharsets.UTF_8.toString())
                            + "&coach+name=" + URLEncoder.encode(fpCoachEnrolmentData.getPersonalTrainerName(), StandardCharsets.UTF_8.toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    logger.error("Error building Query Params");
                }
            }
        }

//        Update Active Campaign
        if (coach != null && ACID != null) {
            String[] split = coach.getValue().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
            Long mboId = Long.parseLong(split[0]);
            Long siteId = Long.parseLong(split[1]);
            staff = getStaffDao().getStaffByMboId(mboId, siteId);
            if (staff != null) {
                getActiveCampaignService().processPtReassign(ACID, staff, queryParams);
            }
        }

    }

    @Async
    @Override
    public void handleDigitalPreExSubmission(PreExData preExData) {

        try {
            getMemberDao().savePreExData(preExData);
        } catch (Exception ex) {
            logger.error("Error handleDigitalPreExSubmission id: [{}] : {}",preExData.getId(), ex.getMessage());
        }

    }

    @Async
    @Override
    public void handleParqSubmission(ParqData parqData) {

        try {
            parqData = getMemberDao().saveParqData(parqData);

            if (parqData.getFs_formId().equals(Constants.FS_FORM_ID_PARQ_TRAINER)) {
                getEmailService().sendParqTrainerNotification(parqData);
                return;
            }

            PtTracker ptTracker = getMemberDao().getPtTrackerById(parqData.getPtTrackerId());

            if (ptTracker == null) {
                logger.error("Error No PtTracker Id Parq id: {}",parqData.getId());
                return;
            }

            EnrolmentData enrolmentData = null;
            if (ptTracker.getEnrolmentDataId() != null) {
                enrolmentData = getMemberDao().getEnrolmentDataById(ptTracker.getEnrolmentDataId());
            }


            PreExData preExData = null;
            if (ptTracker.getPreExId() != null) {
                preExData = getMemberDao().getPreExDataById(ptTracker.getPreExId());
            }

            //            TODO: update PtTracker status
            ptTracker.setParqId(parqData.getId());
            ptTracker.setStatus(MemberStatus.PAR_Q_RECEIVED.getStatus());
            ptTracker = getMemberDao().updatePtTracker(ptTracker);

            getEmailService().sendEnrolmentDataNotificationToCoach(enrolmentData, preExData, parqData, ptTracker);


        } catch (Exception ex) {
            logger.error("Error handleParqSubmission id: [{}] : {}",parqData.getId(), ex.getMessage());
        }

        return;
    }


    private boolean isCancellationPtNotificationOn() {
        return isOperationOnline(getFpOpsConfigDao().findByName("CancellationPtNotificationOn"));
    }

    private boolean isOperationOnline(FPOpsConfig fpOpsConfig) {
        if(null != fpOpsConfig && fpOpsConfig.getValue().equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ArrayList<PtTracker> getPendingParq() {
        return getMemberDao().getPtTrackerByStatus(MemberStatus.PAR_Q_PENDING.getStatus());
    }

    @Override
    public PtTracker updatePtTracker(PtTracker ptTracker) {
        return getMemberDao().updatePtTracker(ptTracker);
    }


    public MemberDao getMemberDao() {
        return memberDao;
    }

    @Autowired
    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public EncryptionService getEncryptionService() {
        return encryptionService;
    }

    @Autowired
    public void setEncryptionService(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    public SubmissionErrorDao getSubmissionErrorDao() {
        return submissionErrorDao;
    }

    @Autowired
    public void setSubmissionErrorDao(SubmissionErrorDao submissionErrorDao) {
        this.submissionErrorDao = submissionErrorDao;
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

    public EmailService getEmailService() {
        return emailService;
    }

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    public FPOpsConfigDao getFpOpsConfigDao() {
        return fpOpsConfigDao;
    }

    @Autowired
    public void setFpOpsConfigDao(FPOpsConfigDao fpOpsConfigDao) {
        this.fpOpsConfigDao = fpOpsConfigDao;
    }
}
