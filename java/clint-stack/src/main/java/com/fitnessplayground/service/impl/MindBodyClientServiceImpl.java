package com.fitnessplayground.service.impl;

import com.fitnessplayground.config.MindBodyClientConfig;
import com.fitnessplayground.dao.ClientDao;
import com.fitnessplayground.dao.domain.EnrolmentData;
import com.fitnessplayground.dao.domain.MboClient;
import com.fitnessplayground.model.mindbody.api.client.*;
import com.fitnessplayground.model.mindbody.api.result.MboApiResultEnrolmentData;
import com.fitnessplayground.model.mindbody.api.util.MindBodyClientServiceUtil;
import com.fitnessplayground.service.MindBodyClientService;
import com.fitnessplayground.service.MindBodyServiceGateway;
import com.fitnessplayground.util.GymName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.soap.SoapMessage;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.fitnessplayground.model.mindbody.api.util.MindBodyServiceUtil.getMindbodySiteId;
import static com.fitnessplayground.model.mindbody.api.util.MindBodyServiceUtil.getMindbodySiteIdDarwin;

/**
 * Created by micheal on 1/02/2017.
 */
@Service
public class MindBodyClientServiceImpl implements MindBodyClientService {

    private static final Logger logger = LoggerFactory.getLogger(MindBodyClientServiceImpl.class);

    @Value("${mindbody.service.api.client.getclients.url}")
    private String MINDBODY_GET_CLIENT_SERVICE_URL;

    @Value("${mindbody.service.api.client.updateclients.url}")
    private String MINDBODY_UPDATE_CLIENT_SERVICE_URL;

    private final ClientDao clientDao;

    @Autowired
    public MindBodyClientServiceImpl(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public List<Client> getAllClients() {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MindBodyClientConfig.class);
        MindBodyServiceGateway gateway = context.getBean(MindBodyServiceGateway.class);

        int pageNumber = 0;
        List<Client> resultList = new ArrayList<>();
        int responseCount = 0;
        String status = "";
        String[] siteList = {getMindbodySiteIdDarwin(), getMindbodySiteId()};

        for (String siteId : siteList) {

            try {

                do {

                    GetClientsRequest request = MindBodyClientServiceUtil.buildGetAllClientsRequest(pageNumber, siteId);
                    GetClients getClients = new GetClients();
                    getClients.setRequest(request);

                    GetClientsResponse response = (GetClientsResponse) gateway.getWebServiceTemplate()
                            .marshalSendAndReceive(getClients, new WebServiceMessageCallback() {
                                @Override
                                public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
                                    ((SoapMessage) message).setSoapAction(MINDBODY_GET_CLIENT_SERVICE_URL);
                                }
                            });

                    responseCount = response.getGetClientsResult().getResultCount();

                    status = response.getGetClientsResult().getStatus().value();

                    resultList = handleResponse(response, resultList);

                    processClients(resultList, siteId);

                    pageNumber++;

                } while (responseCount > 0 && status.equals("Success"));

            } catch (Exception ex) {
                logger.error("Error trying to getClients on page [{}] --> {}",pageNumber, ex.getMessage());
                return null;
            }
        }

