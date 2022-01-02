package com.fitnessplayground.dao.impl;

import com.fitnessplayground.dao.MemberDao;
import com.fitnessplayground.dao.domain.*;
import com.fitnessplayground.dao.domain.temp.EnrolmentLookUp;
import com.fitnessplayground.dao.domain.temp.FindEnrolment;
import com.fitnessplayground.dao.domain.temp.SearchByPersDetails;
import com.fitnessplayground.dao.repository.*;
import com.fitnessplayground.exception.DatabaseException;
import com.fitnessplayground.util.GymName;
import com.fitnessplayground.util.Helpers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by micheal on 25/02/2017.
 */
@Repository
public class MemberDaoImpl implements MemberDao {

    private static final Logger logger = LoggerFactory.getLogger(MemberDaoImpl.class);

    private EnrolmentDataRepository enrolmentDataRepository;
    private MemberCreditCardRepository creditCardRepository;
    private TermsAndConditionsRepository termsAndConditionsRepository;
    private MemberTermsAndConditionsRepository memberTermsAndConditionsRepository;
    private FpCoachEnrolmentDataRepository fpCoachEnrolmentDataRepository;
    private CancellationDataRepository cancellationDataRepository;
    private LeadDataRepository leadDataRepository;
    private MembershipChangeDataRepository membershipChangeDataRepository;
    private FpAcademyRepository fpAcademyRepository;
    private PtFeedbackRepository ptFeedbackRepository;
    private ReferralDataRepository referralDataRepository;
    private ClassReviewRepository classReviewRepository;
    private WebReferralDataRepository webReferralDataRepository;
    private PreExDataRepository preExDataRepository;
    private ParqDataRepository parqDataRepository;
    private PtTrackerRepository ptTrackerRepository;
    private CloudSearchRepository cloudSearchRepository;
    private DebtPortalRepository debtPortalRepository;

    @Override
    public EnrolmentData saveEnrolmentData(EnrolmentData enrolmentData) {

//        boolean isDuplicate =

        try {

            if (enrolmentData.getFs_formId() != null && enrolmentData.getFs_uniqueId() != null) {

                EnrolmentData duplicate = getEnrolmentDataRepository().findByFormstackIds(enrolmentData.getFs_formId(), enrolmentData.getFs_uniqueId());

                if (duplicate != null) {
                    logger.error("Duplicate Submission FS Unique Id: {}", enrolmentData.getFs_uniqueId());
                    return null;
                }

                List<EnrolmentData> duplicates = getEnrolmentDataRepository().findByFormIdAndEmail(enrolmentData.getFs_formId(), enrolmentData.getEmail());

                if (!duplicates.isEmpty()) {
                    for (EnrolmentData d : duplicates) {
                        if (Helpers.compareTwoDates(d.getCreateDate(), enrolmentData.getCreateDate()) == 0) {
                            logger.error("Re-Submit Enrolment Formstack Submission FS Unique Id: [{}]", enrolmentData.getFs_uniqueId());
                            return null;
                        }
                    }
                }
            }

//            logger.info("About to save Member: [{}]", mboContract.getName());
            enrolmentData = getEnrolmentDataRepository().save(enrolmentData);
//            logger.info("Saved Client: [{}]", enrolmentData.getAccessKeyNumber());

        } catch (Exception ex) {
            String errorMsg = "Exception saving member: "+enrolmentData.getAccessKeyNumber();
            logger.error("Exception saving member: [{}] Error: [{}]", enrolmentData.getAccessKeyNumber(), ex.getMessage());
            throw new DatabaseException(errorMsg, ex);
        }

        return enrolmentData;
    }

    @Override
    public EnrolmentData findEnrolmentDataById(long id) {
        return getEnrolmentDataRepository().findOne(id);
    }

    @Override
    public Iterable<MemberCreditCard> getAllCreditCards() {
        return getCreditCardRepository().findAll();
    }

    @Override
    public MemberCreditCard save(MemberCreditCard memberCreditCard) {
        try {
            memberCreditCard = getCreditCardRepository().save(memberCreditCard);
//            logger.info("Saved Member: [{}]", memberCreditCard.getLocId());
        } catch (Exception ex) {
            String errorMsg = "Exception saving member: "+memberCreditCard.getId();
            logger.error("Exception saving member: [{}] Error: [{}]", memberCreditCard.getId(), ex.getMessage());
            throw new DatabaseException(errorMsg, ex);
        }
        return memberCreditCard;
    }

