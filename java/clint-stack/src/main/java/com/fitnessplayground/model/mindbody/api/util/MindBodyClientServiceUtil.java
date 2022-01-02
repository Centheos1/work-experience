package com.fitnessplayground.model.mindbody.api.util;

import com.fitnessplayground.dao.domain.EnrolmentData;
import com.fitnessplayground.dao.domain.MemberCreditCard;
import com.fitnessplayground.model.mindbody.api.client.*;
import com.fitnessplayground.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by micheal on 6/02/2017.
 */

public class MindBodyClientServiceUtil extends MindBodyServiceUtil {


    private static final Logger logger = LoggerFactory.getLogger(MindBodyClientServiceUtil.class);

    private static final ObjectFactory objectFactory = new ObjectFactory();


    public static SourceCredentials buildSourceCredentials(String siteId) {
        // Source credentials
        SourceCredentials sc = new SourceCredentials();
        sc.setSourceName(getMindbodySourceCredentialsUsername());
        sc.setPassword(getMindbodySourceCredentialsPassword());
        ArrayOfInt siteIds = objectFactory.createArrayOfInt();
        siteIds.getInt().add(new Integer(siteId));
        sc.setSiteIDs(siteIds);
        return sc;
    }

    public static UserCredentials buildUserCredentials(String siteId) {
        // User
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUsername(getMindbodyUserCredentialsUsername());
        userCredentials.setPassword(getMindbodyUserCredentialsPassword());
        ArrayOfInt siteIds = objectFactory.createArrayOfInt();
        siteIds.getInt().add(new Integer(siteId));
        userCredentials.setSiteIDs(siteIds);
        return userCredentials;
    }

    /**
     *
     * @return
     */
    public static SourceCredentials buildSourceCredentials() {
        // Source credentials
        SourceCredentials sc = new SourceCredentials();
        sc.setSourceName(getMindbodySourceCredentialsUsername());
        sc.setPassword(getMindbodySourceCredentialsPassword());
        ArrayOfInt siteIds = objectFactory.createArrayOfInt();
        siteIds.getInt().add(new Integer(getMindbodySiteId()));
        siteIds.getInt().add(new Integer(getMindbodySiteIdDarwin()));
        sc.setSiteIDs(siteIds);
        return sc;
    }

    /**
     * When performing an admin task on behalf of a client, like purchasing a membership
     * we need to supply the user credentials that we are performing the task on behalf of
     * @return
     */
    public static UserCredentials buildUserCredentials() {
        // User
        UserCredentials userCredentials = new UserCredentials();
//        TODO -> For production
        userCredentials.setUsername("_"+ getMindbodySourceCredentialsUsername());
        userCredentials.setPassword(getMindbodySourceCredentialsPassword());
//        TODO -> For Testing
//        userCredentials.setUsername("Siteowner");
//        userCredentials.setPassword("apitest1234");

        ArrayOfInt siteIds = objectFactory.createArrayOfInt();
        siteIds.getInt().add(new Integer(getMindbodySiteId()));
        siteIds.getInt().add(new Integer(getMindbodySiteIdDarwin()));
        userCredentials.setSiteIDs(siteIds);
        return userCredentials;
    }

    public static ClientCreditCard buildClientCreditCard(MemberCreditCard memberCreditCard) {
        ClientCreditCard clientCreditCard = new ClientCreditCard();
        clientCreditCard.setCardNumber(memberCreditCard.getNumber());
        clientCreditCard.setCardHolder(memberCreditCard.getHolder());
        clientCreditCard.setCity(memberCreditCard.getCity());
        clientCreditCard.setAddress(memberCreditCard.getAddress());
        clientCreditCard.setState(memberCreditCard.getState());
        clientCreditCard.setPostalCode(memberCreditCard.getPostcode());
        clientCreditCard.setExpMonth(memberCreditCard.getExpMonth());
        clientCreditCard.setExpYear(memberCreditCard.getExpYear());
        clientCreditCard.setCardType(memberCreditCard.getType());
        clientCreditCard.setLastFour(memberCreditCard.getVerificationCode());

        if(null != clientCreditCard.getAddress())
            clientCreditCard.setAddress(clientCreditCard.getAddress().trim());

        return clientCreditCard;
    }


