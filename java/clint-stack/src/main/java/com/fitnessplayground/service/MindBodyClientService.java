package com.fitnessplayground.service;

import com.fitnessplayground.dao.domain.EnrolmentData;
import com.fitnessplayground.model.mindbody.api.client.Client;
import com.fitnessplayground.model.mindbody.api.result.MboApiResultEnrolmentData;

import java.util.List;


/**
 * Created by micheal on 29/01/2017.
 */
public interface MindBodyClientService {
    List<Client> getAllClients();
    MboApiResultEnrolmentData addNewClient(EnrolmentData enrolmentData, boolean isDuplicateKey, boolean isDuplicateUsername, boolean isTestSubmission);
}