    @Override
    public Iterable<EnrolmentData> get100EnrolmentsByLocation(String location) {

//        logger.info("get100EnrolmentsByLocation: location = [{}]",location);

        if (location.equals("0")) {
            return getEnrolmentDataRepository().find100();
        } else {
            return getEnrolmentDataRepository().find100(location);
        }
    }

    @Override
    public Iterable<EnrolmentData> searchEnrolmentData(EnrolmentLookUp enrolmentLookUp) {
        String location = GymName.convertLocationId(enrolmentLookUp.getLocation().trim());
        String search = enrolmentLookUp.getSearch().trim();

        if (search.length() > 0 && search.charAt(0) == '0') {
            search = search.substring(1);
        }

        logger.info("searchEnrolmentData: location = [{}] | search = [{}]",location, search);

        if (location.equals("0")) {
//             return filterEnrolmentData(getEnrolmentDataRepository().findAll(), search);
            return getEnrolmentDataRepository().uiSearchEnrolments(search);
        } else {
//            return filterEnrolmentData(getEnrolmentDataRepository().findAllByLocation(location), search);
            return getEnrolmentDataRepository().uiSearchEnrolmentsLocation(search, location);
        }
    }


    @Override
    public List<EnrolmentData> findEnrolments(FindEnrolment findEnrolment) {
        return getEnrolmentDataRepository().findEnrolments(findEnrolment.getFirstName(), findEnrolment.getLastName(), findEnrolment.getEmail());
    }

    @Override
    public List<EnrolmentData> findEnrolmentsByEmail(String email) {
        return getEnrolmentDataRepository().findEnrolmentsByEmail(email);
    }

    @Override
    public List<EnrolmentData> searchEnrolmentDataByPersDetails(SearchByPersDetails persDetails) {
        return getEnrolmentDataRepository().searchByPersDetails(persDetails.getFirstName(), persDetails.getLastName(), persDetails.getEmail(), persDetails.getPhone());
    }

    @Override
    public EnrolmentData getEnrolmentDataByFormstackIds(String fsFormId, String fsUniqueId) {
        return getEnrolmentDataRepository().findByFormstackIds(fsFormId, fsUniqueId);
    }

    @Override
    public EnrolmentData getEnrolmentDataById(long id) {
        return getEnrolmentDataRepository().findOne(id);
    }

    @Override
    public EnrolmentData updateEnrolmentData(EnrolmentData enrolmentData) {
        logger.info("Committing EnrolmentData Update: {}",enrolmentData.getId());
        try {
            EnrolmentData tmp = getEnrolmentDataRepository().findOne(enrolmentData.getId());
            if (tmp != null) {
                enrolmentData = getEnrolmentDataRepository().save(enrolmentData);
            }
        } catch (Exception ex){
            logger.error("Error [updateEnrolmentData]: {}",ex.getMessage());
        }
        return enrolmentData;
    }

    @Override
    public TermsAndConditions getTermsAndConditionsHtml() {
        return getTermsAndConditionsRepository().getHtml();
    }

    @Override
    public List<EnrolmentData> getProcessingEnrolments() {
        return getEnrolmentDataRepository().getUnprocessed();
    }

    @Override
    public List<EnrolmentData> getSuccessEnrolments() {
        return getEnrolmentDataRepository().getSuccessEnrolments();
    }

    @Override
    public List<EnrolmentData> getSavedEnrolments() {
        return getEnrolmentDataRepository().getSaved();
    }

    @Override
    public List<EnrolmentData> getEmailCampaignPendingEnrolments() {
        return getEnrolmentDataRepository().getEmailCampaingPendingEnrolments();
    }

    @Override
    public List<EnrolmentData> getInternalCommsMCNotesEnrolments() {
        return getEnrolmentDataRepository().getInternalCommsMCNotesEnrolments();
    }

    @Override
    public MemberTermsAndConditions saveMemberTermsAndConditions(MemberTermsAndConditions termsAndConditions) {

        try {
            termsAndConditions = getMemberTermsAndConditionsRepository().save(termsAndConditions);
//            logger.info("Saved Member Terms: [{}]", termsAndConditions.getEnrolmentData().getAccessKeyNumber());
        } catch (Exception ex) {
            String errorMsg = "Exception saving member: "+termsAndConditions.getEnrolmentData().getAccessKeyNumber();
            logger.error("Exception saving member: [{}] Error: [{}]",termsAndConditions.getEnrolmentData().getAccessKeyNumber(), ex.getMessage());
            throw new DatabaseException(errorMsg, ex);
        }

        return termsAndConditions;
    }

    @Override
    public List<MemberTermsAndConditions> getMemberTermsAndConditionsList() {
        return getMemberTermsAndConditionsRepository().getUnprocessed();
    }

