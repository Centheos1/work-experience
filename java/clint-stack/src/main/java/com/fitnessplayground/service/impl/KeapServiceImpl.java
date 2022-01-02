package com.fitnessplayground.service.impl;

import com.fitnessplayground.dao.SecurityDao;
import com.fitnessplayground.dao.domain.Credentials;
import com.fitnessplayground.dao.domain.temp.KeapTokenResponse;
import com.fitnessplayground.service.KeapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class KeapServiceImpl implements KeapService {

    private static final Logger logger = LoggerFactory.getLogger(KeapServiceImpl.class);

    private SecurityDao securityDao;

    @Override
    public Credentials getKeapToken() {
        return getSecurityDao().getCredentialsByTpName("Keap");
    }

    @Override
    public Boolean updateKeapCredential(KeapTokenResponse keapTokenResponse) {
        Credentials credentials = getSecurityDao().getCredentialsByTpName("Keap");

        if (credentials == null) {
            logger.error("Keap Credentials not found in the database");
            return false;
        }

        credentials.setToken(keapTokenResponse.getAccessToken());
        credentials.setRefreshToken(keapTokenResponse.getRefreshToken());
        credentials.setTokenExpiresIn(Integer.toString(keapTokenResponse.getExpiresIn()));

        credentials = getSecurityDao().saveCredentials(credentials);

        if (credentials == null) {
            return false;
        } else {
            return true;
        }
    }


    public SecurityDao getSecurityDao() {
        return securityDao;
    }

    @Autowired
    public void setSecurityDao(SecurityDao securityDao) {
        this.securityDao = securityDao;
    }

}
