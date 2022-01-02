package com.fitnessplayground.service;

import com.fitnessplayground.dao.domain.*;
import com.fitnessplayground.dao.domain.mboDto.*;
import com.fitnessplayground.dao.domain.temp.PersonName;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface MindBodyService {
    CompletableFuture<String> processTestAsync();
    boolean isSydney(String locId);
    long getSiteIdByLocationId(String locationId);
    double getAccessKeyDiscountAmount(String discountCode);
    MboUserResponse getUserToken(String username, String password, boolean isSydney);
    ArrayList<MboContract> syncAllContracts();
    ArrayList<MboService> syncAllServices();
    ArrayList<MboClient> syncAllClients();
    ArrayList<MboProduct> syncAllProducts();
    void syncAllClientContracts();
    void setStaffMap();
    String getStaffOrPersonalTrainerName(String mboId, String siteId);

    boolean isDuplicateKey(String accessKeyNumber);

    List<Client> findMboClientByClientId(String clientId);
    List<Client> searchMboClient(String searchText);
    List<Client> findMboClientByUniqueIds(Long mboUniqueIds);

    String getNameFromMboIdAndSiteId(String input, long siteId);
    String getNameFromMboIdAndLocationId(String input, String locationId);

    //    TODO
    MboPurchaseContractRequest buildPurchaseContract(MboContract contract, EnrolmentData enrolmentData, Boolean isTestSubmission);
    MboPurchaseContractResponse purchaseMboContract(MboPurchaseContractRequest mboPurchaseContractRequest, boolean isSydney);

    MboAddNewClientResponse addMboClient(MboAddNewClientRequest mboAddNewClientRequest);
    MboAddNewClientRequest buildAddNewClient(EnrolmentData enrolmentData, boolean isTestSubmission, boolean skipKey, boolean skipEmail);

    MboUpdateClientResponse updateMboClient(MboUpdateClientRequest mboUpdateClientRequest, Boolean isSydney);
    MboUpdateClientRequest buildUpdateClient(EnrolmentData enrolmentData, Client mboClient, boolean isTestSubmission, boolean skipKey);

    MboUpdateClientResponse updateClientAccessKey(MboUpdateAccessKeyNumberRequest mboUpdateAccessKeyNumberRequest , Boolean isSydney);
    void sendPasswordResetEmail(MboSendPasswordResetEmailRequest mboSendPasswordResetEmailRequest, Boolean isSydney);

    MboAddClientDirectDebitInfoRequest buildAddClientDirectDebitInfo(EnrolmentData enrolmentData, Boolean isTestSubmission);
    MboAddClientDirectDebitInfoResponse addClientDirectDebitInfo(MboAddClientDirectDebitInfoRequest request, String locationId);

    MboShoppingCartResponse checkoutShoppingCart(MboShoppingCartRequest mboShoppingCartRequest, boolean isSydney);
    MboShoppingCartRequest buildAccessKeyPOSCart(EnrolmentData enrolmentData, boolean isTestSubmission);
//    MboGetClassesResponse getMboClasses(String startDateTime, String endDateTime);
    ArrayList<MboClass> getMboClasses(@Nullable String StartDateTime, @Nullable String EndDateTime);


    boolean uploadDocument(MboUploadDocumentRequest request, boolean isSydney);
    boolean processPdf(MemberTermsAndConditions termsAndConditions);

    PersonName getNameFromUIEncoding(String input);
    HashMap<Long, String> getAllStaffMembersMap();
    HashMap<String, String> getAllStaffMap();
    String getStaffName(String key);

    void writeEnrolmentPDF(EnrolmentData enrolmentData, String clubManager);

    void handleWebhookClientContracts(MboHookClientContract mboHookClientContract);
    void handleWebhookClient(MboHookClient mboHookClient);

    ArrayList<com.fitnessplayground.dao.domain.mboDto.Service> getMboServices();
    ArrayList<SessionType> getMboSessionTypes();
    ArrayList<Location> getMboLocations();
    ArrayList<Visit> getClientVisits(String clientId, String fromDate, String toDate);

}
