package com.fitnessplayground.dao.domain;

import com.fitnessplayground.dao.domain.temp.PdfApiResponse;
import com.fitnessplayground.service.MemberStatus;

import javax.persistence.*;

@Entity
public class MemberTermsAndConditions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String fileName;
    private String pdfDocument;
    private String status;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "enrolmentDataId")
    private EnrolmentData enrolmentData;

    public MemberTermsAndConditions() {
    }

    public static MemberTermsAndConditions from(PdfApiResponse response, EnrolmentData enrolmentData) {
        MemberTermsAndConditions conditions = new MemberTermsAndConditions();

        conditions.setFileName(response.getFileName());
        conditions.setPdfDocument(response.getEncodedPdf());
        conditions.setEnrolmentData(enrolmentData);
        conditions.setStatus(MemberStatus.SAVED.getStatus());

        return conditions;
    }

    public long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPdfDocument() {
        return pdfDocument;
    }

    public void setPdfDocument(String pdfDocument) {
        this.pdfDocument = pdfDocument;
    }

    public EnrolmentData getEnrolmentData() {
        return enrolmentData;
    }

    public void setEnrolmentData(EnrolmentData enrolmentData) {
        this.enrolmentData = enrolmentData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MemberTermsAndConditions{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", pdfDocument='" + pdfDocument + '\'' +
                ", status='" + status + '\'' +
                ", enrolmentData=" + enrolmentData +
                '}';
    }
}
