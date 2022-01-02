package com.fitnessplayground.service.impl;

import com.fitnessplayground.dao.MemberDao;
import com.fitnessplayground.dao.domain.FpAcademyEnrolmentData;
import com.fitnessplayground.dao.domain.temp.FpAcademyEnrolmentSubmission;
import com.fitnessplayground.service.EncryptionService;
import com.fitnessplayground.service.FpAcademyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FpAcademyServiceImpl implements FpAcademyService {

    private static final Logger logger = LoggerFactory.getLogger(FpAcademyServiceImpl.class);

    private MemberDao memberDao;
    private EncryptionService encryptionService;

    @Override
    public FpAcademyEnrolmentData saveFpAcademyEnrolmentData(FpAcademyEnrolmentSubmission fpAcademyEnrolmentSubmission) {

        try {
            logger.info("Inside saveEnrolmentDataSubmission()");

            // Build EnrolmentData Entity
            FpAcademyEnrolmentData data = FpAcademyEnrolmentData.create(fpAcademyEnrolmentSubmission);
            // Encrypt Data
            data.setCreditCard(getEncryptionService().encryptCMemberCreditCard(data.getCreditCard()));
            // Save Data
            data = getMemberDao().saveFpAcademyEnrolmentData(data);
            // Return encrypted data
            return data;
//        return null;
        } catch (Exception ex) {
            logger.error("Error Saving FP Academy Enrolment: {}",ex.getMessage());
            return null;
        }
    }

    @Override
    public FpAcademyEnrolmentData updateFpAcademyEnrolmentData(FpAcademyEnrolmentSubmission submission, Boolean encrypted, Boolean isNewPaymentDetails) {

        try {
            FpAcademyEnrolmentData data;
            if (submission.getId() != null) {
                data = getMemberDao().getFpAcademyDataById(submission.getId());
            } else {
                return null;
            }

            if (data != null) {
                data = FpAcademyEnrolmentData.update(data, submission, isNewPaymentDetails);
            }

            // Encrypt Data
            if (isNewPaymentDetails) {
                data.setCreditCard(getEncryptionService().encryptCMemberCreditCard(data.getCreditCard()));
            }
            // Save Data
            data = getMemberDao().saveFpAcademyEnrolmentData(data);

            if (!encrypted) {
                data.setCreditCard(getEncryptionService().decryptCMemberCreditCard(data.getCreditCard()));
            }

            return data;
        } catch (Exception ex) {
            logger.error("Error Updating FP Academy Enrolment: {}",ex.getMessage());
            return null;
        }
    }

    @Override
    public FpAcademyEnrolmentData getFpAcademyEnrolmentData(Long id, Boolean encrypted) {

        try {
            FpAcademyEnrolmentData data = getMemberDao().getFpAcademyDataById(id);

            if (!encrypted) {
                data.setCreditCard(getEncryptionService().decryptCMemberCreditCard(data.getCreditCard()));
            }

            return data;
        } catch (Exception ex) {
            logger.error("Error Getting FP Academy Enrolment: {} {}",id,ex.getMessage());
            return null;
        }
    }

    @Override
    public FpAcademyEnrolmentData getPhoneFpAcademyEnrolmentData(String fsFormId, String fsUniqueId) {
        return getMemberDao().getFpAcademyEnrolmentDataByFormstackIds(fsFormId,fsUniqueId);
    }

    public MemberDao getMemberDao() {
        return memberDao;
    }

    @Autowired
    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public EncryptionService getEncryptionService() {
        return encryptionService;
    }

    @Autowired
    public void setEncryptionService(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }
}
