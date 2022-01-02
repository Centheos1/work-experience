package com.fitnessplayground.service.impl;

import com.fitnessplayground.dao.StaffDao;
import com.fitnessplayground.dao.domain.Staff;
import com.fitnessplayground.dao.domain.StaffAccess;
import com.fitnessplayground.dao.domain.fpSourceDto.*;
import com.fitnessplayground.service.ActiveCampaignService;
import com.fitnessplayground.service.FPSourceService;
import com.fitnessplayground.service.FitnessPlaygroundService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class FPSourceServiceImpl implements FPSourceService {

    private static final Logger logger = LoggerFactory.getLogger(FPSourceServiceImpl.class);

    private int retryCount;
    private final int RETRY_BREAK = 3;
    private boolean isValid;
    private int requestLimit = 100;
    private int requestOffset;
    private int count;
    private int totalResults;

    @Value("${fp.reports.baseurl}")
    private String BASE_URL;

    @Value("${fp.source.handshake.key}")
    private String HANDSHAKE_KEY;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StaffDao staffDao;

//    @Autowired
    private FitnessPlaygroundService fitnessPlaygroundService;

//    @Autowired
    private ActiveCampaignService activeCampaignService;

    @Override
    public DigitalPreExData getDigitalPreData(EntityLookUp entityLookUp) {

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
                response = restTemplate.exchange(BASE_URL + "getPreExData", HttpMethod.POST, entity, FPGetDigitalPreExResponse.class).getBody();
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

    @Override
    public void handleStaffUpdate(ArrayList<MboStaff> staffs) {

        Staff staff;

        for (MboStaff s : staffs) {
            if (s.getMboId() != null && s.getSiteId() != null && Long.parseLong(s.getMboId()) >= 1) {
                staff = staffDao.getStaffByMboId(Long.parseLong(s.getMboId()), Long.parseLong(s.getSiteId()));

                if (staff == null) {
                    staff = Staff.create(s);
                } else {
                    staff = Staff.update(s, staff);
                }
                staffDao.saveStaff(staff);
            }
        }
        syncStaffAuthorisations();
        fitnessPlaygroundService.refreshGyms();
        fitnessPlaygroundService.refreshUIStaffs();
    }

    @Override
    public void handlePTParQ(FPPTParQData data) {
        logger.info("Inside processPTParQ");
        activeCampaignService.processPTParQ(data);
    }


    private void syncStaffAuthorisations() {
        ArrayList<StaffAccess> accesses = Lists.newArrayList(staffDao.getAllStaffAccess());

        for (StaffAccess a : accesses) {
            Staff staff = staffDao.getStaffByMboId(a.getMboId(), a.getSiteId());
            logger.info("syncStaffAuthorisations() look for {}",a.getName());
            if (staff == null) {
                staffDao.deleteStaffAccess(a);
            } else {
                staff.setFirebaseId(a.getFirebaseId());
                staffDao.saveStaff(staff);
            }
        }
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

    public FitnessPlaygroundService getFitnessPlaygroundService() {
        return fitnessPlaygroundService;
    }

    @Autowired
    public void setFitnessPlaygroundService(FitnessPlaygroundService fitnessPlaygroundService) {
        this.fitnessPlaygroundService = fitnessPlaygroundService;
    }

    public ActiveCampaignService getActiveCampaignService() {
        return activeCampaignService;
    }

    @Autowired
    public void setActiveCampaignService(ActiveCampaignService activeCampaignService) {
        this.activeCampaignService = activeCampaignService;
    }
}
