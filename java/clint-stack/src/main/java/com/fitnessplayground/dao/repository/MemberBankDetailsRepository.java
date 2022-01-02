package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.MemberBankDetail;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by micheal on 26/02/2017.
 */
public interface MemberBankDetailsRepository extends CrudRepository<MemberBankDetail, Long> {
}
