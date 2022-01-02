
package com.fitnessplayground.model.mindbody.api.appointment;

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
@WebService(name = "Appointment_x0020_ServiceSoap", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface AppointmentX0020ServiceSoap {


    /**
     * Gets a list of appointments that a given staff member is instructing.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.appointment.GetStaffAppointmentsResult
     */
    @WebMethod(operationName = "GetStaffAppointments", action = "http://clients.mindbodyonline.com/api/0_5_1/GetStaffAppointments")
    @WebResult(name = "GetStaffAppointmentsResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "GetStaffAppointments", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.appointment.GetStaffAppointments")
    @ResponseWrapper(localName = "GetStaffAppointmentsResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.appointment.GetStaffAppointmentsResponse")
    public GetStaffAppointmentsResult getStaffAppointments(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        GetStaffAppointmentsRequest request);

    /**
     * Adds or updates a list of appointments.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.appointment.AddOrUpdateAppointmentsResult
     */
    @WebMethod(operationName = "AddOrUpdateAppointments", action = "http://clients.mindbodyonline.com/api/0_5_1/AddOrUpdateAppointments")
    @WebResult(name = "AddOrUpdateAppointmentsResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "AddOrUpdateAppointments", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.appointment.AddOrUpdateAppointments")
    @ResponseWrapper(localName = "AddOrUpdateAppointmentsResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.appointment.AddOrUpdateAppointmentsResponse")
    public AddOrUpdateAppointmentsResult addOrUpdateAppointments(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        AddOrUpdateAppointmentsRequest request);

    /**
     * Gets a list of bookable items.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.appointment.GetBookableItemsResult
     */
    @WebMethod(operationName = "GetBookableItems", action = "http://clients.mindbodyonline.com/api/0_5_1/GetBookableItems")
    @WebResult(name = "GetBookableItemsResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "GetBookableItems", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.appointment.GetBookableItems")
    @ResponseWrapper(localName = "GetBookableItemsResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.appointment.GetBookableItemsResponse")
    public GetBookableItemsResult getBookableItems(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        GetBookableItemsRequest request);

    /**
     * Gets a list of scheduled items (appointments, availabilities, and unavailabilities).
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.appointment.GetScheduleItemsResult
     */
    @WebMethod(operationName = "GetScheduleItems", action = "http://clients.mindbodyonline.com/api/0_5_1/GetScheduleItems")
    @WebResult(name = "GetScheduleItemsResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "GetScheduleItems", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.appointment.GetScheduleItems")
    @ResponseWrapper(localName = "GetScheduleItemsResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.appointment.GetScheduleItemsResponse")
    public GetScheduleItemsResult getScheduleItems(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        GetScheduleItemsRequest request);

    /**
     * Adds or updates a list of availabilities.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.appointment.AddOrUpdateAvailabilitiesResult
     */
    @WebMethod(operationName = "AddOrUpdateAvailabilities", action = "http://clients.mindbodyonline.com/api/0_5_1/AddOrUpdateAvailabilities")
    @WebResult(name = "AddOrUpdateAvailabilitiesResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "AddOrUpdateAvailabilities", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.appointment.AddOrUpdateAvailabilities")
    @ResponseWrapper(localName = "AddOrUpdateAvailabilitiesResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.appointment.AddOrUpdateAvailabilitiesResponse")
    public AddOrUpdateAvailabilitiesResult addOrUpdateAvailabilities(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        AddOrUpdateAvailabilitiesRequest request);

    /**
     * Gets a list of times that are active for a given program ID.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.appointment.GetActiveSessionTimesResult
     */
    @WebMethod(operationName = "GetActiveSessionTimes", action = "http://clients.mindbodyonline.com/api/0_5_1/GetActiveSessionTimes")
    @WebResult(name = "GetActiveSessionTimesResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "GetActiveSessionTimes", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.appointment.GetActiveSessionTimes")
    @ResponseWrapper(localName = "GetActiveSessionTimesResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.appointment.GetActiveSessionTimesResponse")
    public GetActiveSessionTimesResult getActiveSessionTimes(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        GetActiveSessionTimesRequest request);

    /**
     * Gets a list appointment options.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.appointment.GetAppointmentOptionsResult
     */
    @WebMethod(operationName = "GetAppointmentOptions", action = "http://clients.mindbodyonline.com/api/0_5_1/GetAppointmentOptions")
    @WebResult(name = "GetAppointmentOptionsResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "GetAppointmentOptions", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.appointment.GetAppointmentOptions")
    @ResponseWrapper(localName = "GetAppointmentOptionsResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.appointment.GetAppointmentOptionsResponse")
    public GetAppointmentOptionsResult getAppointmentOptions(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        GetAppointmentOptionsRequest request);

}