    @Override
    public List<FpCoachEnrolmentData> findCoachEnrolments(FindEnrolment findEnrolment) {
        return getFpCoachEnrolmentDataRepository().findEnrolments(findEnrolment.getFirstName(), findEnrolment.getLastName(), findEnrolment.getEmail());
    }

    @Override
    public FpCoachEnrolmentData saveFpCoachEnrolmentData(FpCoachEnrolmentData fpCoachEnrolmentData) {

        try {
            logger.info("About to save FP Coach Submission: [{}]", fpCoachEnrolmentData.getFs_uniqueId());
//            Check Duplicate Formstack Submission
            if (fpCoachEnrolmentData.getFs_formId() != null && fpCoachEnrolmentData.getFs_uniqueId() != null) {

                // TODO: 22/05/20 TEST ME
                FpCoachEnrolmentData duplicate = getFpCoachEnrolmentDataRepository().findByFormstackIds(fpCoachEnrolmentData.getFs_formId(), fpCoachEnrolmentData.getFs_uniqueId());

                if (duplicate != null) {
                    logger.error("Duplicate Submission FS Unique Id: {}", fpCoachEnrolmentData.getFs_uniqueId());
                    return null;
                }

                List<FpCoachEnrolmentData> duplicates = getFpCoachEnrolmentDataRepository().findByFormIdAndEmail(fpCoachEnrolmentData.getFs_formId(), fpCoachEnrolmentData.getEmail());

                logger.info("saveFpCoachEnrolmentData Duplicates Size: [{}]", duplicates.size());

                if (!duplicates.isEmpty()) {
                    for (FpCoachEnrolmentData d : duplicates) {
                        if (Helpers.compareTwoDates(d.getCreateDate(), fpCoachEnrolmentData.getCreateDate()) == 0) {
                            logger.error("Re-Submit FP Coach Formstack Submission FS Unique Id: [{}]", fpCoachEnrolmentData.getFs_uniqueId());
                            return null;
                        }
                    }
                }
            }

            fpCoachEnrolmentData = getFpCoachEnrolmentDataRepository().save(fpCoachEnrolmentData);

        } catch (Exception ex) {
            String errorMsg = "Exception saving member: "+fpCoachEnrolmentData.getFs_uniqueId();
            logger.error("Exception saving member: [{}] Error: [{}]", fpCoachEnrolmentData.getFs_uniqueId(), ex.getMessage());
            throw new DatabaseException(errorMsg, ex);
        }

        return fpCoachEnrolmentData;
    }

    @Override
    public FpCoachEnrolmentData updateFpCoachEnrolmentData(FpCoachEnrolmentData fpCoachEnrolmentData) {
        FpCoachEnrolmentData tmp = getFpCoachEnrolmentDataRepository().findOne(fpCoachEnrolmentData.getId());
        if (tmp != null) {
//            tmp = fpCoachEnrolmentData;
            fpCoachEnrolmentData = getFpCoachEnrolmentDataRepository().save(fpCoachEnrolmentData);
        }
        return fpCoachEnrolmentData;
    }

    @Override
    public FpCoachEnrolmentData getFpCoachEnrolmentDataById(Long id) {

        return getFpCoachEnrolmentDataRepository().findOne(id);
    }

    @Override
    public LeadData saveLeadData(LeadData leadData) {
        try {
            leadData = getLeadDataRepository().save(leadData);
//            logger.info("Saved Member: [{}]", memberCreditCard.getLocId());
        } catch (Exception ex) {
            String errorMsg = "Exception saving member: "+leadData.getId();
            logger.error("Exception saving member: [{}] Error: [{}]", leadData.getId(), ex.getMessage());
            throw new DatabaseException(errorMsg, ex);
        }
        return leadData;
    }

    @Override
    public LeadData getLeadDataById(Long id) {
        return getLeadDataRepository().findOne(id);
    }

