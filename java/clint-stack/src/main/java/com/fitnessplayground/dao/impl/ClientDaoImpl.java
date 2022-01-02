package com.fitnessplayground.dao.impl;

import com.fitnessplayground.dao.ClientDao;
import com.fitnessplayground.dao.domain.MboClient;
import com.fitnessplayground.dao.domain.MboClientContract;
import com.fitnessplayground.dao.domain.MemberStatusReport;
import com.fitnessplayground.dao.repository.ClientContractRepository;
import com.fitnessplayground.dao.repository.ClientRepository;
import com.fitnessplayground.dao.repository.MemberStatusReportRepository;
import com.fitnessplayground.exception.DatabaseException;
import com.fitnessplayground.util.Helpers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.util.List;

@Repository
public class ClientDaoImpl implements ClientDao {

    private static final Logger logger = LoggerFactory.getLogger(ClientDaoImpl.class);

//    @Autowired
    private ClientRepository clientRepository;

//    @Autowired
    private ClientContractRepository clientContractRepository;

//    @Autowired
    private MemberStatusReportRepository memberStatusReportRepository;


    @Override
    public MemberStatusReport getLatestReport(int locationId) {
        return memberStatusReportRepository.getLastestReport(locationId);
    }


    @Override
    public Iterable<MboClient> getAllClients() {
        return getClientRepository().findAll();
    }

    @Override
    public List<MboClient> getActiveClients() {
        return getClientRepository().findActiveClients();
    }

    @Override
    public MboClient findByUniqueIdAndSiteId(long uniqueId, long siteId) {

        List<MboClient> mboClients = getClientRepository().findByUniqueIdAndSiteId(uniqueId, siteId);

        final int SIZE = mboClients.size();

        if (SIZE == 0) {
            return null;

        } else if (SIZE == 1) {
            return mboClients.get(0);

        } else {
//            Return the most recent and delete the rest
            MboClient mostRecent = mboClients.get(0);
            for (MboClient c : mboClients.subList(1, mboClients.size())) {
                try {
                    if (Helpers.compareTwoDates(c.getUpdateDate(), mostRecent.getUpdateDate()) >= 0) {
                        getClientRepository().delete(mostRecent.getId());
                        mostRecent = c;
                    }
                } catch (ParseException e) {
                    logger.error("Error comparing two dates");
                }
            }
            return mostRecent;
        }
    }


    @Override
    public MboClient saveClient(MboClient mboClient) {

        try {
//            logger.info("About to save Member: [{}]", mboContract.getName());
            mboClient = getClientRepository().save(mboClient);
//            logger.info("Saved Client: [{}]", mboClient.getUniqueId());
        } catch (Exception ex) {
            String errorMsg = "Exception saving Client: "+mboClient.getUniqueId();
            logger.error("Exception saving Client: [{}] Error: [{}]", mboClient.getUniqueId(), ex.getMessage());
            throw new DatabaseException(errorMsg, ex);
        }

        return mboClient;
    }

    @Override
    public void saveMemberStatus(MemberStatusReport memberStatusReport) {

        try {
//            logger.info("About to save memberStatusReport: [{}]", memberStatusReport.getCreateDate());
            getMemberStatusReportRepository().save(memberStatusReport);
//            logger.info("Saved memberStatusReport: [{}]", memberStatusReport.getCreateDate());
        } catch (Exception ex) {
            String errorMsg = "Exception saving memberStatusReport: [{}]"+ memberStatusReport.getCreateDate();
            logger.error("Exception saving memberStatusReport: [{}] Error: [{}]", memberStatusReport.getCreateDate(), ex.getMessage());
            throw new DatabaseException(errorMsg, ex);
        }

    }

    @Override
    public List<MboClient> searchDuplicateKey(String accessKeyNumber) {
        List<MboClient> foundClient = getClientRepository().searchDuplicateKey(accessKeyNumber);
//        logger.info("Search Duplicate Key found {} clients with Access Key Number {}", foundClient.size(), accessKeyNumber);
        return foundClient;
    }

    @Override
//    public List<MboClient> searchForExistingClient(String phone, String firstName, String lastName, String email) {
    public List<MboClient> searchForExistingClient(String firstName, String lastName, String email) {

//        return clientRepository.searchForExistingClient(trimString(phone), trimString(firstName), trimString(lastName), trimString(email));
        return getClientRepository().searchForExistingClient(trimString(firstName), trimString(lastName), trimString(email));
    }

    @Override
    public List<MboClient> findClientByUsername(String username) {
        return getClientRepository().findByUsername(username);
    }

    @Override
    public MboClientContract saveClientContract(MboClientContract mboClientContract) {
        try {
//            logger.info("About to save Member: [{}]", mboContract.getName());
            mboClientContract = getClientContractRepository().save(mboClientContract);
//            logger.info("Saved Client Contract: [{}]", mboClientContract.getClientContractId());
        } catch (Exception ex) {
            String errorMsg = "Exception saving Client Contract: "+mboClientContract.getClientContractId();
            logger.error("Exception saving Client Contract: [{}] Error: [{}]", mboClientContract.getClientContractId(), ex.getMessage());
            throw new DatabaseException(errorMsg, ex);
        }

        return mboClientContract;
    }

    @Override
    public MboClientContract findClientContractByContractId(long clientContractId, long siteId) {
        return getClientContractRepository().findByContractIdAndSiteId(clientContractId, siteId);
//        return clientContractRepository.findOne(clientContractId);
    }

    @Override
    public MboClient findClientById(long id) {
        return getClientRepository().findOne(id);
    }


    private String trimString(String input) {
        return input != null ? input.trim() : null;
    }


    public ClientRepository getClientRepository() {
        return clientRepository;
    }

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientContractRepository getClientContractRepository() {
        return clientContractRepository;
    }

    @Autowired
    public void setClientContractRepository(ClientContractRepository clientContractRepository) {
        this.clientContractRepository = clientContractRepository;
    }

    public MemberStatusReportRepository getMemberStatusReportRepository() {
        return memberStatusReportRepository;
    }

    @Autowired
    public void setMemberStatusReportRepository(MemberStatusReportRepository memberStatusReportRepository) {
        this.memberStatusReportRepository = memberStatusReportRepository;
    }
}
