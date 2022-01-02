package com.fitnessplayground.service.impl;

import com.fitnessplayground.dao.*;
import com.fitnessplayground.dao.domain.*;
import com.fitnessplayground.dao.domain.ActiveCampaignDto.*;
import com.fitnessplayground.dao.domain.formstackDto.PtReassignSubmission;
import com.fitnessplayground.dao.domain.fpSourceDto.DigitalPreExData;
import com.fitnessplayground.dao.domain.fpSourceDto.EntityLookUp;
import com.fitnessplayground.dao.domain.fpSourceDto.FPPTParQData;
import com.fitnessplayground.dao.domain.fpSourceDto.FPReassignPersonalTrainerRequest;
import com.fitnessplayground.dao.domain.ops.FPOpsConfig;
import com.fitnessplayground.dao.domain.temp.SubmissionArray;
import com.fitnessplayground.service.*;
import com.fitnessplayground.util.Constants;
import com.fitnessplayground.util.GymName;
import com.fitnessplayground.util.Helpers;
import org.apache.tomcat.util.bcel.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class ActiveCampaignServiceImpl implements ActiveCampaignService {

    private static final Logger logger = LoggerFactory.getLogger(ActiveCampaignServiceImpl.class);

    private int retryCount;
    private final int RETRY_BREAK = 3;
    private boolean isValid;
    private int requestLimit = 100;
    private int requestOffset;
    private int count;
    private int totalResults;

    @Value("${active.campaign.baseurl}")
    private String BASE_URL;

    @Value("${active.campaign.api.token}")
    private String API_TOKEN;

    @Value("${mail.formstack.parq}")
    private String FS_PAR_Q_FORM_URL;

    @Value("${fp.source.handshake.key}")
    private String HANDSHAKE_KEY;

    private RestTemplate restTemplate;
    private ActiveCampaignDao activeCampaignDao;
    private MemberDao memberDao;
    private StaffDao staffDao;
    private FPOpsConfigDao fpOpsConfigDao;
    private GymDao gymDao;
    private EncryptionService encryptionService;
//    private FitnessPlaygroundService fitnessPlaygroundService;


    @Override
    public boolean isActiveCampaignOn() {
        FPOpsConfig fpOpsConfig = getFpOpsConfigDao().findByName("ActiveCampaignOn");
        logger.info("ActiveCampaignOn() {}", fpOpsConfig.getValue());
        if(null != fpOpsConfig && fpOpsConfig.getValue().equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isAddContactOn() {
        FPOpsConfig fpOpsConfig = getFpOpsConfigDao().findByName("ActiveCampaignAddContactsOn");
        logger.info("Active Campaign isAddContactOn() {}", fpOpsConfig.getValue());
        if (fpOpsConfig != null && fpOpsConfig.getValue().equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Async
    public void handleWebhookContact(String body) {
        logger.info("Inside handleWebhookContact()");

        String decoded = null;

        try {
            decoded = java.net.URLDecoder.decode(body, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            logger.error("Error decoding AC Webhook add Contact {}",body);
        }

        String[] fieldSplit = decoded.split("&");
        ACWebhookContact acWebhookContact = new ACWebhookContact();

        for (String field : fieldSplit) {

            if (field.split("=").length >= 1) {
                if (field.contains("id")) acWebhookContact.setId(field.split("=")[1]);
                if (field.contains("first_name")) acWebhookContact.setFirst_name(field.split("=")[1]);
                if (field.contains("last_name")) acWebhookContact.setLast_name(field.split("=")[1]);
                if (field.contains("email")) acWebhookContact.setEmail(field.split("=")[1]);
            }
        }

//        acWebhookContact.setEmail(acWebhookContact.getEmail().replaceAll("%40","@"));

        logger.info("acWebhookContact: {}", acWebhookContact);

        List<AcContact> acContacts = getActiveCampaignDao().findAcContactByAcContactId(acWebhookContact.getId());

//        logger.info("Found {} AC Contacts\n{}\nemail = email = {}",acContacts.size(), acContacts.toString(),
//                !acContacts.isEmpty()
//                        ? acWebhookContact.getEmail().trim().equalsIgnoreCase(acContacts.get(0).getEmail().trim())
//                        : "Non_Existing");

        if (acContacts.size() == 1 && acWebhookContact.getEmail().equals(acContacts.get(0).getEmail())) {
            logger.info("Contact exists, updating contact: {}",acContacts);
            AcContact acContact = acContacts.get(0);
            acContact.setCdate(Helpers.getDateNow());
            acContact = saveAcContact(acContact, acWebhookContact);
            logger.info("Updated {}",acContact);
        } else if (acContacts.size() == 0){
            logger.info("Contact does not exists, creating contact");
            AcContact acContact = new AcContact();
            acContact = saveAcContact(acContact, acWebhookContact);
            logger.info("Saved {}",acContact);
        } else {
            logger.error("Unable to add AcContact via the Webhook: {}",acWebhookContact);
        }
    }

    @Override
    public Iterable<AcEmailTag> getTags() {
        return getActiveCampaignDao().getAllTags();
    }

    @Override
    public Iterable<AcCustomField> getCustomFields() {
        return getActiveCampaignDao().getAllCustomFields();
    }

    private AcContact saveAcContact(AcContact acContact, ACWebhookContact acWebhookContact) {
        acContact.setFirstName(acWebhookContact.getFirst_name());
        acContact.setLastName(acWebhookContact.getLast_name());
        acContact.setEmail(acWebhookContact.getEmail());
        acContact.setAcContactId(acWebhookContact.getId());
        return getActiveCampaignDao().saveAcContact(acContact);
    }


    @Override
    public ArrayList<ACContact> getAllContacts() {

        ArrayList<ACContact> contacts;
        boolean isSuccess;

        resetRequestVariables();
        ACGetContactsListResponse response = null;
        HttpHeaders headers = getHeaders();
        HttpEntity<ACContact> entity = new HttpEntity<>(headers);

        do {
            isSuccess = true;
            try {
                response = getRestTemplate().exchange(BASE_URL + "contacts?limit=" + getRequestLimit() + "&offset=" + getRequestOffset(), HttpMethod.GET, entity, ACGetContactsListResponse.class).getBody();
            } catch (Exception ex) {
                logger.error("Error getting Contracts {}",ex.getMessage());
                setRetryCount(getRetryCount() + 1);
                isSuccess = false;
                setValid(getRetryCount() < RETRY_BREAK);
            }
            if (isSuccess) {
                setTotalResults(Integer.parseInt(response.getMetaData().getTotal()));
                setRequestOffset(getRequestOffset() + response.getMetaData().getPage_input().getLimit());
//                logger.info("AC - getAllContacts(): Offset [{}] | Total Results [{}]", getRequestOffset(), getTotalResults());
//                logger.info("AC - getAllContacts() getRequestOffset(): {} of {}", getRequestOffset(), getTotalResults());

                contacts = response.getContacts();
                processAcContacts(contacts);
                contacts.clear();
                setValid(getRequestOffset() < getTotalResults());
            }
        } while (isValid());
        logger.info("Successfully synced {} ACContacts",getTotalResults());
        return null;
    }

    @Override
    public List<AcContact> getContactByEmail(String email) {
        return getActiveCampaignDao().findAcContactByEmail(email);
    }

    @Override
    public ArrayList<AcEmailTag> getAllTags() {

        resetRequestVariables();
        ArrayList<AcEmailTag> tags = new ArrayList<>();
        resetRequestVariables();
        boolean isSuccess;
        ACGetAllTagsResponse response = null;
        HttpHeaders headers = getHeaders();
        HttpEntity<ACTag> entity = new HttpEntity<>(headers);

        do {
            isSuccess = true;
            try {
                response = getRestTemplate().exchange(BASE_URL + "tags?limit=" + getRequestLimit() + "&offset=" + getRequestOffset(), HttpMethod.GET, entity, ACGetAllTagsResponse.class).getBody();
            } catch (Exception e) {
                logger.error("Error getting tags {}", e.getMessage());
                setRetryCount(getRetryCount() + 1);
                isSuccess = false;
                setValid(getRetryCount() < RETRY_BREAK);
            }
            if (isSuccess) {
                setValid(false);
                tags = processAcTags(response.getTags(), tags);
                tags.clear();
                setTotalResults(Integer.parseInt(response.getMetaData().getTotal()));
                setRequestOffset(getRequestOffset() + getRequestLimit());
                setValid(getRequestOffset() < getTotalResults());
//                logger.info("Total Results: {} | Offset: {} | Limit: {}",getTotalResults(),getRequestOffset(),getRequestLimit());
            }
        } while (isValid());

        return tags;
    }

    @Override
    public ArrayList<AcCustomField> getAllCustomFields() {
        ArrayList<AcCustomField> fields = new ArrayList<>();
        resetRequestVariables();
        boolean isSuccess;
        ACGetAllCustomFieldsResponse response = null;
        HttpHeaders headers = getHeaders();
        HttpEntity<ACField> entity = new HttpEntity<>(headers);

        do {
            isSuccess = true;
            try {
                response = getRestTemplate().exchange(BASE_URL + "fields?limit="+getRequestLimit(), HttpMethod.GET, entity, ACGetAllCustomFieldsResponse.class).getBody();
            } catch (Exception e) {
                logger.error("Error getting custom fields {}", e.getMessage());
                setRetryCount(getRetryCount() + 1);
                isSuccess = false;
                setValid(getRetryCount() < RETRY_BREAK);
            }
            if (isSuccess) {
                logger.info("AC - getAllCustomFields() getRequestOffset(): {} of {}", getRequestOffset(), getTotalResults());
                fields = processAcCustomFields(response.getCustomFields(), fields);
                if (response.getMetaData().getPage_input() != null) {
                    setTotalResults(Integer.parseInt(response.getMetaData().getTotal()));
                    setRequestOffset(getRequestOffset() + response.getMetaData().getPage_input().getLimit());
//                logger.info("AC - getAllCustomFields(): Offset [{}] | Total Results [{}]", getRequestOffset(), getTotalResults());
                    setValid(getRequestOffset() < getTotalResults());
                } else {
                    setValid(false);
                }
            }
        } while (isValid());
        return fields;
    }


    @Override
    public LeadData addOrUpdateContact(LeadData leadData) {

        ACCreateContactRequestBody body = new ACCreateContactRequestBody(
                leadData.getEmail().trim(),
                leadData.getFirst_name(),
                leadData.getLast_name(),
                leadData.getPhone()
        );

        ACCreateContactRequest request = new ACCreateContactRequest(body);

        boolean isSuccess = false;
        ACCreateContactResponse response = null;
        HttpHeaders headers = getHeaders();
        HttpEntity<ACCreateContactRequest> entity = new HttpEntity<>(request, headers);
        try {
            response = getRestTemplate().exchange(BASE_URL + "contact/sync", HttpMethod.POST, entity, ACCreateContactResponse.class).getBody();
            isSuccess = true;
        } catch (Exception e) {
            logger.error("Error creating Active Campaign Contact: {} {}", body.getEmail(), e.getMessage());
            leadData.setStatus(LeadStatus.ERROR.getStatus());
        }

        try {
            if (isSuccess) {
                leadData.setActiveCampaignId(response.getContact().getId());
                Iterable<AcEmailTag> allTags = getActiveCampaignDao().getAllTags();
                String[] tags = new String[3];

                for (AcEmailTag tag : allTags) {

                    try {
                        if (tag.getTag().equalsIgnoreCase(leadData.getSource_name())) {
                            tags[0] = tag.getTagId();
                        }
                    } catch (Exception ex) {
                        logger.error("Error adding AC Tag: {}",ex.getMessage());
                    }

                    if (tag.getTag().equalsIgnoreCase("new_web_lead")) {
                        tags[1] = tag.getTagId();
                    }

                    if (tag.getTag().equalsIgnoreCase(leadData.getGymName().replace(" ",""))) {
                        tags[2] = tag.getTagId();
                    }

                    if (tag.getTag().equalsIgnoreCase("bunker") && leadData.getGymName().contains("unker")) {
                        tags[2] = tag.getTagId();
                    }
                }

                ACAddTagToContactRequestBody add_tag_body = new ACAddTagToContactRequestBody();
                add_tag_body.setContactId(leadData.getActiveCampaignId());

                sendTags(tags, add_tag_body);
                leadData.setStatus(LeadStatus.EMAIL_CAMPAIGN_TRIGGERED.getStatus());
            }

        } catch (Exception ex) {
            logger.error("Error adding Active Campaign Tags: {} {}", leadData.getEmail(), ex.getMessage());
            leadData.setStatus(LeadStatus.ERROR.getStatus());
        }

        return leadData;
    }

    @Async
    @Override
    public void handleFpCoachCommunications(FpCoachEnrolmentData fpCoachEnrolmentData) {
        logger.info("handleFpCoachCommunications");

//        AddOrUpdate Contact
        ACCreateContactRequestBody body = new ACCreateContactRequestBody(
                fpCoachEnrolmentData.getEmail().trim(),
                fpCoachEnrolmentData.getFirstName(),
                fpCoachEnrolmentData.getLastName(),
                fpCoachEnrolmentData.getPhone()
        );

        ACCreateContactRequest request = new ACCreateContactRequest(body);

        boolean isSuccess = false;
        ACCreateContactResponse response = null;
        HttpHeaders headers = getHeaders();
        HttpEntity<ACCreateContactRequest> entity = new HttpEntity<>(request, headers);
        try {
            response = getRestTemplate().exchange(BASE_URL + "contact/sync", HttpMethod.POST, entity, ACCreateContactResponse.class).getBody();
            isSuccess = true;
        } catch (Exception e) {
            logger.error("[handleFpCoachCommunications] Error creating Active Campaign Contact: {} {}", body.getEmail(), e.getMessage());
        }

//        Populate Custom Fields
        if (isSuccess) {
            fpCoachEnrolmentData.setActiveCampaignId(response.getContact().getId());

            try {
                ACAddCustomFieldToContactRequestBody addFieldsBody = new ACAddCustomFieldToContactRequestBody();
                addFieldsBody.setContactId(Long.parseLong(fpCoachEnrolmentData.getActiveCampaignId()));

                HashMap<String, String> fieldMap = getFields(fpCoachEnrolmentData);

                if (fieldMap.isEmpty()) {
                    logger.error("[handleFpCoachCommunications] Error adding custom fields, no field ids");
                    isSuccess = false;
                }

                if (isSuccess) { isSuccess = sendCustomFields(fieldMap, addFieldsBody); }

            } catch (Exception ex) {
                logger.error("[handleFpCoachCommunications] Error adding AC Custom Fields: {}",ex.getMessage());
                isSuccess = false;
            }
        }

//        Add Tags
        if (isSuccess) {

            try {
                ACAddTagToContactRequestBody addTagsBody = new ACAddTagToContactRequestBody();
                addTagsBody.setContactId(fpCoachEnrolmentData.getActiveCampaignId());

                String tagIds = getTags(fpCoachEnrolmentData);
                if (tagIds == "") {
                    logger.error("[handleFpCoachCommunications] Error adding tags, no tag ids");
                    isSuccess = false;
                }

                if (isSuccess) {
                    String[] tags = tagIds.split(",");
                    isSuccess = sendTags(tags, addTagsBody);
                }

            } catch (Exception ex) {
                logger.error("[handleFpCoachCommunications] Error adding AC Tags: {}",ex.getMessage());
                isSuccess = false;
            }

        }

//        Update database
        if (isSuccess) {
            fpCoachEnrolmentData.setCommunicationsStatus(CommunicationsStatus.EMAIL_CAMPAIGN_ADDED.getStatus());
        } else {
            fpCoachEnrolmentData.setCommunicationsStatus(CommunicationsStatus.EMAIL_CAMPAIGN_ERROR.getStatus());
        }

        if (fpCoachEnrolmentData.getMemberCreditCard() != null) {
            fpCoachEnrolmentData.setMemberCreditCard(getEncryptionService().encryptCMemberCreditCard(fpCoachEnrolmentData.getMemberCreditCard()));
        }

        if (fpCoachEnrolmentData.getMemberBankDetail() != null) {
            fpCoachEnrolmentData.setMemberBankDetail(getEncryptionService().encryptMemberBankDetail(fpCoachEnrolmentData.getMemberBankDetail()));
        }

        getMemberDao().updateFpCoachEnrolmentData(fpCoachEnrolmentData);

    }

    @Async
    @Override
    public void handleEnrolmentCommunications(EnrolmentData enrolmentData, DigitalPreExData preExData) {

//        AddOrUpdate Contact
        ACCreateContactRequestBody body = new ACCreateContactRequestBody(
                enrolmentData.getEmail().trim(),
                enrolmentData.getFirstName(),
                enrolmentData.getLastName(),
                enrolmentData.getPhone()
        );

        ACCreateContactRequest request = new ACCreateContactRequest(body);

        boolean isSuccess = false;
        ACCreateContactResponse response = null;
        HttpHeaders headers = getHeaders();
        HttpEntity<ACCreateContactRequest> entity = new HttpEntity<>(request, headers);
        try {
            response = getRestTemplate().exchange(BASE_URL + "contact/sync", HttpMethod.POST, entity, ACCreateContactResponse.class).getBody();
            isSuccess = true;
        } catch (Exception e) {
            logger.error("[handleEnrolmentCommunications] Error creating Active Campaign Contact: {} {}", body.getEmail(), e.getMessage());
        }

//        Populate Custom Fields
        if (isSuccess) {
            enrolmentData.setActiveCampaignId(response.getContact().getId());

            try {
                ACAddCustomFieldToContactRequestBody addFieldsBody = new ACAddCustomFieldToContactRequestBody();
                addFieldsBody.setContactId(Long.parseLong(enrolmentData.getActiveCampaignId()));

                HashMap<String, String> fieldMap = getFields_v2(enrolmentData, preExData);

                if (fieldMap.isEmpty()) {
                    logger.error("[handleEnrolmentCommunications] Error adding custom fields, no field ids");
                    isSuccess = false;
                }

                if (isSuccess) { isSuccess = sendCustomFields(fieldMap, addFieldsBody); }

            } catch (Exception ex) {
                logger.error("[handleEnrolmentCommunications] Error adding AC Custom Fields: {}",ex.getMessage());
                isSuccess = false;
            }
        }

//        Add Tags
        if (isSuccess) {

            try {
                ACAddTagToContactRequestBody addTagsBody = new ACAddTagToContactRequestBody();
                addTagsBody.setContactId(enrolmentData.getActiveCampaignId());

                String tagIds = getTags(enrolmentData);
                if (tagIds == "") {
                    logger.error("[handleEnrolmentCommunications] Error adding tags, no tag ids");
                    isSuccess = false;
                }

                if (isSuccess) {
                    String[] tags = tagIds.split(",");
                    isSuccess = sendTags(tags, addTagsBody);
                }

            } catch (Exception ex) {
                logger.error("[handleEnrolmentCommunications] Error adding AC Tags: {}",ex.getMessage());
                isSuccess = false;
            }
        }

//        Update Database
        if (isSuccess) {
            enrolmentData.setCommunicationsStatus(CommunicationsStatus.EMAIL_CAMPAIGN_ADDED.getStatus());
        } else {
            enrolmentData.setCommunicationsStatus(CommunicationsStatus.EMAIL_CAMPAIGN_ERROR.getStatus());
        }

        if (enrolmentData.getMemberCreditCard() != null) {
            enrolmentData.setMemberCreditCard(getEncryptionService().encryptCMemberCreditCard(enrolmentData.getMemberCreditCard()));
        }

        if (enrolmentData.getMemberBankDetail() != null) {
            enrolmentData.setMemberBankDetail(getEncryptionService().encryptMemberBankDetail(enrolmentData.getMemberBankDetail()));
        }

        getMemberDao().updateEnrolmentData(enrolmentData);
    }

    @Override
    public void handleReferredByMemberComms(EnrolmentData enrolmentData, WebReferralData webReferralData) {

        try {

            logger.info("Inside handleReferredByMemberComms(EnrolmentData.id({}), WebReferralData.id({}))", enrolmentData.getId(), webReferralData.getId());

//        Find the member who referred the enrolment in Active Campaign
            ACContact acContact = null;
            resetRequestVariables();
            boolean isSuccess = true;
            ACGetContactsListResponse response = null;
            HttpHeaders headers = getHeaders();
            HttpEntity<AcContact> entity = new HttpEntity<>(headers);

            try {
                response = getRestTemplate().exchange(BASE_URL + "contacts?email= " + webReferralData.getMemberEmail() + "&limit=" + getRequestLimit(), HttpMethod.GET, entity, ACGetContactsListResponse.class).getBody();
            } catch (Exception e) {
                logger.error("Error getting contact list {}", e.getMessage());
                setRetryCount(getRetryCount() + 1);
                isSuccess = false;
                setValid(getRetryCount() < RETRY_BREAK);
            }
            if (isSuccess) {
                logger.info("AC - handleReferredByMemberComms() getRequestOffset(): {} of {}", getRequestOffset(), getTotalResults());

                if (response.getContacts().size() == 1) {
                    logger.info("Found Contact in Active Campaign");
                    acContact = response.getContacts().get(0);
                }
            }

            if (acContact != null) {

//        Add Referral Name Custom Field
                logger.info("Add Referral Name Custom Field");
                ACAddCustomFieldToContactRequestBody customFieldBody = new ACAddCustomFieldToContactRequestBody();
                customFieldBody.setContactId(Long.parseLong(acContact.getId()));

                Iterable<AcCustomField> fields = getActiveCampaignDao().getAllCustomFields();
                HashMap<String, String> fieldMap = new HashMap<>();
                for (AcCustomField f : fields) {
                    if (f.getPerstag().equals(Constants.ACTIVE_CAMPAIGN_FIELD_REFERRALS_REFERRAL_NAME)) {
                        fieldMap.put(f.getFieldId(), webReferralData.getMemberFirstName() + " " + webReferralData.getMemberLastName());
                        sendCustomFields(fieldMap, customFieldBody);
                    }
                }

//            if (!fieldMap.isEmpty()) {
//                logger.info("Found {} fields",fieldMap.size());
//                sendCustomFields(fieldMap, customFieldBody);
//            }

//        Add referred_join Tag
                logger.info("Add referred_join Tag");
                ACAddTagToContactRequestBody tagBody = new ACAddTagToContactRequestBody();
                tagBody.setContactId(acContact.getId());

                Iterable<AcEmailTag> acEmailTags = getActiveCampaignDao().getAllTags();
                String[] tagIds = {""};

                for (AcEmailTag tag : acEmailTags) {
                    if (tag.getTag().equals(Constants.ACTIVE_CAMPAIGN_TAG_REFERRAL_JOINED)) {
                        tagIds[0] = tag.getTagId();
                        sendTags(tagIds, tagBody);
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("Error handleReferredByMemberComms: {}",ex.getMessage());
        }

    }

    @Override
    public EnrolmentData createContact(EnrolmentData enrolmentData, PreExData preExData, PtTracker ptTracker) {

        List<AcContact> acContacts = getActiveCampaignDao().findAcContactByEmail(enrolmentData.getEmail());
        boolean status = false;

        if (acContacts == null || acContacts.isEmpty()) {

            ACCreateContactRequestBody body = new ACCreateContactRequestBody(
                    enrolmentData.getEmail().trim(),
                    enrolmentData.getFirstName(),
                    enrolmentData.getLastName(),
                    enrolmentData.getPhone()
            );

            ACCreateContactRequest request = new ACCreateContactRequest(body);

            boolean isSuccess = false;
            ACCreateContactResponse response = null;
            HttpHeaders headers = getHeaders();
            HttpEntity<ACCreateContactRequest> entity = new HttpEntity<>(request, headers);
            try {
                response = getRestTemplate().exchange(BASE_URL + "contacts", HttpMethod.POST, entity, ACCreateContactResponse.class).getBody();
                isSuccess = true;
            } catch (Exception e) {
                logger.error("Error creating Active Campaign Contact: {} {}", body.getEmail(), e.getMessage());
            }

            if (isSuccess) {
                enrolmentData.setActiveCampaignId(response.getContact().getId());
                status = addTags(enrolmentData);
                if (status) {
                    status = addCustomFields(enrolmentData,preExData,ptTracker);
                } else {
                    addCustomFields(enrolmentData,preExData,ptTracker);
                }
            }

        } else {
            enrolmentData.setActiveCampaignId(acContacts.get(0).getAcContactId());
            status = addTags(enrolmentData);
            if (status) {
                status = addCustomFields(enrolmentData,preExData,ptTracker);
            } else {
                addCustomFields(enrolmentData,preExData,ptTracker);
            }
        }

        enrolmentData.setCommunicationsStatus(status ? CommunicationsStatus.EMAIL_CAMPAIGN_ADDED.getStatus() : CommunicationsStatus.EMAIL_CAMPAIGN_ERROR.getStatus());

        return enrolmentData;
    }

    @Override
    public Boolean addTags(EnrolmentData enrolmentData) {
        if (enrolmentData.getActiveCampaignId() == null) {
            logger.error("Error adding tags, no Active Campaign Id");
            return false;
        }

        try {
            ACAddTagToContactRequestBody body = new ACAddTagToContactRequestBody();
            body.setContactId(enrolmentData.getActiveCampaignId());

            String tagIds = getTags(enrolmentData);
            if (tagIds == "") {
                logger.error("Error adding tags, no tag ids");
                return false;
            }

            String[] tags = tagIds.split(",");
            return sendTags(tags, body);
        } catch (Exception ex) {
            logger.error("Error adding AC Tags: {}",ex.getMessage());
            return false;
        }
    }

    private Boolean sendTags(String[] tags, ACAddTagToContactRequestBody body) {

        boolean isSuccess;
        boolean returnStatus = true;
        ACAddTagToContactResponse response = null;
        HttpHeaders headers = getHeaders();
        HttpEntity<ACAddTagToContactRequest> entity;
        ACAddTagToContactRequest request = new ACAddTagToContactRequest();

//        String[] tags = tagIds.split(",");
        for (String tag : tags) {
            isSuccess = false;
            body.setTagId(tag);
            request.setBody(body);
            entity = new HttpEntity<ACAddTagToContactRequest>(request, headers);
            try {
                response = getRestTemplate().exchange(BASE_URL+"contactTags", HttpMethod.POST, entity, ACAddTagToContactResponse.class).getBody();
                isSuccess = true;
            } catch (Exception e) {
                logger.error("Error adding Active Campaign Tag {} | {}",request.getBody(), e.getMessage());
                returnStatus = false;
            }

            if (isSuccess) {
                logger.info("Successfully added tag {}",response.getContactTag());
            }
        }
        return returnStatus;
    }

    @Override
    public Boolean addCustomFields(EnrolmentData enrolmentData, PreExData preExData, PtTracker ptTracker) {

        if (enrolmentData.getActiveCampaignId() == null) {
            logger.error("Error adding custom fields to enrolmentDataId {}, no Active Campaign Id", enrolmentData.getId());
            return false;
        }

//        logger.info("Inside addCustomFields");

        try {

            ACAddCustomFieldToContactRequestBody body = new ACAddCustomFieldToContactRequestBody();
            body.setContactId(Long.parseLong(enrolmentData.getActiveCampaignId()));

            HashMap<String, String> fieldMap = getFields(enrolmentData, preExData, ptTracker);

            if (fieldMap.isEmpty()) {
                logger.error("Error adding custom fields, no field ids");
                return false;
            }
//        logger.info("Found {} fields",fieldMap.size());

            return sendCustomFields(fieldMap, body);
        } catch (Exception ex) {
            logger.error("Error adding AC Custom Fields: {}",ex.getMessage());
            return false;
        }
    }

    @Override
    public void processPTParQ(FPPTParQData data) {

        if (data.getACID() == null) {
            logger.error("Failed to update PT ParQ custom fields, no ACID");
            return;
        }
//        logger.info("Inside processPTParQ");

        ACAddCustomFieldToContactRequestBody body = new ACAddCustomFieldToContactRequestBody();
        body.setContactId(Long.parseLong(data.getACID()));
//        HttpHeaders headers = getHeaders();
//        HttpEntity<ACAddCustomFieldToContactRequest> entity;

        HashMap<String,String> fieldMap = getPTParQFields(data);
        if (fieldMap.isEmpty()) {
            logger.error("Error adding PT ParQ custom fields, no field ids");
            return;
        }
//        logger.info("Found {} fields",fieldMap.size());

        sendCustomFields(fieldMap, body);
    }

    @Override
    public void processReassignPersonalTrainer(FPReassignPersonalTrainerRequest fpReassignPersonalTrainerRequest) {
        if (fpReassignPersonalTrainerRequest.getACID() == null) {
            logger.error("Failed to update Personal Trainer custom fields, no ACID");
            return;
        }
//        logger.info("Inside processReassignPersonalTrainer");
        ACAddCustomFieldToContactRequestBody body = new ACAddCustomFieldToContactRequestBody();
        body.setContactId(Long.parseLong(fpReassignPersonalTrainerRequest.getACID()));
        
        HashMap<String, String> fieldMap = getReassignPersonalTrainerFields(fpReassignPersonalTrainerRequest);
        if (fieldMap.isEmpty()) {
            logger.error("Error reassign Personal Trainer custom fields, no field ids");
            return;
        }
        sendCustomFields(fieldMap, body);

        Iterable<AcEmailTag> tags = getActiveCampaignDao().getAllTags();
        HashMap<String, String> tagMap = new HashMap<>();

        for (AcEmailTag t : tags) {
            tagMap.put(t.getTag(), t.getTagId());
        }

        String[] tagIds = { tagMap.get(Constants.ACTIVE_CAMPAIGN_TAG_REASSIGNED_PT) };

        ACAddTagToContactRequestBody body_1 = new ACAddTagToContactRequestBody();
        body_1.setContactId(fpReassignPersonalTrainerRequest.getACID());
        sendTags(tagIds, body_1);
    }

    @Override
    public void processPtReassign(Long ACID, Staff coach, String queryParams) {

        try {
            HashMap<String, String> fieldValueMap = new HashMap<>();
            HashMap<String, String> acFieldsMap = new HashMap<>();
            Iterable<AcCustomField> fields = getActiveCampaignDao().getAllCustomFields();

            for (AcCustomField f : fields) {
                acFieldsMap.put(f.getPerstag(), f.getFieldId());
            }

            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINER_FIRST_NAME), coach.getFirstName());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINER_LAST_NAME), coach.getLastName());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINER_NAME), coach.getName());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINER_EMAIL), coach.getEmail());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINER_PHONE), Helpers.cleanPhoneNumber(coach.getPhone()));

            if (queryParams != null) {
                fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_QUERY_PARAMS), queryParams);
            }

            ACAddCustomFieldToContactRequestBody body = new ACAddCustomFieldToContactRequestBody();
            body.setContactId(ACID);

            sendCustomFields(fieldValueMap, body);

        } catch (Exception ex) {
            logger.error("Error Processing PT Reassign: {}",ex.getMessage());
        }
    }


    private Boolean sendCustomFields(HashMap<String,String> fieldMap, ACAddCustomFieldToContactRequestBody body) {

        boolean isSuccess;
        boolean returnStatus = true;
        ACAddCustomFieldToContactRequest request = new ACAddCustomFieldToContactRequest();
        ACAddCustomFieldToContactResponse response = null;

        HttpHeaders headers = getHeaders();
        HttpEntity<ACAddCustomFieldToContactRequest> entity;

//        logger.info("Found {} fields",fieldMap.size());

//        logger.info("Inside sendCustomFields: fieldMap {}",fieldMap.toString());

        Iterator iterator = fieldMap.entrySet().iterator();
        while (iterator.hasNext()) {

            Map.Entry<String, String> pair = (Map.Entry<String, String>) iterator.next();
            try {
                body.setField(Integer.parseInt(pair.getKey()));
                body.setValue(pair.getValue());
                iterator.remove();
                isSuccess = true;
                request.setBody(body);
                entity = new HttpEntity<ACAddCustomFieldToContactRequest>(request, headers);
                try {
                    response = getRestTemplate().exchange(BASE_URL + "fieldValues", HttpMethod.POST, entity, ACAddCustomFieldToContactResponse.class).getBody();
                } catch (Exception e) {
                    isSuccess = false;
                    logger.error("Error add custom field value {} | {}", request.getBody(), e.getMessage());
                    returnStatus = false;
                }
                if (isSuccess) {
                    logger.info("Successfully added custom field value {}", request.getBody().getField());
                }
            } catch (Exception e) {
                logger.error("Error adding AC Custom Field: KEY = {} | VALUE = {} | ACID = {}, \n{}", pair.getKey(), pair.getValue(), body.getContactId(), e.getMessage());
            }
        }
        return returnStatus;
    }

