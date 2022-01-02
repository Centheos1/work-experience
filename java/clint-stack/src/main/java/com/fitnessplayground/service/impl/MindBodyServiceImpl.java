package com.fitnessplayground.service.impl;

import com.fitnessplayground.dao.ClientDao;
import com.fitnessplayground.dao.MemberDao;
import com.fitnessplayground.dao.SaleDao;
import com.fitnessplayground.dao.StaffDao;
import com.fitnessplayground.dao.domain.*;
import com.fitnessplayground.dao.domain.Staff;
import com.fitnessplayground.dao.domain.lambdaDto.EnrolmentPdfResponse;
import com.fitnessplayground.dao.domain.mboDto.*;
import com.fitnessplayground.dao.domain.mboDto.Class;
import com.fitnessplayground.dao.domain.temp.EnrolmentDataDocument;
import com.fitnessplayground.dao.domain.temp.FindEnrolment;
import com.fitnessplayground.dao.domain.temp.PersonName;
import com.fitnessplayground.service.EmailService;
import com.fitnessplayground.service.MemberStatus;
import com.fitnessplayground.service.MindBodyService;
import com.fitnessplayground.util.Constants;
import com.fitnessplayground.util.GymName;
import com.fitnessplayground.util.Helpers;
import org.apache.tomcat.util.bcel.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class MindBodyServiceImpl implements MindBodyService {

    private static final Logger logger = LoggerFactory.getLogger(MindBodyServiceImpl.class);

    private String[] locIds = {"1","4"};
    private int requestLimit = 100;
    private int requestOffset;
    private int count;
    private int totalResults;
    private int retryCount;
    private final int RETRY_BREAK = 1;
    boolean isValid;
    private String accessToken;
    private String gw_accessToken;
    private HashMap<Long, String> allStaffMembersMap = new HashMap<>();
    private HashMap<Long, String> allStaffMembersMap_Darwin = new HashMap<>();
    private HashMap<String, String> staffMap = new HashMap<>();

    @Value("${mindbody.service.is.test.invocation}")
    private String IS_TEST_INVOCATION;

    @Value("${mindbody.service.rest.baseurl}")
    private String BASE_URL;

    @Value("${mindbody.service.rest.apikey}")
    private String API_KEY;

    @Value("${mindbody.service.siteid}")
    private String SITE_ID;

    @Value("${mindbody.service.siteid.darwin}")
    private String GW_SITE_ID;

    @Value("${mindbody.service.username}")
    private String USERNAME;

    @Value("${mindbody.service.password}")
    private String PASSWORD;

    @Value("${mindbody.service.username.darwin}")
    private String GW_USERNAME;

    @Value("${mindbody.service.password.darwin}")
    private String GW_PASSWORD;

    @Value("${mindbody.product.access.key.price}")
    private String ACCESS_KEY_PRICE;

    @Value("${mindbody.contract.first7days.mboId}")
    private String FIRST_7_DAYS_CONTRACT_MBOID_str;

    @Value("${mindbody.contract.first14days.mboId}")
    private String FIRST_14_DAYS_CONTRACT_MBOID_str;

    @Value("${mindbody.contract.first30days.mboId}")
    private String FIRST_30_DAYS_CONTRACT_MBOID_str;

    @Value("${pdf.writer.lambda.enrolment.endpoint}")
    private String ENROLMENT_PDF_WRITER_ENDPOINT;

    @Value("${pdf.writer.lambda.enrolment.email.address}")
    private String ENROLMENT_PDF_WRITER_EMAIL_ADDRESS;

    @Value("${pdf.writer.lambda.enrolment.email.password}")
    private String ENROLMENT_PDF_WRITER_EMAIL_PASSWORD;

    @Value("${mail.notify.it}")
    private String NOTIFY_IT;

    @Value("${mail.notify.generalmanager}")
    private String NOTIFY_GENERAL_MANAGER;


    private EmailService emailService;

    private SaleDao saleDao;

    private ClientDao clientDao;

    private StaffDao staffDao;

    private MemberDao memberDao;

    private RestTemplate restTemplate;


    @Override
    public CompletableFuture<String> processTestAsync() {

        logger.info("processTestAsync()");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error("ERROR: Test Async was interrupted");
        }

        Random random = new Random();
        String result = "";
        if (random.nextInt(10) % 3 == 0) {
            result = MemberStatus.ERROR.getStatus();
        } else {
            result = MemberStatus.COMPLETE.getStatus();
        }
        return CompletableFuture.completedFuture(result);
    }


    @Override
    public MboUserResponse getUserToken(String username, String password, boolean isSydney) {

        MboUserResponse response = null;
        HttpHeaders headers = setHeaders(isSydney, false);
        MboUserRequest user = new MboUserRequest(username, password);
        HttpEntity<MboUserRequest> entity = new HttpEntity<MboUserRequest>(user, headers);
        try {
            response = getRestTemplate().exchange(BASE_URL + "usertoken/issue", HttpMethod.POST, entity, MboUserResponse.class).getBody();

//            This is a short way to do the same thing. Still can't access the headers though
//            response = restTemplate.postForObject(BASE_URL + "usertoken/issue", entity, MboUserResponse.class);

            if (isSydney) {
                setAccessToken(response.getAccessToken());
            }

            if (!isSydney) {
                setGw_accessToken(response.getAccessToken());
            }

//            logger.info("MBO Service User Token Response: {}", response.toString());
//            logger.info("MBO Service Access Token: {}", getAccessToken());
//            logger.info("MBO Service GATEWAY Access Token: {}", getGw_accessToken());

        } catch(Exception e) {
            logger.error("Error getting MBO Token: {}",e.getMessage());

        } finally {
//            This will return null if the call is not a 200 status
            return response;
        }
    }

    @Override
    public ArrayList<MboContract> syncAllContracts() {

        ArrayList<MboContract> mboContracts = new ArrayList<>();
        boolean isSuccess;

        for (String locId : getLocIds() ) {
            resetRequestVariables();
            MboContractResponse response = null;
            HttpHeaders headers = setHeaders(isSydney(locId), true);
            HttpEntity<Contract> entity = new HttpEntity<>(headers);

            do {
                isSuccess = true;
                try {
                    response = getRestTemplate().exchange(BASE_URL + "sale/contracts?LocationId=" + locId + "&limit=" + requestLimit + "&offset=" + requestOffset, HttpMethod.GET, entity, MboContractResponse.class).getBody();
                } catch (Exception ex) {
                    setRetryCount(getRetryCount() + 1);
                    logger.error("Error getting Contracts  attempt {} of {}", ex.getMessage(), getRetryCount(), RETRY_BREAK);
                    handleMboApiException(ex.getMessage());
                    isSuccess = false;
                    setValid(getRetryCount() <= RETRY_BREAK);
                }
                if (isSuccess) {
                    setTotalResults(response.getPaginationResponse().getTotalResults());
                    setCount(getCount() + response.getPaginationResponse().getPageSize());
                    setRequestOffset(getCount());
//                    logger.info("syncAllContracts: Count [{}] | Offset [{}] | Total Results [{}] | locId [{}]", getCount(), getRequestOffset(), getTotalResults(), locId);

                    mboContracts.addAll(processContracts(response.getContracts(), locId));
                    setValid(getCount() < getTotalResults());
                }
            } while (isValid());
        }
        return mboContracts;
    }


    @Override
    public ArrayList<MboService> syncAllServices() {

        boolean isSuccess;
        ArrayList<MboService> mboServices = new ArrayList<>();

        for (String locId : getLocIds() ) {
            resetRequestVariables();
            MboServiceResponse response = null;
            HttpHeaders headers = setHeaders(isSydney(locId), true);
            HttpEntity<com.fitnessplayground.dao.domain.mboDto.Service> entity = new HttpEntity<>(headers);

            do {
                isSuccess = true;
                try {
                    response = getRestTemplate().exchange(BASE_URL + "sale/services?LocationId=" + locId + "&limit=" + getRequestLimit() + "&offset=" + getRequestOffset(), HttpMethod.GET, entity, MboServiceResponse.class).getBody();
                } catch (Exception ex) {
                    setRetryCount(getRetryCount() + 1);
                    logger.error("Error getting Services  attempt {} of {}, RETRY_BREAK: {}", ex.getMessage(), getRetryCount(), RETRY_BREAK);
                    handleMboApiException(ex.getMessage());
                    isSuccess = false;
                    setValid(getRetryCount() <= RETRY_BREAK);
                }
                if (isSuccess) {
                    setTotalResults(response.getPaginationResponse().getTotalResults());
                    setCount(getCount() + response.getPaginationResponse().getPageSize());
                    setRequestOffset(getCount());
//                    logger.info("syncAllServices: Count [{}] | Offset [{}] | Total Results [{}] | locId [{}]", getCount(), getRequestOffset(), getTotalResults(), locId);
                    mboServices.addAll(processServices(response.getServices(), locId));
                    setValid(getCount() < getTotalResults());
                }
            } while (isValid());
        }
        return mboServices;
    }


    @Override
    public ArrayList<MboProduct> syncAllProducts() {

        boolean isSuccess;
        ArrayList<MboProduct> mboProducts = new ArrayList<>();

        for (String locId : getLocIds() ) {
            resetRequestVariables();
            MboProductResponse response = null;
            HttpHeaders headers = setHeaders(isSydney(locId), true);
            HttpEntity<com.fitnessplayground.dao.domain.mboDto.Product> entity = new HttpEntity<>(headers);

            do {
                isSuccess = true;
                try {
                    response = getRestTemplate().exchange(BASE_URL + "sale/products?LocationId=" + locId + "&limit=" + getRequestLimit() + "&offset=" + getRequestOffset(), HttpMethod.GET, entity, MboProductResponse.class).getBody();
                } catch (Exception ex) {
                    setRetryCount(getRetryCount() + 1);
                    logger.error("Error getting Services  attempt {} of {}, RETRY_BREAK: {}", ex.getMessage(), getRetryCount(), RETRY_BREAK);
                    handleMboApiException(ex.getMessage());
                    isSuccess = false;
                    setValid(getRetryCount() <= RETRY_BREAK);
                }
                if (isSuccess) {
                    setTotalResults(response.getPaginationResponse().getTotalResults());
                    setCount(getCount() + response.getPaginationResponse().getPageSize());
                    setRequestOffset(getCount());
//                    logger.info("syncAllServices: Count [{}] | Offset [{}] | Total Results [{}] | locId [{}]", getCount(), getRequestOffset(), getTotalResults(), locId);
                    mboProducts.addAll(processProducts(response.getProducts(), locId));
                    setValid(getCount() < getTotalResults());
                }
            } while (isValid());
        }
        return mboProducts;
    }

    @Override
    public ArrayList<MboClient> syncAllClients() {

        boolean isSuccess;
        ArrayList<MboClient> mboClients = new ArrayList<>();

        for (String locId : getLocIds()) {
//        String locId = "4";
            resetRequestVariables();
            MboGetClientResponse response = null;
            HttpHeaders headers = setHeaders(isSydney(locId), true);
            HttpEntity<Client> entity = new HttpEntity<>(headers);

            do {
                isSuccess = true;
                try {
                    response = getRestTemplate().exchange(BASE_URL
                            + "client/clients?limit=" + getRequestLimit()
                            + "&offset=" + getRequestOffset(),
                            HttpMethod.GET,
                            entity,
                            MboGetClientResponse.class)
                            .getBody();
                } catch (Exception ex) {
                    setRetryCount(getRetryCount() + 1);
                    logger.error("Error getting Clients  attempt {} of {}", ex.getMessage(), getRetryCount(), RETRY_BREAK);
                    handleMboApiException(ex.getMessage());
                    isSuccess = false;
                    setValid(getRetryCount() <= RETRY_BREAK);
                }
                if (isSuccess) {
                    setTotalResults(response.getPaginationResponse().getTotalResults());
                    setCount(getCount() + response.getPaginationResponse().getPageSize());
                    setRequestOffset(getCount());
                    mboClients.addAll(processClients(response.getClients(), locId));
//                    logger.info("Count [{}] | Offset [{}] | Total Results [{}] | locId [{}]", getCount(), getRequestOffset(), getTotalResults(), locId);
                    setValid(getCount() < getTotalResults());
                }
            } while (isValid());
        }
        return mboClients;
    }


    @Override
    public void syncAllClientContracts() {

        boolean isSuccess;
        int apiCount = 0;

        Iterable<MboClient> mboClients = getClientDao().getAllClients();
//        List<MboClient> mboClients = clientDao.getActiveClients();

        for (MboClient mboClient : mboClients) {
            if (mboClient.getStatus().equals("Active")) {

                apiCount++;
                processClientContracts(getClientContracts(mboClient), mboClient);

//                resetRequestVariables();
//                MboGetClientContractsResponse response = null;
//                HttpHeaders headers = setHeaders(isSydney(String.valueOf(mboClient.getHomeLocationID())), true);
//                HttpEntity<Client> entity = new HttpEntity<>(headers);
//
//                do {
//
//                    isSuccess = true;
////                    logger.info("Client {} {} {} {}", mboClient.getFirstName(), mboClient.getAccessKeyNumber(), mboClient.getUniqueId(), mboClient.getLocId());
//                    try {
//                        response = restTemplate.exchange(BASE_URL + "client/clientcontracts?clientId=" + mboClient.getAccessKeyNumber() + "&limit=" + getRequestLimit() + "&offset=" + getRequestOffset(), HttpMethod.GET, entity, MboGetClientContractsResponse.class).getBody();
//                    } catch (Exception ex) {
//                        setRetryCount(getRetryCount() + 1);
//                        logger.error("Error getting client contracts {} attempt {} of {}", ex.getMessage(), getRetryCount(), RETRY_BREAK);
//                        handleMboApiException(ex.getMessage());
//                        isSuccess = false;
//                        setValid(getRetryCount() <= RETRY_BREAK);
//                    }
//                    if (isSuccess) {
//                        setTotalResults(response.getPaginationResponse().getTotalResults());
//                        setCount(getCount() + response.getPaginationResponse().getPageSize());
//                        setRequestOffset(getCount());
//                        processClientContracts(response.getClientContracts(), mboClient);
//                        logger.info("syncAllClientContracts Count [{}] | Offset [{}] | Total Results [{}] | response.getClientContracts().size() [{}]", getCount(), getRequestOffset(), getTotalResults(), response.getClientContracts().size());
//                        setValid(getCount() < getTotalResults());
//                    }
//                } while (isValid());
            }
        }

        logger.info("syncAllClientContracts API Call Count: {}",apiCount);
//        return null;
    }



    //    TODO: THIS IS THE PREFERED WAY
    @Override
    public ArrayList<Location> getMboLocations() {

        boolean isSuccess;
        int apiCount = 0;
        ArrayList<Location> locations = new ArrayList<Location>();

            resetRequestVariables();
            MboGetLocationsResponse response = null;
            HttpHeaders headers = setHeaders(isSydney(Long.toString(Constants.SYDNEY_SITE_ID)), true);
            HttpEntity<Location> entity = new HttpEntity<>(headers);

            do {
                isSuccess = true;
                try {
                    response = getRestTemplate().exchange(BASE_URL
                            +"site/locations?limit="+getRequestLimit()
                            + "&offset="+getRequestOffset(),
                            HttpMethod.GET,
                            entity,
                            MboGetLocationsResponse.class)
                            .getBody();
                } catch (Exception e) {
                    setRetryCount(getRetryCount() + 1);
                    isSuccess = false;
                    handleMboApiException(e.getMessage());
                    setValid(getRetryCount() <= RETRY_BREAK);
                    logger.error("Error getting Mbo Locations {} of {} | {}", getRetryCount(), RETRY_BREAK, e.getMessage());
                }
                if (isSuccess) {
                    setTotalResults(response.getPaginationResponse().getTotalResults());
                    setCount(getCount() + response.getPaginationResponse().getPageSize());
                    setRequestOffset(getCount());
                    setValid(getCount() < getTotalResults());
                    if (response.getLocations() != null) locations.addAll(response.getLocations());
                }
            } while (isValid());

        return locations;
    }

    @Override
    public ArrayList<Visit> getClientVisits(String clientId, String fromDate, String toDate) {

        try {

            if (fromDate == null) {
                fromDate = Helpers.getDateNow();
            }

            if (toDate == null) {
                toDate = Helpers.getDateNow();
            }

            boolean isSuccess;
            ArrayList<Visit> visits = new ArrayList<>();

            resetRequestVariables();
            MboGetClientVisitsResponse response = null;
            HttpHeaders headers = setHeaders(true, true);
            HttpEntity<Visit> entity = new HttpEntity<>(headers);


            do {
                isSuccess = true;
                try {
                    response = getRestTemplate().exchange(BASE_URL
                                    +"client/clientvisits?limit="+getRequestLimit()
                                    + "&offset="+getRequestOffset()
                                    + "&StartDate="+fromDate
                                    + "&EndDate="+toDate
                                    + "&ClientId="+clientId,
                            HttpMethod.GET,
                            entity,
                            MboGetClientVisitsResponse.class)
                            .getBody();
                } catch (Exception e) {
                    setRetryCount(getRetryCount() + 1);
                    isSuccess = false;
                    handleMboApiException(e.getMessage());
                    setValid(getRetryCount() <= RETRY_BREAK);
                    logger.error("Error getting Mbo Client Visits {} of {} | {}", getRetryCount(), RETRY_BREAK, e.getMessage());
                }
                if (isSuccess) {
                    setTotalResults(response.getPaginationResponse().getTotalResults());
                    setCount(getCount() + response.getPaginationResponse().getPageSize());
                    setRequestOffset(getCount());
                    setValid(getCount() < getTotalResults());
                    visits.addAll(response.getVisits());
                }
            } while (isValid());

            logger.info("Found {} visits",visits.size());

            return visits;

        } catch (Exception ex) {
            logger.error("Error getClientVisits: {}",ex.getMessage());
            return null;
        }
    }


    //    TODO: THIS IS THE PREFERED WAY
    @Override
    public ArrayList<SessionType> getMboSessionTypes() {

        boolean isSuccess;
        int apiCount = 0;
        ArrayList<SessionType> sessionTypes = new ArrayList<SessionType>();

        resetRequestVariables();
        MboGetSessionTypesResponse response = null;
        HttpHeaders headers = setHeaders(isSydney(Long.toString(Constants.SYDNEY_SITE_ID)), true);
        HttpEntity<SessionType> entity = new HttpEntity<>(headers);

        do {
//            logger.info("TOP:  Count: {} | Return Size: {} |  Offset: {} | Retry Count: {} | Retry Break: {}", getCount(), sessionTypes.size(), getRequestOffset(), getRetryCount(), RETRY_BREAK);
            apiCount += 1;
            isSuccess = true;
            try {
                response = getRestTemplate().exchange(BASE_URL
                                +"site/sessionTypes?limit="+getRequestLimit()
                                + "&offset="+getRequestOffset(),
                        HttpMethod.GET,
                        entity,
                        MboGetSessionTypesResponse.class)
                        .getBody();
            } catch (Exception e) {
                setRetryCount(getRetryCount() + 1);
                isSuccess = false;
                handleMboApiException(e.getMessage());
                setValid(getRetryCount() <= RETRY_BREAK);
                logger.error("Error getting Mbo Locations {} of {} | {}", getRetryCount(), RETRY_BREAK, e.getMessage());
            }
            if (isSuccess) {
                setTotalResults(response.getPaginationResponse().getTotalResults());
                setCount(getCount() + response.getPaginationResponse().getPageSize());
                setRequestOffset(getCount());
                setValid(getCount() < getTotalResults());
                if (response.getSessionTypes() != null) sessionTypes.addAll(response.getSessionTypes());
//                logger.info("Total Results: {} | Count: {} | Response Size: {} | Return Size: {} |  Offset: {} | Retry Count: {} | Retry Break: {}",response.getPaginationResponse().getTotalResults(), getCount(), response.getSessionTypes().size(), sessionTypes.size(), getRequestOffset(), getRetryCount(), RETRY_BREAK);
//                response.getSessionTypes().clear();
            }
        } while (isValid());

        logger.info("getMboSessionTypes API Count: {} | Return Size: {}",apiCount, sessionTypes.size());

        return sessionTypes;
    }

    //    TODO: THIS IS THE PREFERED WAY
    @Override
    public ArrayList<com.fitnessplayground.dao.domain.mboDto.Service> getMboServices() {

        boolean isSuccess;
        int apiCount = 0;
        ArrayList<com.fitnessplayground.dao.domain.mboDto.Service> services = new ArrayList<com.fitnessplayground.dao.domain.mboDto.Service>();

        resetRequestVariables();
        MboServiceResponse response = null;
        HttpHeaders headers = setHeaders(isSydney(Long.toString(Constants.SYDNEY_SITE_ID)), true);
        HttpEntity<com.fitnessplayground.dao.domain.mboDto.Service> entity = new HttpEntity<>(headers);

        do {
            isSuccess = true;
            try {
                response = getRestTemplate().exchange(BASE_URL
                                + "sale/services?limit=" + getRequestLimit()
                                + "&offset=" + getRequestOffset(),
                        HttpMethod.GET,
                        entity,
                        MboServiceResponse.class)
                        .getBody();
            } catch (Exception e) {
                setRetryCount(getRetryCount() + 1);
                isSuccess = false;
                handleMboApiException(e.getMessage());
                setValid(getRetryCount() <= RETRY_BREAK);
                logger.error("Error getting Mbo Services {} of {} | {}", getRetryCount(), RETRY_BREAK, e.getMessage());
            }
            if (isSuccess) {
                setTotalResults(response.getPaginationResponse().getTotalResults());
                setCount(getCount() + response.getPaginationResponse().getPageSize());
                setRequestOffset(getCount());
                setValid(getCount() < getTotalResults());
                if (response.getServices() != null) services.addAll(response.getServices());
            }
        } while (isValid());

        return services;
    }

    //    TODO: THIS IS THE PREFERED WAY
    @Override
    public ArrayList<MboClass> getMboClasses(@Nullable String StartDateTime, @Nullable String EndDateTime) {

        if (StartDateTime == null) {
            StartDateTime = Helpers.getDateNow();
        }

        if (EndDateTime == null) {
            EndDateTime = Helpers.getDateNow();
        }

        boolean isSuccess;
        int apiCount = 0;
        ArrayList<MboClass> classes = new ArrayList<MboClass>();

        for (String locId : getLocIds()) {
            resetRequestVariables();
            MboGetClassesResponse response = null;
            HttpHeaders headers = setHeaders(isSydney(locId), true);
            HttpEntity<Class> entity = new HttpEntity<>(headers);

            do {
                isSuccess = true;
                try {
                    response = getRestTemplate().exchange(BASE_URL
                                    +"class/classes?limit="+getRequestLimit()
                                    + "&offset="+getRequestOffset()
                                    + "&StartDateTime="+StartDateTime
                                    + "&EndDateTime="+EndDateTime,
                            HttpMethod.GET,
                            entity,
                            MboGetClassesResponse.class)
                            .getBody();
                } catch (Exception e) {
                    setRetryCount(getRetryCount() + 1);
                    isSuccess = false;
                    handleMboApiException(e.getMessage());
                    setValid(getRetryCount() <= RETRY_BREAK);
                    logger.error("Error getting Mbo Classes {} of {} | {}", getRetryCount(), RETRY_BREAK, e.getMessage());
                }
                if (isSuccess) {
                    setTotalResults(response.getPaginationResponse().getTotalResults());
                    setCount(getCount() + response.getPaginationResponse().getPageSize());
                    setRequestOffset(getCount());
                    setValid(getCount() < getTotalResults());
//                    TODO: UP TO HERE
//                    classes.addAll(processClasses(response.getClasses(), locId));
                }
            } while (isValid());
        }
        return classes;
    }

    @Override
    public boolean isDuplicateKey(String accessKeyNumber) {

        List<MboClient> duplicateKeyMembers = getClientDao().searchDuplicateKey(accessKeyNumber);

        logger.info("searchDuplicateKey found [{}] duplicates",duplicateKeyMembers.size());

        return !duplicateKeyMembers.isEmpty();
    }