    @Override
    public CancellationData saveCancellationData(CancellationData cancellationData) {

        try {

            if (cancellationData.getFs_formId() != null && cancellationData.getFs_uniqueId() != null) {

                CancellationData duplicate = getCancellationDataRepository().findByFormstackIds(cancellationData.getFs_formId(), cancellationData.getFs_uniqueId());

                if (duplicate != null) {
                    logger.error("Duplicate Cancellation Request Submission FS Form Id: {} FS Unique Id: {}", cancellationData.getFs_formId(), cancellationData.getFs_uniqueId());
                    return null;
                }

                List<CancellationData> duplicates = getCancellationDataRepository().findByFormIdAndEmail(cancellationData.getFs_formId(), cancellationData.getEmail());

                logger.info("saveCancellationtData Duplicates Size: [{}]", duplicates.size());

                if (!duplicates.isEmpty()) {
                    for (CancellationData d : duplicates) {
                        if (Helpers.compareTwoDates(d.getCreateDate(), cancellationData.getCreateDate()) == 0) {
                            logger.error("Re-Submit Cancellation Request Formstack Submission FS Form Id: {}  FS Unique Id: [{}]", cancellationData.getFs_formId(), cancellationData.getFs_uniqueId());
                            return null;
                        }
                    }
                }

            }

            if (cancellationData.getCancellationDate() != null && cancellationData.getCancellationDate().isEmpty()) {
                cancellationData.setCancellationDate(null);
            }

            if (cancellationData.getCancellationLastDebitDate() != null && cancellationData.getCancellationLastDebitDate().isEmpty()) {
                cancellationData.setCancellationLastDebitDate(null);
            }

            if (cancellationData.getPtCancellationDate() != null && cancellationData.getPtCancellationDate().isEmpty()) {
                cancellationData.setPtCancellationDate(null);
            }

            if (cancellationData.getPtSaveSuspensionFromDate() != null && cancellationData.getPtSaveSuspensionFromDate().isEmpty()) {
                cancellationData.setPtSaveSuspensionFromDate(null);
            }

            if (cancellationData.getPtSaveSuspensionToDate() != null && cancellationData.getPtSaveSuspensionToDate().isEmpty()) {
                cancellationData.setPtSaveSuspensionToDate(null);
            }

            if (cancellationData.getPtSaveSuspensionNextDebitDate() != null && cancellationData.getPtSaveSuspensionNextDebitDate().isEmpty()) {
                cancellationData.setPtSaveSuspensionNextDebitDate(null);
            }

            if (cancellationData.getPtLastDebitDate() != null && cancellationData.getPtLastDebitDate().isEmpty()) {
                cancellationData.setPtLastDebitDate(null);
            }

            cancellationData = getCancellationDataRepository().save(cancellationData);

        } catch (Exception ex) {
            String errorMsg = "Exception saving cancellationData: "+cancellationData.getId();
            logger.error("Exception saving cancellationData: [{}] Error [{}]",cancellationData.getId(),ex.getMessage());
            throw new DatabaseException(errorMsg,ex);
        }
        return cancellationData;
    }

    @Override
    public CancellationData getCancellationDataByFormstackIds(String fsFormId, String fsUniqueId) {
        return getCancellationDataRepository().findByFormstackIds(fsFormId, fsUniqueId);
    }

    @Override
    public CancellationData getCancellationDataByFsUniqueIdAndLastName(String lastName, String fsUniqueId) {
        return getCancellationDataRepository().findFsUniqueIdAndLastName(fsUniqueId, lastName);
    }

    @Override
    public CancellationData updateCancellationData(CancellationData cancellationData) {
        CancellationData tmp = getCancellationDataRepository().findOne(cancellationData.getId());
        if (tmp != null) {
            cancellationData = getCancellationDataRepository().save(cancellationData);
        }
        return cancellationData;
    }

    @Override
    public ArrayList<CancellationData> getCancellationsByStatus(String status) {

        logger.info("getCancellationsByStatus  {}",status);

        return getCancellationDataRepository().findCancellationsByStatus(status);
    }

    @Override
    public CancellationData getCancellationDataById(Long id) {
        return getCancellationDataRepository().findOne(id);
    }

    @Override
    public MembershipChangeData saveMembershipChangeData(MembershipChangeData membershipChangeData) {

        try {

            if (membershipChangeData.getFs_formId() != null && membershipChangeData.getFs_uniqueId() != null) {

                MembershipChangeData duplicate = getMembershipChangeDataRepository().findByFormstackIds(membershipChangeData.getFs_formId(), membershipChangeData.getFs_uniqueId());

                if (duplicate != null) {
                    logger.error("Duplicate Membership Change Submission FS Form Id: {} FS Unique Id: {}", membershipChangeData.getFs_formId(), membershipChangeData.getFs_uniqueId());
                    return null;
                }

                List<MembershipChangeData> duplicates = getMembershipChangeDataRepository().findByFormIdAndEmail(membershipChangeData.getFs_formId(), membershipChangeData.getEmail());

                logger.info("saveMembershipChangeData Duplicates Size: [{}]", duplicates.size());

                if (!duplicates.isEmpty()) {
                    for (MembershipChangeData d : duplicates) {
                        if (Helpers.compareTwoDates(d.getCreateDate(), membershipChangeData.getCreateDate()) == 0) {
                            logger.error("Re-Submit Membership Change Formstack Submission FS Form Id: {} FS Unique Id: [{}]", membershipChangeData.getFs_formId(), membershipChangeData.getFs_uniqueId());
                            return null;
                        }
                    }
                }

                membershipChangeData = getMembershipChangeDataRepository().save(membershipChangeData);

            }
        } catch (Exception ex) {
            String errorMsg = "Exception saving membershipChangeData: "+membershipChangeData.getId();
            logger.error("Exception saving membershipChangeData: [{}] Error [{}]",membershipChangeData.getId(),ex.getMessage());
            throw new DatabaseException(errorMsg,ex);
        }

        return membershipChangeData;
    }