//    private DigitalPreExData getDigitalPreExData(EnrolmentData enrolmentData) {
//
//        EntityLookUp entityLookUp = new EntityLookUp();
//        entityLookUp.setAuth(HANDSHAKE_KEY);
//        entityLookUp.setEmail(enrolmentData.getEmail());
//        entityLookUp.setPhone(enrolmentData.getPhone());
//        entityLookUp.setFirstName(enrolmentData.getFirstName());
//        entityLookUp.setLastName(enrolmentData.getLastName());
//
//        return fpSourceService.getDigitalPreData(entityLookUp);
//    }


    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Api-Token", API_TOKEN);
//        logger.info("Headers: {}", headers.toString());
        return headers;
    }

    private void resetRequestVariables() {
        setRequestOffset(0);
        setCount(0);
        setTotalResults(0);
        setValid(true);
        setRetryCount(0);
    }

    private void processAcContacts(ArrayList<ACContact> contacts) {
        if (contacts == null || contacts.isEmpty()) {
            return;
        }

        Iterable<AcContact> contactList = getActiveCampaignDao().findAllAcContacts();
        HashMap<String, AcContact> acContactHashMap = setAcContactMap(contactList);

        for (ACContact c : contacts) {

            if (!acContactHashMap.containsKey(c.getId())) {
                AcContact contact = AcContact.from(c);
                try {
                    getActiveCampaignDao().saveAcContact(contact);
                } catch (Exception e) {
                    logger.info("Error saving AcContact {}",e.getMessage());
                }
            }
        }
    }

    private HashMap<String, AcContact> setAcContactMap(Iterable<AcContact> acContacts) {

        HashMap<String, AcContact> acContactHashMap = new HashMap<>();

        for (AcContact c : acContacts) {
            acContactHashMap.put(c.getAcContactId(), c);
        }

        return acContactHashMap;
    }

    private ArrayList<AcEmailTag> processAcTags(ArrayList<ACTag> tags, ArrayList<AcEmailTag> acEmailTags) {

        for (ACTag t : tags) {
            AcEmailTag acEmailTag = getActiveCampaignDao().getTagByTagId(t.getTagId());
            if (acEmailTag == null) {
                acEmailTag = AcEmailTag.create(t);
            } else {
                acEmailTag = AcEmailTag.update(t, acEmailTag);
            }
            try {
                getActiveCampaignDao().saveTag(acEmailTag);
            }catch (Exception e) {
                logger.error(e.getLocalizedMessage());
            }
            acEmailTags.add(acEmailTag);
        }
        return acEmailTags;
    }

    private ArrayList<AcCustomField> processAcCustomFields(ArrayList<ACField> fields, ArrayList<AcCustomField> customFields) {

        for (ACField f : fields) {
            AcCustomField acCustomField = getActiveCampaignDao().findByCustomFieldById(f.getFieldId());
            if (acCustomField == null) {
                acCustomField = AcCustomField.create(f);
            } else {
                acCustomField = AcCustomField.update(f, acCustomField);
            }
            try {
                getActiveCampaignDao().saveAcCustomField(acCustomField);
            } catch (Exception e) {
                logger.error(e.getLocalizedMessage());
            }
            customFields.add(acCustomField);
        }
        return customFields;
    }
    
    private HashMap<String, String> getReassignPersonalTrainerFields(FPReassignPersonalTrainerRequest fpReassignPersonalTrainerRequest) {
        HashMap<String, String> fieldValueMap = new HashMap<>();
        HashMap<String, String> acFieldsMap = new HashMap<>();
        Iterable<AcCustomField> fields = getActiveCampaignDao().getAllCustomFields();

        for (AcCustomField f : fields) {
            acFieldsMap.put(f.getPerstag(), f.getFieldId());
        }
        
        if (fpReassignPersonalTrainerRequest != null) {
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINER_FIRST_NAME), fpReassignPersonalTrainerRequest.getTrainerFirstName());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINER_LAST_NAME), fpReassignPersonalTrainerRequest.getTrainerLastName());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINER_NAME), fpReassignPersonalTrainerRequest.getTrainerName());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINER_EMAIL), fpReassignPersonalTrainerRequest.getTrainerEmail());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINER_PHONE), fpReassignPersonalTrainerRequest.getTrainerPhone());
        }
        return fieldValueMap;
    }
    
    private HashMap<String, String> getPTParQFields(FPPTParQData parQData) {
        HashMap<String, String> fieldValueMap = new HashMap<>();
        HashMap<String, String> acFieldsMap = new HashMap<>();
        Iterable<AcCustomField> fields = getActiveCampaignDao().getAllCustomFields();

        for (AcCustomField f : fields) {
            acFieldsMap.put(f.getPerstag(), f.getFieldId());
        }

        if (parQData != null) {
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PARQ_WHAT_ARE_YOUR_EATING_HABITS), parQData.getWhatAreYourEatingHabits());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PARQ_PHYSICALLY_ACTIVE_AT_WORK), parQData.getAreYouPhysicallyActiveAtWork());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PARQ_HOW_DO_YOU_RATE_YOUR_ENERGY), parQData.getHowDoYouRateYourEnergyLevels());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PARQ_SLEEP_AT_NIGHT), parQData.getHowMuchSleepToYouGetANight());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PARQ_STRESS_LEVELS), parQData.getHowDoYouRateYourStressLevels());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PARQ_DESTRESS), parQData.getHowDoYouDestress());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PARQ_STORE_MAJORITY_BF), parQData.getWhereDoYouStoreTheMajorityOfYourBodyFat());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PARQ_MAJOR_INJURIES_AND_PAINS), parQData.getDoYouHaveAnyPainOrInjuries());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PARQ_OTHER_INJURIES), parQData.getDoYouHaveAnyOtherInjuriesIllnessAilments());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PARQ_WORK_WITH_PT_BEFORE), parQData.getHaveYouWorkedWithAPersonalTrainer());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PARQ_FURTHER_DETAIL_ABOUT_TRAINING), parQData.getWhatWereYourFavouritePartsOfWorkingWithAPT());
        }

        return fieldValueMap;
    }


    private HashMap<String, String> getFields(FpCoachEnrolmentData fpCoachEnrolmentData) {

        HashMap<String, String> fieldValueMap = new HashMap<>();
        HashMap<String, String> acFieldsMap = new HashMap<>();
        String[] strArr;
        Staff tempStaff;

        Iterable<AcCustomField> fields = getActiveCampaignDao().getAllCustomFields();

        for (AcCustomField f : fields) {
            acFieldsMap.put(f.getPerstag(), f.getFieldId());
        }

        Gym gym = getGymDao().getGymByLocationId(fpCoachEnrolmentData.getLocationId());

        if (gym != null) {
            if (gym.getPersonalTrainingManagerId() != null) {
                strArr = gym.getPersonalTrainingManagerId().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
                if (strArr.length > 0) {
                    try {
                        tempStaff = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));

                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_PTM_NAME), tempStaff.getName());
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_PTM_FIRST_NAME), tempStaff.getFirstName());
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_PTM_LAST_NAME), tempStaff.getLastName());
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_PTM_EMAIL), tempStaff.getEmail());

                    } catch (Exception e) {
                        logger.error("Error setting PTM to Active Campaign fpCoachEnrolmentData: {} | {}", fpCoachEnrolmentData.getId(), e.getMessage());
                    }
                }
            }

            if (gym.getGroupFitnessManagerId() != null) {
                strArr = gym.getGroupFitnessManagerId().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
                if (strArr.length > 0) {
                    try {
                        tempStaff = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));

                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_GFM_NAME), tempStaff.getName());
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_GFM_FIRST_NAME), tempStaff.getFirstName());
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_GFM_LAST_NAME), tempStaff.getLastName());
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_GFM_EMAIL), tempStaff.getEmail());
                    } catch (Exception e) {
                        logger.error("Error setting GFM to Active Campaign fpCoachEnrolmentData: {} | {}", fpCoachEnrolmentData.getId(), e.getMessage());
                    }
                }
            }

            if (gym.getClubManagerId() != null) {
                strArr = gym.getClubManagerId().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
                if (strArr.length > 0) {
                    try {
                        tempStaff = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));

                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_CM_FULL_NAME), tempStaff.getName());
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_CM_FIRST_NAME), tempStaff.getFirstName());
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_CM_LAST_NAME), tempStaff.getLastName());
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_CM_EMAIL), tempStaff.getEmail());
                    } catch (Exception e) {
                        logger.error("Error setting CM to Active Campaign fpCoachEnrolmentData: {} | {}", fpCoachEnrolmentData.getId(), e.getMessage());
                    }
                }
            }
        }