//    Find by Email or Name
    @Override
    public List<Client> searchMboClient(String searchText) {

        ArrayList<Client> foundClients = new ArrayList<>();

        for (String locId : getLocIds()) {

            resetRequestVariables();
            MboGetClientResponse response = null;
            HttpHeaders headers = setHeaders(isSydney(locId), true);
            HttpEntity<Client> entity = new HttpEntity<>(headers);

            do {
                boolean isSuccess = true;
                try {
                    response = getRestTemplate().exchange(BASE_URL + "client/clients?SearchText="+searchText +"&limit=" + getRequestLimit() + "&offset=" + getRequestOffset(), HttpMethod.GET, entity, MboGetClientResponse.class).getBody();
                } catch (Exception ex) {
                    setRetryCount(getRetryCount() + 1);
                    logger.error("Error Searching Clients  attempt {} of {}", ex.getMessage(), getRetryCount(), RETRY_BREAK);
                    handleMboApiException(ex.getMessage());
                    isSuccess = false;
                    setValid(getRetryCount() <= RETRY_BREAK);
                }
                if (isSuccess) {
                    setTotalResults(response.getPaginationResponse().getTotalResults());
                    setCount(getCount() + response.getPaginationResponse().getPageSize());
                    setRequestOffset(getCount());
                    foundClients.addAll(response.getClients());
//                    logger.info("Count [{}] | Offset [{}] | Total Results [{}] | locId [{}]", getCount(), getRequestOffset(), getTotalResults(), locId);
                    setValid(getCount() < getTotalResults());
                }
            } while (isValid());
        }
//        logger.info("Found Clients: {}",foundClients.toString());
        return foundClients;
    }


    @Override
    public List<Client> findMboClientByClientId(String clientId) {

        ArrayList<Client> foundClients = new ArrayList<>();

        for (String locId : getLocIds()) {

            resetRequestVariables();
            MboGetClientResponse response = null;
            HttpHeaders headers = setHeaders(isSydney(locId), true);
            HttpEntity<Client> entity = new HttpEntity<>(headers);

            do {
                boolean isSuccess = true;
                try {
                    response = getRestTemplate().exchange(BASE_URL + "client/clients?ClientIds="+clientId +"&limit=" + getRequestLimit() + "&offset=" + getRequestOffset(), HttpMethod.GET, entity, MboGetClientResponse.class).getBody();
                } catch (Exception ex) {
                    setRetryCount(getRetryCount() + 1);
                    logger.error("Error Searching Clients  attempt {} of {}", ex.getMessage(), getRetryCount(), RETRY_BREAK);
                    handleMboApiException(ex.getMessage());
                    isSuccess = false;
                    setValid(getRetryCount() <= RETRY_BREAK);
                }
                if (isSuccess) {
                    setTotalResults(response.getPaginationResponse().getTotalResults());
                    setCount(getCount() + response.getPaginationResponse().getPageSize());
                    setRequestOffset(getCount());
                    foundClients.addAll(response.getClients());
//                    logger.info("Count [{}] | Offset [{}] | Total Results [{}] | locId [{}]", getCount(), getRequestOffset(), getTotalResults(), locId);
                    setValid(getCount() < getTotalResults());
                }
            } while (isValid());
        }
//        logger.info("Found Clients: {}",foundClients.toString());
        return foundClients;
    }


    @Override
    public List<Client> findMboClientByUniqueIds(Long mboUniqueId) {
        ArrayList<Client> foundClients = new ArrayList<>();

        resetRequestVariables();
        MboGetClientResponse response = null;
        HttpHeaders headers = setHeaders(true, true);
        HttpEntity<Client> entity = new HttpEntity<>(headers);

        do {
            boolean isSuccess = true;
            try {
                response = getRestTemplate().exchange(BASE_URL + "client/clients?UniqueIds="+ mboUniqueId +"&limit=" + getRequestLimit() + "&offset=" + getRequestOffset(), HttpMethod.GET, entity, MboGetClientResponse.class).getBody();
            } catch (Exception ex) {
                setRetryCount(getRetryCount() + 1);
                logger.error("Error Searching Clients by Unique Id | attempt {} of {}: {}", getRetryCount(), RETRY_BREAK, ex.getMessage());
                handleMboApiException(ex.getMessage());
                isSuccess = false;
                setValid(getRetryCount() <= RETRY_BREAK);
            }
            if (isSuccess) {
                setTotalResults(response.getPaginationResponse().getTotalResults());
                setCount(getCount() + response.getPaginationResponse().getPageSize());
                setRequestOffset(getCount());
                foundClients.addAll(response.getClients());
//                logger.info("Count [{}] | Offset [{}] | Total Results [{}] | locId [{}]", getCount(), getRequestOffset(), getTotalResults());
                setValid(getCount() < getTotalResults());
            }
        } while (isValid());

        logger.info("Found Clients: {}",foundClients.size());
        return foundClients;
    }





