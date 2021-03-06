<?xml version="1.0" encoding="utf-8"?>
<wsdl:definitions xmlns:tm="http://microsoft.com/wsdl/mime/textMatching/" xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/" xmlns:mime="http://schemas.xmlsoap.org/wsdl/mime/" xmlns:tns="http://clients.mindbodyonline.com/api/0_5" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:s1="http://clients.mindbodyonline.com/API/0_4" xmlns:s="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/" xmlns:http="http://schemas.xmlsoap.org/wsdl/http/" targetNamespace="http://clients.mindbodyonline.com/api/0_5" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">
  <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Provides methods and attributes relating to sites and locations.</wsdl:documentation>
  <wsdl:types>
    <s:schema elementFormDefault="qualified" targetNamespace="http://clients.mindbodyonline.com/api/0_5">
      <s:import namespace="http://clients.mindbodyonline.com/API/0_4" />
      <s:element name="GetSites">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetSitesRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetSitesRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="SearchText" type="s:string" />
              <s:element minOccurs="1" maxOccurs="1" name="RelatedSiteID" nillable="true" type="s:int" />
              <s:element minOccurs="1" maxOccurs="1" name="ShowOnlyTotalWOD" nillable="true" type="s:boolean" />
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
      <s:element name="GetSitesResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetSitesResult" type="tns:GetSitesResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetSitesResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Sites" type="tns:ArrayOfSite" />
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
      <s:complexType name="ArrayOfSite">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Site" nillable="true" type="tns:Site" />
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
      <s:element name="GetLocations">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetLocationsRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetLocationsRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest" />
        </s:complexContent>
      </s:complexType>
      <s:element name="GetLocationsResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetLocationsResult" type="tns:GetLocationsResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetLocationsResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Locations" type="tns:ArrayOfLocation" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfLocation">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Location" nillable="true" type="tns:Location" />
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
      <s:complexType name="MBObject">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Site" type="tns:Site" />
          <s:element minOccurs="0" maxOccurs="1" name="Messages" type="tns:ArrayOfString" />
          <s:element minOccurs="0" maxOccurs="1" name="Execute" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="ErrorCode" type="s:string" />
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
      <s:element name="GetActivationCode">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetActivationCodeRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetActivationCodeRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest" />
        </s:complexContent>
      </s:complexType>
      <s:element name="GetActivationCodeResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetActivationCodeResult" type="tns:GetActivationCodeResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetActivationCodeResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ActivationCode" type="s:string" />
              <s:element minOccurs="0" maxOccurs="1" name="ActivationLink" type="s:string" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetPrograms">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetProgramsRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetProgramsRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="ScheduleType" type="tns:ScheduleType" />
              <s:element minOccurs="1" maxOccurs="1" name="OnlineOnly" type="s:boolean" />
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
      <s:element name="GetProgramsResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetProgramsResult" type="tns:GetProgramsResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetProgramsResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Programs" type="tns:ArrayOfProgram" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
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
      <s:element name="GetSessionTypes">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetSessionTypesRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetSessionTypesRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ProgramIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="1" maxOccurs="1" name="OnlineOnly" type="s:boolean" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetSessionTypesResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetSessionTypesResult" type="tns:GetSessionTypesResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetSessionTypesResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="SessionTypes" type="tns:ArrayOfSessionType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfSessionType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="SessionType" nillable="true" type="tns:SessionType" />
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
      <s:element name="GetResources">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetResourcesRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetResourcesRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="SessionTypeIDs" type="tns:ArrayOfInt" />
              <s:element minOccurs="1" maxOccurs="1" name="LocationID" type="s:int" />
              <s:element minOccurs="1" maxOccurs="1" name="StartDateTime" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="EndDateTime" type="s:dateTime" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetResourcesResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetResourcesResult" type="tns:GetResourcesResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetResourcesResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Resources" type="tns:ArrayOfResource" />
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
      <s:element name="GetRelationships">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetRelationshipsRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetRelationshipsRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest" />
        </s:complexContent>
      </s:complexType>
      <s:element name="GetRelationshipsResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetRelationshipsResult" type="tns:GetRelationshipsResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetRelationshipsResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Relationships" type="tns:ArrayOfRelationship" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfRelationship">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Relationship" nillable="true" type="tns:Relationship" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="Relationship">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="ID" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="RelationshipName1" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="RelationshipName2" type="s:string" />
        </s:sequence>
      </s:complexType>
      <s:element name="GetResourceSchedule">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetResourceScheduleRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetResourceScheduleRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="Date" nillable="true" type="s:dateTime" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetResourceScheduleResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetResourceScheduleResult" type="tns:GetResourceScheduleResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetResourceScheduleResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Results" type="s1:RecordSet" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="ReserveResource">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:ReserveResourceRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="ReserveResourceRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="ResourceID" type="s:int" />
              <s:element minOccurs="1" maxOccurs="1" name="ClientID" type="s:int" />
              <s:element minOccurs="1" maxOccurs="1" name="StaffID" type="s:int" />
              <s:element minOccurs="1" maxOccurs="1" name="StartDateTime" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="EndDateTime" type="s:dateTime" />
              <s:element minOccurs="1" maxOccurs="1" name="LocationID" type="s:int" />
              <s:element minOccurs="1" maxOccurs="1" name="ProgramID" type="s:int" />
              <s:element minOccurs="0" maxOccurs="1" name="Notes" type="s:string" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="ReserveResourceResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="ReserveResourceResult" type="tns:ReserveResourceResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="ReserveResourceResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult" />
        </s:complexContent>
      </s:complexType>
      <s:element name="GetMobileProviders">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetMobileProvidersRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetMobileProvidersRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest" />
        </s:complexContent>
      </s:complexType>
      <s:element name="GetMobileProvidersResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetMobileProvidersResult" type="tns:GetMobileProvidersResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetMobileProvidersResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="MobileProviders" type="tns:ArrayOfMobileProvider" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfMobileProvider">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="MobileProvider" nillable="true" type="tns:MobileProvider" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="MobileProvider">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="ProviderID" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="ProviderName" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="ProviderAddress" type="s:string" />
          <s:element minOccurs="1" maxOccurs="1" name="Active" type="s:boolean" />
        </s:sequence>
      </s:complexType>
      <s:element name="GetProspectStages">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetProspectStagesRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetProspectStagesRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="IncludeInactive" nillable="true" type="s:boolean" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetProspectStagesResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetProspectStagesResult" type="tns:GetProspectStagesResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetProspectStagesResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="ProspectStages" type="tns:ArrayOfProspectStage" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfProspectStage">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="ProspectStage" nillable="true" type="tns:ProspectStage" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ProspectStage">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="ID" nillable="true" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Description" type="s:string" />
          <s:element minOccurs="1" maxOccurs="1" name="Active" type="s:boolean" />
        </s:sequence>
      </s:complexType>
      <s:element name="GetGenders">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Request" type="tns:GetGendersRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetGendersRequest">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBRequest">
            <s:sequence>
              <s:element minOccurs="1" maxOccurs="1" name="IncludeInactive" nillable="true" type="s:boolean" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="GetGendersResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetGendersResult" type="tns:GetGendersResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetGendersResult">
        <s:complexContent mixed="false">
          <s:extension base="tns:MBResult">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="GenderOptions" type="tns:ArrayOfGenderOption" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ArrayOfGenderOption">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="GenderOption" nillable="true" type="tns:GenderOption" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="GenderOption">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="ID" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
          <s:element minOccurs="1" maxOccurs="1" name="IsActive" type="s:boolean" />
          <s:element minOccurs="1" maxOccurs="1" name="IsSystem" type="s:boolean" />
        </s:sequence>
      </s:complexType>
    </s:schema>
    <s:schema targetNamespace="http://clients.mindbodyonline.com/API/0_4">
      <s:complexType name="Row">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Content" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="RecordSet">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Row" type="s1:Row" />
        </s:sequence>
      </s:complexType>
    </s:schema>
  </wsdl:types>
  <wsdl:message name="GetSitesSoapIn">
    <wsdl:part name="parameters" element="tns:GetSites" />
  </wsdl:message>
  <wsdl:message name="GetSitesSoapOut">
    <wsdl:part name="parameters" element="tns:GetSitesResponse" />
  </wsdl:message>
  <wsdl:message name="GetLocationsSoapIn">
    <wsdl:part name="parameters" element="tns:GetLocations" />
  </wsdl:message>
  <wsdl:message name="GetLocationsSoapOut">
    <wsdl:part name="parameters" element="tns:GetLocationsResponse" />
  </wsdl:message>
  <wsdl:message name="GetActivationCodeSoapIn">
    <wsdl:part name="parameters" element="tns:GetActivationCode" />
  </wsdl:message>
  <wsdl:message name="GetActivationCodeSoapOut">
    <wsdl:part name="parameters" element="tns:GetActivationCodeResponse" />
  </wsdl:message>
  <wsdl:message name="GetProgramsSoapIn">
    <wsdl:part name="parameters" element="tns:GetPrograms" />
  </wsdl:message>
  <wsdl:message name="GetProgramsSoapOut">
    <wsdl:part name="parameters" element="tns:GetProgramsResponse" />
  </wsdl:message>
  <wsdl:message name="GetSessionTypesSoapIn">
    <wsdl:part name="parameters" element="tns:GetSessionTypes" />
  </wsdl:message>
  <wsdl:message name="GetSessionTypesSoapOut">
    <wsdl:part name="parameters" element="tns:GetSessionTypesResponse" />
  </wsdl:message>
  <wsdl:message name="GetResourcesSoapIn">
    <wsdl:part name="parameters" element="tns:GetResources" />
  </wsdl:message>
  <wsdl:message name="GetResourcesSoapOut">
    <wsdl:part name="parameters" element="tns:GetResourcesResponse" />
  </wsdl:message>
  <wsdl:message name="GetRelationshipsSoapIn">
    <wsdl:part name="parameters" element="tns:GetRelationships" />
  </wsdl:message>
  <wsdl:message name="GetRelationshipsSoapOut">
    <wsdl:part name="parameters" element="tns:GetRelationshipsResponse" />
  </wsdl:message>
  <wsdl:message name="GetResourceScheduleSoapIn">
    <wsdl:part name="parameters" element="tns:GetResourceSchedule" />
  </wsdl:message>
  <wsdl:message name="GetResourceScheduleSoapOut">
    <wsdl:part name="parameters" element="tns:GetResourceScheduleResponse" />
  </wsdl:message>
  <wsdl:message name="ReserveResourceSoapIn">
    <wsdl:part name="parameters" element="tns:ReserveResource" />
  </wsdl:message>
  <wsdl:message name="ReserveResourceSoapOut">
    <wsdl:part name="parameters" element="tns:ReserveResourceResponse" />
  </wsdl:message>
  <wsdl:message name="GetMobileProvidersSoapIn">
    <wsdl:part name="parameters" element="tns:GetMobileProviders" />
  </wsdl:message>
  <wsdl:message name="GetMobileProvidersSoapOut">
    <wsdl:part name="parameters" element="tns:GetMobileProvidersResponse" />
  </wsdl:message>
  <wsdl:message name="GetProspectStagesSoapIn">
    <wsdl:part name="parameters" element="tns:GetProspectStages" />
  </wsdl:message>
  <wsdl:message name="GetProspectStagesSoapOut">
    <wsdl:part name="parameters" element="tns:GetProspectStagesResponse" />
  </wsdl:message>
  <wsdl:message name="GetGendersSoapIn">
    <wsdl:part name="parameters" element="tns:GetGenders" />
  </wsdl:message>
  <wsdl:message name="GetGendersSoapOut">
    <wsdl:part name="parameters" element="tns:GetGendersResponse" />
  </wsdl:message>
  <wsdl:portType name="Site_x0020_ServiceSoap">
    <wsdl:operation name="GetSites">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list of sites.</wsdl:documentation>
      <wsdl:input message="tns:GetSitesSoapIn" />
      <wsdl:output message="tns:GetSitesSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetLocations">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list of locations.</wsdl:documentation>
      <wsdl:input message="tns:GetLocationsSoapIn" />
      <wsdl:output message="tns:GetLocationsSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetActivationCode">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets an activation code.</wsdl:documentation>
      <wsdl:input message="tns:GetActivationCodeSoapIn" />
      <wsdl:output message="tns:GetActivationCodeSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetPrograms">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list of programs.</wsdl:documentation>
      <wsdl:input message="tns:GetProgramsSoapIn" />
      <wsdl:output message="tns:GetProgramsSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetSessionTypes">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list of session types.</wsdl:documentation>
      <wsdl:input message="tns:GetSessionTypesSoapIn" />
      <wsdl:output message="tns:GetSessionTypesSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetResources">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list of resources.</wsdl:documentation>
      <wsdl:input message="tns:GetResourcesSoapIn" />
      <wsdl:output message="tns:GetResourcesSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetRelationships">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list of relationships.</wsdl:documentation>
      <wsdl:input message="tns:GetRelationshipsSoapIn" />
      <wsdl:output message="tns:GetRelationshipsSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetResourceSchedule">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets all resources schedule.</wsdl:documentation>
      <wsdl:input message="tns:GetResourceScheduleSoapIn" />
      <wsdl:output message="tns:GetResourceScheduleSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="ReserveResource">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Reserves a resource.</wsdl:documentation>
      <wsdl:input message="tns:ReserveResourceSoapIn" />
      <wsdl:output message="tns:ReserveResourceSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetMobileProviders">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list of active mobile providers.</wsdl:documentation>
      <wsdl:input message="tns:GetMobileProvidersSoapIn" />
      <wsdl:output message="tns:GetMobileProvidersSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetProspectStages">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list of prospect stages for a site.</wsdl:documentation>
      <wsdl:input message="tns:GetProspectStagesSoapIn" />
      <wsdl:output message="tns:GetProspectStagesSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetGenders">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gets a list of prospect stages for a site.</wsdl:documentation>
      <wsdl:input message="tns:GetGendersSoapIn" />
      <wsdl:output message="tns:GetGendersSoapOut" />
    </wsdl:operation>
  </wsdl:portType>
  <wsdl:binding name="Site_x0020_ServiceSoap" type="tns:Site_x0020_ServiceSoap">
    <soap:binding transport="http://schemas.xmlsoap.org/soap/http" />
    <wsdl:operation name="GetSites">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetSites" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetLocations">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetLocations" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetActivationCode">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetActivationCode" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetPrograms">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetPrograms" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetSessionTypes">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetSessionTypes" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetResources">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetResources" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetRelationships">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetRelationships" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetResourceSchedule">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetResourceSchedule" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ReserveResource">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/ReserveResource" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetMobileProviders">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetMobileProviders" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetProspectStages">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetProspectStages" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetGenders">
      <soap:operation soapAction="http://clients.mindbodyonline.com/api/0_5/GetGenders" style="document" />
      <wsdl:input>
        <soap:entityLookUp use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:entityLookUp use="literal" />
      </wsdl:output>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:service name="Site_x0020_Service">
    <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Provides methods and attributes relating to sites and locations.</wsdl:documentation>
    <wsdl:port name="Site_x0020_ServiceSoap" binding="tns:Site_x0020_ServiceSoap">
      <soap:address location="https://api.mindbodyonline.com/0_5/SiteService.asmx" />
    </wsdl:port>
  </wsdl:service>
</wsdl:definitions>