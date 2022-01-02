package com.fitnessplayground.service.impl;

import com.fitnessplayground.dao.GymDao;
import com.fitnessplayground.dao.MemberDao;
import com.fitnessplayground.dao.StaffDao;
import com.fitnessplayground.dao.domain.EnrolmentData;
import com.fitnessplayground.dao.domain.Gym;
import com.fitnessplayground.dao.domain.ReferralData;
import com.fitnessplayground.dao.domain.temp.EnrolmentDataSubmission;
import com.fitnessplayground.dao.domain.temp.FindEnrolment;
import com.fitnessplayground.dao.domain.temp.ReferralSubmission;
import com.fitnessplayground.service.EncryptionService;
import com.fitnessplayground.service.FpEnrolmentService;
import com.fitnessplayground.service.MemberStatus;
import com.fitnessplayground.service.MindBodyService;
import com.fitnessplayground.util.Helpers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Map;

@Service
public class FpEnrolmentServiceImpl implements FpEnrolmentService {


    private static final Logger logger = LoggerFactory.getLogger(FpEnrolmentServiceImpl.class);

    private EncryptionService encryptionService;
    private MindBodyService mindBodyService;
    private MemberDao memberDao;
    private StaffDao staffDao;
    private GymDao gymDao;

    @Override
    public List<EnrolmentData> findEnrolments(FindEnrolment findEnrolment) {

        List<EnrolmentData> enrolmentData = getMemberDao().findEnrolments(findEnrolment);
        return cleanAndSanitiseEnrolmentData(enrolmentData);
    }


    //    This is built for 3rd part form submissions such as Formstack
    @Override
    public EnrolmentData saveEnrolmentDataSubmission(EnrolmentDataSubmission enrolmentDataSubmission) {

        logger.info("Inside saveEnrolmentDataSubmission()");

        // Build EnrolmentData Entity
        EnrolmentData data = EnrolmentData.create(enrolmentDataSubmission);
        // Encrypt Data
        data = getEncryptionService().encryptDetails(data);
        data.setStatus(MemberStatus.SAVED.getStatus());
        // Save Data
        data = getMemberDao().saveEnrolmentData(data);
        // Return encrypted data

//        Update Promotions Hub
        try {
            Gym gym = getGymDao().getGymByLocationId(Integer.parseInt(data.getLocationId()));

            if (data.getCouponCode().contains("KeyFree")) {
                gym.setAccessKeyDiscountUsed(gym.getAccessKeyDiscountUsed() + 1);
            }

            if (data.getCouponCode().contains("NDaysFree")) {

                if (data.getDaysFree() != null) {
                    String[] daysFreeSplit = data.getDaysFree().split(" ");
                    Integer numDaysFree = Integer.parseInt(daysFreeSplit[1]);
                    gym.setFirstNDaysDiscountUsed(gym.getFirstNDaysDiscountUsed() + numDaysFree);
                }
            }

            getGymDao().save(gym);
        } catch (Exception ex) {
            logger.error("Error updating Promotions Hub: {}",ex.getMessage());
        }


        return data;
//        return null;
    }

    @Override
    public EnrolmentData updateEnrolmentData(EnrolmentDataSubmission submission, Boolean encrypted) {

        logger.info("\nInside updateEnrolmentData()");

        EnrolmentData data = getMemberDao().findEnrolmentDataById(submission.getId());

        if (data == null) {
            logger.error("No EnrolmentData entity exists");
            return null;
        }

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

//            Part 2 submission so new payment details need to be created
            boolean isNewPaymentDetails = submission.getStatus().equals(MemberStatus.ENROLMENT_AUTHORISED.getStatus());
            data = EnrolmentData.update(data, submission, isNewPaymentDetails);

            isCreditCard = data.getMemberCreditCard() != null;
            isBankAccount = data.getMemberBankDetail() != null;

//        Handle Status
            if (!data.getStatus().equals(MemberStatus.SUCCESS.getStatus())) {

//                logger.info("Encrypting Payment Details\n{}\n",data.toString());

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

//                Complete the submission indicating the sensitive details have been washed
                data.setStatus(MemberStatus.COMPLETE.getStatus());
            }

            data = getMemberDao().updateEnrolmentData(data);

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
            logger.error("Error updating Enrolment Data ID: {}\n{}",data.getId(),e.getMessage());
            return data;
        }

    }

    @Override
    public EnrolmentData getEnrolmentData(Long id, Boolean encrypted) {

        logger.info("Inside getEnrolmentData()");


        EnrolmentData data = getMemberDao().getEnrolmentDataById(id);

        if (data != null && encrypted == false) {
            if (data.getMemberCreditCard() != null) {
                data.setMemberCreditCard(getEncryptionService().decryptCMemberCreditCard(data.getMemberCreditCard()));
            }

            if (data.getMemberBankDetail() != null) {
                data.setMemberBankDetail(getEncryptionService().decryptMemberBankDetail(data.getMemberBankDetail()));
            }
        }

        return data;
    }

    @Async
    @Override
    public void saveReferral(ReferralSubmission submission) {
//        TODO: Implement Me

        EnrolmentData enrolmentData = getMemberDao().getEnrolmentDataById(submission.getEnrolmentDataId());

        if (enrolmentData != null) {

            ReferralData referralData = new ReferralData(
                    submission.getName(),
                    submission.getPhone(),
                    submission.getEmail(),
                    submission.getStatus(),
                    Helpers.getDateNow(),
                    enrolmentData
                    );

            getMemberDao().saveReferralData(referralData);
        }
    }

    @Async
    @Override
    public void updateReferral(ReferralSubmission submission) {
//        TODO: Implement Me

        ReferralData referralData = getMemberDao().getReferralDataById(submission.getId());

        if (referralData != null) {

            referralData.setName(submission.getName());
            referralData.setPhone(submission.getPhone());
            referralData.setEmail(submission.getEmail());
            referralData.setStatus(submission.getStatus());

            getMemberDao().saveReferralData(referralData);
        }
    }


    @Override
    public List<ReferralData> getReferrals(Long enrolmentDataId) {
        //        TODO: Implement Me
        return getMemberDao().getReferralsByEnrolmentDataId(enrolmentDataId);
    }


    private EnrolmentData cleanStaffNames(EnrolmentData e) {

        Map<String, String> staffMap = getMindBodyService().getAllStaffMap();

        if (e.getStaffMember() != null) {
            e.setStaffMember(staffMap.get(e.getStaffMember()));
        }

        if(e.getPersonalTrainer() != null) {
            e.setPersonalTrainer(staffMap.get(e.getPersonalTrainer()));
        }
        return e;
    }


    private List<EnrolmentData> cleanAndSanitiseEnrolmentData(List<EnrolmentData> enrolmentData) {

        for (EnrolmentData e: enrolmentData) {

            e = cleanStaffNames(e);

            e.setMemberCreditCard(null);
            e.setMemberBankDetail(null);
        }
        return enrolmentData;
    }


    public EncryptionService getEncryptionService() {
        return encryptionService;
    }

    @Autowired
    public void setEncryptionService(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    public MindBodyService getMindBodyService() {
        return mindBodyService;
    }

    @Autowired
    public void setMindBodyService(MindBodyService mindBodyService) {
        this.mindBodyService = mindBodyService;
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

    public GymDao getGymDao() {
        return gymDao;
    }

    @Autowired
    public void setGymDao(GymDao gymDao) {
        this.gymDao = gymDao;
    }
}
