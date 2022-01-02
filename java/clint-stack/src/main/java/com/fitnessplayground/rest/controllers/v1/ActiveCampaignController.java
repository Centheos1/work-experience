package com.fitnessplayground.rest.controllers.v1;

import com.fitnessplayground.dao.MemberDao;
import com.fitnessplayground.dao.domain.AcContact;
import com.fitnessplayground.dao.domain.AcCustomField;
import com.fitnessplayground.dao.domain.AcEmailTag;
import com.fitnessplayground.service.ActiveCampaignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RestController
//@CrossOrigin("${active.campaign.baseurl}")
@CrossOrigin("*")
@RequestMapping("/v1/activecampaign/")
public class ActiveCampaignController {

    private static final Logger logger = LoggerFactory.getLogger(ActiveCampaignController.class);
    private ActiveCampaignService activeCampaignService;
    private MemberDao memberDao;

    @Value("${fp.source.handshake.key}")
    private String HANDSHAKE_KEY;

    @RequestMapping(value = "contact", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateAcContact(@RequestBody String body) {
//        logger.info("Active Campaign Test Webhook: {}",body);
        getActiveCampaignService().handleWebhookContact(body);
    }

//    @RequestMapping(value = "test", method = RequestMethod.GET)
//    public void test(@RequestParam String auth) {
//        if (!auth.equals("1984")) return;
//        EnrolmentData enrolmentData = memberDao.findEnrolmentDataById(55);
//
//        logger.info("Inside Active Campaign Test\n{}\n",enrolmentData);
//        activeCampaignService.createContact(enrolmentData);
//        logger.info("Active Campaign Test Complete");
//    }

    @RequestMapping(value = "searchAcClient/{auth}", method = RequestMethod.GET)
    public List<AcContact> getAcContactByEmail(@PathVariable String auth, @RequestParam String email) {

        if (!auth.equals(HANDSHAKE_KEY)) {
            return null;
        }

        return getActiveCampaignService().getContactByEmail(email);
    }


    @RequestMapping(value = "tags/{auth}", method = RequestMethod.GET)
    public Iterable<AcEmailTag> getAcTags(@PathVariable String auth) {

        if (!auth.equals(HANDSHAKE_KEY)) {
            return null;
        }

        return getActiveCampaignService().getTags();
    }


    @RequestMapping(value = "fields/{auth}", method = RequestMethod.GET)
    public Iterable<AcCustomField> getAcCustomFields(@PathVariable String auth) {

        if (!auth.equals(HANDSHAKE_KEY)) {
            return null;
        }

        return getActiveCampaignService().getCustomFields();
    }


    public ActiveCampaignService getActiveCampaignService() {
        return activeCampaignService;
    }

    @Autowired
    public void setActiveCampaignService(ActiveCampaignService activeCampaignService) {
        this.activeCampaignService = activeCampaignService;
    }

    public MemberDao getMemberDao() {
        return memberDao;
    }

    @Autowired
    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }
}
