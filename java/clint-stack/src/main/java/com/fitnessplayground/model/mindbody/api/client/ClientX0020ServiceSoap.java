
package com.fitnessplayground.model.mindbody.api.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "Client_x0020_ServiceSoap", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ClientX0020ServiceSoap {


    /**
     * Adds an arrival record for the given client.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.client.AddArrivalResult
     */
    @WebMethod(operationName = "AddArrival", action = "http://clients.mindbodyonline.com/api/0_5_1/AddArrival")
    @WebResult(name = "AddArrivalResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "AddArrival", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.AddArrival")
    @ResponseWrapper(localName = "AddArrivalResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.AddArrivalResponse")
    public AddArrivalResult addArrival(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        AddArrivalRequest request);

    /**
     * Adds or updates information for a list of clients.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.client.AddOrUpdateClientsResult
     */
    @WebMethod(operationName = "AddOrUpdateClients", action = "http://clients.mindbodyonline.com/api/0_5_1/AddOrUpdateClients")
    @WebResult(name = "AddOrUpdateClientsResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "AddOrUpdateClients", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.AddOrUpdateClients")
    @ResponseWrapper(localName = "AddOrUpdateClientsResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.AddOrUpdateClientsResponse")
    public AddOrUpdateClientsResult addOrUpdateClients(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        AddOrUpdateClientsRequest request);

    /**
     * Updates a client's information and any cross-regional clients' information
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.client.UpdateClientCrossRegionalResult
     */
    @WebMethod(operationName = "UpdateClientCrossRegional", action = "http://clients.mindbodyonline.com/api/0_5_1/UpdateClientCrossRegional")
    @WebResult(name = "UpdateClientCrossRegionalResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "UpdateClientCrossRegional", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.UpdateClientCrossRegional")
    @ResponseWrapper(localName = "UpdateClientCrossRegionalResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.UpdateClientCrossRegionalResponse")
    public UpdateClientCrossRegionalResult updateClientCrossRegional(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        UpdateClientCrossRegionalRequest request);

    /**
     * Gets a list of clients.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.client.GetClientsResult
     */
    @WebMethod(operationName = "GetClients", action = "http://clients.mindbodyonline.com/api/0_5_1/GetClients")
    @WebResult(name = "GetClientsResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "GetClients", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.GetClients")
    @ResponseWrapper(localName = "GetClientsResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.GetClientsResponse")
    public GetClientsResult getClients(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        GetClientsRequest request);

    /**
     * Gets a list of Client IDs representing the same client at different sites within the region.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.client.GetCrossRegionalClientAssociationsResult
     */
    @WebMethod(operationName = "GetCrossRegionalClientAssociations", action = "http://clients.mindbodyonline.com/api/0_5_1/GetCrossRegionalClientAssociations")
    @WebResult(name = "GetCrossRegionalClientAssociationsResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "GetCrossRegionalClientAssociations", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.GetCrossRegionalClientAssociations")
    @ResponseWrapper(localName = "GetCrossRegionalClientAssociationsResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.GetCrossRegionalClientAssociationsResponse")
    public GetCrossRegionalClientAssociationsResult getCrossRegionalClientAssociations(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        GetCrossRegionalClientAssociationsRequest request);

    /**
     * Gets a list of currently available client indexes.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.client.GetCustomClientFieldsResult
     */
    @WebMethod(operationName = "GetCustomClientFields", action = "http://clients.mindbodyonline.com/api/0_5_1/GetCustomClientFields")
    @WebResult(name = "GetCustomClientFieldsResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "GetCustomClientFields", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.GetCustomClientFields")
    @ResponseWrapper(localName = "GetCustomClientFieldsResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.GetCustomClientFieldsResponse")
    public GetCustomClientFieldsResult getCustomClientFields(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        GetCustomClientFieldsRequest request);

    /**
     * Gets a list of currently available client indexes.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.client.GetClientIndexesResult
     */
    @WebMethod(operationName = "GetClientIndexes", action = "http://clients.mindbodyonline.com/api/0_5_1/GetClientIndexes")
    @WebResult(name = "GetClientIndexesResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "GetClientIndexes", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.GetClientIndexes")
    @ResponseWrapper(localName = "GetClientIndexesResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.GetClientIndexesResponse")
    public GetClientIndexesResult getClientIndexes(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        GetClientIndexesRequest request);

    /**
     * Get contact logs for a client.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.client.GetClientContactLogsResult
     */
    @WebMethod(operationName = "GetClientContactLogs", action = "http://clients.mindbodyonline.com/api/0_5_1/GetClientContactLogs")
    @WebResult(name = "GetClientContactLogsResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "GetClientContactLogs", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.GetClientContactLogs")
    @ResponseWrapper(localName = "GetClientContactLogsResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.GetClientContactLogsResponse")
    public GetClientContactLogsResult getClientContactLogs(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        GetClientContactLogsRequest request);

    /**
     * Add or update client contact logs.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.client.AddOrUpdateContactLogsResult
     */
    @WebMethod(operationName = "AddOrUpdateContactLogs", action = "http://clients.mindbodyonline.com/api/0_5_1/AddOrUpdateContactLogs")
    @WebResult(name = "AddOrUpdateContactLogsResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "AddOrUpdateContactLogs", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.AddOrUpdateContactLogs")
    @ResponseWrapper(localName = "AddOrUpdateContactLogsResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.AddOrUpdateContactLogsResponse")
    public AddOrUpdateContactLogsResult addOrUpdateContactLogs(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        AddOrUpdateContactLogsRequest request);

    /**
     * Get contact log types for a client.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.client.GetContactLogTypesResult
     */
    @WebMethod(operationName = "GetContactLogTypes", action = "http://clients.mindbodyonline.com/api/0_5_1/GetContactLogTypes")
    @WebResult(name = "GetContactLogTypesResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "GetContactLogTypes", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.GetContactLogTypes")
    @ResponseWrapper(localName = "GetContactLogTypesResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.GetContactLogTypesResponse")
    public GetContactLogTypesResult getContactLogTypes(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        GetContactLogTypesRequest request);

    /**
     * Upload a client document.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.client.UploadClientDocumentResult
     */
    @WebMethod(operationName = "UploadClientDocument", action = "http://clients.mindbodyonline.com/api/0_5_1/UploadClientDocument")
    @WebResult(name = "UploadClientDocumentResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "UploadClientDocument", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.UploadClientDocument")
    @ResponseWrapper(localName = "UploadClientDocumentResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.UploadClientDocumentResponse")
    public UploadClientDocumentResult uploadClientDocument(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        UploadClientDocumentRequest request);

    /**
     * Upload a client photo.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.client.UploadClientPhotoResult
     */
    @WebMethod(operationName = "UploadClientPhoto", action = "http://clients.mindbodyonline.com/api/0_5_1/UploadClientPhoto")
    @WebResult(name = "UploadClientPhotoResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "UploadClientPhoto", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.UploadClientPhoto")
    @ResponseWrapper(localName = "UploadClientPhotoResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.UploadClientPhotoResponse")
    public UploadClientPhotoResult uploadClientPhoto(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        UploadClientPhotoRequest request);

    /**
     * Gets a list of client formula notes.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.client.GetClientFormulaNotesResult
     */
    @WebMethod(operationName = "GetClientFormulaNotes", action = "http://clients.mindbodyonline.com/api/0_5_1/GetClientFormulaNotes")
    @WebResult(name = "GetClientFormulaNotesResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "GetClientFormulaNotes", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.GetClientFormulaNotes")
    @ResponseWrapper(localName = "GetClientFormulaNotesResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.GetClientFormulaNotesResponse")
    public GetClientFormulaNotesResult getClientFormulaNotes(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        GetClientFormulaNotesRequest request);

    /**
     * Adds a formula note to a client.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.client.AddClientFormulaNoteResult
     */
    @WebMethod(operationName = "AddClientFormulaNote", action = "http://clients.mindbodyonline.com/api/0_5_1/AddClientFormulaNote")
    @WebResult(name = "AddClientFormulaNoteResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "AddClientFormulaNote", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.AddClientFormulaNote")
    @ResponseWrapper(localName = "AddClientFormulaNoteResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.AddClientFormulaNoteResponse")
    public AddClientFormulaNoteResult addClientFormulaNote(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        AddClientFormulaNoteRequest request);

    /**
     * Deletes a formula note to a client.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.client.DeleteClientFormulaNoteResult
     */
    @WebMethod(operationName = "DeleteClientFormulaNote", action = "http://clients.mindbodyonline.com/api/0_5_1/DeleteClientFormulaNote")
    @WebResult(name = "DeleteClientFormulaNoteResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "DeleteClientFormulaNote", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.DeleteClientFormulaNote")
    @ResponseWrapper(localName = "DeleteClientFormulaNoteResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.DeleteClientFormulaNoteResponse")
    public DeleteClientFormulaNoteResult deleteClientFormulaNote(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        DeleteCientFormulaNoteRequest request);

    /**
     * Gets a list of clients.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.client.GetClientReferralTypesResult
     */
    @WebMethod(operationName = "GetClientReferralTypes", action = "http://clients.mindbodyonline.com/api/0_5_1/GetClientReferralTypes")
    @WebResult(name = "GetClientReferralTypesResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "GetClientReferralTypes", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.GetClientReferralTypes")
    @ResponseWrapper(localName = "GetClientReferralTypesResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.GetClientReferralTypesResponse")
    public GetClientReferralTypesResult getClientReferralTypes(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        GetClientReferralTypesRequest request);

    /**
     * Gets the active membership for a given client.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.client.GetActiveClientMembershipsResult
     */
    @WebMethod(operationName = "GetActiveClientMemberships", action = "http://clients.mindbodyonline.com/api/0_5_1/GetActiveClientMemberships")
    @WebResult(name = "GetActiveClientMembershipsResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "GetActiveClientMemberships", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.GetActiveClientMemberships")
    @ResponseWrapper(localName = "GetActiveClientMembershipsResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.GetActiveClientMembershipsResponse")
    public GetActiveClientMembershipsResult getActiveClientMemberships(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        GetActiveClientMembershipsRequest request);

    /**
     * Gets a list of contracts for a given client.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.client.GetClientContractsResult
     */
    @WebMethod(operationName = "GetClientContracts", action = "http://clients.mindbodyonline.com/api/0_5_1/GetClientContracts")
    @WebResult(name = "GetClientContractsResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "GetClientContracts", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.GetClientContracts")
    @ResponseWrapper(localName = "GetClientContractsResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.GetClientContractsResponse")
    public GetClientContractsResult getClientContracts(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        GetClientContractsRequest request);

    /**
     * Gets account balances for the given clients.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.client.GetClientAccountBalancesResult
     */
    @WebMethod(operationName = "GetClientAccountBalances", action = "http://clients.mindbodyonline.com/api/0_5_1/GetClientAccountBalances")
    @WebResult(name = "GetClientAccountBalancesResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "GetClientAccountBalances", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.GetClientAccountBalances")
    @ResponseWrapper(localName = "GetClientAccountBalancesResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.GetClientAccountBalancesResponse")
    public GetClientAccountBalancesResult getClientAccountBalances(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        GetClientAccountBalancesRequest request);

    /**
     * Gets a client service for a given client.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.client.GetClientServicesResult
     */
    @WebMethod(operationName = "GetClientServices", action = "http://clients.mindbodyonline.com/api/0_5_1/GetClientServices")
    @WebResult(name = "GetClientServicesResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "GetClientServices", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.GetClientServices")
    @ResponseWrapper(localName = "GetClientServicesResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.GetClientServicesResponse")
    public GetClientServicesResult getClientServices(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        GetClientServicesRequest request);

    /**
     * Get visits for a client.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.client.GetClientVisitsResult
     */
    @WebMethod(operationName = "GetClientVisits", action = "http://clients.mindbodyonline.com/api/0_5_1/GetClientVisits")
    @WebResult(name = "GetClientVisitsResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "GetClientVisits", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.GetClientVisits")
    @ResponseWrapper(localName = "GetClientVisitsResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.GetClientVisitsResponse")
    public GetClientVisitsResult getClientVisits(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        GetClientVisitsRequest request);

    /**
     * Get purchases for a client.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.client.GetClientPurchasesResult
     */
    @WebMethod(operationName = "GetClientPurchases", action = "http://clients.mindbodyonline.com/api/0_5_1/GetClientPurchases")
    @WebResult(name = "GetClientPurchasesResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "GetClientPurchases", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.GetClientPurchases")
    @ResponseWrapper(localName = "GetClientPurchasesResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.GetClientPurchasesResponse")
    public GetClientPurchasesResult getClientPurchases(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        GetClientPurchasesRequest request);

    /**
     * Get visits for a client.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.client.GetClientScheduleResult
     */
    @WebMethod(operationName = "GetClientSchedule", action = "http://clients.mindbodyonline.com/api/0_5_1/GetClientSchedule")
    @WebResult(name = "GetClientScheduleResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "GetClientSchedule", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.GetClientSchedule")
    @ResponseWrapper(localName = "GetClientScheduleResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.GetClientScheduleResponse")
    public GetClientScheduleResult getClientSchedule(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        GetClientScheduleRequest request);

    /**
     * Updates a client service for a given client.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.client.GetRequiredClientFieldsResult
     */
    @WebMethod(operationName = "GetRequiredClientFields", action = "http://clients.mindbodyonline.com/api/0_5_1/GetRequiredClientFields")
    @WebResult(name = "GetRequiredClientFieldsResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "GetRequiredClientFields", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.GetRequiredClientFields")
    @ResponseWrapper(localName = "GetRequiredClientFieldsResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.GetRequiredClientFieldsResponse")
    public GetRequiredClientFieldsResult getRequiredClientFields(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        GetRequiredClientFieldsRequest request);

    /**
     * Validates a username and password. This method returns the associated clients record and a session GUID on success.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.client.ValidateLoginResult
     */
    @WebMethod(operationName = "ValidateLogin", action = "http://clients.mindbodyonline.com/api/0_5_1/ValidateLogin")
    @WebResult(name = "ValidateLoginResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "ValidateLogin", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.ValidateLogin")
    @ResponseWrapper(localName = "ValidateLoginResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.ValidateLoginResponse")
    public ValidateLoginResult validateLogin(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        ValidateLoginRequest request);

    /**
     * Updates a client service for a given client.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.client.UpdateClientServicesResult
     */
    @WebMethod(operationName = "UpdateClientServices", action = "http://clients.mindbodyonline.com/api/0_5_1/UpdateClientServices")
    @WebResult(name = "UpdateClientServicesResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "UpdateClientServices", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.UpdateClientServices")
    @ResponseWrapper(localName = "UpdateClientServicesResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.UpdateClientServicesResponse")
    public UpdateClientServicesResult updateClientServices(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        UpdateClientServicesRequest request);

    /**
     * Sends the user a new password.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.client.ClientSendUserNewPasswordResult
     */
    @WebMethod(operationName = "SendUserNewPassword", action = "http://clients.mindbodyonline.com/api/0_5_1/SendUserNewPassword")
    @WebResult(name = "SendUserNewPasswordResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "SendUserNewPassword", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.SendUserNewPassword")
    @ResponseWrapper(localName = "SendUserNewPasswordResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.client.SendUserNewPasswordResponse")
    public ClientSendUserNewPasswordResult sendUserNewPassword(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        ClientSendUserNewPasswordRequest request);

}
