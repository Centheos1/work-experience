package com.fitnessplayground.util;

import org.slf4j.MDC;
/**
 * Created by micheal on 18/03/2017.
 */
public class MDCHelper {

    public static void preExecuteMDCSet(String username) {
        MDC.put("Username", username);
    }

    public static void postExecuteMDCUnset() {
        MDC.remove("Username");
    }
}