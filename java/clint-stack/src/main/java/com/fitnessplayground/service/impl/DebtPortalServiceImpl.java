package com.fitnessplayground.service.impl;

import com.fitnessplayground.dao.MemberDao;
import com.fitnessplayground.dao.domain.DebtPortal;
import com.fitnessplayground.service.DebtPortalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DebtPortalServiceImpl implements DebtPortalService {

    private static final Logger logger = LoggerFactory.getLogger(DebtPortalServiceImpl.class);


    private MemberDao memberDao;

    @Override
    public Iterable<DebtPortal> getAllDebtPortal() {
        return getMemberDao().getAllDebtPortal();
    }

    @Override
    public ArrayList<DebtPortal> getAllCurrentDebtPortal() {
        return getMemberDao().getAllCurrentDebtPortal();
    }

    @Override
    public ArrayList<DebtPortal> getDebtPortalCommsList() {
        return getMemberDao().getDebtPortalCommsList();
    }

    @Override
    public DebtPortal getDebtPortalById(Long id) {
        return getMemberDao().getDebtPortalById(id);
    }

    @Override
    public DebtPortal saveDebtPortal(DebtPortal debtPortal) {
        return getMemberDao().saveDebtPortal(debtPortal);
    }

    public MemberDao getMemberDao() {
        return memberDao;
    }

    @Autowired
    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }
}
