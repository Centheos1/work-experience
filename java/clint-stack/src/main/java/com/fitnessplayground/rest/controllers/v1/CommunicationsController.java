package com.fitnessplayground.rest.controllers.v1;

import com.fitnessplayground.dao.CommunicationsDto.InternalCommsMCNotesResponse;
import com.fitnessplayground.dao.domain.formstackDto.PtReassignSubmission;
import com.fitnessplayground.dao.domain.temp.UIGym;
import com.fitnessplayground.service.CommunicationsService;
import com.fitnessplayground.service.EmailService;
import com.fitnessplayground.service.FitnessPlaygroundService;
import com.fitnessplayground.service.FormsService;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
//@CrossOrigin(origins = "${communications.origin}")
@CrossOrigin(origins = "*")
@RequestMapping("/v1/communications")
public class CommunicationsController {

    private static final Logger logger = LoggerFactory.getLogger(CommunicationsController.class);

    private FormsService formsService;
    private EmailService emailService;

    @Value("${communications.resend.parq.redirect.url}")
    private String PAR_Q_REDIRECT_URL;


    @RequestMapping(value = "ptReassign", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity receivePtReassign(@RequestBody PtReassignSubmission body) {

        logger.info("Inside receivePtReassign: {}",body);

        getFormsService().handlePtReassign(body);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    @RequestMapping(value = "resendParq/{id}", method = RequestMethod.GET)
    public ResponseEntity resendParq(@PathVariable Long id) {

        try {

            getEmailService().resendParq(id);

        } catch (Exception ex) {
            logger.error("Invalid id: {}",ex.getMessage());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        String url = PAR_Q_REDIRECT_URL;

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



    public FormsService getFormsService() {
        return formsService;
    }

    @Autowired
    public void setFormsService(FormsService formsService) {
        this.formsService = formsService;
    }

    public EmailService getEmailService() {
        return emailService;
    }

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

}