//        General
        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_ACID), fpCoachEnrolmentData.getActiveCampaignId());

//        PT
        strArr = fpCoachEnrolmentData.getPersonalTrainer().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
        if (strArr.length > 0) {
            if (Long.parseLong(strArr[0]) > 0) {
                try {
                    tempStaff = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));
                    if (tempStaff != null) {
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINER_FIRST_NAME), tempStaff.getFirstName());
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINER_LAST_NAME), tempStaff.getLastName());
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINER_NAME), tempStaff.getFirstName()+" "+tempStaff.getLastName());
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINER_EMAIL), tempStaff.getEmail());
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINER_PHONE), tempStaff.getPhone());
                    }
                } catch (Exception e) {
                    logger.error("Error setting PT to Active Campaign enrolmentDataId: {} {}", fpCoachEnrolmentData.getId(),e);
                }
            }
        }

        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_COACH_START_DATE), fpCoachEnrolmentData.getStartDate());
        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_COACH_FIRST_BILLING_DATE), fpCoachEnrolmentData.getStartDate());

        try {
            String queryParams = "name-first=" + URLEncoder.encode(fpCoachEnrolmentData.getFirstName(), StandardCharsets.UTF_8.toString())
                + "&name-last=" + URLEncoder.encode(fpCoachEnrolmentData.getLastName(), StandardCharsets.UTF_8.toString())
                + "&email=" + URLEncoder.encode(fpCoachEnrolmentData.getEmail(), StandardCharsets.UTF_8.toString())
                + "&phone=" + URLEncoder.encode(fpCoachEnrolmentData.getPhone(), StandardCharsets.UTF_8.toString())
                + "&locationId=" + URLEncoder.encode(Integer.toString(fpCoachEnrolmentData.getLocationId()), StandardCharsets.UTF_8.toString())
                + "&coach+name=" + URLEncoder.encode(fpCoachEnrolmentData.getPersonalTrainerName(), StandardCharsets.UTF_8.toString());


            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_QUERY_PARAMS), queryParams);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }  catch (Exception e) {
            logger.error("Error building Query Params");
        }

        return fieldValueMap;
    }



    private HashMap<String, String> getFields_v2(EnrolmentData enrolmentData, DigitalPreExData preExData) {
        HashMap<String, String> fieldValueMap = new HashMap<>();
        HashMap<String, String> acFieldsMap = new HashMap<>();
        String[] strArr;
        Staff tempStaff;

        Iterable<AcCustomField> fields = getActiveCampaignDao().getAllCustomFields();

        for (AcCustomField f : fields) {
            acFieldsMap.put(f.getPerstag(), f.getFieldId());
        }

        Gym gym = getGymDao().getGymByLocationId(Integer.parseInt(enrolmentData.getLocationId()));

        if (gym != null) {
            if (gym.getPersonalTrainingManagerId() != null) {
                strArr = gym.getPersonalTrainingManagerId().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
                if (strArr.length > 0) {
                    try {
                        tempStaff = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));

                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_PTM_NAME), tempStaff.getName());
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_PTM_FIRST_NAME), tempStaff.getFirstName());
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_PTM_LAST_NAME), tempStaff.getLastName());
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_PTM_EMAIL), tempStaff.getEmail());

                    } catch (Exception e) {
                        logger.error("Error setting PTM to Active Campaign fpCoachEnrolmentData: {} | {}", enrolmentData.getId(), e.getMessage());
                    }
                }
            }

            if (gym.getGroupFitnessManagerId() != null) {
                strArr = gym.getGroupFitnessManagerId().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
                if (strArr.length > 0) {
                    try {
                        tempStaff = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));

                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_GFM_NAME), tempStaff.getName());
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_GFM_FIRST_NAME), tempStaff.getFirstName());
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_GFM_LAST_NAME), tempStaff.getLastName());
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_GFM_EMAIL), tempStaff.getEmail());
                    } catch (Exception e) {
                        logger.error("Error setting GFM to Active Campaign fpCoachEnrolmentData: {} | {}", enrolmentData.getId(), e.getMessage());
                    }
                }
            }

            if (gym.getClubManagerId() != null) {
                strArr = gym.getClubManagerId().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
                if (strArr.length > 0) {
                    try {
                        tempStaff = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));

                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_CM_FULL_NAME), tempStaff.getName());
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_CM_FIRST_NAME), tempStaff.getFirstName());
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_CM_LAST_NAME), tempStaff.getLastName());
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_CM_EMAIL), tempStaff.getEmail());
                    } catch (Exception e) {
                        logger.error("Error setting CM to Active Campaign fpCoachEnrolmentData: {} | {}", enrolmentData.getId(), e.getMessage());
                    }
                }
            }
        }

