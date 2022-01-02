package com.fitnessplayground.model.mindbody.api.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by micheal on 18/02/2017.
 */

@Component
public class MindBodyServiceUtil {

    public static final Logger logger = LoggerFactory.getLogger(MindBodyServiceUtil.class);

    private static String MINDBODY_SOURCE_CREDENTIALS_USERNAME;
    private static String MINDBODY_SOURCE_CREDENTIALS_PASSWORD;
    private static String MINDBODY_USER_CREDENTIALS_USERNAME;
    private static String MINDBODY_USER_CREDENTIALS_PASSWORD;
    private static String MINDBODY_SITE_ID;
    private static String MINDBODY_SITE_ID_DARWIN;
    private static String MINDBODY_COMPINFO_PAYMENT_AMOUNT;
    private static String MINDBODY_CART_SERVICEID;
    private static boolean IS_TEST_INVOCATION;

    @Value("${mindbody.service.sourcecredentials.username}")
    public void setMindbodySourceCredentialsUsername(String username) {
        MINDBODY_SOURCE_CREDENTIALS_USERNAME = username;
    }

    @Value("${mindbody.service.sourcecredentials.password}")
    public void setMindbodySourceCredentialsPassword(String password) {
        MINDBODY_SOURCE_CREDENTIALS_PASSWORD = password;
    }

    @Value("${mindbody.service.usercredentials.username}")
    public void setMindbodyUserCredentialsUsername(String username) {
        MINDBODY_USER_CREDENTIALS_USERNAME = username;
    }

    @Value("${mindbody.service.usercredentials.password}")
    public void setMindbodyUserCredentialsPassword(String password) {
        MINDBODY_USER_CREDENTIALS_PASSWORD = password;
    }


    @Value("${mindbody.service.siteid}")
    public void setMindbodySiteId(String siteId) {
        MINDBODY_SITE_ID = siteId;
    }

    @Value(("${mindbody.service.siteid.darwin}"))
    public void setMindbodySiteIdDarwin(String darwinSiteId) { MINDBODY_SITE_ID_DARWIN = darwinSiteId; }

    @Value("${mindbody.service.is.test.invocation}")
    public void setIsTestInvocation(String isTestInvocation) {
        IS_TEST_INVOCATION = Boolean.parseBoolean(isTestInvocation);
    }

    @Value("${mindbody.service.compinfo.paymentamount}")
    public void setCompInfoPaymentAmount(String compInfoPaymentAmount) {
        MINDBODY_COMPINFO_PAYMENT_AMOUNT = compInfoPaymentAmount;
    }

    @Value("${mindbody.service.cart.serviceid}")
    public void setCartServiceId(String cartServiceId) {
        MINDBODY_CART_SERVICEID = cartServiceId;
    }


    public static String getMindbodySourceCredentialsUsername() {
        return MINDBODY_SOURCE_CREDENTIALS_USERNAME;
    }

    public static String getMindbodySourceCredentialsPassword() {
        return MINDBODY_SOURCE_CREDENTIALS_PASSWORD;
    }

    public static String getMindbodyUserCredentialsUsername() {
        return MINDBODY_USER_CREDENTIALS_USERNAME;
    }

    public static String getMindbodyUserCredentialsPassword() {
        return MINDBODY_USER_CREDENTIALS_PASSWORD;
    }

    public static String getMindbodySiteId() {
        return MINDBODY_SITE_ID;
    }

    public static String getMindbodySiteIdDarwin() {
        return MINDBODY_SITE_ID_DARWIN;
    }

    public static  String getMindbodyCompinfoPaymentAmount() {return MINDBODY_COMPINFO_PAYMENT_AMOUNT; }

    public static String getMindbodyCartServiceId() {return MINDBODY_CART_SERVICEID; }

    public static boolean isTestInvocation() {
        return IS_TEST_INVOCATION;
    }




}
