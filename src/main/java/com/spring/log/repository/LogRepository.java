package com.spring.log.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.log.model.LogEntity;

@Repository
public interface LogRepository extends JpaRepository<LogEntity, Long>{

}