//        General
        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_ACID), enrolmentData.getActiveCampaignId());

        if (enrolmentData.getStaffMember() != null) {
            strArr = enrolmentData.getStaffMember().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
            if (strArr.length > 0) {

                try {
                    tempStaff = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));
                    fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_ENROLMENT_MEMBERSHIP_CONSULTANT_FIRST_NAME), tempStaff.getFirstName());
                    fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_ENROLMENT_MEMBERSHIP_CONSULTANT_LAST_NAME), tempStaff.getLastName());
                    fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_ENROLMENT_MEMBERSHIP_CONSULTANT_NAME), tempStaff.getFirstName() + " " + tempStaff.getLastName());
                    fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_ENROLMENT_MEMBERSHIP_CONSULTANT_EMAIL), tempStaff.getEmail());
                } catch (Exception e) {
                    logger.error("Error setting Membership Consultant to Active Campaign enrolmentDataId: {} | {}", enrolmentData.getId(), e.getMessage());
                }

            }
        }

//        Enrolment
        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_ENROLMENT_DATE_OF_BIRTH), enrolmentData.getDob());

        if (enrolmentData.getContractNamesToBeActivated() != null) {
            String tmpStr = "";
            try {
                strArr = enrolmentData.getContractNamesToBeActivated().split(" | ");

                for (String i : strArr) {
                    if (i.toLowerCase().contains("play") || i.toLowerCase().contains("gym") || i.toLowerCase().contains("perform")) {
                        String[] tmpStrArr = i.split(" # ");
                        tmpStr = tmpStrArr[0];
                        tmpStr = tmpStr.replace(":","");
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_ENROLMENT_MEMBERSHIP_TYPE), tmpStr);
                    }

                    if (i.toLowerCase().contains("pt")) {
                        String[] tmpStrArr = i.split(" # ");
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_SESSIONS_PER_WEEK), tmpStrArr[0]);
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_ENROLMENT_PT_FORTNIGHTLY_DIRECT_DEBIT), tmpStrArr[1]);
                    }
                }

            } catch (Exception e) {
                logger.error("Error adding membership type: {}",e.getMessage());
            }


        }

        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_ENROLMENT_AGREEMENT_START_DATE), enrolmentData.getStartDate());
        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_ENROLMENT_FIRST_DEBIT_DATE), enrolmentData.getFirstBillingDate());
        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_ENROLMENT_NOTES), enrolmentData.getNotes());
        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_ENROLMENT_COMMITMENT_TERM), Helpers.cleanCamelCase(enrolmentData.getNoCommitment()));
        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_ENROLMENT_GYM_LOCATION), enrolmentData.getGymName());

        //        PT
        if (enrolmentData.getPersonalTrainer() != null) {
            strArr = enrolmentData.getPersonalTrainer().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
            if (strArr.length > 0) {
                if (Long.parseLong(strArr[0]) > 0) {
                    try {
                        tempStaff = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));
                        if (tempStaff != null) {
                            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINER_FIRST_NAME), tempStaff.getFirstName());
                            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINER_LAST_NAME), tempStaff.getLastName());
                            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINER_NAME), tempStaff.getFirstName() + " " + tempStaff.getLastName());
                            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINER_EMAIL), tempStaff.getEmail());
                            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINER_PHONE), tempStaff.getPhone());
                        } else {
                            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINER_NAME), "No Complementary Session Requested");
                        }
                    } catch (Exception e) {
                        logger.error("Error setting PT to Active Campaign enrolmentDataId: {}", enrolmentData.getId());
                    }
                }
            }
        }

        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_TRAINING_STARTER_PACK), enrolmentData.getTrainingStarterPack());
        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINING_FIRST_DEBIT_DATE), enrolmentData.getPersonalTrainingStartDate());
        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINING_START_DATE), enrolmentData.getStartDate());
        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_COACH_START_DATE), enrolmentData.getStartDate());
        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_COACH_FIRST_BILLING_DATE), enrolmentData.getPersonalTrainingStartDate());

        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_SESSIONS_PER_WEEK), enrolmentData.getNumberSessionsPerWeek());

        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_AVAILABILITY_DAY_OF_WEEK), enrolmentData.getTrainingAvailability() == null ? enrolmentData.getTrainingAvailability() :enrolmentData.getTrainingAvailability().replace(",",", "));
        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_AVAILABILITY_TIME_OF_DAY), enrolmentData.getTimeAvailability() == null ? enrolmentData.getTimeAvailability() : enrolmentData.getTimeAvailability().replace(",",", "));

        try {
            String queryParams = "name-first=" + (enrolmentData.getFirstName() == null ? enrolmentData.getFirstName() : URLEncoder.encode(enrolmentData.getFirstName(), StandardCharsets.UTF_8.toString())
                    + "&name-last=" + (enrolmentData.getLastName() == null ? enrolmentData.getLastName() : URLEncoder.encode(enrolmentData.getLastName(), StandardCharsets.UTF_8.toString())
                    + "&email=" + (enrolmentData.getEmail() == null ? enrolmentData.getEmail() : URLEncoder.encode(enrolmentData.getEmail(), StandardCharsets.UTF_8.toString()))
                    + "&phone=" + (enrolmentData.getPhone() == null ? enrolmentData.getPhone() : URLEncoder.encode(enrolmentData.getPhone(), StandardCharsets.UTF_8.toString()))
                    + "&locationId=" + (enrolmentData.getLocationId() == null ? enrolmentData.getLocationId() : URLEncoder.encode(enrolmentData.getLocationId(), StandardCharsets.UTF_8.toString()))
                    + "&coach+name=" + (enrolmentData.getPersonalTrainerName() == null ? enrolmentData.getPersonalTrainerName() : URLEncoder.encode(enrolmentData.getPersonalTrainerName(), StandardCharsets.UTF_8.toString()))));


            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_QUERY_PARAMS), queryParams);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("Error building Query Params");
        }

