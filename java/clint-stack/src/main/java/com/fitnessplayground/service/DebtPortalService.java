package com.fitnessplayground.service;

import com.fitnessplayground.dao.domain.DebtPortal;

import java.util.ArrayList;

public interface DebtPortalService {

    Iterable<DebtPortal> getAllDebtPortal();
    ArrayList<DebtPortal> getAllCurrentDebtPortal();
    ArrayList<DebtPortal> getDebtPortalCommsList();
    DebtPortal getDebtPortalById(Long id);
    DebtPortal saveDebtPortal(DebtPortal debtPortal);
}
