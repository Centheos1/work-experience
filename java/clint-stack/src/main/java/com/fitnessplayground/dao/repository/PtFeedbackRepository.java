package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.PtFeedbackData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface PtFeedbackRepository extends CrudRepository<PtFeedbackData, Long> {

    String GET_LAST_30_DAYS = "SELECT * FROM PtFeedbackData WHERE createDate >= DATE_SUB(NOW(), INTERVAL 30 DAY)";

    @Query(value = GET_LAST_30_DAYS, nativeQuery = true)
    ArrayList<PtFeedbackData> getLast30Days();

}
