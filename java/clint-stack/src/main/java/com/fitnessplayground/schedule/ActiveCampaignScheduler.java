package com.fitnessplayground.schedule;

import com.fitnessplayground.dao.MemberDao;
import com.fitnessplayground.dao.MonitoringDao;
import com.fitnessplayground.dao.domain.AcContact;
import com.fitnessplayground.dao.domain.AcCustomField;
import com.fitnessplayground.dao.domain.ActiveCampaignDto.ACContact;
import com.fitnessplayground.dao.domain.EnrolmentData;
import com.fitnessplayground.dao.domain.PerformanceMonitor;
import com.fitnessplayground.dao.domain.fpSourceDto.DigitalPreExData;
import com.fitnessplayground.dao.domain.fpSourceDto.EntityLookUp;
import com.fitnessplayground.service.ActiveCampaignService;
import com.fitnessplayground.service.FPSourceService;
import com.fitnessplayground.util.Constants;
import com.fitnessplayground.util.Helpers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableScheduling
public class ActiveCampaignScheduler {

    private static final Logger logger = LoggerFactory.getLogger(ActiveCampaignScheduler.class);

    private  ActiveCampaignService activeCampaignService;

    @Value("${fp.source.handshake.key}")
    private String HANDSHAKE_KEY;


//    @Scheduled(fixedDelay = 3600000)
    @Scheduled(cron = "0 00 03 * * *", zone = "Australia/Sydney")
    @Async
    public void syncContactList() {

        if(!getActiveCampaignService().isActiveCampaignOn()) {
            logger.info("Active Campaign is offline - skipping Contact List Sync");
            return;
        }

        ArrayList<ACContact> contacts = getActiveCampaignService().getAllContacts();
        logger.info("Synced {} ACContacts at {}",contacts.size(), Helpers.getDateNow());
    }

//    @Scheduled(fixedDelay = 3600000)
    @Scheduled(cron = "0 30 02 * * *", zone = "Australia/Sydney")
    @Async
    public void syncAllTags() {

        if(!getActiveCampaignService().isActiveCampaignOn()) {
            logger.info("Active Campaign is offline - skipping Tag List Sync");
            return;
        }

        logger.info("Inside syncAllTags");
        getActiveCampaignService().getAllTags();
        logger.info("syncAllTags Complete");
    }

//    @Scheduled(fixedDelay = 3600000)
    @Scheduled(cron = "0 35 02 * * *", zone = "Australia/Sydney")
    @Async
    public void syncAllFields() {

        if(!getActiveCampaignService().isActiveCampaignOn()) {
            logger.info("Active Campaign is offline - skipping Custom Field List Sync");
            return;
        }

        logger.info("Inside getAllCustomFields");
        ArrayList<AcCustomField> customFields = getActiveCampaignService().getAllCustomFields();
        logger.info("GetAllCustomFields completed {} custom fields",customFields.size());
    }


    public ActiveCampaignService getActiveCampaignService() {
        return activeCampaignService;
    }

    @Autowired
    public void setActiveCampaignService(ActiveCampaignService activeCampaignService) {
        this.activeCampaignService = activeCampaignService;
    }
}
