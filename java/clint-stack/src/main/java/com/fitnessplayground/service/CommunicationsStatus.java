package com.fitnessplayground.service;

public enum CommunicationsStatus {

    INTERNAL_COMMS_PENDING("INTERNAL_COMMS_PENDING", "Top of funnel for internal communications", 100),
    NO_COMMS_PENDING("NO_COMMS_PENDING", "No Communications required", 200),
    MC_NOTES_PENDING("MC_NOTES", "Membership Consultant needs to send notes to Personal Trainer", 412),
    NOTIFY_PT_LEAD("NOTIFY_PT_LEAD", "Lead has been assigned to a Personal Trainer", 200),
    UNASSIGNED_PT_LEAD("UNASSIGNED_PT_LEAD", "Lead has been assigned to Personal Training Manager", 412),
    REASSIGNED_PT_LEAD("REASSIGNED_PT_LEAD", "Lead has been re-assigned by the Personal Trainer",200),
    EMAIL_CAMPAIGN_PENDING("EMAIL_CAMPAIGN_PENDING", "Member needs to be added to member facing email auto-responder", 100),
    EMAIL_CAMPAIGN_ADDED("EMAIL_CAMPAIGN_ADDED", "Member added to email auto-responder", 201),
    EMAIL_CAMPAIGN_ERROR("EMAIL_CAMPAIGN_ERROR", "Error adding email campaign", 401),
    INTERNAL_COMMS_COMPLETE("INTERNAL_COMMS_COMPLETE", "Internal communications are completed", 200),
    CLIENT_AUTHORISATION_SENT("CLIENT_AUTHORISATION_SENT", "Authorisation email has been sent to client", 200),
    CLIENT_AUTHORISATION_ERROR("CLIENT_AUTHORISATION_ERROR", "Authorisation email failed to send", 401),
    CLIENT_AUTHORISATION_RECEIVED("CLIENT_AUTHORISATION_RECEIVED", "Authorisation has been received by the client",200),
    CLIENT_AUTHORISATION_REMINDER_SENT("CLIENT_AUTHORISATION_REMINDER_SENT", "Authorisation reminder has been sent to the client",200),
    PAR_Q_SENT("PAR_Q_SENT","Par-q has been sent to member",200),
    PAR_Q_REMINDER_SENT("PAR_Q_REMINDER_SENT","Reminder sent to member",200),
    PAR_Q_RECEIVED("PAR_Q_RECEIVED","Par-q has been received",200),
    PT_EARLY_FEEDBACK_SENT("PT_EARLY_FEEDBACK_SENT","PT Early feedback request has been sent",200),
    PT_EARLY_FEEDBACK_REMINDER_SENT("PT_EARLY_FEEDBACK_REMINDER_SENT","PT Early feedback reminder sent to member",200),
    PT_EARLY_FEEDBACK_RECEIVED("PT_EARLY_FEEDBACK_RECEIVED","PT Early feedback received",200),
    PT_FEEDBACK_SENT("PT_FEEDBACK_SENT","PT feedback request sent",200),
    PT_FEEDBACK_REMINDER_SENT("PT_FEEDBACK_REMINDER_SENT","PT feedback request reminder sent to member",200),
    PT_FEEDBACK_RECEIVED("PT_FEEDBACK_RECEIVED","PT Feedback received",200);


    private String status;
    private String description;
    private Integer statusCode;

    CommunicationsStatus(String status, String description, Integer statusCode) {
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
}