//        DigitalPreExData preExData = getFitnessPlaygroundService().getDigitalPreExData(enrolmentData);
        if (preExData != null) {
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_CURRENTLY_EXERCISING), preExData.getAreYouCurrentlyExercising());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_WHAT_DO_YOU_ENJOY), preExData.getWhatExerciseYouEnjoy());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_AREAS_TO_IMPROVE_FRONT), preExData.getAreasToImprove_front());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_AREAS_TO_IMPROVE_BACK), preExData.getAreaToImporve_back());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_GOALS_BODY), preExData.getGoals_body());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_GOALS_HEALTH), preExData.getGoals_health());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_GOALS_FITNESS), preExData.getGoals_fitness());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_SPECIFIC_GOAL_IN_12_WEEKS), preExData.getGoals_specificallyInNext12Weeks());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_REACHED_THESE_GOALS_BEFORE), preExData.getHaveYouReachedTheseGoalsBefore());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_WHAT_PREVENTED_YOU), preExData.getWhatPreventedYouFromAchievingYouGoal());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_SCALE_1_10_IMPORTANCE_TO_ACHIEVE_GOAL), preExData.getOnAScaleOf1to10HowImportantIsItToAchieveYourGoal());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_FREQUENCY_TO_TRAIN_PER_W), preExData.getHowManyDaysAWeekDoYouPlanToExercise());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_FOLLOW_TRAINING_PLAN), preExData.getDoYouFollowATrainingPlan());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_HAVE_AN_EATING_PLAN), preExData.getDoYouHaveAnEatingPlan());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_HOW_EFFECTIVE_WAS_PREVIOUS_PROGRAM), preExData.getHowEffectiveWasYourPreviousProgram());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_WHAT_ELSE_CAN_WE_DO_TO_HELP), preExData.getWhatElseCanWeDoToHelp());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_TAKING_ANY_MEDICATION), preExData.getAreYouTakingMedication());
        }

        return fieldValueMap;

    }


    private HashMap<String, String> getFields(EnrolmentData enrolmentData, PreExData preExData, PtTracker ptTracker) {
        HashMap<String, String> fieldValueMap = new HashMap<>();
        HashMap<String, String> acFieldsMap = new HashMap<>();
        String[] strArr;
        Staff tempStaff;

        Iterable<AcCustomField> fields = getActiveCampaignDao().getAllCustomFields();

        for (AcCustomField f : fields) {
            acFieldsMap.put(f.getPerstag(), f.getFieldId());
        }

        Gym gym = getGymDao().getGymByLocationId(Integer.parseInt(enrolmentData.getLocationId()));

        if (gym != null) {
            if (gym.getPersonalTrainingManagerId() != null) {
                strArr = gym.getPersonalTrainingManagerId().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
                if (strArr.length > 0) {
                    try {
                        tempStaff = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));

                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_PTM_NAME), tempStaff.getName());
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_PTM_FIRST_NAME), tempStaff.getFirstName());
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_PTM_LAST_NAME), tempStaff.getLastName());
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_PTM_EMAIL), tempStaff.getEmail());

                    } catch (Exception e) {
                        logger.error("Error setting PTM to Active Campaign enrolmentDataId: {} | {}", enrolmentData.getId(), e.getMessage());
                    }
                }
            }

            if (gym.getGroupFitnessManagerId() != null) {
                strArr = gym.getGroupFitnessManagerId().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
                if (strArr.length > 0) {
                    try {
                        tempStaff = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));

                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_GFM_NAME), tempStaff.getName());
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_GFM_FIRST_NAME), tempStaff.getFirstName());
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_GFM_LAST_NAME), tempStaff.getLastName());
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_GFM_EMAIL), tempStaff.getEmail());
                    } catch (Exception e) {
                        logger.error("Error setting GFM to Active Campaign enrolmentDataId: {} | {}", enrolmentData.getId(), e.getMessage());
                    }
                }
            }
        }

