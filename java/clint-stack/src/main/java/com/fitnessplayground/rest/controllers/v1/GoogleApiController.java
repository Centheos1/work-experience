package com.fitnessplayground.rest.controllers.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin("${google.api.origin}")
@CrossOrigin("*")
@RequestMapping("/v1/googleapi/")
public class GoogleApiController {

    private static final Logger logger = LoggerFactory.getLogger(GoogleApiController.class);

    @RequestMapping(value = "notifications", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void receiveUpdateStaffRequest(@RequestBody String notification) {

        logger.info("Google API Notification Received\n{}",notification);

    }
}
