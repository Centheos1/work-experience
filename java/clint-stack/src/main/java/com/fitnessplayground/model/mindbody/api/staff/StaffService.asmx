<?xml version="1.0" encoding="utf-8"?>
<wsdl:definitions xmlns:tm="http://microsoft.com/wsdl/mime/textMatching/" xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/" xmlns:mime="http://schemas.xmlsoap.org/wsdl/mime/" xmlns:tns="http://clients.mindbodyonline.com/api/0_5" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:s="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/" xmlns:http="http://schemas.xmlsoap.org/wsdl/http/" targetNamespace="http://clients.mindbodyonline.com/api/0_5" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">
  <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Provides methods and attributes relating to staff.</wsdl:documentation>
  <wsdl:types>
    <s:schema elementFormDefault="qualified" targetNamespace="http://clients.mindbodyonline.com/api/0_5">
      <s:element name="GetStaff">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetStaffRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetStaffRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="StaffIDs" type="tns:ArrayOfLong" />
              <s:element minOccurs="0" maxOccurs="1" name="StaffCredentials" type="tns:StaffCredentials" />
              <s:element minOccurs="0" maxOccurs="1" name="Filters" type="tns:ArrayOfStaffFilter" />
              <s:element minOccurs="1" maxOccurs="1" name="SessionTypeID" nillable="true" type="s:int" />
              <s:element minOccurs="1" maxOccurs="1" name="StartDateTime" nillable="true" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="LocationID" nillable="true" type="s:int" />
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
      <s:complexType name="StaffCredentials">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Username" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Password" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="SiteIDs" type="tns:ArrayOfInt" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ArrayOfStaffFilter">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="StaffFilter" type="tns:StaffFilter" />
        </s:sequence>
      </s:complexType>
      <s:simpleType name="StaffFilter">
        <s:restriction base="s:string">
          <s:enumeration value="StaffViewable" />
          <s:enumeration value="AppointmentInstructor" />
          <s:enumeration value="ClassInstructor" />
          <s:enumeration value="Male" />
          <s:enumeration value="Female" />
        </s:restriction>
      </s:simpleType>
      <s:element name="GetStaffResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetStaffResult" type="tns:GetStaffResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetStaffResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="StaffMembers" type="tns:ArrayOfStaff" />
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
      <s:complexType name="ArrayOfStaff">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Staff" nillable="true" type="tns:Staff" />
        </s:sequence>
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
      <s:complexType name="Relationship">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="ID" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="RelationshipName1" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="RelationshipName2" type="s:string" />
        </s:sequence>
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
      <s:complexType name="ScheduleItem">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBObject" />
        </s:complexContent>
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
      <s:complexType name="ArrayOfAppointment">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Appointment" nillable="true" type="tns:Appointment" />
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
      <s:element name="GetStaffPermissions">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetStaffPermissionsRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetStaffPermissionsRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="StaffID" type="s:long" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetStaffPermissionsResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetStaffPermissionsResult" type="tns:GetStaffPermissionsResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetStaffPermissionsResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Permissions" type="tns:ArrayOfPermission" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfPermission">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Permission" nillable="true" type="tns:Permission" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="Permission">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="DisplayName" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Value" type="s:string" />
        </s:sequence>
      </s:complexType>
      <s:element name="AddOrUpdateStaff">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:AddOrUpdateStaffRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="AddOrUpdateStaffRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="UpdateAction" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="Test" nillable="true" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="Staff" type="tns:ArrayOfStaff" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="AddOrUpdateStaffResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="AddOrUpdateStaffResult" type="tns:AddOrUpdateStaffResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="AddOrUpdateStaffResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Staff" type="tns:ArrayOfStaff" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetStaffImgURL">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetStaffImgURLRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetStaffImgURLRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="StaffID" type="s:long" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetStaffImgURLResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetStaffImgURLResult" type="tns:GetStaffImgURLResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetStaffImgURLResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ImageURL" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="MobileImageURL" type="s:string" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="ValidateStaffLogin">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:ValidateLoginRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="ValidateLoginRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Username" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Password" type="s:string" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="ValidateStaffLoginResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="ValidateStaffLoginResult" type="tns:ValidateLoginResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="ValidateLoginResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="GUID" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Client" type="tns:Client" />
              <s:element minOccurs="0" maxOccurs="1" name="Staff" type="tns:Staff" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetSalesReps">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetSalesRepsRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetSalesRepsRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="SalesRepNumbers" type="tns:ArrayOfInt" />
              <s:element minOccurs="1" maxOccurs="1" name="ShowActiveOnly" nillable="true" type="s:boolean" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetSalesRepsResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetSalesRepsResult" type="tns:GetSalesRepsResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetSalesRepsResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="SalesReps" type="tns:ArrayOfSalesRep" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
    </s:schema>
  </wsdl:types>
  <wsdl:message name="GetStaffSoapIn">
    <wsdl:part name="parameters" element="tns:GetStaff" />
  </wsdl:message>
  <wsdl:message name="GetStaffSoapOut">
    <wsdl:part name="parameters" element="tns:GetStaffResponse" />
  </wsdl:message>
  <wsdl:message name="GetStaffPermissionsSoapIn">
    <wsdl:part name="parameters" element="tns:GetStaffPermissions" />
  </wsdl:message>
  <wsdl:message name="GetStaffPermissionsSoapOut">
    <wsdl:part name="parameters" element="tns:GetStaffPermissionsResponse" />
  </wsdl:message>
  <wsdl:message name="AddOrUpdateStaffSoapIn">
    <wsdl:part name="parameters" element="tns:AddOrUpdateStaff" />
  </wsdl:message>
  <wsdl:message name="AddOrUpdateStaffSoapOut">
    <wsdl:part name="parameters" element="tns:AddOrUpdateStaffResponse" />
  </wsdl:message>
  <wsdl:message name="GetStaffImgURLSoapIn">
    <wsdl:part name="parameters" element="tns:GetStaffImgURL" />
  </wsdl:message>
  <wsdl:message name="GetStaffImgURLSoapOut">
    <wsdl:part name="parameters" element="tns:GetStaffImgURLResponse" />
  </wsdl:message>
  <wsdl:message name="ValidateStaffLoginSoapIn">
    <wsdl:part name="parameters" element="tns:ValidateStaffLogin" />
  </wsdl:message>
  <wsdl:message name="ValidateStaffLoginSoapOut">
    <wsdl:part name="parameters" element="tns:ValidateStaffLoginResponse" />
  </wsdl:message>
  <wsdl:message name="GetSalesRepsSoapIn">
    <wsdl:part name="parameters" element="tns:GetSalesReps" />
  </wsdl:message>
  <wsdl:message name="GetSalesRepsSoapOut">
    <wsdl:part name="parameters" element="tns:GetSalesRepsResponse" />
  </wsdl:message>
  <wsdl:portType name="Staff_x0020_ServiceSoap">
    <wsdl:operation name="GetStaff">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list of staff members.</wsdl:documentation>
      <wsdl:input message="tns:GetStaffSoapIn" />
      <wsdl:output message="tns:GetStaffSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetStaffPermissions">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list of staff permissions based on the given staff member.</wsdl:documentation>
      <wsdl:input message="tns:GetStaffPermissionsSoapIn" />
      <wsdl:output message="tns:GetStaffPermissionsSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="AddOrUpdateStaff">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Add or update staff.</wsdl:documentation>
      <wsdl:input message="tns:AddOrUpdateStaffSoapIn" />
      <wsdl:output message="tns:AddOrUpdateStaffSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetStaffImgURL">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a staff member's image URL if it exists.</wsdl:documentation>
      <wsdl:input message="tns:GetStaffImgURLSoapIn" />
      <wsdl:output message="tns:GetStaffImgURLSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="ValidateStaffLogin">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Validates a username and password. This method returns the staff on success.</wsdl:documentation>
      <wsdl:input message="tns:ValidateStaffLoginSoapIn" />
      <wsdl:output message="tns:ValidateStaffLoginSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetSalesReps">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list of sales reps based on the requested rep IDs</wsdl:documentation>
      <wsdl:input message="tns:GetSalesRepsSoapIn" />
      <wsdl:output message="tns:GetSalesRepsSoapOut" />
    </wsdl:operation>
  </wsdl:portType>
  <wsdl:binding name="Staff_x0020_ServiceSoap" type="tns:Staff_x0020_ServiceSoap">
    <soap:binding transport="http://schemas.xmlsoap.org/soap/http" />
    <wsdl:operation name="GetStaff">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetStaff" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetStaffPermissions">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetStaffPermissions" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="AddOrUpdateStaff">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/AddOrUpdateStaff" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetStaffImgURL">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetStaffImgURL" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ValidateStaffLogin">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/ValidateStaffLogin" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetSalesReps">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetSalesReps" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:service name="Staff_x0020_Service">
    <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Provides methods and attributes relating to staff.</wsdl:documentation>
    <wsdl:port name="Staff_x0020_ServiceSoap" binding="tns:Staff_x0020_ServiceSoap">
      <soap:address location="https://api.mindbodyonline.com/0_5/StaffService.asmx" />
    </wsdl:port>
  </wsdl:service>
</wsdl:definitions>