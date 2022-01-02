package com.fitnessplayground.service.impl;

import com.fitnessplayground.config.MindBodyStaffConfig;
import com.fitnessplayground.model.mindbody.api.staff.*;
import com.fitnessplayground.model.mindbody.api.util.MindBodyStaffServiceUtil;
import com.fitnessplayground.service.MindBodyServiceGateway;
import com.fitnessplayground.service.MindBodyStaffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.soap.SoapMessage;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.fitnessplayground.model.mindbody.api.util.MindBodyServiceUtil.getMindbodySiteId;
import static com.fitnessplayground.model.mindbody.api.util.MindBodyServiceUtil.getMindbodySiteIdDarwin;

/**
 * Created by micheal on 26/03/2017.
 */
@Service
public class MindBodyStaffServiceImpl implements MindBodyStaffService {

    private static final Logger logger = LoggerFactory.getLogger(MindBodyStaffServiceImpl.class);

    @Value("${mindbody.service.api.staff.getstaff.soapaction.url}")
    private String MINDBODY_GET_STAFF_SERVICE_URL;

    @Value("${mindbody.service.api.staff.getstaffpermissions.soapaction.url}")
    private String MINDBODY_GET_STAFF_PERMISSIONS_URL;

//    public List<Staff> getStaffMembers() {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MindBodyStaffConfig.class);
//        MindBodyServiceGateway gateway = context.getBean(MindBodyServiceGateway.class);
//
//        // Source credentials
//        SourceCredentials sc = MindBodyStaffServiceUtil.buildSourceCredentials();
//
//        GetStaff getStaff = MindBodyStaffServiceUtil.buildGetStaff(sc);
//        //  Invoke service
//        GetStaffResponse resp = (GetStaffResponse) gateway.getWebServiceTemplate().
//                marshalSendAndReceive(getStaff,
//                        new WebServiceMessageCallback() {
//                            public void doWithMessage(WebServiceMessage message) {
//                                ((SoapMessage) message).setSoapAction(MINDBODY_GET_STAFF_SERVICE_URL);
//                            }
//                        });
//
//        // FIXME: handle better, handle exceptions
//        return handleResponse(resp);
//    }

    public List<Staff> getStaffMembers() {

        String[] siteList = {getMindbodySiteIdDarwin(), getMindbodySiteId()};

        List<Staff> staffMembers = new ArrayList<>();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MindBodyStaffConfig.class);
        MindBodyServiceGateway gateway = context.getBean(MindBodyServiceGateway.class);

        for (String id : siteList) {

            // Source credentials
            SourceCredentials sc = MindBodyStaffServiceUtil.buildSourceCredentials(id);

            GetStaff getStaff = MindBodyStaffServiceUtil.buildGetStaff(sc);
            //  Invoke service
            GetStaffResponse resp = (GetStaffResponse) gateway.getWebServiceTemplate().
                    marshalSendAndReceive(getStaff,
                            new WebServiceMessageCallback() {
                                public void doWithMessage(WebServiceMessage message) {
                                    ((SoapMessage) message).setSoapAction(MINDBODY_GET_STAFF_SERVICE_URL);
                                }
                            });

            // FIXME: handle better, handle exceptions
            staffMembers.addAll(handleResponse(resp));
        }

        return staffMembers;
    }


    public List<Staff> handleResponse(GetStaffResponse response) {
        List<Staff>  staffFound = new ArrayList<>();
        if(null != response && null != response.getGetStaffResult() && null != response.getGetStaffResult().getStaffMembers()
                && null != response.getGetStaffResult().getStaffMembers().getStaff()) {
            staffFound = response.getGetStaffResult().getStaffMembers().getStaff();
        }
        return staffFound;
    }

    @Override
    public List<Permission> getStaffPermissions(long staffId, List<Location> locations) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MindBodyStaffConfig.class);
        MindBodyServiceGateway gateway = context.getBean(MindBodyServiceGateway.class);

        String siteId = "";

        for (Location loc : locations) {
            siteId = loc.getSiteID().toString();
        }

        // Source credentials
        SourceCredentials sc = MindBodyStaffServiceUtil.buildSourceCredentials(siteId);
        UserCredentials uc = MindBodyStaffServiceUtil.buildUserCredentials(siteId);

        GetStaffPermissions getStaffPermissions = MindBodyStaffServiceUtil.buildGetStaffPermissions(sc, uc, staffId);

        GetStaffPermissionsResponse response = (GetStaffPermissionsResponse) gateway.getWebServiceTemplate().marshalSendAndReceive(getStaffPermissions, new WebServiceMessageCallback() {
            @Override
            public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
                ((SoapMessage) message).setSoapAction(MINDBODY_GET_STAFF_PERMISSIONS_URL);
            }
        });

        return handleResponse(response);
    }

    public List<Permission> handleResponse(GetStaffPermissionsResponse response) {
        List<Permission> permissionsList = new ArrayList<>();
        if (response != null
                && response.getGetStaffPermissionsResult() != null
                && response.getGetStaffPermissionsResult().getPermissions() != null && response.getGetStaffPermissionsResult().getPermissions().getPermission() != null) {

            permissionsList = response.getGetStaffPermissionsResult().getPermissions().getPermission();
        }
        return  permissionsList;
    }

}