    public static String getNextYear() {
        Date input = new Date();
        LocalDate localDate = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        // FIXME: need to fix this
        localDate = localDate.plusYears(2);
        return ""+localDate.getYear();
    }



    public static GetClientsRequest buildGetClientsRequestUsingId(SourceCredentials sourceCredentials, UserCredentials userCredentials, String clientId) {
        ObjectFactory factory = new ObjectFactory();
        ArrayOfString clientIds = factory.createArrayOfString();
        clientIds.getString().add(clientId);

        GetClientsRequest clientsRequest = new GetClientsRequest();
        clientsRequest.setSourceCredentials(sourceCredentials);
        // @FIXME: if we always include this, does it break the get clients api call ?
        if(null != userCredentials) {
            clientsRequest.setUserCredentials(userCredentials);
        }
        clientsRequest.setXMLDetail(XMLDetailLevel.FULL);
        clientsRequest.setClientIDs(clientIds);
        return clientsRequest;
    }

    public static GetClientsRequest buildGetClientsRequestUsingUsername(SourceCredentials sourceCredentials, UserCredentials userCredentials, String username) {
        GetClientsRequest clientsRequest = new GetClientsRequest();
        clientsRequest.setSourceCredentials(sourceCredentials);
        // @FIXME: if we always include this, does it break the get clients api call ?
        if(null != userCredentials) {
            clientsRequest.setUserCredentials(userCredentials);
        }
        clientsRequest.setXMLDetail(XMLDetailLevel.FULL);
        clientsRequest.setSearchText(username);

        return clientsRequest;
    }

    public static GetClientsRequest buildGetAllClientsRequest(int pageNumber, String siteId) {
        GetClientsRequest clientsRequest = new GetClientsRequest();
        clientsRequest.setSourceCredentials(buildSourceCredentials(siteId));
        clientsRequest.setUserCredentials(buildUserCredentials(siteId));
        clientsRequest.setXMLDetail(XMLDetailLevel.FULL);
        clientsRequest.setCurrentPageIndex(pageNumber);
        clientsRequest.setPageSize(1000);
        clientsRequest.setSearchText("");

        logger.info("buildGetAllClientsRequest pageIndex: {}",clientsRequest.getCurrentPageIndex());

        return clientsRequest;
    }

