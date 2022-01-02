package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.Encrypt;
import org.springframework.data.repository.CrudRepository;

public interface SecurityRepository extends CrudRepository<Encrypt, Long> {
}