    @Override
    public MembershipChangeData getMembershipChangeDataByFormstackIds(String fsFormId, String fsUniqueId) {
        return getMembershipChangeDataRepository().findByFormstackIds(fsFormId, fsUniqueId);
    }

    @Override
    public MembershipChangeData updateMembershipChangeData(MembershipChangeData membershipChangeData) {
        MembershipChangeData tmp = getMembershipChangeDataRepository().findOne(membershipChangeData.getId());
        if (tmp != null) {
            membershipChangeData = getMembershipChangeDataRepository().save(membershipChangeData);
        }
        return membershipChangeData;
    }

    @Override
    public ArrayList<MembershipChangeData> getMembershipChangeDataByStatus(String status) {
        logger.info("getMembershipChangeByStatus  {}",status);

        return getMembershipChangeDataRepository().findByStatus(status);
    }

    @Override
    public MembershipChangeData getMembershipChangeDataById(Long id) {
        return getMembershipChangeDataRepository().findOne(id);
    }

    @Override
    public EnrolmentData getEnrolmentDataByFindEnrolment(FindEnrolment findEnrolment) {

        try {
            List<EnrolmentData> enrolmentData = getEnrolmentDataRepository().findEnrolments(findEnrolment.getFirstName(), findEnrolment.getLastName(), findEnrolment.getEmail());

            logger.info("Found {} Enrolments", enrolmentData.size());

            if (enrolmentData.size() == 1) {
                return enrolmentData.get(0);
            }
        }catch (Exception ex) {
            logger.error("Error getEnrolmentDataByFindEnrolment: {} {}",findEnrolment.toString(), ex.getMessage());
        }

        return null;
    }

    @Override
    public FpAcademyEnrolmentData saveFpAcademyEnrolmentData(FpAcademyEnrolmentData fpAcademyEnrolmentData) {

        try {

            if (fpAcademyEnrolmentData.getFs_formId() != null && fpAcademyEnrolmentData.getFs_uniqueId() != null) {

                FpAcademyEnrolmentData duplicate = getFpAcademyRepository().findByFormstackIds(fpAcademyEnrolmentData.getFs_formId(), fpAcademyEnrolmentData.getFs_uniqueId());

                if (duplicate != null) {
                    logger.error("Duplicate FP Academy Enrolment Submission FS Form Id: {} FS Unique Id: {}", fpAcademyEnrolmentData.getFs_formId(), fpAcademyEnrolmentData.getFs_uniqueId());
                    return null;
                }

                List<MembershipChangeData> duplicates = getMembershipChangeDataRepository().findByFormIdAndEmail(fpAcademyEnrolmentData.getFs_formId(), fpAcademyEnrolmentData.getEmail());

                logger.info("saveFpAcademyEnrolmentData Duplicates Size: [{}]", duplicates.size());

                if (!duplicates.isEmpty()) {
                    for (MembershipChangeData d : duplicates) {
                        if (Helpers.compareTwoDates(d.getCreateDate(), fpAcademyEnrolmentData.getCreateDate()) == 0) {
                            logger.error("Re-Submit FP Academy Enrolment Formstack Submission FS Form Id: {} FS Unique Id: [{}]", fpAcademyEnrolmentData.getFs_formId(), fpAcademyEnrolmentData.getFs_uniqueId());
                            return null;
                        }
                    }
                }

                fpAcademyEnrolmentData = getFpAcademyRepository().save(fpAcademyEnrolmentData);

            }
        } catch (Exception ex) {
            String errorMsg = "Exception saving fpAcademyEnrolmentData: "+fpAcademyEnrolmentData.getId();
            logger.error("Exception saving fpAcademyEnrolmentData: [{}] Error [{}]",fpAcademyEnrolmentData.getId(),ex.getMessage());
            throw new DatabaseException(errorMsg,ex);
        }

        return fpAcademyEnrolmentData;
    }

