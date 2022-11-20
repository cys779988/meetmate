package com.spring.course.repository;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.course.model.CourseEntity;
import com.spring.course.model.QCourseEntity;

@Repository
public class CourseRepositorySupport extends QuerydslRepositorySupport {
	
	private final JPAQueryFactory queryFactory;
	QCourseEntity courseEntity =  QCourseEntity.courseEntity;
	
	public CourseRepositorySupport(JPAQueryFactory queryFactory) {
		super(CourseEntity.class);
		this.queryFactory = queryFactory;
	}
	
	public long updateCurNum(Long no) {
		return queryFactory.update(courseEntity)
					.set(courseEntity.curNum, courseEntity.curNum.add(1))
					.where(courseEntity.id.eq(no))
					.execute();
	}
}
