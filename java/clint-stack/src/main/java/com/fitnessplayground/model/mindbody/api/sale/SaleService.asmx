<?xml version="1.0" encoding="utf-8"?>
<wsdl:definitions xmlns:tm="http://microsoft.com/wsdl/mime/textMatching/" xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/" xmlns:mime="http://schemas.xmlsoap.org/wsdl/mime/" xmlns:tns="http://clients.mindbodyonline.com/api/0_5" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:s="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/" xmlns:http="http://schemas.xmlsoap.org/wsdl/http/" targetNamespace="http://clients.mindbodyonline.com/api/0_5" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">
  <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Provides methods and attributes relating to sales.</wsdl:documentation>
  <wsdl:types>
    <s:schema elementFormDefault="qualified" targetNamespace="http://clients.mindbodyonline.com/api/0_5">
      <s:element name="GetAcceptedCardType">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetAcceptedCardTypeRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetAcceptedCardTypeRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest" />
        </s:complexContent>
      </s:complexType>
      <s:complexType name="MBRequest">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="SourceCredentials" type="tns:SourceCredentials" />
          <s:element minOccurs="0" maxOccurs="1" name="UserCredentials" type="tns:UserCredentials" />
          <s:element minOccurs="1" maxOccurs="1" name="XMLDetail" nillable="true" type="tns:XMLDetailLevel" />
          <s:element minOccurs="1" maxOccurs="1" name="PageSize" nillable="true" type="s:int" />
          <s:element minOccurs="1" maxOccurs="1" name="CurrentPageIndex" nillable="true" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Fields" type="tns:ArrayOfString" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="SourceCredentials">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="SourceName" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Password" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="SiteIDs" type="tns:ArrayOfInt" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ArrayOfInt">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="int" type="s:int" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="UserCredentials">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Username" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Password" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="SiteIDs" type="tns:ArrayOfInt" />
          <s:element minOccurs="1" maxOccurs="1" name="LocationID" nillable="true" type="s:int" />
        </s:sequence>
      </s:complexType>
      <s:simpleType name="XMLDetailLevel">
        <s:restriction base="s:string">
          <s:enumeration value="Bare" />
          <s:enumeration value="Basic" />
          <s:enumeration value="Full" />
        </s:restriction>
      </s:simpleType>
      <s:complexType name="ArrayOfString">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="string" nillable="true" type="s:string" />
        </s:sequence>
      </s:complexType>
      <s:element name="GetAcceptedCardTypeResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetAcceptedCardTypeResult" type="tns:GetAcceptedCardTypeResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetAcceptedCardTypeResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="CardTypes" type="tns:ArrayOfString" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="MBResult">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="Status" type="tns:StatusCode" />
          <s:element minOccurs="1" maxOccurs="1" name="ErrorCode" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Message" type="s:string" />
          <s:element minOccurs="1" maxOccurs="1" name="XMLDetail" type="tns:XMLDetailLevel" />
          <s:element minOccurs="1" maxOccurs="1" name="ResultCount" type="s:int" />
          <s:element minOccurs="1" maxOccurs="1" name="CurrentPageIndex" type="s:int" />
          <s:element minOccurs="1" maxOccurs="1" name="TotalPageCount" type="s:int" />
        </s:sequence>
      </s:complexType>
      <s:simpleType name="StatusCode">
        <s:restriction base="s:string">
          <s:enumeration value="Success" />
          <s:enumeration value="InvalidCredentials" />
          <s:enumeration value="InvalidParameters" />
          <s:enumeration value="InternalException" />
          <s:enumeration value="Unknown" />
          <s:enumeration value="FailedAction" />
        </s:restriction>
      </s:simpleType>
      <s:element name="CheckoutShoppingCart">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:CheckoutShoppingCartRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="CheckoutShoppingCartRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="CartID" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="ClientID" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="Test" nillable="true" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="CartItems" type="tns:ArrayOfCartItem" />
              <s:element minOccurs="1" maxOccurs="1" name="InStore" nillable="true" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="PromotionCode" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Payments" type="tns:ArrayOfPaymentInfo" />
              <s:element minOccurs="1" maxOccurs="1" name="SendEmail" nillable="true" type="s:boolean" />
              <s:element minOccurs="1" maxOccurs="1" name="LocationID" nillable="true" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="Image" type="s:base64Binary" />
              <s:element minOccurs="0" maxOccurs="1" name="ImageFileName" type="s:string" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfCartItem">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="CartItem" nillable="true" type="tns:CartItem" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="CartItem">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBObject">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Item" type="tns:Item" />
              <s:element minOccurs="1" maxOccurs="1" name="DiscountAmount" type="s:decimal" />
              <s:element minOccurs="0" maxOccurs="1" name="Appointments" type="tns:ArrayOfAppointment" />
              <s:element minOccurs="0" maxOccurs="1" name="EnrollmentIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="0" maxOccurs="1" name="ClassIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="0" maxOccurs="1" name="CourseIDs" type="tns:ArrayOfLong" />
              <s:element minOccurs="0" maxOccurs="1" name="VisitIDs" type="tns:ArrayOfLong" />
              <s:element minOccurs="0" maxOccurs="1" name="AppointmentIDs" type="tns:ArrayOfLong" />
              <s:element minOccurs="0" maxOccurs="1" name="Action" type="tns:ActionCode" />
              <s:element minOccurs="0" maxOccurs="1" name="ID" nillable="true" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="Quantity" nillable="true" type="s:int" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="MBObject">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Site" type="tns:Site" />
          <s:element minOccurs="0" maxOccurs="1" name="Messages" type="tns:ArrayOfString" />
          <s:element minOccurs="0" maxOccurs="1" name="Execute" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="ErrorCode" type="s:string" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="Site">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="ID" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Description" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="LogoURL" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="PageColor1" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="PageColor2" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="PageColor3" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="PageColor4" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="AcceptsVisa" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="AcceptsDiscover" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="AcceptsMasterCard" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="AcceptsAmericanExpress" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="ContactEmail" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="ESA" nillable="true" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="TotalWOD" nillable="true" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="TaxInclusivePrices" nillable="true" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="SMSPackageEnabled" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="AllowsDashboardAccess" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="PricingLevel" type="s:string" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ClassSchedule">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBObject">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Classes" type="tns:ArrayOfClass" />
              <s:element minOccurs="0" maxOccurs="1" name="Clients" type="tns:ArrayOfClient" />
              <s:element minOccurs="0" maxOccurs="1" name="Course" type="tns:Course" />
              <s:element minOccurs="0" maxOccurs="1" name="SemesterID" nillable="true" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="IsAvailable" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="Action" type="tns:ActionCode" />
              <s:element minOccurs="0" maxOccurs="1" name="ID" nillable="true" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="ClassDescription" type="tns:ClassDescription" />
              <s:element minOccurs="0" maxOccurs="1" name="DaySunday" nillable="true" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="DayMonday" nillable="true" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="DayTuesday" nillable="true" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="DayWednesday" nillable="true" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="DayThursday" nillable="true" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="DayFriday" nillable="true" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="DaySaturday" nillable="true" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="AllowOpenEnrollment" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="AllowDateForwardEnrollment" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="StartTime" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="EndTime" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="StartDate" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="EndDate" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="Staff" type="tns:Staff" />
              <s:element minOccurs="0" maxOccurs="1" name="Location" type="tns:Location" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfClass">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Class" nillable="true" type="tns:Class" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="Class">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBObject">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ClassScheduleID" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="Visits" type="tns:ArrayOfVisit" />
              <s:element minOccurs="0" maxOccurs="1" name="Clients" type="tns:ArrayOfClient" />
              <s:element minOccurs="0" maxOccurs="1" name="Location" type="tns:Location" />
              <s:element minOccurs="0" maxOccurs="1" name="Resource" type="tns:Resource" />
              <s:element minOccurs="1" maxOccurs="1" name="MaxCapacity" nillable="true" type="s:int" />
              <s:element minOccurs="1" maxOccurs="1" name="WebCapacity" nillable="true" type="s:int" />
              <s:element minOccurs="1" maxOccurs="1" name="TotalBooked" nillable="true" type="s:int" />
              <s:element minOccurs="1" maxOccurs="1" name="TotalBookedWaitlist" nillable="true" type="s:int" />
              <s:element minOccurs="1" maxOccurs="1" name="WebBooked" nillable="true" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="SemesterID" nillable="true" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="IsCanceled" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="Substitute" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="Active" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="IsWaitlistAvailable" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="IsEnrolled" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="HideCancel" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="Action" type="tns:ActionCode" />
              <s:element minOccurs="0" maxOccurs="1" name="ID" nillable="true" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="IsAvailable" nillable="true" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="StartDateTime" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="EndDateTime" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="ClassDescription" type="tns:ClassDescription" />
              <s:element minOccurs="0" maxOccurs="1" name="Staff" type="tns:Staff" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfVisit">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Visit" nillable="true" type="tns:Visit" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="Visit">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBObject">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ID" type="s:long" />
              <s:element minOccurs="0" maxOccurs="1" name="ClassID" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="AppointmentID" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="AppointmentGenderPreference" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="StartDateTime" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="LateCancelled" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="EndDateTime" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Staff" type="tns:Staff" />
              <s:element minOccurs="0" maxOccurs="1" name="Location" type="tns:Location" />
              <s:element minOccurs="0" maxOccurs="1" name="Client" type="tns:Client" />
              <s:element minOccurs="0" maxOccurs="1" name="WebSignup" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="Action" type="tns:ActionCode" />
              <s:element minOccurs="0" maxOccurs="1" name="SignedIn" nillable="true" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="AppointmentStatus" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="MakeUp" nillable="true" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="Service" type="tns:ClientService" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="Staff">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBObject">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Appointments" type="tns:ArrayOfAppointment" />
              <s:element minOccurs="0" maxOccurs="1" name="Unavailabilities" type="tns:ArrayOfUnavailability" />
              <s:element minOccurs="0" maxOccurs="1" name="Availabilities" type="tns:ArrayOfAvailability" />
              <s:element minOccurs="0" maxOccurs="1" name="Email" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="MobilePhone" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="HomePhone" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="WorkPhone" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Address" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Address2" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="City" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="State" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Country" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="PostalCode" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="ForeignZip" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="SortOrder" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="LoginLocations" type="tns:ArrayOfLocation" />
              <s:element minOccurs="0" maxOccurs="1" name="MultiLocation" nillable="true" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="AppointmentTrn" nillable="true" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="ReservationTrn" nillable="true" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="IndependentContractor" nillable="true" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="AlwaysAllowDoubleBooking" nillable="true" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="UserAccessLevel" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="ProviderIDs" type="tns:ArrayOfString" />
              <s:element minOccurs="0" maxOccurs="1" name="ProviderIDUpdateList" type="tns:ArrayOfProviderIDUpdate" />
              <s:element minOccurs="0" maxOccurs="1" name="Action" type="tns:ActionCode" />
              <s:element minOccurs="0" maxOccurs="1" name="ID" nillable="true" type="s:long" />
              <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="FirstName" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="LastName" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="ImageURL" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Bio" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="isMale" type="s:boolean" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfAppointment">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Appointment" nillable="true" type="tns:Appointment" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="Appointment">
        <s:complexContent mixed="false">
          <s:extension base="tns:ScheduleItem">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="GenderPreference" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="Duration" nillable="true" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="ProviderID" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Action" type="tns:ActionCode" />
              <s:element minOccurs="0" maxOccurs="1" name="ID" nillable="true" type="s:long" />
              <s:element minOccurs="0" maxOccurs="1" name="Status" type="tns:AppointmentStatus" />
              <s:element minOccurs="0" maxOccurs="1" name="StartDateTime" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="EndDateTime" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="Notes" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="StaffRequested" nillable="true" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="Program" type="tns:Program" />
              <s:element minOccurs="0" maxOccurs="1" name="SessionType" type="tns:SessionType" />
              <s:element minOccurs="0" maxOccurs="1" name="Location" type="tns:Location" />
              <s:element minOccurs="0" maxOccurs="1" name="Staff" type="tns:Staff" />
              <s:element minOccurs="0" maxOccurs="1" name="Client" type="tns:Client" />
              <s:element minOccurs="0" maxOccurs="1" name="FirstAppointment" nillable="true" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="ClientService" type="tns:ClientService" />
              <s:element minOccurs="0" maxOccurs="1" name="Resources" type="tns:ArrayOfResource" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ScheduleItem">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBObject" />
        </s:complexContent>
      </s:complexType>
      <s:complexType name="Unavailability">
        <s:complexContent mixed="false">
          <s:extension base="tns:ScheduleItem">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="ID" type="s:int" />
              <s:element minOccurs="1" maxOccurs="1" name="StartDateTime" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="EndDateTime" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="Description" type="s:string" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="Availability">
        <s:complexContent mixed="false">
          <s:extension base="tns:ScheduleItem">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="ID" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="Staff" type="tns:Staff" />
              <s:element minOccurs="0" maxOccurs="1" name="SessionType" type="tns:SessionType" />
              <s:element minOccurs="0" maxOccurs="1" name="Programs" type="tns:ArrayOfProgram" />
              <s:element minOccurs="1" maxOccurs="1" name="StartDateTime" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="EndDateTime" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="BookableEndDateTime" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="Location" type="tns:Location" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="SessionType">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBObject">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="DefaultTimeLength" nillable="true" type="s:int" />
              <s:element minOccurs="1" maxOccurs="1" name="ProgramID" nillable="true" type="s:int" />
              <s:element minOccurs="1" maxOccurs="1" name="NumDeducted" nillable="true" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="Action" type="tns:ActionCode" />
              <s:element minOccurs="0" maxOccurs="1" name="ID" nillable="true" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:simpleType name="ActionCode">
        <s:restriction base="s:string">
          <s:enumeration value="None" />
          <s:enumeration value="Added" />
          <s:enumeration value="Updated" />
          <s:enumeration value="Failed" />
          <s:enumeration value="Removed" />
        </s:restriction>
      </s:simpleType>
      <s:complexType name="ArrayOfProgram">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Program" nillable="true" type="tns:Program" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="Program">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBObject">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="ID" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="ScheduleType" nillable="true" type="tns:ScheduleType" />
              <s:element minOccurs="1" maxOccurs="1" name="CancelOffset" nillable="true" type="s:int" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:simpleType name="ScheduleType">
        <s:restriction base="s:string">
          <s:enumeration value="All" />
          <s:enumeration value="DropIn" />
          <s:enumeration value="Enrollment" />
          <s:enumeration value="Appointment" />
          <s:enumeration value="Resource" />
          <s:enumeration value="Media" />
          <s:enumeration value="Arrival" />
        </s:restriction>
      </s:simpleType>
      <s:complexType name="Location">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBObject">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="BusinessID" nillable="true" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="SiteID" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="BusinessDescription" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="AdditionalImageURLs" type="tns:ArrayOfString" />
              <s:element minOccurs="0" maxOccurs="1" name="FacilitySquareFeet" nillable="true" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="TreatmentRooms" nillable="true" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="ProSpaFinderSite" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="HasClasses" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="PhoneExtension" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Action" type="tns:ActionCode" />
              <s:element minOccurs="0" maxOccurs="1" name="ID" nillable="true" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Address" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Address2" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Tax1" nillable="true" type="s:float" />
              <s:element minOccurs="0" maxOccurs="1" name="Tax2" nillable="true" type="s:float" />
              <s:element minOccurs="0" maxOccurs="1" name="Tax3" nillable="true" type="s:float" />
              <s:element minOccurs="0" maxOccurs="1" name="Tax4" nillable="true" type="s:float" />
              <s:element minOccurs="0" maxOccurs="1" name="Tax5" nillable="true" type="s:float" />
              <s:element minOccurs="0" maxOccurs="1" name="Phone" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="City" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="StateProvCode" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="PostalCode" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Latitude" nillable="true" type="s:double" />
              <s:element minOccurs="0" maxOccurs="1" name="Longitude" nillable="true" type="s:double" />
              <s:element minOccurs="0" maxOccurs="1" name="DistanceInMiles" nillable="true" type="s:double" />
              <s:element minOccurs="0" maxOccurs="1" name="ImageURL" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Description" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="HasSite" nillable="true" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="CanBook" nillable="true" type="s:boolean" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:simpleType name="AppointmentStatus">
        <s:restriction base="s:string">
          <s:enumeration value="Booked" />
          <s:enumeration value="Completed" />
          <s:enumeration value="Confirmed" />
          <s:enumeration value="Arrived" />
          <s:enumeration value="NoShow" />
          <s:enumeration value="Cancelled" />
          <s:enumeration value="LateCancelled" />
        </s:restriction>
      </s:simpleType>
      <s:complexType name="Client">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBObject">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="NewID" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="AccountBalance" type="s:double" />
              <s:element minOccurs="0" maxOccurs="1" name="ClientIndexes" type="tns:ArrayOfClientIndex" />
              <s:element minOccurs="0" maxOccurs="1" name="Username" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Password" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Notes" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="MobileProvider" nillable="true" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="ClientCreditCard" type="tns:ClientCreditCard" />
              <s:element minOccurs="0" maxOccurs="1" name="LastFormulaNotes" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="AppointmentGenderPreference" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Gender" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="IsCompany" nillable="true" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="Inactive" nillable="true" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="ClientRelationships" type="tns:ArrayOfClientRelationship" />
              <s:element minOccurs="0" maxOccurs="1" name="Reps" type="tns:ArrayOfRep" />
              <s:element minOccurs="0" maxOccurs="1" name="SaleReps" type="tns:ArrayOfSalesRep" />
              <s:element minOccurs="0" maxOccurs="1" name="CustomClientFields" type="tns:ArrayOfCustomClientField" />
              <s:element minOccurs="0" maxOccurs="1" name="LiabilityRelease" nillable="true" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="EmergencyContactInfoName" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="EmergencyContactInfoRelationship" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="EmergencyContactInfoPhone" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="EmergencyContactInfoEmail" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="PromotionalEmailOptIn" nillable="true" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="CreationDate" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="Liability" type="tns:Liability" />
              <s:element minOccurs="0" maxOccurs="1" name="ProspectStage" type="tns:ProspectStage" />
              <s:element minOccurs="0" maxOccurs="1" name="UniqueID" type="s:long" />
              <s:element minOccurs="0" maxOccurs="1" name="MembershipIcon" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="Action" type="tns:ActionCode" />
              <s:element minOccurs="0" maxOccurs="1" name="ID" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="FirstName" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="MiddleName" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="LastName" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Email" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="EmailOptIn" nillable="true" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="AddressLine1" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="AddressLine2" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="City" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="State" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="PostalCode" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Country" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="MobilePhone" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="HomePhone" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="WorkPhone" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="WorkExtension" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="BirthDate" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="FirstAppointmentDate" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="ReferredBy" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="HomeLocation" type="tns:Location" />
              <s:element minOccurs="0" maxOccurs="1" name="YellowAlert" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="RedAlert" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="PhotoURL" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="IsProspect" nillable="true" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="Status" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="ContactMethod" nillable="true" type="s:short" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfClientIndex">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="ClientIndex" nillable="true" type="tns:ClientIndex" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ClientIndex">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBObject">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="RequiredBusinessMode" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="RequiredConsumerMode" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="Action" type="tns:ActionCode" />
              <s:element minOccurs="0" maxOccurs="1" name="ID" nillable="true" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Values" type="tns:ArrayOfClientIndexValue" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfClientIndexValue">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="ClientIndexValue" nillable="true" type="tns:ClientIndexValue" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ClientIndexValue">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBObject">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Action" type="tns:ActionCode" />
              <s:element minOccurs="0" maxOccurs="1" name="ID" nillable="true" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Active" type="s:boolean" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ClientCreditCard">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="CardType" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="LastFour" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="CardNumber" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="CardHolder" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="ExpMonth" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="ExpYear" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Address" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="City" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="State" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="PostalCode" type="s:string" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ArrayOfClientRelationship">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="ClientRelationship" nillable="true" type="tns:ClientRelationship" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ClientRelationship">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBObject">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="RelatedClient" type="tns:Client" />
              <s:element minOccurs="0" maxOccurs="1" name="Relationship" type="tns:Relationship" />
              <s:element minOccurs="0" maxOccurs="1" name="RelationshipName" type="s:string" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="Relationship">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="ID" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="RelationshipName1" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="RelationshipName2" type="s:string" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ArrayOfRep">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Rep" nillable="true" type="tns:Rep" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="Rep">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBObject">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="ID" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="Staff" type="tns:Staff" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfSalesRep">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="SalesRep" nillable="true" type="tns:SalesRep" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="SalesRep">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBObject">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="SalesRepNumber" nillable="true" type="s:int" />
              <s:element minOccurs="1" maxOccurs="1" name="ID" nillable="true" type="s:long" />
              <s:element minOccurs="0" maxOccurs="1" name="FirstName" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="LastName" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="SalesRepNumbers" type="tns:ArrayOfInt" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfCustomClientField">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="CustomClientField" nillable="true" type="tns:CustomClientField" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="CustomClientField">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="ID" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="DataType" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Value" type="s:string" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="Liability">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="IsReleased" type="s:boolean" />
          <s:element minOccurs="1" maxOccurs="1" name="AgreementDate" nillable="true" type="s:dateTime" />
          <s:element minOccurs="1" maxOccurs="1" name="ReleasedBy" nillable="true" type="s:long" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ProspectStage">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="ID" nillable="true" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Description" type="s:string" />
          <s:element minOccurs="1" maxOccurs="1" name="Active" type="s:boolean" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ClientService">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBObject">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="Current" type="s:boolean" />
              <s:element minOccurs="1" maxOccurs="1" name="Count" type="s:int" />
              <s:element minOccurs="1" maxOccurs="1" name="Remaining" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="Action" type="tns:ActionCode" />
              <s:element minOccurs="0" maxOccurs="1" name="ID" nillable="true" type="s:long" />
              <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="PaymentDate" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="ActiveDate" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="ExpirationDate" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="Program" type="tns:Program" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfResource">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Resource" nillable="true" type="tns:Resource" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="Resource">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBObject">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Action" type="tns:ActionCode" />
              <s:element minOccurs="0" maxOccurs="1" name="ID" nillable="true" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfUnavailability">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Unavailability" nillable="true" type="tns:Unavailability" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ArrayOfAvailability">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Availability" nillable="true" type="tns:Availability" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ArrayOfLocation">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Location" nillable="true" type="tns:Location" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ArrayOfProviderIDUpdate">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="ProviderIDUpdate" nillable="true" type="tns:ProviderIDUpdate" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ProviderIDUpdate">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="OldProviderID" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="NewProviderID" type="s:string" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ArrayOfClient">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Client" nillable="true" type="tns:Client" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ClassDescription">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBObject">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ImageURL" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Level" type="tns:Level" />
              <s:element minOccurs="0" maxOccurs="1" name="Action" type="tns:ActionCode" />
              <s:element minOccurs="0" maxOccurs="1" name="ID" nillable="true" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Description" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Prereq" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Notes" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="LastUpdated" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="Program" type="tns:Program" />
              <s:element minOccurs="0" maxOccurs="1" name="SessionType" type="tns:SessionType" />
              <s:element minOccurs="0" maxOccurs="1" name="Active" type="s:boolean" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="Level">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="ID" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Description" type="s:string" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="Course">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="ID" type="s:long" />
          <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Description" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Notes" type="s:string" />
          <s:element minOccurs="1" maxOccurs="1" name="StartDate" type="s:dateTime" />
          <s:element minOccurs="1" maxOccurs="1" name="EndDate" type="s:dateTime" />
          <s:element minOccurs="0" maxOccurs="1" name="Location" type="tns:Location" />
          <s:element minOccurs="0" maxOccurs="1" name="Organizer" type="tns:Staff" />
          <s:element minOccurs="0" maxOccurs="1" name="Program" type="tns:Program" />
          <s:element minOccurs="0" maxOccurs="1" name="ImageURL" type="s:string" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ShoppingCart">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBObject">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="AuthCode" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Action" type="tns:ActionCode" />
              <s:element minOccurs="0" maxOccurs="1" name="ID" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="CartItems" type="tns:ArrayOfCartItem" />
              <s:element minOccurs="0" maxOccurs="1" name="SubTotal" nillable="true" type="s:double" />
              <s:element minOccurs="0" maxOccurs="1" name="DiscountTotal" nillable="true" type="s:double" />
              <s:element minOccurs="0" maxOccurs="1" name="TaxTotal" nillable="true" type="s:double" />
              <s:element minOccurs="0" maxOccurs="1" name="GrandTotal" nillable="true" type="s:double" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="Size">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBObject">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Action" type="tns:ActionCode" />
              <s:element minOccurs="0" maxOccurs="1" name="ID" nillable="true" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="Color">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBObject">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Action" type="tns:ActionCode" />
              <s:element minOccurs="0" maxOccurs="1" name="ID" nillable="true" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="Item">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBObject" />
        </s:complexContent>
      </s:complexType>
      <s:complexType name="Tip">
        <s:complexContent mixed="false">
          <s:extension base="tns:Item">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Amount" type="s:decimal" />
              <s:element minOccurs="0" maxOccurs="1" name="StaffID" type="s:long" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="Package">
        <s:complexContent mixed="false">
          <s:extension base="tns:Item">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="ID" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="DiscountPercentage" type="s:double" />
              <s:element minOccurs="1" maxOccurs="1" name="SellOnline" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="Services" type="tns:ArrayOfService" />
              <s:element minOccurs="0" maxOccurs="1" name="Products" type="tns:ArrayOfProduct" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfService">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Service" nillable="true" type="tns:Service" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="Service">
        <s:complexContent mixed="false">
          <s:extension base="tns:Item">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Price" nillable="true" type="s:decimal" />
              <s:element minOccurs="0" maxOccurs="1" name="OnlinePrice" nillable="true" type="s:decimal" />
              <s:element minOccurs="0" maxOccurs="1" name="TaxIncluded" nillable="true" type="s:decimal" />
              <s:element minOccurs="0" maxOccurs="1" name="ProgramID" nillable="true" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="TaxRate" type="s:decimal" />
              <s:element minOccurs="0" maxOccurs="1" name="ProductID" type="s:double" />
              <s:element minOccurs="0" maxOccurs="1" name="Action" type="tns:ActionCode" />
              <s:element minOccurs="0" maxOccurs="1" name="ID" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Count" nillable="true" type="s:int" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfProduct">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Product" nillable="true" type="tns:Product" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="Product">
        <s:complexContent mixed="false">
          <s:extension base="tns:Item">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Price" nillable="true" type="s:decimal" />
              <s:element minOccurs="0" maxOccurs="1" name="TaxIncluded" nillable="true" type="s:decimal" />
              <s:element minOccurs="0" maxOccurs="1" name="TaxRate" type="s:decimal" />
              <s:element minOccurs="0" maxOccurs="1" name="Action" type="tns:ActionCode" />
              <s:element minOccurs="0" maxOccurs="1" name="ID" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="GroupID" nillable="true" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="OnlinePrice" nillable="true" type="s:decimal" />
              <s:element minOccurs="0" maxOccurs="1" name="ShortDesc" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="LongDesc" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Color" type="tns:Color" />
              <s:element minOccurs="0" maxOccurs="1" name="Size" type="tns:Size" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfLong">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="long" type="s:long" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ArrayOfPaymentInfo">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="PaymentInfo" nillable="true" type="tns:PaymentInfo" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="PaymentInfo">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="GiftCardInfo">
        <s:complexContent mixed="false">
          <s:extension base="tns:PaymentInfo">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="Amount" type="s:decimal" />
              <s:element minOccurs="0" maxOccurs="1" name="Notes" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="CardNumber" type="s:string" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="CheckInfo">
        <s:complexContent mixed="false">
          <s:extension base="tns:PaymentInfo">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="Amount" type="s:decimal" />
              <s:element minOccurs="0" maxOccurs="1" name="Notes" type="s:string" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="CashInfo">
        <s:complexContent mixed="false">
          <s:extension base="tns:PaymentInfo">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="Amount" type="s:decimal" />
              <s:element minOccurs="0" maxOccurs="1" name="Notes" type="s:string" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="CompInfo">
        <s:complexContent mixed="false">
          <s:extension base="tns:PaymentInfo">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="Amount" type="s:decimal" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="TrackDataInfo">
        <s:complexContent mixed="false">
          <s:extension base="tns:PaymentInfo">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="Amount" type="s:decimal" />
              <s:element minOccurs="0" maxOccurs="1" name="TrackData" type="s:string" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="StoredCardInfo">
        <s:complexContent mixed="false">
          <s:extension base="tns:PaymentInfo">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="Amount" type="s:decimal" />
              <s:element minOccurs="0" maxOccurs="1" name="LastFour" type="s:string" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="EncryptedTrackDataInfo">
        <s:complexContent mixed="false">
          <s:extension base="tns:PaymentInfo">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="Amount" type="s:decimal" />
              <s:element minOccurs="0" maxOccurs="1" name="TrackData" type="s:string" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="CustomPaymentInfo">
        <s:complexContent mixed="false">
          <s:extension base="tns:PaymentInfo">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="Amount" type="s:decimal" />
              <s:element minOccurs="1" maxOccurs="1" name="ID" type="s:int" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="DebitAccountInfo">
        <s:complexContent mixed="false">
          <s:extension base="tns:PaymentInfo">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="Amount" type="s:decimal" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="CreditCardInfo">
        <s:complexContent mixed="false">
          <s:extension base="tns:PaymentInfo">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="CVV" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Action" type="tns:ActionCode" />
              <s:element minOccurs="0" maxOccurs="1" name="CreditCardNumber" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Amount" nillable="true" type="s:decimal" />
              <s:element minOccurs="0" maxOccurs="1" name="ExpMonth" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="ExpYear" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="BillingName" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="BillingAddress" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="BillingCity" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="BillingState" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="BillingPostalCode" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="SaveInfo" nillable="true" type="s:boolean" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="CheckoutShoppingCartResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="CheckoutShoppingCartResult" type="tns:CheckoutShoppingCartResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="CheckoutShoppingCartResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ShoppingCart" type="tns:ShoppingCart" />
              <s:element minOccurs="0" maxOccurs="1" name="Classes" type="tns:ArrayOfClass" />
              <s:element minOccurs="0" maxOccurs="1" name="Appointments" type="tns:ArrayOfAppointment" />
              <s:element minOccurs="0" maxOccurs="1" name="Enrollments" type="tns:ArrayOfClassSchedule" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfClassSchedule">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="ClassSchedule" nillable="true" type="tns:ClassSchedule" />
        </s:sequence>
      </s:complexType>
      <s:element name="GetSales">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetSalesRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetSalesRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="SaleID" nillable="true" type="s:long" />
              <s:element minOccurs="1" maxOccurs="1" name="StartSaleDateTime" nillable="true" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="EndSaleDateTime" nillable="true" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="PaymentMethodID" nillable="true" type="s:int" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetSalesResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetSalesResult" type="tns:GetSalesResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetSalesResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Sales" type="tns:ArrayOfSale" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfSale">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Sale" nillable="true" type="tns:Sale" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="Sale">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="ID" type="s:long" />
          <s:element minOccurs="1" maxOccurs="1" name="SaleTime" type="s:dateTime" />
          <s:element minOccurs="1" maxOccurs="1" name="SaleDate" type="s:dateTime" />
          <s:element minOccurs="1" maxOccurs="1" name="SaleDateTime" type="s:dateTime" />
          <s:element minOccurs="0" maxOccurs="1" name="Location" type="tns:Location" />
          <s:element minOccurs="0" maxOccurs="1" name="Payments" type="tns:ArrayOfPayment" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ArrayOfPayment">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Payment" nillable="true" type="tns:Payment" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="Payment">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="ID" type="s:long" />
          <s:element minOccurs="1" maxOccurs="1" name="Amount" type="s:decimal" />
          <s:element minOccurs="1" maxOccurs="1" name="Method" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Type" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="LastFour" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Notes" type="s:string" />
        </s:sequence>
      </s:complexType>
      <s:element name="GetServices">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetServicesRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetServicesRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ProgramIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="0" maxOccurs="1" name="SessionTypeIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="0" maxOccurs="1" name="ServiceIDs" type="tns:ArrayOfString" />
              <s:element minOccurs="1" maxOccurs="1" name="ClassID" nillable="true" type="s:int" />
              <s:element minOccurs="1" maxOccurs="1" name="ClassScheduleID" nillable="true" type="s:int" />
              <s:element minOccurs="1" maxOccurs="1" name="SellOnline" nillable="true" type="s:boolean" />
              <s:element minOccurs="1" maxOccurs="1" name="LocationID" type="s:int" />
              <s:element minOccurs="1" maxOccurs="1" name="HideRelatedPrograms" type="s:boolean" />
              <s:element minOccurs="1" maxOccurs="1" name="StaffID" nillable="true" type="s:long" />
              <s:element minOccurs="1" maxOccurs="1" name="UseOnlineStoreTaxRate" nillable="true" type="s:boolean" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetServicesResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetServicesResult" type="tns:GetServicesResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetServicesResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Services" type="tns:ArrayOfService" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="UpdateServices">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:UpdateServicesRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="UpdateServicesRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Services" type="tns:ArrayOfService" />
              <s:element minOccurs="1" maxOccurs="1" name="Test" nillable="true" type="s:boolean" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="UpdateServicesResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="UpdateServicesResult" type="tns:UpdateServicesResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="UpdateServicesResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Services" type="tns:ArrayOfService" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetPackages">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetPackagesRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetPackagesRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="PackageIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="1" maxOccurs="1" name="SellOnline" type="s:boolean" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetPackagesResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetPackagesResult" type="tns:GetPackagesResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetPackagesResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Packages" type="tns:ArrayOfPackage" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfPackage">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Package" nillable="true" type="tns:Package" />
        </s:sequence>
      </s:complexType>
      <s:element name="GetProducts">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetProductsRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetProductsRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ProductIDs" type="tns:ArrayOfString" />
              <s:element minOccurs="0" maxOccurs="1" name="SearchText" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="SearchDomain" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="CategoryIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="0" maxOccurs="1" name="SubCategoryIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="1" maxOccurs="1" name="SellOnline" type="s:boolean" />
              <s:element minOccurs="1" maxOccurs="1" name="LocationID" nillable="true" type="s:int" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetProductsResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetProductsResult" type="tns:GetProductsResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetProductsResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Products" type="tns:ArrayOfProduct" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="UpdateProducts">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:UpdateProductsRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="UpdateProductsRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Products" type="tns:ArrayOfProduct" />
              <s:element minOccurs="1" maxOccurs="1" name="Test" nillable="true" type="s:boolean" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="UpdateProductsResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="UpdateProductsResult" type="tns:UpdateProductsResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="UpdateProductsResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Products" type="tns:ArrayOfProduct" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="RedeemSpaFinderWellnessCard">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:RedeemSpaFinderWellnessCardRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="RedeemSpaFinderWellnessCardRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="CardID" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="FaceAmount" type="s:double" />
              <s:element minOccurs="0" maxOccurs="1" name="Currency" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="ClientID" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="LocationID" nillable="true" type="s:int" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="RedeemSpaFinderWellnessCardResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="RedeemSpaFinderWellnessCardResult" type="tns:RedeemSpaFinderWellnessCardResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="RedeemSpaFinderWellnessCardResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult" />
        </s:complexContent>
      </s:complexType>
      <s:element name="GetCustomPaymentMethods">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetCustomPaymentMethodsRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetCustomPaymentMethodsRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest" />
        </s:complexContent>
      </s:complexType>
      <s:element name="GetCustomPaymentMethodsResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetCustomPaymentMethodsResult" type="tns:GetCustomPaymentMethodsResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetCustomPaymentMethodsResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="PaymentMethods" type="tns:ArrayOfCustomPaymentInfo" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfCustomPaymentInfo">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="CustomPaymentInfo" nillable="true" type="tns:CustomPaymentInfo" />
        </s:sequence>
      </s:complexType>
      <s:element name="ReturnSale">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:ReturnSaleRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="ReturnSaleRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="Test" type="s:boolean" />
              <s:element minOccurs="1" maxOccurs="1" name="SaleID" nillable="true" type="s:long" />
              <s:element minOccurs="0" maxOccurs="1" name="ReturnReason" type="s:string" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="ReturnSaleResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="ReturnSaleResult" type="tns:ReturnSaleResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="ReturnSaleResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="ReturnSaleID" nillable="true" type="s:long" />
              <s:element minOccurs="1" maxOccurs="1" name="TrainerID" nillable="true" type="s:long" />
              <s:element minOccurs="1" maxOccurs="1" name="Amount" nillable="true" type="s:decimal" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="UpdateSaleDate">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:UpdateSaleDateRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="UpdateSaleDateRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="SaleID" nillable="true" type="s:long" />
              <s:element minOccurs="1" maxOccurs="1" name="SaleDate" nillable="true" type="s:dateTime" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="UpdateSaleDateResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="UpdateSaleDateResult" type="tns:UpdateSaleDateResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="UpdateSaleDateResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Sale" type="tns:Sale" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
    </s:schema>
  </wsdl:types>
  <wsdl:message name="GetAcceptedCardTypeSoapIn">
    <wsdl:part name="parameters" element="tns:GetAcceptedCardType" />
  </wsdl:message>
  <wsdl:message name="GetAcceptedCardTypeSoapOut">
    <wsdl:part name="parameters" element="tns:GetAcceptedCardTypeResponse" />
  </wsdl:message>
  <wsdl:message name="CheckoutShoppingCartSoapIn">
    <wsdl:part name="parameters" element="tns:CheckoutShoppingCart" />
  </wsdl:message>
  <wsdl:message name="CheckoutShoppingCartSoapOut">
    <wsdl:part name="parameters" element="tns:CheckoutShoppingCartResponse" />
  </wsdl:message>
  <wsdl:message name="GetSalesSoapIn">
    <wsdl:part name="parameters" element="tns:GetSales" />
  </wsdl:message>
  <wsdl:message name="GetSalesSoapOut">
    <wsdl:part name="parameters" element="tns:GetSalesResponse" />
  </wsdl:message>
  <wsdl:message name="GetServicesSoapIn">
    <wsdl:part name="parameters" element="tns:GetServices" />
  </wsdl:message>
  <wsdl:message name="GetServicesSoapOut">
    <wsdl:part name="parameters" element="tns:GetServicesResponse" />
  </wsdl:message>
  <wsdl:message name="UpdateServicesSoapIn">
    <wsdl:part name="parameters" element="tns:UpdateServices" />
  </wsdl:message>
  <wsdl:message name="UpdateServicesSoapOut">
    <wsdl:part name="parameters" element="tns:UpdateServicesResponse" />
  </wsdl:message>
  <wsdl:message name="GetPackagesSoapIn">
    <wsdl:part name="parameters" element="tns:GetPackages" />
  </wsdl:message>
  <wsdl:message name="GetPackagesSoapOut">
    <wsdl:part name="parameters" element="tns:GetPackagesResponse" />
  </wsdl:message>
  <wsdl:message name="GetProductsSoapIn">
    <wsdl:part name="parameters" element="tns:GetProducts" />
  </wsdl:message>
  <wsdl:message name="GetProductsSoapOut">
    <wsdl:part name="parameters" element="tns:GetProductsResponse" />
  </wsdl:message>
  <wsdl:message name="UpdateProductsSoapIn">
    <wsdl:part name="parameters" element="tns:UpdateProducts" />
  </wsdl:message>
  <wsdl:message name="UpdateProductsSoapOut">
    <wsdl:part name="parameters" element="tns:UpdateProductsResponse" />
  </wsdl:message>
  <wsdl:message name="RedeemSpaFinderWellnessCardSoapIn">
    <wsdl:part name="parameters" element="tns:RedeemSpaFinderWellnessCard" />
  </wsdl:message>
  <wsdl:message name="RedeemSpaFinderWellnessCardSoapOut">
    <wsdl:part name="parameters" element="tns:RedeemSpaFinderWellnessCardResponse" />
  </wsdl:message>
  <wsdl:message name="GetCustomPaymentMethodsSoapIn">
    <wsdl:part name="parameters" element="tns:GetCustomPaymentMethods" />
  </wsdl:message>
  <wsdl:message name="GetCustomPaymentMethodsSoapOut">
    <wsdl:part name="parameters" element="tns:GetCustomPaymentMethodsResponse" />
  </wsdl:message>
  <wsdl:message name="ReturnSaleSoapIn">
    <wsdl:part name="parameters" element="tns:ReturnSale" />
  </wsdl:message>
  <wsdl:message name="ReturnSaleSoapOut">
    <wsdl:part name="parameters" element="tns:ReturnSaleResponse" />
  </wsdl:message>
  <wsdl:message name="UpdateSaleDateSoapIn">
    <wsdl:part name="parameters" element="tns:UpdateSaleDate" />
  </wsdl:message>
  <wsdl:message name="UpdateSaleDateSoapOut">
    <wsdl:part name="parameters" element="tns:UpdateSaleDateResponse" />
  </wsdl:message>
  <wsdl:portType name="Sale_x0020_ServiceSoap">
    <wsdl:operation name="GetAcceptedCardType">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list of card types that the site accepts.</wsdl:documentation>
      <wsdl:input message="tns:GetAcceptedCardTypeSoapIn" />
      <wsdl:output message="tns:GetAcceptedCardTypeSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="CheckoutShoppingCart">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Validates and completes a sale by processing all items added to a shopping cart.</wsdl:documentation>
      <wsdl:input message="tns:CheckoutShoppingCartSoapIn" />
      <wsdl:output message="tns:CheckoutShoppingCartSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetSales">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list of sales.</wsdl:documentation>
      <wsdl:input message="tns:GetSalesSoapIn" />
      <wsdl:output message="tns:GetSalesSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetServices">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list of services available for sale.</wsdl:documentation>
      <wsdl:input message="tns:GetServicesSoapIn" />
      <wsdl:output message="tns:GetServicesSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="UpdateServices">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Update select services information.</wsdl:documentation>
      <wsdl:input message="tns:UpdateServicesSoapIn" />
      <wsdl:output message="tns:UpdateServicesSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetPackages">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list of packages available for sale.</wsdl:documentation>
      <wsdl:input message="tns:GetPackagesSoapIn" />
      <wsdl:output message="tns:GetPackagesSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetProducts">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Get a list of products available for sale.</wsdl:documentation>
      <wsdl:input message="tns:GetProductsSoapIn" />
      <wsdl:output message="tns:GetProductsSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="UpdateProducts">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Update select products information.</wsdl:documentation>
      <wsdl:input message="tns:UpdateProductsSoapIn" />
      <wsdl:output message="tns:UpdateProductsSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="RedeemSpaFinderWellnessCard">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Redeem a Spa Finder Gift Card.</wsdl:documentation>
      <wsdl:input message="tns:RedeemSpaFinderWellnessCardSoapIn" />
      <wsdl:output message="tns:RedeemSpaFinderWellnessCardSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetCustomPaymentMethods">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list of Custom Payment Methods.</wsdl:documentation>
      <wsdl:input message="tns:GetCustomPaymentMethodsSoapIn" />
      <wsdl:output message="tns:GetCustomPaymentMethodsSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="ReturnSale">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Return a sale used in business mode. This only supports comp payment method.</wsdl:documentation>
      <wsdl:input message="tns:ReturnSaleSoapIn" />
      <wsdl:output message="tns:ReturnSaleSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="UpdateSaleDate">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Modify sale date in business mode</wsdl:documentation>
      <wsdl:input message="tns:UpdateSaleDateSoapIn" />
      <wsdl:output message="tns:UpdateSaleDateSoapOut" />
    </wsdl:operation>
  </wsdl:portType>
  <wsdl:binding name="Sale_x0020_ServiceSoap" type="tns:Sale_x0020_ServiceSoap">
    <soap:binding transport="http://schemas.xmlsoap.org/soap/http" />
    <wsdl:operation name="GetAcceptedCardType">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetAcceptedCardType" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="CheckoutShoppingCart">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/CheckoutShoppingCart" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetSales">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetSales" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetServices">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetServices" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateServices">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/UpdateServices" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetPackages">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetPackages" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetProducts">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetProducts" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateProducts">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/UpdateProducts" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="RedeemSpaFinderWellnessCard">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/RedeemSpaFinderWellnessCard" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetCustomPaymentMethods">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetCustomPaymentMethods" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ReturnSale">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/ReturnSale" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateSaleDate">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/UpdateSaleDate" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:service name="Sale_x0020_Service">
    <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Provides methods and attributes relating to sales.</wsdl:documentation>
    <wsdl:port name="Sale_x0020_ServiceSoap" binding="tns:Sale_x0020_ServiceSoap">
      <soap:address location="https://api.mindbodyonline.com/0_5/SaleService.asmx" />
    </wsdl:port>
  </wsdl:service>
</wsdl:definitions>