    @Override
    public FpAcademyEnrolmentData getFpAcademyDataById(Long id) {
        return getFpAcademyRepository().findOne(id);
    }

    @Override
    public FpAcademyEnrolmentData getFpAcademyEnrolmentDataByFormstackIds(String fsFormId, String fsUniqueId) {

        return getFpAcademyRepository().findByFormstackIds(fsFormId,fsUniqueId);
    }

    @Override
    public PtFeedbackData savePtFeedbackData(PtFeedbackData ptFeedbackData) {
        return getPtFeedbackRepository().save(ptFeedbackData);
    }

    @Override
    public ArrayList<PtFeedbackData> getPtFeedbackData() {
        return getPtFeedbackRepository().getLast30Days();
    }

    @Override
    public ArrayList<CancellationData> getPtCancellationData() {
        return getCancellationDataRepository().getLast30DaysPt();
    }

    @Override
    public ReferralData getReferralDataById(Long id) {
        return getReferralDataRepository().findOne(id);
    }

    @Override
    public ReferralData saveReferralData(ReferralData referralData) {
        return getReferralDataRepository().save(referralData);
    }

    @Override
    public List<ReferralData> getReferralsByEnrolmentDataId(Long enrolmentDataId) {
        return getReferralDataRepository().getReferralsByEnrolmentDataId(enrolmentDataId);
    }

    @Override
    public ClassReviewData saveClassReviewData(ClassReviewData classReviewData) {
        return getClassReviewRepository().save(classReviewData);
    }

    @Override
    public WebReferralData saveWebReferralData(WebReferralData webReferralData) {
        return getWebReferralDataRepository().save(webReferralData);
    }

    @Override
    public WebReferralData searchWebReferrals(String firstName, String lastName, String phone) {
        try {
            Iterable<WebReferralData> referrals = getWebReferralDataRepository().findAll();

            logger.info("Search WebReferralData for firstName: {}, phone: {}",firstName,phone);

            WebReferralData data = null;
            for (WebReferralData r : referrals) {

                if (r.getReferralOneFirstName() != null && r.getReferralOneFirstName().equalsIgnoreCase(firstName)
                        && r.getReferralOnePhone() != null && phone.equals(Helpers.cleanPhoneNumber(r.getReferralOnePhone())) ) {
                    data = r;
                }

                if (data == null && r.getReferralTwoFirstName() != null && r.getReferralTwoFirstName().equalsIgnoreCase(firstName)
                        && r.getReferralTwoPhone() != null && phone.equals(Helpers.cleanPhoneNumber(r.getReferralTwoPhone()))) {
                    data = r;
                }

                if (data == null && r.getReferralThreeFirstName() != null && r.getReferralThreeFirstName().equalsIgnoreCase(firstName)
                        && r.getReferralThreePhone() != null && phone.equals(Helpers.cleanPhoneNumber(r.getReferralThreePhone()))) {
                    data = r;
                }
            }
            return data;

        } catch (Exception ex) {
            logger.error("Error Searching for WebReferralData: {}",ex.getMessage());
        }
        return null;
    }

    @Override
    public PreExData savePreExData(PreExData preExData) {
        return getPreExDataRepository().save(preExData);
    }

    @Override
    public PreExData getPreExDataById(Long id) {
        return getPreExDataRepository().findOne(id);
    }

    @Override
    public PreExData searchPreExData(String firstName, String lastName, String phone, String email) {

        ArrayList<PreExData> preExs = getPreExDataRepository().searchPreExData(firstName, lastName, phone, email);

        if (preExs.size() > 0) {
            return preExs.get(preExs.size() - 1);
        }

        return null;
    }

    @Override
    public ParqData saveParqData(ParqData parqData) {
        return getParqDataRepository().save(parqData);
    }

    @Override
    public ParqData searchParqData(String firstName, String lastName, String phone, String email) {

        ArrayList<ParqData> parqs = getParqDataRepository().searchParqData(firstName, lastName, phone, email);

        if (parqs.size() > 0) {
            return parqs.get(parqs.size() - 1);
        }

        return null;
    }

    @Override
    public ArrayList<PtTracker> getPtTrackerByStatus(String status) {
        return getPtTrackerRepository().findByStatus(status);
    }

    @Override
    public ArrayList<PtTracker> getPtTrackerBySessionCount(Integer sessionCount) {
        return getPtTrackerRepository().findBySessionCount(sessionCount);
    }