//        General
        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_ACID), enrolmentData.getActiveCampaignId());
        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_GENDER), enrolmentData.getGender());

        Staff coach = null;
        try {
            if (enrolmentData.getPersonalTrainer() != null) {
                String[] coachStrArr = Helpers.splitStaffMboIdAndSiteId(enrolmentData.getPersonalTrainer());
                coach = getStaffDao().getStaffByMboId(Long.parseLong(coachStrArr[0]), Long.parseLong(coachStrArr[1]));
            }

        } catch (Exception ex) {
            logger.error("Error getting coach: {}",ex.getMessage());
        }

        String parqParams = "";
        try {
            parqParams = "?pt_tracker_id=" + URLEncoder.encode(ptTracker == null ? "" : Long.toString(ptTracker.getId()), StandardCharsets.UTF_8.toString()) +
                    "&name-first=" + URLEncoder.encode(enrolmentData.getFirstName() == null ? "" : enrolmentData.getFirstName(), StandardCharsets.UTF_8.toString()) +
                    "&name-last=" + URLEncoder.encode(enrolmentData.getLastName() == null ? "" : enrolmentData.getLastName(), StandardCharsets.UTF_8.toString()) +
                    "&Home+Club=" + URLEncoder.encode(enrolmentData.getGymName() == null ? "" : enrolmentData.getGymName(), StandardCharsets.UTF_8.toString()) +
                    "&Your+Coach+is=" + URLEncoder.encode(coach == null ? "" : coach.getName(), StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String parqLink = FS_PAR_Q_FORM_URL + parqParams;


        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PAR_Q_URL), parqLink);

