package com.fitnessplayground.service.impl;

import com.fitnessplayground.dao.MemberDao;
import com.fitnessplayground.dao.StaffDao;
import com.fitnessplayground.dao.domain.EnrolmentData;
import com.fitnessplayground.dao.domain.MemberTermsAndConditions;
import com.fitnessplayground.dao.domain.mboDto.File;
import com.fitnessplayground.dao.domain.mboDto.MboUploadDocumentRequest;
import com.fitnessplayground.dao.domain.temp.EnrolmentDataDocument;
import com.fitnessplayground.dao.domain.temp.PdfApiResponse;
import com.fitnessplayground.dao.domain.temp.PersonName;
import com.fitnessplayground.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PdfWriterServiceImpl implements PdfWriterService {

    private static final Logger logger = LoggerFactory.getLogger(PdfWriterServiceImpl.class);

//    @Autowired
    private MemberDao memberDao;

    private StaffDao staffDao;

//    @Autowired
    private EncryptionService encryptionService;

//    @Autowired
    private MindBodyService mindBodyService;

//    @Autowired
    private FitnessPlaygroundService fitnessPlaygroundService;

    @Override
    public List<EnrolmentDataDocument> getEnrolments() {

        List<EnrolmentDataDocument> dataDocuments = new ArrayList<>();
        List<EnrolmentData> enrolmentData = memberDao.getSuccessEnrolments();
        String termsHtml = memberDao.getTermsAndConditionsHtml().getTermsAndConditions();

        for (EnrolmentData e : enrolmentData) {
            e = encryptionService.decryptAndClean(e);

//            e.setStaffMember(mindBodyService.getNameFromMboIdAndLocationId(e.getStaffMember(), e.getLocationId()));
//            e.setPersonalTrainer(mindBodyService.getNameFromMboIdAndLocationId(e.getPersonalTrainer(), e.getLocationId()));
            PersonName name = mindBodyService.getNameFromUIEncoding(e.getStaffMember());
            e.setStaffMember(name == null ? null : name.getFirstName()+" "+name.getLastName());

            name = mindBodyService.getNameFromUIEncoding(e.getPersonalTrainer());
            e.setPersonalTrainer(name == null ? null : name.getFirstName()+" "+name.getLastName());

            EnrolmentDataDocument dataDocument = EnrolmentDataDocument.from(e);
            dataDocument.setTermAndConditions(termsHtml);
            dataDocuments.add(dataDocument);
        }
//        logger.info("pdfWriter getEnrolments: {}",termsHtml);
        return dataDocuments;
    }

    @Override
    public EnrolmentDataDocument getEnrolment(String UID, Long id) {

        if (!validateStaffId(UID)) {
            logger.error("Unauthorised Submission User");
            return null;
        }

        EnrolmentData enrolmentData = getMemberDao().getEnrolmentDataById(id);

        String termsHtml = getMemberDao().getTermsAndConditionsHtml().getTermsAndConditions();

        enrolmentData = getEncryptionService().decryptAndClean(enrolmentData);

        PersonName name = getMindBodyService().getNameFromUIEncoding(enrolmentData.getStaffMember());
        enrolmentData.setStaffMember(name == null ? null : name.getFirstName() + " " + name.getFirstName());

        name = getMindBodyService().getNameFromUIEncoding(enrolmentData.getPersonalTrainer());
        enrolmentData.setPersonalTrainer(name == null ? null : name.getFirstName() + " " + name.getFirstName());

        EnrolmentDataDocument dataDocument = EnrolmentDataDocument.from(enrolmentData);
        dataDocument.setTermAndConditions(termsHtml);

        return dataDocument;
    }

    @Override
    public void handlePdf(PdfApiResponse response) {

        logger.info("Inside handlePdf");

        if (response == null) { return; }

        EnrolmentData enrolmentData = memberDao.getEnrolmentDataById(Long.parseLong(response.getEnrolmentDataId()));

        if (enrolmentData == null) { return; }

        MemberTermsAndConditions conditions = MemberTermsAndConditions.from(response, enrolmentData);

        enrolmentData.setStatus(MemberStatus.PDF.getStatus());

        memberDao.saveMemberTermsAndConditions(conditions);
        memberDao.saveEnrolmentData(enrolmentData);

    }

    @Override
    public void testUploads() {
        List<MemberTermsAndConditions> list = memberDao.getMemberTermsAndConditionsList();
        if (list.isEmpty()) {
            logger.info("Empty List");
            return;
        }
        mindBodyService.processPdf(list.get(0));
    }

    private boolean validateStaffId(String UID) {
        return getStaffDao().getStaffByFirebaseId(UID) != null;
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

    public FitnessPlaygroundService getFitnessPlaygroundService() {
        return fitnessPlaygroundService;
    }

    @Autowired
    public void setFitnessPlaygroundService(FitnessPlaygroundService fitnessPlaygroundService) {
        this.fitnessPlaygroundService = fitnessPlaygroundService;
    }
}