    public static AddOrUpdateClients buildAddNewClientRequest(EnrolmentData enrolmentData, boolean isDuplicateKey, boolean isDuplicateUsername, boolean isTestSubmission, boolean skipRep) {

        String siteId = getMindbodySiteId();

        if (enrolmentData.getLocationId().equals("4")) {
            siteId = getMindbodySiteIdDarwin();
        }

        AddOrUpdateClientsRequest request = new AddOrUpdateClientsRequest();
        request.setSourceCredentials(buildSourceCredentials(siteId));
        request.setUserCredentials(buildUserCredentials(siteId));
        request.setXMLDetail(XMLDetailLevel.FULL);
        request.setPageSize(1);
        request.setCurrentPageIndex(1);
        request.setTest(isTestInvocation());

        Client client = new Client();

//        FIXME - I'm not sure if this protects the over write
        if (!isDuplicateKey || isTestSubmission) {
            client.setID(enrolmentData.getAccessKeyNumber());
            logger.info("Adding New Client with id: {}",enrolmentData.getAccessKeyNumber());
        }

        Location loc = new Location();
        loc.setID(objectFactory.createLocationID(Integer.parseInt(enrolmentData.getLocationId())));
        client.setHomeLocation(loc);
        client.setAction(ActionCode.ADDED);

        if (!isDuplicateUsername) {
            client.setUsername(enrolmentData.getEmail());
            client.setPassword("password123");
        }

        client.setLiabilityRelease(objectFactory.createClientLiabilityRelease(true));
        client.setEmailOptIn(objectFactory.createClientLiabilityRelease(true));
        client.setPromotionalEmailOptIn(objectFactory.createClientLiabilityRelease(true));

        client.setFirstName(enrolmentData.getFirstName());
        client.setLastName(enrolmentData.getLastName());
        client.setMobilePhone(enrolmentData.getPhone());
        client.setEmail(enrolmentData.getEmail());

        client.setEmergencyContactInfoName(enrolmentData.getEmergencyContactName());
        client.setEmergencyContactInfoPhone(enrolmentData.getEmergencyContactPhone());
        client.setAddressLine1(enrolmentData.getAddress1());
        client.setAddressLine2(enrolmentData.getAddress2());
        client.setCity(enrolmentData.getCity());
        client.setState(enrolmentData.getState());
        client.setCountry("AU");
        client.setPostalCode(enrolmentData.getPostcode());

        if (!enrolmentData.getGender().equals("other")) {
            client.setGender(enrolmentData.getGender());
        }

        client.setBirthDate(objectFactory.createClientBirthDate(handleDate_YYYYMMDD(enrolmentData.getDob())));
        client.setNotes(enrolmentData.getNotes());

        if (!skipRep) {

            try {
                ArrayOfRep arrayOfRep = objectFactory.createArrayOfRep();
                String strArr[] = enrolmentData.getStaffMember().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
                Rep rep = objectFactory.createRep();
                if (enrolmentData.getStaffMember() != null) {
                    Staff staff = objectFactory.createStaff();
                    staff.setID(objectFactory.createStaffID(Long.parseLong(strArr[0])));
                    rep.setStaff(staff);
                    rep.setID(1);
                    arrayOfRep.getRep().add(rep);
                }

                strArr = enrolmentData.getPersonalTrainer().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
                long pt = Long.parseLong(strArr[0]);

                if (enrolmentData.getPersonalTrainer() != null && pt > 0) {

                    Rep rep2 = objectFactory.createRep();

                    Staff staff = objectFactory.createStaff();
                    staff.setID(objectFactory.createStaffID(pt));
                    rep2.setStaff(staff);
                    rep2.setID(2);
                    arrayOfRep.getRep().add(rep2);
                }
                client.setReps(arrayOfRep);
            } catch (Exception ex) {
                logger.error("Error assigning Rep Trainer: {} Staff:{} {}",enrolmentData.getStaffMember(), enrolmentData.getPersonalTrainer(), ex.getMessage());
            }
        }

        if (enrolmentData.getMemberCreditCard() != null) {

            ClientCreditCard cc = new ClientCreditCard();
            cc.setCardHolder(enrolmentData.getMemberCreditCard().getHolder());
            cc.setAddress(enrolmentData.getMemberCreditCard().getAddress());
            cc.setCity(enrolmentData.getMemberCreditCard().getCity());
            cc.setState(enrolmentData.getMemberCreditCard().getState());
            cc.setPostalCode(enrolmentData.getMemberCreditCard().getPostcode());
            cc.setCardNumber(enrolmentData.getMemberCreditCard().getNumber());
            cc.setExpMonth(enrolmentData.getMemberCreditCard().getExpMonth());
            cc.setExpYear(enrolmentData.getMemberCreditCard().getExpYear());

            client.setClientCreditCard(cc);
        }

        ArrayOfClient arrClient = objectFactory.createArrayOfClient();
        arrClient.getClient().add(client);
        request.setClients(arrClient);

        // AddOrUpdateClient
        AddOrUpdateClients clients = new AddOrUpdateClients();
        clients.setRequest(request);

        return clients;

    }

    private static XMLGregorianCalendar handleDate_YYYYMMDD(String dateString) {

//        logger.info("handleDate_YYYYMMDD({})",dateString);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        XMLGregorianCalendar cal = null;
        try {
            GregorianCalendar c = new GregorianCalendar();
            c.setTime(dateFormat.parse(dateString));
            cal = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            cal.setTimezone( DatatypeConstants.FIELD_UNDEFINED );
//            logger.info("cal {}",cal);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
            logger.error("Error handling date {} -> {}",dateString, e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("Error handling date {} -> {}",dateString, e.getMessage());
        }
        return cal;
    }

    private static XMLGregorianCalendar handleDate(String dateString) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        XMLGregorianCalendar cal = null;
        try {
            GregorianCalendar c = new GregorianCalendar();
            c.setTime(dateFormat.parse(dateString));
            cal = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            // remove timezone as mindbody is in US and is causing conversions
            // to occur with birthdate resulting in it being incorrect
            cal.setTimezone( DatatypeConstants.FIELD_UNDEFINED );
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
            logger.error("Error handling date {} -> {}",dateString, e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("Error handling date {} -> {}",dateString, e.getMessage());
        }
        return cal;
    }

//    private static XMLGregorianCalendar setDate
}
