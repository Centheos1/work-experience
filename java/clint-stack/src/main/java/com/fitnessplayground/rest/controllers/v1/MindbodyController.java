package com.fitnessplayground.rest.controllers.v1;

import com.fitnessplayground.dao.domain.mboDto.*;
import com.fitnessplayground.service.FitnessPlaygroundService;
import com.fitnessplayground.service.MindBodyService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;

@RestController
@CrossOrigin(origins = "${mbo.cors.origin}")
@RequestMapping("/v1/mbo/")
public class MindbodyController {

    private static final Logger logger = LoggerFactory.getLogger(MindbodyController.class);

    @Value("${mindbody.webhook.header.signatureKey}")
    private String HEADER_SIGNATURE_KEY;

    @Value("${mindbody.webhook.messageSignatureKey.clientContracts}")
    private String MBO_CLIENT_CONTRACT_KEY;

    @Value("${mindbody.webhook.messageSignatureKey.client}")
    private String MBO_CLIENT_KEY;

    @Value("${fp.authorisation.header}")
    private String FP_AUTHORIZATION_HEADER;

    private MindBodyService mindBodyService;
    private FitnessPlaygroundService fitnessPlaygroundService;


    @RequestMapping(value = "login", method = RequestMethod.POST)
    public MboUserResponse getUserToken(@RequestBody MboUserRequest user) {

        MboUserResponse response = null;
        try {
//            TODO - this is hardcoded for Sydney only
            response = getMindBodyService().getUserToken(user.getUsername(), user.getPassword(), true);
        } catch (Exception ex) {
            logger.error("Error logging into MBO via v6: {}", ex.getMessage());
        } finally {
            return response;
        }

    }


//    Client Contracts Webhook
    @RequestMapping(value = "clientContract", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void clientContractWebhook(@RequestHeader Map<String, String> headers, @RequestBody String body) {

        try {

            if (!verifySignature(MBO_CLIENT_CONTRACT_KEY, body, headers.get(HEADER_SIGNATURE_KEY))) {
                logger.info("HASH SIGNATURE VERIFICATION FAIL");
                return;
            }

            MboHookClientContract clientContract = new Gson().fromJson(body, MboHookClientContract.class);

//            logger.info("clientContract: {}",clientContract.toString());

            getMindBodyService().handleWebhookClientContracts(clientContract);

        } catch (Exception e) {
            logger.error("Error clientContractWebhook(): {}", e.getMessage());
        }
        return;
    }


    @RequestMapping(value = "clientContract", method = RequestMethod.HEAD)
    public void clientContractHead() {
//        logger.info("Client Contract HEAD received");
        return;
    }


//    Clients Webhook
    @RequestMapping(value = "client", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void clientWebhook(@RequestHeader Map<String, String> headers, @RequestBody String body) {

        logger.info("Inside clientWebhook");

         try {

             if (!verifySignature(MBO_CLIENT_KEY, body, headers.get(HEADER_SIGNATURE_KEY))) {
                 logger.info("HASH SIGNATURE VERIFICATION FAIL");
                 return;
             }

             MboHookClient client = new Gson().fromJson(body, MboHookClient.class);

//             logger.info("client: {}",client.toString());

             getMindBodyService().handleWebhookClient(client);

         } catch (Exception e) {
             logger.error("Error clientWebhook(): {}", e.getMessage());
         }
         return;
    }

    @RequestMapping(value = "client", method = RequestMethod.HEAD)
    public void clientHead() {
        return;
    }


    private static boolean verifySignature(String key, String data, String handshake) throws Exception {

        String signature = "sha256=";
        final String ALGO = "HMACSHA256";
        final String CHARSET = "UTF-8";

        Mac mac = Mac.getInstance(ALGO);
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(CHARSET), mac.getAlgorithm());
        mac.init(secretKey);

        byte[] hash = mac.doFinal(data.getBytes(CHARSET));
        signature += new String(Base64.getEncoder().encode(hash));

//        logger.info("signature = {}", signature);
//        logger.info("handshake = {}", handshake);
//        logger.info("verified = {}", signature.equals(handshake));
//        logger.info("2. Base64 = {}", Base64.getEncoder().encodeToString(mac.doFinal(data.getBytes("UTF-8"))));
//        logger.info("3. Hex = {}", Hex.encodeHexString(mac.doFinal(data.getBytes("UTF-8"))));

        return signature.equals(handshake);
    }



    @RequestMapping(value = "services/{siteId}", method = RequestMethod.GET)
    public ArrayList<Service> getMboServices(@RequestHeader Map<String, String> headers, @PathVariable Long siteId) {

        logger.info("Inside Get Services for {}",siteId);

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        //      Security check
        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Submission User");
            return null;
        }

        return getMindBodyService().getMboServices();
    }



    @RequestMapping(value = "sessionTypes/{siteId}", method = RequestMethod.GET)
    public ArrayList<SessionType> getMboSessionTypes(@RequestHeader Map<String, String> headers, @PathVariable Long siteId) {

        logger.info("Inside Get Session Types for {}",siteId);

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        //      Security check
        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Submission User");
            return null;
        }

        return getMindBodyService().getMboSessionTypes();

    }


    @RequestMapping(value = "locations/{siteId}", method = RequestMethod.GET)
    public ArrayList<Location> getMboLocations(@RequestHeader Map<String, String> headers, @PathVariable Long siteId) {

        logger.info("Inside Get Locations for {}",siteId);

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        //      Security check
        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Submission User");
            return null;
        }

        return getMindBodyService().getMboLocations();

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

    public MindBodyService getMindBodyService() {
        return mindBodyService;
    }

    @Autowired
    public void setMindBodyService(MindBodyService mindBodyService) {
        this.mindBodyService = mindBodyService;
    }

}
