package com.spring.course.repository;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.course.model.GroupEntity;
import com.spring.course.model.QCourseEntity;
import com.spring.group.model.QGroupEntity;

@Repository
public class GroupRepositorySupport extends QuerydslRepositorySupport {
	
	private final JPAQueryFactory queryFactory;
	QGroupEntity groupEntity =  QGroupEntity.groupEntity;
	QCourseEntity courseEntity = QCourseEntity.courseEntity;
	
	public GroupRepositorySupport(JPAQueryFactory queryFactory) {
		super(GroupEntity.class);
		this.queryFactory = queryFactory;
	}
	
	public void resetDivide(Long no) {
		queryFactory.update(groupEntity)
					.set(groupEntity.divNo, 0L)
					.where(groupEntity.id.course.id.eq(no))
					.execute();
	}
}
