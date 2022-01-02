package com.fitnessplayground.service;

import com.fitnessplayground.security.User;

/**
 * Created by micheal on 22/06/2017.
 */
public interface UserService {
    User findUserByEmail(String email);
    void saveUser(User user);
}