//    Changed this to run on the handle Enrolment Thread
//    @Async
    @Override
    public void writeEnrolmentPDF(EnrolmentData enrolmentData, String clubManager) {

        logger.info("Inside writeEnrolmentPDF");

        EnrolmentDataDocument dataDocument = EnrolmentDataDocument.from(enrolmentData);

        dataDocument.setClubManager(clubManager);

        String cleanedNumber = Helpers.cleanCreditCard(dataDocument.getBdAccountNumber());
        dataDocument.setBdAccountNumber(cleanedNumber);

        cleanedNumber = Helpers.cleanCreditCard(dataDocument.getCcNumber());
        dataDocument.setCcNumber(cleanedNumber);

        dataDocument.setStaffMember(getStaffName(enrolmentData.getStaffMember()));
        dataDocument.setPersonalTrainer(getStaffName(enrolmentData.getPersonalTrainer()));

        String termsHtml = getMemberDao().getTermsAndConditionsHtml().getTermsAndConditions();
        dataDocument.setTermAndConditions(termsHtml);

        dataDocument.setSENDER_EMAIL_ADDRESS(ENROLMENT_PDF_WRITER_EMAIL_ADDRESS);
        dataDocument.setSENDER_EMAIL_PASSWORD(ENROLMENT_PDF_WRITER_EMAIL_PASSWORD);
        dataDocument.setTOKEN(accessToken);
        dataDocument.setMBO_API_KEY(API_KEY);
        dataDocument.setMBO_SITE_ID(isSydney(dataDocument.getLocationId()) ? SITE_ID : GW_SITE_ID);

        HttpEntity<EnrolmentDataDocument> entity = new HttpEntity<>(dataDocument);
        try {
            getRestTemplate().exchange(ENROLMENT_PDF_WRITER_ENDPOINT, HttpMethod.POST, entity, EnrolmentPdfResponse.class).getBody();

        } catch(Exception ex) {
            logger.error("Error Writing Enrolment PDF - enrolmentDataId: {}",enrolmentData.getId());
        }
    }

