package com.fitnessplayground.service;

import com.fitnessplayground.dao.domain.temp.EnrolmentDataDocument;
import com.fitnessplayground.dao.domain.temp.PdfApiResponse;

import java.util.List;

public interface PdfWriterService {

    List<EnrolmentDataDocument> getEnrolments();
    EnrolmentDataDocument getEnrolment(String UID, Long id);
    void handlePdf(PdfApiResponse response);
    void testUploads();

}
