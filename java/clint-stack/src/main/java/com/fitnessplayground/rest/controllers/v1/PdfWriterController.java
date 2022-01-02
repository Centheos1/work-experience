package com.fitnessplayground.rest.controllers.v1;

import com.fitnessplayground.dao.domain.temp.EnrolmentDataDocument;
import com.fitnessplayground.dao.domain.temp.PdfApiResponse;
import com.fitnessplayground.service.PdfWriterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "${pdf.writer.origin}")
//@CrossOrigin(origins = "*")
@RequestMapping("/v1/pdf")
public class PdfWriterController {

    private static final Logger logger = LoggerFactory.getLogger(PdfWriterController.class);

    @Value("${fp.source.handshake.key}")
    private String HANDSHAKE_KEY;

    @Value("${fp.authorisation.header}")
    private String FP_AUTHORIZATION_HEADER;

//    @Autowired
    private PdfWriterService pdfWriterService;

    @RequestMapping(value = "getEnrolmentDocuments", method = RequestMethod.GET)
    public List<EnrolmentDataDocument> getEnrolments(@RequestParam String auth) {
//        logger.info("In pdfWriter getEnrolments **********");
        if (!auth.equals(HANDSHAKE_KEY)) return null;

        return pdfWriterService.getEnrolments();
    }


    @RequestMapping(value = "getEnrolmentDocument/{id}", method = RequestMethod.GET)
    public EnrolmentDataDocument getEnrolments(@RequestHeader Map<String, String> headers, @PathVariable Long id) {

        String UID = headers.get(FP_AUTHORIZATION_HEADER);

        logger.info("In pdfWriter getEnrolmentDocument/{}",id);

        return getPdfWriterService().getEnrolment(UID, id);
    }

    @RequestMapping(value = "addPdf", method = RequestMethod.POST)
    public void addPdf(@RequestParam String auth, @RequestBody PdfApiResponse response) {
        logger.info("In pdfWriter addPdf **********");
        if (!auth.equals(HANDSHAKE_KEY)) return;

        getPdfWriterService().handlePdf(response);
    }

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public void test(@RequestParam String auth) {
        logger.info("In pdfWriter getEnrolments **********");
        if (!auth.equals(HANDSHAKE_KEY)) return;

        getPdfWriterService().testUploads();
    }


    public PdfWriterService getPdfWriterService() {
        return pdfWriterService;
    }

    @Autowired
    public void setPdfWriterService(PdfWriterService pdfWriterService) {
        this.pdfWriterService = pdfWriterService;
    }
}
