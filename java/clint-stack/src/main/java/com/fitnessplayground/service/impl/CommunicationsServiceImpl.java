package com.fitnessplayground.service.impl;

import com.fitnessplayground.dao.CommunicationsDto.InternalCommsMCNotesResponse;
import com.fitnessplayground.dao.CommunicationsDto.MCNotesData;
import com.fitnessplayground.dao.MemberDao;
import com.fitnessplayground.dao.StaffDao;
import com.fitnessplayground.dao.domain.EnrolmentData;
import com.fitnessplayground.dao.domain.Staff;
import com.fitnessplayground.dao.domain.temp.UIGym;
import com.fitnessplayground.service.CommunicationsService;
import com.fitnessplayground.service.FitnessPlaygroundService;
import com.fitnessplayground.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CommunicationsServiceImpl implements CommunicationsService {

    private static final Logger logger = LoggerFactory.getLogger(CommunicationsServiceImpl.class);

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private StaffDao staffDao;

//    @Autowired
//    private FitnessPlaygroundService fitnessPlaygroundService;


    @Override
    public InternalCommsMCNotesResponse getMCNotesComms(UIGym uiGym) {
        List<EnrolmentData> enrolments = memberDao.getInternalCommsMCNotesEnrolments();

        List<MCNotesData> data = new ArrayList<>();
        Staff pt = null;
        Staff mc;
        String[] tmp;

        for (EnrolmentData e : enrolments) {
            try {
                tmp = e.getStaffMember().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
                mc = staffDao.getStaffByMboId(Long.parseLong(tmp[0]), Long.parseLong(tmp[1]));

                if (e.getPersonalTrainer().equals(Constants.ASSIGN_TO_PTM_ID)) {
//                    UIGym uiGym = fitnessPlaygroundService.getGymByLocation(e.getUID(), e.getLocationId());

                    if (uiGym != null) {
                        tmp = uiGym.getPersonalTrainingManager() != null
                                ? uiGym.getPersonalTrainingManager().getMboIdAndSiteId().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER)
                                : null;
                        pt = tmp != null
                                ? staffDao.getStaffByMboId(Long.parseLong(tmp[0]), Long.parseLong(tmp[1]))
                                : null;
                    }
                } else if (e.getPersonalTrainer().equals(Constants.NO_COMP_SESSION_ID)) {
                    pt = null;
                } else {
                    tmp = e.getPersonalTrainer().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
                    pt = staffDao.getStaffByMboId(Long.parseLong(tmp[0]), Long.parseLong(tmp[1]));
                }

                MCNotesData mcNotesData = MCNotesData.from(e, pt, mc);
                data.add(mcNotesData);
            } catch (Exception ex) {
                logger.error("Error creating MCNotesData: EnrolmentData Id = {} | {}",e.getId(),ex.getMessage());
            }
        }

        InternalCommsMCNotesResponse response = new InternalCommsMCNotesResponse(data);
        return response;
    }
}
