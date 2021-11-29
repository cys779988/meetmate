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
	
	public Long findCountByTitle(String param) {
		return queryFactory
				.selectFrom(courseEntity)
				.where(courseEntity.title.like(param+"%"))
				.fetchCount();
	}
	
	public void updateCourse(CourseEntity param) {
		queryFactory.update(courseEntity)
					.set(courseEntity.title, param.getTitle())
					.set(courseEntity.category, param.getCategory())
					.set(courseEntity.divclsNo, param.getDivclsNo())
					.set(courseEntity.maxNum, param.getMaxNum())
					.set(courseEntity.content, param.getContent())
					.set(courseEntity.node, param.getNode())
					.set(courseEntity.edge, param.getEdge())
					.where(courseEntity.id.eq(param.getId()))
					.execute();
	}

	public void updateCurNum(Long no) {
		queryFactory.update(courseEntity)
					.set(courseEntity.curNum, courseEntity.curNum.add(1))
					.where(courseEntity.id.eq(no))
					.execute();
	}
}