//    1. Receive Event
//    2a. getData (MBO)
//    2b. getDate (DB)
//    3. saveUpdateData

    @Override
    public void handleWebhookClientContracts(MboHookClientContract mboHookClientContract) {

        try {
            MboClient mboClient = getClientDao().findByUniqueIdAndSiteId(mboHookClientContract.getEventData().getClientUniqueId(), mboHookClientContract.getEventData().getSiteId());

            //        handleCancelled
            if (mboHookClientContract.getEventId().contains("cancelled")) {

                logger.info("MBO Client Contract Cancelled");

//            Send notification
                String text = "Mbo Contract Id: " + mboHookClientContract.getEventData().getClientContractId()
                        + " SiteId: " + mboHookClientContract.getEventData().getSiteId()
                        + "\nClient: " + mboClient.getFirstName() + " " + mboClient.getLastName()
                        + "\nTimestamp: " + mboHookClientContract.getEventInstanceOriginationDateTime()
                        + "\n\nThanks,\nClint";
                getEmailService().sendEmail(NOTIFY_IT, "MBO Contract Deleted", text);
            }


            if (mboClient == null) {
                logger.error("handleWebhookClientContracts: Mbo Client [{}] | Site: [{}] not found", mboHookClientContract.getEventData().getClientUniqueId(), mboHookClientContract.getEventData().getSiteId());
                return;
            }

            processClientContracts(getClientContracts(mboClient), mboClient);
        }catch (Exception e) {
            logger.error("Error handleWebhookClientContracts {}",e.getMessage());
        }
    }

    @Override
    public void handleWebhookClient(MboHookClient mboHookClient) {

//        handleDeactivated
//        if (mboHookClient.getEventId().contains("deactivated")) {
//            logger.info("MBO Client  Deactivated");

//            Send notification
//            String text = "Client: " + mboHookClient.getEventData().getFirstName() + " " + mboHookClient.getEventData().getLastName()
//                    + "\nEmail: " + mboHookClient.getEventData().getEmail()
//                    + "\nAccess Key: " + mboHookClient.getEventData().getClientId()
//                    + "\nTimestamp: " + mboHookClient.getEventInstanceOriginationDateTime()
//                    + "\n\nThanks,\nClint";
//            getEmailService().sendEmail(NOTIFY_IT, "MBO Client Deactivated", text);
//        }

        try {

            MboClient mboClient = getClientDao().findByUniqueIdAndSiteId(mboHookClient.getEventData().getClientUniqueId(), mboHookClient.getEventData().getSiteId());

            // FIXME: 15/06/20: this will only work if you hit the public API and get the updated Client - Check this as the webhook sends the Client Entity

            if (mboClient == null) {
                mboClient = MboClient.create(mboHookClient.getEventData());
            } else {
                mboClient = MboClient.update(mboHookClient.getEventData(), mboClient);
            }

            getClientDao().saveClient(mboClient);

        } catch (Exception e) {
            logger.error("Error handleWebhookClient {}",e.getMessage());
        }


    }


    @Override
    public MboAddNewClientRequest buildAddNewClient(EnrolmentData enrolmentData, boolean isTestSubmission, boolean skipKey, boolean skipEmail) {

        MboAddNewClientRequest request = new MboAddNewClientRequest();

        String siteId = getSiteId(isSydney(enrolmentData.getLocationId()));
        request.setSiteId(Integer.parseInt(siteId));

        if (!skipKey) {
//            request.setAccessKeyNumber(enrolmentData.getAccessKeyNumber());
            logger.info("Adding New Client with id: {}",enrolmentData.getAccessKeyNumber());
        }

        request.setAction("Added");
        request.setCreateDate(Helpers.handleDate_YYYYMMDD(enrolmentData.getCreateDate()).toString());

        request.setAddressLine1(enrolmentData.getAddress1());
        request.setAddressLine2(enrolmentData.getAddress2());
        request.setCity(enrolmentData.getCity());
        request.setState(enrolmentData.getState().toUpperCase());
        request.setCountry("AU");
        request.setPostCode(enrolmentData.getPostcode());

        request.setBirthDate(Helpers.handleDate_YYYYMMDD(enrolmentData.getDob()).toString());

        if (enrolmentData.getMemberCreditCard() != null) {

            ClientCreditCard cc = new ClientCreditCard();
            cc.setCardHolder(enrolmentData.getMemberCreditCard().getHolder());
            cc.setAddress(enrolmentData.getMemberCreditCard().getAddress());
            cc.setCity(enrolmentData.getMemberCreditCard().getCity());
            cc.setState(enrolmentData.getMemberCreditCard().getState());
            cc.setPostCode(enrolmentData.getMemberCreditCard().getPostcode());
            cc.setCardNumber(enrolmentData.getMemberCreditCard().getNumber());
            cc.setExpMonth(enrolmentData.getMemberCreditCard().getExpMonth());
            cc.setExpYear(enrolmentData.getMemberCreditCard().getExpYear());

            request.setClientCreditCard(cc);
        }

        request.setFirstName(enrolmentData.getFirstName());
        request.setLastName(enrolmentData.getLastName());
        request.setMobilePhone(enrolmentData.getPhone());

        if (!skipEmail) {
            request.setEmail(enrolmentData.getEmail());
        }
        request.setEmergencyContactInfoName(enrolmentData.getEmergencyContactName());
        request.setEmergencyContactInfoPhone(enrolmentData.getEmergencyContactPhone());

        Location location = new Location();
        location.setSiteID(Integer.parseInt(siteId));
        location.setLocId(Integer.parseInt(enrolmentData.getLocationId()));
        request.setHomeLocation(location);
        request.setProspect(false);
        request.setCompany(false);
        Liability liability = new Liability();
        liability.setAgreementDate(Helpers.handleDate_YYYYMMDD(enrolmentData.getCreateDate()).toString());
        liability.setReleased(true);
        liability.setReleasedBy(null);
        request.setLiability(liability);
        request.setLiabilityRelease(true);
        if (!enrolmentData.getGender().toLowerCase().equals("other")) {
            request.setGender(enrolmentData.getGender());
        }

        request.setNotes(enrolmentData.getNotes());

//        FIXME: This is unstable at the API
//        ArrayList<SalesRep> salesReps = new ArrayList<>();
//        SalesRep salesRep1 = new SalesRep();
//        String staffMboId ;
//        String staffSiteId;
//        String[] s;
//
//        if (enrolmentData.getStaffMember() != null && !enrolmentData.getStaffMember().isEmpty()) {
//            s = enrolmentData.getStaffMember().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
//            staffMboId = s[0];
//            staffSiteId = s[1];
//
//            String staffMember = getStaffOrPersonalTrainerName(staffMboId, staffSiteId);
//            if (staffMember != null) {
//                salesRep1 = setSalesRepName(staffMember, salesRep1);
//                salesRep1.setMboId(Long.parseLong(staffMboId));
//                salesRep1.setSalesRepNumber(1);
//                salesReps.add(salesRep1);
//            }
//        }
//
//        if (enrolmentData.getPersonalTrainer() != null && !enrolmentData.getPersonalTrainer().isEmpty() && enrolmentData.getPersonalTrainer().contains(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER)) {
//            s = enrolmentData.getPersonalTrainer().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
//            if (s.length > 0) {
//                staffMboId = s[0];
//                staffSiteId = s[1];
//                if (Long.parseLong(staffMboId) > 0) {
//                    SalesRep salesRep2 = new SalesRep();
//
//                    String personalTrainer = getStaffOrPersonalTrainerName(staffMboId, staffSiteId);
//                    if (personalTrainer != null) {
//                        salesRep2 = setSalesRepName(personalTrainer, salesRep2);
//                        salesRep2.setMboId(Long.parseLong(staffMboId));
//                        salesRep2.setSalesRepNumber(2);
//                        salesReps.add(salesRep2);
//                    }
//                }
//            }
//        }
//        request.setSaleReps(salesReps);

        request.setSendAccountEmails(true);
        request.setSendAccountTexts(true);
        request.setSendScheduleEmails(true);
        request.setSendScheduleTexts(true);
        request.setSendPromotionalEmails(true);
        request.setTest(isTestSubmission);

//        logger.info("About to return MbosyncAllClientslientRequest\n{}",request.toString());

        return request;
    }


    @Override
    public MboAddNewClientResponse addMboClient(MboAddNewClientRequest mboAddNewClientRequest) {

        logger.info("Inside addClient");

        boolean isSydney = isSydney(mboAddNewClientRequest.getHomeLocation().getLocId().toString());

        MboAddNewClientResponse response = null;
        HttpHeaders headers = setHeaders(isSydney, true);
        HttpEntity<MboAddNewClientRequest> entity = new HttpEntity<>(mboAddNewClientRequest, headers);
        try {
            response = getRestTemplate().exchange(BASE_URL + "client/addclient", HttpMethod.POST, entity, MboAddNewClientResponse.class).getBody();
//            logger.info("Add Client Response\n{}\n",response.toString());

        } catch(Exception ex) {
//            Turned this off because handling duplicate access key already
//            mboAddNewClientRequest.setAccessKeyNumber(null);
            entity = new HttpEntity<>(mboAddNewClientRequest, headers);
            response = handleMboAddNewClientException(ex.getMessage(), entity);
            logger.error("Error Adding New Client: {} | {}", mboAddNewClientRequest.getLastName(),ex.getMessage());
        }


        return response;
    }

    private MboAddNewClientResponse handleMboAddNewClientException(String errorMessage, HttpEntity<MboAddNewClientRequest> entity) {
        if (errorMessage.contains("404") || errorMessage.contains("400")) {
            try {
                return getRestTemplate().exchange(BASE_URL + "client/addclient", HttpMethod.POST, entity, MboAddNewClientResponse.class).getBody();
            } catch (Exception ex) {
                logger.error("Error Adding New Client Retry: {}",ex.getMessage());
            }
        }
        return null;
    }


    @Override
    public MboUpdateClientRequest buildUpdateClient(EnrolmentData enrolmentData, Client mboClient, boolean isTestSubmission, boolean skipKey) {

        MboUpdateClientRequest request = new MboUpdateClientRequest();

        String siteId = getSiteId(isSydney(enrolmentData.getLocationId()));
//        request.setClient().setSiteId(Integer.parseInt(siteId));

        if (!skipKey) {
             request.setNewId(enrolmentData.getAccessKeyNumber());
//            request.setAccessKeyNumber(enrolmentData.getAccessKeyNumber());
            logger.info("Updating Client with id: {}",enrolmentData.getAccessKeyNumber());
        }

        mboClient.setAccessKeyNumber(mboClient.getAccessKeyNumber());

        logger.info("buildUpdateClient clientId: {} | enrolmentData Accesskey: {}", mboClient.getAccessKeyNumber(), enrolmentData.getAccessKeyNumber());
//        mboClient.setAction("Updated");
//        request.setCreateDate(Helpers.handleDate_YYYYMMDD(enrolmentData.getCreateDate()).toString());

        mboClient.setAddressLine1(enrolmentData.getAddress1());
        mboClient.setAddressLine2(enrolmentData.getAddress2());
        mboClient.setCity(enrolmentData.getCity());
        mboClient.setState(enrolmentData.getState().toUpperCase());
        mboClient.setCountry("AU");
        mboClient.setPostalCode(enrolmentData.getPostcode());

        mboClient.setBirthDate(Helpers.handleDate_YYYYMMDD(enrolmentData.getDob()).toString());

        if (enrolmentData.getMemberCreditCard() != null) {

            ClientCreditCard cc = new ClientCreditCard();
            cc.setCardHolder(enrolmentData.getMemberCreditCard().getHolder());
            cc.setAddress(enrolmentData.getMemberCreditCard().getAddress());
            cc.setCity(enrolmentData.getMemberCreditCard().getCity());
            cc.setState(enrolmentData.getMemberCreditCard().getState());
            cc.setPostCode(enrolmentData.getMemberCreditCard().getPostcode());
            cc.setCardNumber(enrolmentData.getMemberCreditCard().getNumber());
            cc.setExpMonth(enrolmentData.getMemberCreditCard().getExpMonth());
            cc.setExpYear(enrolmentData.getMemberCreditCard().getExpYear());

            mboClient.setClientCreditCard(cc);
        }

        mboClient.setFirstName(enrolmentData.getFirstName());
        mboClient.setLastName(enrolmentData.getLastName());
        mboClient.setPhone(enrolmentData.getPhone());

//        if (!skipEmail) {
        mboClient.setEmail(enrolmentData.getEmail());
//        }
        mboClient.setEmergencyContactName(enrolmentData.getEmergencyContactName());
        mboClient.setEmergencyContactPhone(enrolmentData.getEmergencyContactPhone());

//        Location location = new Location();
//        location.setSiteID(Integer.parseInt(siteId));
//        location.setLocId(Integer.parseInt(enrolmentData.getLocationId()));
//        mboClient.setHomeLocation(location);
//        request.setProspect(false);
//        request.setCompany(false);
        Liability liability = new Liability();
        liability.setAgreementDate(Helpers.handleDate_YYYYMMDD(enrolmentData.getCreateDate()).toString());
        liability.setReleased(true);
        liability.setReleasedBy(null);
        mboClient.setLiability(liability);
        mboClient.setLiabilityRelease(true);
        if (!enrolmentData.getGender().toLowerCase().equals("other")) {
            mboClient.setGender(enrolmentData.getGender());
        }

        mboClient.setNotes(enrolmentData.getNotes());

        mboClient.setSendAccountEmails(true);
        mboClient.setSendAccountTexts(true);
        mboClient.setSendScheduleEmails(true);
        mboClient.setSendScheduleTexts(true);
        mboClient.setSendPromotionalEmails(true);

        request.setClient(mboClient);
        request.setCrossRegionalUpdate(false);
        request.setTestSubmission(isTestSubmission);

//        logger.info("About to return Update Client Request\n{}",request.toString());

        return request;
    }


    @Override
    public MboUpdateClientResponse updateMboClient(MboUpdateClientRequest request, Boolean isSydney) {
        MboUpdateClientResponse response = null;
        HttpHeaders headers = setHeaders(isSydney, true);
        HttpEntity<MboUpdateClientRequest> entity = new HttpEntity<>(request, headers);

//        logger.info("UpdateClient Request: {}",entity.toString());

        try {
            response = getRestTemplate().exchange(BASE_URL + "client/updateclient", HttpMethod.POST, entity, MboUpdateClientResponse.class).getBody();
        } catch (Exception ex) {
            logger.error("Error updating client {}",ex.getMessage());
            entity = new HttpEntity<>(request, headers);
            response = handleMboUpdateClientException( entity, ex.getMessage());
        }

        return response;
    }

    private MboUpdateClientResponse handleMboUpdateClientException(HttpEntity<MboUpdateClientRequest> entity, String errorMessage) {
        if (errorMessage.contains("404") || errorMessage.contains("400")) {
            try {
                return getRestTemplate().exchange(BASE_URL + "client/updateclient", HttpMethod.POST, entity, MboUpdateClientResponse.class).getBody();
            } catch (Exception ex) {
                logger.error("Error Updating Client Retry: {}",ex.getMessage());
            }
        }
        return null;
    }


    @Override
    public MboUpdateClientResponse updateClientAccessKey(MboUpdateAccessKeyNumberRequest request, Boolean isSydney) {

        MboUpdateClientResponse response = null;
        HttpHeaders headers = setHeaders(isSydney, true);
        HttpEntity<MboUpdateAccessKeyNumberRequest> entity = new HttpEntity<>(request, headers);
        try {
            response = getRestTemplate().exchange(BASE_URL + "client/updateclient", HttpMethod.POST, entity, MboUpdateClientResponse.class).getBody();
        } catch (Exception ex) {
            logger.error("Error updating client {}",ex.getMessage());
            entity = new HttpEntity<>(request, headers);
            response = handleMboUpdateAccessKeyNumberException( entity, ex.getMessage());
        }

        return response;
    }

    private MboUpdateClientResponse handleMboUpdateAccessKeyNumberException(HttpEntity<MboUpdateAccessKeyNumberRequest> entity, String errorMessage) {
        if (errorMessage.contains("404") || errorMessage.contains("400")) {
            try {
                return getRestTemplate().exchange(BASE_URL + "client/updateclient", HttpMethod.POST, entity, MboUpdateClientResponse.class).getBody();
            } catch (Exception ex) {
                logger.error("Error Updating Client Retry: {}",ex.getMessage());
            }
        }
        return null;
    }

    @Override
    public void sendPasswordResetEmail(MboSendPasswordResetEmailRequest request, Boolean isSydney) {

        HttpHeaders headers = setHeaders(isSydney, true);
        HttpEntity<MboSendPasswordResetEmailRequest> entity = new HttpEntity<>(request, headers);
        try {
            getRestTemplate().exchange(BASE_URL + "client/sendpasswordresetemail", HttpMethod.POST, entity, MboUpdateClientResponse.class).getBody();
        } catch (Exception ex) {
            logger.error("Error sending reset user password email  {}",ex.getMessage());
        }
    }


    @Override
    public MboAddClientDirectDebitInfoRequest buildAddClientDirectDebitInfo(EnrolmentData enrolmentData, Boolean isTestSubmission) {

        MemberBankDetail bankDetail = enrolmentData.getMemberBankDetail();
        MboAddClientDirectDebitInfoRequest request = new MboAddClientDirectDebitInfoRequest();

        if (bankDetail == null) {
            return null;
        }

        request.setTest(isTestSubmission);
        request.setClientId(enrolmentData.getAccessKeyNumber());
        request.setNameOnAccount(bankDetail.getAccountHolderName());
        request.setAccountNumber(bankDetail.getAccountNumber());
        request.setBsb(bankDetail.getBsb());
        String type = bankDetail.getAccountType().equals(Constants.SUBMISSION_BANK_ACCOUNT_TYPE_CHECK) ? Constants.MBO_DIRECT_DEBIT_INFO_ACCOUNT_TYPE_CHECK : Constants.MBO_DIRECT_DEBIT_INFO_ACCOUNT_TYPE_SAVINGS;
        request.setAccountType(type);

        return request;
    }


    @Override
    public MboAddClientDirectDebitInfoResponse addClientDirectDebitInfo(MboAddClientDirectDebitInfoRequest request, String locationId) {

        boolean isSydney = isSydney(locationId);

        MboAddClientDirectDebitInfoResponse response = null;
        HttpHeaders headers = setHeaders(isSydney, true);
        HttpEntity<MboAddClientDirectDebitInfoRequest> entity = new HttpEntity<>(request, headers);
        try {
            response = getRestTemplate().exchange(BASE_URL + "client/addclientdirectdebitinfo", HttpMethod.POST, entity, MboAddClientDirectDebitInfoResponse.class).getBody();
        } catch (Exception ex) {
//            response = handleMboAddClientDirectDebitInfoException(entity, ex.getMessage());
            logger.error("Error Adding Client Direct Debit Info {}", ex.getMessage());
        }
        return response;
    }

    private MboAddClientDirectDebitInfoResponse handleMboAddClientDirectDebitInfoException(HttpEntity<MboAddClientDirectDebitInfoRequest> entity, String errorMessage) {
        if (errorMessage.contains("404") || errorMessage.contains("400")) {
            try {
                return getRestTemplate().exchange(BASE_URL + "client/addclientdirectdebitinfo", HttpMethod.POST, entity, MboAddClientDirectDebitInfoResponse.class).getBody();
            } catch (Exception ex){
                logger.error("Error Adding Client Direct Debit Info Retry {}", ex.getMessage());
            }
        }
        return null;
    }


    @Override
    public MboPurchaseContractRequest buildPurchaseContract(MboContract contract, EnrolmentData enrolmentData, Boolean isTestSubmission) {

        if (contract == null) return null;

        logger.info("Inside buildPurchaseContracts");
        MboPurchaseContractRequest request = new MboPurchaseContractRequest();

        request.setLocationId(Integer.parseInt(enrolmentData.getLocationId()));
        request.setTest(isTestSubmission);

        boolean isPTContract = false;
        boolean isPTP = false;
        boolean isFirstNDays;

//        handle FIRST_N_DAYS
        switch (contract.getMboId()) {
            case Constants.COUPON_CODE_7_DAYS_ID:
                isFirstNDays = true;
                break;
            case Constants.COUPON_CODE_14_DAYS_ID:
                isFirstNDays = true;
                break;
            case Constants.COUPON_CODE_21_DAYS_ID:
                isFirstNDays = true;
                break;
            case Constants.COUPON_CODE_30_DAYS_ID:
                isFirstNDays = true;
                break;
            default:
                isFirstNDays = false;
        }

//        handle PT Contracts
        String[] tmp = contract.getContractType().contains("pt") ? contract.getContractType().split("_") : null;

        if (tmp != null && tmp.length > 1) {

            logger.info("Found a PT Contract: {}",contract.getName());

            for (String type : tmp) {
                if (type.equals("pt")) {
                    logger.info("tmp = {}",type);
                    isPTContract = true;
                } else if (type.equals("ptPack")) {
                    isPTP = true;
                }
            }
        }

        logger.info("isPTContract = {} | isFirstNDays = {}",isPTContract, isFirstNDays);

        if (isPTContract) {
            request.setStartDate(Helpers.handleDate_YYYYMMDD(enrolmentData.getPersonalTrainingStartDate()).toString());
        } else if (isFirstNDays) {

//            if (contract.getMboId() == Constants.COUPON_CODE_21_DAYS_ID) {
//                request.setStartDate(Helpers.handleDate_YYYYMMDD(Constants.COUPON_CODE_21_DAYS_FIRST_BILLING_DATE).toString());
//            } else {
//                request.setStartDate(Helpers.handleDate_YYYYMMDD(enrolmentData.getStartDate()).toString());
//            }

            request.setStartDate(Helpers.handleDate_YYYYMMDD(enrolmentData.getStartDate()).toString());
            request.setPromotionCode(Constants.FIRST_N_DAYS_MBO_PROMO_CODE);
        } else if (isPTP) {
            request.setStartDate(Helpers.handleDate_YYYYMMDD(enrolmentData.getStartDate()).toString());
        } else {
            request.setStartDate(Helpers.handleDate_YYYYMMDD(enrolmentData.getFirstBillingDate()).toString());
        }

        request.setFirstPaymentOccurs("StartDate");
        request.setClientId(enrolmentData.getAccessKeyNumber());
        request.setContractId(contract.getMboId());
        request.setSendNotifications(false);
//        request.setClientSignature(handleSignature(enrolmentData.getPrimarySignatureURL()));

//        Access Key
        if (contract.getContractType().contains(Constants.ACCESS_KEY)
                && enrolmentData.getAccessKeyDiscount() != null
                && enrolmentData.getAccessKeyDiscount().length() > 0) {
                logger.info("isAccessKey and isDiscounted with {}", enrolmentData.getAccessKeyDiscount());
                request.setPromotionCode(enrolmentData.getAccessKeyDiscount());
        }

//        PTPack
        if ((enrolmentData.getFreePTPack().equals(Constants.COUPON_CODE_PTP) || enrolmentData.getFreePTPack().equalsIgnoreCase(Constants.ENROLMENT_DATA_COUPON_2_FREE_PT))
                && (contract.getMboId() == Constants.STARTERPACK_PTPACK_ID || contract.getMboId() == Constants.GW_STARTERPACK_PTPACK_ID  || contract.getMboId() == Constants.BUNKER_STARTERPACK_PTPACK_ID)) {
                logger.info("Sale Service Free PT Pack enrolmentData.getFreePTPack(): {} contract.getMboId(): {}",enrolmentData.getFreePTPack(), contract.getMboId());
                request.setPromotionCode(Constants.COUPON_CODE_PTP);
        }

//        UseDirectDebit or StoredCardInfo
        if (enrolmentData.getPaymentType().equals(Constants.SUBMISSION_PAYMENT_TYPE_CREDIT_CARD)) {
            StoredCardInfo cardInfo = new StoredCardInfo();
            cardInfo.setLastFour(Helpers.cleanCreditCard(enrolmentData.getMemberCreditCard().getNumber()));
            request.setStoredCardInfo(cardInfo);
        } else if (enrolmentData.getPaymentType().equals(Constants.SUBMISSION_PAYMENT_TYPE_BANK_ACCOUNT)) {
            request.setUseDirectDebit(true);
        } else {
            return null;
        }

//        SalesRepId
//        if (enrolmentData.getStaffMember() != null) {
//            String[] split = enrolmentData.getStaffMember().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
//            if (split.length > 0) {
//                Long salesRepId = Long.parseLong(split[0]);
//                request.setSalesRepMboId(salesRepId);
//            }
//        }

        return request;
    }

    @Override
    public MboPurchaseContractResponse purchaseMboContract(MboPurchaseContractRequest request, boolean isSydney) {

       logger.info("Inside purchaseMboContract constractId: {} - isSydney = {}",request.getContractId(), isSydney);

        MboPurchaseContractResponse response = null;
        HttpHeaders headers = setHeaders(isSydney, true);
        HttpEntity<MboPurchaseContractRequest> entity = new HttpEntity<>(request, headers);
        try {
            response = getRestTemplate().exchange(BASE_URL + "sale/purchasecontract", HttpMethod.POST, entity, MboPurchaseContractResponse.class).getBody();
        } catch (Exception ex) {
//            response = handleMboPurchaseContractResponseException(entity, ex.getMessage());
            logger.error("Error Purchasing Contract {}", ex.getMessage());
        }
        return response;

//        logger.info("TESTING ONLY -> Inside purchaseMboContract \n{} ",entity.toString());
//        return null;
    }

    private MboPurchaseContractResponse handleMboPurchaseContractResponseException(HttpEntity<MboPurchaseContractRequest> entity, String errorMessage) {
        if (errorMessage.contains("404") || errorMessage.contains("400")) {
            try {
                return getRestTemplate().exchange(BASE_URL + "sale/purchasecontract", HttpMethod.POST, entity, MboPurchaseContractResponse.class).getBody();
            } catch (Exception ex) {
                logger.error("Error Purchasing Contract attempt 2 {}", ex.getMessage());
            }
        }
        return null;
    }

    @Override
    public String getNameFromMboIdAndSiteId(String input, long siteId) {
        String site = Long.toString(siteId);
        if (Helpers.isNumeric(input)) {
            input = getStaffOrPersonalTrainerName(input, site);
        }
        return input;
    }

    @Override
    public String getNameFromMboIdAndLocationId(String input, String locationId) {
        if (Helpers.isNumeric(input)) {
            long siteId = getSiteIdByLocationId(locationId);
            return getNameFromMboIdAndSiteId(input, siteId);
        } else {
            return input;
        }
    }

    @Override
    public PersonName getNameFromUIEncoding(String input) {

        PersonName name = null;

        if (input == null) {
            return name;
        }

//        FIXME: THIS IS HORRIBLY INEFFICIENT
        try {
            String[] arr = input.split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
            if (arr.length == 2) {
                String n = getStaffOrPersonalTrainerName(arr[0], arr[1]);
                String[] nArr = n.split(" ");
                name = new PersonName(nArr[0], nArr[1]);
            }

        } catch (Exception e) {
            logger.error("Error getting name from UI input {} {}",input, e.getMessage());
            return name;
        }

        return name;
    }


    @Override
    public MboShoppingCartResponse checkoutShoppingCart(MboShoppingCartRequest mboShoppingCartRequest, boolean isSydney) {

        MboShoppingCartResponse response = null;
        HttpHeaders headers = setHeaders(isSydney, true);
        HttpEntity<MboShoppingCartRequest> entity = new HttpEntity<MboShoppingCartRequest>(mboShoppingCartRequest, headers);
        try {
            response = getRestTemplate().exchange(BASE_URL + "sale/checkoutshoppingcart", HttpMethod.POST, entity, MboShoppingCartResponse.class).getBody();

        } catch(Exception e) {
            logger.error("Error Checking Out Shipping Cart: {} | {}", mboShoppingCartRequest.getClientId(),e.getMessage());

        }
        return response;
    }


    @Override
    public MboShoppingCartRequest buildAccessKeyPOSCart(EnrolmentData enrolmentData, boolean isTestSubmission) {

        MboShoppingCartRequest request = new MboShoppingCartRequest();

        request.setClientId(enrolmentData.getAccessKeyNumber());
        request.setInStore(true);
        request.setSendEmail(false);
        request.setTest(isTestSubmission);
        request.setLocationId(Integer.parseInt(enrolmentData.getLocationId()));
        request.setPromotionCode(enrolmentData.getAccessKeyDiscount());


        MboMetaData itemMetaData = new MboMetaData();
        itemMetaData.setId(enrolmentData.getLocationId().equals("4") ? Constants.GW_ACCESS_KEY_PRODUCT_CODE : Constants.ACCESS_KEY_PRODUCT_CODE);

        MboRequestItem requestItem = new MboRequestItem();
        requestItem.setType(Constants.MBO_METADATA_TYPE_PRODUCT);
        requestItem.setMetadata(itemMetaData);

        MboRequestItems items = new MboRequestItems();
        items.setMboRequestItem(requestItem);
        items.setQuantity(1);

        ArrayList<MboRequestItems> mboRequestItemsArrayList = new ArrayList<>();
        mboRequestItemsArrayList.add(items);

        request.setItems(mboRequestItemsArrayList);

        double amount = Double.parseDouble(ACCESS_KEY_PRICE);

        if (enrolmentData.getAccessKeyDiscount() != null) {
            amount -= getAccessKeyDiscountAmount(enrolmentData.getAccessKeyDiscount());
        }

        MboMetaData paymentMetaData = new MboMetaData();
        paymentMetaData.setAmount(Double.toString(amount));
        paymentMetaData.setNotes("Access Key Paid at Point of Sale");

        MboRequestItem paymentItem = new MboRequestItem();
        paymentItem.setType(Constants.MBO_METADATA_TYPE_CASH);
        paymentItem.setMetadata(paymentMetaData);

        ArrayList<MboRequestItem> paymentItems = new ArrayList<>();
        paymentItems.add(paymentItem);
        request.setPayments(paymentItems);

//        logger.info("buildAccessKeyPOSCart request\n{}",request.toString());

        return request;
    }


    @Override
    public boolean uploadDocument(MboUploadDocumentRequest request, boolean isSydney) {
        boolean isSuccess = true;
        HttpHeaders headers = setHeaders(isSydney, true);
        HttpEntity<MboUploadDocumentRequest> entity = new HttpEntity<MboUploadDocumentRequest>(request, headers);
        try {
            getRestTemplate().exchange(BASE_URL + "client/uploadclientdocument", HttpMethod.POST, entity, String.class).getBody();

        } catch(Exception e) {
            isSuccess = false;
            logger.error("Error uploading document: {} | {}", request.getClientId(),e.getMessage());
        }
        return isSuccess;
    }


    @Override
    public boolean processPdf(MemberTermsAndConditions termsAndConditions) {
        logger.info("Inside processPdf");

        File file = new File();
        file.setFileName(termsAndConditions.getFileName());
        file.setMediaType("application/pdf");
        file.setBuffer(termsAndConditions.getPdfDocument());
        MboUploadDocumentRequest request = new MboUploadDocumentRequest();
        request.setClientId(termsAndConditions.getEnrolmentData().getAccessKeyNumber());
        request.setFile(file);

        return uploadDocument(request, !termsAndConditions.getEnrolmentData().getLocationId().equals("4"));
    }


    @Override
    public HashMap<String, String> getAllStaffMap() {
        return getStaffMap();
    }


    @Override
    public void setStaffMap() {
        String key;
        String value;
        Iterable<Staff> staffs = getStaffDao().getAllStaff();
        HashMap<String, String> temp = new HashMap<>();

        for (Staff s : staffs) {
            key = s.getMboId() + Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER + s.getSiteId();
            value = s.getName();
            temp.put(key, value);
        }

        setStaffMap(temp);
    }

    @Override
    public String getStaffOrPersonalTrainerName(String mboId, String siteId) {
        String key = mboId + Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER + siteId;
        return getStaffName(key);
    }

    @Override
    public String getStaffName(String key) {
        String name = null;
        try {
            name = getStaffMap().get(key);
        } catch (Exception e) {
            logger.error("Error getting Staff Or Personal Trainer Name");
        }
        return name;
    }


    @Override
    public boolean isSydney(String locId) {

        if (locId.equals("4")) {
            return false;
        }

        if (locId.equals(Constants.DARWIN_SITE_ID)) {
            return false;
        }

        return true;
    }

    @Override
    public long getSiteIdByLocationId(String locationId) {
        return isSydney(locationId) ? Constants.SYDNEY_SITE_ID : Constants.DARWIN_SITE_ID;
    }

