package com.fitnessplayground.dao.impl;

import com.fitnessplayground.dao.FPOpsConfigDao;
import com.fitnessplayground.dao.domain.AccessKeySiteCode;
import com.fitnessplayground.dao.domain.ops.FPOpsConfig;
import com.fitnessplayground.dao.repository.AccessKeySiteCodeRepository;
import com.fitnessplayground.dao.repository.FPOpsConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by micheal on 11/03/2017.
 */
@Repository
public class FPOpsConfigDaoImpl implements FPOpsConfigDao {

    private FPOpsConfigRepository fpOpsConfigRepository;

    private AccessKeySiteCodeRepository accessKeySiteCodeRepository;

    @Override
    public FPOpsConfig findByName(String name) {
        return getFpOpsConfigRepository().findByName(name);
    }

    @Override
    public FPOpsConfig findById(long id) {
        return getFpOpsConfigRepository().findById(id);
    }

    @Override
    public AccessKeySiteCode findSiteCodeByLocationId(Integer locationId) {
        return getAccessKeySiteCodeRepository().findByLocationId(locationId);
    }

    public FPOpsConfigRepository getFpOpsConfigRepository() {
        return fpOpsConfigRepository;
    }

    @Autowired
    public void setFpOpsConfigRepository(FPOpsConfigRepository fpOpsConfigRepository) {
        this.fpOpsConfigRepository = fpOpsConfigRepository;
    }

    public AccessKeySiteCodeRepository getAccessKeySiteCodeRepository() {
        return accessKeySiteCodeRepository;
    }

    @Autowired
    public void setAccessKeySiteCodeRepository(AccessKeySiteCodeRepository accessKeySiteCodeRepository) {
        this.accessKeySiteCodeRepository = accessKeySiteCodeRepository;
    }
}
