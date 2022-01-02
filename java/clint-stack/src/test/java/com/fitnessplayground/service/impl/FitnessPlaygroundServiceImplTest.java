package com.fitnessplayground.service.impl;


import com.fitnessplayground.dao.domain.EnrolmentData;
import com.fitnessplayground.dao.domain.temp.EnrolmentFormSubmission;
import com.fitnessplayground.dao.repository.*;

import com.fitnessplayground.service.FitnessPlaygroundService;

import com.fitnessplayground.util.TestEntities;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;



@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE) // this ignores controller components
@SpringBootTest
public class FitnessPlaygroundServiceImplTest {


    private static final Logger logger = LoggerFactory.getLogger(FitnessPlaygroundServiceImplTest.class);

    @Autowired
    private FitnessPlaygroundService fitnessPlaygroundService;


    @Autowired
    MemberCreditCardRepository memberCreditCardRepository;

    @Autowired
    MemberBankDetailsRepository memberBankDetailsRepository;


    @Autowired
    RandomOnlineRepository randomOnlineRepository;

    @Before
    public void setUp() throws Exception {
       RandomOnline randomOnline = new RandomOnline("true");
       randomOnlineRepository.save(randomOnline);
    }

    @Ignore // temp cos 203 will prob exist in mindbody and so test will fail and stop startup
    @Test
    public void test1() throws Exception {
       cleanDatabase();
       RandomOnline randomOnline = new RandomOnline("true");
       randomOnline = randomOnlineRepository.save(randomOnline);
       Member member = FitnessPlaygroundUtil.buildFullMemberFromFullWebhookSubmission("2");
       // this will have been performed by the formstack service already
       member = memberRepository.save(member);
//
//        MindBodyApiResult mindBodyApiResult = fitnessPlaygroundService.createMemberInMindBody(member);
//        logger.info("{}",mindBodyApiResult);
//        assertEquals(true, mindBodyApiResult.isSuccess());
//        Member memberFromDb = memberRepository.findByClientId(mindBodyApiResult.getMindBodyId());
//        assertEquals("firstname203", memberFromDb.getFirstName());
    }
    private void cleanDatabase() {
        memberBankDetailsRepository.deleteAll();
        memberCreditCardRepository.deleteAll();
        //randomOnlineRepository.deleteAll();
    }
}