    @Override
    public ArrayList<PtTracker> getPtTrackerByPersonalTrainer(String personalTrainer) {
        return getPtTrackerRepository().findByPersonalTrainer(personalTrainer);
    }

    @Override
    public ArrayList<PtTracker> getPtTrackerByNoFirstSession() {
        return getPtTrackerRepository().getPtTrackerByNoFirstSession();
    }

    @Override
    public ArrayList<EnrolmentData> getMtdEnrolments() {
        LocalDate todaydate = LocalDate.now();
        String fom = todaydate.withDayOfMonth(1).toString();

        return getEnrolmentDataRepository().getEnrolmentsFromDate(fom);
    }

    @Override
    public ArrayList<CancellationData> getMtdCancellations() {
        LocalDate todaydate = LocalDate.now();
        String fom = todaydate.withDayOfMonth(1).toString();

        return getCancellationDataRepository().getMtdCancellationsFromDate(fom);
    }

    @Override
    public ArrayList<PreExData> getMtdPreExs() {
        LocalDate todaydate = LocalDate.now();
        String fom = todaydate.withDayOfMonth(1).toString();

        return getPreExDataRepository().getMtdPreExsFromDate(fom);
    }

    @Override
    public ArrayList<FpCoachEnrolmentData> getMtdFpCoachEnrolments() {
        LocalDate todaydate = LocalDate.now();
        String fom = todaydate.withDayOfMonth(1).toString();

        return getFpCoachEnrolmentDataRepository().getMtdFpCoachEnrolmentsFromDate(fom);
    }

    @Override
    public Iterable<CloudSearch> addCloudSearchMembers(ArrayList<CloudSearch> members) {
        for (CloudSearch m : members) {
            getCloudSearchRepository().save(m);
        }
        return getCloudSearchRepository().findAll();
    }

    @Override
    public Iterable<CloudSearch> getAllCloudSearch() {
        return getCloudSearchRepository().findAll();
    }

    @Override
    public Iterable<DebtPortal> getAllDebtPortal() {
        return getDebtPortalRepository().findAll();
    }

    @Override
    public ArrayList<DebtPortal> getAllCurrentDebtPortal() {
        return getDebtPortalRepository().findAllCurrent();
    }

    @Override
    public ArrayList<DebtPortal> getDebtPortalCommsList() {
        return getDebtPortalRepository().getCommsList();
    }

    @Override
    public DebtPortal getDebtPortalById(Long id) {
        return getDebtPortalRepository().findOne(id);
    }

    @Override
    public DebtPortal saveDebtPortal(DebtPortal debtPortal) {
        return getDebtPortalRepository().save(debtPortal);
    }

//    @Override
//    public DebtPortal updateDebtPortal(DebtPortal debtPortal) {
//        return null;
//    }

    @Override
    public PtTracker savePtTracker(PtTracker ptTracker) {
        return getPtTrackerRepository().save(ptTracker);
    }

    @Override
    public PtTracker updatePtTracker(PtTracker ptTracker) {
        PtTracker tmp = getPtTrackerRepository().findOne(ptTracker.getId());
        if (tmp != null) {
            ptTracker.setUpdateDate(Helpers.getDateNow());
            return getPtTrackerRepository().save(ptTracker);
        }
        return ptTracker;
    }

    @Override
    public PtTracker getPtTrackerById(Long id) {
        return getPtTrackerRepository().findOne(id);
    }

    @Override
    public PtTracker findPtTrackerByEnrolmentDataId(Long enrolmentDataId) {
        return getPtTrackerRepository().findByEnrolmentDataId(enrolmentDataId);
    }


    private ArrayList<EnrolmentData> filterEnrolmentData(Iterable<EnrolmentData> all, String search) {

        ArrayList<EnrolmentData> returnData = new ArrayList<>();

        for (EnrolmentData e : all) {
            if (e.getFirstName().contains(search)
                    || e.getLastName().contains(search)
                    || e.getEmail().contains(search)
                    || e.getPhone().contains(search)
                    || e.getAccessKeyNumber().contains(search)) {
                returnData.add(e);
            }
        }

        return returnData;
    }


    public EnrolmentDataRepository getEnrolmentDataRepository() {
        return enrolmentDataRepository;
    }

    @Autowired
    public void setEnrolmentDataRepository(EnrolmentDataRepository enrolmentDataRepository) {
        this.enrolmentDataRepository = enrolmentDataRepository;
    }

    public MemberCreditCardRepository getCreditCardRepository() {
        return creditCardRepository;
    }

