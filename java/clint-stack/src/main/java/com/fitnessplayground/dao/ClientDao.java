package com.fitnessplayground.dao;

import com.fitnessplayground.dao.domain.MboClient;
import com.fitnessplayground.dao.domain.MboClientContract;
import com.fitnessplayground.dao.domain.MemberStatusReport;

import java.util.List;

public interface ClientDao {

    MemberStatusReport getLatestReport(int locationId);

    Iterable<MboClient> getAllClients();

    List<MboClient> getActiveClients();

    MboClient findByUniqueIdAndSiteId(long uniqueId, long siteId);

    MboClient saveClient(MboClient mboClient);

    void saveMemberStatus(MemberStatusReport memberStatusReport);

    List<MboClient> searchDuplicateKey(String accessKeyNumber);

    List<MboClient> searchForExistingClient(String firstName, String lastName, String email);

    List<MboClient> findClientByUsername(String username);

    MboClientContract saveClientContract(MboClientContract mboClientContract);

    MboClientContract findClientContractByContractId(long clientContractId, long siteId);

    MboClient findClientById(long id);


}
