package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.Credentials;
import org.springframework.data.repository.CrudRepository;

public interface CredentialsRepository extends CrudRepository<Credentials, Long> {
}
