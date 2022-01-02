package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.*;
import com.fitnessplayground.util.FitnessPlaygroundUtil;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class RepositoryTest {

    @Autowired
    MemberCreditCardRepository memberCreditCardRepository;

    @Autowired
    MemberBankDetailsRepository memberBankDetailsRepository;


    @Before
    public void setUp() throws Exception {
//        cleanDatabase();
        //sScriptUtils.executeSqlScript();
    }


    @Test
    public void testLoadMembers() {
       cleanDatabase();
       FullWebhookSubmission fullWebhookSubmission = TestUtil.buildFullWebhookSubmission();

       MemberAddress memberAddress = MemberAddress.from(fullWebhookSubmission);
       memberAddress = memberAddressRepository.save(memberAddress);

       MemberCreditCard memberCreditCard = MemberCreditCard.from(fullWebhookSubmission);
       memberCreditCard = memberCreditCardRepository.save(memberCreditCard);

       MemberDetail memberDetail = MemberDetail.from(fullWebhookSubmission);
       memberDetail = memberDetailsRepository.save(memberDetail);

       Member member = Member.from(fullWebhookSubmission);
       member.setAddressId(memberAddress.getUser_id());
       member.setCreditCardId(memberCreditCard.getUser_id());
       member.setMemberDetailsId(memberDetail.getUser_id());
       member = memberRepository.save(member);

       Member member1 = memberRepository.findById(member.getUser_id());
       assertEquals(member, member1);
       member1.setClientId("132456798");
       Member member2 = memberRepository.save(member1);
       assertEquals(member1.getClientId(), member2.getClientId());
    }


    private void cleanDatabase() {
        memberBankDetailsRepository.deleteAll();
        memberCreditCardRepository.deleteAll();
    }
}