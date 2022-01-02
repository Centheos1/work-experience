package com.fitnessplayground.service.impl;

import com.fitnessplayground.dao.MemberDao;
import com.fitnessplayground.dao.StaffDao;
import com.fitnessplayground.dao.domain.FpCoachEnrolmentData;
import com.fitnessplayground.dao.domain.temp.FindEnrolment;
import com.fitnessplayground.dao.domain.temp.FpCoachEnrolmentSubmission;
import com.fitnessplayground.service.EncryptionService;
import com.fitnessplayground.service.FpCoachService;
import com.fitnessplayground.service.MemberStatus;
import com.fitnessplayground.service.MindBodyService;
import com.fitnessplayground.util.Constants;
import com.fitnessplayground.util.Helpers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FpCoachServiceImpl implements FpCoachService {

    private static final Logger logger = LoggerFactory.getLogger(FpCoachServiceImpl.class);

    private EncryptionService encryptionService;
    private MindBodyService mindBodyService;
    private MemberDao memberDao;
    private StaffDao staffDao;

    @Override
    public List<FpCoachEnrolmentData> findCoachEnrolment(String UID, FindEnrolment findEnrolment) {

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Submission User");
            return null;
        }

        List<FpCoachEnrolmentData> fpCoachEnrolmentData = getMemberDao().findCoachEnrolments(findEnrolment);
        return fpCoachEnrolmentData;

    }

    @Override
    public FpCoachEnrolmentData saveFpCoachEnrolmentData(FpCoachEnrolmentSubmission submission) {

        logger.info("Inside saveFpCoachEnrolmentData()");

//        build Data Entity
        FpCoachEnrolmentData data = FpCoachEnrolmentData.create(submission);

        logger.info("{}",data.getCommunicationsStatus());

//        Encrypt and save
        if (data.getMemberCreditCard() != null) {
            data.setMemberCreditCard(getEncryptionService().encryptCMemberCreditCard(data.getMemberCreditCard()));
        }

        if (data.getMemberBankDetail() != null) {
            data.setMemberBankDetail(getEncryptionService().encryptMemberBankDetail(data.getMemberBankDetail()));
        }

//        Status
        data.setStatus(MemberStatus.SAVED.getStatus());
        data = getMemberDao().saveFpCoachEnrolmentData(data);
        return data;
    }

    @Override
    public FpCoachEnrolmentData updateFpCoachEnrolmentData(FpCoachEnrolmentSubmission submission, Boolean encrypted) {

        logger.info("Inside updateFpCoachEnrolmentData()");

        FpCoachEnrolmentData data = getMemberDao().getFpCoachEnrolmentDataById(submission.getId());
        data = FpCoachEnrolmentData.update(data, submission, false);

        boolean isCreditCard = data.getMemberCreditCard() != null;
        boolean isBankAccount = data.getMemberBankDetail() != null;

        try {
            if (encrypted) {
                if (isCreditCard) {
                    data.setMemberCreditCard(getEncryptionService().decryptCMemberCreditCard(data.getMemberCreditCard()));
                }

                if (isBankAccount) {
                    data.setMemberBankDetail(getEncryptionService().decryptMemberBankDetail(data.getMemberBankDetail()));
                }
            }

//        Handle Status
            if (!data.getStatus().equals(MemberStatus.SUCCESS.getStatus())) {

//            TODO: HANDLE ERROR SUBMISSIONS

                if (isCreditCard) {
                    data.setMemberCreditCard(getEncryptionService().encryptCMemberCreditCard(data.getMemberCreditCard()));
                }

                if (isBankAccount) {
                    data.setMemberBankDetail(getEncryptionService().encryptMemberBankDetail(data.getMemberBankDetail()));
                }

            } else {
                if (isCreditCard) {
                    data.setMemberCreditCard(getEncryptionService().cleanAndEncryptCreditCard(data.getMemberCreditCard()));
                }

                if (isBankAccount) {
                    data.setMemberBankDetail(getEncryptionService().cleanAndEncryptBankDetails(data.getMemberBankDetail()));
                }

                data.setStatus(MemberStatus.COMPLETE.getStatus());
            }


//        Update Database & Return Data
            data = getMemberDao().updateFpCoachEnrolmentData(data);

            if (encrypted == false) {
                if (isCreditCard) {
                    data.setMemberCreditCard(getEncryptionService().decryptCMemberCreditCard(data.getMemberCreditCard()));
                }

                if (isBankAccount) {
                    data.setMemberBankDetail(getEncryptionService().decryptMemberBankDetail(data.getMemberBankDetail()));
                }
            }

            return data;

        } catch (Exception e) {
            logger.error("Error updating FP Coach Data ID: {}\n{}",data.getId(),e.getMessage());
            return data;
        }
    }

    @Override
    public FpCoachEnrolmentData getFpCoachEnrolmentData(Long id, String UID, Boolean encrypted) {

        logger.info("Inside getFpCoachEnrolmentData()");

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Submission User");
            return null;
        }

        FpCoachEnrolmentData data = getMemberDao().getFpCoachEnrolmentDataById(id);

        if (data != null && encrypted == false) {
            if (data.getMemberCreditCard() != null) {
                data.setMemberCreditCard(getEncryptionService().decryptCMemberCreditCard(data.getMemberCreditCard()));
            }

            if (data.getMemberBankDetail() != null) {
                data.setMemberBankDetail(getEncryptionService().decryptMemberBankDetail(data.getMemberBankDetail()));
            }
        }

        if (data != null && encrypted == true) {
            if (data.getMemberCreditCard() != null) {
                data.setMemberCreditCard(getEncryptionService().decryptAndCleanMemberCreditCard(data.getMemberCreditCard()));
            }

            if (data.getMemberBankDetail() != null) {
                data.setMemberBankDetail(getEncryptionService().decryptAndCleanMemberBankDetail(data.getMemberBankDetail()));
            }
        }

        return data;
    }


    private boolean validateStaffId(String UID) {
        return getStaffDao().getStaffByFirebaseId(UID) != null;
    }


    public MindBodyService getMindBodyService() {
        return mindBodyService;
    }

    public void setMindBodyService(MindBodyService mindBodyService) {
        this.mindBodyService = mindBodyService;
    }

    public EncryptionService getEncryptionService() {
        return encryptionService;
    }


    @Autowired
    public void setEncryptionService(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    public MemberDao getMemberDao() {
        return memberDao;
    }

    @Autowired
    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public StaffDao getStaffDao() {
        return staffDao;
    }

    @Autowired
    public void setStaffDao(StaffDao staffDao) {
        this.staffDao = staffDao;
    }
}
