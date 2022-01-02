package com.fitnessplayground.util;

import com.fitnessplayground.dao.domain.MemberCreditCard;
import com.fitnessplayground.dao.domain.Staff;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Helpers {

    private static final Logger logger = LoggerFactory.getLogger(Helpers.class);

    public static String arrayToString(String[] input) {
        String output = "";

//        logger.info("Inside arrayToString with input {} : output {}");

        if (input == null || input.length <= 0) {
            return Arrays.toString(input);
        }

        for (String s : input) {
            output += s;
            output += ",";
        }

//        logger.info("Inside arrayToString about to removeLastCharacter with input {} : output {}");

        return removeLastCharacter(output);
    }


    public static String intArrayToString(int[] input) {

        String output = "";

//        logger.info("Inside removeLastCharacter with input {} : output {}");

        if (input == null || input.length <= 0) {
            return Arrays.toString(input);
        }

        for (int i : input) {
            output += String.valueOf(i);
            output += ",";
        }

//        logger.info("Inside longArrayToString about to removeLastCharacter with input {} : output {}");

        return removeLastCharacter(output);
    }


    public static String longArrayToString(long[] input) {

        String output = "";

//        logger.info("Inside removeLastCharacter with input {} : output {}");

        if (input == null || input.length <= 0) {
            return Arrays.toString(input);
        }

        for (long l : input) {
            output += String.valueOf(l);
            output += ",";
        }

//        logger.info("Inside longArrayToString about to removeLastCharacter with input {} : output {}");

        return removeLastCharacter(output);
    }

    public static String cleanPhoneNumber(String input) {

        if (input == null) {
            return input;
        }

        String output = "+61";

//        logger.info("cleanPhoneNumber input: {}", input);

        if (input == null) {
            return input;
        }

        String str = input.replaceAll("\\s","");

//        logger.info("cleanPhoneNumber str after replaceAll: {}", str);

        str = str.substring(1, str.length());
        output += str;

        return output;
    }

    public static String capitalise(String input) {
        String output = "";

        if (input == null || input.length() <= 0) {
            return input;
        }

//        logger.info("capitalise input -> {}",input);

        try {
            String[] names = input.split("\\s");

//            logger.info("capitalise names -> {}",Arrays.toString(names));

            for (String s : names) {
                if (s.length() > 0) {
                    s = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
                    output += s;
                    output += " ";
                }
            }
        } catch (Exception ex) {
            logger.error("Error Capitalising {} error: {} ",input,ex.getMessage());
            return input;
        }

//        logger.info("capitalise output -> {}",output);
        return removeLastCharacter(output);
    }

    public static String removeLastCharacter(String input) {
        String output = "";

//        logger.info("Remove Last Character of input {}", input);

        if (input == null || input.length() <= 0) {
            return input;
        }

        try {
            if (input.length() > 0) {
                output = input.substring(0, input.length() - 1);
            }
        } catch (Exception ex) {
            logger.error("Error removeLastCharacter() {} error: {} ",input,ex.getMessage());
            return input;
        }

//        logger.info("Last Character Removed output {}", output);

        return output;
    }

    public static String removeLastNCharacters(String input, int numChar) {
        String output = "";

//        logger.info("Remove Last Character of input {}", input);

        if (input == null || input.length() <= 0) {
            return input;
        }

        try {
            if (input.length() > 0) {
                output = input.substring(0, input.length() - numChar);
            }
        } catch (Exception ex) {
            logger.error("Error removeLastCharacter() {} error: {} ",input,ex.getMessage());
            return input;
        }

//        logger.info("Last Character Removed output {}", output);

        return output;
    }

    public static String[] splitFullName(String name) {
        if (name != null) {
            return name.split(" ");
        } else {
            return null;
        }
    }

    public static String splitFullNameGetFirstName(String name) {
        String[] split = splitFullName(name);
        if (split != null) {
            return split[0];
        } else {
            return "";
        }
    }

    public static String splitFullNameGetLastName(String name) {
        String[] split = splitFullName(name);
        if (split != null) {
            if (split.length > 1) {
                return split[split.length - 1];
            } else {
                return split[0];
            }
        } else {
            return "";
        }
    }

    public static String cleanCamelCase(String in) {
        if (in == null) return in;
        String output = "";
        String[] s = in.split(",");
        for (String input : s) {
            String[] split = input.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");
            try {
                for (String word : split) {
                    output += word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
                    output += " ";
                }
            } catch (Exception e) {
                logger.error("Error cleaning camel case word");
                return input;
            }
        }
        return output;
    }

    public static String cleanCreditCard(String input) {
        if (input == null || input.length() <= 4) {
            return input;
        }
        return input.substring(input.length() - 4);
    }

    public static MemberCreditCard cleanPaymentDetails(MemberCreditCard creditCard) {
        if (creditCard == null) {
            return creditCard;
        }

        creditCard.setNumber(cleanCreditCard(creditCard.getNumber()));
        creditCard.setVerificationCode("***");

        return creditCard;
    }


    public static String getDatePlusNDays(int N) {
//        UTC TIMESTAMP OBJECT
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DATE, N);
//        logger.info("getDatePlusNDays({}): {}",N,now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH) + 1)+"-"+now.get(Calendar.DATE));
        return now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH) + 1 + "-"+now.get(Calendar.DATE));
    }

    public static String cleanDateTime(String input) {
//        logger.info("Clean date {}, {}",input, input.length());
//        String[] inputArr = input.split("T");
//        input = inputArr[0];
        if (input == null) {
            return input;
        }

        input = input.substring(0, 19);
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        DateTimeFormatter dtf = getDateTimeFormat();
        LocalDateTime dateTime = LocalDateTime.parse(input);
        String formattedDate= dtf.format(dateTime);
    //        logger.info("Clean date [{}] | [{}]",input, formattedDate);

        return formattedDate;
    }

    public static String cleanDate(String input) {
        if (input == null) {
            return input;
        }

        try {
            input = input.split(" ")[0];
            input = input.split("T")[0];
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
            LocalDate dateObj = LocalDate.parse(input);
            String formattedDate = capitalise(dateObj.getDayOfWeek().toString()) + ", " + dateObj.format(dtf);

            return formattedDate;
        } catch (Exception ex) {
            logger.error("Error formating date: {}",ex.getMessage());
            return input;
        }
    }

    public static String getDateNow(long siteId) {
        ZoneId zoneId = getZoneId(siteId);
        return getDateInstant(zoneId);
    }

    public static String getDateNow(String locationID) {
        ZoneId zoneId = getZoneId(locationID);
        return getDateInstant(zoneId);
    }

    public static String getDateNow() {
        ZoneId zoneId =  ZoneId.of("Australia/Sydney");
        return getDateInstant(zoneId);
    }

    public static Instant getInstant() {
        return Instant.now();
    }

    public static Long getTimeElapsed(Instant start, Instant finish) {
        try {
            return Duration.between(start, finish).toMillis();
        } catch (Exception e) {
            return null;
        }
    }

    public static int getDateDifference(String date1, String date2) {

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDate localDate1 = LocalDate.parse(date1, formatter);
            LocalDate localDate2 = LocalDate.parse(date2, formatter);
            Period period = Period.between(localDate1, localDate2);
            return period.getDays();
        }catch (Exception ex) {
            logger.error("Error trying to get date difference: {}",ex.getMessage());
            return 0;
        }
    }

