package com.fitnessplayground.util;

import org.junit.Test;
import static org.junit.Assert.*;

public class FitnessPlaygroundUtilTest {


    @Test
    public void test() throws  Exception {
        //String s = "{\"FormID\":\"2614972\",\"UniqueID\":\"309447420\",\"Enrolment Form\":\"\",\"Phone\":null,\"Email\":\"alex.obrien@email.com\",\"Date of Birth\":\"06 Aug 1988\"}";
//        String s = "{\"FormID\":\"2614972\",\"UniqueID\":\"309447420\",\"Enrolment Form\":\"\",\"Name\":{\"first\":\"Alex\",\"last\":\"O'Brien\"},\"Address\":{\"address\":\"1 Priory Way\",\"address2\":\"Celbridge\",\"city\":\"Dublin\",\"state\":\"NSW\",\"zip\":\"2017\"},\"Phone\":null,\"Email\":\"alex.obrien@email.com\",\"Date of Birth\":\"06 Aug 1988\",\"Gender\":\"Male\",\"Emergency Contact Name\":\"Gerry\",\"Emergency Contact Phone\":\"(08) 7526 9875\",\"How Did You Hear About Us?\":\"FRIEND OR FAMILY\",\"Who Referred You?\":null,\"Phone Number\":null,\"Payment Details\":\"Credit Card\",\"Credit Card\":\"************1111\",\"Expiration Date\":\"Jan 2020\",\"Card Verification Code\":\"123\",\"Account Holder Name\":null,\"Branch\":null,\"Financial Institution\":null,\"BSB\":null,\"Account Number\":null,\"Account Type\":null,\"Presentation\":\"GRAB A TEAM MEMBER\",\"MEMBER IS UNDER 18 YEARS\":\"MEMBER IS UNDER 18 YEARS\",\"Staff Name\":\"Daniel Bowden\",\"Access Key Number\":\"12345\",\"CHOOSE MEMBERSHIP TYPE\":[{\"value\":\"$31.40\",\"label\":\"GYM $14.95\"}],\"No Minimum Term\":null,\"TRAINING STARTER PACK\":\"29\",\"TRAINER NAME\":null,\"Start Date\":\"04 Mar 2017\",\"Debit Date\":\"04 March 2017\",\"Pro Rata Days\":\"0\",\"Access Key\":\"99.00\",\"Training Pack\":\"29.00\",\"Pro Rata\":null,\"TODAY'S ONE OFF TOTAL\":\"128.00\",\"First Payment Options\":\"Add to First Direct Debit\",\"Pay Today\":null,\"ONGOING FORTNIGHTLY TOTAL\":\"31.40\",\"Coupon Button\":\"Cancel\",\"Coupon Code\":null,\"Managers Signature\":\"https:\\/\\/s3.amazonaws.com\\/files.formstack.com\\/uploads\\/2614972\\/50039739\\/309447420\\/signature_50039739.png\",\"Please Initial to Confirm You Understand this is a 12 Months Agreement\":\"https:\\/\\/s3.amazonaws.com\\/files.formstack.com\\/uploads\\/2614972\\/50039740\\/309447420\\/signature_50039740.png\",\"Signature\":\"https:\\/\\/s3.amazonaws.com\\/files.formstack.com\\/uploads\\/2614972\\/50039741\\/309447420\\/signature_50039741.png\",\"Guardian Signature\":\"https:\\/\\/s3.amazonaws.com\\/files.formstack.com\\/uploads\\/2614972\\/50039742\\/309447420\\/signature_50039742.png\",\"Is there anyone you want to refer to The Fitness Playground?\":null,\"Referral Name\":null,\"Is there anyone else?\":null,\"Pre Exercise Questionnaire\":\"https:\\/\\/s3.amazonaws.com\\/files.formstack.com\\/uploads\\/2614972\\/50039752\\/309447420\\/50039752_empty.txt\",\"Add Member Photo\":\"\",\"Notes\":\"NO notes\"}";
//        GsonBuilder builder = new GsonBuilder();
//        Gson gson = builder.create();
//        ActualSubmission actualSubmission = gson.fromJson(s, ActualSubmission.class);
//       int i =0;
        String moves = "DULRLLLLdx";
        if (moves.matches("[DLURdlur]*")) {
            System.out.println("yes");
        } else {
            System.out.println("no");
        }

    }
}
