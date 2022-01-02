package com.fitnessplayground.rest.controllers.v1;

import com.fitnessplayground.dao.domain.fpSourceDto.*;
import com.fitnessplayground.service.ActiveCampaignService;
import com.fitnessplayground.service.FPSourceService;
import com.fitnessplayground.service.FitnessPlaygroundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@CrossOrigin("${fp.reports.origin}")
//@CrossOrigin("*")
@RequestMapping("/v1/reports/")
public class FPReportsController {

    private static final Logger logger = LoggerFactory.getLogger(FPReportsController.class);

    @Value("${fp.source.handshake.key}")
    private String HANDSHAKE_KEY;

    @Value("${fp.authorisation.header}")
    private String FP_AUTHORIZATION_HEADER;

//    @Autowired
    private FPSourceService fpSourceService;

//    @Autowired
    private ActiveCampaignService activeCampaignService;
    private FitnessPlaygroundService fitnessPlaygroundService;

    @RequestMapping(value = "testDigitalPreEx", method = RequestMethod.GET)
    public DigitalPreExData testGetPreExData(@RequestParam String auth) {
//        logger.info("In pdfWriter getEnrolments **********");
        if (!auth.equals(HANDSHAKE_KEY)) return null;

        EntityLookUp entityLookUp = new EntityLookUp();

        entityLookUp.setAuth(HANDSHAKE_KEY);
        entityLookUp.setFirstName("Jenny");
        entityLookUp.setLastName("Solmy");
        entityLookUp.setEmail("jennydejonquieres@gmail.com");
        entityLookUp.setPhone("+61424808407");

        DigitalPreExData digitalPreExData = fpSourceService.getDigitalPreData(entityLookUp);
        if (digitalPreExData != null) {
            logger.info(digitalPreExData.toString());
        }
        return digitalPreExData;
    }

    @RequestMapping(value = "syncStaff", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void receiveUpdateStaffRequest(@RequestBody FPUpdateStaffRequest fpUpdateStaffRequest) {
//    public void receiveUpdateStaffRequest(@RequestBody String fpUpdateStaffRequest) {
        logger.info("Update Staff Received");

        if (!fpUpdateStaffRequest.getHandshakeKey().equals(HANDSHAKE_KEY)) { return; }

        fpSourceService.handleStaffUpdate(fpUpdateStaffRequest.getStaffs());
    }

    @RequestMapping(value = "ptParQ", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void receivePtParqSubmission(@RequestBody FPReceivePTParQRequest fpReceivePTParQRequest) {
        logger.info("PT ParQ received");
        if (!fpReceivePTParQRequest.getHandshakeKey().equals(HANDSHAKE_KEY)) { return; }
        fpSourceService.handlePTParQ(fpReceivePTParQRequest.getData());
    }

    @RequestMapping(value = "ptReassign", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void receivedReassignACPersonalTrainer(@RequestBody FPReassignPersonalTrainerRequest fpReassignPersonalTrainerRequest) {
        logger.info("receivedReassignACPersonalTrainer received");
        if (!fpReassignPersonalTrainerRequest.getHandshakeKey().equals(HANDSHAKE_KEY)) { return; }
//        fpSourceService.handleActiveCampaignUpdatePersonalTrainer(fpReassignPersonalTrainerRequest);
        activeCampaignService.processReassignPersonalTrainer(fpReassignPersonalTrainerRequest);
    }


    @RequestMapping(value = "companyDashboard", method = RequestMethod.GET)
    public CompanyDashboard getCompanyDashboardData(@RequestHeader Map<String, String> headers) {

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return null;
        }

        return getFitnessPlaygroundService().getCompanyDashboardData();
    }


    private boolean validateStaffId(String UID) {
        return getFitnessPlaygroundService().validateStaffId(UID);
    }

    public FPSourceService getFpSourceService() {
        return fpSourceService;
    }

    @Autowired
    public void setFpSourceService(FPSourceService fpSourceService) {
        this.fpSourceService = fpSourceService;
    }

    public ActiveCampaignService getActiveCampaignService() {
        return activeCampaignService;
    }

    @Autowired
    public void setActiveCampaignService(ActiveCampaignService activeCampaignService) {
        this.activeCampaignService = activeCampaignService;
    }

    public FitnessPlaygroundService getFitnessPlaygroundService() {
        return fitnessPlaygroundService;
    }

    @Autowired
    public void setFitnessPlaygroundService(FitnessPlaygroundService fitnessPlaygroundService) {
        this.fitnessPlaygroundService = fitnessPlaygroundService;
    }
}
