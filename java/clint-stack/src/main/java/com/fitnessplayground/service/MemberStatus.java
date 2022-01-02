package com.fitnessplayground.service;

/**
 * Created by micheal on 3/03/2017.
 */
public enum MemberStatus {
    RECEIVED("RECEIVED", "Submission has been received",102),
    SAVED("SAVED", "Data has been saved to the database",102),
    PENDING("PENDING", "New member, waiting to be added to MindBody",418),
    ADDING("ADDING", "New member, cleared to be added to MindBody",102),
    ADDED("ADDED", "Added to MindBody but not active yet",200),
    INACTIVE("INACTIVE", "Added to MindBody but not active yet",418),
    PURCHASE_CONTRACT_ERROR("PURCHASE_CONTRACT_ERROR", "Error purchasing MBO Contract", 418),
    PURCHASE_SERVICE_ERROR("PURCHASE_SERVICE_ERROR", "Error purchasing MBO Service", 418),
    ADD_DIRECT_DEBIT_INFO_ERROR("ADD_DIRECT_DEBIT_INFO_ERROR", "Error adding client direct debit info", 501),
    TEMP_MEMBERSHIP("TEMP_MEMBERSHIP", "Temporary membership applied",412),
    ACTIVE("ACTIVE", "Active in MindBody but incomplete submission",412),
    MANUAL("MANUAL", "Memberships need to be actioned manually",412),
    DUPLICATE_KEY_DIFF_MEMBER("DUPLICATE_KEY_DIFF_MEMBER", "Access key already exists on a different member in MindBody",418),
    DUPLICATE_KEY_SAME_MEMBER("DUPLICATE_KEY_SAME_MEMBER", "Access key and member already exist in MindBody",418),
    ACCESS_KEY_ERROR("ACCESS_KEY_ERROR", "Failed to upload access key to Mindbody", 418),
    DUPLICATE_KEY("DUPLICATE_KEY", "Access key already exist in MindBody",418),
    DUPLICATE_USERNAME("DUPLICATE_USERNAME", "Username already exist in MindBody",418),
    ERROR("ERROR", "Error",501),
    EXTERNAL_PT("EXTERNAL_PT", "Member has signed up for external personal training",412),
    SUCCESS("SUCCESS", "Action was successful",200),
    EMAIL_CAMPAIGN_FAILED("EMAIL_CAMPAIGN_FAILED","Failed to trigger email auto-responder", 418),
    EMAIL_CAMPAIGN_ADDED("EMAIL_CAMPAIGN_ADDED", "Member added to email auto-responder", 200),
    PDF("PDF", "contract is waiting to be uploaded to MBO",200),
    COMPLETE("COMPLETE", "Action is complete",200),
    PROCESSING("PROCESSING", "Action is currently been processed",102),
    CLIENT_NOT_FOUND("CLIENT_NOT_FOUND", "Client not found in Mindbody Online", 412),
    PENDING_CLIENT_AUTHORISATION("PENDING_CLIENT_AUTHORISATION","Waiting for client authorisation",206),
    CANCELLATION_REQUEST_RECEIVED("CANCELLATION_REQUEST_RECEIVED", "Recieved Cancellation Request", 100),
    CANCELLATION_SAVED("CANCELLATION_SAVED", "Member was saved from cancellation", 200),
    CANCELLATION_AUTHORISATION_PENDING("CANCELLATION_AUTHORISATION_PENDING", "Waiting on authorisation from member", 200),
    CANCELLATION_AUTHORISATION_OVERDUE("CANCELLATION_AUTHORISATION_OVERDUE", "Authorisation from member is overdue", 200),
    CANCELLATION_AUTHORISED("CANCELLATION_AUTHORISED", "Cancellation has been authorised by member", 200),
    CANCELLATION_COMPLETE("CANCELLATION_COMPLETE", "Cancellation has actioned", 200),
    MEMBERSHIP_TRANSFER("MEMBERSHIP_TRANSFER","Membership is to be transferred",200),
    MEMBERSHIP_CHANGE_RECEIVED("MEMBERSHIP_CHANGE_RECEIVED", "Received Membership Change", 100),
    MEMBERSHIP_CHANGE_AUTHORISATION_PENDING("MEMBERSHIP_CHANGE_AUTHORISATION_PENDING", "Waiting on authorisation from member", 200),
    MEMBERSHIP_CHANGE_AUTHORISATION_OVERDUE("MEMBERSHIP_CHANGE_AUTHORISATION_OVERDUE", "Authorisation from member is overdue", 200),
    MEMBERSHIP_CHANGE_AUTHORISED("MEMBERSHIP_CHANGE_AUTHORISED", "Membership Change has been authorised by member", 200),
    MEMBERSHIP_CHANGE_COMPLETE("MEMBERSHIP_CHANGE_COMPLETE", "Membership Change has been actions", 200),
    ENROLMENT_RECEIVED("ENROLMENT_RECEIVED", "Received Membership Change", 100),
    ENROLMENT_AUTHORISATION_PENDING("ENROLMENT_AUTHORISATION_PENDING", "Waiting on authorisation from member", 200),
    ENROLMENT_AUTHORISATION_OVERDUE("ENROLMENT_AUTHORISATION_OVERDUE", "Authorisation from member is overdue", 200),
    ENROLMENT_AUTHORISED("ENROLMENT_AUTHORISED", "Enrolment has been authorised by member", 200),
    PAR_Q_PENDING("PAR_Q_PENDING","Waiting to receive par-q",200),
    PAR_Q_RECEIVED("PAR_Q_RECEIVED","Par-q has been received",200),
    PAR_Q_OVERDUE("PAR_Q_OVERDUE","Par-q is overdue",200),
    PT_FREE_SESSION("PT_FREE_SESSION","Member has booked N Free PT Sessions",200),
    PT_PAID_SESSION("PT_PAID_SESSION","Memebr has booked N Paid PT Sessions",200),
    PT_CANCELLED("PT_CANCELLED","Member has cancelled PT",200);

    private String status;
    private String description;
    private Integer statusCode;

    MemberStatus(String status, String description, Integer statusCode) {
        this.status = status;
        this.description = description;
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    //    MemberStatus(String status, String description) {
//        this.status = status;
//        this.description = description;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public String getDescription() {
//        return description;
//    }
}
