package com.fitnessplayground.dao.repository;

import com.fitnessplayground.security.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by micheal on 22/06/2017.
 */
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}
