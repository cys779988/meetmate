package com.spring.board.repository;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.board.model.CourseEntity;
import com.spring.board.model.QCourseEntity;

@Repository
public class CourseRepositorySupport extends QuerydslRepositorySupport {
	
	private final JPAQueryFactory queryFactory;
	
	public CourseRepositorySupport(JPAQueryFactory queryFactory) {
		super(CourseEntity.class);
		this.queryFactory = queryFactory;
	}
	
	public Long findCountByTitle(String param) {
		return queryFactory
				.selectFrom(QCourseEntity.courseEntity)
				.where(QCourseEntity.courseEntity.courseDiv.like(param+"%"))
				.fetchCount();
	}
}
