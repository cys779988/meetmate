package com.spring.course.repository;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.course.model.Assignment;
import com.spring.course.model.GroupDto;
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
	
	public List<GroupDto> findUsersInGroupById(Long no) {
		Expression<String> cases = new CaseBuilder()
				.when(groupEntity.divNo.isNull()
						.or(groupEntity.divNo.eq(0L))
					).then(Assignment.N.getValue())
				.otherwise(Assignment.Y.getValue()).as("assignmentType");
		
		return queryFactory.select(Projections.bean(GroupDto.class
													, cases
													, groupEntity.id.course.id.as("courseId")
													, groupEntity.id.member.email.as("memberId")
													, groupEntity.id.member.name.as("memberName")
													, groupEntity.createdDate
													, groupEntity.divNo))
											.from(groupEntity)
											.where(groupEntity.id.course.id.eq(no))
											.fetch();
	}
}
