package com.example.jwt2.javainuse.service;

import com.example.jwt2.javainuse.entity.User;
import com.example.jwt2.javainuse.entity.UserRole;

public interface UserService {

    User createUser(User user);

    User getUserByUserNameRegister(String userName);

    UserRole createRole(UserRole userRole);

}
