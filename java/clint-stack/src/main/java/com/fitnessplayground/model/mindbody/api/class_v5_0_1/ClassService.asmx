<?xml version="1.0" encoding="utf-8"?>
<wsdl:definitions xmlns:tm="http://microsoft.com/wsdl/mime/textMatching/" xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/" xmlns:mime="http://schemas.xmlsoap.org/wsdl/mime/" xmlns:tns="http://clients.mindbodyonline.com/api/0_5_1" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:s="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/" xmlns:http="http://schemas.xmlsoap.org/wsdl/http/" targetNamespace="http://clients.mindbodyonline.com/api/0_5_1" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">
  <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Provides methods and attributes relating to classes and enrollments.</wsdl:documentation>
  <wsdl:types>
    <s:schema elementFormDefault="qualified" targetNamespace="http://clients.mindbodyonline.com/api/0_5_1">
      <s:element name="GetClasses">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetClassesRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetClassesRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ClassDescriptionIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="0" maxOccurs="1" name="ClassIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="0" maxOccurs="1" name="StaffIDs" type="tns:ArrayOfLong" />
              <s:element minOccurs="1" maxOccurs="1" name="StartDateTime" nillable="true" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="EndDateTime" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="ClientID" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="ProgramIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="0" maxOccurs="1" name="SessionTypeIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="0" maxOccurs="1" name="LocationIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="0" maxOccurs="1" name="SemesterIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="1" maxOccurs="1" name="HideCanceledClasses" nillable="true" type="s:boolean" />
              <s:element minOccurs="1" maxOccurs="1" name="SchedulingWindow" nillable="true" type="s:boolean" />
              <s:element minOccurs="1" maxOccurs="1" name="LastModifiedDate" nillable="true" type="s:dateTime" />
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
      <s:complexType name="ArrayOfLong">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="long" type="s:long" />
        </s:sequence>
      </s:complexType>
      <s:element name="GetClassesResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetClassesResult" type="tns:GetClassesResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetClassesResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Classes" type="tns:ArrayOfClass" />
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
              <s:element minOccurs="0" maxOccurs="1" name="LastModifiedDateTime" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="ClassDescription" type="tns:ClassDescription" />
              <s:element minOccurs="0" maxOccurs="1" name="Staff" type="tns:Staff" />
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
      <s:simpleType name="ActionCode">
        <s:restriction base="s:string">
          <s:enumeration value="None" />
          <s:enumeration value="Added" />
          <s:enumeration value="Updated" />
          <s:enumeration value="Failed" />
          <s:enumeration value="Removed" />
        </s:restriction>
      </s:simpleType>
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
      <s:complexType name="ArrayOfResource">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Resource" nillable="true" type="tns:Resource" />
        </s:sequence>
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
              <s:element minOccurs="0" maxOccurs="1" name="LastModifiedDateTime" nillable="true" type="s:dateTime" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfVisit">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Visit" nillable="true" type="tns:Visit" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ArrayOfClient">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Client" nillable="true" type="tns:Client" />
        </s:sequence>
      </s:complexType>
      <s:element name="UpdateClientVisits">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:UpdateClientVisitsRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="UpdateClientVisitsRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Visits" type="tns:ArrayOfVisit" />
              <s:element minOccurs="1" maxOccurs="1" name="Test" nillable="true" type="s:boolean" />
              <s:element minOccurs="1" maxOccurs="1" name="SendEmail" nillable="true" type="s:boolean" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="UpdateClientVisitsResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="UpdateClientVisitsResult" type="tns:UpdateClientVisitsResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="UpdateClientVisitsResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Visits" type="tns:ArrayOfVisit" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetClassVisits">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetClassVisitsRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetClassVisitsRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="ClassID" type="s:int" />
              <s:element minOccurs="1" maxOccurs="1" name="LastModifiedDate" nillable="true" type="s:dateTime" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetClassVisitsResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetClassVisitsResult" type="tns:GetClassVisitsResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetClassVisitsResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Class" type="tns:Class" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetClassDescriptions">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetClassDescriptionsRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetClassDescriptionsRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ClassDescriptionIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="0" maxOccurs="1" name="ProgramIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="0" maxOccurs="1" name="StaffIDs" type="tns:ArrayOfLong" />
              <s:element minOccurs="0" maxOccurs="1" name="LocationIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="1" maxOccurs="1" name="StartClassDateTime" nillable="true" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="EndClassDateTime" nillable="true" type="s:dateTime" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetClassDescriptionsResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetClassDescriptionsResult" type="tns:GetClassDescriptionsResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetClassDescriptionsResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ClassDescriptions" type="tns:ArrayOfClassDescription" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfClassDescription">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="ClassDescription" nillable="true" type="tns:ClassDescription" />
        </s:sequence>
      </s:complexType>
      <s:element name="GetEnrollments">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetEnrollmentsRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetEnrollmentsRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="LocationIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="0" maxOccurs="1" name="ClassScheduleIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="0" maxOccurs="1" name="StaffIDs" type="tns:ArrayOfLong" />
              <s:element minOccurs="0" maxOccurs="1" name="ProgramIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="0" maxOccurs="1" name="SessionTypeIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="0" maxOccurs="1" name="SemesterIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="0" maxOccurs="1" name="CourseIDs" type="tns:ArrayOfLong" />
              <s:element minOccurs="1" maxOccurs="1" name="StartDate" nillable="true" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="EndDate" nillable="true" type="s:dateTime" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetEnrollmentsResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetEnrollmentsResult" type="tns:GetEnrollmentsResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetEnrollmentsResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
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
      <s:element name="GetClassSchedules">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetClassSchedulesRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetClassSchedulesRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="LocationIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="0" maxOccurs="1" name="ClassScheduleIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="0" maxOccurs="1" name="StaffIDs" type="tns:ArrayOfLong" />
              <s:element minOccurs="0" maxOccurs="1" name="ProgramIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="0" maxOccurs="1" name="SessionTypeIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="1" maxOccurs="1" name="StartDate" nillable="true" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="EndDate" nillable="true" type="s:dateTime" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetClassSchedulesResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetClassSchedulesResult" type="tns:GetClassSchedulesResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetClassSchedulesResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ClassSchedules" type="tns:ArrayOfClassSchedule" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="AddClientsToClasses">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:AddClientsToClassesRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="AddClientsToClassesRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ClientIDs" type="tns:ArrayOfString" />
              <s:element minOccurs="0" maxOccurs="1" name="ClassIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="1" maxOccurs="1" name="Test" nillable="true" type="s:boolean" />
              <s:element minOccurs="1" maxOccurs="1" name="RequirePayment" nillable="true" type="s:boolean" />
              <s:element minOccurs="1" maxOccurs="1" name="Waitlist" nillable="true" type="s:boolean" />
              <s:element minOccurs="1" maxOccurs="1" name="SendEmail" nillable="true" type="s:boolean" />
              <s:element minOccurs="1" maxOccurs="1" name="WaitlistEntryID" nillable="true" type="s:int" />
              <s:element minOccurs="1" maxOccurs="1" name="ClientServiceID" nillable="true" type="s:int" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="AddClientsToClassesResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="AddClientsToClassesResult" type="tns:AddClientsToClassesResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="AddClientsToClassesResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Classes" type="tns:ArrayOfClass" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="AddClientToClass">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:AddClientToClassRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="AddClientToClassRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ClientID" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="ClassID" type="s:int" />
              <s:element minOccurs="1" maxOccurs="1" name="Test" nillable="true" type="s:boolean" />
              <s:element minOccurs="1" maxOccurs="1" name="RequirePayment" nillable="true" type="s:boolean" />
              <s:element minOccurs="1" maxOccurs="1" name="Waitlist" nillable="true" type="s:boolean" />
              <s:element minOccurs="1" maxOccurs="1" name="SendEmail" nillable="true" type="s:boolean" />
              <s:element minOccurs="1" maxOccurs="1" name="WaitlistEntryID" nillable="true" type="s:int" />
              <s:element minOccurs="1" maxOccurs="1" name="ClientServiceID" nillable="true" type="s:int" />
              <s:element minOccurs="1" maxOccurs="1" name="CrossRegionalBooking" type="s:boolean" />
              <s:element minOccurs="1" maxOccurs="1" name="CrossRegionalBookingClientServiceSiteId" nillable="true" type="s:int" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="AddClientToClassResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="AddClientToClassResult" type="tns:AddClientsToClassesResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="RemoveClientsFromClasses">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:RemoveClientsFromClassesRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="RemoveClientsFromClassesRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ClientIDs" type="tns:ArrayOfString" />
              <s:element minOccurs="0" maxOccurs="1" name="ClassIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="1" maxOccurs="1" name="Test" nillable="true" type="s:boolean" />
              <s:element minOccurs="1" maxOccurs="1" name="SendEmail" nillable="true" type="s:boolean" />
              <s:element minOccurs="1" maxOccurs="1" name="LateCancel" nillable="true" type="s:boolean" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="RemoveClientsFromClassesResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="RemoveClientsFromClassesResult" type="tns:RemoveClientsFromClassesResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="RemoveClientsFromClassesResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Classes" type="tns:ArrayOfClass" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="AddClientsToEnrollments">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:AddClientsToEnrollmentsRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="AddClientsToEnrollmentsRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ClientIDs" type="tns:ArrayOfString" />
              <s:element minOccurs="0" maxOccurs="1" name="ClassScheduleIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="0" maxOccurs="1" name="CourseIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="1" maxOccurs="1" name="EnrollDateForward" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="EnrollOpen" type="tns:ArrayOfDateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="Test" nillable="true" type="s:boolean" />
              <s:element minOccurs="1" maxOccurs="1" name="SendEmail" nillable="true" type="s:boolean" />
              <s:element minOccurs="1" maxOccurs="1" name="Waitlist" nillable="true" type="s:boolean" />
              <s:element minOccurs="1" maxOccurs="1" name="WaitlistEntryID" nillable="true" type="s:int" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfDateTime">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="dateTime" type="s:dateTime" />
        </s:sequence>
      </s:complexType>
      <s:element name="AddClientsToEnrollmentsResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="AddClientsToEnrollmentsResult" type="tns:AddClientsToEnrollmentsResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="AddClientsToEnrollmentsResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Enrollments" type="tns:ArrayOfClassSchedule" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="RemoveFromWaitlist">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:RemoveFromWaitlistRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="RemoveFromWaitlistRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="WaitlistEntryIDs" type="tns:ArrayOfInt" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="RemoveFromWaitlistResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="RemoveFromWaitlistResult" type="tns:RemoveFromWaitlistResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="RemoveFromWaitlistResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult" />
        </s:complexContent>
      </s:complexType>
      <s:element name="GetSemesters">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetSemestersRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetSemestersRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="SemesterIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="1" maxOccurs="1" name="StartDate" nillable="true" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="EndDate" nillable="true" type="s:dateTime" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetSemestersResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetSemestersResult" type="tns:GetSemestersResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetSemestersResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Semesters" type="tns:ArrayOfSemester" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfSemester">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Semester" nillable="true" type="tns:Semester" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="Semester">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="ID" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Description" type="s:string" />
          <s:element minOccurs="1" maxOccurs="1" name="StartDate" type="s:dateTime" />
          <s:element minOccurs="1" maxOccurs="1" name="EndDate" type="s:dateTime" />
          <s:element minOccurs="1" maxOccurs="1" name="MultiRegistrationDiscount" type="s:decimal" />
          <s:element minOccurs="1" maxOccurs="1" name="MultiRegistrationDeadline" type="s:dateTime" />
        </s:sequence>
      </s:complexType>
      <s:element name="GetCourses">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetCoursesRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetCoursesRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="LocationIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="0" maxOccurs="1" name="CourseIDs" type="tns:ArrayOfLong" />
              <s:element minOccurs="0" maxOccurs="1" name="StaffIDs" type="tns:ArrayOfLong" />
              <s:element minOccurs="0" maxOccurs="1" name="ProgramIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="1" maxOccurs="1" name="StartDate" nillable="true" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="EndDate" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="SemesterIDs" type="tns:ArrayOfInt" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetCoursesResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetCoursesResult" type="tns:GetCoursesResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetCoursesResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Courses" type="tns:ArrayOfCourse" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfCourse">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Course" nillable="true" type="tns:Course" />
        </s:sequence>
      </s:complexType>
      <s:element name="GetWaitlistEntries">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetWaitlistEntriesRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetWaitlistEntriesRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ClassScheduleIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="0" maxOccurs="1" name="ClientIDs" type="tns:ArrayOfString" />
              <s:element minOccurs="0" maxOccurs="1" name="WaitlistEntryIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="0" maxOccurs="1" name="ClassIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="1" maxOccurs="1" name="HidePastEntries" nillable="true" type="s:boolean" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetWaitlistEntriesResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetWaitlistEntriesResult" type="tns:GetWaitlistEntriesResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetWaitlistEntriesResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="WaitlistEntries" type="tns:ArrayOfWaitlistEntry" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfWaitlistEntry">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="WaitlistEntry" nillable="true" type="tns:WaitlistEntry" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="WaitlistEntry">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="ID" type="s:int" />
          <s:element minOccurs="1" maxOccurs="1" name="ClassID" type="s:long" />
          <s:element minOccurs="1" maxOccurs="1" name="ClassDate" nillable="true" type="s:dateTime" />
          <s:element minOccurs="0" maxOccurs="1" name="Client" type="tns:Client" />
          <s:element minOccurs="0" maxOccurs="1" name="ClassSchedule" type="tns:ClassSchedule" />
          <s:element minOccurs="1" maxOccurs="1" name="EnrollmentDateForward" type="s:dateTime" />
          <s:element minOccurs="1" maxOccurs="1" name="RequestDateTime" type="s:dateTime" />
          <s:element minOccurs="1" maxOccurs="1" name="Web" type="s:boolean" />
          <s:element minOccurs="1" maxOccurs="1" name="VisitRefNo" type="s:int" />
        </s:sequence>
      </s:complexType>
      <s:element name="SubstituteClassTeacher">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:SubstituteClassTeacherRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="SubstituteClassTeacherRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="ClassID" type="s:long" />
              <s:element minOccurs="1" maxOccurs="1" name="StaffID" type="s:long" />
              <s:element minOccurs="1" maxOccurs="1" name="OverrideConflicts" type="s:boolean" />
              <s:element minOccurs="1" maxOccurs="1" name="SendClientEmail" type="s:boolean" />
              <s:element minOccurs="1" maxOccurs="1" name="SendOldStaffEmail" type="s:boolean" />
              <s:element minOccurs="1" maxOccurs="1" name="SendNewStaffEmail" type="s:boolean" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="SubstituteClassTeacherResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="SubstituteClassTeacherResult" type="tns:SubstituteClassTeacherResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="SubstituteClassTeacherResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Class" type="tns:Class" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="SubtituteClassTeacher">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:SubstituteClassTeacherRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="SubtituteClassTeacherResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="SubtituteClassTeacherResult" type="tns:SubstituteClassTeacherResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="CancelSingleClass">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:CancelSingleClassRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="CancelSingleClassRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="ClassID" type="s:long" />
              <s:element minOccurs="1" maxOccurs="1" name="HideCancel" type="s:boolean" />
              <s:element minOccurs="1" maxOccurs="1" name="SendClientEmail" type="s:boolean" />
              <s:element minOccurs="1" maxOccurs="1" name="SendStaffEmail" type="s:boolean" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="CancelSingleClassResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="CancelSingleClassResult" type="tns:CancelSingleClassResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="CancelSingleClassResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Class" type="tns:Class" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
    </s:schema>
  </wsdl:types>
  <wsdl:message name="GetClassesSoapIn">
    <wsdl:part name="parameters" element="tns:GetClasses" />
  </wsdl:message>
  <wsdl:message name="GetClassesSoapOut">
    <wsdl:part name="parameters" element="tns:GetClassesResponse" />
  </wsdl:message>
  <wsdl:message name="UpdateClientVisitsSoapIn">
    <wsdl:part name="parameters" element="tns:UpdateClientVisits" />
  </wsdl:message>
  <wsdl:message name="UpdateClientVisitsSoapOut">
    <wsdl:part name="parameters" element="tns:UpdateClientVisitsResponse" />
  </wsdl:message>
  <wsdl:message name="GetClassVisitsSoapIn">
    <wsdl:part name="parameters" element="tns:GetClassVisits" />
  </wsdl:message>
  <wsdl:message name="GetClassVisitsSoapOut">
    <wsdl:part name="parameters" element="tns:GetClassVisitsResponse" />
  </wsdl:message>
  <wsdl:message name="GetClassDescriptionsSoapIn">
    <wsdl:part name="parameters" element="tns:GetClassDescriptions" />
  </wsdl:message>
  <wsdl:message name="GetClassDescriptionsSoapOut">
    <wsdl:part name="parameters" element="tns:GetClassDescriptionsResponse" />
  </wsdl:message>
  <wsdl:message name="GetEnrollmentsSoapIn">
    <wsdl:part name="parameters" element="tns:GetEnrollments" />
  </wsdl:message>
  <wsdl:message name="GetEnrollmentsSoapOut">
    <wsdl:part name="parameters" element="tns:GetEnrollmentsResponse" />
  </wsdl:message>
  <wsdl:message name="GetClassSchedulesSoapIn">
    <wsdl:part name="parameters" element="tns:GetClassSchedules" />
  </wsdl:message>
  <wsdl:message name="GetClassSchedulesSoapOut">
    <wsdl:part name="parameters" element="tns:GetClassSchedulesResponse" />
  </wsdl:message>
  <wsdl:message name="AddClientsToClassesSoapIn">
    <wsdl:part name="parameters" element="tns:AddClientsToClasses" />
  </wsdl:message>
  <wsdl:message name="AddClientsToClassesSoapOut">
    <wsdl:part name="parameters" element="tns:AddClientsToClassesResponse" />
  </wsdl:message>
  <wsdl:message name="AddClientToClassSoapIn">
    <wsdl:part name="parameters" element="tns:AddClientToClass" />
  </wsdl:message>
  <wsdl:message name="AddClientToClassSoapOut">
    <wsdl:part name="parameters" element="tns:AddClientToClassResponse" />
  </wsdl:message>
  <wsdl:message name="RemoveClientsFromClassesSoapIn">
    <wsdl:part name="parameters" element="tns:RemoveClientsFromClasses" />
  </wsdl:message>
  <wsdl:message name="RemoveClientsFromClassesSoapOut">
    <wsdl:part name="parameters" element="tns:RemoveClientsFromClassesResponse" />
  </wsdl:message>
  <wsdl:message name="AddClientsToEnrollmentsSoapIn">
    <wsdl:part name="parameters" element="tns:AddClientsToEnrollments" />
  </wsdl:message>
  <wsdl:message name="AddClientsToEnrollmentsSoapOut">
    <wsdl:part name="parameters" element="tns:AddClientsToEnrollmentsResponse" />
  </wsdl:message>
  <wsdl:message name="RemoveFromWaitlistSoapIn">
    <wsdl:part name="parameters" element="tns:RemoveFromWaitlist" />
  </wsdl:message>
  <wsdl:message name="RemoveFromWaitlistSoapOut">
    <wsdl:part name="parameters" element="tns:RemoveFromWaitlistResponse" />
  </wsdl:message>
  <wsdl:message name="GetSemestersSoapIn">
    <wsdl:part name="parameters" element="tns:GetSemesters" />
  </wsdl:message>
  <wsdl:message name="GetSemestersSoapOut">
    <wsdl:part name="parameters" element="tns:GetSemestersResponse" />
  </wsdl:message>
  <wsdl:message name="GetCoursesSoapIn">
    <wsdl:part name="parameters" element="tns:GetCourses" />
  </wsdl:message>
  <wsdl:message name="GetCoursesSoapOut">
    <wsdl:part name="parameters" element="tns:GetCoursesResponse" />
  </wsdl:message>
  <wsdl:message name="GetWaitlistEntriesSoapIn">
    <wsdl:part name="parameters" element="tns:GetWaitlistEntries" />
  </wsdl:message>
  <wsdl:message name="GetWaitlistEntriesSoapOut">
    <wsdl:part name="parameters" element="tns:GetWaitlistEntriesResponse" />
  </wsdl:message>
  <wsdl:message name="SubstituteClassTeacherSoapIn">
    <wsdl:part name="parameters" element="tns:SubstituteClassTeacher" />
  </wsdl:message>
  <wsdl:message name="SubstituteClassTeacherSoapOut">
    <wsdl:part name="parameters" element="tns:SubstituteClassTeacherResponse" />
  </wsdl:message>
  <wsdl:message name="SubtituteClassTeacherSoapIn">
    <wsdl:part name="parameters" element="tns:SubtituteClassTeacher" />
  </wsdl:message>
  <wsdl:message name="SubtituteClassTeacherSoapOut">
    <wsdl:part name="parameters" element="tns:SubtituteClassTeacherResponse" />
  </wsdl:message>
  <wsdl:message name="CancelSingleClassSoapIn">
    <wsdl:part name="parameters" element="tns:CancelSingleClass" />
  </wsdl:message>
  <wsdl:message name="CancelSingleClassSoapOut">
    <wsdl:part name="parameters" element="tns:CancelSingleClassResponse" />
  </wsdl:message>
  <wsdl:portType name="Class_x0020_ServiceSoap">
    <wsdl:operation name="GetClasses">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list of classes.</wsdl:documentation>
      <wsdl:input message="tns:GetClassesSoapIn" />
      <wsdl:output message="tns:GetClassesSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="UpdateClientVisits">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Update a list of visits.</wsdl:documentation>
      <wsdl:input message="tns:UpdateClientVisitsSoapIn" />
      <wsdl:output message="tns:UpdateClientVisitsSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetClassVisits">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a class with a list of clients.</wsdl:documentation>
      <wsdl:input message="tns:GetClassVisitsSoapIn" />
      <wsdl:output message="tns:GetClassVisitsSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetClassDescriptions">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list of class descriptions.</wsdl:documentation>
      <wsdl:input message="tns:GetClassDescriptionsSoapIn" />
      <wsdl:output message="tns:GetClassDescriptionsSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetEnrollments">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list of enrollments.</wsdl:documentation>
      <wsdl:input message="tns:GetEnrollmentsSoapIn" />
      <wsdl:output message="tns:GetEnrollmentsSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetClassSchedules">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list of class schedules.</wsdl:documentation>
      <wsdl:input message="tns:GetClassSchedulesSoapIn" />
      <wsdl:output message="tns:GetClassSchedulesSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="AddClientsToClasses">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Adds clients to classes (signup).</wsdl:documentation>
      <wsdl:input message="tns:AddClientsToClassesSoapIn" />
      <wsdl:output message="tns:AddClientsToClassesSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="AddClientToClass">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Adds a client to a class.</wsdl:documentation>
      <wsdl:input message="tns:AddClientToClassSoapIn" />
      <wsdl:output message="tns:AddClientToClassSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="RemoveClientsFromClasses">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Removes clients from classes.</wsdl:documentation>
      <wsdl:input message="tns:RemoveClientsFromClassesSoapIn" />
      <wsdl:output message="tns:RemoveClientsFromClassesSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="AddClientsToEnrollments">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Adds clients to enrollments (signup).</wsdl:documentation>
      <wsdl:input message="tns:AddClientsToEnrollmentsSoapIn" />
      <wsdl:output message="tns:AddClientsToEnrollmentsSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="RemoveFromWaitlist">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Removes client from enrollment waitlist</wsdl:documentation>
      <wsdl:input message="tns:RemoveFromWaitlistSoapIn" />
      <wsdl:output message="tns:RemoveFromWaitlistSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetSemesters">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list of semesters.</wsdl:documentation>
      <wsdl:input message="tns:GetSemestersSoapIn" />
      <wsdl:output message="tns:GetSemestersSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetCourses">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list of courses.</wsdl:documentation>
      <wsdl:input message="tns:GetCoursesSoapIn" />
      <wsdl:output message="tns:GetCoursesSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetWaitlistEntries">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Get waitlist entries.</wsdl:documentation>
      <wsdl:input message="tns:GetWaitlistEntriesSoapIn" />
      <wsdl:output message="tns:GetWaitlistEntriesSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="SubstituteClassTeacher">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Substitutes the teacher for a class.</wsdl:documentation>
      <wsdl:input message="tns:SubstituteClassTeacherSoapIn" />
      <wsdl:output message="tns:SubstituteClassTeacherSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="SubtituteClassTeacher">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Substitutes the teacher for a class.</wsdl:documentation>
      <wsdl:input message="tns:SubtituteClassTeacherSoapIn" />
      <wsdl:output message="tns:SubtituteClassTeacherSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="CancelSingleClass">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Cancels a single class instance.</wsdl:documentation>
      <wsdl:input message="tns:CancelSingleClassSoapIn" />
      <wsdl:output message="tns:CancelSingleClassSoapOut" />
    </wsdl:operation>
  </wsdl:portType>
  <wsdl:binding name="Class_x0020_ServiceSoap" type="tns:Class_x0020_ServiceSoap">
    <soap:binding transport="http://schemas.xmlsoap.org/soap/http" />
    <wsdl:operation name="GetClasses">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5_1/GetClasses" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateClientVisits">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5_1/UpdateClientVisits" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetClassVisits">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5_1/GetClassVisits" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetClassDescriptions">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5_1/GetClassDescriptions" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetEnrollments">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5_1/GetEnrollments" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetClassSchedules">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5_1/GetClassSchedules" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="AddClientsToClasses">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5_1/AddClientsToClasses" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="AddClientToClass">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5_1/AddClientToClass" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="RemoveClientsFromClasses">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5_1/RemoveClientsFromClasses" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="AddClientsToEnrollments">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5_1/AddClientsToEnrollments" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="RemoveFromWaitlist">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5_1/RemoveFromWaitlist" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetSemesters">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5_1/GetSemesters" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetCourses">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5_1/GetCourses" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetWaitlistEntries">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5_1/GetWaitlistEntries" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="SubstituteClassTeacher">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5_1/SubstituteClassTeacher" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="SubtituteClassTeacher">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5_1/SubtituteClassTeacher" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="CancelSingleClass">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5_1/CancelSingleClass" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:service name="Class_x0020_Service">
    <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Provides methods and attributes relating to classes and enrollments.</wsdl:documentation>
    <wsdl:port name="Class_x0020_ServiceSoap" binding="tns:Class_x0020_ServiceSoap">
      <soap:address location="https://api.mindbodyonline.com/0_5_1/ClassService.asmx" />
    </wsdl:port>
  </wsdl:service>
</wsdl:definitions>