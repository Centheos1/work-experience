package com.fitnessplayground.service.impl;

import com.fitnessplayground.dao.MemberDao;
import com.fitnessplayground.dao.StaffDao;
import com.fitnessplayground.dao.domain.*;
import com.fitnessplayground.dao.domain.lambdaDto.GymSalesAddPeopleResponse;
import com.fitnessplayground.dao.domain.WebFlowDto.WebflowApiLeadSubmissionData;
import com.fitnessplayground.dao.domain.lambdaDto.TwilioSendSmsRequest;
import com.fitnessplayground.dao.domain.mboDto.Client;
import com.fitnessplayground.dao.domain.temp.FindEnrolment;
import com.fitnessplayground.dao.domain.temp.GravityFormsWebhookReferral;
import com.fitnessplayground.dao.domain.temp.WebLead;
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

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class FpWebsiteServiceImpl implements FpWebsiteService {

    private static final Logger logger = LoggerFactory.getLogger(FpWebsiteServiceImpl.class);

    private ActiveCampaignService activeCampaignService;
    private EmailService emailService;
    private MindBodyService mindBodyService;
    private RestTemplate restTemplate;
    private MemberDao memberDao;
    private StaffDao staffDao;

    @Value("${lambda.gymsales.newlead}")
    private String GYMSALES_ADD_NEW_LEAD;

    @Value("${lambda.twilio.send.sms}")
    private String TWILIO_SERVICE_SEND_SMS;

    @Value("${bitly.fp.vip.pass}")
    private String VIP_PASS_BITLY_FP;

    @Value("${bitly.bk.vip.pass}")
    private String VIP_PASS_BITLY_BK;

    @Value("${bitly.fp.two.weeks.free}")
    private String TWO_WEEKS_FREE_BITLY_FP;

    @Value("${bitly.bk.two.weeks.free}")
    private String TWO_WEEKS_FREE_BITLY_BK;

    @Value("${mail.notify.fpacademy}")
    private String FP_ACADEMY_EMAIL_ADDRESS;

    @Override
    public LeadData handleLead(String submissionData) {

        LeadData leadData = new LeadData();

        try {
            String notes = "";
            for (String s : submissionData.split("&")) {

                String[] split = s.split("=");
                if (split.length == 2) {

                    //                Name
                    if (split[0].trim().equalsIgnoreCase("name")) {
                        leadData = handleName(leadData, split[1]);
                    }
                    //                Phone
                    if (split[0].trim().equalsIgnoreCase("phone")) {
                        leadData.setPhone(split[1].trim());
                    }
                    //                Email
                    if (split[0].trim().equalsIgnoreCase("email")) {
                        leadData.setEmail(split[1].trim());
                    }
                    //                Location
                    if (split[0].trim().equalsIgnoreCase("location") || split[0].trim().equalsIgnoreCase("gym") || split[0].trim().equalsIgnoreCase("gym name")) {
                        leadData.setGymName(split[1].trim());
                    }
                    //                Source Name - What is the offer
                    if (split[0].trim().equalsIgnoreCase("source_name") || split[0].trim().equalsIgnoreCase("campaign_offer")) {
                        leadData.setSource_name(split[1].trim());
                    }
                    //                 Google Click Id
                    if (split[0].trim().equalsIgnoreCase("gclid")) {
                        leadData.setGoogleClickId(split[1]);
                    }

//                    Fitness Challenge
                    if (split[0].trim().equalsIgnoreCase("fitness-challenge")) {
                        notes += "Fitness Challenge: " + split[1] + ", ";
                    }
//                    Course Type - Study With Us
                    if (split[0].trim().equalsIgnoreCase("course")) {
                        notes += "Course: " + split[1] + ", ";
                    }
//                    Study Preference - Study With Us
                    if (split[0].trim().equalsIgnoreCase("study-preference")) {
                        notes += "Study Preference: " + split[1] + ", ";
                    }
//                    Notes
                    if (split[0].trim().equalsIgnoreCase("notes")) {
                        notes += split[1] + ", ";
                    }
                }
            }

            if (leadData.getGymName().contains("Darwin")) {
                leadData.setGymName("Gateway");
            }

            if (leadData.getGymName() == null) {
                leadData.setGymName("Surry Hills");
            }

            leadData.setContact_method_name("Internet");
            leadData.setStatus(LeadStatus.RECEIVED.getStatus());
            leadData.setCreateDate(Helpers.getDateNow());
            leadData.setUpdateDate(Helpers.getDateNow());
            leadData.setNotes(notes);

            leadData = getMemberDao().saveLeadData(leadData);

        } catch (Exception e) {
            logger.error("Error creating LeadData {}",e.getMessage());
            return null;
        }

        return leadData;
    }


    @Async
    @Override
    public void processFormSubmission(LeadData d) {

        logger.info("Inside processFormSubmission: {}",d);

        CompletableFuture<LeadData> submissionCompletableFuture = completeSubmission(d);

        CompletableFuture<LeadData> result = submissionCompletableFuture.thenApply(leadData -> leadData);

        try {
            LeadData data = result.get();

            logger.info("Web Form Submission Complete: {}",data);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processWebflowApiFormSubmission(WebflowApiLeadSubmissionData submission) {

        logger.info("processWebflowApiFormSubmission() {}",submission);

        LeadData leadData = new LeadData();
        LeadData part_1 = null;

        if (submission.getSource_id() != null && !submission.getSource_id().isEmpty()) {
//            Part 2 submission, retrieve data from the database
            try {
                part_1 = getMemberDao().getLeadDataById(Long.parseLong(submission.getSource_id()));
            } catch (NumberFormatException ex) {
                logger.error("Error parsing source_id {} to Long: {}",submission.getSource_id(),ex.getMessage());
            }
        }

        leadData.setStatus(LeadStatus.RECEIVED.getStatus());
        leadData.setCreateDate(Helpers.getDateNow());
        leadData.setUpdateDate(Helpers.getDateNow());

        if (submission.getGclid() != null && !submission.getGclid().equals("null")) {
            leadData.setGoogleClickId(submission.getGclid());
        } else {
            leadData.setGoogleClickId(part_1.getGoogleClickId());
        }

        if (submission.getCampaign_offer() != null) {
            leadData.setSource_name(submission.getCampaign_offer());
        }

        if (part_1 != null) {

            String notes = "";

            try {
                leadData.setFirst_name(part_1.getFirst_name());
                leadData.setLast_name(part_1.getLast_name());
                leadData.setEmail(part_1.getEmail());
                leadData.setPhone(part_1.getPhone());
                leadData.setGymName(part_1.getGymName());

                if (submission.getFitness_Challenge() != null && !submission.getFitness_Challenge().equals("null")) {
                    notes += "Fitness Challenge: " + submission.getFitness_Challenge() + ", ";
                }

                if (submission.getNotes() != null && !submission.getNotes().equals("null")) {
                    notes += "Notes: " + submission.getNotes() + ", ";
                }

                leadData.setNotes(notes);

            } catch (Exception e) {
                leadData.setStatus(LeadStatus.ERROR.getStatus());
                logger.error("Error building LeadData from Part 1 Submission: {}",leadData.getId());
            }

        } else {

            if (submission.getName() != null) {
                leadData = handleName(leadData, submission.getName());
            } else {
                leadData.setStatus(LeadStatus.ERROR.getStatus());
            }

            if (submission.getEmail() != null && !leadData.getStatus().equals(LeadStatus.ERROR.getStatus())) {
                leadData.setEmail(submission.getEmail());
            } else {
                leadData.setStatus(LeadStatus.ERROR.getStatus());
            }

            if (!leadData.getStatus().equals(LeadStatus.ERROR.getStatus())) {

                if (submission.getPhone() != null) {
                    leadData.setPhone(submission.getPhone());
                }

                if ((submission.getLocation() != null)) {
                    leadData.setGymName(submission.getLocation());
                }
            }
        }

        if (leadData.getGymName().contains("Darwin")) {
            leadData.setGymName("Gateway");
        }

        //        Send to GymSales -> Lambda returns partial LeadData entity
        leadData = processActiveCampaignAndGymSales(leadData);

        getMemberDao().saveLeadData(leadData);
    }

    @Async
    @Override
    public void handleWebLead(WebLead webLead) {

        LeadData leadData = new LeadData();
        boolean willProcess = true;

        try {
            leadData.setStatus(LeadStatus.RECEIVED.getStatus());
            leadData.setCreateDate(Helpers.getDateNow());
            leadData.setUpdateDate(Helpers.getDateNow());
            leadData = handleName(leadData, webLead.getName());
            leadData.setPhone(webLead.getPhone());
            leadData.setEmail(webLead.getEmail());
            if (webLead.getLocation() == null || webLead.getLocation().isEmpty()) {
                leadData.setGymName(Constants.BUNKER_GYM_NAME);
            } else {
                leadData.setGymName(webLead.getLocation());
            }
            leadData.setSource_name(webLead.getSource());

            if (leadData.getSource_name().equalsIgnoreCase("free-lower-body-workout")
                    || leadData.getSource_name().equalsIgnoreCase("free-upper-body-workout")
                    || leadData.getSource_name().equalsIgnoreCase("free-full-body-workout")) {
                willProcess = false;
            }

            if (webLead.getNotes() != null && !webLead.getNotes().isEmpty()) {
                leadData.setNotes(webLead.getNotes());
            }

            leadData.setFacebookCampaignId(webLead.getFacebookCampaignId());
            leadData.setGoogleClickId(webLead.getGoogleClickId());

        } catch (Exception ex) {
            logger.error("Error Processing Weblead: {}",ex.getMessage());
            leadData.setStatus(LeadStatus.ERROR.getStatus());
            willProcess = false;
        }

        leadData = getMemberDao().saveLeadData(leadData);

        if (willProcess) {
            logger.info("Sending Web Lead id: {} to be processed", leadData.getId());
            processFormSubmission(leadData);
        }

        logger.info("handleWebLead id: {} done", leadData.getId());
    }

    @Async
    @Override
    public void handleClassReview(ClassReviewData classReviewData) {

        try {
            getMemberDao().saveClassReviewData(classReviewData);
        } catch (Exception ex) {
            logger.error("Error handling class review {}: {}",classReviewData.getId(), ex.getMessage());
        }

    }

    @Override
    public WebReferralData handleWebReferral(String submissionData) {

        WebReferralData webReferralData = new WebReferralData();

        webReferralData.setCreateDate(Helpers.getDateNow());

        try {
            for (String s : submissionData.split("&")) {
                String[] split = s.split("=");

                logger.info("handleWebReferral: s: {} | split: {}",s,split);

                if (split.length == 2) {

//                    redirect_url=https://fitnessplayground.com.au/refer-a-friend-thank-you&
                    if (split[0].trim().equalsIgnoreCase("redirect_url")) {
                        webReferralData.setRedirectUrl(split[1].trim());
                    }

//                    campaign_offer
                    if (split[0].trim().equalsIgnoreCase("campaign_offer")) {
                        webReferralData.setCampaignOffer(split[1].trim());
                    }

//                    referral_source
                    if (split[0].trim().equalsIgnoreCase("referral_source")) {
                        webReferralData.setReferralSource(split[1].trim());
                    }

//                    gclid
                    if (split[0].trim().equalsIgnoreCase("gclid")) {
                        webReferralData.setGoogleClickId(split[1].trim());
                    }

//                    referral_1_name
                    if (split[0].trim().equalsIgnoreCase("referral_1_name")) {
                        String name = split[1];
                        HashMap<String, String> names = handleName(name);
                        webReferralData.setReferralOneFirstName(names.get("firstName"));
                        webReferralData.setReferralOneLastName(names.get("lastName"));
                    }

//                    referral_1_phone
                    if (split[0].trim().equalsIgnoreCase("referral_1_phone")) {
                        webReferralData.setReferralOnePhone(split[1].trim());
                    }

//                    member_name
                    if (split[0].trim().equalsIgnoreCase("member_name")) {
                        String name = split[1];
                        HashMap<String, String> names = handleName(name);
                        webReferralData.setMemberFirstName(names.get("firstName"));
                        webReferralData.setMemberLastName(names.get("lastName"));
                    }

//                    member_email
                    if (split[0].trim().equalsIgnoreCase("member_email")) {
                        webReferralData.setMemberEmail(split[1].trim());
                    }

//                    gymName
                    if (split[0].trim().equalsIgnoreCase("location") || split[0].trim().equalsIgnoreCase("gym") || split[0].trim().equalsIgnoreCase("gymName")) {
                        webReferralData.setGymName(split[1].trim());
                    }

//                    referral_2_name
                    if (split[0].trim().equalsIgnoreCase("referral_2_name")) {
                        String name = split[1];
                        HashMap<String, String> names = handleName(name);
                        webReferralData.setReferralTwoFirstName(names.get("firstName"));
                        webReferralData.setReferralTwoLastName(names.get("lastName"));
                    }

//                    referral_2_phone
                    if (split[0].trim().equalsIgnoreCase("referral_2_phone")) {
                        webReferralData.setReferralTwoPhone(split[1].trim());
                    }

//                    referral_3_name
                    if (split[0].trim().equalsIgnoreCase("referral_3_name")) {
                        String name = split[1];
                        HashMap<String, String> names = handleName(name);
                        webReferralData.setReferralThreeFirstName(names.get("firstName"));
                        webReferralData.setReferralThreeLastName(names.get("lastName"));
                    }

//                    referral_3_phone
                    if (split[0].trim().equalsIgnoreCase("referral_3_phone")) {
                        webReferralData.setReferralThreePhone(split[1].trim());
                    }

                }
            }
        } catch (Exception ex) {
            logger.error("Error handling Web Referral {}: {}",webReferralData.getId(),ex.getMessage());
        }

        return webReferralData;
    }


    @Override
    public WebReferralData handleGravityFormsWebReferral(GravityFormsWebhookReferral gravityFormsWebhookReferral) {

        WebReferralData webReferralData = new WebReferralData();
        HashMap<String, String> names;

        webReferralData.setCreateDate(Helpers.getDateNow());

        try {

//            campaign_offer
            webReferralData.setCampaignOffer(gravityFormsWebhookReferral.getCampaignOffer());

//            referral_source
            webReferralData.setReferralSource(gravityFormsWebhookReferral.getReferralSource());

//            gclid
            webReferralData.setGoogleClickId(gravityFormsWebhookReferral.getGoogleClickId());


//            referral_1_name
            names = handleName(gravityFormsWebhookReferral.getReferralOneFullName());
            webReferralData.setReferralOneFirstName(names.get("firstName"));
            webReferralData.setReferralOneLastName(names.get("lastName"));
            names.clear();

//            referral_1_phone
            webReferralData.setReferralOnePhone(gravityFormsWebhookReferral.getReferralOnePhone());

//            referral_2_name
            if (gravityFormsWebhookReferral.getReferralTwoFullName() != null && !gravityFormsWebhookReferral.getReferralTwoFullName().isEmpty()) {
                names = handleName(gravityFormsWebhookReferral.getReferralTwoFullName());
                webReferralData.setReferralTwoFirstName(names.get("firstName"));
                webReferralData.setReferralTwoLastName(names.get("lastName"));
                names.clear();
            }

//            referral_2_phone
            if (gravityFormsWebhookReferral.getReferralTwoPhone() != null && !gravityFormsWebhookReferral.getReferralTwoPhone().isEmpty()) {
                webReferralData.setReferralTwoPhone(gravityFormsWebhookReferral.getReferralTwoPhone());
            }

//            referral_3_name
            if (gravityFormsWebhookReferral.getReferralThreeFullName() != null && !gravityFormsWebhookReferral.getReferralThreeFullName().isEmpty()) {
                names = handleName(gravityFormsWebhookReferral.getReferralThreeFullName());
                webReferralData.setReferralThreeFirstName(names.get("firstName"));
                webReferralData.setReferralThreeLastName(names.get("lastName"));
                names.clear();
            }

//            referral_3_phone
            if (gravityFormsWebhookReferral.getReferralThreePhone() != null && !gravityFormsWebhookReferral.getReferralThreePhone().isEmpty()) {
                webReferralData.setReferralThreePhone(gravityFormsWebhookReferral.getReferralThreePhone());
            }

//            member_name
            names = handleName(gravityFormsWebhookReferral.getMemberFullName());
            webReferralData.setMemberFirstName(names.get("firstName"));
            webReferralData.setMemberLastName(names.get("lastName"));
            names.clear();

//            member_email
            webReferralData.setMemberEmail(gravityFormsWebhookReferral.getMemberEmail());

//            gymName
            webReferralData.setGymName(gravityFormsWebhookReferral.getGymName());



        } catch (Exception ex) {
            logger.error("Error handling Gravity Forms Web Referral {}: {}",webReferralData.getId(),ex.getMessage());
        }

        return webReferralData;
    }



    @Async
    @Override
    public void processWebReferral(WebReferralData webReferralData) {

        CompletableFuture<WebReferralData> weReferralCompletableFuture = completeWeReferral(webReferralData);

        CompletableFuture<WebReferralData> result = weReferralCompletableFuture.thenApply(leadData -> leadData);

        try {
            WebReferralData data = result.get();

            logger.info("Web Referral Submission Complete: {}",data);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }


    private CompletableFuture<WebReferralData> completeWeReferral(WebReferralData webReferralData) {

//        This is from Web Referral Submissions
        logger.info("Inside completeWeReferral()");

        TwilioSendSmsRequest smsRequest = new TwilioSendSmsRequest();
        String smsMessage;

        try {
//           Look up EnrolmentData to find staff member to attribute the lead
            try {
                if (webReferralData.getReferralSource() == null) {

//                    FindEnrolment findEnrolment = new FindEnrolment(webReferralData.getMemberFirstName(), webReferralData.getMemberLastName(), webReferralData.getMemberEmail());

                    logger.info("No referral source looking for Enrolment");

                    List<EnrolmentData> enrolments = getMemberDao().findEnrolmentsByEmail(webReferralData.getMemberEmail());
                    EnrolmentData enrolmentData = null;

                    if (enrolments != null) {
                        for (EnrolmentData e : enrolments) {
                            if (e.getFirstName().equalsIgnoreCase(webReferralData.getMemberFirstName())) {
                                enrolmentData = e;
                            }
                        }
                    }

                    if (enrolmentData != null) {

                        logger.info("Found Enrolment");

                        int dateDiff = Helpers.getDateDifference(enrolmentData.getCreateDate(), webReferralData.getCreateDate());

//                        If Enrolment is less than 2 weeks old attribute the Lead Source to Staff Member
                        if (enrolmentData.getStaffMember() != null && dateDiff < 14 && dateDiff >= 0) {
                            Staff staff;
                            String[] split = enrolmentData.getStaffMember().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);

                            logger.info("\nStaff Member: {}\n", enrolmentData.getStaffMember());
                            //
                            if (split.length == 2) {
                                String mboId = split[0];
                                String siteId = split[1];
                                staff = getStaffDao().getStaffByMboId(Long.parseLong(mboId), Long.parseLong(siteId));

                                logger.info("\nFound Staff: {}\n", staff.toString());

                                webReferralData.setReferralSource(staff.getName());
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                logger.error("Error Handling NULL referral source: {}",ex.getMessage());
            }


//        Find Referrer in MBO and set MBO attributes
            try {
                List<Client> mboClients = getMindBodyService().searchMboClient(webReferralData.getMemberEmail());

                logger.info("Found {} results in MBO Search:",mboClients.size());

                for (Client client : mboClients) {

//                    logger.info("{} ?= {} | {} ?= {} | {} ?= {}",client.getFirstName(),webReferralData.getMemberFirstName(),client.getLastName(),webReferralData.getMemberLastName(),client.getEmail(),webReferralData.getMemberEmail());

                    if (client.getFirstName().trim().equalsIgnoreCase(webReferralData.getMemberFirstName().trim())
                            && client.getLastName().trim().equalsIgnoreCase(webReferralData.getMemberLastName().trim())) {
                        webReferralData.setReferrerMboId(client.getAccessKeyNumber());
                        webReferralData.setReferMboUniqueId(client.getUniqueId());
                    }
                }
            } catch (Exception ex) {
                logger.error("Error setting MBO attributes: {}",ex.getMessage());
            }

//        Add to GymSales
            LeadData leadData = new LeadData();
            leadData.setSource_name(webReferralData.getCampaignOffer());
            leadData.setGymName(webReferralData.getGymName());
            leadData.setContact_method_name("Referral");
            leadData.setReferred_by_email(webReferralData.getMemberEmail());
            leadData.setReferred_by_name(webReferralData.getMemberFirstName() +" "+webReferralData.getMemberLastName());

            if (webReferralData.getReferralSource() == null) {
                webReferralData.setReferralSource("Marketing");
            }

            String note = "Lead Source by " + webReferralData.getReferralSource();

            leadData.setNotes(note);

//            Referral One
            leadData.setFirst_name(webReferralData.getReferralOneFirstName());
            leadData.setLast_name(webReferralData.getReferralOneLastName());
            leadData.setPhone(webReferralData.getReferralOnePhone());
            leadData.setGoogleClickId(webReferralData.getGoogleClickId());

            sendLeadToGymSalesLambda(leadData);

            smsMessage = getWebReferralSmsMessage(webReferralData.getReferralOneFirstName(), webReferralData.getMemberFirstName(), webReferralData.getGymName());
            smsRequest.setMessage(smsMessage);
            smsRequest.setTo_number(webReferralData.getReferralOnePhone());
            sendLeadSmsLambda(smsRequest);

//            Referral Two
            if ( webReferralData.getReferralTwoPhone() != null && webReferralData.getReferralTwoFirstName() != null) {
                leadData.setFirst_name(webReferralData.getReferralTwoFirstName());
                leadData.setLast_name(webReferralData.getReferralTwoLastName());
                leadData.setPhone(webReferralData.getReferralTwoPhone());
                leadData.setGoogleClickId(webReferralData.getGoogleClickId());

                sendLeadToGymSalesLambda(leadData);

                smsMessage = getWebReferralSmsMessage(webReferralData.getReferralTwoFirstName(), webReferralData.getMemberFirstName(), webReferralData.getGymName());
                smsRequest.setMessage(smsMessage);
                smsRequest.setTo_number(webReferralData.getReferralTwoPhone());
                sendLeadSmsLambda(smsRequest);
            }

//            Referral Three
            if ( webReferralData.getReferralThreePhone() != null && webReferralData.getReferralThreeFirstName() != null) {
                leadData.setFirst_name(webReferralData.getReferralThreeFirstName());
                leadData.setLast_name(webReferralData.getReferralThreeLastName());
                leadData.setPhone(webReferralData.getReferralThreePhone());
                leadData.setGoogleClickId(webReferralData.getGoogleClickId());

                sendLeadToGymSalesLambda(leadData);

                smsMessage = getWebReferralSmsMessage(webReferralData.getReferralThreeFirstName(), webReferralData.getMemberFirstName(), webReferralData.getGymName());
                smsRequest.setMessage(smsMessage);
                smsRequest.setTo_number(webReferralData.getReferralThreePhone());
                sendLeadSmsLambda(smsRequest);
            }

//            Save to DB
            webReferralData = getMemberDao().saveWebReferralData(webReferralData);

        } catch (Exception e) {
            logger.error("Error processing LeadData {}",e.getMessage());
        }

        return CompletableFuture.completedFuture(webReferralData);
    }


    private String getWebReferralSmsMessage(String friendName, String memberName, String gymName) {

        String VIP_PASS_BITLY;
        String gym;

        if (gymName == null) {
            VIP_PASS_BITLY = VIP_PASS_BITLY_FP;
            gym = "Fitness Playground";
        } else if (gymName.toLowerCase().contains("bunker") ) {
            VIP_PASS_BITLY = VIP_PASS_BITLY_BK;
            gym = Constants.BUNKER_GYM_NAME;
        } else {
            VIP_PASS_BITLY = VIP_PASS_BITLY_FP;
            gym = "Fitness Playground";
        }

        return "Hey " + friendName + ",\n" +
                "\n" +
                "Your friend " + memberName + " has sent you a free VIP Pass to train with them!\n" +
                "\n" +
                "Click here to collect: " + VIP_PASS_BITLY + "\n" +
                "\n" +
                gym + " Crew\n" +
                "\n" +
                "This is an automated message, please do not reply.";
    }

    private HashMap<String, String> handleName(String name) {

        HashMap<String, String> names = new HashMap<>();

        if (name != null) {
            String[] splitName = name.split(" ");
            if (splitName.length == 1) {
                names.put("firstName", Helpers.capitalise(splitName[0]));
                names.put("lastName", null);
            } else if (splitName.length == 2) {
                names.put("firstName", Helpers.capitalise(splitName[0]));
                names.put("lastName", Helpers.capitalise(splitName[1]));
            } else if (splitName.length > 2) {
                String firstName = "";
                for (int i = 0; i < splitName.length - 1; i++) {
                    firstName += splitName[i] + " ";
                }

                firstName = Helpers.capitalise(firstName.trim());
                names.put("firstName", firstName);
                names.put("lastName", Helpers.capitalise(splitName[splitName.length - 1]));
            } else {
                names.put("firstName", null);
                names.put("lastName", null);
            }
        } else {
            names.put("firstName", null);
            names.put("lastName", null);
        }

        return names;
    }

    private LeadData handleName(LeadData leadData, String name) {

        if (name != null) {
            String[] split = name.split(" ");
            if (split.length == 1) {
                leadData.setFirst_name(split[0]);
            } else if (split.length == 2) {
                leadData.setFirst_name(split[0]);
                leadData.setLast_name(split[1]);
            } else if (split.length > 2) {
                String firstName = "";
                for (int i = 0; i < split.length - 1; i++) {
                    firstName += split[i] + " ";
                }

                firstName = Helpers.capitalise(firstName.trim());
                leadData.setFirst_name(firstName);
                leadData.setLast_name(Helpers.capitalise(split[split.length - 1]));
            }
        }

        return leadData;
    }



    private CompletableFuture<LeadData> completeSubmission(LeadData leadData) {

        try {
            leadData = processActiveCampaignAndGymSales(leadData);
//
            leadData = getMemberDao().saveLeadData(leadData);

//            logger.info("Completing Web Form Submission: {}", submissionData);

            return CompletableFuture.completedFuture(leadData);
        } catch (Exception e) {

            logger.error("Error processing LeadData {}",e.getMessage());
            return CompletableFuture.completedFuture(leadData);
        }
    }

    private LeadData processActiveCampaignAndGymSales(LeadData leadData) {

        logger.info("processActiveCampaignAndGymSales: {}",leadData.toString());

        //        Send to GymSales -> Lambda returns partial LeadData entity
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<LeadData> entity = new HttpEntity<>(leadData);
//        GymSalesAddPeopleResponse response = null;
//        try {
//            response = getRestTemplate().exchange(GYMSALES_ADD_NEW_LEAD, HttpMethod.POST, entity, GymSalesAddPeopleResponse.class).getBody();
//
//        } catch(Exception ex) {
//            logger.error("Error Sending to GymSales: {} {}",leadData.getId(), ex.getMessage());
//            leadData.setStatus(LeadStatus.ERROR.getStatus());
//        }
//
////        FIXME: This is throwing a NullPointerException
//        if (response != null && response.getId() != null) {
//            leadData.setGymSalesId(response.getId().toString());
//        }

        leadData = sendLeadToGymSalesLambda(leadData);
//
//        Send to Active Campaign
        leadData = getActiveCampaignService().addOrUpdateContact(leadData);


        if (!leadData.getStatus().equals(LeadStatus.ERROR.getStatus())) {
            leadData.setStatus(LeadStatus.PROCESSED.getStatus());
        }

        if (leadData.getGymName().equalsIgnoreCase("Fitness Playground Academy")) {
            getEmailService().sendEmail(FP_ACADEMY_EMAIL_ADDRESS, "New Lead", leadData.toString());
        }

        return leadData;
    }

    private LeadData sendLeadToGymSalesLambda(LeadData leadData) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LeadData> entity = new HttpEntity<>(leadData);
        GymSalesAddPeopleResponse response = null;
        try {
            response = getRestTemplate().exchange(GYMSALES_ADD_NEW_LEAD, HttpMethod.POST, entity, GymSalesAddPeopleResponse.class).getBody();

        } catch(Exception ex) {
            logger.error("Error Sending to GymSales: {} {}",leadData.getId(), ex.getMessage());
            leadData.setStatus(LeadStatus.ERROR.getStatus());
        }

        if (response != null && response.getId() != null) {
            leadData.setGymSalesId(response.getId().toString());
        }

        return leadData;
    }

    private void sendLeadSmsLambda(TwilioSendSmsRequest request) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TwilioSendSmsRequest> entity = new HttpEntity<>(request);
        try {
            getRestTemplate().exchange(TWILIO_SERVICE_SEND_SMS, HttpMethod.POST, entity, String.class).getBody();

        } catch(Exception ex) {
            logger.error("Error Sending SMS to Lead Error: {}", ex.getMessage());
        }

    }



    public ActiveCampaignService getActiveCampaignService() {
        return activeCampaignService;
    }

    @Autowired
    public void setActiveCampaignService(ActiveCampaignService activeCampaignService) {
        this.activeCampaignService = activeCampaignService;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public MemberDao getMemberDao() {
        return memberDao;
    }

    @Autowired
    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
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

    public MindBodyService getMindBodyService() {
        return mindBodyService;
    }

    @Autowired
    public void setMindBodyService(MindBodyService mindBodyService) {
        this.mindBodyService = mindBodyService;
    }
}
