package com.fitnessplayground.schedule;

import com.fitnessplayground.dao.FPOpsConfigDao;
import com.fitnessplayground.dao.MemberDao;
import com.fitnessplayground.dao.domain.MemberCreditCard;
import com.fitnessplayground.dao.domain.ops.FPOpsConfig;
import com.fitnessplayground.service.EncryptionService;
import com.fitnessplayground.service.FitnessPlaygroundService;
import com.fitnessplayground.service.KeapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SecurityScheduler {

    private static final Logger logger = LoggerFactory.getLogger(SecurityScheduler.class);

    private EncryptionService encryptionService;

    private FitnessPlaygroundService fitnessPlaygroundService;

    @Scheduled(cron = "0 0 6 * * *")
    public void syncEncryptionKeys() {

        if (!getFitnessPlaygroundService().isRefreshEncryptionKeysOn()) {
            logger.info("Refresh Encryption Keys is Off");
            return;
        }

        getEncryptionService().setKeys();
        logger.info("Keys are set");
    }


    public EncryptionService getEncryptionService() {
        return encryptionService;
    }

    @Autowired
    public void setEncryptionService(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    public FitnessPlaygroundService getFitnessPlaygroundService() {
        return fitnessPlaygroundService;
    }

    @Autowired
    public void setFitnessPlaygroundService(FitnessPlaygroundService fitnessPlaygroundService) {
        this.fitnessPlaygroundService = fitnessPlaygroundService;
    }
}
