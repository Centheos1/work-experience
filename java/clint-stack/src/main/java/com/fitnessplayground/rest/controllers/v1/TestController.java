package com.fitnessplayground.rest.controllers.v1;

import com.fitnessplayground.dao.MemberDao;
import com.fitnessplayground.dao.domain.*;
import com.fitnessplayground.dao.domain.formstackDto.PtReassignSubmission;
import com.fitnessplayground.dao.domain.mboDto.Client;
import com.fitnessplayground.dao.domain.mboDto.Visit;
import com.fitnessplayground.dao.domain.temp.SearchByPersDetails;
import com.fitnessplayground.dao.repository.*;
import com.fitnessplayground.service.*;
import com.fitnessplayground.util.Constants;
import com.fitnessplayground.util.Helpers;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/test")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Value("${fp.source.handshake.key}")
    private String HANDSHAKE_KEY;

    private FitnessPlaygroundService fitnessPlaygroundService;
    private FormsService formsService;
    private MemberDao memberDao;
    private EmailService emailService;
    private MindBodyService mindBodyService;
    private ActiveCampaignService activeCampaignService;

    ArrayList<String> l = new ArrayList<>();

//    private RestTemplate restTemplate;

//    @Autowired
//    private ResourceLoader resourceLoader;

//    @Value("classpath:CompanyDashboardCredentials.json")
//    Resource resource;


//curl --header "Content-Type: application/json" \
//--header "x-fp-authorization: CgnkHCLoAiXgGdfB9ykLQwOERMT2" \
//--request GET \
//http://localhost:8080/v1/test/cloudSearch/Clint/10

