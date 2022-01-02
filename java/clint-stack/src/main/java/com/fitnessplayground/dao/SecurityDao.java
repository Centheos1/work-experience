package com.fitnessplayground.dao;

import com.fitnessplayground.dao.domain.Credentials;
import com.fitnessplayground.dao.domain.Encrypt;

public interface SecurityDao {
    Iterable<Encrypt> getEncryptionKeys();
    boolean save(Encrypt encrypt);
    Credentials getCredentialsByTpName(String tpName);
    Credentials saveCredentials(Credentials credentials);
}