//    Returns positive IF date_1 is after date_2
//    negative IF date_1 is before date_2
//    0 IF date_1 is the same date_2
    public static int compareTwoDates(String date_1, String date_2) throws ParseException {

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = sdformat.parse(date_1);
        Date d2 = sdformat.parse(date_2);

//        logger.info("The date 1 is: {}", sdformat.format(d1));
//        logger.info("The date 2 is: {}", sdformat.format(d2));

//        if(d1.compareTo(d2) > 0) {
//            logger.info("Date 1 occurs after Date 2");
//        } else if(d1.compareTo(d2) < 0) {
//            logger.info("Date 1 occurs before Date 2");
//        } else if(d1.compareTo(d2) == 0) {
//            logger.info("Both dates are equal");
//        }

        return d1.compareTo(d2);
    }

    public static boolean isNumeric(String input) {

        if (input == null) {
            return false;
        }
        return input.matches("-?\\d+(\\.\\d+)?");
    }

    private static String getDateInstant(ZoneId zoneId) {
        DateTimeFormatter dtf = getDateTimeFormat();
        Instant instant = Instant.now();
        ZonedDateTime zdt = instant.atZone(zoneId);
        return dtf.format(zdt);
    }

    private static ZoneId getZoneId(long siteId) {
        return siteId == Constants.DARWIN_SITE_ID ? ZoneId.of("Australia/Darwin") : ZoneId.of("Australia/Sydney");
    }

    private static ZoneId getZoneId(String locId) {
        return locId.equals(Constants.GATEWAY_LOCATION_ID) ? ZoneId.of("Australia/Darwin") : ZoneId.of("Australia/Sydney");
    }

    private static DateTimeFormatter getDateTimeFormat() {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        return outputFormatter;
    }

    public static byte[] decodeBase64(String input) {
        String[] output;
        if (input.contains(",")) {
            output = input.split(",");
            return Base64.decodeBase64(output[1].getBytes());
        } else if (input.contains("'")) {
            output = input.split("'");
            return Base64.decodeBase64(output[1].getBytes());
        } else {
            return Base64.decodeBase64(input.getBytes());
        }
    }

    public static XMLGregorianCalendar handleDate_YYYYMMDD(String dateString) {

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

    public static HashMap<Long, String> sortMapByValue(Map<Long, String> unsortMap, final boolean ascending) {
        List<Map.Entry<Long, String>> list = new LinkedList<>(unsortMap.entrySet());

        // Sorting the list based on values
        list.sort((o1, o2) -> ascending ? o1.getValue().compareTo(o2.getValue()) == 0
                ? o1.getKey().compareTo(o2.getKey())
                : o1.getValue().compareTo(o2.getValue()) : o2.getValue().compareTo(o1.getValue()) == 0
                ? o2.getKey().compareTo(o1.getKey())
                : o2.getValue().compareTo(o1.getValue()));
        return list.stream().collect(Collectors.toMap(Map.Entry::getKey, Entry::getValue, (a, b) -> b, HashMap::new));
    }

    public static String staffToMboIdAndSiteId(Staff staff) {
        return staff.getMboId() + Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER + staff.getSiteId();
    }

    public static String[] splitStaffMboIdAndSiteId(String mboIdAndSiteId) {
        return mboIdAndSiteId.split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
    }

    public static boolean isSydney(String locId) {
        return !locId.equals("4");
    }

    public static long getSiteIdByLocationId(String locationId) {
        return isSydney(locationId) ? Constants.SYDNEY_SITE_ID : Constants.DARWIN_SITE_ID;
    }

    public static double getAccessKeyDiscountAmount(String discountCode) {

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

}
