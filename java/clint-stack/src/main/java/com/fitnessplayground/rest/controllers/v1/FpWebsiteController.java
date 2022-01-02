package com.fitnessplayground.rest.controllers.v1;

import com.fitnessplayground.dao.domain.ClassReviewData;
import com.fitnessplayground.dao.domain.LeadData;
import com.fitnessplayground.dao.domain.WebFlowDto.WebflowApiRequest;
import com.fitnessplayground.dao.domain.WebReferralData;
import com.fitnessplayground.dao.domain.temp.ClassReviewSubmission;
import com.fitnessplayground.dao.domain.temp.GravityFormsWebhookLead;
import com.fitnessplayground.dao.domain.temp.GravityFormsWebhookReferral;
import com.fitnessplayground.dao.domain.temp.WebLead;
import com.fitnessplayground.service.FpWebsiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
//@CrossOrigin("${fp.website.api.origin}")
@CrossOrigin("*")
@RequestMapping("/v1/web/")
public class FpWebsiteController {

    private static final Logger logger = LoggerFactory.getLogger(FpWebsiteController.class);

    private FpWebsiteService fpWebsiteService;

    private Boolean checkBot(String x) {
        boolean isBot = false;

        if (x.startsWith("+1")) {
            isBot = true;
        }

        int checkNumeric = 0;

        try {
            checkNumeric = Integer.parseInt(x.substring(x.length() - 1));
        } catch (Exception ex) {
            logger.info("{} is not a number. Bot submission",checkNumeric);
            isBot = true;
        }

        return isBot;
    }


