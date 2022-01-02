package com.fitnessplayground.dao;

import com.fitnessplayground.dao.domain.AccessKeySiteCode;
import com.fitnessplayground.dao.domain.ops.FPOpsConfig;

/**
 * Created by micheal on 11/03/2017.
 */
public interface FPOpsConfigDao {
    FPOpsConfig findByName(String name);
    FPOpsConfig findById(long id);
    AccessKeySiteCode findSiteCodeByLocationId(Integer locationId);
}
