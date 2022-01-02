package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.TermsAndConditions;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TermsAndConditionsRepository extends CrudRepository<TermsAndConditions, Long> {

    String GET_HTML = "SELECT * FROM TermsAndConditions LIMIT 1";

    @Query(value = GET_HTML, nativeQuery = true)
    TermsAndConditions getHtml();
}
