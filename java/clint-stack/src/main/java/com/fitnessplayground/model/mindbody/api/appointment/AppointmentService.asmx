<?xml version="1.0" encoding="utf-8"?>
<wsdl:definitions xmlns:tm="http://microsoft.com/wsdl/mime/textMatching/" xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/" xmlns:mime="http://schemas.xmlsoap.org/wsdl/mime/" xmlns:tns="http://clients.mindbodyonline.com/api/0_5_1" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:s="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/" xmlns:http="http://schemas.xmlsoap.org/wsdl/http/" targetNamespace="http://clients.mindbodyonline.com/api/0_5_1" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">
  <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Provides methods and attributes relating to appointments.</wsdl:documentation>
  <wsdl:types>
    <s:schema elementFormDefault="qualified" targetNamespace="http://clients.mindbodyonline.com/api/0_5_1">
      <s:element name="GetStaffAppointments">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetStaffAppointmentsRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetStaffAppointmentsRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="StaffCredentials" type="tns:StaffCredentials" />
              <s:element minOccurs="0" maxOccurs="1" name="AppointmentIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="0" maxOccurs="1" name="LocationIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="1" maxOccurs="1" name="StartDate" nillable="true" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="EndDate" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="StaffIDs" type="tns:ArrayOfLong" />
              <s:element minOccurs="0" maxOccurs="1" name="ClientIDs" type="tns:ArrayOfString" />
            </s:sequence>
          </s:extension>
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
      <s:complexType name="StaffCredentials">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Username" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Password" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="SiteIDs" type="tns:ArrayOfInt" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ArrayOfLong">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="long" type="s:long" />
        </s:sequence>
      </s:complexType>
      <s:element name="GetStaffAppointmentsResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetStaffAppointmentsResult" type="tns:GetStaffAppointmentsResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetStaffAppointmentsResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Appointments" type="tns:ArrayOfAppointment" />
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
      <s:simpleType name="ActionCode">
        <s:restriction base="s:string">
          <s:enumeration value="None" />
          <s:enumeration value="Added" />
          <s:enumeration value="Updated" />
          <s:enumeration value="Failed" />
          <s:enumeration value="Removed" />
        </s:restriction>
      </s:simpleType>
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
              <s:element minOccurs="0" maxOccurs="1" name="SiteID" type="s:int" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
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
      <s:complexType name="ArrayOfUnavailability">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Unavailability" nillable="true" type="tns:Unavailability" />
        </s:sequence>
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
      <s:complexType name="ArrayOfAvailability">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Availability" nillable="true" type="tns:Availability" />
        </s:sequence>
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
              <s:element minOccurs="0" maxOccurs="1" name="SiteID" nillable="true" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="CrossRegionalBookingPerformed" type="s:boolean" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfProgram">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Program" nillable="true" type="tns:Program" />
        </s:sequence>
      </s:complexType>
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
              <s:element minOccurs="0" maxOccurs="1" name="SiteId" nillable="true" type="s:int" />
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
              <s:element minOccurs="0" maxOccurs="1" name="LastModifiedDateTime" type="s:dateTime" />
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
      <s:complexType name="ArrayOfRep">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Rep" nillable="true" type="tns:Rep" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ArrayOfSalesRep">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="SalesRep" nillable="true" type="tns:SalesRep" />
        </s:sequence>
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
      <s:complexType name="Relationship">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="ID" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="RelationshipName1" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="RelationshipName2" type="s:string" />
        </s:sequence>
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
      <s:complexType name="ArrayOfResource">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Resource" nillable="true" type="tns:Resource" />
        </s:sequence>
      </s:complexType>
      <s:element name="AddOrUpdateAppointments">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:AddOrUpdateAppointmentsRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="AddOrUpdateAppointmentsRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="UpdateAction" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="Test" nillable="true" type="s:boolean" />
              <s:element minOccurs="1" maxOccurs="1" name="SendEmail" nillable="true" type="s:boolean" />
              <s:element minOccurs="1" maxOccurs="1" name="ApplyPayment" nillable="true" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="Appointments" type="tns:ArrayOfAppointment" />
              <s:element minOccurs="1" maxOccurs="1" name="IgnoreDefaultSessionLength" nillable="true" type="s:boolean" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="AddOrUpdateAppointmentsResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="AddOrUpdateAppointmentsResult" type="tns:AddOrUpdateAppointmentsResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="AddOrUpdateAppointmentsResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Appointments" type="tns:ArrayOfAppointment" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetBookableItems">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetBookableItemsRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetBookableItemsRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="SessionTypeIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="0" maxOccurs="1" name="LocationIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="0" maxOccurs="1" name="StaffIDs" type="tns:ArrayOfLong" />
              <s:element minOccurs="1" maxOccurs="1" name="StartDate" nillable="true" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="EndDate" nillable="true" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="AppointmentID" nillable="true" type="s:long" />
              <s:element minOccurs="1" maxOccurs="1" name="IgnoreDefaultSessionLength" nillable="true" type="s:boolean" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetBookableItemsResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetBookableItemsResult" type="tns:GetBookableItemsResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetBookableItemsResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ScheduleItems" type="tns:ArrayOfScheduleItem" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfScheduleItem">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="ScheduleItem" nillable="true" type="tns:ScheduleItem" />
        </s:sequence>
      </s:complexType>
      <s:element name="GetScheduleItems">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetScheduleItemsRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetScheduleItemsRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="LocationIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="0" maxOccurs="1" name="StaffIDs" type="tns:ArrayOfLong" />
              <s:element minOccurs="1" maxOccurs="1" name="StartDate" nillable="true" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="EndDate" nillable="true" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="IgnorePrepFinishTimes" type="s:boolean" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetScheduleItemsResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetScheduleItemsResult" type="tns:GetScheduleItemsResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetScheduleItemsResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="StaffMembers" type="tns:ArrayOfStaff" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfStaff">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Staff" nillable="true" type="tns:Staff" />
        </s:sequence>
      </s:complexType>
      <s:element name="AddOrUpdateAvailabilities">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:AddOrUpdateAvailabilitiesRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="AddOrUpdateAvailabilitiesRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="UpdateAction" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="Test" nillable="true" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="AvailabilityIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="1" maxOccurs="1" name="LocationID" nillable="true" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="StaffIDs" type="tns:ArrayOfLong" />
              <s:element minOccurs="0" maxOccurs="1" name="ProgramIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="1" maxOccurs="1" name="StartDateTime" nillable="true" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="EndDateTime" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="DaysOfWeek" type="tns:ArrayOfDayOfWeek" />
              <s:element minOccurs="0" maxOccurs="1" name="UnavailableDescription" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="IsUnavailable" type="s:boolean" />
              <s:element minOccurs="1" maxOccurs="1" name="PublicDisplay" nillable="true" type="tns:AvailabilityDisplay" />
              <s:element minOccurs="0" maxOccurs="1" name="Execute" type="s:string" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfDayOfWeek">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="DayOfWeek" type="tns:DayOfWeek" />
        </s:sequence>
      </s:complexType>
      <s:simpleType name="DayOfWeek">
        <s:restriction base="s:string">
          <s:enumeration value="Sunday" />
          <s:enumeration value="Monday" />
          <s:enumeration value="Tuesday" />
          <s:enumeration value="Wednesday" />
          <s:enumeration value="Thursday" />
          <s:enumeration value="Friday" />
          <s:enumeration value="Saturday" />
        </s:restriction>
      </s:simpleType>
      <s:simpleType name="AvailabilityDisplay">
        <s:restriction base="s:string">
          <s:enumeration value="Hide" />
          <s:enumeration value="Show" />
          <s:enumeration value="Mask" />
        </s:restriction>
      </s:simpleType>
      <s:element name="AddOrUpdateAvailabilitiesResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="AddOrUpdateAvailabilitiesResult" type="tns:AddOrUpdateAvailabilitiesResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="AddOrUpdateAvailabilitiesResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="StaffMembers" type="tns:ArrayOfStaff" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetActiveSessionTimes">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetActiveSessionTimesRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetActiveSessionTimesRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="ScheduleType" nillable="true" type="tns:ScheduleType" />
              <s:element minOccurs="0" maxOccurs="1" name="SessionTypeIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="1" maxOccurs="1" name="StartTime" nillable="true" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="EndTime" nillable="true" type="s:dateTime" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetActiveSessionTimesResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetActiveSessionTimesResult" type="tns:GetActiveSessionTimesResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetActiveSessionTimesResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Times" type="tns:ArrayOfDateTime" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfDateTime">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="dateTime" type="s:dateTime" />
        </s:sequence>
      </s:complexType>
      <s:element name="GetAppointmentOptions">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetAppointmentOptionsRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetAppointmentOptionsRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest" />
        </s:complexContent>
      </s:complexType>
      <s:element name="GetAppointmentOptionsResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetAppointmentOptionsResult" type="tns:GetAppointmentOptionsResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetAppointmentOptionsResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Options" type="tns:ArrayOfOption" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfOption">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Option" nillable="true" type="tns:Option" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="Option">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="DisplayName" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Value" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Type" type="s:string" />
        </s:sequence>
      </s:complexType>
    </s:schema>
  </wsdl:types>
  <wsdl:message name="GetStaffAppointmentsSoapIn">
    <wsdl:part name="parameters" element="tns:GetStaffAppointments" />
  </wsdl:message>
  <wsdl:message name="GetStaffAppointmentsSoapOut">
    <wsdl:part name="parameters" element="tns:GetStaffAppointmentsResponse" />
  </wsdl:message>
  <wsdl:message name="AddOrUpdateAppointmentsSoapIn">
    <wsdl:part name="parameters" element="tns:AddOrUpdateAppointments" />
  </wsdl:message>
  <wsdl:message name="AddOrUpdateAppointmentsSoapOut">
    <wsdl:part name="parameters" element="tns:AddOrUpdateAppointmentsResponse" />
  </wsdl:message>
  <wsdl:message name="GetBookableItemsSoapIn">
    <wsdl:part name="parameters" element="tns:GetBookableItems" />
  </wsdl:message>
  <wsdl:message name="GetBookableItemsSoapOut">
    <wsdl:part name="parameters" element="tns:GetBookableItemsResponse" />
  </wsdl:message>
  <wsdl:message name="GetScheduleItemsSoapIn">
    <wsdl:part name="parameters" element="tns:GetScheduleItems" />
  </wsdl:message>
  <wsdl:message name="GetScheduleItemsSoapOut">
    <wsdl:part name="parameters" element="tns:GetScheduleItemsResponse" />
  </wsdl:message>
  <wsdl:message name="AddOrUpdateAvailabilitiesSoapIn">
    <wsdl:part name="parameters" element="tns:AddOrUpdateAvailabilities" />
  </wsdl:message>
  <wsdl:message name="AddOrUpdateAvailabilitiesSoapOut">
    <wsdl:part name="parameters" element="tns:AddOrUpdateAvailabilitiesResponse" />
  </wsdl:message>
  <wsdl:message name="GetActiveSessionTimesSoapIn">
    <wsdl:part name="parameters" element="tns:GetActiveSessionTimes" />
  </wsdl:message>
  <wsdl:message name="GetActiveSessionTimesSoapOut">
    <wsdl:part name="parameters" element="tns:GetActiveSessionTimesResponse" />
  </wsdl:message>
  <wsdl:message name="GetAppointmentOptionsSoapIn">
    <wsdl:part name="parameters" element="tns:GetAppointmentOptions" />
  </wsdl:message>
  <wsdl:message name="GetAppointmentOptionsSoapOut">
    <wsdl:part name="parameters" element="tns:GetAppointmentOptionsResponse" />
  </wsdl:message>
  <wsdl:portType name="Appointment_x0020_ServiceSoap">
    <wsdl:operation name="GetStaffAppointments">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list of appointments that a given staff member is instructing.</wsdl:documentation>
      <wsdl:input message="tns:GetStaffAppointmentsSoapIn" />
      <wsdl:output message="tns:GetStaffAppointmentsSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="AddOrUpdateAppointments">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Adds or updates a list of appointments.</wsdl:documentation>
      <wsdl:input message="tns:AddOrUpdateAppointmentsSoapIn" />
      <wsdl:output message="tns:AddOrUpdateAppointmentsSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetBookableItems">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list of bookable items.</wsdl:documentation>
      <wsdl:input message="tns:GetBookableItemsSoapIn" />
      <wsdl:output message="tns:GetBookableItemsSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetScheduleItems">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list of scheduled items (appointments, availabilities, and unavailabilities).</wsdl:documentation>
      <wsdl:input message="tns:GetScheduleItemsSoapIn" />
      <wsdl:output message="tns:GetScheduleItemsSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="AddOrUpdateAvailabilities">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Adds or updates a list of availabilities.</wsdl:documentation>
      <wsdl:input message="tns:AddOrUpdateAvailabilitiesSoapIn" />
      <wsdl:output message="tns:AddOrUpdateAvailabilitiesSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetActiveSessionTimes">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list of times that are active for a given program ID.</wsdl:documentation>
      <wsdl:input message="tns:GetActiveSessionTimesSoapIn" />
      <wsdl:output message="tns:GetActiveSessionTimesSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetAppointmentOptions">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list appointment options.</wsdl:documentation>
      <wsdl:input message="tns:GetAppointmentOptionsSoapIn" />
      <wsdl:output message="tns:GetAppointmentOptionsSoapOut" />
    </wsdl:operation>
  </wsdl:portType>
  <wsdl:binding name="Appointment_x0020_ServiceSoap" type="tns:Appointment_x0020_ServiceSoap">
    <soap:binding transport="http://schemas.xmlsoap.org/soap/http" />
    <wsdl:operation name="GetStaffAppointments">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5_1/GetStaffAppointments" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="AddOrUpdateAppointments">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5_1/AddOrUpdateAppointments" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetBookableItems">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5_1/GetBookableItems" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetScheduleItems">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5_1/GetScheduleItems" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="AddOrUpdateAvailabilities">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5_1/AddOrUpdateAvailabilities" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetActiveSessionTimes">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5_1/GetActiveSessionTimes" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetAppointmentOptions">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5_1/GetAppointmentOptions" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:service name="Appointment_x0020_Service">
    <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Provides methods and attributes relating to appointments.</wsdl:documentation>
    <wsdl:port name="Appointment_x0020_ServiceSoap" binding="tns:Appointment_x0020_ServiceSoap">
      <soap:address location="https://api.mindbodyonline.com/0_5_1/AppointmentService.asmx" />
    </wsdl:port>
  </wsdl:service>
</wsdl:definitions>