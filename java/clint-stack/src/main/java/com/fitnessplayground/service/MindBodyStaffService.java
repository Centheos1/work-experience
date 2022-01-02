package com.fitnessplayground.service;

import com.fitnessplayground.model.mindbody.api.staff.Location;
import com.fitnessplayground.model.mindbody.api.staff.Permission;
import com.fitnessplayground.model.mindbody.api.staff.Staff;

import java.util.List;

/**
 * Created by micheal on 26/03/2017.
 */
public interface MindBodyStaffService {
    List<Staff> getStaffMembers();
    List<Permission> getStaffPermissions(long staffId, List<Location> locations);
}
