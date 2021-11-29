package com.spring.group.repository;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.group.model.GroupEntity;
import com.spring.group.model.QGroupEntity;

@Repository
public class GroupRepositorySupport extends QuerydslRepositorySupport {
	
	private final JPAQueryFactory queryFactory;
	QGroupEntity groupEntity =  QGroupEntity.groupEntity;
	
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
