package com.spring.course.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.course.model.CourseEntity;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
	Page<CourseEntity> findByCategoryId(Pageable pageable, Long id);
	
	List<CourseEntity> findByRegistrant_email(String email);
}