//    @RequestMapping(value = "cloudSearch/{query}/{size}", method = RequestMethod.GET)
//    public String cloudSearch(@PathVariable String query, @PathVariable Integer size) {
//        logger.info("Cloud Search: query => {} | size => {}",query, size);
//
//        String response = null;
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
//        headers.setContentType(MediaType.APPLICATION_JSON);
////        MboUserRequest user = new MboUserRequest(username, password);
////        HttpEntity<String> entity = new HttpEntity<MboUserRequest>(user, headers);
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//
//        String BASE_URL = "https://doc-fp-members-e5y6whbvfibtlrpduz7d5dfby4.ap-southeast-2.cloudsearch.amazonaws.com/2013-01-01/search";
//
//        String QUERY = "?q='" + query + "'*&q.options={fields:['first_name','last_name','email','access_key_number','phone']}&size=" + size;
//
//        String URL = BASE_URL + QUERY;
//
//        logger.info("Cloud Search URL: {}", URL);
//
//
////        try {
//            response = getRestTemplate().exchange(URL, HttpMethod.GET, entity, String.class).getBody();
//
//            logger.info("Cloud Search response: {}", response);
//
////        } catch(Exception e) {
////            logger.error("Error Cloud Search Request: {}", e.getMessage());
////        }
//
////        } finally {
//////            This will return null if the call is not a 200 status
////            return response;
////        }
//
//
//        return "Cloud Search";
//    }


    @RequestMapping(value = "workflow", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void fsWebflowTest(@RequestBody String body) {
        logger.info("Worflow Test Submission: {}",body);
    }


    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String getEnrolments(@RequestParam String auth) {
//        logger.info("In pdfWriter getEnrolments **********");
        if (!auth.equals(HANDSHAKE_KEY)) return null;

//        @Value("classpath:data/CompanyDashboardCredentials.json") Resource file;

//        String file = new ClassPathResource("CompanyDashboardCredentials.json").getPath();

////        Resource resource = resourceLoader.getResource("classpath:CompanyDashboardCredentials.json");
//        try {
//            InputStream stream = resource.getInputStream();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        String path = "./src/main/resources/CompanyDashboardCredentials.json";
//
        try {
            Path file = ResourceUtils.getFile(path).toPath();
            logger.info("{}",file);
            InputStream serviceAccount = new FileInputStream(file.toString());
            logger.info("{}",serviceAccount);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    @RequestMapping(value = "test3/{id}", method = RequestMethod.GET)
    public void test3(@PathVariable String id) {
        logger.info("Firing Test 3: {}",id);
//        EnrolmentData enrolmentData = getMemberDao().getEnrolmentDataById(566);
//        enrolmentData.setPersonalTrainer("100000111::152065");
//        logger.info("About to trigger notification to {}"); //,enrolmentData.getPersonalTrainer());
//        getEmailService().sendEnrolmentDataNotificationToCoach(enrolmentData);

//        String toEmail = "clint@thefitnessplayground.com.au";
//        String subject = "Test HTML Email";
//        String text = "<h1>Header</h1><a href='https://www.fitnessplayground.com.au'>click here</a>";
//
//        getEmailService().sendEmailFromForms(toEmail,subject,text,true);

//        CancellationData cancellationData = getMemberDao().getCancellationDataById(Long.parseLong("2404"));
//        cancellationData.setPersonalTrainer("100000111::152065");
//        getEmailService().sendCancellationNotificationToCoach(cancellationData);

        MembershipChangeData membershipChangeData = getMemberDao().getMembershipChangeDataById(Long.parseLong(id));
        membershipChangeData.setPersonalTrainer("100000111::152065");

        if (membershipChangeData == null) {
            logger.info("No Submission Found");
        }

        logger.info("Found: {} {} {}",membershipChangeData.getId(), membershipChangeData.getFirstName(), membershipChangeData.getLastName());
        logger.info("Found: {}, {}, {}",membershipChangeData.getStatus(), membershipChangeData.getChangeTypeMembership(), membershipChangeData.getChangeCoaching());


        if (membershipChangeData.getStatus().equals(MemberStatus.MEMBERSHIP_CHANGE_AUTHORISED.getStatus()) && membershipChangeData.getChangeTypeMembership().contains("Update My Coaching")) {

            logger.info("Authorized Coaching Update");

            if (membershipChangeData.getChangeCoaching().contains("Suspend" )) {
                logger.info("Found Suspend");
                getEmailService().sendSuspensionNotificationToCoach(membershipChangeData);
            }

            if (membershipChangeData.getChangeCoaching().contains("Upgrade") || membershipChangeData.getChangeCoaching().contains("Downgrade")) {
                logger.info("Found Upgrade");
                getEmailService().sendChangeNotificationToCoach(membershipChangeData);
            }
        }

        logger.info("Done");
    }



    @RequestMapping(value = "test", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity handleRedirect(@RequestBody PtReassignSubmission body) {

        logger.info("Inside Post Test: {}",body);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "async", method = RequestMethod.GET)
    public void doAsyncTest() {

        for (int i = 1; i <= 3; i++) {
            logger.info("Controller about to hit async service i = {}",i);
            CompletableFuture<String> completableFuture = fitnessPlaygroundService.testAsync(1);
            fitnessPlaygroundService.testAsync(i);
        }

        logger.info("Exiting doAsyncTest()");

       CompletableFuture<String> result = completableFuture.thenApplyAsync(s -> s);
       try {
           logger.info("Back to Controller from service i = {} -> {}",1,result.get());
       } catch (InterruptedException e) {
           e.printStackTrace();
       } catch (ExecutionException e) {
           e.printStackTrace();
       }

    }

    public FitnessPlaygroundService getFitnessPlaygroundService() {
        return fitnessPlaygroundService;
    }

    @Autowired
    public void setFitnessPlaygroundService(FitnessPlaygroundService fitnessPlaygroundService) {
        this.fitnessPlaygroundService = fitnessPlaygroundService;
    }

    public FormsService getFormsService() {
        return formsService;
    }

    @Autowired
    public void setFormsService(FormsService formsService) {
        this.formsService = formsService;
    }

    public MemberDao getMemberDao() {
        return memberDao;
    }

    @Autowired
    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public EmailService getEmailService() {
        return emailService;
    }

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    public MindBodyService getMindBodyService() {
        return mindBodyService;
    }

    @Autowired
    public void setMindBodyService(MindBodyService mindBodyService) {
        this.mindBodyService = mindBodyService;
    }

    public ActiveCampaignService getActiveCampaignService() {
        return activeCampaignService;
    }

    @Autowired
    public void setActiveCampaignService(ActiveCampaignService activeCampaignService) {
        this.activeCampaignService = activeCampaignService;
    }

//    public RestTemplate getRestTemplate() {
//        return restTemplate;
//    }
//
//    @Autowired
//    public void setRestTemplate(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
}
