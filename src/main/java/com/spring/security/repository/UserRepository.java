package com.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.security.model.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
}