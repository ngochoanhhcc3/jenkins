package com.example.jwt2.javainuse.repository;

import com.example.jwt2.javainuse.entity.User;
import com.example.jwt2.javainuse.entity.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query(nativeQuery = true,  name = "findByUsernameDTO")
    UserDto findByUsernameDTO(String userName);

    User findByUsername(String userName);

}