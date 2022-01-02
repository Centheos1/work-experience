package com.fitnessplayground.service;

import com.fitnessplayground.dao.domain.fpSourceDto.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;

public interface FPSourceService {

    DigitalPreExData getDigitalPreData(EntityLookUp entityLookUp);
    void handleStaffUpdate(ArrayList<MboStaff> staffs);
    void handlePTParQ(FPPTParQData data);
//    void handleActiveCampaignUpdatePersonalTrainer(FPReassignPersonalTrainerRequest fpReassignPersonalTrainerRequest);
}
