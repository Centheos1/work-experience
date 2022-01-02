package com.fitnessplayground.service;

import com.fitnessplayground.dao.domain.EnrolmentData;
import com.fitnessplayground.dao.domain.MboContract;
import com.fitnessplayground.model.mindbody.api.result.MboApiResultEnrolmentData;
import com.fitnessplayground.model.mindbody.api.result.MindBodyApiResult;
import com.fitnessplayground.model.mindbody.api.sale.Contract;

import java.util.List;

/**
 * Created by micheal on 18/02/2017.
 */
public interface MindBodySaleService {
    List<Contract> getContracts();
    MboApiResultEnrolmentData purchaseContract(MboContract contract, EnrolmentData enrolmentData);
    MboApiResultEnrolmentData purchaseTempMembership(String serviceId, EnrolmentData enrolmentData);
}
