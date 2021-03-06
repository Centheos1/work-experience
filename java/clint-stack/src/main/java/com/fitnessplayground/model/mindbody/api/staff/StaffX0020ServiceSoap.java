
package com.fitnessplayground.model.mindbody.api.staff;

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
@WebService(name = "Staff_x0020_ServiceSoap", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface StaffX0020ServiceSoap {


    /**
     * Gets a list of staff members.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.staff.GetStaffResult
     */
    @WebMethod(operationName = "GetStaff", action = "http://clients.mindbodyonline.com/api/0_5_1/GetStaff")
    @WebResult(name = "GetStaffResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "GetStaff", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.staff.GetStaff")
    @ResponseWrapper(localName = "GetStaffResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.staff.GetStaffResponse")
    public GetStaffResult getStaff(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        GetStaffRequest request);

    /**
     * Gets a list of staff permissions based on the given staff member.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.staff.GetStaffPermissionsResult
     */
    @WebMethod(operationName = "GetStaffPermissions", action = "http://clients.mindbodyonline.com/api/0_5_1/GetStaffPermissions")
    @WebResult(name = "GetStaffPermissionsResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "GetStaffPermissions", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.staff.GetStaffPermissions")
    @ResponseWrapper(localName = "GetStaffPermissionsResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.staff.GetStaffPermissionsResponse")
    public GetStaffPermissionsResult getStaffPermissions(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        GetStaffPermissionsRequest request);

    /**
     * Add or update staff.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.staff.AddOrUpdateStaffResult
     */
    @WebMethod(operationName = "AddOrUpdateStaff", action = "http://clients.mindbodyonline.com/api/0_5_1/AddOrUpdateStaff")
    @WebResult(name = "AddOrUpdateStaffResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "AddOrUpdateStaff", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.staff.AddOrUpdateStaff")
    @ResponseWrapper(localName = "AddOrUpdateStaffResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.staff.AddOrUpdateStaffResponse")
    public AddOrUpdateStaffResult addOrUpdateStaff(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        AddOrUpdateStaffRequest request);

    /**
     * Gets a staff member's image URL if it exists.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.staff.GetStaffImgURLResult
     */
    @WebMethod(operationName = "GetStaffImgURL", action = "http://clients.mindbodyonline.com/api/0_5_1/GetStaffImgURL")
    @WebResult(name = "GetStaffImgURLResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "GetStaffImgURL", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.staff.GetStaffImgURL")
    @ResponseWrapper(localName = "GetStaffImgURLResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.staff.GetStaffImgURLResponse")
    public GetStaffImgURLResult getStaffImgURL(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        GetStaffImgURLRequest request);

    /**
     * Validates a username and password. This method returns the staff on success.
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.staff.ValidateLoginResult
     */
    @WebMethod(operationName = "ValidateStaffLogin", action = "http://clients.mindbodyonline.com/api/0_5_1/ValidateStaffLogin")
    @WebResult(name = "ValidateStaffLoginResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "ValidateStaffLogin", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.staff.ValidateStaffLogin")
    @ResponseWrapper(localName = "ValidateStaffLoginResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.staff.ValidateStaffLoginResponse")
    public ValidateLoginResult validateStaffLogin(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        ValidateLoginRequest request);

    /**
     * Gets a list of sales reps based on the requested rep IDs
     * 
     * @param request
     * @return
     *     returns com.fitnessplayground.model.mindbody.api.staff.GetSalesRepsResult
     */
    @WebMethod(operationName = "GetSalesReps", action = "http://clients.mindbodyonline.com/api/0_5_1/GetSalesReps")
    @WebResult(name = "GetSalesRepsResult", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
    @RequestWrapper(localName = "GetSalesReps", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.staff.GetSalesReps")
    @ResponseWrapper(localName = "GetSalesRepsResponse", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1", className = "com.fitnessplayground.model.mindbody.api.staff.GetSalesRepsResponse")
    public GetSalesRepsResult getSalesReps(
        @WebParam(name = "Request", targetNamespace = "http://clients.mindbodyonline.com/api/0_5_1")
        GetSalesRepsRequest request);

}
