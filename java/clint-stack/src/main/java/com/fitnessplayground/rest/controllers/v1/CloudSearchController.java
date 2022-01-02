package com.fitnessplayground.rest.controllers.v1;

import com.fitnessplayground.dao.domain.CloudSearch;
import com.fitnessplayground.dao.domain.temp.CloudSearchAddRequest;
import com.fitnessplayground.dao.domain.temp.CloudSearchRequest;
import com.fitnessplayground.dao.domain.temp.FindEnrolment;
import com.fitnessplayground.service.CloudSearchService;
import com.fitnessplayground.service.FitnessPlaygroundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
//@CrossOrigin("${fp.cloudsearch.origin}")
@CrossOrigin("*")
@RequestMapping("/v1/cloudsearch/")
public class CloudSearchController {

    private static final Logger logger = LoggerFactory.getLogger(CloudSearchController.class);

    @Value("${fp.authorisation.header}")
    private String FP_AUTHORIZATION_HEADER;

    private CloudSearchService cloudSearchService;
    private FitnessPlaygroundService fitnessPlaygroundService;


    @RequestMapping(value = "add", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addMembers(@RequestHeader Map<String, String> headers, @RequestBody CloudSearchAddRequest data) {

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        logger.info("Inside Cloud Search addMembers Controller");

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }


        try {
            getCloudSearchService().addMembers(data.getMembers());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            logger.error("Error adding members to Cloud Search: {}",ex.getMessage());
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }

    }


    @RequestMapping(value = "search", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public ArrayList<CloudSearch> searchMembers(@RequestHeader Map<String, String> headers, @RequestBody CloudSearchRequest query) {
        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        logger.info("Inside Cloud Search search Controller find: {}",query.getQuery());

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Request");
            return null;
        }

        try {
            return getCloudSearchService().searchMembers(query.getQuery());
        } catch (Exception ex) {
            logger.error("Error searching members in Cloud Search: {}",ex.getMessage());
            return null;
        }

    }

    private boolean validateStaffId(String UID) {
        return getFitnessPlaygroundService().validateStaffId(UID);
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
