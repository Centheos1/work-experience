package com.fitnessplayground.dao.domain;

import com.fitnessplayground.util.Helpers;

import javax.persistence.*;

@Entity
public class EnrolmentSubmissionError {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String status;
    private String errorDetails;
    private int errorCode;
    private String submissionDate;// varchar(255),
    private int mboSubmissionCount;
    private String staffMember;
    private String productId;
    private String staffName;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "enrolmentDataId")
    private EnrolmentData enrolmentData;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cancellationDataId")
    private CancellationData cancellationData;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "contractId")
    private MboContract contract;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "serviceId")
    private MboService service;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fpCoachEnrolmentDataId")
    private FpCoachEnrolmentData fpCoachEnrolmentData;



    public EnrolmentSubmissionError() {
        this.submissionDate = Helpers.getDateNow();
    }

//    public EnrolmentSubmissionError(String status, String errorDetails, int errorCode, String submissionDate, int mboSubmissionCount, String staffMember, String productId, EnrolmentData enrolmentData, CancellationData cancellationData, MboContract contract, MboService service) {
//        this.status = status;
//        this.errorDetails = errorDetails;
//        this.errorCode = errorCode;
//        this.submissionDate = submissionDate;
//        this.mboSubmissionCount = mboSubmissionCount;
//        this.staffMember = staffMember;
//        this.productId = productId;
//        this.enrolmentData = enrolmentData;
//        this.cancellationData = cancellationData;
//        this.contract = contract;
//        this.service = service;
//    }


    public EnrolmentSubmissionError(String status, String errorDetails, int errorCode, String submissionDate, int mboSubmissionCount, String staffMember, String productId, String staffName, EnrolmentData enrolmentData, CancellationData cancellationData, MboContract contract, MboService service, FpCoachEnrolmentData fpCoachEnrolmentData) {
        this.status = status;
        this.errorDetails = errorDetails;
        this.errorCode = errorCode;
        this.submissionDate = submissionDate;
        this.mboSubmissionCount = mboSubmissionCount;
        this.staffMember = staffMember;
        this.productId = productId;
        this.staffName = staffName;
        this.enrolmentData = enrolmentData;
        this.cancellationData = cancellationData;
        this.contract = contract;
        this.service = service;
        this.fpCoachEnrolmentData = fpCoachEnrolmentData;
    }

    public long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public int getMboSubmissionCount() {
        return mboSubmissionCount;
    }

    public void setMboSubmissionCount(int mboSubmissionCount) {
        this.mboSubmissionCount = mboSubmissionCount;
    }

    public String getStaffMember() {
        return staffMember;
    }

    public void setStaffMember(String staffMember) {
        this.staffMember = staffMember;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public EnrolmentData getEnrolmentData() {
        return enrolmentData;
    }

    public void setEnrolmentData(EnrolmentData enrolmentData) {
        this.enrolmentData = enrolmentData;
    }

    public CancellationData getCancellationData() {
        return cancellationData;
    }

    public void setCancellationData(CancellationData cancellationData) {
        this.cancellationData = cancellationData;
    }

    public MboContract getContract() {
        return contract;
    }

    public void setContract(MboContract contract) {
        this.contract = contract;
    }

    public MboService getService() {
        return service;
    }

    public void setService(MboService service) {
        this.service = service;
    }

    public FpCoachEnrolmentData getFpCoachEnrolmentData() {
        return fpCoachEnrolmentData;
    }

    public void setFpCoachEnrolmentData(FpCoachEnrolmentData fpCoachEnrolmentData) {
        this.fpCoachEnrolmentData = fpCoachEnrolmentData;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    @Override
    public String toString() {
        return "EnrolmentSubmissionError{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", errorDetails='" + errorDetails + '\'' +
                ", errorCode=" + errorCode +
                ", submissionDate='" + submissionDate + '\'' +
                ", mboSubmissionCount=" + mboSubmissionCount +
                ", staffMember='" + staffMember + '\'' +
                ", productId='" + productId + '\'' +
                ", staffName='" + staffName + '\'' +
                ", enrolmentData=" + enrolmentData +
                ", cancellationData=" + cancellationData +
                ", contract=" + contract +
                ", service=" + service +
                ", fpCoachEnrolmentData=" + fpCoachEnrolmentData +
                '}';
    }
}