    @Autowired
    public void setCreditCardRepository(MemberCreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    public TermsAndConditionsRepository getTermsAndConditionsRepository() {
        return termsAndConditionsRepository;
    }

    @Autowired
    public void setTermsAndConditionsRepository(TermsAndConditionsRepository termsAndConditionsRepository) {
        this.termsAndConditionsRepository = termsAndConditionsRepository;
    }

    public MemberTermsAndConditionsRepository getMemberTermsAndConditionsRepository() {
        return memberTermsAndConditionsRepository;
    }

    @Autowired
    public void setMemberTermsAndConditionsRepository(MemberTermsAndConditionsRepository memberTermsAndConditionsRepository) {
        this.memberTermsAndConditionsRepository = memberTermsAndConditionsRepository;
    }

    public FpCoachEnrolmentDataRepository getFpCoachEnrolmentDataRepository() {
        return fpCoachEnrolmentDataRepository;
    }

    @Autowired
    public void setFpCoachEnrolmentDataRepository(FpCoachEnrolmentDataRepository fpCoachEnrolmentDataRepository) {
        this.fpCoachEnrolmentDataRepository = fpCoachEnrolmentDataRepository;
    }

    public CancellationDataRepository getCancellationDataRepository() {
        return cancellationDataRepository;
    }

    @Autowired
    public void setCancellationDataRepository(CancellationDataRepository cancellationDataRepository) {
        this.cancellationDataRepository = cancellationDataRepository;
    }

    public LeadDataRepository getLeadDataRepository() {
        return leadDataRepository;
    }

    @Autowired
    public void setLeadDataRepository(LeadDataRepository leadDataRepository) {
        this.leadDataRepository = leadDataRepository;
    }

    public MembershipChangeDataRepository getMembershipChangeDataRepository() {
        return membershipChangeDataRepository;
    }

    @Autowired
    public void setMembershipChangeDataRepository(MembershipChangeDataRepository membershipChangeDataRepository) {
        this.membershipChangeDataRepository = membershipChangeDataRepository;
    }

    public FpAcademyRepository getFpAcademyRepository() {
        return fpAcademyRepository;
    }

    @Autowired
    public void setFpAcademyRepository(FpAcademyRepository fpAcademyRepository) {
        this.fpAcademyRepository = fpAcademyRepository;
    }

    public PtFeedbackRepository getPtFeedbackRepository() {
        return ptFeedbackRepository;
    }

    @Autowired
    public void setPtFeedbackRepository(PtFeedbackRepository ptFeedbackRepository) {
        this.ptFeedbackRepository = ptFeedbackRepository;
    }

    public ReferralDataRepository getReferralDataRepository() {
        return referralDataRepository;
    }

    @Autowired
    public void setReferralDataRepository(ReferralDataRepository referralDataRepository) {
        this.referralDataRepository = referralDataRepository;
    }

    public ClassReviewRepository getClassReviewRepository() {
        return classReviewRepository;
    }

    @Autowired
    public void setClassReviewRepository(ClassReviewRepository classReviewRepository) {
        this.classReviewRepository = classReviewRepository;
    }

    public WebReferralDataRepository getWebReferralDataRepository() {
        return webReferralDataRepository;
    }

    @Autowired
    public void setWebReferralDataRepository(WebReferralDataRepository webReferralDataRepository) {
        this.webReferralDataRepository = webReferralDataRepository;
    }

    public PreExDataRepository getPreExDataRepository() {
        return preExDataRepository;
    }

    @Autowired
    public void setPreExDataRepository(PreExDataRepository preExDataRepository) {
        this.preExDataRepository = preExDataRepository;
    }

    public ParqDataRepository getParqDataRepository() {
        return parqDataRepository;
    }

    @Autowired
    public void setParqDataRepository(ParqDataRepository parqDataRepository) {
        this.parqDataRepository = parqDataRepository;
    }

    public PtTrackerRepository getPtTrackerRepository() {
        return ptTrackerRepository;
    }

    @Autowired
    public void setPtTrackerRepository(PtTrackerRepository ptTrackerRepository) {
        this.ptTrackerRepository = ptTrackerRepository;
    }

    public CloudSearchRepository getCloudSearchRepository() {
        return cloudSearchRepository;
    }

    @Autowired
    public void setCloudSearchRepository(CloudSearchRepository cloudSearchRepository) {
        this.cloudSearchRepository = cloudSearchRepository;
    }

    public DebtPortalRepository getDebtPortalRepository() {
        return debtPortalRepository;
    }

    @Autowired
    public void setDebtPortalRepository(DebtPortalRepository debtPortalRepository) {
        this.debtPortalRepository = debtPortalRepository;
    }
}
