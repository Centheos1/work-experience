package com.fitnessplayground.service.impl;

import com.fitnessplayground.config.MindBodySaleConfig;
import com.fitnessplayground.dao.domain.EnrolmentData;
import com.fitnessplayground.dao.domain.MboContract;
import com.fitnessplayground.model.mindbody.api.result.MboApiResultEnrolmentData;
import com.fitnessplayground.model.mindbody.api.result.MindBodyApiResult;
import com.fitnessplayground.model.mindbody.api.sale.*;
import com.fitnessplayground.model.mindbody.api.util.MindBodySaleServiceUtil;
import com.fitnessplayground.service.MindBodySaleService;
import com.fitnessplayground.service.MindBodyServiceGateway;
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

import static com.fitnessplayground.model.mindbody.api.util.MindBodyServiceUtil.*;

/**
 * Created by micheal on 18/02/2017.
 */
@Service
public class MindBodySaleServiceImpl implements MindBodySaleService {

    @Value("${mindbody.service.api.sale.checkout.url}")
    private String MINDBODY_SALE_CHECKOUT_SERVICE_URL;

    @Value("${mindbody.service.api.sale.getcontracts}")
    private String MINDBODY_SALE_GET_CONTRACT_URL;

    @Value("${mindbody.service.api.sale.purchasecontract}")
    private String MINDBODY_SALE_PURCHASE_CONTRACT_URL;


    @Override
    public List<Contract> getContracts() {
        String[] siteList = {getMindbodySiteIdDarwin(), getMindbodySiteId()};

        List<Contract> contracts = new ArrayList<>();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MindBodySaleConfig.class);
        MindBodyServiceGateway gateway = context.getBean(MindBodyServiceGateway.class);

        for (String id : siteList) {

            GetContracts getContracts = MindBodySaleServiceUtil.buildGetContracts(id);

            GetContractsResponse response = (GetContractsResponse) gateway.getWebServiceTemplate().marshalSendAndReceive(getContracts, new WebServiceMessageCallback() {
                @Override
                public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
                    ((SoapMessage) message).setSoapAction(MINDBODY_SALE_GET_CONTRACT_URL);
                }
            });

            contracts = handleResponseContracts(response, contracts);

            logger.info("Added {} Contracts for SiteId {}",contracts.size(), id);
        }

        logger.info("Total of {} Contracts",contracts.size());
        return contracts;
    }

    private List<Contract> handleResponseContracts(GetContractsResponse response, List<Contract> contracts) {

//        List<Contract> contracts = new ArrayList<>();
        if (response != null
                && response.getGetContractsResult() != null
                && response.getGetContractsResult().getContracts() != null
                && response.getGetContractsResult().getContracts().getContract() != null) {


            List<Contract> contractsFound = response.getGetContractsResult().getContracts().getContract();

            logger.info("Contracts found in handleResponseContracts: {}", contractsFound.size());

            for (Contract c : contractsFound) {
                contracts.add(c);
            }

            logger.info("Contracts Size in handleResponseContracts: {}", contracts.size());
        }
        return contracts;
    }


    @Override
    public MboApiResultEnrolmentData purchaseContract(MboContract contract, EnrolmentData enrolmentData) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MindBodySaleConfig.class);
        MindBodyServiceGateway gateway = context.getBean(MindBodyServiceGateway.class);

        PurchaseContracts purchaseContracts = MindBodySaleServiceUtil.buildPurchaseContracts(contract, enrolmentData);

        PurchaseContractsResponse response = (PurchaseContractsResponse) gateway.getWebServiceTemplate().marshalSendAndReceive(purchaseContracts, new WebServiceMessageCallback() {
            @Override
            public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
                ((SoapMessage) message).setSoapAction(MINDBODY_SALE_PURCHASE_CONTRACT_URL);
            }
        });

        MboApiResultEnrolmentData result = handleResponse(response, enrolmentData);

//        logger.info("Purchase Contract Result = {}",result.toString());

        return result;
    }

    private MboApiResultEnrolmentData handleResponse(PurchaseContractsResponse response, EnrolmentData enrolmentData) {
        MboApiResultEnrolmentData result;
        if(response.getPurchaseContractsResult().getStatus().equals(StatusCode.SUCCESS)) {
            result = new MboApiResultEnrolmentData(true, response.getPurchaseContractsResult().getClientID(), enrolmentData, "", response.getPurchaseContractsResult().getErrorCode());
        } else {
            result = new MboApiResultEnrolmentData(false, enrolmentData.getAccessKeyNumber(), enrolmentData, response.getPurchaseContractsResult().getMessage(), response.getPurchaseContractsResult().getErrorCode() );
        }
        return result;
    }


    @Override
    public MboApiResultEnrolmentData purchaseTempMembership(String serviceId, EnrolmentData enrolmentData) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MindBodySaleConfig.class);
        MindBodyServiceGateway gateway = context.getBean(MindBodyServiceGateway.class);
        CheckoutShoppingCart cart = MindBodySaleServiceUtil.buildCheckoutShoppingCart(serviceId, enrolmentData);
        //  Invoke service
        CheckoutShoppingCartResponse response = (CheckoutShoppingCartResponse) gateway.getWebServiceTemplate().
                marshalSendAndReceive(cart,
                        new WebServiceMessageCallback() {
                            public void doWithMessage(WebServiceMessage message) {
                                ((SoapMessage) message).setSoapAction(MINDBODY_SALE_CHECKOUT_SERVICE_URL);
                            }
                        });

        MboApiResultEnrolmentData result = handleResponse(response, enrolmentData);
        return result;
    }

    private MboApiResultEnrolmentData handleResponse(CheckoutShoppingCartResponse response, EnrolmentData enrolmentData) {
        MboApiResultEnrolmentData result;
        if(response.getCheckoutShoppingCartResult().getStatus().equals(StatusCode.SUCCESS)) {
            result = new MboApiResultEnrolmentData(true, enrolmentData.getAccessKeyNumber(), enrolmentData, "", response.getCheckoutShoppingCartResult().getErrorCode());
        } else {
            result = new MboApiResultEnrolmentData(false, enrolmentData.getAccessKeyNumber(), enrolmentData, response.getCheckoutShoppingCartResult().getMessage(),response.getCheckoutShoppingCartResult().getErrorCode());
        }
        return result;
    }

}


//
//
//    public List<Staff> handleResponse(GetStaffResponse response) {
//        List<Staff>  staffFound = new ArrayList<>();
//        if(null != response && null != response.getGetStaffResult() && null != response.getGetStaffResult().getStaffMembers()
//                && null != response.getGetStaffResult().getStaffMembers().getStaff()) {
//            staffFound = response.getGetStaffResult().getStaffMembers().getStaff();
//        }
//        return staffFound;
//    }