//        TODO: These need to be updated to the new Pre Ex
//        Pre Ex
//        if (preExData != null) {
//            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_CURRENTLY_EXERCISING), preExData.getIsCurrentlyExercising());
////            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_WHAT_DO_YOU_ENJOY), preExData.());
//            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_AREAS_TO_IMPROVE_FRONT), preExData.getAreaOfFocus());
//            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_AREAS_TO_IMPROVE_BACK), preExData.getAreaToImporve_back());
//            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_GOALS_BODY), preExData.getGoals_body());
//            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_GOALS_HEALTH), preExData.getGoals_health());
//            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_GOALS_FITNESS), preExData.getGoals_fitness());
//            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_SPECIFIC_GOAL_IN_12_WEEKS), preExData.getGoals_specificallyInNext12Weeks());
//            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_REACHED_THESE_GOALS_BEFORE), preExData.getHaveYouReachedTheseGoalsBefore());
//            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_WHAT_PREVENTED_YOU), preExData.getWhatPreventedYouFromAchievingYouGoal());
//            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_SCALE_1_10_IMPORTANCE_TO_ACHIEVE_GOAL), preExData.getOnAScaleOf1to10HowImportantIsItToAchieveYourGoal());
//            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_FREQUENCY_TO_TRAIN_PER_W), preExData.getHowManyDaysAWeekDoYouPlanToExercise());
//            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_FOLLOW_TRAINING_PLAN), preExData.getDoYouFollowATrainingPlan());
//            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_HAVE_AN_EATING_PLAN), preExData.getDoYouHaveAnEatingPlan());
//            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_HOW_EFFECTIVE_WAS_PREVIOUS_PROGRAM), preExData.getHowEffectiveWasYourPreviousProgram());
//            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_WHAT_ELSE_CAN_WE_DO_TO_HELP), preExData.getWhatElseCanWeDoToHelp());
//            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PRE_EX_TAKING_ANY_MEDICATION), preExData.getAreYouTakingMedication());
//        }

//        Enrolment
        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_ENROLMENT_DATE_OF_BIRTH), enrolmentData.getDob());
        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_ENROLMENT_MEMBERSHIP_TYPE), Helpers.cleanCamelCase(enrolmentData.getFpOrBunker())+" "+Helpers.cleanCamelCase(enrolmentData.getGymOrPlay()));
        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_ENROLMENT_AGREEMENT_START_DATE), enrolmentData.getStartDate());
        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_ENROLMENT_FIRST_DEBIT_DATE), enrolmentData.getFirstBillingDate());
        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_ENROLMENT_NOTES), enrolmentData.getNotes());

        strArr = enrolmentData.getStaffMember().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
        if (strArr.length > 0) {

            try {
                tempStaff = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));
                fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_ENROLMENT_MEMBERSHIP_CONSULTANT_FIRST_NAME), tempStaff.getFirstName());
                fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_ENROLMENT_MEMBERSHIP_CONSULTANT_LAST_NAME), tempStaff.getLastName());
                fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_ENROLMENT_MEMBERSHIP_CONSULTANT_NAME), tempStaff.getFirstName() + " " + tempStaff.getLastName());
                fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_ENROLMENT_MEMBERSHIP_CONSULTANT_EMAIL), tempStaff.getEmail());
            } catch (Exception e) {
                logger.error("Error setting Membership Consultant to Active Campaign enrolmentDataId: {} | {}", enrolmentData.getId(), e.getMessage());
            }

        }

//        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_ENROLMENT_COMMITMENT_TERM), enrolmentData.getNoCommitment());
//        EXCLUDED FIELDS HERE

        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_ENROLMENT_YOUR_HEALTH_ANY_MEDICAL_ISSUES), Helpers.cleanCamelCase(enrolmentData.getMedical()) +" "+Helpers.cleanCamelCase(enrolmentData.getInjuries()));
        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_ENROLMENT_GYM_LOCATION), GymName.getGymName(Integer.parseInt(enrolmentData.getLocationId())).getName());

//        PT
        strArr = enrolmentData.getPersonalTrainer().split(Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER);
        if (strArr.length > 0) {
            if (Long.parseLong(strArr[0]) > 0) {
                try {
                    tempStaff = getStaffDao().getStaffByMboId(Long.parseLong(strArr[0]), Long.parseLong(strArr[1]));
                    if (tempStaff != null) {
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINER_FIRST_NAME), tempStaff.getFirstName());
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINER_LAST_NAME), tempStaff.getLastName());
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINER_NAME), tempStaff.getFirstName()+" "+tempStaff.getLastName());
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINER_EMAIL), tempStaff.getEmail());
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINER_PHONE), tempStaff.getPhone());
                    } else {
                        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINER_NAME), "No Complementary Session Requested");
                    }
                } catch (Exception e) {
                    logger.error("Error setting PT to Active Campaign enrolmentDataId: {}", enrolmentData.getId());
                }
            }
        }

        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_TRAINING_STARTER_PACK), Helpers.cleanCamelCase(enrolmentData.getTrainingStarterPack()));

        if (enrolmentData.getTrainingStarterPack().equals(Constants.ENROLMENT_DATA_ONGOING_PT)) {
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINING_START_DATE), enrolmentData.getPersonalTrainingStartDate());
//            Start Date == Debit Date
//            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_PERSONAL_TRAINING_FIRST_DEBIT_DATE), enrolmentData.getPersonalTrainingStartDate());
            fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_SESSIONS_PER_WEEK), enrolmentData.getNumberSessionsPerWeek());
        }

        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_AVAILABILITY_DAY_OF_WEEK), enrolmentData.getTrainingAvailability());
        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PT_AVAILABILITY_TIME_OF_DAY), Helpers.cleanCamelCase(enrolmentData.getTimeAvailability()));