//    Moved to Helpers
    @Override
    public double getAccessKeyDiscountAmount(String discountCode) {

        double amount = 0;

        switch (discountCode) {
            case Constants.COUPON_CODE_KEY_0:
                amount += 99;
                break;
            case Constants.COUPON_CODE_KEY_21:
                amount += 78;
                break;
            case Constants.COUPON_CODE_KEY_30:
                amount += 69;
                break;
            case Constants.COUPON_CODE_KEY_49:
                amount += 50;
                break;
            case Constants.COUPON_CODE_KEY_79:
                amount += 20;
                break;
            case Constants.COUPON_CODE_KEY_89:
                amount += 10;
                break;
        }

        return amount;
    }


    private HttpHeaders setHeaders(boolean isSydney, boolean withAccessToken) {

        String siteId = isSydney ? SITE_ID : GW_SITE_ID;
        String username = isSydney ? USERNAME : GW_USERNAME;
        String password = isSydney ? PASSWORD : GW_PASSWORD;
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("SiteId", siteId);
        headers.set("Api-Key", API_KEY);

        if (withAccessToken) {
            if (isSydney) {
                if( getAccessToken() == null) {
                    getUserToken(username, password, isSydney);
                }
                headers.set("authorization", getAccessToken());
            }

            if (!isSydney) {
                if (getGw_accessToken() == null) {
                    getUserToken(username, password, isSydney);
                }
                headers.set("authorization", getGw_accessToken());
            }
        }
//        logger.info("Headers {}", headers.toString());
        return headers;
    }


    private ArrayList<MboContract> processContracts(ArrayList<Contract> contracts, String locId) {

        ArrayList<MboContract> mboContracts = new ArrayList<>();

        for (Contract c : contracts) {

//            logger.info("Contract Name: {} | MboId: {}", c.getName(), c.getMboId());

            MboContract mboContract = getSaleDao().getContractByMboIdAndLocation(c.getMboId(), locId);

            if (mboContract == null) {
                mboContract = MboContract.save(c);
            } else {
                mboContract = MboContract.update(c, mboContract);
            }
            try {
                mboContracts.add(getSaleDao().saveContract(mboContract));
            } catch (Exception ex) {
                logger.error("Error adding Contract [{}] to the list | {}",mboContract.getName(), ex.getMessage());
            }
        }
//        logger.info("processContracts Contract size {}", mboContracts.size());
        return mboContracts;
    }

    private ArrayList<MboService> processServices(ArrayList<com.fitnessplayground.dao.domain.mboDto.Service> services, String locId) {

        ArrayList<MboService> mboServices = new ArrayList<>();

        for (com.fitnessplayground.dao.domain.mboDto.Service s : services) {
            MboService mboService = getSaleDao().getServiceByMboIdAndLocation(Integer.parseInt(s.getMboId()), locId);

            if (mboService == null) {
                mboService = MboService.save(s, locId);
            } else {
                mboService = MboService.update(s, mboService, locId);
            }
            try {
                mboServices.add(getSaleDao().saveService(mboService));
            } catch (Exception ex) {
                logger.error("Error adding Service [{}] to list | {}",mboService.getName(), ex.getMessage());
            }
        }
//        logger.info("processServices Service size {}", mboServices.size());
        return mboServices;
    }


    private ArrayList<MboProduct> processProducts(ArrayList<Product> products, String locId) {

        ArrayList<MboProduct> mboProducts = new ArrayList<>();

        for (Product p : products) {
            MboProduct mboProduct = getSaleDao().getProductByMboIdAndLocation(p.getMboId(), locId);

            if (mboProduct == null) {
                mboProduct = MboProduct.save(p, locId);
            } else {
                mboProduct = MboProduct.update(p, mboProduct, locId);
            }
            try {
                mboProducts.add(getSaleDao().saveProduct(mboProduct));
            } catch (Exception ex) {
                logger.error("Error adding Product [{}] to list | {}",mboProduct.getName(), ex.getMessage());
            }
        }
        logger.info("processProducts Product size {}", mboProducts.size());
        return mboProducts;
    }


    private ArrayList<MboClient> processClients(ArrayList<Client> clients, String locId) {
        GymName gym = GymName.getGymName(Integer.valueOf(locId));
        ArrayList<MboClient> mboClients = new ArrayList<>();


        for (Client c : clients) {
            MboClient mboClient = getClientDao().findByUniqueIdAndSiteId(Long.parseLong(c.getUniqueId()), gym.getSiteId());

            if (mboClient == null) {
//                logger.info("No Client Found, creating new Client");
                mboClient = MboClient.save(c, locId);

            } else {
//                logger.info("Found Client {}", mboClient.getUniqueId());
                mboClient = MboClient.update(c, mboClient, locId);
            }

            try {
                mboClients.add(getClientDao().saveClient(mboClient));
            } catch (Exception ex) {
                logger.error("Error adding Client {} to list | {}",mboClient.getUniqueId(), ex.getMessage());
            }
        }
//        logger.info("processClients Clients size {} | index number {}", mboClients.size());
        return mboClients;
    }


    private ArrayList<ClientContract> getClientContracts(MboClient mboClient) {

        ArrayList<ClientContract> contracts = new ArrayList<>();

        boolean isSuccess;
        resetRequestVariables();
        MboGetClientContractsResponse response = null;
        HttpHeaders headers = setHeaders(isSydney(String.valueOf(mboClient.getSiteId())), true);
        HttpEntity<Client> entity = new HttpEntity<>(headers);

        do {

            isSuccess = true;
//                    logger.info("Client {} {} {} {}", mboClient.getFirstName(), mboClient.getAccessKeyNumber(), mboClient.getUniqueId(), mboClient.getLocId());
            try {
                response = getRestTemplate().exchange(BASE_URL + "client/clientcontracts?clientId=" +mboClient.getAccessKeyNumber() + "&limit=" + getRequestLimit() + "&offset=" + getRequestOffset(), HttpMethod.GET, entity, MboGetClientContractsResponse.class).getBody();
            } catch (Exception ex) {
                setRetryCount(getRetryCount() + 1);
                logger.error("Error getting client contracts {} attempt {} of {}", ex.getMessage(), getRetryCount(), RETRY_BREAK);
                handleMboApiException(ex.getMessage());
                isSuccess = false;
                setValid(getRetryCount() <= RETRY_BREAK);
            }
            if (isSuccess) {
                setTotalResults(response.getPaginationResponse().getTotalResults());
                setCount(getCount() + response.getPaginationResponse().getPageSize());
                setRequestOffset(getCount());
                contracts.addAll(response.getClientContracts());
                logger.info("getClientContracts Count [{}] | Offset [{}] | Total Results [{}] | response.getClientContracts().size() [{}]", getCount(), getRequestOffset(), getTotalResults(), response.getClientContracts().size());
                setValid(getCount() < getTotalResults());
            }
        } while (isValid());

        return contracts;
    }

    private void processClientContracts(ArrayList<ClientContract> clientContracts, MboClient mboClient) {
        ArrayList<MboClientContract> mboClientContracts = new ArrayList<>();

        for (ClientContract cc : clientContracts) {
            MboClientContract mboClientContract = clientDao.findClientContractByContractId(cc.getClientContractId(), mboClient.getSiteId());

            if (mboClientContract == null) {
                mboClientContract = MboClientContract.save(cc, mboClient);
            } else {
                mboClientContract = MboClientContract.update(cc, mboClientContract, mboClient);
            }

            getClientDao().saveClientContract(mboClientContract);
            mboClient.setHomeLocationID(cc.getOriginationLocationId());
            mboClientContracts.add(mboClientContract);
            if (mboClientContract.getAutopayStatus().equals("Active") && (mboClientContract.getContractName().contains("Play") || mboClientContract.getContractName().contains("Fit") || mboClientContract.getContractName().contains("Gym"))) {
                mboClient.setMembershipName(mboClientContract.getContractName());
            }
        }
        mboClient.setMboClientContractList(mboClientContracts);
        getClientDao().saveClient(mboClient);
        logger.info("processClientContracts ClientContracts size {}", clientContracts.size());
    }


    private void resetRequestVariables() {
        setRequestOffset(0);
        setCount(0);
        setTotalResults(1);
        setRetryCount(0);
        setValid(true);
    }

    private void refreshToken() {

        boolean isSydney;
        for (int i = 0; i < 2; i++) {
            isSydney = i == 0;
            String username = isSydney ? USERNAME : GW_USERNAME;
            String password = isSydney ? PASSWORD : GW_PASSWORD;
            getUserToken(username, password, isSydney);
        }
    }

    private SalesRep setSalesRepName(String name, SalesRep salesRep) {
        if (name != null) {
            String[] staffName = name.split(" ");
            if (staffName.length == 2) {
                salesRep.setFirstName(staffName[0]);
                salesRep.setLastName(staffName[1]);
            }
        }
        return salesRep;
    }

    private void handleMboApiException(String errorMessage) {
        if (errorMessage!= null && (errorMessage.contains("403") || errorMessage.contains("401"))) {
            refreshToken();
        }
    }

    private String getSiteId(boolean isSydney) {
        return isSydney ? SITE_ID : GW_SITE_ID;
    }

    private String[] getSiteIds() {
        String[] siteIds = {SITE_ID, GW_SITE_ID};
        return siteIds;
    }

    private void putIntoAllStaffMap(long siteId, long mboId, String name) {

        if (siteId == Constants.SYDNEY_SITE_ID && !allStaffMembersMap.containsKey(mboId)) {
            getAllStaffMembersMap().put(mboId, name);
        }

        if (siteId == Constants.DARWIN_SITE_ID && !allStaffMembersMap_Darwin.containsKey(mboId)) {
            getAllStaffMembersMap_Darwin().put(mboId, name);
        }
    }


    private boolean isTestInvocation() {
        return Boolean.parseBoolean(IS_TEST_INVOCATION);
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getGw_accessToken() {
        return gw_accessToken;
    }

    public void setGw_accessToken(String gw_accessToken) {
        this.gw_accessToken = gw_accessToken;
    }

    public String[] getLocIds() {
        return locIds;
    }

    public void setLocIds(String[] locIds) {
        this.locIds = locIds;
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

    public HashMap<String, String> getStaffMap() {
        return staffMap;
    }

    public void setStaffMap(HashMap<String, String> staffMap) {
        this.staffMap = staffMap;
    }

    public void setAllStaffMembersMap(HashMap<Long, String> allStaffMembersMap) {
        this.allStaffMembersMap = allStaffMembersMap;
    }

    public HashMap<Long, String> getAllStaffMembersMap_Darwin() {
        return allStaffMembersMap_Darwin;
    }

    public void setAllStaffMembersMap_Darwin(HashMap<Long, String> allStaffMembersMap_Darwin) {
        this.allStaffMembersMap_Darwin = allStaffMembersMap_Darwin;
    }

    @Override
    public HashMap<Long, String> getAllStaffMembersMap() {
        return allStaffMembersMap;
    }

    public EmailService getEmailService() {
        return emailService;
    }

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    public SaleDao getSaleDao() {
        return saleDao;
    }

    @Autowired
    public void setSaleDao(SaleDao saleDao) {
        this.saleDao = saleDao;
    }

    public ClientDao getClientDao() {
        return clientDao;
    }

    @Autowired
    public void setClientDao(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public StaffDao getStaffDao() {
        return staffDao;
    }

    @Autowired
    public void setStaffDao(StaffDao staffDao) {
        this.staffDao = staffDao;
    }

    public MemberDao getMemberDao() {
        return memberDao;
    }

    @Autowired
    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
