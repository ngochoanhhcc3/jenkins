package com.example.jwt2.javainuse.service.impl;

import com.example.jwt2.javainuse.entity.User;
import com.example.jwt2.javainuse.entity.UserRole;
import com.example.jwt2.javainuse.repository.UserRepository;
import com.example.jwt2.javainuse.repository.UserRoleRepository;
import com.example.jwt2.javainuse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public User createUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public User getUserByUserNameRegister(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public UserRole createRole(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }
}
