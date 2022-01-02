package com.fitnessplayground.service.impl;

import com.fitnessplayground.service.MindBodyClientService;
import com.fitnessplayground.service.MindBodySaleService;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class MindBodyClientServiceImplTest {


    @Autowired
    private MindBodyClientService mindBodyClientService;

    @Autowired
    private MindBodySaleService mindBodySaleService;


    @Ignore
    @Test
    public void endToEndTest() throws Exception {
//        ClientDetails clientDetails = buildTestClientDetails();
//        Member member = Member.from(clientDetails);
        Member member = FitnessPlaygroundUtil.buildFullMemberFromFullWebhookSubmission("101");
        ClientDetails clientDetails = FitnessPlaygroundUtil.buildClientDetailsFromMember(member);
        // TODO Bad hack for now...need to set up an in memory database for tests
        memberRepository.deleteAll();
        memberRepository.save(member);

        List<Member> members = (ArrayList<Member>) memberRepository.findAll();
        assertEquals("Should only have one", 1, members.size());
        Member member1 = memberRepository.findById(members.get(0).getUser_id());
        clientDetails.setUser_id(""+member1.getUser_id());

        MindBodyApiResult mindBodyAddClientResult = mindBodyClientService.addOrUpdateClient(clientDetails);
        System.out.println(mindBodyAddClientResult);

        if (mindBodyAddClientResult.isSuccess()) {
            MindBodyApiResult result = mindBodySaleService.addClientSaleViaShoppingCart(mindBodyAddClientResult.getMindBodyId());
            System.out.println(result);
        }

        Member member2 = memberRepository.findById(Integer.parseInt(mindBodyAddClientResult.getMember().getUser_id()));
        System.out.println(member2.getClientId());

        assertEquals("Active", member2.getStatus());
    } 

    @Test
    public void addClient() throws Exception {


       Member member = FitnessPlaygroundUtil.buildFullMemberFromFullWebhookSubmission("m11@email.com");
       //ClientDetails clientDetails = FitnessPlaygroundUtil.buildClientDetailsFromMember(member);
       MindBodyApiResult mindBodyAddClientResult = mindBodyClientService.addOrUpdateClient(member);
       System.out.println(mindBodyAddClientResult);
    }

    @Test
    @Ignore
    public void addClientSale() throws Exception {
       String ID = "ID24";
       Member member = FitnessPlaygroundUtil.buildFullMemberFromFullWebhookSubmission("101");
      // ClientDetails clientDetails = FitnessPlaygroundUtil.buildClientDetailsFromMember(member);
       MindBodyApiResult mindBodyAddClientResult = mindBodyClientService.addOrUpdateClient(member);
       System.out.println(mindBodyAddClientResult);

       if (mindBodyAddClientResult.isSuccess()) {
           MindBodyApiResult result = mindBodySaleService.addClientSaleViaShoppingCart(mindBodyAddClientResult.getMindBodyId());
           System.out.println(result);
       }
    }
}
