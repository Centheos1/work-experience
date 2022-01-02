package com.fitnessplayground.service;

import com.fitnessplayground.dao.domain.CloudSearch;
import com.fitnessplayground.dao.domain.temp.FindEnrolment;

import java.util.ArrayList;

public interface CloudSearchService {

    Boolean addMembers(ArrayList<CloudSearch> members);
    ArrayList<CloudSearch> searchMembers(String query);
    void refresh();
}
