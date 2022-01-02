package com.fitnessplayground.model.mindbody.api.util;

import com.fitnessplayground.model.mindbody.api.staff.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by micheal on 26/03/2017.
 */
public class MindBodyStaffServiceUtil  extends MindBodyServiceUtil {
    private static final ObjectFactory objectFactory = new ObjectFactory();

    private static Map<String,Long> staffMap = new HashMap<>();

    public static void setStaffMembers(List<Staff> staffList) {
        // TODO understand how to do this in streams/lambda
        System.out.println("Syncing Staff members by retrieving from MindBody");

        try {
            for(Staff staff : staffList) {
                System.out.println("*** Staff name "+staff.getName() + " id: " + staff.getID().getValue());// + " location: " + staff.getLoginLocations().getLocation().get(0).getBusinessID().getValue());
                if(staffMap.containsKey(staff.getName())) {
                    System.out.println("* already have "+staff.getName()+" : "+staffMap.get(staff.getName()));
                }
                staffMap.put(staff.getName(), staff.getID().getValue());
            }

        } catch (NullPointerException ex) {
            logger.error("Error in setStaffMembers {}", ex.getMessage());
        }


        // This fails if there are duplicate keys (ie staff names)
//        try {
//            staffMap = staffList.stream().collect(Collectors.toMap(Staff::getName, s -> s.getID().getValue()));
//        }catch (Exception ex) {
//int b = 2;
//        }
    }

    /**
     * Return the id for the supplier staff member name
     * @param staffMemberName
     * @return
     */
    public static Long getStaffMemberId(String staffMemberName) {
        return staffMap.get(staffMemberName);
    }

    /**
     *
     * @return
     */
    public static SourceCredentials buildSourceCredentials() {
        // Source credentials
        SourceCredentials sc = new SourceCredentials();
        sc.setSourceName(getMindbodySourceCredentialsUsername());
        sc.setPassword(getMindbodySourceCredentialsPassword());
        ArrayOfInt siteIds = objectFactory.createArrayOfInt();
        siteIds.getInt().add(new Integer(getMindbodySiteId()));
        siteIds.getInt().add(new Integer(getMindbodySiteIdDarwin()));
        sc.setSiteIDs(siteIds);
        return sc;
    }

    /**
     * When performing an admin task on behalf of a client, like purchasing a membership
     * we need to supply the user credentials that we are performing the task on behalf of
     * @return
     */
    public static UserCredentials buildUserCredentials() {
        // User
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUsername(getMindbodyUserCredentialsUsername());
        userCredentials.setPassword(getMindbodyUserCredentialsPassword());
//        userCredentials.setUsername("Siteowner");  // sandbox
//        userCredentials.setPassword("apitest1234");  // sandbox
        ArrayOfInt siteIds = objectFactory.createArrayOfInt();
        siteIds.getInt().add(new Integer(getMindbodySiteId()));
        siteIds.getInt().add(new Integer(getMindbodySiteIdDarwin()));
        userCredentials.setSiteIDs(siteIds);
        return userCredentials;
    }

    public static SourceCredentials buildSourceCredentials(String siteId) {
        // Source credentials
        SourceCredentials sc = new SourceCredentials();
        sc.setSourceName(getMindbodySourceCredentialsUsername());
        sc.setPassword(getMindbodySourceCredentialsPassword());
        ArrayOfInt siteIds = objectFactory.createArrayOfInt();
        siteIds.getInt().add(new Integer(siteId));
        sc.setSiteIDs(siteIds);
        return sc;
    }

    public static UserCredentials buildUserCredentials(String siteId) {
        // User
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUsername(getMindbodyUserCredentialsUsername());
        userCredentials.setPassword(getMindbodyUserCredentialsPassword());
        ArrayOfInt siteIds = objectFactory.createArrayOfInt();
        siteIds.getInt().add(new Integer(siteId));
        userCredentials.setSiteIDs(siteIds);
        return userCredentials;
    }

    /**
     * Build the GetStaffRequest
     *
     * @param sourceCredentials
     * @return
     */
    public static GetStaff buildGetStaff(SourceCredentials sourceCredentials) {
        // Tip: Was getting Caused by: com.sun.istack.internal.SAXException2: unable to marshal type "com.fitnessplayground.model.mindbody.api.staff.GetStaffRequest" as an element because it is missing an @XmlRootElement annotation
        // cos i Was dointhis GetStaffREquest g = new GetStaffREquest
        GetStaffRequest getStaffRequest = objectFactory.createGetStaffRequest();
        getStaffRequest.setSourceCredentials(sourceCredentials);
        // @FIXME: if we always include this, does it break the get clients api call ?
//        if(null != userCredentials) {
//            staffRequest.setUserCredentials(userCredentials);
//        }
        getStaffRequest.setXMLDetail(XMLDetailLevel.FULL);
        getStaffRequest.setPageSize(1000);
        getStaffRequest.setCurrentPageIndex(0);
        ArrayOfString filters = objectFactory.createArrayOfString();
        filters.getString().add("Staff.Locations");
        getStaffRequest.setFields(filters);

        GetStaff getStaff = objectFactory.createGetStaff();
//        GetStaff getStaff = new GetStaff();
        getStaff.setRequest(getStaffRequest);

        return getStaff;
    }

    public static GetStaffPermissions buildGetStaffPermissions(SourceCredentials sourceCredentials, UserCredentials userCredentials, long staffId) {
        GetStaffPermissionsRequest getStaffPermissionsRequest = objectFactory.createGetStaffPermissionsRequest();
        getStaffPermissionsRequest.setSourceCredentials(sourceCredentials);
        getStaffPermissionsRequest.setUserCredentials(userCredentials);
        getStaffPermissionsRequest.setStaffID(staffId);

        GetStaffPermissions getStaffPermissions = objectFactory.createGetStaffPermissions();
        getStaffPermissions.setRequest(getStaffPermissionsRequest);

        return  getStaffPermissions;
    }
}
