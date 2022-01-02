package com.fitnessplayground.schedule;

import com.fitnessplayground.dao.MemberDao;
import com.fitnessplayground.dao.MonitoringDao;
import com.fitnessplayground.dao.domain.*;
import com.fitnessplayground.dao.domain.mboDto.File;
import com.fitnessplayground.dao.domain.mboDto.MboUploadDocumentRequest;
import com.fitnessplayground.model.mindbody.api.client.Client;
import com.fitnessplayground.model.mindbody.api.staff.Staff;
import com.fitnessplayground.model.mindbody.api.util.MindBodyStaffServiceUtil;
import com.fitnessplayground.service.*;
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
public class MindBodyQueryScheduler {

    private static final Logger logger = LoggerFactory.getLogger(MindBodyQueryScheduler.class);

    private boolean isNotActive = true;

    @Value("${mindbody.service.username}")
    private String USERNAME;

    @Value("${mindbody.service.password}")
    private String PASSWORD;

    private FitnessPlaygroundService fitnessPlaygroundService;
    private MindBodyService mindBodyService;


//    @Scheduled(fixedDelay = 3600000)
    @Scheduled(cron = "0 30 4 * * *", zone = "Australia/Sydney")
    public void testMboV6() {
        getMindBodyService().getUserToken(USERNAME, PASSWORD, true);
    }


//    @Scheduled(fixedDelay = 36000000)
    @Scheduled(cron = "0 45 22 * * *", zone = "Australia/Sydney")
    @Async
    public void syncServices() {

        if(!getFitnessPlaygroundService().isMindBodyOnline()) {
            logger.info("MindBody is offline - skipping Service Sync");
            return;
        }

        if(!getFitnessPlaygroundService().isSyncMboServicesOn()) {
            logger.info("Sync MBO Services is offline - skipping Service Sync");
            return;
        }

        ArrayList<MboService> services = getMindBodyService().syncAllServices();
        if (services != null) {
            logger.info("Mbo Service update complete. {} services updated",services.size());
        } else {
            logger.error("Failed to update Mbo Services");
        }
    }


//    @Scheduled(fixedDelay = 36000000)
    @Scheduled(cron = "0 55 22 * * *", zone = "Australia/Sydney")
    @Async
    public void syncProducts() {

        if (!getFitnessPlaygroundService().isMindBodyOnline()) {
            logger.info("MindBody is offline - skipping Product Sync");
            return;
        }

        if (!getFitnessPlaygroundService().isSyncMboProductsOn()) {
            logger.info("Sync MBO Products is offline - skipping Product Sync");
            return;
        }

        ArrayList<MboProduct> products = getMindBodyService().syncAllProducts();
        if (products != null) {
            logger.info("Mbo Product update complete. {} products updated",products.size());
        } else {
            logger.error("Failed to update Mbo Products");
        }
    }

//    @Scheduled(fixedDelay = 3600000)
    @Scheduled(cron = "0 30 22 * * *", zone = "Australia/Sydney")
    @Async
    public void syncContracts() {

        if(!getFitnessPlaygroundService().isMindBodyOnline()) {
            logger.info("MindBody is offline - skipping Contract Sync");
            return;
        }

        if(!getFitnessPlaygroundService().isSyncMboContractsOn()) {
            logger.info("Sync MBO Contracts is offline - skipping Contract Sync");
            return;
        }

        ArrayList<MboContract> contracts = getMindBodyService().syncAllContracts();
        if (contracts != null) {
            logger.info("Contract update complete. {} contracts updated",contracts.size());
        } else {
            logger.error("Failed to update Mbo Contracts");
        }
    }

    private PerformanceMonitor getPerformanceMonitoringEntity(String status, Long elapsedTime, String entityType, Long entityId, String actionType) {
        PerformanceMonitor performanceMonitor = new PerformanceMonitor(status,
                elapsedTime != null ? Long.toString(elapsedTime) : null,
                entityType,
                Long.toString(entityId),
                actionType);
        return performanceMonitor;
    }



/**    @Scheduled(fixedDelay = 3600000) |-> DO NOT RUN THIS AS THE PROCESS REPEATS EVERY HOUR FROM THE START!! **/
////        1st Sunday of the Month at 1:02am
    @Scheduled(cron = "0 02 1 1-7 * 0", zone = "Australia/Sydney")
    @Async
    public void syncClientContracts() {

        if(!getFitnessPlaygroundService().isMindBodyOnline()) {
            logger.info("MindBody is offline - skipping Contract Sync");
            return;
        }

        if (!getFitnessPlaygroundService().isSyncMboClientContractsOn()) {
            logger.info("Sync Client Contracts is offline - skipping Contract Sync");
            return;
        }

        getMindBodyService().syncAllClientContracts();
    }

    @Async
    @Scheduled(fixedDelay = 3600000)
    public void setStaffMap() {
        logger.info("Refreshing Staff Map");
        getMindBodyService().setStaffMap();
    }

    @Scheduled(fixedDelay = 3600000)
    public void refreshUIGyms() {
        logger.info("Refreshing UI Gyms");
        getFitnessPlaygroundService().refreshGyms();
    }

    @Async
    @Scheduled(fixedDelay = 300000)
    public void refreshManualSubmissions() {
        logger.info("Refreshing Manual Submissions");
        getFitnessPlaygroundService().refreshManualSubmissionsMap();
    }

    @Async
    @Scheduled(fixedDelay = 300000)
    public void refreshSubmissions() {
        logger.info("Refreshing Submissions");
        getFitnessPlaygroundService().refreshSubmissionsMap();
    }


//    Every Sunday night
    @Scheduled(cron = "0 55 23 * * 0", zone = "Australia/Sydney")
    @Async
    public void syncMboClients() {

        if(!getFitnessPlaygroundService().isMindBodyOnline()) {
            logger.info("MindBody is offline - skipping Client Sync");
            return;
        }

        if(!getFitnessPlaygroundService().isSyncMboClientsOn()) {
            logger.info("Sync Clients is offline - skipping Client Sync");
            return;
        }

        logger.info("Kicking off MBO Client Sync");

        ArrayList<MboClient> clients = getMindBodyService().syncAllClients();
        if (clients != null) {
            logger.info("MBO Clients Update Complete -> {} Updated", clients.size());
        } else {
            logger.error("Failed to update Mbo Clients");
        }

//        Version 5.1
//        List<Client> clients = mindBodyClientService.getAllClients();
//        logger.info("Updated {} clients",clients.size());
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
}
