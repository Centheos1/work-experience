package com.fitnessplayground.service.impl;

import com.fitnessplayground.dao.GymDao;
import com.fitnessplayground.dao.MemberDao;
import com.fitnessplayground.dao.StaffDao;
import com.fitnessplayground.dao.domain.*;
import com.fitnessplayground.dao.domain.mboDto.Client;
import com.fitnessplayground.dao.domain.temp.EnrolmentLookUp;
import com.fitnessplayground.dao.domain.temp.SearchByPersDetails;
import com.fitnessplayground.service.EmailService;
import com.fitnessplayground.util.Constants;
import com.fitnessplayground.util.GymName;
import com.fitnessplayground.util.Helpers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    private MemberDao memberDao;
    private StaffDao staffDao;
    private GymDao gymDao;

    @Value("${mail.username}")
    private String USERNAME;

    @Value("${mail.password}")
    private String PASSWORD;

    @Value("${mail.notify.it}")
    private String NOTIFY_IT;

    @Value("${mail.notify.generalmanager}")
    private String NOTIFY_GENERAL_MANAGER;

    @Value("${mail.forms.username}")
    private String FORMS_USERNAME;

    @Value("${mail.forms.password}")
    private String FORMS_PASSWORD;

    @Value("${mail.coaching.username}")
    private String COACHING_USERNAME;

    @Value("${mail.coaching.password}")
    private String COACHING_PASSWORD;

    @Value("${mail.formstack.reassign.pt}")
    private String FS_PT_REASSIGN_FORM_URL;

    @Value("${mail.formstack.parq}")
    private String FS_PAR_Q_FORM_URL;

    @Value("${communications.resend.parq.url}")
    private String RESEND_PAR_Q_URL;

    @Value("${communications.pt.early.feedback.url}")
    private String FS_PT_EARLY_FEEDBACK_FORM_URL;

    @Value("${communications.pt.feedback.url}")
    private String FS_PT_FEEDBACK_FORM_URL;


    @Override
    public void sendErrorNotification(String errorMessage, String method) {

        sendEmail(NOTIFY_IT, "Communications Error " + method, errorMessage);

    }

    @Override
    public void sendEmail(String toEmail, String subject, String text) {

        logger.info("Sending Email");

        Session session = getEmailSession(USERNAME, PASSWORD);

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(toEmail)
            );
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);

            logger.info("Email Sent");

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
//
    @Override
    public void sendEmailFromForms(String toEmail, String subject, String text, boolean isHtml) {

        logger.info("Inside sendEmailFromForms");

// use the true flag to indicate the text included is HTML
        try {
            JavaMailSenderImpl sender = new JavaMailSenderImpl();
            Session session = getEmailSession(FORMS_USERNAME, FORMS_PASSWORD);
            sender.setSession(session);
            MimeMessage message = sender.createMimeMessage();

// use the true flag to indicate you need a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setFrom(FORMS_USERNAME);
            helper.setSubject(subject);
            helper.setText(text, isHtml);

            sender.send(message);

            logger.info("Email Sent from {}",FORMS_USERNAME);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sendEmailFromCoaching(String toEmail, String subject, String text, boolean isHtml, String replyTo) {

        logger.info("Inside sendEmailFromCoaching");

// use the true flag to indicate the text included is HTML
        try {
            JavaMailSenderImpl sender = new JavaMailSenderImpl();
            Session session = getEmailSession(COACHING_USERNAME, COACHING_PASSWORD);
            sender.setSession(session);
            MimeMessage message = sender.createMimeMessage();

// use the true flag to indicate you need a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setFrom(COACHING_USERNAME);
            helper.setSubject(subject);
            helper.setText(text, isHtml);

            if (replyTo != null) {
                helper.setReplyTo(replyTo);
            }

            sender.send(message);

            logger.info("Email Sent from {}",COACHING_USERNAME);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    @Async
    @Override
    public void sendEnrolmentDataNotificationToCoach(EnrolmentData enrolmentData, PreExData preExData, ParqData parqData, PtTracker ptTracker) {

        if (enrolmentData == null) return;

        logger.info("Inside sendEnrolmentDataNotificationToCoach with enrolmentData: {}",enrolmentData.getId());

        try {
            Gym gym = getGymDao().getGymByLocationId(Integer.parseInt(enrolmentData.getLocationId()));
            Staff coach = null;
            Staff ptm;
            Staff membershipConsultant;
            String[] strArr;

            if (gym != null) {
                strArr = gym.getPersonalTrainingManagerId().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
                ptm = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));
            } else {
                ptm = null;
            }

            if (enrolmentData.getPersonalTrainer() != null) {

                if (!enrolmentData.getPersonalTrainer().equals(Constants.NO_COMP_SESSION_ID) && !enrolmentData.getPersonalTrainer().equals(Constants.ASSIGN_TO_PTM_ID)) {
                    strArr = enrolmentData.getPersonalTrainer().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
                    coach = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));
                } else if (enrolmentData.getPersonalTrainer().equals(Constants.ASSIGN_TO_PTM_ID)) {
                    coach = ptm;
                } else {
                    coach = null;
                }
            }

            if (enrolmentData.getStaffMember() != null) {
                strArr = enrolmentData.getStaffMember().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
                membershipConsultant = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));
            } else {
                membershipConsultant = null;
            }

            if (ptm != null && coach != null) {

                String contractNames = "";
                String contractParams = "";
                try {
                    if (enrolmentData.getContractNamesToBeActivated() != null) {
                        strArr = enrolmentData.getContractNamesToBeActivated().split(" \\| ");
                        contractNames += "<ul>";

                        for (String i : strArr) {

                            if (i.split(" # ")[0].toLowerCase().contains("pt") || i.split(" # ")[0].toLowerCase().contains("coach")) {
                                contractNames += "<li>" + i.split(" # ")[0] + "</li>";
                                contractParams +=  i.split(" # ")[0] + ", ";
                            }
                        }

                        if (contractParams.length() >= 2) {
                            contractParams = contractParams.substring(0, contractParams.length() - 2);
                        }
                        contractNames += "</ul>";
                    }
                } catch (Exception ex) {
                    logger.error("Error setting contract names");
                }

                String params = null;
                try {
                    params = "?enrolmentId=" + URLEncoder.encode(Long.toString(enrolmentData.getId()), StandardCharsets.UTF_8.toString()) +
                            "&fpCoachId=" + "" +
                            "&acid=" + URLEncoder.encode(enrolmentData.getActiveCampaignId() == null ? "" : enrolmentData.getActiveCampaignId(), StandardCharsets.UTF_8.toString()) +
                            "&gym+location=" + URLEncoder.encode(gym.getName() == null ? "" : gym.getName(), StandardCharsets.UTF_8.toString()) +
                            "&locationId=" + URLEncoder.encode(gym.getLocationId() == null ? "" : gym.getLocationId().toString(), StandardCharsets.UTF_8.toString()) +
                            "&name-first=" + URLEncoder.encode(enrolmentData.getFirstName() == null ? "" : enrolmentData.getFirstName(), StandardCharsets.UTF_8.toString()) +
                            "&name-last=" + URLEncoder.encode(enrolmentData.getLastName() == null ? "" : enrolmentData.getLastName(), StandardCharsets.UTF_8.toString()) +
                            "&phone=" + URLEncoder.encode(enrolmentData.getPhone() == null ? "" : enrolmentData.getPhone(), StandardCharsets.UTF_8.toString()) +
                            "&email=" + URLEncoder.encode(enrolmentData.getEmail() == null ? "" : enrolmentData.getEmail(), StandardCharsets.UTF_8.toString()) +
                            "&coaching+pack=" + URLEncoder.encode(contractParams, StandardCharsets.UTF_8.toString()) +
                            "&sessions+per+week=" + URLEncoder.encode(enrolmentData.getNumberSessionsPerWeek() == null ? "" : enrolmentData.getNumberSessionsPerWeek(), StandardCharsets.UTF_8.toString()) +
                            "&availability=" + URLEncoder.encode(enrolmentData.getTrainingAvailability() == null ? "" : enrolmentData.getTrainingAvailability().replace(",",", "), StandardCharsets.UTF_8.toString()) +
                            "&available+times=" + URLEncoder.encode(enrolmentData.getTimeAvailability() == null ? "" : enrolmentData.getTimeAvailability().replace(",",", "), StandardCharsets.UTF_8.toString()) +
                            "&originally+assigned+to=" + URLEncoder.encode(coach.getName() == null ? "" : coach.getName(), StandardCharsets.UTF_8.toString());

                    if (ptTracker != null) {
                        params += "&ptTrackerId=" + URLEncoder.encode(Long.toString(ptTracker.getId()), StandardCharsets.UTF_8.toString());
                    }

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                String link = FS_PT_REASSIGN_FORM_URL  + params;

                String subject = "You have a Lead: " + enrolmentData.getFirstName() + " " + enrolmentData.getLastName();

                String persDetails = "<p>Hi " + coach.getFirstName() + ",</p>"
                        + membershipConsultant == null ? "" : "<p>" + membershipConsultant.getName() + " has a new lead for you.</p>"
                        + "<h3><u>Member Details</u></h3>"
                        + "<p>Full Name: " + enrolmentData.getFirstName() + " " + enrolmentData.getLastName() + "</p>"
                        + "<p>Email: " + enrolmentData.getEmail() + "</p>"
                        + "<p>Phone: " + enrolmentData.getPhone() + "</p>"
                        + "<p><em>Please call the member within the next 24 hours.</em></p>"
                        + "<p><em>If you can NOT service this lead, <a href='" + link + "'>please complete this form</a></em></p>";

                if (ptTracker != null) {
                    persDetails += "<p><em><a href='" + RESEND_PAR_Q_URL + ptTracker.getId() + "'>Click Here to resend the Par-Q .</a></em></p><p><i>Note: the Par-Q has already been sent to the member. <u>Please only click this link after " + enrolmentData.getFirstName() + " has been contacted</u></i>.</p>";
                }

                String enrolDetails = "<h3><u>Enrolment Details</u></h3>"
                        + "<p>First Debit Date: " + Helpers.cleanDate(enrolmentData.getPersonalTrainingStartDate()) + "</p>"
                        + "<p>Lead Type: " + Helpers.cleanCamelCase(enrolmentData.getTrainingStarterPack()) + "</p>"
                        + "<p>Coaching Option:</p><p>" + contractNames + "</p>"
                        + "<p>Goal: " + Helpers.cleanCamelCase(enrolmentData.getNumberOneGoal()) + "</p>"
                        + "<p>Time Availability: " + (enrolmentData.getTimeAvailability() == null ? "" : enrolmentData.getTimeAvailability().replace(",",", ")) + "</p>"
                        + "<p>Day Availability: " + (enrolmentData.getTrainingAvailability() == null ? "" : enrolmentData.getTrainingAvailability().replace(",", ", ")) + "</p>"
                        + "<p>Enrolment Notes: "+ enrolmentData.getNotes() +"</p>";


                String preExDetails = "";
                if (preExData != null) {

                    String prevMemberships = "";
                    if (preExData.getPreviousMembershipType() != null) {
                        prevMemberships = "<p>Previous Memberships:</p><ul>";
                        for (String p : preExData.getPreviousMembershipType().split(",")) {
                            prevMemberships += "<li>" + p + "</li>";

                        }
                        prevMemberships += "</ul>";
                    }

                    String prevServices = "";
                    if (preExData.getPreviousServicesUsed() != null) {
                        prevServices = "<p>Previous Services Used:</p><ul>";
                        for (String p : preExData.getPreviousServicesUsed().split(",")) {
                            prevServices += "<li>" + p + "</li>";

                        }
                        prevServices += "</ul>";
                    }

                    String priorities = "";
                    if (preExData.getPriorities() != null) {
                        priorities = "<p>Priorities:</p><ul>";
                        for (String p : preExData.getPriorities().split(",")) {
                            priorities += "<li>" + p + "</li>";

                        }
                        priorities += "</ul>";
                    }

                    String roadBlocks = "";
                    if (preExData.getRoadBlocks() != null) {
                        roadBlocks = "<p>Roadblocks:</p><ul>";
                        for (String p : preExData.getRoadBlocks().split(",")) {
                            roadBlocks += "<li>" + p + "</li>";
                        }
                        roadBlocks += "</ul>";
                    }

                    String medicationConditions = "";
                    if (preExData.getMedicalConditions() != null) {
                        medicationConditions = "<p>Medical Conditions:</p><ul>";
                        for (String p : preExData.getMedicalConditions().split(",")) {
                            medicationConditions += "<li>" + p + "</li>";

                        }
                        medicationConditions += "</ul>";
                    }

//                    String exerciseInterests = "";
//                    if (preExData.getExerciseInterests() != null) {
//                        exerciseInterests = "<p>Would like to try:</p><ul>";
//                        for (String p : preExData.getExerciseInterests().split(",")) {
//                            exerciseInterests += "<li>" + p + "</li>";
//
//                        }
//                        exerciseInterests += "</ul>";
//                    }

//                    String areasToHelp = "";
//                    if (preExData.getAreasToHelp() != null) {
//                        areasToHelp = "<p>Would like help with:</p><ul>";
//                        for (String p : preExData.getAreasToHelp().split(",")) {
//                            areasToHelp += "<li>" + p + "</li>";
//
//                        }
//                        areasToHelp += "</ul>";
//                    }


                    preExDetails = "<h3><u>Pre Ex Details</u></h3>"
                            + "<h5><u>Gym Experience</u></h5>"
                            + (preExData.getPreviousMembershipType() == null ? "" : "<p>Has previously been a member of a fitness facility: " + preExData.getHasPreviousMembership() +"</p>")
                            + prevMemberships
                            + prevServices
                            + (preExData.getIsCurrentlyExercising() == null ? "" : "<p>Is currently exercising: " + preExData.getIsCurrentlyExercising()  +"</p>")
                            + (preExData.getCurrentExerciseType() == null ? "" : "<p>Current exercise: " + preExData.getCurrentExerciseType() + "</p>")
                            + (preExData.getLastExercisePeriod() == null ? "" : "<p>Last time exercised: " + preExData.getLastExercisePeriod() + "</p>")
                            + (preExData.getChangeMotivation() == null ? "" : "<p>Reason for change: " + preExData.getChangeMotivation() + "</p>")
//                            + exerciseInterests
//                            + areasToHelp
                            + "<h5><u>Goals</u></h5>"
                            + (preExData.getAreaOfFocus() == null ? "" : "<p>Primary Focus: " + preExData.getAreaOfFocus() + "</p>")
                            + priorities
                            + (preExData.getAreaOfFocusDetails() == null ? "" : "<p>Details: " + preExData.getAreaOfFocusDetails() + "</p>")
                            + (preExData.getAreaOfFocusFirst30Days() == null ? "" : "<p>First 30 days: " + preExData.getAreaOfFocusFirst30Days() + "</p>")
                            + roadBlocks
                            + (preExData.getSignificantsScale() == null ? "" : "<p>Significance: " + preExData.getSignificantsScale() + "/10</p>")
                            + (preExData.getNumberDaysPerWeekExercise() == null ? "" : "<p>Plan to exercise " + preExData.getNumberDaysPerWeekExercise() + " days a week.</p>")
                            + "<h5><u>Health</u></h5>"
                            + (preExData.getHasMedicalCondition() != null && preExData.getHasMedicalCondition() ? "<p><u>Has a medical condition</u>.</p>" : "<p>Has <u>no</u> medical condition.</p>")
                            + medicationConditions
                            + (preExData.getHasClearanceMedicalCondition() != null && preExData.getHasClearanceMedicalCondition() ? "<p>Has medical clearance.</p>" : "<p>Has <u>no</u> medical clearance.</p>")
                            + (preExData.getOnMedication() != null && preExData.getOnMedication() ? "<p><u>Is taking medication</u>.</p>" : "<p>Is not taking medication.</p>")
                            + (preExData.getHasClearanceMedication() != null && preExData.getHasClearanceMedication() ? "" : "<p>Has medical clearance for medication.</p>")
                            + "<p>Pre Ex Notes: " + preExData.getNotes() + "</p>";
                }


                String parqDetails = "";
                if (parqData != null) {

                    String _roadBlocks = "";
                    if (parqData.getRoadblocks() != null) {
                        _roadBlocks = "<p>Roadblocks:</p><ul>";
                        for (String p : parqData.getRoadblocks().split(",")) {
                            _roadBlocks += "<li>" + p + "</li>";
                        }
                        _roadBlocks += "</ul>";
                    }

                    String injuries = "";
                    if (parqData.getPainOrInjuriesAreas() != null) {
                        injuries = "<p>Pain or injuries:</p><ul>";
                        for (String p : parqData.getPainOrInjuriesAreas().split(",")) {
                            injuries += "<li>" + p + "</li>";
                        }
                        injuries += "</ul>";
                    }


                    parqDetails = "<h3><u>Par-Q Details</u></h3>"
                            + "<h5><u>Previous Experience</u></h5>"
                            + (parqData.getComfortInGym() == null ? "" : "<p>Comfort levels in the gym: " + parqData.getComfortInGym() + "</p>")
                            + (parqData.getHasPreviousCoach() == null ? "" : "<p>Has worked with a coach in the past: " + parqData.getHasPreviousCoach() + "</p>")
                            + (parqData.getPreviousCoachDuration() == null || parqData.getPreviousCoachFrequency() == null ? "" : "<p>Worked with coach for " + parqData.getPreviousCoachDuration() + " " + parqData.getPreviousCoachFrequency() + ".</p>")
                            + (parqData.getPreviousCoachPreferences() == null ? "" : "<p>Likes & dislikes with previous coach: " + parqData.getPreviousCoachPreferences() + "</p>")
                            + "<h5><u>Moving Forward</u></h5>"
                            + (parqData.getIsCurrentlyExercising() == null || parqData.getNumberDaysPerWeekExercise() == null ? "" : "<p>Is currently exercising: " + parqData.getIsCurrentlyExercising() + "</p>")
                            + (parqData.getNumberDaysPerWeekExercise() == null ? "" : "<p>Currently exercising " + parqData.getNumberDaysPerWeekExercise() + " time per week.</p>")
                            + (parqData.getHasProgram() == null ? "" : "<p>Currently following a program: " + parqData.getHasProgram() + "</p>")
                            + (parqData.getAreaOfFocus() == null ? "" : "<p>Area of focus: " + parqData.getAreaOfFocus() + "</p>")
                            + (parqData.getAreaOfFocusDetails() == null ? "" : "<p>Details: " + parqData.getAreaOfFocusDetails() + "</p>")
                            + (parqData.getSignificanceScale() == null ? "" : "<p>Significance: " + parqData.getSignificanceScale() + "/5</p>")
                            + (parqData.getSignificanceDetail() == null ? "" : "<p>Because: " + parqData.getSignificanceDetail() + "</p>")
                            + _roadBlocks
                            + "<h5><u>Lifestyle</u></h5>"
                            + (parqData.getHasPhysicalJob() == null ? "" : "<p>Physical activity at work: " + parqData.getHasPhysicalJob() + "</p>")
                            + (parqData.getSleepPerNight() == null ? "" : "<p>Typically sleeps " + parqData.getSleepPerNight() + " per night.</p>")
                            + (parqData.getEnergyLevelRating() == null ? "" : "<p>Energy levels: " + parqData.getEnergyLevelRating() + "/5</p>")
                            + (parqData.getStressLevelRating() == null ? "" : "<p>Stress levels: " + parqData.getStressLevelRating() + "/5</p>")
                            + (parqData.getDestressMethod() == null ? "" : "<p>Destress method: " + parqData.getDestressMethod() + "</p>")
                            + (parqData.getNutritionRating() == null ? "" : "<p>Eating habits: " + parqData.getNutritionRating() + "/5</p>")
                            + (parqData.getNutritionHelpRequest() == null ? "" : "<p>Nutrition help request: " + parqData.getNutritionHelpRequest() + "</p>")
                            + "<h5><u>Health</u></h5>"
                            + injuries
                            + (parqData.getAggrevationOfAilmentsRisk() == null ? "" : "<p>Has injuries, illnesses or ailments that could be aggravated by exercise: " + parqData.getAggrevationOfAilmentsRisk() + "</p>")
                            + (parqData.getAggrevationOfAilmentsRiskDetails() == null ? "" : "<p>Details: " + parqData.getAggrevationOfAilmentsRiskDetails() + "</p>")
                            + (parqData.getHealthConcerns() == null ? "" : "<p>Has other health concerns: " + parqData.getHealthConcerns() + "</p>")
                            + (parqData.getHealthConcernsDetails() == null ? "" : "<p>Details: " + parqData.getHealthConcernsDetails() + "</p>")
                            + (parqData.getHowToHelp() == null ? "" : "<p>To be successful: " + parqData.getHowToHelp() + "</p>");
                }


                String signOff = "<p><em>If you can NOT service this lead, <a href='" + link + "'>please complete this form</a></em></p>"
                        + "<p>Thanks,<br />" + ptm.getFirstName() + "</p>";

                String text = persDetails + enrolDetails + preExDetails + parqDetails + signOff;

                logger.info("About to send email to {}",coach.getEmail());
                sendEmailFromForms(coach.getEmail(), subject, text, true);
            }

        } catch (Exception ex) {
            logger.error("Error [sendEnrolmentDataNotificationToCoach]: {}",ex.getMessage());
            sendErrorNotification("Error [sendEnrolmentDataNotificationToCoach]: EnrolmentData Id: " + enrolmentData.getId() + " Error: " + ex.getMessage(), "sendEnrolmentDataNotificationToCoach");
        }
    }


    @Async
    @Override
    public void sendFpCoachNotificationToCoach(FpCoachEnrolmentData fpCoachEnrolmentData) {

        try {
            Gym gym = getGymDao().getGymByLocationId(fpCoachEnrolmentData.getLocationId());
            Staff ptm;
            String[] strArr;

            if (gym != null) {
                strArr = gym.getPersonalTrainingManagerId().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
                ptm = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));
            } else {
                ptm = null;
            }

            strArr = fpCoachEnrolmentData.getPersonalTrainer().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
            Staff coach = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));
            String subject = fpCoachEnrolmentData.getFirstName() + " has signed up for Personal Training";

            if (ptm != null && coach != null) {

                String contractNames = "";
                String contractParams = "";
                try {
                    if (fpCoachEnrolmentData.getMboContractNames() != null) {
                        strArr = fpCoachEnrolmentData.getMboContractNames().split(" \\| ");
                        contractNames += "<ul>";

                        for (String i : strArr) {

                            if (i.split(" # ")[0].toLowerCase().contains("pt") || i.split(" # ")[0].toLowerCase().contains("coach")) {
                                contractNames += "<li>" + i.split(" # ")[0] + "</li>";
                                contractParams += i.split(" # ")[0] + ", ";
                            }
                        }

                        if (contractParams.length() >= 2) {
                            contractParams = contractParams.substring(0, contractParams.length() - 2);
                        }
                        contractNames += "</ul>";
                    }
                } catch (Exception ex) {
                    logger.error("Error setting contract names");
                }

                String params = null;
                try {
                    params = "enrolmentId=" + "" +
                            "&fpCoachId=" + URLEncoder.encode(Long.toString(fpCoachEnrolmentData.getId()), StandardCharsets.UTF_8.toString()) +
                            "&acid=" + URLEncoder.encode(fpCoachEnrolmentData.getActiveCampaignId() == null ? "" : fpCoachEnrolmentData.getActiveCampaignId(), StandardCharsets.UTF_8.toString()) +
                            "&gym+location=" + URLEncoder.encode(gym.getName() == null ? "" : gym.getName(), StandardCharsets.UTF_8.toString()) +
                            "&locationId=" + URLEncoder.encode(gym.getLocationId() == null ? "" : gym.getLocationId().toString(), StandardCharsets.UTF_8.toString()) +
                            "&name-first=" + URLEncoder.encode(fpCoachEnrolmentData.getFirstName() == null ? "" : fpCoachEnrolmentData.getFirstName(), StandardCharsets.UTF_8.toString()) +
                            "&name-last=" + URLEncoder.encode(fpCoachEnrolmentData.getLastName() == null ? "" : fpCoachEnrolmentData.getLastName(), StandardCharsets.UTF_8.toString()) +
                            "&phone=" + URLEncoder.encode(fpCoachEnrolmentData.getPhone() == null ? "" : fpCoachEnrolmentData.getPhone(), StandardCharsets.UTF_8.toString()) +
                            "&email=" + URLEncoder.encode(fpCoachEnrolmentData.getEmail() == null ? "" : fpCoachEnrolmentData.getEmail(), StandardCharsets.UTF_8.toString()) +
                            "&coaching+pack=" + URLEncoder.encode(contractParams, StandardCharsets.UTF_8.toString()) +
                            "&sessions+per+week=" + URLEncoder.encode(fpCoachEnrolmentData.getNumberSessioinsPerWeek() == null ? "" : fpCoachEnrolmentData.getNumberSessioinsPerWeek(), StandardCharsets.UTF_8.toString()) +
                            "&originally+assigned+to=" + URLEncoder.encode(coach.getName() == null ? "" : coach.getName(), StandardCharsets.UTF_8.toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                String link = FS_PT_REASSIGN_FORM_URL + "?" + params;

                String text = "<p>Whoo hoo! You have a new PT Ongoing Client!</p>"
                        + "<p>" + fpCoachEnrolmentData.getStaffName() + " has signed up a new PT Ongoing client for:</p>"
                        + "<h5><u>Details</u></h5>"
                        + "<p>Coaching Option:</p><p>" + contractNames + "</p>"
                        + "<p>Name: " + fpCoachEnrolmentData.getFirstName() + " " + fpCoachEnrolmentData.getLastName() + "</p>"
                        + "<p>Phone: " + fpCoachEnrolmentData.getPhone() + "</p>"
                        + "<p>Email: " + fpCoachEnrolmentData.getEmail() + "</p>"
                        + "<p>First Debit Date: " + Helpers.cleanDate(fpCoachEnrolmentData.getStartDate()) + "</p>"
                        + "<p><em>Member Signed up on: " + Helpers.cleanDate(fpCoachEnrolmentData.getUpdateDate()) + " please contact within 24 hours.</em></p>"
                        + "<p>Notes:<br />" + fpCoachEnrolmentData.getNotes() + "</p>"
                        + "<p><em>If you can NOT service this lead, <a href='" + link + "'>please complete this form</a></em></p>"
                        + "<p>Thanks,<br />" + ptm.getFirstName()  + "</p>";

                logger.info("About to send FP Coach email to {}", coach.getEmail());
                sendEmailFromForms(coach.getEmail(), subject, text, true);
            }
        } catch (Exception ex) {
            logger.error("Error [sendFpCoachNotificationToCoach]: {}", ex.getMessage());
            sendErrorNotification("Error [sendFpCoachNotificationToCoach]: FP Coach Id: " + fpCoachEnrolmentData.getId() + " Error: " + ex.getMessage(), "sendFpCoachNotificationToCoach");
        }
    }

    @Async
    @Override
    public void resendParq(Long ptTrackerId) {

        try {

            PtTracker ptTracker = getMemberDao().getPtTrackerById(ptTrackerId);
            if (ptTracker != null) {

                EnrolmentData enrolmentData = getMemberDao().getEnrolmentDataById(ptTracker.getEnrolmentDataId());

                if (enrolmentData != null) {
//
                    Gym gym = getGymDao().getGymByLocationId(Integer.parseInt(enrolmentData.getLocationId()));
                    Staff coach = null;
                    Staff ptm;
                    String[] strArr;

                    if (gym != null) {
                        strArr = gym.getPersonalTrainingManagerId().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
                        ptm = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));
                    } else {
                        ptm = null;
                    }

                    if (enrolmentData.getPersonalTrainer() != null) {

                        if (!enrolmentData.getPersonalTrainer().equals(Constants.NO_COMP_SESSION_ID) && !enrolmentData.getPersonalTrainer().equals(Constants.ASSIGN_TO_PTM_ID)) {
                            strArr = enrolmentData.getPersonalTrainer().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
                            coach = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));
                        } else if (enrolmentData.getPersonalTrainer().equals(Constants.ASSIGN_TO_PTM_ID)) {
                            coach = ptm;
                        } else {
                            coach = null;
                        }
                    }

                    String COMPANY_NAME = gym.getLocationId() == 5 ? "The Bunker Gym" : "Fitness Playground";
                    String SIGN_OFF = gym.getLocationId() == 5 ? "The Bunker Gym" : "Fitness Playground " + gym.getName();

                    String parqParams = "";
                    try {
                        parqParams = "?pt_tracker_id=" + URLEncoder.encode(ptTracker == null ? "" : Long.toString(ptTracker.getId()), StandardCharsets.UTF_8.toString()) +
                                "&name-first=" + URLEncoder.encode(enrolmentData.getFirstName() == null ? "" : enrolmentData.getFirstName(), StandardCharsets.UTF_8.toString()) +
                                "&name-last=" + URLEncoder.encode(enrolmentData.getLastName() == null ? "" : enrolmentData.getLastName(), StandardCharsets.UTF_8.toString()) +
                                "&Home+Club=" + URLEncoder.encode(enrolmentData.getGymName() == null ? "" : enrolmentData.getGymName(), StandardCharsets.UTF_8.toString()) +
                                "&Your+Coach+is=" + URLEncoder.encode(coach == null ? "" : coach.getName(), StandardCharsets.UTF_8.toString());
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    String parqLink = FS_PAR_Q_FORM_URL + parqParams;

                    if (coach != null) {

                        String subject = COMPANY_NAME + ": PAR-Q Form";

                        String text = "<p>Hi " + enrolmentData.getFirstName() + ",</p>" +
                                "<p>Another warm welcome to " + COMPANY_NAME + ".</p>" +
                                "<p>As requested, here is the PAR-Q form that we need you to complete before commencing your training with us.</p>" +
                                "<p>It will only take a few minutes and will help us to get you on the right track.</p>" +
                                "<p><strong><a href='" + parqLink + "'>Please click here to complete your PAR-Q</a></strong></p>" +
                                "<p>I'm looking forward to helping you on your coaching journey!</p>" +
                                "<p>Regards,<br/>" + coach.getName() + "<br/>Personal Trainer | " + SIGN_OFF + "</p>";

                        sendEmailFromCoaching(enrolmentData.getEmail(), subject, text, true, null);
                    }
                }
            }

        } catch (Exception ex) {
            logger.error("Error [resendParq]: {}",ex.getMessage());
            sendErrorNotification("Error [resendParq]: " + ex.getMessage(), "resendParq");
        }
    }


    @Async
    @Override
    public void sendParqTrainerNotification(ParqData parqData) {

        try {

            Gym gym = getGymDao().getGymByLocationId(GymName.getLocationIdByGymName(parqData.getGymName()));
            Staff coach = null;
            Staff ptm;
//            Staff membershipConsultant;
            String[] strArr;

            if (gym != null) {
                strArr = gym.getPersonalTrainingManagerId().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
                ptm = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));
            } else {
                ptm = null;
            }

            if (parqData.getPersonalTrainerName() != null) {

                coach = getStaffDao().getStaffByName(parqData.getPersonalTrainerName());

                if (coach == null) {
                    sendErrorNotification("Error [sendParqTrainerNotification]: Could not find Coach ", "sendParqTrainerNotification");
                    return;
                }
            }

            String persDetails = "";
            try {
                persDetails = "<p>Hi " + coach.getFirstName() + ",</p>"
                        + "<h3><u>Member Details</u></h3>"
                        + "<p>Full Name: " + parqData.getFirstName() + " " + parqData.getLastName() + "</p>";
            } catch (Exception ex) {
                logger.error("Error setting perDetails [sendParqTrainerNotification]: {}",ex.getMessage());
            }

            String parqDetails = "";
            if (parqData != null) {

                String _roadBlocks = "";
                if (parqData.getRoadblocks() != null) {
                    _roadBlocks = "<p>Roadblocks:</p><ul>";
                    for (String p : parqData.getRoadblocks().split(",")) {
                        _roadBlocks += "<li>" + p + "</li>";
                    }
                    _roadBlocks += "</ul>";
                }

                String injuries = "";
                if (parqData.getPainOrInjuriesAreas() != null) {
                    injuries = "<p>Pain or injuries:</p><ul>";
                    for (String p : parqData.getPainOrInjuriesAreas().split(",")) {
                        injuries += "<li>" + p + "</li>";
                    }
                    injuries += "</ul>";
                }


                parqDetails = "<h3><u>Par-Q Details</u></h3>"
                    + "<h5><u>Previous Experience</u></h5>"
                    + (parqData.getComfortInGym() == null ? "" : "<p>Comfort levels in the gym: " + parqData.getComfortInGym() + "</p>")
                    + (parqData.getHasPreviousCoach() == null ? "" : "<p>Has worked with a coach in the past: " + parqData.getHasPreviousCoach() + "</p>")
                    + (parqData.getPreviousCoachDuration() == null || parqData.getPreviousCoachFrequency() == null ? "" : "<p>Worked with coach for " + parqData.getPreviousCoachDuration() + " " + parqData.getPreviousCoachFrequency() + ".</p>")
                    + (parqData.getPreviousCoachPreferences() == null ? "" : "<p>Likes & dislikes with previous coach: " + parqData.getPreviousCoachPreferences() + "</p>")
                    + "<h5><u>Moving Forward</u></h5>"
                    + (parqData.getIsCurrentlyExercising() == null || parqData.getNumberDaysPerWeekExercise() == null ? "" : "<p>Is currently exercising: " + parqData.getIsCurrentlyExercising() + "</p>")
                    + (parqData.getNumberDaysPerWeekExercise() == null ? "" : "<p>Currently exercising " + parqData.getNumberDaysPerWeekExercise() + " time per week.</p>")
                    + (parqData.getHasProgram() == null ? "" : "<p>Currently following a program: " + parqData.getHasProgram() + "</p>")
                    + (parqData.getAreaOfFocus() == null ? "" : "<p>Area of focus: " + parqData.getAreaOfFocus() + "</p>")
                    + (parqData.getAreaOfFocusDetails() == null ? "" : "<p>Details: " + parqData.getAreaOfFocusDetails() + "</p>")
                    + (parqData.getSignificanceScale() == null ? "" : "<p>Significance: " + parqData.getSignificanceScale() + "/5</p>")
                    + (parqData.getSignificanceDetail() == null ? "" : "<p>Because: " + parqData.getSignificanceDetail() + "</p>")
                    + _roadBlocks
                    + "<h5><u>Lifestyle</u></h5>"
                    + (parqData.getHasPhysicalJob() == null ? "" : "<p>Physical activity at work: " + parqData.getHasPhysicalJob() + "</p>")
                    + (parqData.getSleepPerNight() == null ? "" : "<p>Typically sleeps " + parqData.getSleepPerNight() + " per night.</p>")
                    + (parqData.getEnergyLevelRating() == null ? "" : "<p>Energy levels: " + parqData.getEnergyLevelRating() + "/5</p>")
                    + (parqData.getStressLevelRating() == null ? "" : "<p>Stress levels: " + parqData.getStressLevelRating() + "/5</p>")
                    + (parqData.getDestressMethod() == null ? "" : "<p>Destress method: " + parqData.getDestressMethod() + "</p>")
                    + (parqData.getNutritionRating() == null ? "" : "<p>Eating habits: " + parqData.getNutritionRating() + "/5</p>")
                    + (parqData.getNutritionHelpRequest() == null ? "" : "<p>Nutrition help request: " + parqData.getNutritionHelpRequest() + "</p>")
                    + "<h5><u>Health</u></h5>"
                    + injuries
                    + (parqData.getAggrevationOfAilmentsRisk() == null ? "" : "<p>Has injuries, illnesses or ailments that could be aggravated by exercise: " + parqData.getAggrevationOfAilmentsRisk() + "</p>")
                    + (parqData.getAggrevationOfAilmentsRiskDetails() == null ? "" : "<p>Details: " + parqData.getAggrevationOfAilmentsRiskDetails() + "</p>")
                    + (parqData.getHealthConcerns() == null ? "" : "<p>Has other health concerns: " + parqData.getHealthConcerns() + "</p>")
                    + (parqData.getHealthConcernsDetails() == null ? "" : "<p>Details: " + parqData.getHealthConcernsDetails() + "</p>")
                    + (parqData.getHowToHelp() == null ? "" : "<p>To be successful: " + parqData.getHowToHelp() + "</p>");
            }

            String subject = parqData.getFirstName() + " " + parqData.getLastName() + " has completed their Par-Q";

            String signOff = "<p>Thanks,<br />" + ptm.getFirstName() + "</p>";

            String text = persDetails + parqDetails + signOff;

            logger.info("About to send email to {}",coach.getEmail());
            sendEmailFromForms(coach.getEmail(), subject, text, true);

        } catch (Exception ex) {
            logger.error("Error [sendParqTrainerNotification]: {}",ex.getMessage());
            sendErrorNotification("Error [sendParqTrainerNotification]: " + ex.getMessage(), "sendParqTrainerNotification");
        }
    }


    @Override
    public void sendParqReminder(PtTracker ptTracker) {

        try {
            if (ptTracker != null) {
                EnrolmentData enrolmentData = getMemberDao().getEnrolmentDataById(ptTracker.getEnrolmentDataId());

                if (enrolmentData != null) {

                    Gym gym = getGymDao().getGymByLocationId(Integer.parseInt(enrolmentData.getLocationId()));
                    Staff coach = null;
                    Staff ptm;
                    String[] strArr;

                    if (gym != null) {
                        strArr = gym.getPersonalTrainingManagerId().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
                        ptm = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));
                    } else {
                        ptm = null;
                    }

                    if (enrolmentData.getPersonalTrainer() != null) {

                        if (!enrolmentData.getPersonalTrainer().equals(Constants.NO_COMP_SESSION_ID) && !enrolmentData.getPersonalTrainer().equals(Constants.ASSIGN_TO_PTM_ID)) {
                            strArr = enrolmentData.getPersonalTrainer().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
                            coach = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));
                        } else if (enrolmentData.getPersonalTrainer().equals(Constants.ASSIGN_TO_PTM_ID)) {
                            coach = ptm;
                        } else {
                            coach = null;
                        }
                    }

                    if (coach != null) {

                        String COMPANY_NAME = gym.getLocationId() == 5 ? "The Bunker Gym" : "Fitness Playground";
                        String SIGN_OFF = gym.getLocationId() == 5 ? "The Bunker Gym" : "Fitness Playground " + gym.getName();

                        String subject = COMPANY_NAME + ": Before you start ...";

                        String parqParams = "";
                        try {
                            parqParams = "?pt_tracker_id=" + URLEncoder.encode(ptTracker == null ? "" : Long.toString(ptTracker.getId()), StandardCharsets.UTF_8.toString()) +
                                    "&name-first=" + URLEncoder.encode(enrolmentData.getFirstName() == null ? "" : enrolmentData.getFirstName(), StandardCharsets.UTF_8.toString()) +
                                    "&name-last=" + URLEncoder.encode(enrolmentData.getLastName() == null ? "" : enrolmentData.getLastName(), StandardCharsets.UTF_8.toString()) +
                                    "&Home+Club=" + URLEncoder.encode(enrolmentData.getGymName() == null ? "" : enrolmentData.getGymName(), StandardCharsets.UTF_8.toString()) +
                                    "&Your+Coach+is=" + URLEncoder.encode(coach == null ? "" : coach.getName(), StandardCharsets.UTF_8.toString());
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        String parqLink = FS_PAR_Q_FORM_URL + parqParams;

                        String text = "<p>Hi " + enrolmentData.getFirstName() + ",</p>" +
                                "<p>You are shortly about to start your Coaching Journey with " + coach.getName() +
                                " and as far as we can tell, you have not yet completed your physical activity readiness questionnaire (PAR-Q) that was in your welcome email.</p>" +
                                "<p>The questions asked are <u>different</u> to the form you filled in when joining the gym, and are specifically designed to help our coaches best prepare your sessions.</p>" +
                                "<p>If you have 5 minutes please follow this <a href='" + parqLink + "'>link</a> and give your coach the best chance of providing exactly the service you're looking for and remember," +
                                " the more information you give us, the better we get!</p>" +
                                "<p><strong><a href='" + parqLink + "'>Please click here to complete your PAR-Q</a></strong></p>" +
                                "<p>Thank you,<br/>Matt Duncan | Personal Training Manager<br/>" + SIGN_OFF + "</p>";

                        sendEmailFromCoaching(enrolmentData.getEmail(), subject, text, true, null);
                    }
                }
            }

        } catch (Exception ex) {
            logger.error("Error [sendParqReminder]: {}",ex.getMessage());
            sendErrorNotification("Error [sendParqReminder]: " + ex.getMessage(), "sendParqReminder");
        }
    }


    @Override
    public void sendPtEarlyFeedbackRequest(PtTracker ptTracker, Client mboClient) {

        try {
            if (ptTracker != null) {
                EnrolmentData enrolmentData = getMemberDao().getEnrolmentDataById(ptTracker.getEnrolmentDataId());

                if (enrolmentData != null) {

                    Gym gym = getGymDao().getGymByLocationId(Integer.parseInt(enrolmentData.getLocationId()));
                    Staff coach = null;
                    Staff ptm;
                    String[] strArr;

                    if (gym != null) {
                        strArr = gym.getPersonalTrainingManagerId().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
                        ptm = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));
                    } else {
                        ptm = null;
                    }

                    if (enrolmentData.getPersonalTrainer() != null) {

                        if (!ptTracker.getPersonalTrainer().equals(Constants.NO_COMP_SESSION_ID) && !enrolmentData.getPersonalTrainer().equals(Constants.ASSIGN_TO_PTM_ID)) {
                            strArr = ptTracker.getPersonalTrainer().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
                            coach = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));
                        } else if (ptTracker.getPersonalTrainer().equals(Constants.ASSIGN_TO_PTM_ID)) {
                            coach = ptm;
                        } else {
                            coach = null;
                        }
                    }

                    if (coach != null) {

                        String COMPANY_NAME = gym.getLocationId() == 5 ? "The Bunker Gym" : "Fitness Playground";
                        String SIGN_OFF = gym.getLocationId() == 5 ? "The Bunker Gym" : "Fitness Playground " + gym.getName();

                        String subject = "How's your training so far?";

                        String params = "";
                        try {
                            params = "?pt_tracker_id=" + URLEncoder.encode(ptTracker == null ? "" : Long.toString(ptTracker.getId()), StandardCharsets.UTF_8.toString());
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        String ptEarlyFeedbackLink = FS_PT_EARLY_FEEDBACK_FORM_URL + params;

                        String text = "<p>Hi " + mboClient.getFirstName() + ",</p>" +
                                "<p>I need your help. I understand that you have now completed at least 4 sessions with your Coach " +
                                coach.getFirstName() + " and I'd love to know how you're finding the experience so far.</p>" +
                                "<p>One of " + COMPANY_NAME + "'s core values is to be Better Everyday, and we are constantly looking for feedback that will help us to improve our coaching service.</p>" +
                                "<p>With that being said, I would love to hear your thoughts and really appreciate it if you took one minute of your time to fill out the below questionnaire.</p>" +
                                "<p><strong><a href='"+ ptEarlyFeedbackLink + "'>You can give me your feedback here.</a></strong></p>" +
                                "<p>If you would like us to not mention your name, please click the check box \"I would like to stay anonymous\" on the form.</p>" +
                                "<p>Your feedback means a lot to us and every submission is reviewed carefully.</p>" +
                                "<p>Thank you,<br/>Matt Duncan | Personal Training Manager<br/>" + SIGN_OFF + "</p>";

                        sendEmailFromCoaching(mboClient.getEmail(), subject, text, true, null);
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("Error [sendPtEarlyFeedbackRequest]: {}",ex.getMessage());
            sendErrorNotification("Error [sendPtEarlyFeedbackRequest]: " + ex.getMessage(), "sendPtEarlyFeedbackRequest");
        }

    }

    @Override
    public void sendPtFeedbackRequest(PtTracker ptTracker, Client mboClient) {

        try {
            if (ptTracker != null) {
                EnrolmentData enrolmentData = getMemberDao().getEnrolmentDataById(ptTracker.getEnrolmentDataId());

                if (enrolmentData != null) {

                    Gym gym = getGymDao().getGymByLocationId(Integer.parseInt(enrolmentData.getLocationId()));
                    Staff coach = null;
                    Staff ptm;
                    String[] strArr;

                    if (gym != null) {
                        strArr = gym.getPersonalTrainingManagerId().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
                        ptm = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));
                    } else {
                        ptm = null;
                    }

                    if (enrolmentData.getPersonalTrainer() != null) {

                        if (!ptTracker.getPersonalTrainer().equals(Constants.NO_COMP_SESSION_ID) && !enrolmentData.getPersonalTrainer().equals(Constants.ASSIGN_TO_PTM_ID)) {
                            strArr = ptTracker.getPersonalTrainer().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
                            coach = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));
                        } else if (ptTracker.getPersonalTrainer().equals(Constants.ASSIGN_TO_PTM_ID)) {
                            coach = ptm;
                        } else {
                            coach = null;
                        }
                    }

                    if (coach != null) {

                        String SIGN_OFF = gym.getLocationId() == 5 ? "The Bunker Gym" : "Fitness Playground " + gym.getName();

                        String subject = "How's your training?";

                        String params = "";
                        try {
                            params = "?pt_tracker_id=" + URLEncoder.encode(ptTracker == null ? "" : Long.toString(ptTracker.getId()), StandardCharsets.UTF_8.toString());
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        String ptFeedbackLink = FS_PT_FEEDBACK_FORM_URL + params;

                        String text = "<p>Hi " + mboClient.getFirstName() + ",</p>" +
                                "<p>I would like to congratulate you on your commitment to your health and fitness.</p>" +
                                "<p>Our records show that you have now completed 12 sessions with your Coach " + coach.getFirstName() + ".</p>" +
                                "<p>I hope that the process has been fun but challenging, in a good way!</p>" +
                                "<p>I would also like to take this opportunity to once again ask for your help in bettering our coaching service. " +
                                "It is our mission to raise the standard of personal training within the fitness industry and we have no better tool to do this, " +
                                "than the feedback we receive from our clients.</p>" +
                                "<p>Please take a few minutes to share your experience with us here. Once again, there is the option for your feedback to remain anonymous.</p>" +
                                "<p>We are grateful for every insight we receive.</p>" +
                                "<p><strong><a href='"+ ptFeedbackLink + "'>You can give me your feedback here.</a></strong></p>" +
                                "<p>Thank you,<br/>Matt Duncan | Personal Training Manager<br/>" + SIGN_OFF + "</p>";

                        sendEmailFromCoaching(mboClient.getEmail(), subject, text, true, null);
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("Error [sendPtFeedbackRequest]: {}",ex.getMessage());
            sendErrorNotification("Error [sendPtFeedbackRequest]: " + ex.getMessage(), "sendPtFeedbackRequest");
        }

    }

    @Override
    public void sendUnservicedLeadToCoach(PtTracker ptTracker) {

        try {
            if (ptTracker != null) {
                EnrolmentData enrolmentData = getMemberDao().getEnrolmentDataById(ptTracker.getEnrolmentDataId());

                if (enrolmentData != null) {

                    Gym gym = getGymDao().getGymByLocationId(Integer.parseInt(enrolmentData.getLocationId()));
                    Staff coach = null;
                    Staff ptm;
                    String[] strArr;

                    if (gym != null) {
                        strArr = gym.getPersonalTrainingManagerId().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
                        ptm = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));
                    } else {
                        ptm = null;
                    }

                    if (enrolmentData.getPersonalTrainer() != null) {

                        if (!ptTracker.getPersonalTrainer().equals(Constants.NO_COMP_SESSION_ID) && !enrolmentData.getPersonalTrainer().equals(Constants.ASSIGN_TO_PTM_ID)) {
                            strArr = ptTracker.getPersonalTrainer().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
                            coach = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));
                        } else if (ptTracker.getPersonalTrainer().equals(Constants.ASSIGN_TO_PTM_ID)) {
                            coach = ptm;
                        } else {
                            coach = null;
                        }
                    }

                    if (coach != null) {

                        String SIGN_OFF = gym.getLocationId() == 5 ? "The Bunker Gym" : "Fitness Playground " + gym.getName();

                        String subject = enrolmentData.getFirstName() + " " + enrolmentData.getLastName() + " has No First Session Booked";

                        String text = "<p>Hi " + coach.getFirstName() + ",</p>" +
                                "<p>" + enrolmentData.getFirstName() + " " + enrolmentData.getLastName() + " has not been booked for their first PT Session with you.</p>" +
                                "<p>Please contact " + enrolmentData.getFirstName() + " on " + enrolmentData.getPhone() + " to get them started with their Personal Training.</p>" +
                                "<p>If you have booked the first session but have not added the session to your Mindbody calendar please update your Mindbody calendar.</p>" +
                                "<p>Let me know if you are having any troubles contacting " + enrolmentData.getFirstName() + ".</p>" +
                                "<p>Many Thanks,<br/>" + ptm.getName() + " | Head Coach<br/>" + SIGN_OFF + "</p>";

                        sendEmailFromCoaching(coach.getEmail(), subject, text, true, ptm.getEmail());
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("Error [sendUnservicedLeadToCoach]: {}",ex.getMessage());
            sendErrorNotification("Error [sendUnservicedLeadToCoach]: " + ex.getMessage(), "sendUnservicedLeadToCoach");
        }
    }

    @Override
    public void sendUnservicedLeadToHeadCoach(PtTracker ptTracker) {

        try {
            if (ptTracker != null) {
                EnrolmentData enrolmentData = getMemberDao().getEnrolmentDataById(ptTracker.getEnrolmentDataId());

                if (enrolmentData != null) {

                    Gym gym = getGymDao().getGymByLocationId(Integer.parseInt(enrolmentData.getLocationId()));
                    Staff coach = null;
                    Staff ptm;
                    String[] strArr;

                    if (gym != null) {
                        strArr = gym.getPersonalTrainingManagerId().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
                        ptm = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));
                    } else {
                        ptm = null;
                    }

                    if (enrolmentData.getPersonalTrainer() != null) {

                        if (!ptTracker.getPersonalTrainer().equals(Constants.NO_COMP_SESSION_ID) && !enrolmentData.getPersonalTrainer().equals(Constants.ASSIGN_TO_PTM_ID)) {
                            strArr = ptTracker.getPersonalTrainer().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
                            coach = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));
                        } else if (ptTracker.getPersonalTrainer().equals(Constants.ASSIGN_TO_PTM_ID)) {
                            coach = ptm;
                        } else {
                            coach = null;
                        }
                    }

                    if (coach != null) {

                        String SIGN_OFF = gym.getLocationId() == 5 ? "The Bunker Gym" : "Fitness Playground " + gym.getName();

                        String subject = enrolmentData.getFirstName() + " " + enrolmentData.getLastName() + " has No First Session Booked";

                        String text = "<p>Hi " + ptm.getFirstName() + ",</p>" +
                                "<p>" + enrolmentData.getFirstName() + " " + enrolmentData.getLastName() + " has not been booked for their first PT Session with " + coach.getName() +".</p>" +
                                "<p>The first session may have not being added the session to " + coach.getFirstName() + "'s Mindbody calendar.</p>" +
                                "<p>Many Thanks.<br/>" + SIGN_OFF + "</p>";

                        sendEmailFromCoaching(ptm.getEmail(), subject, text, true, ptm.getEmail());
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("Error [sendUnservicedLeadToHeadCoach]: {}",ex.getMessage());
            sendErrorNotification("Error [sendUnservicedLeadToHeadCoach]: " + ex.getMessage(), "sendUnservicedLeadToHeadCoach");
        }
    }


    @Async
    @Override
    public void sendCancellationNotificationToCoach(CancellationData cancellationData) {

        try {
            if (cancellationData.getPersonalTrainer() != null) {
                String[] strArr = cancellationData.getPersonalTrainer().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
                Staff coach = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));
                String subject = cancellationData.getFirstName() + " has cancelled their Personal Training";

                if (coach != null) {

                    String text = "<p>" + cancellationData.getFirstName() + " " + cancellationData.getLastName() + " has cancelled their Personal Training.</p>"
                            + "<p>Number of sessions completed: " + cancellationData.getPtNumberSessionsCompleted() + "</p>"
                            + "<h5><u>Feedback:</u></h5>";

                    if (cancellationData.getPtCancellationChecklist() != null) {
                        String[] split = cancellationData.getPtCancellationChecklist().split(",");
                        text += "<p>Coaching received:</p><ul>";
                        for (String s : split) {
                            text += "<li>" + s + "</li>";
                        }
                        text += "</ul>";
                    }

                    text += "<p>Goal achieved: " + cancellationData.getDidYouAchieveYourGoal() + "</p>";

                    if (cancellationData.getReasonGoalNotAchieved() != null) {
                        text += "<p>Reason why: " + cancellationData.getReasonGoalNotAchieved() + "</p>";
                    }

                    text += "<p>Rating: " + cancellationData.getPtNetPromoterScore() + " out of 10.</p>";

//                    if (cancellationData.getCoachFeedbackNegative() != null) {
//                        text += "<p>" + cancellationData.getCoachFeedbackNegative() + "</p>";
//                    }

                    if (cancellationData.getCoachFeedbackPositive() != null) {
                        text += "<p>" + cancellationData.getCoachFeedbackPositive() + "</p>";
                    }

                    text += "<p>Thanks,<br />" + cancellationData.getStaffName() + "</p>";

                    logger.info("About to send PT Cancellation notification to {}", coach.getEmail());
                    sendEmailFromForms(coach.getEmail(), subject, text, true);

                }
            }
        } catch (Exception ex) {
            logger.error("Error [sendCancellationNotificationToCoach]: {}", ex.getMessage());
            sendErrorNotification("Error [sendCancellationNotificationToCoach]: " + ex.getMessage(), "sendCancellationNotificationToCoach");
        }
    }

    @Async
    @Override
    public void sendSuspensionNotificationToCoach(MembershipChangeData membershipChangeData) {

        try {
            if (membershipChangeData.getPersonalTrainer() != null) {

                String[] strArr = membershipChangeData.getPersonalTrainer().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
                Staff coach = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));
                String subject = membershipChangeData.getFirstName() + " has Suspended their Personal Training";

                if (coach != null) {
                    String text = "<p>" + membershipChangeData.getFirstName() + " " + membershipChangeData.getLastName() + " has place their PT Session on Hold.</p>"
                            + "<p>Please note this when doing your next sessions.</p>"
                            + "<h5><u>Details:</u></h5>"
                            + "<p>Email: " + membershipChangeData.getEmail() + "</p>"
                            + "<p>Phone: " + membershipChangeData.getPhone() + "</p>"
                            + "<p>Duration: " + membershipChangeData.getCoachingSuspensionDuration() + "</p>"
                            + "<p>From: " + Helpers.cleanDate(membershipChangeData.getCoachingSuspensionFromDate()) + "</p>"
                            + "<p>To: " + Helpers.cleanDate(membershipChangeData.getCoachingReturnDate())
                            + "<p>Notes: " + membershipChangeData.getNotes() + "</p>"
                            + "<p>Thanks<br />" + membershipChangeData.getStaffName() + "</p>";

                    logger.info("About to send PT Suspension notification to {}", coach.getEmail());
                    sendEmailFromForms(coach.getEmail(), subject, text, true);
                }
            }
        }catch (Exception ex) {
            logger.error("Error [sendSuspensionNotificationToCoach]: {}", ex.getMessage());
            sendErrorNotification("Error [sendSuspensionNotificationToCoach]: " + ex.getMessage(), "sendSuspensionNotificationToCoach");
        }
    }

    @Async
    @Override
    public void sendChangeNotificationToCoach(MembershipChangeData membershipChangeData) {
        try {
            if (membershipChangeData.getPersonalTrainer() != null) {

                String[] strArr = membershipChangeData.getPersonalTrainer().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
                Staff coach = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));
                String subject = membershipChangeData.getFirstName() + " has Changed their Personal Training";

                if (coach != null) {
                    String text = "<p>" + membershipChangeData.getFirstName() + " " + membershipChangeData.getLastName() + " has changed their Personal Training.</p>"
                            + "<h5><u>Details:</u></h5>"
                            + "<p>Change: " + membershipChangeData.getChangeCoaching() +"</p>"
                            + "<p>Email: " + membershipChangeData.getEmail() + "</p>"
                            + "<p>Phone: " + membershipChangeData.getPhone() + "</p>"
                            + "<p>Coaching Modality: " + membershipChangeData.getCoachingModality() + "</p>"
                            + "<p>Training Package: " + membershipChangeData.getTrainingPackage() + "</p>"
                            + "<p>Notes: " + membershipChangeData.getNotes() + "</p>"
                            + "<p>Thanks<br />" + membershipChangeData.getStaffName() + "</p>";

                    logger.info("About to send PT Change notification to {}", coach.getEmail());
                    sendEmailFromForms(coach.getEmail(), subject, text, true);
                }
            }
        } catch (Exception ex) {
            logger.error("Error [sendUpgradeNotificationToCoach]: {}", ex.getMessage());
            sendErrorNotification("Error [sendUpgradeNotificationToCoach]: " + ex.getMessage(), "sendUpgradeNotificationToCoach");
        }
    }


    private Session getEmailSession(String username, String password) {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        return session;
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

    public GymDao getGymDao() {
        return gymDao;
    }

    @Autowired
    public void setGymDao(GymDao gymDao) {
        this.gymDao = gymDao;
    }
}