        return resultList;
    }


    private List<Client> handleResponse(GetClientsResponse response, List<Client> clientList) {

        if(null != response
                && null != response.getGetClientsResult()
                && null != response.getGetClientsResult().getClients()
                && null != response.getGetClientsResult().getClients().getClient()) {

            List<Client> clientsFound = response.getGetClientsResult().getClients().getClient();

            for (Client client : clientsFound) {
                clientList.add(client);
            }
        }
        return clientList;

    }

    private List<MboClient> processClients(List<Client> clients, String siteId) {

//        GymName gym = GymName.getGymName(Integer.valueOf(locId));
        ArrayList<MboClient> mboClients = new ArrayList<>();
        for (Client c : clients) {
            MboClient mboClient = clientDao.findByUniqueIdAndSiteId(Long.parseLong(c.getID()), Long.parseLong(siteId));

            if (mboClient == null) {
//                logger.info("No Client Found, creating new Client");
                mboClient = MboClient.save(c, siteId);
            } else if (siteId.equals(getMindbodySiteIdDarwin()) && mboClient.getHomeLocationID() != 4) {
//                logger.info("Client Found with WRONG locId, creating new Client");
                mboClient = MboClient.save(c, siteId);
            } else {
//                logger.info("Found Client {}", mboClient.getUniqueId());
                mboClient = MboClient.update(c, mboClient, siteId);
            }
            try {
                mboClients.add(clientDao.saveClient(mboClient));
            } catch (Exception ex) {
                logger.error("Error adding Client {} to list | {}",mboClient.getUniqueId(), ex.getMessage());
            }
        }
//        logger.info("processClients Clients size {} | index number {}", mboClients.size());
        return mboClients;

    }

    @Override
    public MboApiResultEnrolmentData addNewClient(EnrolmentData enrolmentData, boolean isDuplicateKey, boolean isDuplicateUsername, boolean isTestSubmission) {

        logger.info("Inside addNewClient");

        MboApiResultEnrolmentData result;

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MindBodyClientConfig.class);
        MindBodyServiceGateway gateway = context.getBean(MindBodyServiceGateway.class);

        AddOrUpdateClients request = MindBodyClientServiceUtil.buildAddNewClientRequest(enrolmentData, isDuplicateKey, isDuplicateUsername, isTestSubmission, false);

        AddOrUpdateClientsResponse response = sendToMindBody(request);
        result = handleResponse(response, enrolmentData);

        // If failed due to rep issue then retry without the reps set
        if(!result.isSuccess() && response.getAddOrUpdateClientsResult().getMessage().contains("can't be associated with rep ID")) {
            logger.warn("Error adding Member [{}] with Rep information. Retrying without Rep details.", enrolmentData.getAccessKeyNumber());
            request = MindBodyClientServiceUtil.buildAddNewClientRequest(enrolmentData, isDuplicateKey, isDuplicateUsername, isTestSubmission, true);
            response = sendToMindBody(request);
            result = handleResponse(response, enrolmentData);
        }

        return result;
    }

    private MboApiResultEnrolmentData handleResponse(AddOrUpdateClientsResponse response, EnrolmentData enrolmentData) {
        MboApiResultEnrolmentData result;
        if(response.getAddOrUpdateClientsResult().getStatus().equals(StatusCode.SUCCESS)) {
            result = new MboApiResultEnrolmentData(true,
                    response.getAddOrUpdateClientsResult().getClients().getClient().get(0).getID(),
                    enrolmentData, response.getAddOrUpdateClientsResult().getMessage(), response.getAddOrUpdateClientsResult().getErrorCode());
        }
        else {
            try {
                if(response.getAddOrUpdateClientsResult().getStatus() == StatusCode.INTERNAL_EXCEPTION || response.getAddOrUpdateClientsResult().getStatus() == StatusCode.INVALID_PARAMETERS) {
                    result = new MboApiResultEnrolmentData(false, "",
                            enrolmentData,
                            response.getAddOrUpdateClientsResult().getMessage(), response.getAddOrUpdateClientsResult().getErrorCode());
                } else {
                    if (response.getAddOrUpdateClientsResult().getClients() != null) {
                        result = new MboApiResultEnrolmentData(false, "", enrolmentData,
                                response.getAddOrUpdateClientsResult().getClients().getClient().get(0).getMessages().getString().get(0), response.getAddOrUpdateClientsResult().getErrorCode());
                    } else {
                        result = new MboApiResultEnrolmentData(false,"", enrolmentData, "Unexpected Error encountered invoking MindBody API", response.getAddOrUpdateClientsResult().getErrorCode());
                    }
                }
            } catch (Exception e) {
                logger.error("Unexpected error adding client to mindbody: [{}]", e);
                result = new MboApiResultEnrolmentData(false, "", enrolmentData, "Unexpected error adding client to MindBody "+e.getMessage(), -1);
            }
        }
        return result;
    }



    private AddOrUpdateClientsResponse sendToMindBody(AddOrUpdateClients clients) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MindBodyClientConfig.class);
        MindBodyServiceGateway gateway = context.getBean(MindBodyServiceGateway.class);

        AddOrUpdateClientsResponse resp = (AddOrUpdateClientsResponse) gateway.getWebServiceTemplate().
                marshalSendAndReceive(clients,
                        new WebServiceMessageCallback() {
                            public void doWithMessage(WebServiceMessage message) {
                                ((SoapMessage) message).setSoapAction(MINDBODY_UPDATE_CLIENT_SERVICE_URL);
                            }
                        });
        return resp;
    }


}
