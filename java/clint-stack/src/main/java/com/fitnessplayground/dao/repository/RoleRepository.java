package com.fitnessplayground.dao.repository;

import com.fitnessplayground.security.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by micheal on 22/06/2017.
 */
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByRole(String role);
}
