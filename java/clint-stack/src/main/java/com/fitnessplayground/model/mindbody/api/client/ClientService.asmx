<?xml version="1.0" encoding="utf-8"?>
<wsdl:definitions xmlns:tm="http://microsoft.com/wsdl/mime/textMatching/" xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/" xmlns:mime="http://schemas.xmlsoap.org/wsdl/mime/" xmlns:tns="http://clients.mindbodyonline.com/api/0_5" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:s="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/" xmlns:http="http://schemas.xmlsoap.org/wsdl/http/" targetNamespace="http://clients.mindbodyonline.com/api/0_5" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">
  <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Provides methods and attributes relating to clients.</wsdl:documentation>
  <wsdl:types>
    <s:schema elementFormDefault="qualified" targetNamespace="http://clients.mindbodyonline.com/api/0_5">
      <s:element name="AddArrival">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:AddArrivalRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="AddArrivalRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ClientID" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="LocationID" type="s:int" />
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
      <s:element name="AddArrivalResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="AddArrivalResult" type="tns:AddArrivalResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="AddArrivalResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="ArrivalAdded" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="ClientService" type="tns:ClientService" />
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
      <s:simpleType name="ActionCode">
        <s:restriction base="s:string">
          <s:enumeration value="None" />
          <s:enumeration value="Added" />
          <s:enumeration value="Updated" />
          <s:enumeration value="Failed" />
          <s:enumeration value="Removed" />
        </s:restriction>
      </s:simpleType>
      <s:element name="AddOrUpdateClients">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:AddOrUpdateClientsRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="AddOrUpdateClientsRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="UpdateAction" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="Test" nillable="true" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="Clients" type="tns:ArrayOfClient" />
              <s:element minOccurs="1" maxOccurs="1" name="SendEmail" nillable="true" type="s:boolean" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfClient">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Client" nillable="true" type="tns:Client" />
        </s:sequence>
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
      <s:element name="AddOrUpdateClientsResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="AddOrUpdateClientsResult" type="tns:AddOrUpdateClientsResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="AddOrUpdateClientsResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Clients" type="tns:ArrayOfClient" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetClients">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetClientsRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetClientsRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ClientIDs" type="tns:ArrayOfString" />
              <s:element minOccurs="0" maxOccurs="1" name="SearchText" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="IsProspect" nillable="true" type="s:boolean" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetClientsResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetClientsResult" type="tns:GetClientsResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetClientsResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Clients" type="tns:ArrayOfClient" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetCustomClientFields">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetCustomClientFieldsRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetCustomClientFieldsRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest" />
        </s:complexContent>
      </s:complexType>
      <s:element name="GetCustomClientFieldsResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetCustomClientFieldsResult" type="tns:GetCustomClientFieldsResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetCustomClientFieldsResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="CustomClientFields" type="tns:ArrayOfCustomClientField" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetClientIndexes">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetClientIndexesRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetClientIndexesRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="RequiredOnly" nillable="true" type="s:boolean" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetClientIndexesResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetClientIndexesResult" type="tns:GetClientIndexesResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetClientIndexesResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ClientIndexes" type="tns:ArrayOfClientIndex" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetClientContactLogs">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetClientContactLogsRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetClientContactLogsRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ClientID" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="StartDate" nillable="true" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="EndDate" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="StaffIDs" type="tns:ArrayOfLong" />
              <s:element minOccurs="1" maxOccurs="1" name="ShowSystemGenerated" nillable="true" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="TypeIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="0" maxOccurs="1" name="SubtypeIDs" type="tns:ArrayOfInt" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfLong">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="long" type="s:long" />
        </s:sequence>
      </s:complexType>
      <s:element name="GetClientContactLogsResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetClientContactLogsResult" type="tns:GetClientContactLogsResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetClientContactLogsResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ContactLogs" type="tns:ArrayOfContactLog" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfContactLog">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="ContactLog" nillable="true" type="tns:ContactLog" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ContactLog">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBObject">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ID" nillable="true" type="s:long" />
              <s:element minOccurs="0" maxOccurs="1" name="CreatedBy" type="tns:Staff" />
              <s:element minOccurs="0" maxOccurs="1" name="Client" type="tns:Client" />
              <s:element minOccurs="0" maxOccurs="1" name="CreatedDateTime" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="FollowupByDate" nillable="true" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="ContactName" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Text" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="AssignedTo" type="tns:Staff" />
              <s:element minOccurs="0" maxOccurs="1" name="ContactMethod" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="IsSystemGenerated" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="Comments" type="tns:ArrayOfContactLogComment" />
              <s:element minOccurs="0" maxOccurs="1" name="Types" type="tns:ArrayOfContactLogType" />
              <s:element minOccurs="0" maxOccurs="1" name="Action" type="tns:ActionCode" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfContactLogComment">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="ContactLogComment" nillable="true" type="tns:ContactLogComment" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ContactLogComment">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBObject">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ID" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="Text" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="CreatedBy" type="tns:Staff" />
              <s:element minOccurs="0" maxOccurs="1" name="CreatedDateTime" type="s:dateTime" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfContactLogType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="ContactLogType" nillable="true" type="tns:ContactLogType" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ContactLogType">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBObject">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ID" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Subtypes" type="tns:ArrayOfContactLogSubtype" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfContactLogSubtype">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="ContactLogSubtype" nillable="true" type="tns:ContactLogSubtype" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ContactLogSubtype">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBObject">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ID" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="AddOrUpdateContactLogs">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:AddOrUpdateContactLogsRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="AddOrUpdateContactLogsRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="UpdateAction" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="Test" nillable="true" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="ContactLogs" type="tns:ArrayOfContactLog" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="AddOrUpdateContactLogsResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="AddOrUpdateContactLogsResult" type="tns:AddOrUpdateContactLogsResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="AddOrUpdateContactLogsResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ContactLogs" type="tns:ArrayOfContactLog" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetContactLogTypes">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetContactLogTypesRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetContactLogTypesRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest" />
        </s:complexContent>
      </s:complexType>
      <s:element name="GetContactLogTypesResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetContactLogTypesResult" type="tns:GetContactLogTypesResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetContactLogTypesResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ContatctLogTypes" type="tns:ArrayOfContactLogType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="UploadClientDocument">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:UploadClientDocumentRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="UploadClientDocumentRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ClientID" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="FileName" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Bytes" type="s:base64Binary" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="UploadClientDocumentResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="UploadClientDocumentResult" type="tns:UploadClientDocumentResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="UploadClientDocumentResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="FileSize" type="s:long" />
              <s:element minOccurs="0" maxOccurs="1" name="FileName" type="s:string" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="UploadClientPhoto">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:UploadClientPhotoRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="UploadClientPhotoRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ClientID" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Bytes" type="s:base64Binary" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="UploadClientPhotoResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="UploadClientPhotoResult" type="tns:UploadClientPhotoResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="UploadClientPhotoResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="PhotoUrl" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="ClientID" type="s:string" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetClientFormulaNotes">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetClientFormulaNotesRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetClientFormulaNotesRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ClientID" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="AppointmentID" nillable="true" type="s:long" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetClientFormulaNotesResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetClientFormulaNotesResult" type="tns:GetClientFormulaNotesResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetClientFormulaNotesResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="FormulaNotes" type="tns:ArrayOfFormulaNote" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfFormulaNote">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="FormulaNote" nillable="true" type="tns:FormulaNote" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="FormulaNote">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBObject">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ID" type="s:long" />
              <s:element minOccurs="0" maxOccurs="1" name="ClientID" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Note" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="EntryDateTime" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="AppointmentID" nillable="true" type="s:long" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="AddClientFormulaNote">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:AddClientFormulaNoteRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="AddClientFormulaNoteRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ClientID" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="AppointmentID" nillable="true" type="s:long" />
              <s:element minOccurs="0" maxOccurs="1" name="Note" type="s:string" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="AddClientFormulaNoteResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="AddClientFormulaNoteResult" type="tns:AddClientFormulaNoteResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="AddClientFormulaNoteResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="FormulaNote" type="tns:FormulaNote" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="DeleteClientFormulaNote">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:DeleteCientFormulaNoteRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="DeleteCientFormulaNoteRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="FormulaNoteID" type="s:long" />
              <s:element minOccurs="0" maxOccurs="1" name="ClientID" type="s:string" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="DeleteClientFormulaNoteResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="DeleteClientFormulaNoteResult" type="tns:DeleteClientFormulaNoteResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="DeleteClientFormulaNoteResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="FormulaNote" type="tns:FormulaNote" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetClientReferralTypes">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetClientReferralTypesRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetClientReferralTypesRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="IncludeInactive" nillable="true" type="s:boolean" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetClientReferralTypesResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetClientReferralTypesResult" type="tns:GetClientReferralTypesResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetClientReferralTypesResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ReferralTypes" type="tns:ArrayOfString" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetActiveClientMemberships">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetActiveClientMembershipsRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetActiveClientMembershipsRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ClientID" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="LocationID" nillable="true" type="s:int" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetActiveClientMembershipsResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetActiveClientMembershipsResult" type="tns:GetActiveClientMembershipsResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetActiveClientMembershipsResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ClientMemberships" type="tns:ArrayOfClientMembership" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfClientMembership">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="ClientMembership" nillable="true" type="tns:ClientMembership" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ClientMembership">
        <s:complexContent mixed="false">
          <s:extension base="tns:ClientService">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="RestrictedLocations" type="tns:ArrayOfLocation" />
              <s:element minOccurs="0" maxOccurs="1" name="IconCode" type="s:string" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetClientContracts">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetClientContractsRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetClientContractsRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ClientID" type="s:string" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetClientContractsResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetClientContractsResult" type="tns:GetClientContractsResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetClientContractsResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Contracts" type="tns:ArrayOfClientContract" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfClientContract">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="ClientContract" nillable="true" type="tns:ClientContract" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ClientContract">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBObject">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="AgreementDate" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="StartDate" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="EndDate" type="s:dateTime" />
              <s:element minOccurs="0" maxOccurs="1" name="ContractName" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="Action" type="tns:ActionCode" />
              <s:element minOccurs="1" maxOccurs="1" name="ID" nillable="true" type="s:int" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetClientAccountBalances">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetClientAccountBalancesRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetClientAccountBalancesRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ClientIDs" type="tns:ArrayOfString" />
              <s:element minOccurs="1" maxOccurs="1" name="BalanceDate" nillable="true" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="ClassID" nillable="true" type="s:long" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetClientAccountBalancesResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetClientAccountBalancesResult" type="tns:GetClientAccountBalancesResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetClientAccountBalancesResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Clients" type="tns:ArrayOfClient" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetClientServices">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetClientServicesRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetClientServicesRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ClientID" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="ClassID" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="ProgramIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="0" maxOccurs="1" name="SessionTypeIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="0" maxOccurs="1" name="LocationIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="1" maxOccurs="1" name="VisitCount" nillable="true" type="s:int" />
              <s:element minOccurs="1" maxOccurs="1" name="StartDate" nillable="true" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="EndDate" nillable="true" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="ShowActiveOnly" nillable="true" type="s:boolean" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetClientServicesResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetClientServicesResult" type="tns:GetClientServicesResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetClientServicesResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ClientServices" type="tns:ArrayOfClientService" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfClientService">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="ClientService" nillable="true" type="tns:ClientService" />
        </s:sequence>
      </s:complexType>
      <s:element name="GetClientVisits">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetClientVisitsRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetClientVisitsRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ClientID" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="StartDate" nillable="true" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="EndDate" nillable="true" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="UnpaidsOnly" type="s:boolean" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetClientVisitsResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetClientVisitsResult" type="tns:GetClientVisitsResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetClientVisitsResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Visits" type="tns:ArrayOfVisit" />
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
      <s:element name="GetClientPurchases">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetClientPurchasesRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetClientPurchasesRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ClientID" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="StartDate" nillable="true" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="EndDate" nillable="true" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="SaleID" nillable="true" type="s:int" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetClientPurchasesResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetClientPurchasesResult" type="tns:GetClientPurchasesResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetClientPurchasesResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Purchases" type="tns:ArrayOfSaleItem" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfSaleItem">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="SaleItem" nillable="true" type="tns:SaleItem" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="SaleItem">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBObject">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Sale" type="tns:Sale" />
              <s:element minOccurs="0" maxOccurs="1" name="Description" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="AccountPayment" nillable="true" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="Price" type="s:decimal" />
              <s:element minOccurs="0" maxOccurs="1" name="AmountPaid" type="s:decimal" />
              <s:element minOccurs="0" maxOccurs="1" name="Discount" type="s:decimal" />
              <s:element minOccurs="0" maxOccurs="1" name="Tax" type="s:decimal" />
              <s:element minOccurs="0" maxOccurs="1" name="Returned" type="s:boolean" />
              <s:element minOccurs="0" maxOccurs="1" name="Quantity" type="s:int" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
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
      <s:element name="GetClientSchedule">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetClientScheduleRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetClientScheduleRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ClientID" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="StartDate" nillable="true" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="EndDate" nillable="true" type="s:dateTime" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetClientScheduleResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetClientScheduleResult" type="tns:GetClientScheduleResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetClientScheduleResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Visits" type="tns:ArrayOfVisit" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetRequiredClientFields">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetRequiredClientFieldsRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetRequiredClientFieldsRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest" />
        </s:complexContent>
      </s:complexType>
      <s:element name="GetRequiredClientFieldsResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetRequiredClientFieldsResult" type="tns:GetRequiredClientFieldsResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetRequiredClientFieldsResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="RequiredClientFields" type="tns:ArrayOfString" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="ValidateLogin">
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
      <s:element name="ValidateLoginResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="ValidateLoginResult" type="tns:ValidateLoginResult" />
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
      <s:element name="UpdateClientServices">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:UpdateClientServicesRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="UpdateClientServicesRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ClientServices" type="tns:ArrayOfClientService" />
              <s:element minOccurs="1" maxOccurs="1" name="Test" nillable="true" type="s:boolean" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="UpdateClientServicesResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="UpdateClientServicesResult" type="tns:UpdateClientServicesResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="UpdateClientServicesResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ClientServices" type="tns:ArrayOfClientService" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="SendUserNewPassword">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:ClientSendUserNewPasswordRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="ClientSendUserNewPasswordRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="UserEmail" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="UserFirstName" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="UserLastName" type="s:string" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="SendUserNewPasswordResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="SendUserNewPasswordResult" type="tns:ClientSendUserNewPasswordResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="ClientSendUserNewPasswordResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult" />
        </s:complexContent>
      </s:complexType>
    </s:schema>
  </wsdl:types>
  <wsdl:message name="AddArrivalSoapIn">
    <wsdl:part name="parameters" element="tns:AddArrival" />
  </wsdl:message>
  <wsdl:message name="AddArrivalSoapOut">
    <wsdl:part name="parameters" element="tns:AddArrivalResponse" />
  </wsdl:message>
  <wsdl:message name="AddOrUpdateClientsSoapIn">
    <wsdl:part name="parameters" element="tns:AddOrUpdateClients" />
  </wsdl:message>
  <wsdl:message name="AddOrUpdateClientsSoapOut">
    <wsdl:part name="parameters" element="tns:AddOrUpdateClientsResponse" />
  </wsdl:message>
  <wsdl:message name="GetClientsSoapIn">
    <wsdl:part name="parameters" element="tns:GetClients" />
  </wsdl:message>
  <wsdl:message name="GetClientsSoapOut">
    <wsdl:part name="parameters" element="tns:GetClientsResponse" />
  </wsdl:message>
  <wsdl:message name="GetCustomClientFieldsSoapIn">
    <wsdl:part name="parameters" element="tns:GetCustomClientFields" />
  </wsdl:message>
  <wsdl:message name="GetCustomClientFieldsSoapOut">
    <wsdl:part name="parameters" element="tns:GetCustomClientFieldsResponse" />
  </wsdl:message>
  <wsdl:message name="GetClientIndexesSoapIn">
    <wsdl:part name="parameters" element="tns:GetClientIndexes" />
  </wsdl:message>
  <wsdl:message name="GetClientIndexesSoapOut">
    <wsdl:part name="parameters" element="tns:GetClientIndexesResponse" />
  </wsdl:message>
  <wsdl:message name="GetClientContactLogsSoapIn">
    <wsdl:part name="parameters" element="tns:GetClientContactLogs" />
  </wsdl:message>
  <wsdl:message name="GetClientContactLogsSoapOut">
    <wsdl:part name="parameters" element="tns:GetClientContactLogsResponse" />
  </wsdl:message>
  <wsdl:message name="AddOrUpdateContactLogsSoapIn">
    <wsdl:part name="parameters" element="tns:AddOrUpdateContactLogs" />
  </wsdl:message>
  <wsdl:message name="AddOrUpdateContactLogsSoapOut">
    <wsdl:part name="parameters" element="tns:AddOrUpdateContactLogsResponse" />
  </wsdl:message>
  <wsdl:message name="GetContactLogTypesSoapIn">
    <wsdl:part name="parameters" element="tns:GetContactLogTypes" />
  </wsdl:message>
  <wsdl:message name="GetContactLogTypesSoapOut">
    <wsdl:part name="parameters" element="tns:GetContactLogTypesResponse" />
  </wsdl:message>
  <wsdl:message name="UploadClientDocumentSoapIn">
    <wsdl:part name="parameters" element="tns:UploadClientDocument" />
  </wsdl:message>
  <wsdl:message name="UploadClientDocumentSoapOut">
    <wsdl:part name="parameters" element="tns:UploadClientDocumentResponse" />
  </wsdl:message>
  <wsdl:message name="UploadClientPhotoSoapIn">
    <wsdl:part name="parameters" element="tns:UploadClientPhoto" />
  </wsdl:message>
  <wsdl:message name="UploadClientPhotoSoapOut">
    <wsdl:part name="parameters" element="tns:UploadClientPhotoResponse" />
  </wsdl:message>
  <wsdl:message name="GetClientFormulaNotesSoapIn">
    <wsdl:part name="parameters" element="tns:GetClientFormulaNotes" />
  </wsdl:message>
  <wsdl:message name="GetClientFormulaNotesSoapOut">
    <wsdl:part name="parameters" element="tns:GetClientFormulaNotesResponse" />
  </wsdl:message>
  <wsdl:message name="AddClientFormulaNoteSoapIn">
    <wsdl:part name="parameters" element="tns:AddClientFormulaNote" />
  </wsdl:message>
  <wsdl:message name="AddClientFormulaNoteSoapOut">
    <wsdl:part name="parameters" element="tns:AddClientFormulaNoteResponse" />
  </wsdl:message>
  <wsdl:message name="DeleteClientFormulaNoteSoapIn">
    <wsdl:part name="parameters" element="tns:DeleteClientFormulaNote" />
  </wsdl:message>
  <wsdl:message name="DeleteClientFormulaNoteSoapOut">
    <wsdl:part name="parameters" element="tns:DeleteClientFormulaNoteResponse" />
  </wsdl:message>
  <wsdl:message name="GetClientReferralTypesSoapIn">
    <wsdl:part name="parameters" element="tns:GetClientReferralTypes" />
  </wsdl:message>
  <wsdl:message name="GetClientReferralTypesSoapOut">
    <wsdl:part name="parameters" element="tns:GetClientReferralTypesResponse" />
  </wsdl:message>
  <wsdl:message name="GetActiveClientMembershipsSoapIn">
    <wsdl:part name="parameters" element="tns:GetActiveClientMemberships" />
  </wsdl:message>
  <wsdl:message name="GetActiveClientMembershipsSoapOut">
    <wsdl:part name="parameters" element="tns:GetActiveClientMembershipsResponse" />
  </wsdl:message>
  <wsdl:message name="GetClientContractsSoapIn">
    <wsdl:part name="parameters" element="tns:GetClientContracts" />
  </wsdl:message>
  <wsdl:message name="GetClientContractsSoapOut">
    <wsdl:part name="parameters" element="tns:GetClientContractsResponse" />
  </wsdl:message>
  <wsdl:message name="GetClientAccountBalancesSoapIn">
    <wsdl:part name="parameters" element="tns:GetClientAccountBalances" />
  </wsdl:message>
  <wsdl:message name="GetClientAccountBalancesSoapOut">
    <wsdl:part name="parameters" element="tns:GetClientAccountBalancesResponse" />
  </wsdl:message>
  <wsdl:message name="GetClientServicesSoapIn">
    <wsdl:part name="parameters" element="tns:GetClientServices" />
  </wsdl:message>
  <wsdl:message name="GetClientServicesSoapOut">
    <wsdl:part name="parameters" element="tns:GetClientServicesResponse" />
  </wsdl:message>
  <wsdl:message name="GetClientVisitsSoapIn">
    <wsdl:part name="parameters" element="tns:GetClientVisits" />
  </wsdl:message>
  <wsdl:message name="GetClientVisitsSoapOut">
    <wsdl:part name="parameters" element="tns:GetClientVisitsResponse" />
  </wsdl:message>
  <wsdl:message name="GetClientPurchasesSoapIn">
    <wsdl:part name="parameters" element="tns:GetClientPurchases" />
  </wsdl:message>
  <wsdl:message name="GetClientPurchasesSoapOut">
    <wsdl:part name="parameters" element="tns:GetClientPurchasesResponse" />
  </wsdl:message>
  <wsdl:message name="GetClientScheduleSoapIn">
    <wsdl:part name="parameters" element="tns:GetClientSchedule" />
  </wsdl:message>
  <wsdl:message name="GetClientScheduleSoapOut">
    <wsdl:part name="parameters" element="tns:GetClientScheduleResponse" />
  </wsdl:message>
  <wsdl:message name="GetRequiredClientFieldsSoapIn">
    <wsdl:part name="parameters" element="tns:GetRequiredClientFields" />
  </wsdl:message>
  <wsdl:message name="GetRequiredClientFieldsSoapOut">
    <wsdl:part name="parameters" element="tns:GetRequiredClientFieldsResponse" />
  </wsdl:message>
  <wsdl:message name="ValidateLoginSoapIn">
    <wsdl:part name="parameters" element="tns:ValidateLogin" />
  </wsdl:message>
  <wsdl:message name="ValidateLoginSoapOut">
    <wsdl:part name="parameters" element="tns:ValidateLoginResponse" />
  </wsdl:message>
  <wsdl:message name="UpdateClientServicesSoapIn">
    <wsdl:part name="parameters" element="tns:UpdateClientServices" />
  </wsdl:message>
  <wsdl:message name="UpdateClientServicesSoapOut">
    <wsdl:part name="parameters" element="tns:UpdateClientServicesResponse" />
  </wsdl:message>
  <wsdl:message name="SendUserNewPasswordSoapIn">
    <wsdl:part name="parameters" element="tns:SendUserNewPassword" />
  </wsdl:message>
  <wsdl:message name="SendUserNewPasswordSoapOut">
    <wsdl:part name="parameters" element="tns:SendUserNewPasswordResponse" />
  </wsdl:message>
  <wsdl:portType name="Client_x0020_ServiceSoap">
    <wsdl:operation name="AddArrival">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Adds an arrival record for the given client.</wsdl:documentation>
      <wsdl:input message="tns:AddArrivalSoapIn" />
      <wsdl:output message="tns:AddArrivalSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="AddOrUpdateClients">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Adds or updates information for a list of clients.</wsdl:documentation>
      <wsdl:input message="tns:AddOrUpdateClientsSoapIn" />
      <wsdl:output message="tns:AddOrUpdateClientsSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetClients">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list of clients.</wsdl:documentation>
      <wsdl:input message="tns:GetClientsSoapIn" />
      <wsdl:output message="tns:GetClientsSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetCustomClientFields">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list of currently available client indexes.</wsdl:documentation>
      <wsdl:input message="tns:GetCustomClientFieldsSoapIn" />
      <wsdl:output message="tns:GetCustomClientFieldsSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetClientIndexes">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list of currently available client indexes.</wsdl:documentation>
      <wsdl:input message="tns:GetClientIndexesSoapIn" />
      <wsdl:output message="tns:GetClientIndexesSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetClientContactLogs">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Get contact logs for a client.</wsdl:documentation>
      <wsdl:input message="tns:GetClientContactLogsSoapIn" />
      <wsdl:output message="tns:GetClientContactLogsSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="AddOrUpdateContactLogs">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Add or update client contact logs.</wsdl:documentation>
      <wsdl:input message="tns:AddOrUpdateContactLogsSoapIn" />
      <wsdl:output message="tns:AddOrUpdateContactLogsSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetContactLogTypes">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Get contact log types for a client.</wsdl:documentation>
      <wsdl:input message="tns:GetContactLogTypesSoapIn" />
      <wsdl:output message="tns:GetContactLogTypesSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="UploadClientDocument">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Upload a client document.</wsdl:documentation>
      <wsdl:input message="tns:UploadClientDocumentSoapIn" />
      <wsdl:output message="tns:UploadClientDocumentSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="UploadClientPhoto">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Upload a client photo.</wsdl:documentation>
      <wsdl:input message="tns:UploadClientPhotoSoapIn" />
      <wsdl:output message="tns:UploadClientPhotoSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetClientFormulaNotes">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list of client formula notes.</wsdl:documentation>
      <wsdl:input message="tns:GetClientFormulaNotesSoapIn" />
      <wsdl:output message="tns:GetClientFormulaNotesSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="AddClientFormulaNote">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Adds a formula note to a client.</wsdl:documentation>
      <wsdl:input message="tns:AddClientFormulaNoteSoapIn" />
      <wsdl:output message="tns:AddClientFormulaNoteSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="DeleteClientFormulaNote">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Deletes a formula note to a client.</wsdl:documentation>
      <wsdl:input message="tns:DeleteClientFormulaNoteSoapIn" />
      <wsdl:output message="tns:DeleteClientFormulaNoteSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetClientReferralTypes">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list of clients.</wsdl:documentation>
      <wsdl:input message="tns:GetClientReferralTypesSoapIn" />
      <wsdl:output message="tns:GetClientReferralTypesSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetActiveClientMemberships">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets the active membership for a given client.</wsdl:documentation>
      <wsdl:input message="tns:GetActiveClientMembershipsSoapIn" />
      <wsdl:output message="tns:GetActiveClientMembershipsSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetClientContracts">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list of contracts for a given client.</wsdl:documentation>
      <wsdl:input message="tns:GetClientContractsSoapIn" />
      <wsdl:output message="tns:GetClientContractsSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetClientAccountBalances">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets account balances for the given clients.</wsdl:documentation>
      <wsdl:input message="tns:GetClientAccountBalancesSoapIn" />
      <wsdl:output message="tns:GetClientAccountBalancesSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetClientServices">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a client service for a given client.</wsdl:documentation>
      <wsdl:input message="tns:GetClientServicesSoapIn" />
      <wsdl:output message="tns:GetClientServicesSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetClientVisits">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Get visits for a client.</wsdl:documentation>
      <wsdl:input message="tns:GetClientVisitsSoapIn" />
      <wsdl:output message="tns:GetClientVisitsSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetClientPurchases">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Get purchases for a client.</wsdl:documentation>
      <wsdl:input message="tns:GetClientPurchasesSoapIn" />
      <wsdl:output message="tns:GetClientPurchasesSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetClientSchedule">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Get visits for a client.</wsdl:documentation>
      <wsdl:input message="tns:GetClientScheduleSoapIn" />
      <wsdl:output message="tns:GetClientScheduleSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetRequiredClientFields">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Updates a client service for a given client.</wsdl:documentation>
      <wsdl:input message="tns:GetRequiredClientFieldsSoapIn" />
      <wsdl:output message="tns:GetRequiredClientFieldsSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="ValidateLogin">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Validates a username and password. This method returns the associated clients record and a session GUID on success.</wsdl:documentation>
      <wsdl:input message="tns:ValidateLoginSoapIn" />
      <wsdl:output message="tns:ValidateLoginSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="UpdateClientServices">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Updates a client service for a given client.</wsdl:documentation>
      <wsdl:input message="tns:UpdateClientServicesSoapIn" />
      <wsdl:output message="tns:UpdateClientServicesSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="SendUserNewPassword">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Sends the user a new password.</wsdl:documentation>
      <wsdl:input message="tns:SendUserNewPasswordSoapIn" />
      <wsdl:output message="tns:SendUserNewPasswordSoapOut" />
    </wsdl:operation>
  </wsdl:portType>
  <wsdl:binding name="Client_x0020_ServiceSoap" type="tns:Client_x0020_ServiceSoap">
    <soap:binding transport="http://schemas.xmlsoap.org/soap/http" />
    <wsdl:operation name="AddArrival">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/AddArrival" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="AddOrUpdateClients">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/AddOrUpdateClients" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetClients">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetClients" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetCustomClientFields">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetCustomClientFields" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetClientIndexes">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetClientIndexes" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetClientContactLogs">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetClientContactLogs" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="AddOrUpdateContactLogs">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/AddOrUpdateContactLogs" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetContactLogTypes">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetContactLogTypes" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UploadClientDocument">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/UploadClientDocument" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UploadClientPhoto">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/UploadClientPhoto" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetClientFormulaNotes">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetClientFormulaNotes" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="AddClientFormulaNote">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/AddClientFormulaNote" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="DeleteClientFormulaNote">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/DeleteClientFormulaNote" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetClientReferralTypes">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetClientReferralTypes" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetActiveClientMemberships">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetActiveClientMemberships" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetClientContracts">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetClientContracts" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetClientAccountBalances">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetClientAccountBalances" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetClientServices">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetClientServices" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetClientVisits">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetClientVisits" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetClientPurchases">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetClientPurchases" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetClientSchedule">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetClientSchedule" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetRequiredClientFields">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetRequiredClientFields" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ValidateLogin">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/ValidateLogin" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateClientServices">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/UpdateClientServices" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="SendUserNewPassword">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/SendUserNewPassword" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:service name="Client_x0020_Service">
    <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Provides methods and attributes relating to clients.</wsdl:documentation>
    <wsdl:port name="Client_x0020_ServiceSoap" binding="tns:Client_x0020_ServiceSoap">
      <soap:address location="https://api.mindbodyonline.com/0_5/ClientService.asmx" />
    </wsdl:port>
  </wsdl:service>
</wsdl:definitions>