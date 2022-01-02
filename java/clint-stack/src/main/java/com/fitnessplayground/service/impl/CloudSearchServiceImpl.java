package com.fitnessplayground.service.impl;

import com.fitnessplayground.dao.MemberDao;
import com.fitnessplayground.dao.domain.CloudSearch;
import com.fitnessplayground.dao.domain.temp.FindEnrolment;
import com.fitnessplayground.service.CloudSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CloudSearchServiceImpl implements CloudSearchService {

    private static final Logger logger = LoggerFactory.getLogger(CloudSearchServiceImpl.class);

    private MemberDao memberDao;

    private Iterable<CloudSearch> MEMBERS;

    @Async
    @Override
    public Boolean addMembers(ArrayList<CloudSearch> members) {

        logger.info("Add {} members",members.size());

        try {
            setMEMBERS(getMemberDao().addCloudSearchMembers(members));
        } catch (Exception ex) {
            logger.error("Error adding members to Cloud Search: {}",ex.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public ArrayList<CloudSearch> searchMembers(String query) {

        if (query == null) {
            return null;
        }

        query = query.toLowerCase().trim();
        logger.info("Search for: {}",query);

        ArrayList<CloudSearch> result = new ArrayList<>();


        if (query.contains(" ")) {
            String[] split = query.split("\\s",2);
            String firstName = split[0];
            String lastName = split[1];
            for (CloudSearch c : getMEMBERS()) {
                if (c.getFirst_name().toLowerCase().contains(firstName) && c.getLast_name().toLowerCase().contains(lastName)) {
                    result.add(c);
                }
            }
            return result;
        }

        if (query.contains("@")) {
            for (CloudSearch c : getMEMBERS()) {
                if (c.getEmail().toLowerCase().contains(query)) {
                    result.add(c);
                }
            }
            return result;
        }

        if (query.startsWith("0")) {
//            String x = query.substring(1);
//            String phone = query.substring(1);
//
//            for (CloudSearch c : getMEMBERS()) {
//                if (c.getPhone().startsWith("+61")) {
//                    phone = c.getPhone().substring(3);
//                } else if (c.getPhone().startsWith("61")) {
//                    phone = c.getPhone().substring(2);
//                }
//                if (c.getPhone().contains(phone) || c.getAccess_key_number().toLowerCase().contains(x)) {
//                    result.add(c);
//                }
//            }
//            return result;

            String washedQuery = query.substring(1);
            String washedPhone;
            for (CloudSearch c : getMEMBERS()) {
                washedPhone = "";
                if (c.getPhone() != null && !c.getPhone().isEmpty()) {
                    if (c.getPhone().startsWith("+61")) {
                        washedPhone = c.getPhone().substring(3);
                    } else if (c.getPhone().startsWith("61")) {
                        washedPhone = c.getPhone().substring(2);
                    } else if (c.getPhone().startsWith("0")) {
                        washedPhone = c.getPhone().substring(1);
                    }
                }

//                logger.info("Cloud Search Phone:  {} -> {} || {} | result = {}", c.getPhone(),washedPhone, washedQuery, washedPhone.startsWith(washedQuery));

                if (washedPhone.startsWith(washedQuery) || c.getAccess_key_number().contains(query)) {
                    result.add(c);
                }
            }
            return result;
        }

        for (CloudSearch c : getMEMBERS()) {
            if (c.getAccess_key_number().toLowerCase().contains(query) ||
                    c.getFirst_name().toLowerCase().contains(query) ||
                    c.getLast_name().toLowerCase().contains(query) ||
                    c.getEmail().toLowerCase().contains(query) ||
                    c.getPhone().contains(query)
            ) {
                result.add(c);
            }

        }

        return result;
    }

    @Override
    public void refresh() {
        setMEMBERS(getMemberDao().getAllCloudSearch());
    }


    public Iterable<CloudSearch> getMEMBERS() {
        return MEMBERS;
    }

    public void setMEMBERS(Iterable<CloudSearch> MEMBERS) {
        this.MEMBERS = MEMBERS;
    }

    public MemberDao getMemberDao() {
        return memberDao;
    }

    @Autowired
    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }
}
