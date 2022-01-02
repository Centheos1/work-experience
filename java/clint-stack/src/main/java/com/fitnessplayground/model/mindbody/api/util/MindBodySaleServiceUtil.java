package com.fitnessplayground.model.mindbody.api.util;

import com.fitnessplayground.dao.domain.EnrolmentData;
import com.fitnessplayground.dao.domain.MboContract;
import com.fitnessplayground.dao.domain.MemberCreditCard;
import com.fitnessplayground.model.mindbody.api.sale.*;
import com.fitnessplayground.util.Constants;
import org.apache.commons.codec.binary.Base64;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Created by micheal on 18/02/2017.
 */
public class MindBodySaleServiceUtil extends MindBodyServiceUtil {

    private static final ObjectFactory objectFactory = new ObjectFactory();

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
        userCredentials.setUsername(getMindbodyUserCredentialsUsername());
        userCredentials.setPassword(getMindbodyUserCredentialsPassword());
//        userCredentials.setUsername("Siteowner");  // sandbox
//        userCredentials.setPassword("apitest1234");  // sandbox
        ArrayOfInt siteIds = objectFactory.createArrayOfInt();
        siteIds.getInt().add(new Integer(getMindbodySiteId()));
        siteIds.getInt().add(new Integer(getMindbodySiteIdDarwin()));
        userCredentials.setSiteIDs(siteIds);
        return userCredentials;
    }

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

    public static CartItem buildCartItems() {
        CartItem cartItem = new CartItem();

        //cartItem.setQuantity(Obje);
        return cartItem;
    }

    public static ArrayOfPaymentInfo buildPayment() {
        CompInfo compInfo = objectFactory.createCompInfo();
        //compInfo.setAmount(new BigDecimal(0)); // live
//        compInfo.setAmount(new BigDecimal(100)); //sandbox
        compInfo.setAmount(new BigDecimal(getMindbodyCompinfoPaymentAmount()));
        ArrayOfPaymentInfo arrayOfPaymentInfo = objectFactory.createArrayOfPaymentInfo();
        arrayOfPaymentInfo.getPaymentInfo().add(compInfo);
        return arrayOfPaymentInfo;
    }

    public static ArrayOfCartItem buildCart() {
        CartItem cartItem = objectFactory.createCartItem();
//        cartItem.setQuantity(objectFactory.createLocationID(10209));
        cartItem.setQuantity(objectFactory.createCartItemQuantity(1));
        cartItem.setDiscountAmount(new BigDecimal("0"));

        Service service = objectFactory.createService();
//        service.setID("1403"); // sandbox
//        service.setID("10209"); // live site
        service.setID(getMindbodyCartServiceId());
        cartItem.setItem(service);

        //objectFactory.createCartItemID(10209);
        ArrayOfCartItem arrayOfCartItem = objectFactory.createArrayOfCartItem();
        arrayOfCartItem.getCartItem().add(cartItem);
        return  arrayOfCartItem;
    }




    public static CheckoutShoppingCart buildCheckoutShoppingCart(String clientId) {
        CheckoutShoppingCartRequest checkoutShoppingCartRequest = new CheckoutShoppingCartRequest();
        checkoutShoppingCartRequest.setSourceCredentials(buildSourceCredentials());
        checkoutShoppingCartRequest.setUserCredentials(buildUserCredentials());
        checkoutShoppingCartRequest.setXMLDetail(XMLDetailLevel.FULL);
        checkoutShoppingCartRequest.setClientID(clientId);
        checkoutShoppingCartRequest.setSendEmail(false);
        checkoutShoppingCartRequest.setCartItems(buildCart());
        checkoutShoppingCartRequest.setPayments(buildPayment());
        checkoutShoppingCartRequest.setTest(isTestInvocation());

        CheckoutShoppingCart cart = new CheckoutShoppingCart();
        cart.setRequest(checkoutShoppingCartRequest);
        return cart;
    }

    public static GetContracts buildGetContracts(String siteId) {

        int locationId = 1;

        if (siteId == getMindbodySiteIdDarwin()) {
            locationId = 4;
        }

        GetContractsRequest getContractsRequest = new GetContractsRequest();
        getContractsRequest.setSourceCredentials(buildSourceCredentials(siteId));
        getContractsRequest.setUserCredentials(buildUserCredentials(siteId));
        getContractsRequest.setXMLDetail(XMLDetailLevel.FULL);
        getContractsRequest.setPageSize(1000);
        getContractsRequest.setLocationID(locationId);

        GetContracts getContracts = new GetContracts();
        getContracts.setRequest(getContractsRequest);

        return getContracts;
    }

    public static PurchaseContracts buildPurchaseContracts(MboContract contract, EnrolmentData enrolmentData) {

//        logger.info("Inside buildPurchaseContracts");

        String siteId = getMindbodySiteId();

        if (enrolmentData.getLocationId().equals("4")) {
            siteId = getMindbodySiteIdDarwin();
        }

        PurchaseContractsRequest request = new PurchaseContractsRequest();
        request.setSourceCredentials(buildSourceCredentials(siteId));
        request.setUserCredentials(buildUserCredentials(siteId));
        request.setXMLDetail(XMLDetailLevel.FULL);
        request.setPageSize(1000);
        request.setLocationID(Integer.parseInt(enrolmentData.getLocationId()));
        request.setTest(isTestInvocation());

        boolean isPTContract = false;

        if (contract != null) {
            String[] tmp = contract.getContractType().split("_");

            for (String type : tmp) {
                if (type.equals("pt")) {
                    isPTContract = true;
                }
            }
        }

//        logger.info("isPTContract = {}",isPTContract);

        if (isPTContract) {
            request.setStartDate(handleDate_YYYYMMDD(enrolmentData.getPersonalTrainingStartDate()));
        } else {
            request.setStartDate(handleDate_YYYYMMDD(enrolmentData.getFirstBillingDate()));
        }

        request.setFirstPaymentOccurs("StartDate");
        request.setClientID(enrolmentData.getAccessKeyNumber());
        request.setContractID(contract.getMboId());
        request.setSendNotifications(false);
//        request.setClientSignature(handleSignature(enrolmentData.getPrimarySignatureURL()));

//        tmp = contract.getContractType().split("_");

        if (enrolmentData.getAccessKeyDiscount() != null) {
            if (contract.getContractType().contains("accessKey") && enrolmentData.getAccessKeyDiscount().length() > 0) {
//                logger.info("isAccessKey and isDiscounted with {}", enrolmentData.getAccessKeyDiscount());
                request.setPromotionCode(enrolmentData.getAccessKeyDiscount());
            }
        }

        if (enrolmentData.getFreePTPack().equals(Constants.COUPON_CODE_PTP)) {
            if (contract.getMboId() == Constants.STARTERPACK_PTPACK_ID || contract.getMboId() == Constants.GW_STARTERPACK_PTPACK_ID) {
                logger.info("Sale Service Free PT Pack enrolmentData.getFreePTPack(): {} contract.getMboId(): {}",enrolmentData.getFreePTPack(), contract.getMboId());
                request.setPromotionCode(enrolmentData.getFreePTPack());
            }
        }

        StoredCardInfo cardInfo = new StoredCardInfo();
        cardInfo.setLastFour(enrolmentData.getMemberCreditCard().getNumber());

//        CreditCardInfo cardInfo = buildCreditCardInfo(enrolmentData.getCreditCard());

        request.setPaymentInfo(cardInfo);

        PurchaseContracts purchaseContracts = new PurchaseContracts();
        purchaseContracts.setRequest(request);

        return purchaseContracts;
//        return null;
    }


    private static CreditCardInfo buildCreditCardInfo(MemberCreditCard memberCreditCard) {

        CreditCardInfo cardInfo = new CreditCardInfo();
        cardInfo.setCreditCardNumber(memberCreditCard.getNumber());
        cardInfo.setExpMonth(memberCreditCard.getExpMonth());
        cardInfo.setExpYear(memberCreditCard.getExpYear());
        cardInfo.setBillingName(memberCreditCard.getHolder());
        cardInfo.setBillingAddress(memberCreditCard.getAddress());
        cardInfo.setBillingCity(memberCreditCard.getCity());
        cardInfo.setBillingState(memberCreditCard.getState());
        cardInfo.setBillingPostalCode(memberCreditCard.getPostcode());

        return cardInfo;
    }

    public static CheckoutShoppingCart buildCheckoutShoppingCart(String serviceId, EnrolmentData enrolmentData) {

        String siteId = getMindbodySiteId();

        if (enrolmentData.getLocationId().equals("4")) {
            siteId = getMindbodySiteIdDarwin();
        }

        CheckoutShoppingCartRequest checkoutShoppingCartRequest = new CheckoutShoppingCartRequest();
        checkoutShoppingCartRequest.setSourceCredentials(buildSourceCredentials(siteId));
        checkoutShoppingCartRequest.setUserCredentials(buildUserCredentials(siteId));
        checkoutShoppingCartRequest.setXMLDetail(XMLDetailLevel.FULL);
        checkoutShoppingCartRequest.setClientID(enrolmentData.getAccessKeyNumber());
        checkoutShoppingCartRequest.setSendEmail(false);

        checkoutShoppingCartRequest.setCartItems(buildCart(serviceId, enrolmentData));

        CompInfo compInfo = objectFactory.createCompInfo();
        compInfo.setAmount(new BigDecimal("0"));
        ArrayOfPaymentInfo arrayOfPaymentInfo = objectFactory.createArrayOfPaymentInfo();
        arrayOfPaymentInfo.getPaymentInfo().add(compInfo);
        checkoutShoppingCartRequest.setPayments(arrayOfPaymentInfo);

//        checkoutShoppingCartRequest.setPayments(buildPayment());

        checkoutShoppingCartRequest.setTest(isTestInvocation());
        checkoutShoppingCartRequest.setLocationID(Integer.parseInt(enrolmentData.getLocationId()));
        CheckoutShoppingCart cart = new CheckoutShoppingCart();
        cart.setRequest(checkoutShoppingCartRequest);
        return cart;
    }

    public static ArrayOfCartItem buildCart(String serviceId, EnrolmentData enrolmentData) {

        CartItem cartItem = objectFactory.createCartItem();
        cartItem.setQuantity(objectFactory.createCartItemQuantity(1));
        cartItem.setDiscountAmount(new BigDecimal("0"));

        Service service = objectFactory.createService();

        logger.info("Build Cart with coupon code: {} status: {} serviceId: {}",enrolmentData.getDaysFree(), enrolmentData.getStatus(), serviceId);

        if (serviceId != null) {
            service.setID(serviceId);
        }
        cartItem.setItem(service);

        //objectFactory.createCartItemID(10209);
        ArrayOfCartItem arrayOfCartItem = objectFactory.createArrayOfCartItem();
        arrayOfCartItem.getCartItem().add(cartItem);
        return  arrayOfCartItem;
    }


    private static byte[] handleSignature(String input) {
        String[] output = input.split(",");
        return Base64.decodeBase64(output[1].getBytes());
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
}
