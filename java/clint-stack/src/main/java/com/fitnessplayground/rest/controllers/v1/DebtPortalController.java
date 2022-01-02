package com.fitnessplayground.rest.controllers.v1;

import com.fitnessplayground.dao.domain.DebtPortal;
import com.fitnessplayground.service.DebtPortalService;
import com.fitnessplayground.service.FitnessPlaygroundService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/debt")
public class DebtPortalController {

    private static final Logger logger = LoggerFactory.getLogger(DebtPortalController.class);

    @Value("${fp.authorisation.header}")
    private String FP_AUTHORIZATION_HEADER;

    private FitnessPlaygroundService fitnessPlaygroundService;
    private DebtPortalService debtPortalService;

    @RequestMapping(value = "dataWarehouseSyncOn", method = RequestMethod.GET)
    public Boolean isDataWarehouseSyncOn(@RequestHeader Map<String, String> headers) {

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        //      Security check
        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Submission User");
            return false;
        }

        return getFitnessPlaygroundService().isDataWarehouseSyncOn();
    }

    @RequestMapping(value = "debtportal", method = RequestMethod.GET)
    public Iterable<DebtPortal> getAll(@RequestHeader Map<String, String> headers) {

        logger.info("Inside Get All Debt Portal Controller");

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        //      Security check
        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Submission User");
            return null;
        }

        return getDebtPortalService().getAllDebtPortal();
    }

    @RequestMapping(value = "getAllCurrent", method = RequestMethod.GET)
    public ArrayList<DebtPortal> getAllCurrent(@RequestHeader Map<String, String> headers) {

        logger.info("Inside Get all Current Debt Portal Controller");

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        //      Security check
        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Submission User");
            return null;
        }

        return getDebtPortalService().getAllCurrentDebtPortal();
    }

    @RequestMapping(value = "getCommsList", method = RequestMethod.GET)
    public ArrayList<DebtPortal> getDebtPortalCommsList(@RequestHeader Map<String, String> headers) {

        logger.info("Inside Get Debt Portal Comms List Controller");

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        //      Security check
        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Submission User");
            return null;
        }

        return getDebtPortalService().getDebtPortalCommsList();
    }

    @RequestMapping(value = "debtportal/{id}", method = RequestMethod.GET)
    public DebtPortal getDebtPortalCommsList(@RequestHeader Map<String, String> headers, @PathVariable Long id) {
        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        logger.info("Inside Get Debt Portal By Id Controller");

        //      Security check
        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Submission User");
            return null;
        }

        return getDebtPortalService().getDebtPortalById(id);
    }

    @RequestMapping(value = "debtportal", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public DebtPortal saveEnrolmentData(@RequestHeader Map<String, String> headers, @RequestBody String body) {

        logger.info("Inside Save Debt Portal Controller");

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        //      Security check
        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Submission User");
            return null;
        }

        DebtPortal debtPortal = new Gson().fromJson(body, DebtPortal.class);

        debtPortal = getDebtPortalService().saveDebtPortal(debtPortal);

        return debtPortal;
    }


    private boolean validateStaffId(String UID) {
        return getFitnessPlaygroundService().validateStaffId(UID);
//        return true;
    }


    public FitnessPlaygroundService getFitnessPlaygroundService() {
        return fitnessPlaygroundService;
    }

    @Autowired
    public void setFitnessPlaygroundService(FitnessPlaygroundService fitnessPlaygroundService) {
        this.fitnessPlaygroundService = fitnessPlaygroundService;
    }

    public DebtPortalService getDebtPortalService() {
        return debtPortalService;
    }

    @Autowired
    public void setDebtPortalService(DebtPortalService debtPortalService) {
        this.debtPortalService = debtPortalService;
    }
}
