package com.spring.group.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.group.model.GroupEntity;
import com.spring.group.model.GroupID;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, GroupID> {
	List<GroupEntity> findById_course_id(Long courseId);
}