    @RequestMapping(value = "redirect", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity handleRedirect(@RequestBody String body) {

        logger.info("Inside Redirect: {}\n",body);

        LeadData leadData = null;

        try {
            body = URLDecoder.decode(body, StandardCharsets.UTF_8.toString());
            logger.info("Decoded body:\n{}",body);
            leadData = getFpWebsiteService().handleLead(body);
//            Check if bot submission
//            if (!leadData.getPhone().startsWith("+1")) {
            if (leadData != null && !checkBot(leadData.getPhone())) {
                getFpWebsiteService().processFormSubmission(leadData);
            }

        } catch (UnsupportedEncodingException e) {
            logger.error("Error decoding body\n{}",e.getMessage());
        }

        String params = null;

        if (leadData != null) {
            params = "?source_id=" + leadData.getId(); // + body;
        }

        String url = null;

        for (String p : body.split("&")) {

            if (p.contains("redirect_url")) {
                String[] split = p.split("=");
                logger.info("{} : {}", split[0], split[1]);
                url = leadData != null ? split[1] + params : split[1];
            }
        }

        logger.info("url: {}",url);

        HttpHeaders headers = new HttpHeaders();

        if (url != null) {
            headers.setLocation(URI.create(url));
            logger.info("{}", headers.getLocation().toString());
            return new ResponseEntity<>(headers, HttpStatus.FOUND);

        } else {

            headers.setLocation(URI.create("https://www.fitnessplayground.com.au"));
            logger.info("{}", headers.getLocation().toString());
            return new ResponseEntity<>(headers, HttpStatus.FOUND);

        }
    }


//    This is from the Webflow API
    @RequestMapping(value = "form_submission", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void handleFormSubmission(@RequestBody WebflowApiRequest body) {

        logger.info("Inside handleFormSubmission: {}",body);

        getFpWebsiteService().processWebflowApiFormSubmission(body.getData());
    }


    @RequestMapping(value = "lead", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity receiveLead(@RequestBody WebLead webLead) {

        logger.info("Receive Web Lead");

        getFpWebsiteService().handleWebLead(webLead);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


    @RequestMapping(value = "review", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity receiveReviewSubmission(@RequestBody ClassReviewSubmission body) {

        logger.info("receiveReviewSubmission: {}",body);

//        ClassReviewData classReviewData = new Gson().fromJson(body, ClassReviewData.class);

        ClassReviewData classReviewData = ClassReviewData.from(body);

        logger.info("classReviewData: {}",classReviewData.toString());

        getFpWebsiteService().handleClassReview(classReviewData);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


    @RequestMapping(value = "referral", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity handleReferral(@RequestBody String body) {

        logger.info("\nInside Web Referral: {}\n",body);

        WebReferralData webReferralData = null;

        try {
            body = URLDecoder.decode(body, StandardCharsets.UTF_8.toString());
//            logger.info("Decoded body:\n{}",body);
            webReferralData = getFpWebsiteService().handleWebReferral(body);
            logger.info("webReferralData:\n{}",webReferralData);
            getFpWebsiteService().processWebReferral(webReferralData);

        } catch (UnsupportedEncodingException e) {
            logger.error("Error decoding body\n{}",e.getMessage());
        }

        if (webReferralData != null) {
            String url = webReferralData.getRedirectUrl();

            HttpHeaders headers = new HttpHeaders();

            if (url != null) {
                headers.setLocation(URI.create(url));
                logger.info("Redirecting to {}", headers.getLocation().toString());
                return new ResponseEntity<>(headers, HttpStatus.FOUND);

            } else {

                headers.setLocation(URI.create("https://www.fitnessplayground.com.au"));
                logger.info("Redirecting to {}", headers.getLocation().toString());
                return new ResponseEntity<>(headers, HttpStatus.FOUND);

            }
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("https://www.fitnessplayground.com.au"));
        logger.info("Redirecting to {}", headers.getLocation().toString());
        return new ResponseEntity<>(headers, HttpStatus.FOUND);

//        For Testing Only
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value = "gravityFormsWebhook", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void handleGravityFormsWebhook(@RequestBody GravityFormsWebhookLead gravityFormsWebhookLead) {
        logger.info("Inside handleGravityFormsWebhook:\n{}",gravityFormsWebhookLead);

        try {
            WebLead webLead = new WebLead();

            if (gravityFormsWebhookLead.getName() != null) {
                webLead.setName(gravityFormsWebhookLead.getName());
            }

            if (gravityFormsWebhookLead.getFullName() != null) {
                webLead.setName(gravityFormsWebhookLead.getFullName());
            }

            if (gravityFormsWebhookLead.getEmail() != null) {
                webLead.setEmail(gravityFormsWebhookLead.getEmail());
            }

            if (gravityFormsWebhookLead.getEmailAddress() != null) {
                webLead.setEmail(gravityFormsWebhookLead.getEmailAddress());
            }

            if (gravityFormsWebhookLead.getPhone() != null) {
                webLead.setPhone(gravityFormsWebhookLead.getPhone());
            }

            if (gravityFormsWebhookLead.getMobilePhone() != null) {
                webLead.setPhone(gravityFormsWebhookLead.getMobilePhone());
            }

            if (gravityFormsWebhookLead.getGymName() != null) {
                webLead.setLocation(gravityFormsWebhookLead.getGymName());
            } else if (gravityFormsWebhookLead.getGymName2() != null) {
                webLead.setLocation(gravityFormsWebhookLead.getGymName2());
            }

            if (gravityFormsWebhookLead.getLocation() != null) {
                webLead.setLocation(gravityFormsWebhookLead.getLocation());
            }

            if (gravityFormsWebhookLead.getSourceName() != null) {
                webLead.setSource(gravityFormsWebhookLead.getSourceName());
            }

            if (gravityFormsWebhookLead.getLeadSource() != null) {
                webLead.setSource(gravityFormsWebhookLead.getLeadSource());
            }

            String notes = "";

            if (gravityFormsWebhookLead.getUyg_category() != null) {
                notes += "UYG Category: " + gravityFormsWebhookLead.getUyg_category();
            }

            if (gravityFormsWebhookLead.getUyg_is_member() != null) {
                notes += "\nUYG is Member: " + gravityFormsWebhookLead.getUyg_is_member();

                if (gravityFormsWebhookLead.getUyg_is_member().contains("Current")) {
                    webLead.setSource("UYG Member");
                } else {
                    webLead.setSource("UYG Non-Member");
                }
            }

            if (gravityFormsWebhookLead.getUyg_has_coach_interest() != null) {
                notes += "\nUGY Coach Interest: " + gravityFormsWebhookLead.getUyg_has_coach_interest();
            }

            if (gravityFormsWebhookLead.getIsMember() != null) {
                notes += "Member Status: " + gravityFormsWebhookLead.getIsMember();
            }

            if (!notes.isEmpty()) {
                webLead.setNotes(notes);
            }

            if (gravityFormsWebhookLead.getNotes() != null) {
                webLead.setNotes(gravityFormsWebhookLead.getNotes());
            }

            webLead.setGoogleClickId(gravityFormsWebhookLead.getGoogleClickId());
            webLead.setFacebookCampaignId(gravityFormsWebhookLead.getFacebookCampaignId());

            getFpWebsiteService().handleWebLead(webLead);

        } catch (Exception ex) {
            logger.error("Error handling gravityFormsWebhook: {}",ex.getMessage());
            return;
        }

        logger.info("handleGravityFormsWebhook email: {} done!\n",gravityFormsWebhookLead.getEmailAddress());
        return;
    }



    @RequestMapping(value = "gravityFormsReferral", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity handleGravityFormsReferral(@RequestBody GravityFormsWebhookReferral gravityFormsWebhookReferral) {
        logger.info("\nInside handleGravityFormsReferral: {}\n", gravityFormsWebhookReferral);

        try {
            WebReferralData webReferralData = getFpWebsiteService().handleGravityFormsWebReferral(gravityFormsWebhookReferral);
            getFpWebsiteService().processWebReferral(webReferralData);

        } catch (Exception ex) {
            logger.error("Error handling handleGravityFormsReferral: {}",ex.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


    public FpWebsiteService getFpWebsiteService() {
        return fpWebsiteService;
    }

    @Autowired
    public void setFpWebsiteService(FpWebsiteService fpWebsiteService) {
        this.fpWebsiteService = fpWebsiteService;
    }
}
