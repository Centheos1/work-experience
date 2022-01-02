package com.fitnessplayground.schedule;

import com.fitnessplayground.dao.domain.CancellationData;
import com.fitnessplayground.dao.domain.MembershipChangeData;
import com.fitnessplayground.dao.domain.ParqData;
import com.fitnessplayground.dao.domain.PtTracker;
import com.fitnessplayground.service.*;
import com.fitnessplayground.util.Helpers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableScheduling
public class FitnessPlaygroundScheduler {

    private static final Logger logger = LoggerFactory.getLogger(FitnessPlaygroundScheduler.class);

    private FormsService formsService;
    private FitnessPlaygroundService fitnessPlaygroundService;
    private EmailService emailService;
    private CloudSearchService cloudSearchService;


//    FIXME - Turning this off because Cancellation process has been changed as of 13/12/21
////    @Scheduled(fixedDelay = 3600000)
//    @Scheduled(cron = "0 00 08 * * *", zone = "Australia/Sydney")
//    @Async
//    public void fireCancellationReminders() {
//
//        if(!getFitnessPlaygroundService().isCancellationRemindersOn()) {
//            logger.info("Cancellation Reminders is offline - skipping Cancellation Reminders");
//            return;
//        }
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDate now = LocalDate.now();
//        logger.info("About to Fire Cancellation Reminders");
//
//        List<CancellationData> reminderList = getFormsService().getPendingCancellations();
//
//        for (CancellationData c : reminderList) {
//
//            LocalDate submissionDate = LocalDate.parse(c.getCreateDate(), formatter);
//
//            Period period = Period.between(now, submissionDate);
//            int diff = period.getDays();
//
////            logger.info("fireCancellationReminders now: {} < createDate: {} == {}", now, submissionDate, diff);
//
//            if (diff < -2) {
//
////                Fire Lambda
//                getFormsService().sendCancellationReminder(c);
//
////                Update status to OVERDUE
//                c.setStatus(MemberStatus.CANCELLATION_AUTHORISATION_OVERDUE.getStatus());
//                c.setUpdateDate(Helpers.getDateNow());
//
//                getFormsService().updateCancellationData(c);
//            }
//        }
//    }


//    @Scheduled(fixedDelay = 3600000)
    @Scheduled(cron = "0 30 08 * * *", zone = "Australia/Sydney")
    @Async
    public void fireMembershipChangeReminders() {

        if(!getFitnessPlaygroundService().isMembershipChangeRemindersOn()) {
            logger.info("Membership Change Reminders is offline - skipping Membership Change Reminders");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate now = LocalDate.now();

        List<MembershipChangeData> reminderList = getFormsService().getPendingMembershipChange();

        logger.info("About to Fire Membership Change {} Reminders", reminderList.size());

        for (MembershipChangeData c : reminderList) {

            LocalDate submissionDate = LocalDate.parse(c.getCreateDate(), formatter);

            Period period = Period.between(now, submissionDate);
            int diff = period.getDays();

            logger.info("fireMembershipChangeReminders now: {} < createDate: {} == {}", now, submissionDate, diff);

            if (diff < -2) {

                logger.info("fireMembershipChangeReminders -> {} IS OVERDUE: {}",c.getId(),c.getCreateDate());

//                Fire Lambda
                getFormsService().sendMembershipChangeReminder(c);

//                Update status to OVERDUE
                c.setStatus(MemberStatus.MEMBERSHIP_CHANGE_AUTHORISATION_OVERDUE.getStatus());
                c.setUpdateDate(Helpers.getDateNow());

                getFormsService().updateMembershipChangeData(c);
            }
        }
    }


//    @Scheduled(fixedDelay = 3600000)
   @Scheduled(cron = "0 50 08 * * *", zone = "Australia/Sydney")
   @Async
    public void fireParqReminders() {

        if (!getFitnessPlaygroundService().isParqRemindersOn()) {
            logger.info("Parq Reminders is offline - skipping Par-Q Reminders");
            return;
        }

        List<PtTracker> ptTrackerList = getFormsService().getPendingParq();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate now = LocalDate.now();

        logger.info("About to Fire {} Par-Q Reminders", ptTrackerList.size());

        for (PtTracker p : ptTrackerList) {

            LocalDate submissionDate = LocalDate.parse(p.getCreateDate(), formatter);
            Period period = Period.between(now, submissionDate);

            int diff = period.getDays();

            logger.info("Time period between submission and now: {} days",diff);

            if (diff < 0) {
                getEmailService().sendParqReminder(p);

                p.setStatus(MemberStatus.PAR_Q_OVERDUE.getStatus());
                p.setCommunicationsStatus(CommunicationsStatus.PAR_Q_REMINDER_SENT.getStatus());
                p.setCommunicationsUpdateDate(Helpers.getDateNow());

                getFormsService().updatePtTracker(p);
            }

        }

    }

//    @Scheduled(fixedDelay = 3600000)
    @Scheduled(cron = "0 30 05 * * *", zone = "Australia/Sydney")
    @Async
    public void firePtSessionCountUpdate() {

        if (!getFitnessPlaygroundService().isUpdatePtSessionCountOn()) {
            logger.info("Update PT Session Count is offline - skipping Update PT Session");
            return;
        }

        getFitnessPlaygroundService().updatePtSessionsCount();
        fireParqReminders();
    }


//        @Scheduled(fixedDelay = 3600000)
    @Scheduled(cron = "0 50 9 * * *", zone = "Australia/Sydney")
    @Async
    public void fireUnservicedPtNotifications() {

        if (!getFitnessPlaygroundService().isNotifyUnservicedPtOn()) {
            logger.info("Update Unserviced PT Notifications is offline - skipping Unserviced PT Notifications");
            return;
        }

        try {
            ArrayList<PtTracker> ptTrackers = getFitnessPlaygroundService().getPtTrackerByNoFirstSession();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDate now = LocalDate.now();

            for (PtTracker p : ptTrackers) {
                LocalDate submissionDate = LocalDate.parse(p.getCreateDate(), formatter);
                Period period = Period.between(now, submissionDate);

                int diff = period.getDays();

                logger.info("Time period between submission and now: {} days",diff);

                if (diff == -3) {
                    getEmailService().sendUnservicedLeadToCoach(p);
                }

                if (diff == -7) {
                    getEmailService().sendUnservicedLeadToHeadCoach(p);
                }
            }

        } catch (Exception ex) {
            logger.error("Error notifying Unserviced PT: {}",ex.getMessage());
        }

    }


    @Scheduled(fixedDelay = 3600000)
    @Async
    public void refreshCloudSearch() {
        logger.info("Refresh Cloud Search");
        getCloudSearchService().refresh();
    }


    public EmailService getEmailService() {
        return emailService;
    }

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }


    public FormsService getFormsService() {
        return formsService;
    }

    @Autowired
    public void setFormsService(FormsService formsService) {
        this.formsService = formsService;
    }

    public FitnessPlaygroundService getFitnessPlaygroundService() {
        return fitnessPlaygroundService;
    }

    @Autowired
    public void setFitnessPlaygroundService(FitnessPlaygroundService fitnessPlaygroundService) {
        this.fitnessPlaygroundService = fitnessPlaygroundService;
    }

    public CloudSearchService getCloudSearchService() {
        return cloudSearchService;
    }

    @Autowired
    public void setCloudSearchService(CloudSearchService cloudSearchService) {
        this.cloudSearchService = cloudSearchService;
    }
}