//        FIXME - THIS IS FOR TESTING
//        fieldValueMap.put(acFieldsMap.get(Constants.ACTIVE_CAMPAIGN_FIELD_PERSONAL_TRAINER_EMAIL), "quintin@thebunkergym.com.au");

        return fieldValueMap;
    }

    private String getTags(FpCoachEnrolmentData fpCoachEnrolmentData) {

        Iterable<AcEmailTag> tags = getActiveCampaignDao().getAllTags();
        HashMap<String, String> tagMap = new HashMap<>();

        for (AcEmailTag t : tags) {
            tagMap.put(t.getTag(), t.getTagId());
        }

        String tagIds = "";

        if (fpCoachEnrolmentData.getLocationId() != null) {
            switch (fpCoachEnrolmentData.getLocationId()) {
                case Constants.BUNKER_LOCATION_ID:
                    tagIds += tagMap.get(Constants.ACTIVE_CAMPAIGN_TAG_BUNKER) + ",";
                    break;
                case Constants.GATEWAY_LOCATION_ID:
                    tagIds += tagMap.get(Constants.ACTIVE_CAMPAIGN_TAG_GATEWAY) + ",";
                    break;
                case Constants.MARRICKVILLE_LOCATION_ID:
                    tagIds += tagMap.get(Constants.ACTIVE_CAMPAIGN_TAG_MARRICKVILLE) + ",";
                    break;
                case Constants.NEWTOWN_LOCATION_ID:
                    tagIds += tagMap.get(Constants.ACTIVE_CAMPAIGN_TAG_NEWTOWN) + ",";
                    break;
                case Constants.SURRY_HILLS_LOCATION_ID:
                    tagIds += tagMap.get(Constants.ACTIVE_CAMPAIGN_TAG_SURRY_HILLS) + ",";
                    break;
            }
        }

        tagIds += tagMap.get(Constants.ACTIVE_CAMPAIGN_TAG_FP_COACH_ENROLMENT);

        return tagIds;
    }

    private String getTags(EnrolmentData enrolmentData) {

        Iterable<AcEmailTag> tags = getActiveCampaignDao().getAllTags();
        HashMap<String, String> tagMap = new HashMap<>();

        for (AcEmailTag t : tags) {
            tagMap.put(t.getTag(), t.getTagId());
        }

        String tagIds = "";

        if (enrolmentData.getGender() != null) {
            if (enrolmentData.getGender().equals(Constants.ENROLMENT_DATA_MALE)) {
                tagIds += tagMap.get(Constants.ACTIVE_CAMPAIGN_TAG_MALE) + ",";
            }

            if (enrolmentData.getGender().equals(Constants.ENROLMENT_DATA_FEMALE)) {
                tagIds += tagMap.get(Constants.ACTIVE_CAMPAIGN_TAG_FEMALE) + ",";
            }
        }

        if (enrolmentData.getCouponCode() != null) {
            if (enrolmentData.getCouponCode().toLowerCase().contains(Constants.ENROLMENT_DATA_COUPON_CORPORATE)) {
                tagIds += tagMap.get(Constants.ACTIVE_CAMPAIGN_TAG_CORPORATE) + ",";
            }

            if (enrolmentData.getCouponCode().toLowerCase().contains(Constants.ENROLMENT_DATA_COUPON_PTP)) {
                tagIds += tagMap.get(Constants.ACTIVE_CAMPAIGN_TAG_COUPON_PTP) + ",";
            }
        }

        if (enrolmentData.getLocationId() != null) {
            switch (Integer.parseInt(enrolmentData.getLocationId())) {
                case Constants.BUNKER_LOCATION_ID:
                    tagIds += tagMap.get(Constants.ACTIVE_CAMPAIGN_TAG_BUNKER) + ",";
                    break;
                case Constants.GATEWAY_LOCATION_ID:
                    tagIds += tagMap.get(Constants.ACTIVE_CAMPAIGN_TAG_GATEWAY) + ",";
                    break;
                case Constants.MARRICKVILLE_LOCATION_ID:
                    tagIds += tagMap.get(Constants.ACTIVE_CAMPAIGN_TAG_MARRICKVILLE) + ",";
                    break;
                case Constants.NEWTOWN_LOCATION_ID:
                    tagIds += tagMap.get(Constants.ACTIVE_CAMPAIGN_TAG_NEWTOWN) + ",";
                    break;
                case Constants.SURRY_HILLS_LOCATION_ID:
                    tagIds += tagMap.get(Constants.ACTIVE_CAMPAIGN_TAG_SURRY_HILLS) + ",";
                    break;
            }
        }


        if (enrolmentData.getGymOrPlay() != null) {
            switch (enrolmentData.getGymOrPlay()) {
                case Constants.ENROLMENT_DATA_GYM:
                    tagIds += tagMap.get(Constants.ACTIVE_CAMPAIGN_TAG_MEMBERSHIP_GYM) + ",";
                    break;
                case Constants.ENROLMENT_DATA_FIT:
                    tagIds += tagMap.get(Constants.ACTIVE_CAMPAIGN_TAG_MEMBERSHIP_FIT) + ",";
                    break;
                case Constants.ENROLMENT_DATA_PLAY:
                    tagIds += tagMap.get(Constants.ACTIVE_CAMPAIGN_TAG_MEMBERSHIP_PLAY) + ",";
                    break;
            }
        }

        if (enrolmentData.getTrainingStarterPack() != null) {
            switch (enrolmentData.getTrainingStarterPack()) {
                case Constants.STARTER_PACK_FACE_TO_FACE:
                    tagIds += tagMap.get(Constants.ACTIVE_CAMPAIGN_TAG_PT_ONGOING) + ",";
                    break;
                case Constants.STARTER_PACK_LIFESTYLE_PT:
                    tagIds += tagMap.get(Constants.ACTIVE_CAMPAIGN_TAG_PT_ONGOING) + ",";
                    break;
                case Constants.STARTER_PACK_EXTERNAL_PT:
                    tagIds += tagMap.get(Constants.ACTIVE_CAMPAIGN_TAG_EXTERNAL_PT) + ",";
                    break;
                case Constants.STARTER_PACK_PT_ONGO:
                    tagIds += tagMap.get(Constants.ACTIVE_CAMPAIGN_TAG_PT_ONGOING) + ",";
                    break;
                case Constants.STARTER_PACK_PT_PACK:
                    tagIds += tagMap.get(Constants.ACTIVE_CAMPAIGN_TAG_PT_PACK) + ",";
                    break;
                case Constants.STARTER_PACK_TRANSFORMER:
                    tagIds += tagMap.get(Constants.ACTIVE_CAMPAIGN_TAG_PT_PACK) + ",";
                    break;
                case Constants.STARTER_PACK_GURU:
                    if (enrolmentData.getPersonalTrainer().equals(Constants.ENROLMENT_DATA_NO_COMP_SESSION)) {
                        tagIds += tagMap.get(Constants.ACTIVE_CAMPAIGN_TAG_PT_NO_COMP_SESSION) + ",";
                    } else {
                        tagIds += tagMap.get(Constants.ACTIVE_CAMPAIGN_TAG_PT_COMP_SESSION) + ",";
                    }
                    break;
                case Constants.STARTER_PACK_EP_REFERRAL:
                    tagIds += tagMap.get(Constants.ACTIVE_CAMPAIGN_TAG_EP_REFERRAL) + ",";
                    break;
            }
        }

        if (enrolmentData.getPersonalTrainer() != null) {
            if (enrolmentData.getPersonalTrainer().equals(Constants.ASSIGN_TO_PTM_ID)) {
                tagIds += tagMap.get(Constants.ACTIVE_CAMPAIGN_TAG_PTM_ASSIGNED) + ",";
            }

            if (enrolmentData.getPersonalTrainer().equals(Constants.NO_COMP_SESSION_ID)) {
                tagIds += tagMap.get(Constants.ACTIVE_CAMPAIGN_TAG_PT_NO_COMP_SESSION) + ",";
            }
        }

        tagIds += tagMap.get(Constants.ACTIVE_CAMPAIGN_TAG_NEW_ENROLMENT);

        logger.info("getTags tagIds: {}",tagIds);
        return tagIds;
    }

    public int getRequestLimit() {
        return requestLimit;
    }

    public void setRequestLimit(int requestLimit) {
        this.requestLimit = requestLimit;
    }

    public int getRequestOffset() {
        return requestOffset;
    }

    public void setRequestOffset(int requestOffset) {
        this.requestOffset = requestOffset;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }


    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ActiveCampaignDao getActiveCampaignDao() {
        return activeCampaignDao;
    }

    @Autowired
    public void setActiveCampaignDao(ActiveCampaignDao activeCampaignDao) {
        this.activeCampaignDao = activeCampaignDao;
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

    public FPOpsConfigDao getFpOpsConfigDao() {
        return fpOpsConfigDao;
    }

    @Autowired
    public void setFpOpsConfigDao(FPOpsConfigDao fpOpsConfigDao) {
        this.fpOpsConfigDao = fpOpsConfigDao;
    }

    public GymDao getGymDao() {
        return gymDao;
    }

    @Autowired
    public void setGymDao(GymDao gymDao) {
        this.gymDao = gymDao;
    }

    public EncryptionService getEncryptionService() {
        return encryptionService;
    }

    @Autowired
    public void setEncryptionService(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

//    public FitnessPlaygroundService getFitnessPlaygroundService() {
//        return fitnessPlaygroundService;
//    }
//
//    @Autowired
//    public void setFitnessPlaygroundService(FitnessPlaygroundService fitnessPlaygroundService) {
//        this.fitnessPlaygroundService = fitnessPlaygroundService;
//    }
}
