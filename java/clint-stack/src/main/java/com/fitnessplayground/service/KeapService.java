package com.fitnessplayground.service;

import com.fitnessplayground.dao.domain.Credentials;
import com.fitnessplayground.dao.domain.temp.KeapTokenResponse;

public interface KeapService {

    Credentials getKeapToken();
    Boolean updateKeapCredential(KeapTokenResponse keapTokenResponse);
}
