package com.fitnessplayground.dao.impl;

import com.fitnessplayground.dao.SecurityDao;
import com.fitnessplayground.dao.domain.Credentials;
import com.fitnessplayground.dao.domain.Encrypt;
import com.fitnessplayground.dao.repository.CredentialsRepository;
import com.fitnessplayground.dao.repository.SecurityRepository;
import com.fitnessplayground.exception.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SecurityDaoImpl implements SecurityDao {

    private static final Logger logger = LoggerFactory.getLogger(SecurityDaoImpl.class);

    private SecurityRepository securityRepository;
    private CredentialsRepository credentialsRepository;

    @Override
    public Iterable<Encrypt> getEncryptionKeys() {
        return securityRepository.findAll();
    }

    @Override
    public boolean save(Encrypt encrypt) {
        try {
            getSecurityRepository().save(encrypt);
            logger.info("Saved new Encryption Keys");
            return true;
        } catch (Exception ex) {
            logger.error("Exception saving new Encryption Keys - Error: [{}]", ex.getMessage());
//            String errorMsg = "Exception saving new Encryption Keys";
//            throw new DatabaseException(errorMsg, ex);
            return false;
        }
    }

    @Override
    public Credentials getCredentialsByTpName(String tpName) {
        Iterable<Credentials> credentials = getCredentialsRepository().findAll();
        for (Credentials c : credentials) {
            if (c.getTpName().equals(tpName)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public Credentials saveCredentials(Credentials credentials) {
        try {
            Credentials cred = getCredentialsRepository().save(credentials);
            logger.info("Saved new Credentials");
            return cred;
        } catch (Exception ex) {
            logger.error("Exception saving new Credentials - Error: [{}]", ex.getMessage());
//            String errorMsg = "Exception saving new Encryption Keys";
//            throw new DatabaseException(errorMsg, ex);
            return null;
        }
    }


    public SecurityRepository getSecurityRepository() {
        return securityRepository;
    }

    @Autowired
    public void setSecurityRepository(SecurityRepository securityRepository) {
        this.securityRepository = securityRepository;
    }

    public CredentialsRepository getCredentialsRepository() {
        return credentialsRepository;
    }

    @Autowired
    public void setCredentialsRepository(CredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository;
    }
}
